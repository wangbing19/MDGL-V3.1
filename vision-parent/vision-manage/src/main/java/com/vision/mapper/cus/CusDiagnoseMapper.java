package com.vision.mapper.cus;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.cus.CusDiagnose;

public interface CusDiagnoseMapper extends BaseMapper<CusDiagnose> {
	
	/**
	 * 依据条件获取总记录数并进行验证
	 * @param name 
	 * @param tel 
	 * @param orgIds	门店id
	 * @return
	 */
	int getRowCount(@Param("name")String name, @Param("tel")String tel, @Param("orgIds")List<Long> orgIds);
	
	/**
	 * 诊断表页面加载,查询
	 * @param name 
	 * @param tel 
	 * @param startIndex	起始位置
	 * @param pageSize	页面大小
	 * @param userId	门店id
	 * @return	当前页记录
	 */
	List<CusDiagnose> findPageObjects(
			@Param("name")String name, 
			@Param("tel")String tel,
			@Param("orgIds")List<Long> orgIds, 
			@Param("startIndex")int startIndex, 
			@Param("pageSize")int pageSize);
	
	/**
	 * 基于id和orgId查询
	 * @param id 
	 * @param orgId 
	 * @return	CusDiagnose
	 */
	CusDiagnose getDiagnoseById(@Param("id")Integer id, @Param("orgId")Integer orgId);
}
