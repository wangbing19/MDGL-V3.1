package com.vision.mapper.sys;

import java.util.List;

import com.vision.pojo.sys.vo.SysUserOrganization;

public interface SysUserRoleMapper {

	void insertObjects(Long userId, Integer[] roleIds);

	void deleteObjectsByUserId(Long userId);

	SysUserOrganization findObjectById(Integer userId);

	List<Integer> findRoleIdsByUserId(Integer userId);

}