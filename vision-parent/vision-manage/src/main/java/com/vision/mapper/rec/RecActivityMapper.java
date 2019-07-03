package com.vision.mapper.rec;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vision.pojo.rec.RecActivityPush;

public interface RecActivityMapper {
	/**根据充值活动的id删除该充值活动*/
	void deleteRecActivityById(@Param("id")Long id);
	/**新增充值活动表对象*/
	void insertRecActivity(RecActivityPush recActivityPush);
	/**修改充值活动*/
	void updateRecActivityById(RecActivityPush recActivityPush);
	/**查询充值活动*/
	List<RecActivityPush> findAllRecActivityObjects(@Param("userId")long userId);
	/**根据活动id查询充值活动*/
	RecActivityPush findRecActivityObject(@Param("id")Long id);

}
