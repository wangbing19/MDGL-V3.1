package com.vision.mapper.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.sys.SysLogs;
import com.vision.pojo.sys.vo.SysOrganizationLogs;

public interface SysLogsMapper extends BaseMapper<SysLogs>{

	List<SysLogs> findLogs(@Param("username")String name, @Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);

	int getRowCount(@Param("username")String name);

	List<SysOrganizationLogs> findAllLogs(@Param("list")List<Long> findOrganizationIdList,@Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize);

	int findSysLogCount(@Param("list")List<Long> findOrganizationIdList);
	
}
