package com.vision.service.rec;

import com.vision.pojo.rec.RecPayUser;
import com.vision.vo.PageObject;

public interface RecActivityRecordService {
	/**根据活动记录的id删除客户充值记录*/
	int deleteActivityRecordByid(Long id);
	/**分页查询所有的充值记录*/
	PageObject<RecPayUser> findAllActivityRecords(Integer pageCurrent, Integer pageSize);

}
