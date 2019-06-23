package com.vision.mapper.cus;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.cus.CusDiagnose;

public interface CusDiagnoseMapper extends BaseMapper<CusDiagnose> {
	
	/**
	 * 依据条件获取总记录数并进行验证
	 * @param orgId	门店id
	 * @return
	 */
	int getRowCount(@Param("orgId")Integer orgId);
	
	/**
	 * 诊断表页面加载,查询
	 * @param startIndex	起始位置
	 * @param pageSize	页面大小
	 * @param userId	门店id
	 * @return	当前页记录
	 */
	List<CusDiagnose> findPageObjects(
			@Param("orgId")Integer orgId, 
			@Param("startIndex")int startIndex, 
			@Param("pageSize")int pageSize);
}
