package com.vision.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.sys.SysLogs;
import com.vision.pojo.sys.SysRole;
import com.vision.pojo.sys.vo.SysRoleOrganizationResult;
import com.vision.service.sys.SysRoleService;
import com.vision.vo.CheckBox;
import com.vision.vo.JsonResult;

@Controller
@RequestMapping("/sysRole/")
public class SysRoleController {
	@Autowired
	private SysRoleService sysRoleService;
	
	@RequestMapping("doInsertRole")
	@ResponseBody
	public JsonResult insertRole(SysRole sysRole) {
		try {
			Integer[] menuIds = sysRole.getMenuIds();
			int result = sysRoleService.doInsertRole(sysRole,menuIds);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "角色添加失败!");
		
	}
	

	@RequestMapping("doDeleteRole")
	@ResponseBody
	public JsonResult deleteRole(Integer id) {
		try {
			int result = sysRoleService.deleteRole(id);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "角色删除失败!");
		
	}
	
	@RequestMapping("doUpdateRole")
	@ResponseBody
	public JsonResult updateRole(SysRole sysRole,Integer[] menuIds) {
		try {
			int result = sysRoleService.updateRole(sysRole,menuIds);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "角色修改失败!");
		
	}
	/**
	 * 获取所有角色的id和name属性值
	 * @return
	 */
	 @RequestMapping("doFindRoles")
	 @ResponseBody
	 public JsonResult doFindObjects(){
	  	try {
	  		List<CheckBox> result = sysRoleService.findObjects();
	  		return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	  	return JsonResult.build(201, "查询失败！");
	 }
	 
	 /**
		 * 基于角色id获取角色以及角色对应的菜单信息
		 * @param id
		 * @return
		 */
	 @RequestMapping("doFindObjectById")
	 @ResponseBody
	 public JsonResult doFindObjectById(Integer id){
	    try {
	    	SysRoleOrganizationResult result = sysRoleService.findObjectById(id);
	    	return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return JsonResult.build(201, "查询失败！");
	 } 
	 
	 /**
		 * 基于角色id查询自身信息
		 * @param id
		 * @return
		 */
	 @RequestMapping("doFindObjectByIds")
	 @ResponseBody
	 public JsonResult doFindObjectByIds(Integer... ids){
	    try {
	    	List<SysRole> result = sysRoleService.findObjectByIds(ids);
	    	return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return JsonResult.build(201, "查询失败！");
	 } 
	 
	 /**
	  * 查询所有角色消息信息
	  * @return
	  */
	 @RequestMapping("doFindRoleAll")
	 @ResponseBody
	 public JsonResult doFindRoleAll(){
	    try {
	    	List<SysRole> result = sysRoleService.doFindRoleAll();
	    	return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return JsonResult.build(201, "查询失败！");
	 } 
	 
}
