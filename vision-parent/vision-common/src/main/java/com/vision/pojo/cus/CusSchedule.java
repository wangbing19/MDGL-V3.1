package com.vision.pojo.cus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)    //开启链式结构
@TableName("cus_schedule")
public class CusSchedule {

	/**课程表训练项目id*/
	@TableField(exist=false)	//mybatis入库操作时,忽略该字段
	private Integer[] symptomTypes;
	
    @TableId(type = IdType.AUTO)
    private Integer id; /**id*/
    /**客户id*/
    private Integer customerId;
    /**客户姓名*/
    private String name;
    /**门店id*/
    private Integer orgId;
    /**课程名称*/
    private String courseTitle;
    /**课程价格*/
    private Double priceOfCourse;
    /**课程训练数*/
    private Integer courseTraining;
    /**课程项目数*/
    private Integer numberOfCourse;
    /**总价格*/
    private Double totalPrice;
    /**诊断师*/
    private String diagnostics;
    /**建表时间*/
    private Date gmtCreate;
    /**修改时间*/
    private Date gmtModified;
    /**创建用户*/
    private String createdUser;
    /**修改用户*/
    private String modifiedUser;
    

}
