package com.vision.pojo.tra;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("tra_informationrecord")
public class TraInformationrecord {
	/** 训练管理表id*/
	@TableId(type=IdType.AUTO)
	private Integer id;
	/**门店id*/
	private Integer orgId;
	/** 客户姓名*/
	private String name;
	/**客户电话*/
//	private String tel;
	/**左眼视力*/
	private Double lVision;
	/**右眼视力*/
	private Double rVision;
	/**训练评分*/
	private Integer grade;
	/**训练评价*/
	private String evaluate;
	/**训练内容*/
	private String content;
	/**导师*/
	private String tutor;
	/**训练结束时间*/
	private Date endTime;
	/**表创建时间*/
	private Date gmtCreate;
	/**表修改时间*/
	private Date gmtModified;
	/**客户id*/
	private Integer customerId;
	/**课程表id*/
	private Integer scheduleId;
	/**创建用户*/
    private String createdUser;
    /**修改用户*/
    private String modifiedUser;
}
