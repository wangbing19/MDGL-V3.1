package com.vision.service.cus.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.exception.ServiceException;
import com.vision.mapper.cus.CusResScheduleMapper;
import com.vision.mapper.cus.CusScheduleMapper;
import com.vision.pojo.cus.CusDiagnose;
import com.vision.pojo.cus.CusResSchedule;
import com.vision.pojo.cus.CusSchedule;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.service.cus.CusScheduleService;
import com.vision.vo.PageObject;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CusScheduleServiceImpl implements CusScheduleService {

	@Autowired
	private CusScheduleMapper cusScheduleMapper;
	@Autowired
	private CusResScheduleMapper cusResScheduleMapper;
	

	/**基于用户/电话及当前页码值条件查询课程信息*/
	@Override
	public PageObject<CusSchedule> getSchedule(CusVo cusVo) {
		String name = cusVo.getName();
		if("".equals(name)) {
			name = null;
		}
		Integer pageCurrent = cusVo.getPageCurrent();
		Integer orgId = cusVo.getOrgId();
		Integer pageSize = cusVo.getPageSize();

		//1.数据合法性验证
		if(pageCurrent==null||pageCurrent<=0)
			throw new ServiceException("页码值不正确");
		if(orgId<0||orgId==null)
			throw new ServiceException("门店信息不正确");
		if(pageSize<0||pageSize==null)
			throw new ServiceException("pageSize不正确");
		//2.依据条件获取总记录数并进行验证
		int rowCount = cusScheduleMapper.getRowCount(name,orgId);
		if(rowCount==0)
			throw new ServiceException("记录不存在");
		//3.基于条件查询当前页记录
		int startIndex = (pageCurrent-1)*pageSize;
		List<CusSchedule> records =
				cusScheduleMapper.findPageObjects(
						name, startIndex, pageSize,orgId);
		//4.对查询结果进行封装并返回
		PageObject<CusSchedule> pageObject = 
				new PageObject<>();
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);

		return pageObject;
	}

	/**基于id删除课程信息*/
	@Override
	public Integer deleteSchedule(Integer id, Integer orgId) {
		//验证数据
		if(id==null||id<=0)
			throw new ServiceException("请选择一条数据");
		if(orgId==null||orgId<0)
			throw new ServiceException("orgId错误");
		//执行删除
		int rows = cusScheduleMapper.deleteById(id);
		//删除课程表与资源配置表(训练项目表)的关系表
		deleteCusResSchedule(id);
		
		//判断数据有无
		if(rows==0)
			throw new ServiceException("数据可能已删除");
		return rows;
	}

	/**基于id查询课程信息*/
	@Override
	public CusSchedule getScheduleById(Integer id, Integer orgId) {
		//验证数据
		if(id==null||id<=0)
			throw new ServiceException("请选择一条数据");
		if(orgId==null||orgId<=0)
			throw new ServiceException("orgId错误");
		//执行基于id查找课程表信息
		CusSchedule cusSchedule = cusScheduleMapper.selectById(id);
		
		//基于课程表查询训练项目信息
		QueryWrapper<CusResSchedule> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("cus_schedule_id", id);
		List<CusResSchedule> list = cusResScheduleMapper.selectList(queryWrapper);
		//遍历list存入数组
		Integer[] symptomTypes = new Integer[list.size()];
		for(int i = 0; i <list.size() ;i++) {
			symptomTypes[i] = list.get(i).getResSymptomId();
		}
		
		cusSchedule.setSymptomTypes(symptomTypes);
	//	System.out.println(cusSchedule.toString());
		return cusSchedule;
	}

	/**创建客户课程表*/
	@Override
	public Integer addSchedule(CusSchedule cusSchedule) {
	//	System.out.println(cusSchedule.toString());
		//验证数据合法性
		if(cusSchedule==null)
			throw new ServiceException("对象不能为空");
		if(StringUtils.isEmpty(cusSchedule.getName()))
			throw new ServiceException("用户名不能为空");
		if(cusSchedule.getOrgId()<0||cusSchedule.getOrgId()==null)
			throw new ServiceException("OrgId不能为空");
		if(cusSchedule.getSymptomTypes()==null||cusSchedule.getSymptomTypes().length==0)
			throw new ServiceException("课程表训练项目不能为空");
		//保存数据
		cusSchedule.setGmtCreate(new Date());
		cusSchedule.setGmtModified(cusSchedule.getGmtCreate());
		//保存课程表数据
		int rows = cusScheduleMapper.insert(cusSchedule);
		//更改用户表课程数量信息
		
		//循环遍历保存课程表训练项目id
		for (Integer resSymptomId : cusSchedule.getSymptomTypes()) {

			CusResSchedule cusResSchedule = new CusResSchedule();
			cusResSchedule.setCusScheduleId(cusSchedule.getId());
			cusResSchedule.setResSymptomId(resSymptomId);
			cusResScheduleMapper.insert(cusResSchedule);
		}
		//返回结果
		return rows;
	}

	/**修改课程表数据*/
	@Override
	public Integer updateSchedule(CusSchedule cusSchedule) {
	//	System.out.println(cusSchedule.toString());
		//验证数据合法性
		if(cusSchedule==null)
			throw new ServiceException("对象不能为空");
		if(cusSchedule.getId()<0||cusSchedule.getId()==null)
			throw new ServiceException("Id不能为空");
		if(StringUtils.isEmpty(cusSchedule.getName()))
			throw new ServiceException("用户名不能为空");
		if(cusSchedule.getOrgId()<0||cusSchedule.getOrgId()==null)
			throw new ServiceException("OrgId不能为空");
		if(cusSchedule.getSymptomTypes()==null||cusSchedule.getSymptomTypes().length==0)
			throw new ServiceException("课程表训练项目不能为空");
		//保存数据
		cusSchedule.setGmtModified(new Date());
		int rows = cusScheduleMapper.updateById(cusSchedule);
		
		//删除课程表与资源配置表(训练项目表)的关系表
		deleteCusResSchedule(cusSchedule.getId());
		for (Integer resSymptomId : cusSchedule.getSymptomTypes()) {

			CusResSchedule cusResSchedule = new CusResSchedule();
			cusResSchedule.setCusScheduleId(cusSchedule.getId());
			cusResSchedule.setResSymptomId(resSymptomId);
			cusResScheduleMapper.insert(cusResSchedule);
		}
		//返回结果
		return rows;
	}
	
	/**删除课程表与资源配置表(训练项目表)的关系表*/
	public void deleteCusResSchedule(Integer cusScheduleId) {
		QueryWrapper<CusResSchedule> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("cus_schedule_id", cusScheduleId);
		cusResScheduleMapper.delete(queryWrapper);
	}

	/**基于客户id查询用户课程表信息*/
	@Override
	public List<CusSchedule> getByCustomerId(CusVo cusVo) {
		Integer customerId = cusVo.getCustomerId();
		Integer orgId = cusVo.getOrgId();
		if(customerId==null||customerId<=0)
			throw new ServiceException("customerId错误");
		if(orgId==null||orgId<=0)
			throw new ServiceException("orgId错误");
		QueryWrapper<CusSchedule> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("customer_id", customerId);
		List<CusSchedule> list = cusScheduleMapper.selectList(queryWrapper);
		return list;
	}

	/**基于用户id删除课程信息*/
	@Override
	public Integer deleteScheduleByCustomerId(Integer customerId, Integer orgId) {
		CusVo cusVo = new CusVo();
		Integer row = null;
		cusVo.setCustomerId(customerId);
		cusVo.setOrgId(orgId);
		List<CusSchedule> byCustomerId = getByCustomerId(cusVo);
		for (CusSchedule cusSchedule : byCustomerId) {
			row = deleteSchedule(cusSchedule.getId(), orgId);
		}
		return row;
	}
	
	

}
