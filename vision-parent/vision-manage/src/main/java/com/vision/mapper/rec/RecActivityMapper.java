package com.vision.mapper.rec;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.rec.RecActivityPush;

public interface RecActivityMapper extends BaseMapper<RecActivityPush> {
	/**根据充值活动的id删除该充值活动*/
	void deleteRecActivityById(@Param("ids")Long[] ids);
	/**新增充值活动表对象*/
	void insertRecActivity(RecActivityPush recActivityPush);
	/**修改充值活动*/
	void updateRecActivityById(RecActivityPush recActivityPush);
	/**查询充值活动*/
	List<RecActivityPush> findAllRecActivityObjects(
			@Param("title")String title,
			@Param("orgId")long orgId,
			@Param("startIndex")Integer startIndex, 
			@Param("pageSize")Integer pageSize);
	/**根据活动id查询充值活动*/
	RecActivityPush findRecActivityObject(@Param("id")Long id);
	/**查询数量*/
	int getRowCount(@Param("title")String title,@Param("orgId")long orgId);

}
