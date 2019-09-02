package com.vision.mapper.sys;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.sys.SysRoleUser;
import com.vision.pojo.sys.vo.SysUserOrganization;

public interface SysUserRoleMapper extends BaseMapper<SysRoleUser>{

	void insertObjects(Long id, Integer[] roleIds);

	void deleteObjectsByUserId(Long id);

	SysUserOrganization findObjectById(Integer userId);

	List<Integer> findRoleIdsByUserId(Integer id);

}
