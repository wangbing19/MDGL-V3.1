package com.vision.controller.rec;

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
}
