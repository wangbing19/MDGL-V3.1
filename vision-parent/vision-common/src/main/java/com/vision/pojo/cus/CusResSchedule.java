package com.vision.pojo.cus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)    //开启链式结构
@TableName("cus_res_shhedule")
public class CusResSchedule {

	@TableId(type = IdType.AUTO)
	/**id*/
	private int id;
	/**课程表id*/
	private Integer cusScheduleId;
	/**资源配置表id*/
	private Integer resSymptomId;
	
}
