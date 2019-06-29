package com.vision.service.tool.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.mapper.sys.SysOrganizationMapper;
import com.vision.pojo.sys.SysOrganization;
import com.vision.service.tool.ToolOrganizationIdList;
@Service
public class ToolOrganizationIdListImpl implements ToolOrganizationIdList{
	@Autowired
	private SysOrganizationMapper sysOrganizationMapper;
	@Override
	public List<Long> findOrganizationIdList(Long organizationId) {
		boolean state = true;
		List<Long> organizationIdList = new ArrayList<Long>();
		List<Long> list = new ArrayList<Long>();
		QueryWrapper<SysOrganization> queryWrapper = new QueryWrapper<SysOrganization>();
		queryWrapper.eq("organization_parent_id", organizationId);
		List<SysOrganization> selectList = sysOrganizationMapper.selectList(queryWrapper);
		while (state) {
			if(selectList.size() > 0) {
				for (SysOrganization sysOrganization : selectList) {
					organizationIdList.add(sysOrganization.getOrganizationId());
					list.add(sysOrganization.getOrganizationId());
				}
				selectList.clear();
				selectList = sysOrganizationMapper.selectOrganizationIdList(list);
				list.clear();
			}else {
				state = false;
			}
			
			
			
		}
		
		organizationIdList.add(organizationId);
		return organizationIdList;
	}

}
