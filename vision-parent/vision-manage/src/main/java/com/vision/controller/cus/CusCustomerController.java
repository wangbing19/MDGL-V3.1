package com.vision.controller.cus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.sys.SysOrganization;
import com.vision.pojo.sys.SysUser;
import com.vision.service.cus.CusCustomerService;
import com.vision.service.sys.SysOrganizationService;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;

@Controller
@RequestMapping("/customer")
public class CusCustomerController {
	
	@Autowired
    private CusCustomerService cusCustomerService;
	@Autowired
	private SysOrganizationService sysOrganizationService;
	
	/**用户页面查看所有信息*/
    @RequestMapping("/getCustomer")
    @ResponseBody
    public JsonResult getCustomer( CusVo cusVo){
    	
    	//1.数据合法性验证
    	Integer pageCurrent = cusVo.getPageCurrent();
    	Integer orgId = cusVo.getOrgId();
    	Integer pageSize = cusVo.getPageSize();
    	if(pageCurrent==null||pageCurrent<=0) {
    		return JsonResult.build(201, "页码值不正确");
    	}
    	if(orgId==null||orgId<0) {
    		return JsonResult.build(201, "门店信息不正确");
    	}
    	if(pageSize==null||pageSize<0)
    		return JsonResult.build(201, "页码大小不正确");
    			
    	
    	try {
			PageObject<CusCustomer> pageObject = cusCustomerService.getCustomer(cusVo);
				return JsonResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("用户页面查看所有信息=============错误=================");
		}
    	return JsonResult.build(201, "查询无数据");
    }
    
    /**基于客户id查询客户信息*/
    @RequestMapping("/getCustomerById")
    @ResponseBody
    public JsonResult getCustomerById( Integer id, Integer orgId){
    	
    	//验证数据合法性
    	if(id==null||id<=0)
    		return JsonResult.build(201, "id错误");
		if(orgId==null||orgId<0)
			return JsonResult.build(201, "orgId错误");
    	
    	try {
    		CusCustomer cusCustomer = cusCustomerService.getCustomerById(id, orgId);
			if(cusCustomer != null) {
				return JsonResult.oK(cusCustomer);
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("基于客户id查询客户所有信息=============错误=================");
    	}
    	return JsonResult.build(201, "查询失败");
    }
    
	/**基于用户id修改用户状态*/
	@RequestMapping("updateCustomerState")
	@ResponseBody
	public JsonResult updateCustomerState( CusVo cusVo) {
		//获取登录用户信息

		if(cusVo.getId()==null||cusVo.getId()<=0)
			return JsonResult.build(201, "id错误");
		if(cusVo.getOrgId()==null||cusVo.getOrgId()<0)
			return JsonResult.build(201, "orgId错误");
		if(cusVo.getState()!=0 && cusVo.getState()!=1)
			return JsonResult.build(201, "状态错误");
		
//		//获取账号信息
//		SysUser user = null;
//		if(user.getOrganizationId()!=cusVo.getOrgId().longValue())
//			return JsonResult.build(201, "该账号不能修改该客户状态，请联系客户所属门店修改");
//		cusVo.setUser(user.getUserName());
		try {
			Integer row = cusCustomerService.updateCustomerState(cusVo);
			if(row != null && row != 0) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于用户id修改用户状态=============错误=================");
		}
		return JsonResult.build(201, "状态修改失败");
	}
	
	/**根据咨询表id查询客户表信息有无*/
	@RequestMapping("getCustomerByConsultationId")
	@ResponseBody
	public JsonResult getCustomerByConsultationId( Integer consultationId) {
		if(consultationId==null||consultationId<0)
			return JsonResult.build(201, "consultationId错误");
		try {
			CusCustomer cusCustomer = cusCustomerService.getCustomerByConsultationId(consultationId);
			if(cusCustomer!=null) {
				return JsonResult.oK(1);
			} else if(cusCustomer==null) {
				return JsonResult.oK(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("根据咨询表id查询客户表信息有无=============错误=================");
		}
		return JsonResult.build(201, "已有数据,无法添加");
	}
	
	/**将CusCustomer类型数据添加到数据库*/
	@RequestMapping("/addCustomer")
	@ResponseBody
	public JsonResult addCustomer( CusCustomer cusCustomer) {
		//验证数据合法性
		if(cusCustomer==null)
			return JsonResult.build(201, "对象不能为空");
		if(StringUtils.isEmpty(cusCustomer.getName()))
			return JsonResult.build(201, "用户名不能为空");
		if(StringUtils.isEmpty(cusCustomer.getTel()))
			return JsonResult.build(201, "电话不能为空");
		if(StringUtils.isEmpty(cusCustomer.getGuardian()))
			return JsonResult.build(201, "监护人不能为空");
		if(cusCustomer.getOrgId()==null||cusCustomer.getOrgId()<0)
			return JsonResult.build(201, "门店信息错误");
		if(cusCustomer.getConsultationId()==null||cusCustomer.getConsultationId()<0)
			return JsonResult.build(201, "咨询表信息错误");
		
		try {
			//获取登录用户信息
//        	SysUser user = null;
//        	cusCustomer.setCreatedUser(user.getUserName());
//        	cusCustomer.setModifiedUser(user.getUserName());
//        	//获取登录用户创建客户数量
//        	SysOrganization organization = sysOrganizationService.getSysOrganizationById(user.getOrganizationId());
//        	if(organization.getSurplus()<=0) {
//        		return JsonResult.build(201,"创建客户数量已达上限,无法再次创建客户,请联系总店208-62825475");
//        	}
			Integer row = cusCustomerService.addCustomer(cusCustomer);
			if(row != null && row != 0) {
				  //添加登录用户创建客户数量 
//				organization.setSurplus(organization.getSurplus()-row);
//				sysOrganizationService.updateOrganization(organization);
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("添加客户表信息=============错误=================");
		}
		return JsonResult.build(201, "此客户可能已存在");
	}
	
	/**基于id删除客户信息*/
	@RequestMapping("/deleteCustomer")
	@ResponseBody
	public JsonResult deleteCustomer( Integer id, Integer orgId) {
		//1.验证参数有效性
		if(id==null||id<0)
			return JsonResult.build(201, "id参数无效");
		if(orgId==null||orgId<0)
			return JsonResult.build(201, "orgId参数无效");
		//获取登录账号信息
//		SysUser user = null;
//		if(user.getOrganizationId()!=orgId.longValue())
//			return JsonResult.build(201, "该账户无法删除该客户信息，请联系客户所属门店修改");
		try {
			Integer row = cusCustomerService.deleteCustomer(id, orgId);
			if(row != null && row != 0) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于id删除客户信息=============错误=================");
		}
		return JsonResult.build(201, "此客户可能已经不存在");
	}
	
	/**基于客户id修改客户信息*/
	@RequestMapping("/updateCustomer")
	@ResponseBody
	public JsonResult updateCustomer( CusCustomer cusCustomer) {
		try {
			//验证数据合法性
			if(cusCustomer==null)
				return JsonResult.build(201, "对象不能为空");
			if(cusCustomer.getId()==null||cusCustomer.getId()<0)
				return JsonResult.build(201, "id错误");
			if(cusCustomer.getOrgId()==null||cusCustomer.getOrgId()<0)
				return JsonResult.build(201, "orgId错误");
			if(StringUtils.isEmpty(cusCustomer.getName()))
				return JsonResult.build(201, "客户名不能为空");
			if(StringUtils.isEmpty(cusCustomer.getTel()))
				return JsonResult.build(201, "电话不能为空");
			if(StringUtils.isEmpty(cusCustomer.getGuardian()))
				return JsonResult.build(201, "监护人不能为空");
			//获取登录用户信息
//			SysUser user = null;
//			if(user.getOrganizationId()!=cusCustomer.getOrgId().longValue())
//				return JsonResult.build(201, "该账户无法修改该客户信息，请联系客户所属门店修改");
//			cusCustomer.setModifiedUser(user.getUserName());
			
			Integer row = cusCustomerService.updateCustomer(cusCustomer);
			if(row != 0 && row != null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("修改客户表信息=============错误=================");
		}
		return JsonResult.build(201, "此客户信息修改失败");
	}
	
	
}
