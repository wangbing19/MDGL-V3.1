package com.vision.controller.ppo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.ppo.PpoServiceItems;
import com.vision.service.ppo.PpoServiceItemsService;
import com.vision.vo.JsonResult;

@Controller
@RequestMapping("/PpoServiceItems")
public class PpoServiceItemsController {
			@Autowired
	private PpoServiceItemsService ppoServiceItemsService;
	
	/**
	 * 添加服务项目
	 * @param ppoServiceItems
	 * @return
	 */
	@RequestMapping("/doSaveServiceItems")
	@ResponseBody		
	public JsonResult saveServiceItems(PpoServiceItems ppoServiceItems) {
		try {
			int result = ppoServiceItemsService.saveServiceItems(ppoServiceItems);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201, "保存失败！");
	}
	
	@RequestMapping("/doFindServiceItems")
	@ResponseBody	
	public JsonResult findServiceItems(Long organizationId) {
		try {
			List<PpoServiceItems> result = ppoServiceItemsService.findServiceItems(organizationId);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201, "查询失败！");
	}
}
