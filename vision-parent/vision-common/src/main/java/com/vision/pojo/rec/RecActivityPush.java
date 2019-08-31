package com.vision.pojo.rec;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vision.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@TableName("rec_activity_push")
public class RecActivityPush extends BasePojo{
	@TableId(type=IdType.AUTO)
	/**充值活动表主键id*/
	private Long id;
	/**门店id*/
	private Long orgId;
	/**充值活动标题*/
	private String title;
	/**描述*/
	private String describe;
	/**充值金额*/
	private Double payAmount;
	/**赠送金额*/
	private Double presentedAmount;
	/**活动开始时间*/
	private Date activityStartTime;
	/**活动结束时间*/
	private Date activityEndTime;
	/**活动状态 1表示活动正在进行  0表示活动已经结束或者没有开始*/
	private Integer activityState;
}
