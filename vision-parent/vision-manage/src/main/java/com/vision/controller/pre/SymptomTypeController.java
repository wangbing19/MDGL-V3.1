package com.vision.controller.pre;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.pre.SymptomType;
import com.vision.pojo.sys.SysMenu;
import com.vision.service.pre.SymptomTypeService;
import com.vision.util.GetMenusTreeData;
import com.vision.util.GetTreeData;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;
import com.vision.vo.TreeMenus;
import com.vision.vo.TreeStructure;
import com.vision.vo.pre.SymptomAllMsg;

@Controller
@RequestMapping("/symptomType")
public class SymptomTypeController {
	@Autowired
	private SymptomTypeService symptomTypeService;
	@RequestMapping("/findAllObjects")
	@ResponseBody
	/**内嵌套查询症状类型数据**/
	public JsonResult findAllObjects() {
		try {
			List<SymptomAllMsg> symptomlist = symptomTypeService.findAllObjects();
			if(symptomlist==null||symptomlist.size()==0) {
				return JsonResult.oK("没有症状类型数据信息");
			}
			
//			List<TreeStructure<SymptomAllMsg>> treeStructures = new ArrayList<>();
//			for(int i=0;i<symptomlist.size();i++) {
//				TreeStructure<SymptomAllMsg> treeStructure = new TreeStructure<>();
//				treeStructure.setId(symptomlist.get(i).getId());
//				treeStructure.setParentId(symptomlist.get(i).getParentId());
//				treeStructure.setData(symptomlist.get(i));
//				treeStructures.add(treeStructure);			
//			}
//			GetTreeData<SymptomAllMsg>	tree = new GetTreeData<>();
//			TreeStructure<SymptomAllMsg>  treeData = tree.getTree(treeStructures);
			
			List<TreeMenus<SymptomAllMsg>> result1 = new ArrayList<>();
			for(int i = 0; i < symptomlist.size() ; i++) {
				TreeMenus<SymptomAllMsg> treeStructure = new TreeMenus<>();
				treeStructure.setKey((Long)symptomlist.get(i).getId());
				treeStructure.setTitle(symptomlist.get(i).getSymptomName());
				treeStructure.setValue((Long)symptomlist.get(i).getId());
				treeStructure.setpId((Long)symptomlist.get(i).getParentId());
				 result1.add(treeStructure);
			}
			
			GetMenusTreeData<SymptomAllMsg> tree1 = new GetMenusTreeData<SymptomAllMsg>();
			TreeMenus<SymptomAllMsg> tree2 = tree1.getTree(result1);
			
			return JsonResult.oK(tree2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"查询症状类型数据信息出错，检查网络连接");
	}
	
	/**根据症状id删除症状*/
	@RequestMapping("/deleteSymptomObjectById")
	@ResponseBody
	public JsonResult deleteSymptomObjectById(Long ...id) {
		try {
			int s = symptomTypeService.deleteSymptomObjectById(id);
			if(s>0) JsonResult.oK("删除成功");
			else {
				return JsonResult.build(201,"请先删除子症状");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"删除症状对象失败");
	}
	
	/**新增症状类型对象*/
	@RequestMapping("/insertSymptomObject")
	@ResponseBody
	public JsonResult insertSymptomObject(SymptomType symptomType,String desc) {
		try {
			symptomTypeService.insertSymptomObject(symptomType,desc);
			return JsonResult.oK("新增成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"新增失败");
	}
	
	/**修改症状类型对象*/
	@RequestMapping("/updateSymptomObject")
	@ResponseBody
	public JsonResult updateSymptomObject(SymptomType symptomType,String desc) {
		try {
			symptomTypeService.updateSymptomObject(symptomType,desc);
			return JsonResult.oK("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"修改失败");
	}
	
	/**根据症状id查询症状类型信息*/
	@RequestMapping("/findSymptomObjectById")
	@ResponseBody
	public JsonResult findSymptomObjectById(Long id) {
		try {
			SymptomType symptomType = symptomTypeService.findSymptomObjectById(id);
			return JsonResult.oK(symptomType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"查询症状信息失败");
	}
	
	/**分页查询*/
	@RequestMapping("/findAllObjectsList")
	@ResponseBody
	public JsonResult findAllObjectsList(Integer pageCurrent,Integer pageSize) {
		try {
			PageObject<SymptomType> symptomlist  = symptomTypeService.findAllObjectsList(pageCurrent,pageSize);
			if(symptomlist.getRecords()==null||symptomlist.getRecords().size()==0) {
				return JsonResult.oK("没有症状类型数据信息");
			}
			
			else {
				return JsonResult.oK(symptomlist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201,"查询症状类型数据信息出错，检查网络连接");
	}
}
