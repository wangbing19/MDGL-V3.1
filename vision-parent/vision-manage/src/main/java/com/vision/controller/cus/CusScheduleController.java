package com.vision.controller.cus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.vision.exception.ServiceException;
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
	
	
	/**基于用户及当前页码值条件查询课程信息*/
	@RequestMapping("/getSchedule")
	@ResponseBody
	public JsonResult getSchedule( CusVo cusVo){
		try {

			//1.数据合法性验证
			if(cusVo.getPageCurrent()==null||cusVo.getPageCurrent()<=0)
				return JsonResult.build(201, "页码值不正确");
			if(cusVo.getOrgId()==null||cusVo.getOrgId()<0)
				return JsonResult.build(201, "门店信息不正确");
			if(cusVo.getPageSize()==null||cusVo.getPageSize()<0)
				return JsonResult.build(201, "pageSize不正确");
			
        	PageObject<CusSchedule> pageObject = cusScheduleService.getSchedule(cusVo);
        	return JsonResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于用户/电话及当前页码值条件查询课程信息=============错误=================");
		}
		return JsonResult.build(201, "查询无数据");
	}
	
	/**基于id删除课程信息*/
	@RequestMapping("/deleteSchedule")
	@ResponseBody
	public JsonResult deleteSchedule( Integer id, Integer orgId) {
		try {
			//验证数据
			if(id==null||id<=0)
				return JsonResult.build(201, "id错误");
			if(orgId==null||orgId<0)
				return JsonResult.build(201, "orgId错误");
			
			Integer rows = cusScheduleService.deleteSchedule(id,orgId);
			if(rows !=null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于id删除课程信息=============错误=================");
		}
		return JsonResult.build(201, "该数据可能已经被删除");
	}
	
	/**基于id查询课程信息*/
	@RequestMapping("/getScheduleById")
	@ResponseBody
	public JsonResult getScheduleById( Integer id, Integer orgId) {
		try {
			//验证数据
			if(id==null||id<=0)
				return JsonResult.build(201, "id错误");
			if(orgId==null||orgId<=0)
				return JsonResult.build(201, "orgId错误");
			
			CusSchedule cusSchedule = cusScheduleService.getScheduleById(id, orgId);
			if(cusSchedule != null) {
				return JsonResult.oK(cusSchedule);
			}
		} catch (Exception e) {
			System.out.println("基于id查询课程信息=============错误=================");
		}
		return JsonResult.build(201, "查询无数据");
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
			//验证数据合法性
			if(StringUtils.isEmpty(cusSchedule.getName()))
				return JsonResult.build(201, "用户名不能为空");
			if(cusSchedule.getOrgId()==null||cusSchedule.getOrgId()<0)
				return JsonResult.build(201, "orgId不能为空");
			if(cusSchedule.getSymptomTypes()==null||cusSchedule.getSymptomTypes().length==0)
				return JsonResult.build(201, "课程表训练项目不能为空");
			
			Integer rows = cusScheduleService.addSchedule(cusSchedule);
			if(rows != 0 && rows != null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			
			//验证数据合法性
			if(StringUtils.isEmpty(cusSchedule.getName()))
				return JsonResult.build(201, "用户名不能为空");
			if(StringUtils.isEmpty(cusSchedule.getName()))
				return JsonResult.build(201, "用户名不能为空");
			if(cusSchedule.getOrgId()==null||cusSchedule.getOrgId()<0)
				return JsonResult.build(201, "orgId不能为空");
			if(cusSchedule.getSymptomTypes()==null||cusSchedule.getSymptomTypes().length==0)
				return JsonResult.build(201, "课程表训练项目不能为空");
			
			Integer rows = cusScheduleService.updateSchedule(cusSchedule);
			if(rows != 0 && rows != null) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("修改课程表数据=============错误=================");
		}
		return JsonResult.build(201, "修改数据错误,请稍后重试");
	}
	
	/**基于客户id查询用户课程表信息*/
	@RequestMapping("/getByCustomerId")
	@ResponseBody
	public JsonResult getByCustomerId(CusVo cusVo) {
		try {
			if(cusVo.getCustomerId()==null||cusVo.getCustomerId()<0)
				throw new ServiceException("customerId错误");
			if(cusVo.getOrgId()==null||cusVo.getOrgId()<0)
				throw new ServiceException("orgId错误");
			List<CusSchedule> list = cusScheduleService.getByCustomerId(cusVo);
			if(list.size()!=0 && list != null) {
				return JsonResult.oK(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("基于客户id查询用户课程表信息=============错误=================");
		}
		return JsonResult.build(201, "该用户无课程,需添加课程信息");
	}
	
}
