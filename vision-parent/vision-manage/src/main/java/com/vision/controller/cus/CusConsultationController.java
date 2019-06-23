package com.vision.controller.cus;

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
    public JsonResult getById( CusVo cusVo){
    	try {
    		PageObject<CusConsultation> pageObject = cusConsultationService.getConsultation(cusVo);
    		if(!(pageObject.getRecords().size()==0)) {
        		return JsonResult.oK(pageObject);
        	}
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
    	//获取登录用户信息
    	try {
			Integer row = cusConsultationService.addConsultation(cusConsultation);
			if(row != 0 || row == null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			System.out.println("将CusCustomer类型数据添加到数据库=============错误==================");
		}
    	return JsonResult.build(201, "保存失败");
    }
    
	/**基于id删除咨询表信息*/
	@RequestMapping("deleteConsultation")
	@ResponseBody
	public JsonResult deleteConsultation(Integer id, Integer  orgId) {
		try {
			Integer row = cusConsultationService.deleteConsultation(id, orgId);
			if(row != 0 || row == null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			System.out.println("基于id删除咨询表信息=============错误==================");
		}
		return JsonResult.build(201, "删除失败,数据或已删除");
	}
    
	/**基于咨询表id,查询相关id所有信息*/
	@RequestMapping("getConsultationById")
	@ResponseBody
	public JsonResult findObjectById(Integer id, Integer orgId) {
		try {
			CusConsultation cusConsultation = cusConsultationService.getConsultationById(id, orgId);
			if(cusConsultation != null) {
				return JsonResult.oK(cusConsultation);
			}
		} catch (Exception e) {
			System.out.println("基于咨询表id,查询相关id所有信息=============错误==================");
		}
		return JsonResult.build(201, "修改查询失败");
	}
	

	/**基于咨询表id更改用户信息*/
	@RequestMapping("updateConsultation")
	@ResponseBody
	public JsonResult updateConsultation(CusConsultation cusConsultation) {
		//获取登录用户信息
		try {
			Integer row = cusConsultationService.updateConsultation(cusConsultation);
			if(row != 0 || row == null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			System.out.println("基于咨询表id更改用户信息=============错误==================");
		}
		return JsonResult.build(201, "修改保存失败");
	}

}
