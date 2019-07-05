package com.vision.mapper.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysRoleMenusMapper {

	int insertObject(Integer roleId, Integer[] menuIds);

	int deleteObjectsByRoleId(Integer roleId);
	/**
	 * 基于角色id获取菜单id
	 * @param roleIds
	 * @return
	 */
	List<Integer> findMenuIdsByRoleIds(
			@Param("roleIds")Integer...roleIds);
	
	List<Integer> findMenuIdsByRoleId(Integer id);
}
