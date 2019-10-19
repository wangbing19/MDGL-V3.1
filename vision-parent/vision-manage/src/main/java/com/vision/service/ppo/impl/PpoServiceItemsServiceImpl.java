package com.vision.service.ppo.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.exception.ServiceException;
import com.vision.mapper.ppo.PpoServiceItemsMapper;
import com.vision.mapper.sys.SysOrganizationMapper;
import com.vision.pojo.ppo.PpoServiceItems;
import com.vision.pojo.ppo.PpoTrainer;
import com.vision.service.ppo.PpoServiceItemsService;
import com.vision.service.tool.ToolOrganizationIdList;
import com.vision.vo.PageObject;
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
	public PageObject<PpoServiceItems> findServiceItems(Long organizationId,Integer pageCurrent,Integer pageSize) {
		boolean state = true;
		if(organizationId == null||organizationId<0) {
			throw new ServiceException("组织id不能为空或id错误!");
		}
		if(pageCurrent==null||pageCurrent<=0) throw new ServiceException("参数不合法");
		
		List<Long> findOrganizationIdList = toolOrganizationIdList.findOrganizationIdList(organizationId);
		Integer pageCount  = ppoServiceItemsMapper.selectCountNum(findOrganizationIdList);
		List<PpoServiceItems> result= ppoServiceItemsMapper.selectByIds(findOrganizationIdList);
		PageObject<PpoServiceItems> pageObject = new PageObject<PpoServiceItems>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCount(pageCount);
		pageObject.setRecords(result);
		return pageObject;
	}

	@Override
	public int deleteServiceItems(Long rederId) {
		if(rederId == null) {
			throw new ServiceException("订单id不能为空！");
		}
		int deleteById = ppoServiceItemsMapper.deleteById(rederId);
		return deleteById;
	}

	@Override
	public int updeteServiceItems(PpoServiceItems ppoServiceItems) {
		if(ppoServiceItems.getOrganizationId() == null) {
			throw new ServiceException("门店id不能为空！");
		}
		
		if(ppoServiceItems.getServiceName() == null) {
			throw new ServiceException("服务项目不能为空！");
		}
		int updateById = ppoServiceItemsMapper.updateById(ppoServiceItems);
		
		return updateById;
	}

	@Override
	public PpoServiceItems findServiceItemOne(PpoServiceItems ppoServiceItems) {
		if(ppoServiceItems.getOrganizationId() == null) {
			throw new ServiceException("门店id不能为空！");
		}
		if(ppoServiceItems.getId() == null) {
			throw new ServiceException("服务项目id不能为空！");
		}
		QueryWrapper<PpoServiceItems> queryWrapper = new QueryWrapper<PpoServiceItems>();
		queryWrapper.eq("id", ppoServiceItems.getId());
		PpoServiceItems selectOne = ppoServiceItemsMapper.selectOne(queryWrapper);
		return selectOne;
	}

	

	
}
