package com.vision.service.rec.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vision.exception.ServiceException;
import com.vision.mapper.cus.CusCustomerMapper;
import com.vision.mapper.rec.RecActivityMapper;
import com.vision.mapper.rec.RecActivityRecordMapper;
import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.rec.RecActivityPush;
import com.vision.pojo.rec.RecPayUser;
import com.vision.service.cus.CusCustomerService;
import com.vision.service.rec.RecActivityRecordService;
import com.vision.service.tool.ToolOrganizationIdList;
import com.vision.vo.PageObject;

@Service
public class RecActivityRecordServiceImpl implements RecActivityRecordService{
	@Autowired
	private RecActivityRecordMapper recActivityRecordMapper;
    @Autowired
    private ToolOrganizationIdList toolOrganizationIdList;
    @Autowired
    private RecActivityMapper recActivityMapper;
    @Autowired
    private CusCustomerService cusCustomerService;
    @Autowired
	private CusCustomerMapper cusCustomerMapper;
	@Override
	/**根据活动记录的id删除客户充值记录*/
	public int deleteActivityRecordByid(Long id) {
		try {
			RecPayUser recPayUser = recActivityRecordMapper.findRecActivityRecordById(id);
			if(recPayUser==null) {
				return 2;			
			}
			//查看要删除的客户充值记录属于哪个门店下的
			Long userId = recPayUser.getUserId();
			//获取当前登录用户的id
			//杜健华假设
			Long nowUserId = 2L;
			if(userId!=nowUserId) {
				throw new ServiceException("没有权限删除,该客户记录不是本店充值的记录");
			}else if(userId.equals(nowUserId)){
				recActivityRecordMapper.deleteActivityRecordByid(id);
				return 1;
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	/**分页查询所有的充值记录*/
	//pageCurrent 当前页的页码  pageSize页面大小（显示几条数据）
	public PageObject<RecPayUser> findAllActivityRecords(Integer pageCurrent, Integer pageSize) {
		if(pageCurrent==null||pageCurrent<=0 && pageSize==null||pageSize<=0 ) {
			throw new ServiceException("参数不合法");
		}
		int startIndex=(pageCurrent-1)*pageSize;//计算当前页起始下标
		//杜健华假设
		Long orgId = 2L;
		
		//根据门店id获取所有子门店的id
		List<Long> orgIds = toolOrganizationIdList.findOrganizationIdList(orgId);
		//查询分页查询的数据的总条数
		int rowCount = 1;
		rowCount = recActivityRecordMapper.getPageCount(orgIds);	
		//分页查询所有记录
		
		List<RecPayUser> records = recActivityRecordMapper.findLimitRecActivityRecords(startIndex,pageSize,orgIds);
		PageObject<RecPayUser> pageObject = new PageObject<>();
		//设置总页数
		pageObject.setPageCount(rowCount/pageSize+1);
		//设置当前页数
		pageObject.setPageCurrent(pageCurrent);
		//设置页面显示的条数（大小）
		pageObject.setPageSize(pageSize);
		//设置总记录数
		pageObject.setRowCount(rowCount);
		//设置查询记录
		pageObject.setRecords(records);
		return pageObject;
	}

	@Override
	/**客户充值时新增新的充值记录*/
	public int insertActivityRecord(CusCustomer cusCustomer, RecPayUser recPayUser) {
		//获取客户的id
		Integer customerId = cusCustomer.getId();
		//获取客户的姓名
		String customerName = cusCustomer.getName();
		//获取客户的账户金额
		//Double customerMoney = cusCustomer.getMoney();
		if(customerId==null||customerName==null) {
			throw new ServiceException("充值时用户信息参数不合法");
		}
		//根据充值类型id查询充值的类型信息
		RecActivityPush RecActivityObject = recActivityMapper.findRecActivityObject(recPayUser.getRechargeType());
		//获取充值金额
		Double rechargeAmount = RecActivityObject.getPayAmount();
		//获取赠送金额
		Double presentedAmount = RecActivityObject.getPresentedAmount();
				
		//杜健华假设
		//假设该用的门店id
		Long userId = 1L;
		
		//创建需要插入的对象，更新字段
		//设置充值记录对用的充值类型id
		recPayUser.setRechargeType(recPayUser.getRechargeType());
		//设置充值记录创建时间
		recPayUser.setCreateTime(new Date());
		//设置充值记录更新时间
		recPayUser.setModifiedTime(recPayUser.getCreateTime());
		//设置充值记录组织id
		int  orgId = cusCustomer.getOrgId();		
		recPayUser.setOrgId((long)orgId);
		//设置充值记录客户id
		recPayUser.setCustomerId(customerId);
		//设置充值记录客户姓名
		recPayUser.setName(customerName);
		//设置充值记录门店id
		recPayUser.setUserId(userId);
		//设置充值记录充值金额
		recPayUser.setRechargeAmount(rechargeAmount);
		//设置充值记录赠送金额
		recPayUser.setPresentedAmount(presentedAmount);
		//查询该用户上次充值时间
		RecPayUser recPayUserLast = recActivityRecordMapper.getLastRechargeTime(customerId);
		if(recPayUserLast==null) {
			recPayUser.setLastPayTime(null);
		}else {
			//设置该客户充值记录上次充值时间
			recPayUser.setLastPayTime(recPayUserLast.getCreateTime());
		}		
		recPayUser.setPracticeTimes(0);
		Integer updateObjectByMoney = cusCustomerService.updateObjectByMoney(recPayUser);
		if(updateObjectByMoney!=null&&updateObjectByMoney>0) {
			CusCustomer selectById = cusCustomerMapper.selectById(customerId);//查询更新完后的顾客信息（方便取出客户账户余额）
			Double money = selectById.getMoney();
			Integer practiceTimes = selectById.getRechargeCount();
			//设置充值记录顾客的余额
			recPayUser.setMoney(money);
			//设置充值记录的顾客的总充值次数
			recPayUser.setPracticeTimes(practiceTimes);
			int insert = recActivityRecordMapper.insert(recPayUser);
			//int insertResult = recActivityRecordMapper.insertActivityRecord(recPayUser);
			if(insert>0) return 1;
		}		
		return 0;
	}

	@Override
	/**基于用户id删除所有相关的充值记录*/
	public Integer deleteRecPayUserByCustomerId(Integer customerId) {
		try {
			//根据客户id查询所有充值记录
			List<RecPayUser> list = recActivityRecordMapper.findRecActivityRecordByCustomerId(customerId);
			if(list==null) {
				return 0;
			}
			List<Long> idList = new ArrayList<Long>();
			//杜健华假设
			Long userIdLong = 1L;
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getUserId()==userIdLong) {
					idList.add(list.get(i).getId());
				}
			}
			//根据id批量删除充值记录
			Integer result = recActivityRecordMapper.deleteAllRecPayUserByIds(idList);
			if(result==0) {
				return 0;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}	
}
