package com.vision.controller.sys;


import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vision.pojo.sys.SysUser;
import com.vision.pojo.sys.vo.SysUserOrganization;
import com.vision.service.sys.SysRoleService;
import com.vision.service.sys.SysUserService;
import com.vision.vo.AntCheckbox;
import com.vision.vo.CheckBox;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;


@Controller
@RequestMapping("/sysUser/")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;

	//基于CAS算法，实现的并发安全，底层乐观锁实现。
	private AtomicInteger counter=new AtomicInteger(0);
	/**
	 * 用户添加
	 * @param entity
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject( SysUser entity,Integer[] roleIds){
		try {
			int result =  sysUserService.saveObject(entity, roleIds);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}


		return JsonResult.build(201, "保存失败！");
	}

	/**
	 * 用户添加
	 * @param entity
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject( SysUser entity,Integer[] roleIds){
		try {
			int result =  sysUserService.updateObject(entity, roleIds);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}


		return JsonResult.build(201, "修改失败！");
	}


	/**
	 * 查询用户与组织
	 * @param organizationId
	 * @param username
	 * @param pageCurrent
	 * @return
	 */
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(Long organizationId,String username,Integer pageCurrent,Integer pageSize){
		organizationId=0L;
		PageObject<SysUserOrganization> pageObject=sysUserService.findPageObjects(organizationId,username,pageCurrent,pageSize);
		return new JsonResult(pageObject);
	}


	/**
	 * 
	 * @param columnName
	 * @param columnValue
	 * @return
	 */
	@RequestMapping("doFindObjectByColumn")
	@ResponseBody
	public JsonResult doFindObjectByColumn(String columnName,String columnValue){
		try {
			int result = sysUserService.findObjectByColumn(columnName, columnValue);
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonResult.build(201, "查询失败！");
	}  

	/**
	 * 用户的禁用与启用
	 * @param id
	 * @param valid
	 * @return
	 */
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(Integer id,Integer valid){
		try {
			
			int result = sysUserService.validById(id,valid, "admin");//"admin"用户将来是登陆用户
			return JsonResult.oK("设置成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return JsonResult.build(201, "设置失败！");      
	}
	/**
	 * 查询该用户的角色
	 * @return
	 */
	 @RequestMapping("doFindObjects")
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

	 @RequestMapping("doFindObjectById")
		@ResponseBody
		public JsonResult doFindObjectById(Integer userId){
		 try {
			 Map<String,Object> map=sysUserService.findObjectById(userId);
			 return JsonResult.oK(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
			return JsonResult.build(201, "查询失败！");
		}
	 
	 
	 /**
	  * 用于前端修改时的查询
	  * @param userId
	  * @return
	  */
	 @RequestMapping("doFindUserByIdWeb")
	 @ResponseBody
		public JsonResult findUserByIdWeb(SysUser SysUser){
		 try {
			 SysUser result=sysUserService.findUserByIdWeb(SysUser.getId());
			 return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
			return JsonResult.build(201, "查询失败！");
		}
	 
	 /**
	  * 用于权限用户认证时，根据id查询该用户信息
	  * @param userId
	  * @return
	  */
	 @RequestMapping("doFindUserById")
	 @ResponseBody
		public JsonResult findUserById(Integer userId){
		 try {
			 SysUser result=sysUserService.findUserById(userId);
			 return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
			return JsonResult.build(201, "查询失败！");
		}
	 
	 /**
	  * 用于权限用户认证时，根据name查询该用户信息
	  * @param userId
	  * @return
	  */
	 @RequestMapping("doFindUserByName")
	 @ResponseBody
		public JsonResult findUserByName(String userName){
		 try {
			 SysUser result=sysUserService.findUserByName(userName);
			 return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
			return JsonResult.build(201, "查询失败！");
		}
	 /**
	  * 用于用户查询所有角色
	  * @param userId
	  * @return
	  */
	 @RequestMapping("doFindRoleCheckboxAll")
	 @ResponseBody
		public JsonResult findRoleCheckboxAll(){
		 try {
			 List<AntCheckbox> result=sysUserService.findRoleCheckboxAll();
			 return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
			return JsonResult.build(201, "查询失败！");
		}
	 
	 /**
	  * 根据用户id查询用户所有的角色
	  * @param userId
	  * @return
	  */
	 @RequestMapping("doFindRoleCheckbox")
	 @ResponseBody
		public JsonResult findRoleCheckbox(Long id){
		 try {
			 String[] result=sysUserService.findRoleCheckbox( id);
			 return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
			return JsonResult.build(201, "查询失败！");
		}
	 
}
