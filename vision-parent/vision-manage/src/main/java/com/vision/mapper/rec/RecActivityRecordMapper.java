package com.vision.mapper.rec;

import org.apache.ibatis.annotations.Param;

import com.vision.pojo.rec.RecPayUser;

public interface RecActivityRecordMapper {
	/**根据活动记录的id删除客户充值记录*/
	void deleteActivityRecordByid(@Param("id")Long id);
	/**根据充值记录id查询充值记录信息*/
	RecPayUser findRecActivityRecordById(@Param("id")Long id);

}
