package com.vision.controller.cus;

import com.alibaba.druid.util.StringUtils;
import com.vision.exception.ServiceException;
import com.vision.pojo.cus.CusConsultation;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.service.cus.CusConsultationService;
import com.vision.vo.PageObject;
import com.vision.vo.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/consultation")
public class CusConsultationController {

    @Autowired
    private CusConsultationService cusConsultationService;

    
    
    /**基于用户/电话及当前页码值条件查询用户信息*/
    @RequestMapping("/getConsultation")
    @ResponseBody
    public JsonResult getConsultation( CusVo cusVo){
    	//1.数据合法性验证
		Integer pageCurrent = cusVo.getPageCurrent();
		Integer orgId = cusVo.getOrgId();
		Integer pageSize = cusVo.getPageSize();
		if(pageCurrent==null||pageCurrent<=0)
			return JsonResult.build(201, "页码值不正确");
		if(orgId<0||orgId==null)
			return JsonResult.build(201, "门店信息不正确");
		if(pageSize<0||pageSize==null)
			return JsonResult.build(201, "页码大小不正确");
		
		
    	try {
    		PageObject<CusConsultation> pageObject = cusConsultationService.getConsultation(cusVo);
        		return JsonResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于用户/电话及当前页码值条件查询用户信息=============错误=================");
		}
    	
    	return JsonResult.build(201, "查询无数据");
    }
    
    /**将CusCustomer类型数据添加到数据库*/
    @RequestMapping("/addConsultation")
    @ResponseBody
    public JsonResult addConsultation( CusConsultation cusConsultation) {
    	
    	//验证数据合法性
    	if(cusConsultation==null)
    		return JsonResult.build(201, "对象不能为空");
    	if(cusConsultation.getOrgId()<0||cusConsultation.getOrgId()==null)
    		return JsonResult.build(201, "门店orgId不能为空");
    	if(StringUtils.isEmpty(cusConsultation.getName()))
    		return JsonResult.build(201, "用户名不能为空");
    	if(StringUtils.isEmpty(cusConsultation.getTel()))
    		return JsonResult.build(201, "电话不能为空");
    	
    	//获取登录用户信息
    	try {
			Integer row = cusConsultationService.addConsultation(cusConsultation);
			if(row != 0 || row == null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("将CusCustomer类型数据添加到数据库=============错误==================");
		}
    	
    	return JsonResult.build(201, "保存失败");
    }
    
	/**基于id删除咨询表信息*/
	@RequestMapping("deleteConsultation")
	@ResponseBody
	public JsonResult deleteConsultation(Integer id, Integer  orgId) {
		//验证数据合法性
		if(orgId==null||orgId<0)
			return JsonResult.build(201, "请选择一条数据");
		if(id==null||id<=0)
			return JsonResult.build(201, "请选择一条数据");
		
		try {
			Integer row = cusConsultationService.deleteConsultation(id, orgId);
			if(row != 0 || row == null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于id删除咨询表信息=============错误==================");
		}
		return JsonResult.build(201, "删除失败,数据或已删除");
	}
    
	/**基于咨询表id,查询相关id所有信息*/
	@RequestMapping("getConsultationById")
	@ResponseBody
	public JsonResult findObjectById(Integer id, Integer orgId) {
		
		//验证数据合法性
		if(orgId==null||orgId<0)
			return JsonResult.build(201, "orgId错误");
		if(id==null||id<=0)
			return JsonResult.build(201, "id错误");
		
		try {
			CusConsultation cusConsultation = cusConsultationService.getConsultationById(id, orgId);
			if(cusConsultation != null) {
				return JsonResult.oK(cusConsultation);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于咨询表id,查询相关id所有信息=============错误==================");
		}
		return JsonResult.build(201, "修改查询失败");
	}
	

	/**基于咨询表id更改用户信息*/
	@RequestMapping("updateConsultation")
	@ResponseBody
	public JsonResult updateConsultation(CusConsultation cusConsultation) {
		
		//验证数据合法性
    	if(cusConsultation==null)
    		return JsonResult.build(201, "对象不能为空");
		if(cusConsultation.getId()<0||cusConsultation.getId()==null)
			return JsonResult.build(201, "对象id不能为空");
    	if(cusConsultation.getOrgId()<0||cusConsultation.getOrgId()==null)
    		return JsonResult.build(201, "门店orgId不能为空");
    	if(StringUtils.isEmpty(cusConsultation.getName()))
    		return JsonResult.build(201, "用户名不能为空");
    	if(StringUtils.isEmpty(cusConsultation.getTel()))
    		return JsonResult.build(201, "电话不能为空");
		
		//获取登录用户信息
		try {
			Integer row = cusConsultationService.updateConsultation(cusConsultation);
			if(row != 0 || row == null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于咨询表id更改用户信息=============错误==================");
		}
		return JsonResult.build(201, "修改保存失败");
	}

}
