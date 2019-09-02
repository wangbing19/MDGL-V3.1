package com.vision.service.sys;

import java.util.List;

import com.vision.pojo.sys.SysRole;
import com.vision.pojo.sys.vo.SysRoleOrganizationResult;
import com.vision.vo.CheckBox;
import com.vision.vo.PageObject;

public interface SysRoleService {

	int doInsertRole(SysRole sysRole,Integer[] menuIds);

	int deleteRole(Integer id);

	int updateRole(SysRole sysRole, Integer[] menuIds);

	List<CheckBox> findObjects();

	SysRoleOrganizationResult findObjectById(Integer id);

	List<SysRole> findObjectByIds(Integer[] ids);

	 PageObject<SysRole> doFindRoleAll(Integer pageCurrent,Integer pageSize);
	
	
}
