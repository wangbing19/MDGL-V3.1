package com.vision.controller.pre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.pre.SymptomDesc;
import com.vision.service.pre.SymptomDescService;
import com.vision.vo.JsonResult;

@Controller
@RequestMapping("/sympyomDesc")
public class SymptomDescController {
	@Autowired
	private SymptomDescService symptomDescService;
	@RequestMapping("/findSymptomDescObjectByid")
	@ResponseBody
	/**根据症状id查询症状描述信息*/
	public JsonResult findSymptomDescObjectByid(Long id) {
		try {
			SymptomDesc symptomDesc = symptomDescService.findSymptomDescObjectByid(id);
			return JsonResult.oK(symptomDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"查询症状描述信息失败");
	}
}
