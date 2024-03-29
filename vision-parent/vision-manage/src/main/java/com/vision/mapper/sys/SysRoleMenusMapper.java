package com.vision.mapper.sys;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.sys.SysMenu;
import com.vision.pojo.sys.SysRoleUser;
import com.vision.pojo.sys.vo.SysRoleMenus;

public interface SysRoleMenusMapper extends BaseMapper<SysRoleMenus> {

	int insertObject(Integer roleId, Integer[] menuIds);

	int deleteObjectsByRoleId(Integer roleId);
	/**
	 * 基于角色id获取菜单id
	 * @param roleIds
	 * @return
	 */
	List<Integer> findMenuIdsByRoleIds(
			@Param("roleIds")Integer...roleIds);
	
	List<Integer> findMenuIdsByRoleId(@Param("id")Integer id);

	int deleteRoleById(@Param("id")Integer id);

	List<SysRoleMenus> selectListAll(ArrayList<Integer> listIds);

	List<SysMenu> findMenuCheckbox(@Param("id")Long id);
}
