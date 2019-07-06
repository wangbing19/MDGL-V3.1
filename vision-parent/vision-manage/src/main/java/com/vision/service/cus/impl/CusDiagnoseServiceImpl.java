package com.vision.service.cus.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.mapper.cus.CusCustomerMapper;
import com.vision.mapper.cus.CusDiagnoseMapper;
import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.cus.CusDiagnose;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.service.cus.CusDiagnoseService;
import com.vision.service.tool.ToolOrganizationIdList;
import com.vision.vo.PageObject;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CusDiagnoseServiceImpl implements CusDiagnoseService {

	@Autowired
	private CusDiagnoseMapper cusDiagnoseMapper;
	@Autowired
	private CusCustomerMapper cusCustomerMapper;
	@Autowired
	private ToolOrganizationIdList toolOrganizationIdList;

	/**诊断表页面加载,查询*/
	@Override
	public PageObject<CusDiagnose> getDiagnose(CusVo cusVo) {
		PageObject<CusDiagnose> pageObject = new PageObject<>();
		Integer orgId = cusVo.getOrgId();
		Integer pageCurrent = cusVo.getPageCurrent();
		Integer pageSize = cusVo.getPageSize();
		String name = cusVo.getName();
		if("".equals(name))
			name = null;
		String tel = cusVo.getTel();
		//查询组织下下级组织
		List<Long> orgIds = toolOrganizationIdList.findOrganizationIdList(orgId.longValue());
		//2.依据条件获取总记录数并进行验证
		int rowCount = cusDiagnoseMapper.getRowCount(name, tel, orgIds);
		if(rowCount==0) {
			pageObject.setRowCount(rowCount);
			pageObject.setRecords(null);
			pageObject.setPageCurrent(pageCurrent);
			pageObject.setPageSize(pageSize);
			return pageObject;
		}
		//3.基于条件查询当前页记录
		int startIndex = (pageCurrent-1)*pageSize;
		List<CusDiagnose> records = cusDiagnoseMapper.findPageObjects(name, tel, orgIds, startIndex, pageSize);
		//4.对查询结果进行封装并返回
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);

		return pageObject;
	}

	/**基于咨询表id,查询相关id所有信息*/
	@Override
	public CusDiagnose getDiagnoseById(Integer id, Integer orgId) {
		CusDiagnose cusDiagnose = cusDiagnoseMapper.getDiagnoseById(id, orgId);
		return cusDiagnose;
	}

	/**基于客户id查询诊断表相关信息*/
	@Override
	public CusDiagnose getByCustomerId(Integer customerId ,Integer orgId) {
		
		QueryWrapper<CusDiagnose> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("customer_id", customerId);
		queryWrapper.eq("org_id", orgId);
		CusDiagnose cusDiagnose = cusDiagnoseMapper.selectOne(queryWrapper);
		return cusDiagnose;
	}

	/**基于客户id创建客户诊断表*/
	@Override
	public Integer addDiagnose(CusDiagnose cusDiagnose) {
		
		cusDiagnose.setGmtCreate(new Date());
		cusDiagnose.setGmtModified(cusDiagnose.getGmtCreate());
		//保存数据
		int rows = cusDiagnoseMapper.insert(cusDiagnose);
		//添加客户表信息
		CusCustomer customer = cusCustomerMapper.selectById(cusDiagnose.getCustomerId());
		customer.setDiagnoseId(cusDiagnose.getId());
		customer.setGmtModified(new Date());
		cusCustomerMapper.updateById(customer);
		return rows;
	}

	/**基于诊断表id删除数据*/
	@Override
	public Integer deleteDiagnose(Integer id, Integer orgId) {
		//执行删除
		Integer rows = cusDiagnoseMapper.deleteById(id);
		//修改用户表数据
		QueryWrapper<CusCustomer> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("diagnose_id", id);
		queryWrapper.eq("org_id", orgId);
		CusCustomer cusCustomer = cusCustomerMapper.selectOne(queryWrapper);
		if(cusCustomer==null)
			return rows;
		cusCustomer.setDiagnoseId(null);
		cusCustomerMapper.updateById(cusCustomer);
		return rows;
	}

	/** 基于客户id删除诊断表信息 */
	@Override
	public Integer deleteDiagnoseByCustomerId(Integer customerId, Integer orgId) {
		
		QueryWrapper<CusDiagnose> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("customer_id", customerId);
		queryWrapper.eq("org_id", orgId);
		int row = cusDiagnoseMapper.delete(queryWrapper);
		return row;
	}
}
