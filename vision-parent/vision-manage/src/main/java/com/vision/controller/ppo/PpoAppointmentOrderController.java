package com.vision.controller.ppo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.ppo.PpoAppointmentOrder;
import com.vision.pojo.ppo.PpoAppointmentTime;
import com.vision.pojo.ppo.PpoTrainer;
import com.vision.service.ppo.PpoAppointmentOrderService;
import com.vision.service.ppo.PpoTrainerService;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;
@Controller
@RequestMapping("/PpoOder")
public class PpoAppointmentOrderController {
	@Autowired
	private PpoAppointmentOrderService ppoAppointmentOrderService;
	
	/**
	 * 预约所有订单查询
	 * @param trainerName
	 * @param pageCurrent
	 * @param pageSize
	 * @param organizationId
	 * @return
	 */
	@RequestMapping("/doFindPpoOder")
	@ResponseBody
	public JsonResult findPpoOderAll(  PpoAppointmentOrder ppoAppointmentOrder,Integer pageCurrent,Integer pageSize ) {
		try {
		
			PageObject<PpoAppointmentOrder> pageObject = ppoAppointmentOrderService.findPpoOderAll(ppoAppointmentOrder,pageCurrent,pageSize);
			return JsonResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return JsonResult.build(201, "查询失败");
	}
	
	/**
	 * 添加预约订单
	 * @param ppoAppointmentOrder
	 * @return
	 */
	@RequestMapping("/doSavePpoOder")
	@ResponseBody
	public JsonResult savePpoOrder(PpoAppointmentOrder ppoAppointmentOrder) {
		try {
			int result = ppoAppointmentOrderService.savePpoOrder(ppoAppointmentOrder);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
			return JsonResult.build(201, "保存失败！");
	}
	
	@RequestMapping("/doDeletePpoOder")
	@ResponseBody
	public JsonResult deletePpoOrder(Long orderId) {
		try {
			int result = ppoAppointmentOrderService.deletePpoOrder(orderId);
			return JsonResult.oK(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"删除失败！");
	}
	
	/**
	 * 修改预约订单
	 * @param ppoAppointmentOrder
	 * @return
	 */
	@RequestMapping("/doupdatePpoOrder")
	@ResponseBody
	public JsonResult updatePpoOrder(PpoAppointmentOrder ppoAppointmentOrder) {
		try {
			int result = ppoAppointmentOrderService.updatePpoOrder(ppoAppointmentOrder);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201, "修改失败！");
	}
}
