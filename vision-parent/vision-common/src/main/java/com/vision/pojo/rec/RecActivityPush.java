package com.vision.pojo.rec;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@TableName("rec_activity_push")
public class RecActivityPush {
	@TableId(type=IdType.AUTO)
	private Long id;
	private Long userId;
	private Long parentId;
	/**标题*/
	private String title;
	/**充值金额*/
	private Double payAmount;
	/**赠送金额*/
	private Double presentedAmount;
	/**活动开始时间*/
	private Date activityStartTime;
	/**活动结束时间*/
	private Date activityEndTime;
	/**活动状态*/
	private Integer activityState;
	
	private Date gmtCreate;
	private Date gmtModified;
}
