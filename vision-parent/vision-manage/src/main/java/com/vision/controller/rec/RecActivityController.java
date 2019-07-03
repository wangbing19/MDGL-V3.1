package com.vision.controller.rec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.rec.RecActivityPush;
import com.vision.service.rec.RecActivityService;
import com.vision.vo.JsonResult;

/**充值活动*/
@Controller
@RequestMapping("/rechargeActivaty")
public class RecActivityController {
	@Autowired
	private RecActivityService recActivityService;
	
	@RequestMapping("/deleteRecActivityById")
	@ResponseBody
	/**根据充值活动的id删除该充值活动*/
	public JsonResult deleteRecActivityById(Long id) {
		try {
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
			recActivityService.updateRecActivityById(recActivityPush);
			return JsonResult.oK("修改充值活动成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"修改充值活动失败");
	}
	
	/**查询充值活动*/
	@RequestMapping("/findAllRecActivityObjects")
	@ResponseBody
	public JsonResult findAllRecActivityObjects() {
		try {
			List<RecActivityPush> recActivityPushs = recActivityService.findAllRecActivityObjects();
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
