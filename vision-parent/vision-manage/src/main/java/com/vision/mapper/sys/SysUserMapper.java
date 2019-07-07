package com.vision.mapper.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.sys.SysUser;
import com.vision.pojo.sys.vo.SysUserOrganization;

public interface SysUserMapper extends BaseMapper<SysUser>{

	int insertObject(SysUser entity);

	int updateObject(SysUser entity);

	int findObjectByColumn(String columnName, String columnValue);

	int getRowCount(String username);

	List<SysUserOrganization> findPageObjects(String username, int startIndex, int pageSize);

	int validById(@Param("id")Integer id,@Param("valid") Integer valid, @Param("modifiedUser")String modifiedUser);

	SysUserOrganization findObjectById(Integer userId);


}
