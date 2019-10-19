package com.vision.controller.ppo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.ppo.PpoServiceItems;
import com.vision.service.ppo.PpoServiceItemsService;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;

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
	
	/**
	 * 根据组织id查询该id下的所有数据
	 * @param organizationId
	 * @return
	 */
	@RequestMapping("/doFindServiceItems")
	@ResponseBody	
	public JsonResult findServiceItems(Long organizationId,Integer pageCurrent,Integer pageSize) {
		try {
			PageObject<PpoServiceItems> result = ppoServiceItemsService.findServiceItems(organizationId,pageCurrent,pageSize);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201, "查询失败！");
	}
	/**
	 * 删除
	 * @param rederId
	 * @return
	 */
	@RequestMapping("/dodeleteServiceItems")
	@ResponseBody
	public JsonResult deleteServiceItems(Long rederId) {
		try {
			int  result = ppoServiceItemsService.deleteServiceItems(rederId);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201, "查询失败！");
	}
	
	/**
	 * 修改
	 * @param ppoServiceItems
	 * @return
	 */
	@RequestMapping("/doupdeteServiceItems")
	@ResponseBody
	public JsonResult updeteServiceItems(PpoServiceItems ppoServiceItems) {
		try {
			int  result = ppoServiceItemsService.updeteServiceItems(ppoServiceItems);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201, "查询失败！");
	}
	
	/**
	 * 根据id查询
	 * @param ppoServiceItems
	 * @return
	 */
	@RequestMapping("/doFindServiceItemOne")
	@ResponseBody
	public JsonResult findServiceItemOne(Long organizationId,Long id) {
		try {
			PpoServiceItems ppoServiceItems =new PpoServiceItems();
			ppoServiceItems.setOrganizationId(0L);
			ppoServiceItems.setId(id);
			PpoServiceItems  result = ppoServiceItemsService.findServiceItemOne(ppoServiceItems);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201, "查询失败！");
	}
	
}
