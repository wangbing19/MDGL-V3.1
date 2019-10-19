package com.vision.controller.rec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**充值记录表*/
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.rec.RecPayUser;
import com.vision.service.rec.RecActivityRecordService;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;
@Controller
@RequestMapping("/activityUser")

public class RecActivityUserRecord {

	@Autowired
	private RecActivityRecordService recActivityRecordService;

	/**分页查询所有的充值记录*/
	@RequestMapping("/findAllActivityRecords")
	@ResponseBody
	public JsonResult findAllActivityRecords(CusVo cusVo) {
		try {
			Integer pageCurrent = cusVo.getPageCurrent();
			Integer pageSize = cusVo.getPageSize();
			Integer orgId = cusVo.getOrgId();
			if(pageCurrent==null||pageCurrent<=0)
				return JsonResult.build(201, "页码值不正确");
			if(orgId==null||orgId<0)
				return JsonResult.build(201, "门店信息不正确");
			if(pageSize==null||pageSize<0)
				return JsonResult.build(201, "页码大小不正确");
			
			PageObject<RecPayUser> pageObject = recActivityRecordService.findAllActivityRecords(cusVo);
			return JsonResult.oK(pageObject);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("查询充值记录失败");
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



