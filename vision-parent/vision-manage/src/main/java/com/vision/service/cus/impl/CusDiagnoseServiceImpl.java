package com.vision.service.cus.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.exception.ServiceException;
import com.vision.mapper.cus.CusCustomerMapper;
import com.vision.mapper.cus.CusDiagnoseMapper;
import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.cus.CusDiagnose;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.service.cus.CusDiagnoseService;
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

	/**诊断表页面加载,查询*/
	@Override
	public PageObject<CusDiagnose> getDiagnose(CusVo cusVo) {
		Integer orgId = cusVo.getOrgId();
		Integer pageCurrent = cusVo.getPageCurrent();
		Integer pageSize = cusVo.getPageSize();
		//1.数据合法性验证
		if(pageCurrent==null||pageCurrent<=0)
			throw new ServiceException("页码值不正确");
		if(orgId<0||orgId==null)
			throw new ServiceException("门店id不正确");
		if(pageSize<0||pageSize==null)
			throw new ServiceException("页码大小不正确");
		//2.依据条件获取总记录数并进行验证
		int rowCount = cusDiagnoseMapper.getRowCount(orgId);
		//	System.out.println(rowCount);
		if(rowCount==0)
			throw new ServiceException("记录不存在");
		//3.基于条件查询当前页记录
		int startIndex = (pageCurrent-1)*pageSize;
		List<CusDiagnose> records = cusDiagnoseMapper.findPageObjects(orgId, startIndex, pageSize);
		//4.对查询结果进行封装并返回
		PageObject<CusDiagnose> pageObject = new PageObject<>();
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);

		/**页数设置，在com.md.common.vo.PageObject<T>中的getPageCount更改返回值
		 *  int pageCount=(rowCount-1)/pageSize+1;
		 *	pageObject.setPageCount(pageCount);
		 */
		return pageObject;
	}

	/**基于咨询表id,查询相关id所有信息*/
	@Override
	public CusDiagnose getDiagnoseById(Integer id) {
		if(id==null||id<=0)
			throw new ServiceException("id错误");
		//执行查找
		CusDiagnose cusDiagnose = cusDiagnoseMapper.selectById(id);
		return cusDiagnose;
	}

	/**基于客户id查询诊断表相关信息*/
	@Override
	public CusDiagnose getByCustomerId(CusVo cusVo) {
		Integer customerId = cusVo.getCustomerId();
		Integer orgId = cusVo.getOrgId();
		Integer pageCurrent = cusVo.getPageCurrent();
		Integer pageSize = cusVo.getPageSize();
		if(customerId==null||customerId<=0)
			throw new ServiceException("customerId错误");
		if(orgId==null||orgId<=0)
			throw new ServiceException("orgId错误");
		if(pageCurrent==null||pageCurrent<=0)
			throw new ServiceException("pageCurrent错误");
		if(pageSize==null||pageSize<=0)
			throw new ServiceException("pageSize错误");
		QueryWrapper<CusDiagnose> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("customer_id", customerId);
		CusDiagnose cusDiagnose = cusDiagnoseMapper.selectOne(queryWrapper);
		List<CusDiagnose> records = cusDiagnoseMapper.selectList(queryWrapper);
		Integer rowCount = cusDiagnoseMapper.selectCount(queryWrapper);
		PageObject<CusDiagnose> pageObject = new PageObject<>();
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		return cusDiagnose;
	}

	/**基于客户id创建客户诊断表*/
	@Override
	public Integer addDiagnose(CusDiagnose cusDiagnose) {
		if(cusDiagnose==null)
			throw new ServiceException("对象不能为空");
		if(cusDiagnose.getOrgId()==null||cusDiagnose.getOrgId()<=0)
			throw new ServiceException("orgId错误");
		cusDiagnose.setGmtCreate(new Date());
		cusDiagnose.setGmtModified(cusDiagnose.getGmtCreate());
		//保存数据
		int rows = cusDiagnoseMapper.insert(cusDiagnose);
		//添加客户表信息
		CusCustomer cusCustomer = new CusCustomer();
		cusCustomer.setId(cusDiagnose.getCustomerId());
		cusCustomer.setDiagnoseId(cusDiagnose.getId());
		cusCustomer.setGmtCreate(new Date());
		cusCustomerMapper.updateById(cusCustomer);
		return rows;
	}

	/**基于诊断表id删除数据*/
	@Override
	public Integer deleteDiagnose(Integer id, Integer orgId) {
		//验证数据
		if(id==null||id<=0)
			throw new ServiceException("请选择一条数据");
		if(orgId==null||orgId<=0)
			throw new ServiceException("orgId错误");
		//执行删除
		Integer rows = cusDiagnoseMapper.deleteById(id);
		//判断数据有无
		if(rows==0)
			throw new ServiceException("数据可能已删除");
		return rows;
	}
}
