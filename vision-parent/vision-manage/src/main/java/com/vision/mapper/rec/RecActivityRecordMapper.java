package com.vision.mapper.rec;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.rec.RecPayUser;

public interface RecActivityRecordMapper extends BaseMapper<RecPayUser> {
	/**根据活动记录的id删除客户充值记录*/
	void deleteActivityRecordByid(@Param("id")Long id);
	/**根据充值记录id查询充值记录信息*/
	RecPayUser findRecActivityRecordById(@Param("id")Long id);
	/**根据用户id和父级id查询所有充值记录条数*/
	int getPageCount(@Param("orgId")List<Long>  orgId);
	/**分页查询所有充值记录条数*/
	List<RecPayUser> findLimitRecActivityRecords(@Param("startIndex")Integer startIndex,
												 @Param("pageSize")Integer pageSize,
												 List<Long> orgIds);
	/**查询该用户上次充值时间*/
	RecPayUser getLastRechargeTime(@Param("customerId")Integer customerId);
	/**客户充值时新增新的充值记录*/
	int insertActivityRecord(RecPayUser recPayUser);
	/**根据客户id查询所有该门店的充值记录*/
	List<RecPayUser> findRecActivityRecordByCustomerId(@Param("customerId")Integer customerId);
	/**根据id批量删除充值记录*/
	Integer deleteAllRecPayUserByIds(List<Long> ids);

}
