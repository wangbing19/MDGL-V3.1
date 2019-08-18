package com.vision.controller.tra;

import java.util.List;
import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.exception.ServiceException;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.sys.SysUser;
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
			//1.数据合法性验证
			if(cusVo.getPageCurrent()==null||cusVo.getPageCurrent()<=0)
				return  JsonResult.build(201, "页码值不正确");
			if(cusVo.getOrgId()==null||cusVo.getOrgId()<0)
				return  JsonResult.build(201, "orgId不正确");
			if(cusVo.getPageSize()==null||cusVo.getPageSize()<0)
				return  JsonResult.build(201, "pageSize不正确");
			
			PageObject<TraInformationrecord> postForObject = traInformationrecordService.getTraInfor(cusVo);
			return JsonResult.oK(postForObject);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("训练记录分页及姓名查询==============错误=======================");
		}
		return  JsonResult.build(201, "查询无数据");
	}
	
	
	/**添加训练记录到数据库*/
	@RequestMapping("addTraInfor")
	@ResponseBody
	public JsonResult addTraInfor( TraInformationrecord entity) {
		try {
			if(StringUtils.isEmpty(entity.getName())) {
				return JsonResult.build(201, "用户名错误");
			}
			if(entity.getOrgId()==null || entity.getOrgId()<0) {
				return JsonResult.build(201, "门店信息错误");
			}
			if(entity.getCustomerId()==null || entity.getCustomerId()<0) {
				return JsonResult.build(201, "客户信息错误");
			}
			if(entity.getScheduleId()==null || entity.getScheduleId()<0) {
				return JsonResult.build(201, "课程表信息错误");
			}
			
//			SysUser user = null;
//			entity.setModifiedUser(user.getUserName());
//			entity.setCreatedUser(user.getUserName());
			
			Integer rows = traInformationrecordService.addTraInfor(entity);
			if(rows != null && rows != 0) {
				cusCustomerService.updateObjectByTimesOfTraining(entity);
				return JsonResult.oK("保存成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("添加训练记录到数据库==============错误=======================");
		}
		return JsonResult.build(201, "添加数据异常,请稍后重试");
	}
	
	
	/**删除*/
	@RequestMapping("deleteTraInfor")
	@ResponseBody
	public JsonResult deleteTraInfor( Integer id, Integer orgId) {
		
		try {
			if(id==null||id<=0)
				return JsonResult.build(201, "请选择一条数据");
			if(orgId==null||orgId<=0)
				return JsonResult.build(201, "orgId错误");
			
//			SysUser user = null;
//			if(user.getOrganizationId()!=orgId.longValue()) {
//				return JsonResult.build(201, "该账号无法删除该诊断表，请联系相关门店更改");
//			}
			
			Integer rows = traInformationrecordService.deleteTraInfor(id, orgId);
			if(rows != null) {
				return JsonResult.oK("删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("从数据删除训练记录表信息==============错误=======================");
		}
		return JsonResult.build(201, "数据可能已不存在");
	}
	
	
	/**通过id查询*/
	@RequestMapping("getTraInforById")
	@ResponseBody
	public JsonResult getTraInforById( Integer id, Integer orgId) {
		try {
			if(id==null||id<=0)
				return JsonResult.build(201, "请选择一条数据");
			if(orgId==null||orgId<=0)
				return JsonResult.build(201, "orgId错误");
			
			TraInformationrecord entity = traInformationrecordService.getTraInforById(id, orgId);
			if(entity != null) {
				return JsonResult.oK(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("通过id查询训练表信息==============错误=======================");
		}
		return JsonResult.build(201, "该条数据已不存在");
	}
	
	
	/**通过id修改训练表信息*/
	@RequestMapping("updateTraInfor")
	@ResponseBody
	public JsonResult updateTraInfor( TraInformationrecord entity) {
		try {
			if(entity.getId()==0||entity.getId()==null)
				return JsonResult.build(201, "对象id不能为空");
			if(StringUtils.isEmpty(entity.getName())) {
				return JsonResult.build(201, "用户名错误");
			}
			if(entity.getOrgId()==null || entity.getOrgId()<0) {
				return JsonResult.build(201, "门店信息错误");
			}
			if(entity.getCustomerId()==null || entity.getCustomerId()<0) {
				return JsonResult.build(201, "客户信息错误");
			}
			if(entity.getScheduleId()==null || entity.getScheduleId()<0) {
				return JsonResult.build(201, "课程表信息错误");
			}
			
//			SysUser user = null;
//			if(user.getOrganizationId()!=entity.getOrgId().longValue()) {
//				return JsonResult.build(201, "该账号无法修改该诊断表，请联系相关门店更改");
//			}
//			entity.setModifiedUser(user.getUserName());
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
			if(cusVo.getCustomerId()==null||cusVo.getCustomerId()<0) {
				return JsonResult.build(201, "customerId错误");
			}
			if(cusVo.getOrgId()==null||cusVo.getOrgId()<0) {
				return JsonResult.build(201, "orgId错误");
			}
			List<TraInformationrecord> list = traInformationrecordService.getByCustomerId(cusVo);
			return JsonResult.oK(list);
		} catch (Exception e) {
			System.out.println("基于客户id查询用户课程表信息=============错误=================");
		}
		return JsonResult.build(201, "该用户无课程,需添加课程信息");
	}
}
