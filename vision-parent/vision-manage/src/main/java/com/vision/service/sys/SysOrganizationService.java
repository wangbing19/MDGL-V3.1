package com.vision.service.sys;

import java.util.List;
import java.util.Map;

import com.vision.pojo.sys.SysOrganization;
import com.vision.vo.Node;
import com.vision.vo.Node2;

public interface SysOrganizationService {

	int updateOrganization(SysOrganization sysOrganization);

	int deleteOrganization(Long organizationId);

	int saveOrganization(SysOrganization sysOrganization);

	List<SysOrganization> findOrganization();

	List<Node2> findZTreeNodes();

	

}
