package com.vision.service.cus;

import com.vision.pojo.cus.CusCustomer;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.rec.RecPayUser;
import com.vision.pojo.tra.TraInformationrecord;
import com.vision.vo.PageObject;

public interface CusCustomerService {

	/**用户页面查看所有信息*/
	PageObject<CusCustomer> getCustomer(CusVo cusVo);
	/**基于客户id查询客户所有信息*/
	CusCustomer getCustomerById(Integer id, Integer orgId);
	/**基于用户id修改用户状态*/
	Integer updateCustomerState(CusVo cusVo);
	/**根据咨询表id查询客户表信息有无*/
	CusCustomer getCustomerByConsultationId(Integer consultationId);
	/**将CusCustomer类型数据添加到数据库*/
	Integer addCustomer(CusCustomer cusCustomer);
	/**基于id删除客户信息*/
	Integer deleteCustomer(Integer id, Integer orgId);
	/**基于客户id修改客户信息*/
	Integer updateCustomer(CusCustomer cusCustomer);
	/**基于用户id修改金额,余额及充值次数*/
	Integer updateObjectByMoney(RecPayUser recPayUser);
	/**基于训练记录表返回信息更改训练次数*/
	Integer updateObjectByTimesOfTraining(TraInformationrecord entity);
}
