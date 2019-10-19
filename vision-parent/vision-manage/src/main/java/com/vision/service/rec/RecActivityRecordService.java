package com.vision.service.rec;

import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.rec.RecPayUser;
import com.vision.vo.PageObject;

public interface RecActivityRecordService {
	/**分页查询所有的充值记录*/
	PageObject<RecPayUser> findAllActivityRecords(CusVo cusVo);	
	/**客户充值时新增新的充值记录*/
	int insertActivityRecord(CusCustomer cusCustomer, RecPayUser recPayUser);
	
	Integer deleteRecPayUserByCustomerId(Integer id, Integer orgId);
}
