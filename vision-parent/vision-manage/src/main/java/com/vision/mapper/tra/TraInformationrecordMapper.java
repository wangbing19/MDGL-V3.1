package com.vision.mapper.tra;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.tra.TraInformationrecord;

public interface TraInformationrecordMapper extends BaseMapper<TraInformationrecord>{
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
	 * @param orgIds 
	 * @param customerId
	 * @return	当前页记录
	 */
	List<TraInformationrecord> findPageObjects(
			@Param("name")String name, 
			@Param("startIndex")Integer startIndex, 
			@Param("pageSize")Integer pageSize, 
			@Param("orgIds")List<Long> orgIds,
			@Param("customerId")Integer customerId);
	
	
	
	/**
	 * 基于用户名查询记录总数
	 * @param name	查询条件
	 * @param orgIds 
	 * @param customerId
	 * @return
	 */
	int getRowCount(
			@Param("name")String name, 
			@Param("orgIds")List<Long> orgIds, 
			@Param("customerId")Integer customerId);


	TraInformationrecord selectId(Integer id);
}
