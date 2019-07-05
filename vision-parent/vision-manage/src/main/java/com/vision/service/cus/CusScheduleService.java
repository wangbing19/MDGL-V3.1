package com.vision.service.cus;

import java.util.List;

import com.vision.pojo.cus.CusSchedule;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.vo.PageObject;



public interface CusScheduleService {

	/**基于用户/电话及当前页码值条件查询课程信息*/
	PageObject<CusSchedule> getSchedule(CusVo cusVo);
	/**基于id删除课程信息*/
	Integer deleteSchedule(Integer id, Integer orgId);
	/**基于id查询课程信息*/
	CusSchedule getScheduleById(Integer id, Integer orgId);
	/**创建客户课程表*/
	Integer addSchedule(CusSchedule cusSchedule);
	/**修改课程表数据*/
	Integer updateSchedule(CusSchedule cusSchedule);
	/**基于客户id查询用户课程表信息*/
	List<CusSchedule> getByCustomerId(CusVo cusVo);
	/**基于用户id删除课程信息*/
	Integer deleteScheduleByCustomerId(Integer customerId, Integer orgId);
}
