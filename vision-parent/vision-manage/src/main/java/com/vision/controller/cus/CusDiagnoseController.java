package com.vision.controller.cus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.exception.ServiceException;
import com.vision.pojo.cus.CusDiagnose;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.service.cus.CusDiagnoseService;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;


@Controller
@RequestMapping("/diagnose")
public class CusDiagnoseController {
	
	@Autowired
	private CusDiagnoseService cusDiagnoseService;
	
	
	/**诊断表页面加载,查询*/
	@RequestMapping("/getDiagnose")
	@ResponseBody
	public JsonResult getDiagnose(CusVo cusVo){
		//1.数据合法性验证
		if(cusVo.getPageCurrent()==null||cusVo.getPageCurrent()<=0)
			return JsonResult.build(201, "页码值不正确");
		if(cusVo.getOrgId()<0||cusVo.getOrgId()==null)
			return JsonResult.build(201, "门店id不正确");
		if(cusVo.getPageSize()<0||cusVo.getPageSize()==null)
			return JsonResult.build(201, "页码大小不正确");
		
		try {
			PageObject<CusDiagnose> pageObject = cusDiagnoseService.getDiagnose(cusVo);
			return JsonResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("诊断表页面加载,查询=============错误=================");
		}
		return JsonResult.build(201, "查询无数据");
	}
	
	/**基于id,查询相关id所有信息*/
	@RequestMapping("/getDiagnoseById")
	@ResponseBody
	public JsonResult getDiagnoseById(Integer id, Integer orgId) {
		if(id==null||id<0)
			return JsonResult.build(201, "id错误");
		if(orgId==null||orgId<0)
			return JsonResult.build(201, "orgId错误");
		
		try {
			CusDiagnose cusDiagnose = cusDiagnoseService.getDiagnoseById(id,orgId);
			return JsonResult.oK(cusDiagnose);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于咨询表id,查询相关id所有信息=============错误=================");
		}
		return JsonResult.build(201, "修改查询数据错误");
	}
	
	/**基于客户id查询诊断表相关信息*/
	@RequestMapping("/getByCustomerId")
	@ResponseBody
	public JsonResult getByCustomerId(Integer customerId ,Integer orgId) {
		if(customerId==null||customerId<0)
			return JsonResult.build(201, "customerId错误");
		if(orgId==null||orgId<0)
			return JsonResult.build(201, "orgId错误");
		
		try {
			CusDiagnose cusDiagnose = cusDiagnoseService.getByCustomerId(customerId, orgId);
			if(cusDiagnose != null) {
				return JsonResult.oK(cusDiagnose);
			} else if(cusDiagnose == null){
				return JsonResult.build(203, "无数据,需新增数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于客户id查询诊断表相关信息=============错误=================");
		}
		return JsonResult.build(201, "查询数据错误");
	}
	
	/**基于客户id创建客户诊断表*/
	@RequestMapping("/addDiagnose")
	@ResponseBody
	public JsonResult addDiagnose(CusDiagnose cusDiagnose) {
		if(cusDiagnose==null)
			return JsonResult.build(201, "数据错误");
		if(cusDiagnose.getOrgId()==null||cusDiagnose.getOrgId()<0)
			return JsonResult.build(201, "orgId错误");
		//获取登录用户信息
//    	Users user = ShiroUtils.getUser();
		cusDiagnose.setCreatedUser("admin");
		cusDiagnose.setModifiedUser("admin");
		try {
			Integer row = cusDiagnoseService.addDiagnose(cusDiagnose);
			if(row != 0 && row != null) {
				return JsonResult.oK("添加成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于客户id创建客户诊断表=============错误=================");
		}
		return JsonResult.build(201, "新增诊断表错误");
	}
	
	/**基于诊断表id删除数据*/
	@RequestMapping("/deleteDiagnose")
	@ResponseBody
	public JsonResult deleteDiagnose(Integer id, Integer orgId) {
		//验证数据
		if(id==null||id<0)
			return JsonResult.build(201, "请选择数据");
		if(orgId==null||orgId<0)
			return JsonResult.build(201, "orgId错误");
		try {
			Integer row = cusDiagnoseService.deleteDiagnose(id, orgId);
			if(row != null) {
				return JsonResult.oK("数据已删除");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于诊断表id删除数据=============错误=================");
		}
		return JsonResult.build(201, "数据可能已删除,请刷新");
	}
	
	
}
