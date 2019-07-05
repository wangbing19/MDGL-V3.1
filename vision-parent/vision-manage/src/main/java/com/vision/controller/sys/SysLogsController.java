package com.vision.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.sys.SysLogs;
import com.vision.pojo.sys.vo.SysOrganizationLogs;
import com.vision.service.sys.SysLogsService;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;

@Controller
@RequestMapping("/sysLogs")
public class SysLogsController {
	@Autowired
	private SysLogsService sysLogsService;
	
	/**
	 * 日志添加日志
	 * @param sysLogs
	 * @return
	 */
	@RequestMapping("doInsertLogs")
	@ResponseBody
	public JsonResult insertLogs(SysLogs sysLogs) {
		try {
			int result = sysLogsService.insertLogs(sysLogs);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "日志添加失败!");
		
	}
	
	@RequestMapping("dofindLogs")
	@ResponseBody
	public JsonResult findLogs(Long organizationId,Integer pageCurrent,Integer pageSize) {
		try {
			PageObject<SysOrganizationLogs> result = sysLogsService.findLogs(organizationId,pageCurrent,pageSize);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "日志查询失败!");
		
	}
}
