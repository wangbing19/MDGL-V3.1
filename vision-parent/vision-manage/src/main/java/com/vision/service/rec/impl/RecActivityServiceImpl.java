package com.vision.service.rec.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.exception.ServiceException;
import com.vision.mapper.rec.RecActivityMapper;
import com.vision.pojo.rec.RecActivityPush;
import com.vision.service.rec.RecActivityService;

@Service
public class RecActivityServiceImpl implements RecActivityService{
	@Autowired
	private RecActivityMapper recActivityMapper;

	@Override
	/**根据充值活动的id删除该充值活动*/
	public void deleteRecActivityById(Long id) {
		try {
			if(id==null) {
				throw new ServiceException("请先选择需要删除的充值活动项目");
			}
			recActivityMapper.deleteRecActivityById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	/**新增充值活动表对象*/
	public void insertRecActivity(RecActivityPush recActivityPush) {
		try {
			//暂时自定义门店id和父级门店id
			long userId= 1;
			long parentId = 2;
			//设置活动的门店id（标志是哪一个门店做的活动）
			recActivityPush.setUserId(userId); 
			//设置活动的上级门店id（关联门店做的活动，做好记录查询）
			recActivityPush.setParentId(parentId);
			//补齐活动创建时间
			recActivityPush.setCreateTime(new Date());
			//补齐活动修改时间
			recActivityPush.setModifiedTime(recActivityPush.getCreateTime());
			//判断活动开始时间和当前活动创建时间对比，确定活动状态 1 活动开始  0活动结束或者未开始
			Date activityStartTime = recActivityPush.getActivityStartTime();
			Date activityEndTime = recActivityPush.getActivityEndTime();
			if(activityStartTime.before(recActivityPush.getCreateTime())&&
					recActivityPush.getCreateTime().before(activityEndTime)) {
				recActivityPush.setActivityState(1);
			}else {
				recActivityPush.setActivityState(0);
			}
			//设置活动标题
			String title = "充"+recActivityPush.getPayAmount()+"元送"+recActivityPush.getPresentedAmount()+"元,可叠加";
			recActivityPush.setTitle(title);
			recActivityMapper.insertRecActivity(recActivityPush);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
