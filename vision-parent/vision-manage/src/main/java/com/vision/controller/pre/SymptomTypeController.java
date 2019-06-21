package com.vision.controller.pre;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.service.pre.SymptomTypeService;
import com.vision.vo.JsonResult;
import com.vision.vo.pre.SymptomAllMsg;

@Controller
@RequestMapping("/symptomType")
public class SymptomTypeController {
	@Autowired
	private SymptomTypeService symptomTypeService;
	@RequestMapping("/findAllObjects")
	@ResponseBody
	public JsonResult findAllObjects() {
		try {
			List<SymptomAllMsg> symptomlist = symptomTypeService.findAllObjects();
			if(symptomlist==null||symptomlist.size()==0) {
				return JsonResult.oK("没有症状类型数据信息");
			}
			return JsonResult.oK(symptomlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"查询症状类型数据信息出错，检查网络连接");
	}
}
