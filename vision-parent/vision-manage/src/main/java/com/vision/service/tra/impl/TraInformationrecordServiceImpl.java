package com.vision.service.tra.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.exception.ServiceException;
import com.vision.mapper.tra.TraInformationrecordMapper;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.tra.TraInformationrecord;
import com.vision.service.cus.CusCustomerService;
import com.vision.service.tool.ToolOrganizationIdList;
import com.vision.service.tra.TraInformationrecordService;
import com.vision.vo.PageObject;

@Service
public class TraInformationrecordServiceImpl implements TraInformationrecordService {
	@Autowired
	private TraInformationrecordMapper traInformationrecordMapper;
	@Autowired
	private ToolOrganizationIdList toolOrganizationIdList;
	@Autowired
	private CusCustomerService cusCustomerService;

	/**分页*/
	@Override
	public PageObject<TraInformationrecord> getTraInfor(CusVo cusVo) {
		PageObject<TraInformationrecord> pageObject = new PageObject<>();
		String name = cusVo.getName();
		if("".equals(name)) {
			name = null;
		}
		Integer pageCurrent = cusVo.getPageCurrent();
		Integer orgId = cusVo.getOrgId();
		Integer pageSize = cusVo.getPageSize();
		Integer customerId = cusVo.getCustomerId();
		//查询组织下下级组织
		List<Long> orgIds = toolOrganizationIdList.findOrganizationIdList(orgId.longValue());	

		//2.依据条件获取总记录数并进行验证
		int rowCount = traInformationrecordMapper.getRowCount(name, orgIds, customerId);
		
		pageObject.setRowCount(rowCount);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		if(rowCount==0) {
			pageObject.setRecords(null);
			return pageObject;
		}
		//3.基于条件查询当前页记录
		int startIndex = (pageCurrent-1)*pageSize;
		List<TraInformationrecord> records =
				traInformationrecordMapper.findPageObjects(name, startIndex, pageSize, orgIds, customerId);
		//4.对查询结果进行封装并返回
		pageObject.setRecords(records);

		return pageObject;
	}


	/**添加训练记录到数据库*/
	@Override
	public Integer addTraInfor(TraInformationrecord entity) {

		entity.setGmtCreate(new Date());
		entity.setGmtModified(entity.getGmtCreate());
		entity.setEndTime(entity.getGmtCreate());

		int rows = traInformationrecordMapper.insert(entity);
		return rows;
	}


	/**删除*/
	@Override
	public Integer deleteTraInfor(Integer id, Integer orgId) {
		//执行删除
		int rows = traInformationrecordMapper.deleteById(id);
		return rows;
	}

	/**通过id查询*/
	@Override
	public TraInformationrecord getTraInforById(Integer id, Integer orgId) {
		TraInformationrecord traInformationrecord = traInformationrecordMapper.selectId(id);
		return traInformationrecord;
	}


	/**通过id修改训练表信息*/
	@Override
	public Integer updateTraInfor(TraInformationrecord entity) {
		entity.setGmtModified(new Date());
		int rows = traInformationrecordMapper.updateById(entity);
		return rows;
	}

	/**基于客户id查询用户课程表信息*/
	@Override
	public List<TraInformationrecord> getByCustomerId(CusVo cusVo) {
		Integer customerId = cusVo.getCustomerId();
		QueryWrapper<TraInformationrecord> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("customer_id", customerId);
		List<TraInformationrecord> list = traInformationrecordMapper.selectList(queryWrapper);
		return list;
	}

	/**基于客户id删除充值记录*/
	@Override
	public Integer deleteTraInforByCustomerId(Integer customerId, Integer orgId) {
		QueryWrapper<TraInformationrecord> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("customer_id", customerId);
//		queryWrapper.eq("org_id", orgId);
		int row = traInformationrecordMapper.delete(queryWrapper);
		return row;
	}

}
	

