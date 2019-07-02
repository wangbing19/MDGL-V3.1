package com.vision.mapper.tra;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.tra.TraTrainingEquipment;

public interface TraTrainingEquipmentMapper extends BaseMapper<TraTrainingEquipment>{
//	
//	List<TraInformationrecord> findPageObjects(
//			@Param("name") String name,
//			@Param("startIndex")Integer startIndex,
//			@Param("pageSize")Integer pageSize,
//			@Param("userParentId")Integer userParentId);
	
	
	/**
	 * 基于用户名/电话查询当前页记录
	 * @param name	查询条件
	 * @param startIndex	起始位置
	 * @param pageSize	页面大小
	 * @param orgId	门店id
	 * @return	当前页记录
	 */
	List<TraTrainingEquipment> findPageObjects(
			@Param("name")String name, 
			@Param("startIndex")Integer startIndex, 
			@Param("pageSize")Integer pageSize, 
			@Param("orgId")Integer orgId);
	
	
	
	/**
	 * 基于用户名查询记录总数
	 * @param name	查询条件
	 * @param orgId	门店id
	 * @return
	 */
	int getRowCount(@Param("name")String name, @Param("orgId")Integer orgId);


	TraTrainingEquipment selectId(Integer id);
}
