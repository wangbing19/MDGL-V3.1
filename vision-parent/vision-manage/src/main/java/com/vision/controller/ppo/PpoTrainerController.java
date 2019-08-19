package com.vision.controller.ppo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.ppo.PpoAppointmentTime;
import com.vision.pojo.ppo.PpoTrainer;
import com.vision.pojo.ppo.vo.PpoTrainerOrganization;
import com.vision.service.ppo.PpoTrainerService;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;
@Controller
@RequestMapping("/PpoTrainer")
public class PpoTrainerController {
	@Autowired
	private PpoTrainerService ppoTrainerService;
	
	/**
	 * 查询所有训练师
	 * @param appointmentName
	 * @param pageCurrent
	 * @return
	 */
	@RequestMapping("/doFindPpoTrainer")
	@ResponseBody
	public JsonResult findPpoTrainerAll(  String trainerName,  Integer pageCurrent,Integer pageSize,Long organizationId) {
		try {
			organizationId = 0L;
			PageObject<PpoTrainer> pageObject = ppoTrainerService.findPpoTrainerAll(trainerName, pageCurrent,pageSize,organizationId);
			return JsonResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return JsonResult.build(201, "查询失败");
		
	}
	
	/**
	 * 查询所有训练师
	 * @param appointmentName
	 * @param pageCurrent
	 * @return
	 */
	@RequestMapping("/doFindPpoTrainerOne")
	@ResponseBody
	public JsonResult findPpoTrainerOne( PpoTrainer ppoTrainer ) {
		try {
		
			PpoTrainerOrganization one = ppoTrainerService.findPpoTrainerOne(ppoTrainer);
			return JsonResult.oK(one);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return JsonResult.build(201, "查询失败");
		
	}
	
	/**
     * 添加训练师
     * @param ppoAppointment
     * @return
     */
    @RequestMapping("/doInsertPpoTrainer")
    @ResponseBody
    public JsonResult doInsertPpoTrainer( PpoTrainer ppoTrainer ) {
    	
    	try {
    		
    	
    		
    		
    		int saveState = ppoTrainerService.savePpoTrainer(ppoTrainer);
    		
			return JsonResult.oK(saveState);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return JsonResult.build(201, "添加失败");
    }
    
    /**
	 * 修改训练师
	 * 
	 */
	@RequestMapping("/doUpdatePpoTrainer")
	@ResponseBody
	public JsonResult doUpdatePpoTrainer( PpoTrainer ppoTrainer) {
		try {
		
    		if(ppoTrainer.getOrganizationId() == null) {
    			return JsonResult.build(400, "修改失败，组织id不能为空！");
    		}
			int result = ppoTrainerService.doUpdatePpoTrainer(ppoTrainer);
			
			return JsonResult.oK(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(400, "修改失败");
	}
	
	/**
	 * 删除训练师
	 * @param ppoAppointment
	 * @return
	 */
	 @RequestMapping("/dodeletePpoTrainer")
	    @ResponseBody
	    public JsonResult dodeletePpoTrainer(Long ...id) {
	    	try {
	    		ArrayList<Long> list = new ArrayList<Long>();
	    		for (Long long1 : id) {
	    			list.add(long1);
				}
	    		Integer result=ppoTrainerService.dodeletePpoTrainer(list);
	    		return JsonResult.oK(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return JsonResult.build(201, "删除失败");
	    }
	 
	 /**
		 * 添加预约时间
		 * @param ppoAppTime
		 * @return
		 */
		@RequestMapping("/doinsertAppointmentTime")
		@ResponseBody
		public JsonResult doinsertAppointmentTime(PpoAppointmentTime ppoAppointmentTime) {
			try {
			
				Integer result = ppoTrainerService.doInsertAppointmentTime(ppoAppointmentTime);
				
					return JsonResult.oK(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return JsonResult.build(201, "保存失败");
		}

	//查询训练师时间
			@RequestMapping("/dofindAppointmentTime")
			@ResponseBody
			public JsonResult dofindAppointmentTime( Long tarinerId) {
				try {
					
					List<PpoAppointmentTime> result = ppoTrainerService.dofindappointmentTime(tarinerId);
					if(result.size() == 0) {
						return JsonResult.build(200, "无查询结果！");
					}
					return JsonResult.oK(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return JsonResult.build(201, "查询失败");
			}
			
			/**
			 * 删除训练师的训练时间
			 * @param id
			 * @return
			 */
			@RequestMapping("/dodeleteAppointmentTime")
			@ResponseBody
			public JsonResult dodeleteAppointmentTime( Long id) {
				try {
					
					int result = ppoTrainerService.dodeleteAppointmentTime(id);
					
					return JsonResult.oK(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return JsonResult.build(201, "查询失败");
			} 
			
			/**
			 * 修改训练师的训练时间
			 * @param ppoAppointmentTime
			 * @return
			 */
			@RequestMapping("/doupdateAppointmentTime")
			@ResponseBody
			public JsonResult doupdateAppointmentTime( PpoAppointmentTime ppoAppointmentTime) {
				try {
					
					int result = ppoTrainerService.updateAppointmentTime(ppoAppointmentTime);
					
					return JsonResult.oK(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return JsonResult.build(201, "查询失败");
			} 
}
