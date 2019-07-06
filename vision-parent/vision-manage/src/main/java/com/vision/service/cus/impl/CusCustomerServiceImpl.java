package com.vision.service.cus.impl;

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
import com.vision.service.cus.CusDiagnoseService;
import com.vision.service.cus.CusScheduleService;
import com.vision.service.tool.ToolOrganizationIdList;
import com.vision.service.tra.TraInformationrecordService;
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
	@Autowired
	private CusDiagnoseService cusDiagnoseService;
	@Autowired
	private TraInformationrecordService traInformationrecordService;
	@Autowired
	private CusScheduleService cusScheduleService;
	@Autowired
	private ToolOrganizationIdList toolOrganizationIdList;

	/**用户页面查看所有信息*/
	@Override
	public PageObject<CusCustomer> getCustomer(CusVo cusVo) {

		String name = cusVo.getName();
		if("".equals(name)) {
			name = null;
		}
		String tel = cusVo.getTel();
		Integer pageCurrent = cusVo.getPageCurrent();
		Integer orgId = cusVo.getOrgId();
		Integer pageSize = cusVo.getPageSize();
		//查询组织下下级组织
		List<Long> orgIds = toolOrganizationIdList.findOrganizationIdList(orgId.longValue());			
		//2.依据条件获取总记录数并进行验证
		int rowCount = cusCustomerMapper.getRowCount(name,tel,orgIds);
		if(rowCount==0)
			throw new ServiceException("记录不存在");
		//3.基于条件查询当前页记录
		
		int startIndex = (pageCurrent-1)*pageSize;
		List<CusCustomer> records =
				cusCustomerMapper.findPageObjects(
						name, tel, startIndex, pageSize,orgIds);
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
		QueryWrapper<CusCustomer> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("org_id", orgId);
		CusCustomer cusCustomer = cusCustomerMapper.selectOne(queryWrapper);
//		CusCustomer cusCustomer = cusCustomerMapper.selectById(id);
		return cusCustomer;
		
	}

	/**基于用户id修改用户状态*/
	@Override
	public Integer updateCustomerState(CusVo cusVo) {
		CusCustomer cusCustomer = new CusCustomer();
		Integer id = cusVo.getId();
		Integer orgId = cusVo.getOrgId();
		Integer state = cusVo.getState();

		cusCustomer.setId(id);
		cusCustomer.setState(state);
		cusCustomer.setOrgId(orgId);
		/**获取登陆用户,还未写*/
		cusCustomer.setModifiedUser(cusVo.getUser());
		cusCustomer.setGmtModified(new Date());

		int row = cusCustomerMapper.updateById(cusCustomer);
		return row;
	}

	/**根据咨询表id查询客户表信息有无*/
	@Override
	public Integer getCustomerByConsultationId(Integer consultationId) {
		QueryWrapper<CusCustomer> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("consultation_id", consultationId);
		Integer rows = cusCustomerMapper.selectCount(queryWrapper);
		return rows;

	}

	/**将CusCustomer类型数据添加到数据库*/
	@Override
	public Integer addCustomer(CusCustomer entity) {

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
		QueryWrapper<CusCustomer> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("org_id", orgId);
		//2.删除当前用户信息
		int row = cusCustomerMapper.delete(queryWrapper);
		//4.删除菜单角色的关系数据
		//删除诊断表
		cusDiagnoseService.deleteDiagnoseByCustomerId(id, orgId);
		//删除课程表
		cusScheduleService.deleteScheduleByCustomerId(id, orgId);
		//删除训练表
		traInformationrecordService.deleteTraInforByCustomerId(id, orgId);
		//删除充值记录
		
		return row;
	}

	/**基于客户id修改客户信息*/
	@Override
	public Integer updateCustomer(CusCustomer entity) {
		//保存数据
		int rows = cusCustomerMapper.updateById(entity);
		//返回结果
		return rows;
	}

	/**基于用户id修改金额,余额及充值次数*/
	@Override
	public Integer updateObjectByMoney(RecPayUser recPayUser) {
		
		CusCustomer cusCustomer = cusCustomerMapper.selectById(recPayUser.getCustomerId());
		//修改充值次数
		cusCustomer.setRechargeCount(cusCustomer.getRechargeCount()+1);
		//计算总金额
		double money = cusCustomer.getMoney();
		double rechargeAmount = recPayUser.getRechargeAmount();
		double presentedAmount = recPayUser.getPresentedAmount();
		money = money + rechargeAmount + presentedAmount;
		cusCustomer.setMoney(money);
		//计算余额
		double balance = cusCustomer.getBalance();
		balance = balance + rechargeAmount + presentedAmount;
		cusCustomer.setBalance(balance);
		if(balance>0) {
			cusCustomer.setState(1);
		}
		//修改总训练次数
		Integer totalTrainingTime = cusCustomer.getTotalTrainingTime();
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
		CusCustomer cusCustomer = cusCustomerMapper.selectById(entity.getCustomerId());
		//获取训练课程的单价
		CusSchedule cusSchedule = cusScheduleMapper.selectById(entity.getScheduleId());
		Double priceOfCourse = cusSchedule.getPriceOfCourse();
		//修改余额
		Double balance = cusCustomer.getBalance();
		balance = balance - priceOfCourse;
		if(balance<=0) {
			cusCustomer.setState(0);
		}
		//获取训练次数并修改赋值
		cusCustomer.setTimesOfTraining(cusCustomer.getTimesOfTraining()+1);
		//设置余额
		cusCustomer.setBalance(balance);
		cusCustomer.setId(entity.getCustomerId());
		cusCustomer.setGmtCreate(new Date());
		int rows = cusCustomerMapper.updateById(cusCustomer);
		return rows;
	}
}
