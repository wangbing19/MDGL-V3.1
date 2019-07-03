package com.vision.service.rec;

import java.util.List;

import com.vision.pojo.rec.RecActivityPush;

public interface RecActivityService {
	/**根据充值活动的id删除该充值活动*/
	void deleteRecActivityById(Long id);
	/**新增充值活动表对象*/
	void insertRecActivity(RecActivityPush recActivityPush);
	/**修改充值活动*/
	void updateRecActivityById(RecActivityPush recActivityPush);
	/**查询充值活动*/
	List<RecActivityPush> findAllRecActivityObjects();
	/**根据活动id查询充值活动*/
	RecActivityPush findRecActivityObject(Long id);
}
