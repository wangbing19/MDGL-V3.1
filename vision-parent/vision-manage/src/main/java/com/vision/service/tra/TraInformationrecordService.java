package com.vision.service.tra;

import java.util.List;

import com.vision.pojo.cus.CusSchedule;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.tra.TraInformationrecord;
import com.vision.vo.PageObject;

public interface TraInformationrecordService {
	/**训练记录分页及姓名查询*/
	PageObject<TraInformationrecord> getTraInfor(CusVo cusVo);
	/**添加训练记录到数据库*/
	Integer addTraInfor(TraInformationrecord entity);
	/**删除*/
	Integer deleteTraInfor(Integer id, Integer orgId);
	/**通过id查询*/
	TraInformationrecord getTraInforById(Integer id, Integer orgId);
	/**通过id修改训练表信息*/
	Integer updateTraInfor(TraInformationrecord entity);
	/**基于客户id查询用户课程表信息*/
	List<TraInformationrecord> getByCustomerId(CusVo cusVo);

}
