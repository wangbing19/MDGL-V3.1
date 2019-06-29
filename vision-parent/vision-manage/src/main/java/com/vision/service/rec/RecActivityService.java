package com.vision.service.rec;

import com.vision.pojo.rec.RecActivityPush;

public interface RecActivityService {
	/**根据充值活动的id删除该充值活动*/
	void deleteRecActivityById(Long id);

	void insertRecActivity(RecActivityPush recActivityPush);

}
