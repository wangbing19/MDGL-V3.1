package com.vision.service.cus.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.exception.ServiceException;
import com.vision.mapper.cus.CusConsultationMapper;
import com.vision.mapper.cus.CusCustomerMapper;
import com.vision.mapper.cus.CusScheduleMapper;
import com.vision.pojo.cus.CusConsultation;
import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.cus.CusSchedule;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.rec.RecPayUser;
import com.vision.pojo.tra.TraInformationrecord;
import com.vision.service.cus.CusCustomerService;
import com.vision.vo.PageObject;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CusCustomerServiceImpl implements CusCustomerService {

	@Autowired
	private CusCustomerMapper cusCustomerMapper;
	@Autowired
	private CusConsultationMapper cusConsultationMapper;
	@Autowired
	private CusScheduleMapper cusScheduleMapper;

	/**用户页面查看所有信息*/
	@Override
	public PageObject<CusCustomer> getCustomer(CusVo cusVo) {

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
			throw new ServiceException("门店id信息不正确");
		if(pageSize<0||pageSize==null)
			throw new ServiceException("页码大小信息不正确");
		//2.依据条件获取总记录数并进行验证
		int rowCount = cusCustomerMapper.getRowCount(name,orgId);
		if(rowCount==0)
			throw new ServiceException("记录不存在");
		//3.基于条件查询当前页记录
		
		int startIndex = (pageCurrent-1)*pageSize;
		List<CusCustomer> records =
				cusCustomerMapper.findPageObjects(
						name, startIndex, pageSize,orgId);
		//4.对查询结果进行封装并返回
		PageObject<CusCustomer> pageObject = 
				new PageObject<>();
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);

		return pageObject;
	}

	/**基于客户id查询客户所有信息*/
	@Override
	public CusCustomer getCustomerById(Integer id, Integer orgId) {
		if(id<=0||id==null)
			throw new ServiceException("id错误");
		if(orgId<0||orgId==null)
			throw new ServiceException("orgId错误");
		try {
			CusCustomer cusCustomer = cusCustomerMapper.selectById(id);
			return cusCustomer;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("wqe");
		}
		return null;
		
	}

	/**基于用户id修改用户状态*/
	@Override
	public Integer updateCustomerState(CusVo cusVo) {
		CusCustomer cusCustomer = new CusCustomer();
		Integer id = cusVo.getId();
		Integer orgId = cusVo.getOrgId();
		Integer state = cusVo.getState();

		if(id<=0||id==null)
			throw new ServiceException("id错误");
		if(orgId<0||orgId==null)
			throw new ServiceException("orgId错误");
		if(state!=0 && state!=1)
			throw new ServiceException("状态错误");

		cusCustomer.setId(id);
		cusCustomer.setState(state);
		cusCustomer.setOrgId(orgId);
		/**获取登陆用户,还未写*/
		cusCustomer.setModifiedUser(cusVo.getUser());
		cusCustomer.setGmtModified(new Date());

		int rows = cusCustomerMapper.updateById(cusCustomer);
		return rows;
	}

	/**根据咨询表id查询客户表信息有无*/
	@Override
	public Integer getCustomerByConsultationId(Integer consultationId) {
		if(consultationId<=0||consultationId==null)
			throw new ServiceException("consultationId错误");
		QueryWrapper<CusCustomer> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("consultation_id", consultationId);
		Integer rows = cusCustomerMapper.selectCount(queryWrapper);
		return rows;

	}

	/**将CusCustomer类型数据添加到数据库*/
	@Override
	public Integer addCustomer(CusCustomer entity) {
		//验证数据合法性
		if(entity==null)
			throw new ServiceException("对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new ServiceException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getTel()))
			throw new ServiceException("电话不能为空");
		if(StringUtils.isEmpty(entity.getGuardian()))
			throw new ServiceException("监护人不能为空");
		if(entity.getOrgId()==0||entity.getOrgId()==null)
			throw new ServiceException("门店信息错误");
		//保存数据
		/**设置状态*/
		entity.setState(1);
		entity.setGmtCreate(new Date());
		entity.setGmtModified(entity.getGmtCreate());
		entity.setMoney(0.0);
		entity.setBalance(0.0);
		entity.setTotalTrainingTime(0);
		entity.setTimesOfTraining(0);
		entity.setRechargeCount(0);
		//建立咨询表对象并赋值
		CusConsultation consultation = new CusConsultation();
		consultation.setId(entity.getConsultationId());
		consultation.setName(entity.getName());
		consultation.setAge(entity.getAge());
		consultation.setGender(entity.getGender());
		consultation.setTel(entity.getTel());
		/**修改咨询表部分信息*/
		cusConsultationMapper.updateById(consultation);
		/**客户表新增*/
		Integer rows = cusCustomerMapper.insert(entity);
		//返回结果
		return rows;
	}

	/**基于id删除客户信息*/
	@Override
	public Integer deleteCustomer(Integer id, Integer orgId) {
		//1.验证参数有效性
		if(id==null||id<1)
			throw new ServiceException("参数id无效");
		//2.删除当前菜单信息
		int rows = cusCustomerMapper.deleteById(id);
		if(rows==0)
			throw new ServiceException("此客户可能已经不存在");
		//4.删除菜单角色的关系数据
		//关联其他表项未做删除
		return rows;
	}

	/**基于客户id修改客户信息*/
	@Override
	public Integer updateCustomer(CusCustomer entity) {
		//验证数据合法性
		if(entity==null)
			throw new ServiceException("对象不能为空");
		if(entity.getId()<=0||entity.getId()==null)
			throw new ServiceException("id错误");
		if(entity.getOrgId()<=0||entity.getOrgId()==null)
			throw new ServiceException("orgId错误");
		if(StringUtils.isEmpty(entity.getName()))
			throw new ServiceException("客户名不能为空");
		if(StringUtils.isEmpty(entity.getTel()))
			throw new ServiceException("电话不能为空");
		if(StringUtils.isEmpty(entity.getGuardian()))
			throw new ServiceException("监护人不能为空");
		//保存数据
		int rows = cusCustomerMapper.updateById(entity);
		//返回结果
		return rows;
	}

	/**基于用户id修改金额,余额及充值次数*/
	@Override
	public Integer updateObjectByMoney(RecPayUser recPayUser) {
		
		CusCustomer cusCustomer = new CusCustomer();
		CusCustomer customer = cusCustomerMapper.selectById(recPayUser.getCustomerId());
		//修改充值次数
		cusCustomer.setRechargeCount(customer.getRechargeCount()+1);
		//计算总金额
		double money = customer.getMoney();
		double rechargeAmount = recPayUser.getRechargeAmount();
		double presentedAmount = recPayUser.getPresentedAmount();
		money = money + rechargeAmount + presentedAmount;
		cusCustomer.setMoney(money);
		//计算余额
		double balance = customer.getBalance();
		balance = balance + rechargeAmount + presentedAmount;
		cusCustomer.setBalance(balance);
		//修改总训练次数
		Integer totalTrainingTime = customer.getTotalTrainingTime();
		totalTrainingTime = totalTrainingTime + recPayUser.getPracticeTimes();
		cusCustomer.setTotalTrainingTime(totalTrainingTime);
		//修改时间
		cusCustomer.setGmtModified(new Date());
		//根据id修改信息
		cusCustomer.setId(recPayUser.getCustomerId());
		int rows = cusCustomerMapper.updateById(cusCustomer);
		return rows;
	}



	/**基于训练记录表返回信息更改训练次数及余额*/
	@Override
	public Integer updateObjectByTimesOfTraining(TraInformationrecord entity) {
		CusCustomer cusCustomer = new CusCustomer();
		CusCustomer customer = cusCustomerMapper.selectById(entity.getCustomerId());
		//获取训练课程的单价
		CusSchedule cusSchedule = cusScheduleMapper.selectById(entity.getScheduleId());
		Double priceOfCourse = cusSchedule.getPriceOfCourse();
		//修改余额
		Double balance = customer.getBalance();
		balance = balance - priceOfCourse;
		if(balance<0) {
			cusCustomer.setState(0);
		}
		//获取训练次数并修改赋值
		cusCustomer.setTimesOfTraining(customer.getTimesOfTraining()+1);
		//设置余额
		cusCustomer.setBalance(balance);
		cusCustomer.setId(entity.getCustomerId());
		cusCustomer.setGmtCreate(new Date());
		int rows = cusCustomerMapper.updateById(cusCustomer);
		return rows;
	}
}
