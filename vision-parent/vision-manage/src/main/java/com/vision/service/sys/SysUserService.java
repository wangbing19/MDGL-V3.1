package com.vision.service.sys;

import java.util.Map;

import com.vision.pojo.sys.SysUser;
import com.vision.pojo.sys.vo.SysUserOrganization;
import com.vision.vo.PageObject;

public interface SysUserService {

	int saveObject(SysUser entity, Integer[] roleIds);

	int updateObject(SysUser entity, Integer[] roleIds);

	int findObjectByColumn(String columnName, String columnValue);

	PageObject<SysUserOrganization> findPageObjects(Long organizationId,String username, Integer pageCurrent);

	int validById(Integer id, Integer valid, String string);

	Map<String, Object> findObjectById(Integer id);

}
