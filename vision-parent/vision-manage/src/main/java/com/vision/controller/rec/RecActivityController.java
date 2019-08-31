package com.vision.controller.rec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.rec.RecActivityPush;
import com.vision.service.rec.RecActivityService;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;

/**充值活动*/
@Controller
@RequestMapping("/rechargeActivaty")
public class RecActivityController {
	@Autowired
	private RecActivityService recActivityService;
	
	@RequestMapping("/deleteRecActivityById")
	@ResponseBody
	/**根据充值活动的id删除该充值活动*/
	public JsonResult deleteRecActivityById(Long[] id) {
		try {
			if(id==null) {
				return JsonResult.build(201,"数据错误");
			}
			recActivityService.deleteRecActivityById(id);
			return JsonResult.oK("删除充值活动成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"删除充值活动失败");
	}
	
	@RequestMapping("/insertRecActivity")
	@ResponseBody
	/**新增充值活动表对象*/
	public JsonResult insertRecActivity(RecActivityPush recActivityPush) {
		try {
			
			recActivityService.insertRecActivity(recActivityPush);
			return JsonResult.oK("新增充值活动成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"新增充值活动失败");
	}
	
	@RequestMapping("/updateRecActivityById")
	@ResponseBody
	/**修改充值活动*/
	public JsonResult updateRecActivityById(RecActivityPush recActivityPush) {
		try {
			if(recActivityPush==null)
				return JsonResult.build(201, "对象不能为空");
			if(recActivityPush.getId()==null||recActivityPush.getId()<0)
				return JsonResult.build(201, "id错误");
			if(recActivityPush.getOrgId()==null||recActivityPush.getOrgId()<0)
				return JsonResult.build(201, "orgId错误");
			if(StringUtils.isEmpty(recActivityPush.getTitle()))
				return JsonResult.build(201, "活动名称不能为空");
			
			Integer row = recActivityService.updateRecActivityById(recActivityPush);
			if(row != 0 && row != null) {
				return JsonResult.oK("修改充值活动成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"修改充值活动失败");
	}
	
	/**查询充值活动*/
	@RequestMapping("/findAllRecActivityObjects")
	@ResponseBody
	public JsonResult findAllRecActivityObjects(CusVo cusVo) {
		try {
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
			
	    	PageObject<RecActivityPush> recActivityPushs = recActivityService.findAllRecActivityObjects(cusVo);
			return JsonResult.oK(recActivityPushs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"查询充值活动数据失败");
	}
	
	@RequestMapping("/findRecActivityObject")
	@ResponseBody
	/**根据活动id查询充值活动*/
	public JsonResult findRecActivityObject(Long id) {
		try {
			RecActivityPush recActivityPush = recActivityService.findRecActivityObject(id);
			return JsonResult.oK(recActivityPush);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"查询失败");
	}
}
