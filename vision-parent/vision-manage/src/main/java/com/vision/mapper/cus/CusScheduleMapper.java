package com.vision.mapper.cus;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.cus.CusSchedule;

public interface CusScheduleMapper extends BaseMapper<CusSchedule> {
	
	/**
	 * 基于用户名/电话查询当前页记录
	 * @param name	查询条件
	 * @param startIndex	起始位置
	 * @param pageSize	页面大小
	 * @param orgIds	门店id
	 * @param customerId 
	 * @return	当前页记录
	 */
	List<CusSchedule> findPageObjects(
			@Param("name")String name, 
			@Param("startIndex")Integer startIndex, 
			@Param("pageSize")Integer pageSize, 
			@Param("orgIds")List<Long> orgIds, 
			@Param("customerId")Integer customerId);


	/**
	 * 基于用户名查询记录总数
	 * @param name	查询条件
	 * @param orgIds	门店id
	 * @param customerId 
	 * @return
	 */
	int getRowCount(
			@Param("name")String name,
			@Param("orgIds")List<Long> orgIds, 
			@Param("customerId")Integer customerId);
}
