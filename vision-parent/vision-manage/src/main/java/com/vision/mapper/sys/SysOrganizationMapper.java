package com.vision.mapper.sys;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.sys.SysOrganization;
import com.vision.vo.Node;
import com.vision.vo.Node2;

public interface SysOrganizationMapper extends BaseMapper<SysOrganization>{

	

	List<SysOrganization> selectOrganizationIdList(List<Long> list);

	int getChildCount(@Param("organizationId")Long organizationId);

	List<Map<String, Object>> findObjects();

	List<Node2> findZTreeNodes();
	SysOrganization findById(@Param("organizationId")Long organizationId);

	List<SysOrganization> findObjectsAll();

}
