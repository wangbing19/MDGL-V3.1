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
import com.vision.service.tool.ToolOrganizationIdList;
@Service
public class PpoServiceItemsServiceImpl implements PpoServiceItemsService{
	@Autowired
	private PpoServiceItemsMapper ppoServiceItemsMapper;
	@Autowired
	private SysOrganizationMapper sysOrganizationMapper;
	@Autowired
	private ToolOrganizationIdList toolOrganizationIdList;
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
		if(organizationId == null) {
			throw new ServiceException("门店id不能为空！");
		}
		
		List<Long> findOrganizationIdList = toolOrganizationIdList.findOrganizationIdList(organizationId);
		List<PpoServiceItems> result= ppoServiceItemsMapper.selectByIds(findOrganizationIdList);
		return result;
	}

}
