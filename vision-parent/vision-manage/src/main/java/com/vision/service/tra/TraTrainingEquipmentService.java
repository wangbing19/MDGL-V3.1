package com.vision.service.tra;

import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.tra.TraTrainingEquipment;
import com.vision.vo.PageObject;

public interface TraTrainingEquipmentService {
	/**训练记录分页及姓名查询*/
	PageObject<TraTrainingEquipment> getTraInfor(CusVo cusVo);
	/**添加训练记录到数据库*/
	Integer addTraInfor(TraTrainingEquipment entity);
	/**删除*/
	Integer deleteTraInfor(Integer id, Integer orgId);
	/**通过id查询*/
	TraTrainingEquipment getTraInforById(Integer id, Integer orgId);
	/**通过id修改训练表信息*/
	Integer updateTraInfor(TraTrainingEquipment entity);

}
