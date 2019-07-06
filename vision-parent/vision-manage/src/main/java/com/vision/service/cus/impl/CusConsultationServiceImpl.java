package com.vision.service.cus.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.vision.mapper.cus.CusConsultationMapper;
import com.vision.mapper.cus.CusCustomerMapper;
import com.vision.pojo.cus.CusConsultation;
import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.service.cus.CusConsultationService;
import com.vision.service.tool.ToolOrganizationIdList;
import com.vision.vo.PageObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CusConsultationServiceImpl implements CusConsultationService {
	@Autowired
	private CusConsultationMapper cusConsultationMapper;
	@Autowired
	private CusCustomerMapper CusCustomerMapper;
	@Autowired
	private ToolOrganizationIdList toolOrganizationIdList;

	/**基于用户/电话及当前页码值条件查询用户信息*/
	@Override
	public PageObject<CusConsultation> getConsultation(CusVo cusVo) {
		PageObject<CusConsultation> pageObject = new PageObject<>();
		String name = cusVo.getName();
		if("".equals(name)) {
			name = null;
		}
		String tel = cusVo.getTel();
		Integer pageCurrent = cusVo.getPageCurrent();
		Integer orgId = cusVo.getOrgId();
		Integer pageSize = cusVo.getPageSize();
		Integer consultationId = cusVo.getConsultationId();
		//查询组织下下级组织
		List<Long> orgIds = toolOrganizationIdList.findOrganizationIdList(orgId.longValue());
		//2.依据条件获取总记录数并进行验证
		int rowCount = cusConsultationMapper.getRowCount(name, tel, orgIds,consultationId);
		if(rowCount==0) {
//			throw new ServiceException("记录不存在");
			pageObject.setRowCount(rowCount);
			pageObject.setRecords(null);
			pageObject.setPageCurrent(pageCurrent);
			pageObject.setPageSize(pageSize);
			return pageObject;
		}
			
		int startIndex = (pageCurrent-1)*pageSize;
		List<CusConsultation> records =
				cusConsultationMapper.findPageObjects(name, tel,startIndex, pageSize,orgIds,consultationId);// userId, userParentId ,
		//4.对查询结果进行封装并返回
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);

		return pageObject;
	}


	/**将CusCustomer类型数据添加到数据库*/
	@Override
	public Integer addConsultation(CusConsultation entity) {
		
		//封装信息
		entity.setGmtCreate(new Date());
		entity.setGmtModified(entity.getGmtCreate());
		//保存数据
		Integer row = cusConsultationMapper.insert(entity);

		//返回结果
		return row;
	}

	/**基于id删除咨询表信息*/
	@Override
	public Integer deleteConsultation(Integer id, Integer  orgId) {
		QueryWrapper<CusConsultation> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("org_id", orgId);
		//执行删除
		int rows = cusConsultationMapper.delete(queryWrapper);
		
		return rows;
	}

	/**基于咨询表id,查询相关id所有信息*/
	@Override
	public CusConsultation getConsultationById(Integer id, Integer orgId) {
		QueryWrapper<CusConsultation> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("org_id", orgId);
		CusConsultation cusConsultation = cusConsultationMapper.selectOne(queryWrapper);
		return cusConsultation;
	}

	/**基于咨询表id更改用户信息*/
	@Override
	public Integer updateConsultation(CusConsultation entity) {

	//	entity.setFOther(null);
		int rows = cusConsultationMapper.updateObject(entity);
		//修改客户数据信息
		QueryWrapper<CusCustomer> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("consultation_id", entity.getId());
		//修改客户信息
		CusCustomer cusCustomer = CusCustomerMapper.selectOne(queryWrapper);
		if(cusCustomer!=null) {
			cusCustomer.setName(entity.getName());
			cusCustomer.setAge(entity.getAge());
			cusCustomer.setGender(entity.getGender());
			cusCustomer.setTel(entity.getTel());
			UpdateWrapper<CusCustomer> updateWrapper = new UpdateWrapper<>();
			updateWrapper.eq("consultation_id", entity.getId());
			CusCustomerMapper.update(cusCustomer, updateWrapper);
		}
		return rows;
	}
}
