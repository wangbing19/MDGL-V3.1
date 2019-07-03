package com.vision.service.ppo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.exception.ServiceException;
import com.vision.mapper.ppo.PpoAppointmentOrderMapper;
import com.vision.pojo.ppo.PpoAppointmentOrder;
import com.vision.pojo.ppo.PpoTrainer;
import com.vision.service.ppo.PpoAppointmentOrderService;
import com.vision.service.tool.ToolOrganizationIdList;
import com.vision.vo.PageObject;

@Service
public class PpoAppointmentOrderServiseImpl implements PpoAppointmentOrderService{
	@Autowired
	private PpoAppointmentOrderMapper ppoAppointmentOrderMapper;
	@Autowired
	private ToolOrganizationIdList toolOrganizationIdList;
	@Override
	public PageObject<PpoAppointmentOrder> findPpoOderAll(PpoAppointmentOrder ppoAppointmentOrder) {
		if(ppoAppointmentOrder.getOrganizationId() == null && ppoAppointmentOrder.getOrganizationId()  == null) {
			throw new ServiceException("参数不合法");
		}
		Long organizationId = ppoAppointmentOrder.getOrganizationId();
		
		List<Long> findOrganizationIdList = toolOrganizationIdList.findOrganizationIdList(organizationId);
		List<PpoAppointmentOrder> result= ppoAppointmentOrderMapper.findPpoOderAll(findOrganizationIdList);
		PageObject<PpoAppointmentOrder> pageObject = new PageObject<PpoAppointmentOrder>();
		pageObject.setRecords(result);
		return pageObject;
	}
	
	@Override
	public PageObject<PpoAppointmentOrder> findPpoOderAll(PpoAppointmentOrder ppoAppointmentOrder, Integer pageCurrent,
			Integer pageSize) {
		if(ppoAppointmentOrder.getOrganizationId() == null && ppoAppointmentOrder.getOrganizationId()  == null) {
			throw new ServiceException("参数不合法");
		}
		if(pageCurrent==null||pageCurrent<=0 && pageSize==null||pageSize<=0 ) throw new ServiceException("参数不合法");
		int startIndex=(pageCurrent-1)*pageSize;
		
		Long organizationId = ppoAppointmentOrder.getOrganizationId();
		List<Long> findOrganizationIdList = toolOrganizationIdList.findOrganizationIdList(organizationId);
		List<PpoAppointmentOrder> result= ppoAppointmentOrderMapper.findPpoOderAll(findOrganizationIdList,startIndex,pageSize);
		int pageCount = ppoAppointmentOrderMapper.findPpoOderCount(findOrganizationIdList);
		PageObject<PpoAppointmentOrder> pageObject = new PageObject<PpoAppointmentOrder>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCount(pageCount);
		pageObject.setRecords(result);
		return pageObject;
	}
	@Override
	public int savePpoOrder(PpoAppointmentOrder ppoAppointmentOrder) {
		if(ppoAppointmentOrder.getTrainerId() == null && ppoAppointmentOrder.getTrainerName() == null) {
			throw new ServiceException("训练师id和训练师姓名参数不能为空！");
		}
		if(ppoAppointmentOrder.getOrganizationId() == null) {
			throw new ServiceException("组织id不能为空！");
		}
		if(ppoAppointmentOrder.getCustomerId() == null) {
			throw new ServiceException("顾客id不能为空！");
		}
		if(ppoAppointmentOrder.getServiceItems() == null) {
			throw new ServiceException("预约项目名称不能为空！");
		}
		if(ppoAppointmentOrder.getAppointmentTime() == null) {
			throw new ServiceException("预约时间不能为空！");
		}
		/*
		 * if(ppoAppointmentOrder.getAppointmentTime() == null) { throw new
		 * ServiceException("预约时间不能为空！"); }
		 */
		ppoAppointmentOrder.setAppointmentState(1);
		ppoAppointmentOrder.setCreateTime(new Date());
		ppoAppointmentOrder.setModifiedTime(ppoAppointmentOrder.getCreateTime());
		int insert = ppoAppointmentOrderMapper.insert(ppoAppointmentOrder);
		return insert;
	}

	@Override
	public int deletePpoOrder(Long orderId) {
		if(orderId == null) {
			throw new ServiceException("订单ID不能为空");
		}
		int deleteById = ppoAppointmentOrderMapper.deleteById(orderId);
		return deleteById;
	}

	@Override
	public int updatePpoOrder(PpoAppointmentOrder ppoAppointmentOrder) {
		if(ppoAppointmentOrder.getTrainerId() == null && ppoAppointmentOrder.getTrainerName() == null) {
			throw new ServiceException("训练师id和训练师姓名参数不能为空！");
		}
		if(ppoAppointmentOrder.getOrganizationId() == null) {
			throw new ServiceException("组织id不能为空！");
		}
		if(ppoAppointmentOrder.getCustomerId() == null) {
			throw new ServiceException("顾客id不能为空！");
		}
		if(ppoAppointmentOrder.getServiceItems() == null) {
			throw new ServiceException("预约项目名称不能为空！");
		}
		if(ppoAppointmentOrder.getAppointmentTime() == null) {
			throw new ServiceException("预约时间不能为空！");
		}
		int result = ppoAppointmentOrderMapper.updateById(ppoAppointmentOrder);
		return result;
	}
}
