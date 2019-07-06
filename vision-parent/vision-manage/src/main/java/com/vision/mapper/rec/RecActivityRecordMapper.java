package com.vision.mapper.rec;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.vision.pojo.rec.RecPayUser;

public interface RecActivityRecordMapper {
	/**根据活动记录的id删除客户充值记录*/
	void deleteActivityRecordByid(@Param("id")Long id);
	/**根据充值记录id查询充值记录信息*/
	RecPayUser findRecActivityRecordById(@Param("id")Long id);
	/**根据用户id和父级id查询所有充值记录条数*/
	int getPageCount(@Param("userId")Long userId,@Param("parentId")Long parentId);
	/**分页查询所有充值记录条数*/
	List<RecPayUser> findLimitRecActivityRecords(@Param("startIndex")int startIndex,@Param("pageSize")Integer pageSize);

}
