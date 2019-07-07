package com.vision.mapper.cus;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.cus.CusCustomer;

public interface CusCustomerMapper extends BaseMapper<CusCustomer> {

	/**
	 * 基于用户名/电话查询当前页记录
	 * @param name	查询条件
	 * @param tel 
	 * @param startIndex	起始位置
	 * @param pageSize	页面大小
	 * @param orgIds	门店id
	 * @param consultationId 
	 * @return	当前页记录
	 */
	List<CusCustomer> findPageObjects(
			@Param("name")String name, 
			@Param("tel")String tel,
			@Param("startIndex")Integer startIndex, 
			@Param("pageSize")Integer pageSize, 
			@Param("orgIds")List<Long> orgIds, 
			@Param("consultationId")Integer consultationId);

	/**
	 * 基于用户名查询记录总数
	 * @param name	查询条件
	 * @param tel 
	 * @param orgIds	门店id
	 * @param consultationId 
	 * @return
	 */
	int getRowCount(
			@Param("name")String name,
			@Param("tel")String tel, 
			@Param("orgIds")List<Long> orgIds, 
			@Param("consultationId")Integer consultationId);
}
