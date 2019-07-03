package com.vision.service.rec.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	/**修改充值活动*/
	@Override
	public void updateRecActivityById(RecActivityPush recActivityPush) {
		try {
			if(recActivityPush==null) {
				throw new ServiceException("请选择需要修改的充值活动");
			}
			Date nowDate = new Date();
			//获取充值金额和赠送金额
			Double payAmount = recActivityPush.getPayAmount();
			Double presentedAmount = recActivityPush.getPresentedAmount();
			//更新中值活动标题
			String title = "充"+payAmount+"元送"+presentedAmount+"元,可叠加";
			recActivityPush.setTitle(title);
			//更新活动修改时间
			recActivityPush.setModifiedTime(nowDate);
			//更新活动时间和活动状态
			Date activityStartTime = recActivityPush.getActivityStartTime();
			Date activityEndTime = recActivityPush.getActivityEndTime();
			
			boolean startResult = activityStartTime.before(nowDate);
			boolean endResult = nowDate.before(activityEndTime);
			if(startResult&&endResult) {
				recActivityPush.setActivityState(1);
			}else {
				recActivityPush.setActivityState(0);
			}
			//System.out.println(recActivityPush.getId());
			recActivityMapper.updateRecActivityById(recActivityPush);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	/**查询充值活动*/
	public List<RecActivityPush> findAllRecActivityObjects() {
		try {
			//先设定门店id为1
			long user_id = 1;
			List<RecActivityPush> recActivityPushs = recActivityMapper.findAllRecActivityObjects(user_id);
			if(recActivityPushs==null||recActivityPushs.size()==0) {
				return null;
			}
			return recActivityPushs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
