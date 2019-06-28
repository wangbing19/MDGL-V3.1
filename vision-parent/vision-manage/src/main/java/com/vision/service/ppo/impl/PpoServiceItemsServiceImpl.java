package com.vision.service.ppo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.exception.ServiceException;
import com.vision.mapper.ppo.PpoServiceItemsMapper;
import com.vision.mapper.sys.SysOrganizationMapper;
import com.vision.pojo.ppo.PpoServiceItems;
import com.vision.pojo.sys.SysOrganization;
import com.vision.service.ppo.PpoServiceItemsService;
@Service
public class PpoServiceItemsServiceImpl implements PpoServiceItemsService{
	@Autowired
	private PpoServiceItemsMapper ppoServiceItemsMapper;
	@Autowired
	private SysOrganizationMapper sysOrganizationMapper;
	@Override
	public int saveServiceItems(PpoServiceItems ppoServiceItems) {
		if(ppoServiceItems.getOrganizationId() == null) {
			throw new ServiceException("门店id不能为空！");
		}
		
		if(ppoServiceItems.getServiceName() == null) {
			throw new ServiceException("服务项目不能为空！");
		}
		
		int insert = ppoServiceItemsMapper.insert(ppoServiceItems);
		return insert;
	}
	
	@Override
	public List<PpoServiceItems> findServiceItems(Long organizationId) {
		boolean state = true;
		List<Long> organizationIdList = new ArrayList<Long>();
		organizationIdList.add(organizationId);
		if(organizationId == null) {
			throw new ServiceException("门店id不能为空！");
		}
		QueryWrapper<SysOrganization> queryWrapper = new QueryWrapper<SysOrganization>();
		queryWrapper.eq("organization_parent_id", organizationId);
		List<SysOrganization> selectList = sysOrganizationMapper.selectList(queryWrapper);
		
		SysOrganization selectById = sysOrganizationMapper.selectById(organizationId);
		
		while (state) {

			if(selectById != null && selectById.getOrganizationParentId() != null) {
				organizationIdList.add(selectById.getOrganizationParentId());
				 selectById = sysOrganizationMapper.selectById(selectById.getOrganizationParentId());
			}else {
				state = false;
			}
		}
		
		
		List<PpoServiceItems> result= ppoServiceItemsMapper.selectByIds(organizationIdList);
		return result;
	}

}
