package com.vision.pojo.cus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 客户信息表
 * @author tarena
 *
 */
@Data
@Accessors(chain = true)    //开启链式结构
@TableName("cus_customer")
public class CusCustomer {

	/**课程记录数*/
	@TableField(exist=false)	//mybatis入库操作时,忽略该字段
	private int scheduleCount;
	
    @TableId(type = IdType.AUTO)
    private Integer id; /**序号*/
    /**门店id*/
    private Integer orgId;
    /**姓名*/
    private String name;
    /**性别*/
    private String gender;
    /**年龄*/
    private Integer age;
    /**生日*/
    private Date birthday;
    /**联系电话*/
    private String tel;
    /**状态*/
    private Integer state;
    /**监护人*/
    private String guardian;
    /**次监护人*/
    private String lastGuardian;
    /**次监护人电话*/
    private String lastGuardianTel;
    /**家庭住址*/
    private String home;
    /**学校地址*/
    private String school;
    /**总金额*/
    private Double money;
    /**余额*/
    private Double balance;
    /**总训练次数*/
    private Integer totalTrainingTime;
    /**已训练次数*/
    private Integer timesOfTraining;
    /**备注*/
    private String remark;
    /**咨询表id*/
    private Integer consultationId;
    /**诊断表id*/
    private Integer diagnoseId;
    /**充值记录数*/
	private Integer rechargeCount;
	/**上次训练时间*/
	private Date lastTrain;
    /**建表时间*/
    private Date gmtCreate;
    /**修改时间*/
    private Date gmtModified;
    /**创建用户*/
    private String createdUser;
    /**修改用户*/
    private String modifiedUser;

}
