package com.vision.controller.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.sys.SysMenu;
import com.vision.pojo.sys.SysOrganization;
import com.vision.service.sys.SysOrganizationService;
import com.vision.util.GetTreeData;
import com.vision.vo.JsonResult;
import com.vision.vo.Node;
import com.vision.vo.Node2;
import com.vision.vo.TreeStructure;

@Controller
@RequestMapping("SysOrganization")
public class SysOrganizationController {
	@Autowired
	private SysOrganizationService sysOrganizationService;
	
	@RequestMapping("doUpdateOrganization")
	@ResponseBody
	public JsonResult doUpdateOrganization(SysOrganization sysOrganization){
		try {
			int result = sysOrganizationService.updateOrganization(sysOrganization);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return JsonResult.build(201, "修改失败！");
	}
	
	@RequestMapping("doDeleteOrganization")
	@ResponseBody
	public JsonResult deleteOrganization(Long organizationId){
		try {
			int result = sysOrganizationService.deleteOrganization(organizationId);
			if(result == -1) {
				 return JsonResult.build(201, "该元素下有子元素，无法删除！");
			}
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return JsonResult.build(201, "修改失败！");
	}
	
	@RequestMapping("doSaveOrganization")
	@ResponseBody
	public JsonResult saveOrganization(SysOrganization sysOrganization){
		try {
			int result = sysOrganizationService.saveOrganization(sysOrganization);
			
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return JsonResult.build(201, "保存失败！");
	}
	
	@RequestMapping("doFindOrganization")
	@ResponseBody
	public JsonResult findOrganization(){
		try {
			List<SysOrganization> list = sysOrganizationService.findOrganization();
			
			List<TreeStructure<SysOrganization>> result = new ArrayList<>();
			
			for(int i = 0; i < list.size(); i++) {
				TreeStructure<SysOrganization> treeStructure = new TreeStructure<>();
				treeStructure.setId((Long)list.get(i).getOrganizationId());
				
				treeStructure.setParentId((Long)list.get(i).getOrganizationParentId());
				treeStructure.setData(list.get(i));
				result.add(treeStructure);
			}
			
			GetTreeData<SysOrganization> tree = new GetTreeData<>();
			TreeStructure<SysOrganization> treeData = tree.getTree(result);
			
			return JsonResult.oK(treeData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return JsonResult.build(201, "查询失败！");
	}
	
	@RequestMapping("doFindZTreeNodes")
	@ResponseBody
	public JsonResult doFindZTreeNodes(){
		try {
			List<Node2> result = sysOrganizationService.findZTreeNodes();
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return JsonResult.build(201, "组织失败！");
	}
}
