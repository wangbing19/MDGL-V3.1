package com.vision.service.rec.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.exception.ServiceException;
import com.vision.mapper.rec.RecActivityMapper;
import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.rec.RecActivityPush;
import com.vision.service.rec.RecActivityService;
import com.vision.vo.PageObject;

@Service
public class RecActivityServiceImpl implements RecActivityService{
	@Autowired
	private RecActivityMapper recActivityMapper;

	@Override
	/**根据充值活动的id删除该充值活动*/
	public void deleteRecActivityById(Long[] ids) {
		recActivityMapper.deleteRecActivityById(ids);	
	}

	@Override
	/**新增充值活动表对象*/
	public void insertRecActivity(RecActivityPush recActivityPush) {
		try {
			Date date = new Date();
			//补齐活动创建时间
			recActivityPush.setCreateTime(date);
			//补齐活动修改时间
			recActivityPush.setModifiedTime(date);
			//判断活动开始时间和当前活动创建时间对比，确定活动状态 1 活动开始  0活动结束或者未开始
			Date activityStartTime = recActivityPush.getActivityStartTime();
			Date activityEndTime = recActivityPush.getActivityEndTime();
			if(activityStartTime.before(date) && date.before(activityEndTime)) {
				recActivityPush.setActivityState(1);
			}else if(activityStartTime.before(date) && activityEndTime.before(date)) {
				//活动结束
				recActivityPush.setActivityState(0);
			} else if(activityStartTime.after(date) && activityEndTime.after(date)) {
				//活动未开始
				recActivityPush.setActivityState(2);
			}
			recActivityMapper.insertRecActivity(recActivityPush);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**修改充值活动*/
	@Override
	public Integer updateRecActivityById(RecActivityPush recActivityPush) {
			Date nowDate = new Date();
			//更新活动修改时间
			recActivityPush.setModifiedTime(nowDate);
			//判断活动开始时间和当前活动创建时间对比，确定活动状态 1 活动开始  0活动结束或者未开始
			Date activityStartTime = recActivityPush.getActivityStartTime();
			Date activityEndTime = recActivityPush.getActivityEndTime();
			if(activityStartTime.before(nowDate) && nowDate.before(activityEndTime)) {
				recActivityPush.setActivityState(1);
			}else if(activityStartTime.before(nowDate) && activityEndTime.before(nowDate)) {
				//活动结束
				recActivityPush.setActivityState(0);
			} else if(activityStartTime.after(nowDate) && activityEndTime.after(nowDate)) {
				//活动未开始
				recActivityPush.setActivityState(2);
			}
			Integer row = recActivityMapper.updateById(recActivityPush);
			return row;
	}

	@Override
	/**查询充值活动*/
	public PageObject<RecActivityPush> findAllRecActivityObjects(CusVo cusVo) {
		PageObject<RecActivityPush> pageObject =  new PageObject<>();
		List<RecActivityPush> recActivityPushs = new ArrayList<RecActivityPush>();
		String title = cusVo.getName();
		if("".equals(title)) {
			title = null;
		}
		Integer pageCurrent = cusVo.getPageCurrent();
		Integer orgId = cusVo.getOrgId();
		Integer pageSize = cusVo.getPageSize();
		int rowCount = recActivityMapper.getRowCount(title,orgId);
		if(rowCount==0) {
			pageObject.setRowCount(rowCount);
			pageObject.setRecords(recActivityPushs);
			pageObject.setPageCurrent(pageCurrent);
			pageObject.setPageSize(pageSize);
			return pageObject;
		}
		//3.基于条件查询当前页记录
		int startIndex = (pageCurrent-1)*pageSize;
		recActivityPushs = recActivityMapper.findAllRecActivityObjects(title, orgId, startIndex, pageSize);
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(recActivityPushs);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);

		return pageObject;
	}

	@Override
	/**根据活动id查询充值活动*/
	public RecActivityPush findRecActivityObject(Long id) {
		try {
			if(id==null) {
				throw new ServiceException("没有查询的活动id");
			}			
			RecActivityPush recActivityPush = recActivityMapper.findRecActivityObject(id);
			return recActivityPush;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
