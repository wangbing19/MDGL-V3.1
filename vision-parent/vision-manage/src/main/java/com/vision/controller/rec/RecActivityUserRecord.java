package com.vision.controller.rec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**充值记录表*/
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.rec.RecPayUser;
import com.vision.service.rec.RecActivityRecordService;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;
@Controller
@RequestMapping("/activityUser")

public class RecActivityUserRecord {

	@Autowired
	private RecActivityRecordService recActivityRecordService;

	@RequestMapping("/deleteActivityRecordByid")
	@ResponseBody
	/**根据活动记录的id删除客户充值记录*/
	public JsonResult deleteActivityRecordByid(Long id) {
		try {
			if(id==null) {
				return JsonResult.build(201,"未选择要删除的充值记录");
			}	
			int deleteResult =  recActivityRecordService.deleteActivityRecordByid(id);
			if(deleteResult==1)return JsonResult.oK("客户充值记录删除成功");
			if(deleteResult==0)return JsonResult.build(201,"没有权限删除,该客户记录不是本店充值的记录");
			if(deleteResult==2)return JsonResult.build(201,"要删除的充值活动记录不存在");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"客户充值记录删除失败");
	}

	/**分页查询所有的充值记录*/
	@RequestMapping("/findAllActivityRecords")
	@ResponseBody
	public JsonResult findAllActivityRecords(Integer pageCurrent,Integer pageSize) {
		try {
			PageObject<RecPayUser> pageObject = recActivityRecordService.findAllActivityRecords(pageCurrent,pageSize);
			return JsonResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"查询充值记录失败");
	}
	
	/**客户充值时新增新的充值记录*/
	@RequestMapping("/insertActivityRecord")
	@ResponseBody
	public JsonResult insertActivityRecord(CusCustomer cusCustomer,RecPayUser recPayUser) {
		try {
			Integer result = recActivityRecordService.insertActivityRecord(cusCustomer,recPayUser);
			if(result==1) {
				return JsonResult.oK("充值成功");
			}else {
				return JsonResult.build(201,"充值失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"充值失败");
	}
}



