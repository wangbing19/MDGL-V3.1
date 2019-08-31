package com.vision.service.rec;


import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.rec.RecActivityPush;
import com.vision.vo.PageObject;

public interface RecActivityService {
	/**根据充值活动的id删除该充值活动*/
	void deleteRecActivityById(Long[] ids);
	/**新增充值活动表对象*/
	void insertRecActivity(RecActivityPush recActivityPush);
	/**修改充值活动*/
	Integer updateRecActivityById(RecActivityPush recActivityPush);
	/**查询充值活动*/
	PageObject<RecActivityPush> findAllRecActivityObjects(CusVo cusVo);
	/**根据活动id查询充值活动*/
	RecActivityPush findRecActivityObject(Long id);
}
