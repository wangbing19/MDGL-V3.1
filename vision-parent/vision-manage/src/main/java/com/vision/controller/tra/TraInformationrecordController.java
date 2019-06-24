package com.vision.controller.tra;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.cus.CusSchedule;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.tra.TraInformationrecord;
import com.vision.service.cus.CusCustomerService;
import com.vision.service.tra.TraInformationrecordService;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;

@Controller
@RequestMapping("/traInformationrecord")
public class TraInformationrecordController {
	
	@Autowired
	private TraInformationrecordService traInformationrecordService;
	@Autowired
	private CusCustomerService cusCustomerService;
	
	/**训练记录分页及姓名查询*/
	@RequestMapping("/getTraInfor")
	@ResponseBody
	public JsonResult getTraInfor( CusVo cusVo){
		try {
			PageObject<TraInformationrecord> postForObject = traInformationrecordService.getTraInfor(cusVo);
			if(postForObject.getRecords().size()!=0) {
				return JsonResult.oK(postForObject);
			}
		} catch (Exception e) {
			System.out.println("训练记录分页及姓名查询==============错误=======================");
		}
		return  JsonResult.build(201, "查询无数据");
	}
	
	
	/**添加训练记录到数据库*/
	@RequestMapping("addTraInfor")
	@ResponseBody
	public JsonResult addTraInfor( TraInformationrecord entity) {
//		Users user=ShiroUtils.getUser();
//		entity.setUserId(user.getId());
//		entity.setOrgId(0);
		try {
			
			Integer rows = traInformationrecordService.addTraInfor(entity);
			if(rows != null && rows != 0) {
				cusCustomerService.updateObjectByTimesOfTraining(entity);
				return JsonResult.oK("保存成功");
			}
		} catch (Exception e) {
			System.out.println("添加训练记录到数据库==============错误=======================");
		}
		return JsonResult.build(201, "添加数据异常,请稍后重试");
	}
	
	
	/**删除*/
	@RequestMapping("deleteTraInfor")
	@ResponseBody
	public JsonResult deleteTraInfor( Integer id, Integer orgId) {
		try {
			Integer rows = traInformationrecordService.deleteTraInfor(id, orgId);
			if(rows != null && rows != 0) {
				return JsonResult.oK();
			}
		} catch (Exception e) {
			System.out.println("从数据删除训练记录表信息==============错误=======================");
		}
		return JsonResult.build(201, "数据可能已不存在");
	}
	
	
	/**通过id查询*/
	@RequestMapping("getTraInforById")
	@ResponseBody
	public JsonResult getTraInforById( Integer id, Integer orgId) {
		try {
			TraInformationrecord entity = traInformationrecordService.getTraInforById(id, orgId);
			if(entity != null) {
				return JsonResult.oK(entity);
			}
		} catch (Exception e) {
			System.out.println("通过id查询训练表信息==============错误=======================");
		}
		return JsonResult.build(201, "该条数据已不存在");
	}
	
	
	/**通过id修改训练表信息*/
	@RequestMapping("updateTraInfor")
	@ResponseBody
	public JsonResult updateTraInfor( TraInformationrecord entity) {
//		Users user = ShiroUtils.getUser();
		entity.setCreatedUser("admin");
		entity.setModifiedUser("admin");
		try {
			Integer rows = traInformationrecordService.updateTraInfor(entity);
			if(rows != null && rows != 0) {
				return JsonResult.oK("保存成功");
			}
		} catch (Exception e) {
			System.out.println("通过id修改训练表信息==============错误=======================");
		}
		return JsonResult.build(201, "修改保存数据错误,请稍后重试");
	}
	
	/**基于客户id查询用户课程表信息*/
	@RequestMapping("/getByCustomerId")
	@ResponseBody
	public JsonResult getByCustomerId( CusVo cusVo) {
		try {
			List<TraInformationrecord> list = traInformationrecordService.getByCustomerId(cusVo);
			if(list.size()!=0 && list != null) {
				return JsonResult.oK(list);
			}
		} catch (Exception e) {
			System.out.println("基于客户id查询用户课程表信息=============错误=================");
		}
		return JsonResult.build(201, "该用户无课程,需添加课程信息");
	}
}
