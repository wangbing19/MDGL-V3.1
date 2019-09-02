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
	/**
	 * 基于id获取组织信息
	 * @param id
	 * @return
	 */
	SysOrganization getSysOrganizationById(Long id);

	/**
	 * @Author lihd
	 * @Description 根据id查询子级信息
	 * @Date 2019/9/2 19:27
	 * @param
	 * @return
	 */
	List<SysOrganization> findSublevel(Long organizationId);
}
