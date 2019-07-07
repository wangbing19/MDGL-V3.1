package com.vision.service.rec;

import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.rec.RecPayUser;
import com.vision.vo.PageObject;

public interface RecActivityRecordService {
	/**根据活动记录的id删除客户充值记录*/
	int deleteActivityRecordByid(Long id);
	/**分页查询所有的充值记录*/
	PageObject<RecPayUser> findAllActivityRecords(Integer pageCurrent, Integer pageSize);	
	/**客户充值时新增新的充值记录*/
	int insertActivityRecord(CusCustomer cusCustomer, RecPayUser recPayUser);
	/**基于用户id删除所有相关的充值记录*/
	Integer deleteRecPayUserByCustomerId(Integer customerId);
}
