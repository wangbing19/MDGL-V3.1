package com.vision.mapper.sys;



import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.sys.SysRole;
import com.vision.pojo.sys.vo.SysRoleOrganizationResult;
import com.vision.vo.CheckBox;
public interface SysRoleMapper extends BaseMapper<SysRole>{

	int insertObject(SysRole sysRole);

	int deleteObject(Integer id);

	int updateObject(SysRole sysRole);

	List<CheckBox> findObjects();

	SysRoleOrganizationResult findObjectById(Integer id);

	List<SysRole> doFindRoleAll(@Param("startIndex")Integer startIndex,@Param("pageSize") Integer pageSize);

	


	
	
}
