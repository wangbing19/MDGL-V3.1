package com.vision.mapper.sys;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.sys.SysUser;
import com.vision.pojo.sys.vo.SysUserOrganization;

public interface SysUserMapper extends BaseMapper<SysUser>{

	int insertObject(SysUser entity);

	int updateObject(SysUser entity);

	int findObjectByColumn(String columnName, String columnValue);

	int getRowCount(String username);

	List<SysUserOrganization> findPageObjects(String username, int startIndex, int pageSize);

	int validById(Integer id, Integer valid, String modifiedUser);

	SysUserOrganization findObjectById(Integer userId);


}
