package com.vision.pojo.tra;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("tra_training_equipment")
public class TraTrainingEquipment {
	/** 训练项目配置表id*/
	@TableId(type=IdType.AUTO)
	private Integer id;
	/**门店id*/
	private Integer orgId;
	/**项目名称*/
	private String title;
	/**项目描述*/
	private String describe;
	/**单次价格（元/次）*/
	private Double singlePrice;
	/**状态：1启用，2停用*/
	private Integer state;
	/**创建时间*/
	private Date gmtCreate;
	/**修改时间*/
	private Date gmtModified;
	/**创建用户*/
	private String createdUser;
	/**修改用户*/
	private String modifiedUser;
}
