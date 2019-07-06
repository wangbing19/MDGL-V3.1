package com.vision.pojo.rec;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vision.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@TableName("rec_pay_user")
public class RecPayUser extends BasePojo{
	@TableId(type=IdType.AUTO)
	private Long id;
	/**门店id*/
	private Long userId;
	/**组织id*/
	private Long orgId;
	/**客户姓名*/
	private String name;
	/**客户id*/
	private Integer customerId;
	/**活动类型表id*/
	private Long rechargeType;
	/**客户账户金额*/
	private Double money;
	/**客户充值金额*/
	private Double rechargeAmount;
	/**赠送金额*/
	private Double presentedAmount;
	/**充值的次数*/
	private Integer practiceTimes;
	/**上次充值时间*/
	private Date lastPayTime;
	/*
	 * @TableField(exist=false) private String title;
	 */
}
