package com.vision.controller.cus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.cus.CusSchedule;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.service.cus.CusScheduleService;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;


@Controller
@RequestMapping("/cusSchedule")
public class CusScheduleController {
	
	@Autowired
	private CusScheduleService cusScheduleService;
	
	
	/**基于用户/电话及当前页码值条件查询课程信息*/
	@RequestMapping("/getSchedule")
	@ResponseBody
	public JsonResult getSchedule( CusVo cusVo){
		try {
        	PageObject<CusSchedule> pageObject = cusScheduleService.getSchedule(cusVo);
        	if(!(pageObject.getRecords().size()==0)) {
        		return JsonResult.oK(pageObject);
        	}
		} catch (Exception e) {
			System.out.println("基于用户/电话及当前页码值条件查询课程信息=============错误=================");
		}
		return JsonResult.build(201, "查询无数据");
	}
	
	/**基于id删除课程信息*/
	@RequestMapping("/deleteSchedule")
	@ResponseBody
	public JsonResult deleteSchedule( Integer id, Integer orgId) {
		try {
			Integer rows = cusScheduleService.deleteSchedule(id,orgId);
			if(rows !=0 && rows !=null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			System.out.println("基于id删除课程信息=============错误=================");
		}
		return JsonResult.build(201, "该数据可能已经被删除");
	}
	
	/**基于id查询课程信息*/
	@RequestMapping("/getScheduleById")
	@ResponseBody
	public JsonResult getScheduleById( Integer id, Integer orgId) {
		try {
			CusSchedule cusSchedule = cusScheduleService.getScheduleById(id, orgId);
			if(cusSchedule != null) {
				return JsonResult.oK(cusSchedule);
			}
		} catch (Exception e) {
			System.out.println("基于id查询课程信息=============错误=================");
		}
		return JsonResult.build(201, "修改查询数据错误");
	}
	
	/**创建客户课程表*/
	@RequestMapping("/addSchedule")
	@ResponseBody
	public JsonResult addSchedule( CusSchedule cusSchedule) {
		//获取登录用户信息
////    	Users user = ShiroUtils.getUser();
//		cusSchedule.setOrgId(1);
		cusSchedule.setCreatedUser("admin");
		cusSchedule.setModifiedUser("admin");
		try {
			Integer rows = cusScheduleService.addSchedule(cusSchedule);
			if(rows != 0 && rows != null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			System.out.println("创建客户课程表=============错误=================");
		}
		return JsonResult.build(201, "保存数据错误,请稍后重试");
	}
	
	/**修改课程表数据*/
	@RequestMapping("/updateSchedule")
	@ResponseBody
	public JsonResult updateSchedule( CusSchedule cusSchedule) {
		try {
			cusSchedule.setModifiedUser("admin");
			Integer rows = cusScheduleService.updateSchedule(cusSchedule);
			if(rows != 0 && rows != null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			System.out.println("修改课程表数据=============错误=================");
		}
		return JsonResult.build(201, "修改数据错误,请稍后重试");
	}
	
	/**基于客户id查询用户课程表信息*/
	@RequestMapping("/getByCustomerId")
	@ResponseBody
	public JsonResult getByCustomerId(CusVo cusVo) {
		try {
			List<CusSchedule> list = cusScheduleService.getByCustomerId(cusVo);
			if(list.size()!=0 && list != null) {
				return JsonResult.oK(list);
			}
		} catch (Exception e) {
			System.out.println("基于客户id查询用户课程表信息=============错误=================");
		}
		return JsonResult.build(201, "该用户无课程,需添加课程信息");
	}
	
}
