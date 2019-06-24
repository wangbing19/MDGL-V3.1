package com.vision.pojo.cus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)    //开启链式结构
@TableName("cus_consultation")
public class CusConsultation {

    @TableId(type = IdType.AUTO)
    private Integer id; /**序号*/
    /**门店id*/
    private Integer orgId;
    /**姓名*/
    private String name;
    /**年龄*/
    private Integer age;
    /**性别*/
    private String gender;
    /**电话*/
    private String tel;
    /**左右眼*/
    private String eye;
    /**视力下降时间(年)*/
    private String declineTimeYear;
    /**视力下降时间(月)*/
    private String declineTimeMonth;
    /**诊断*/
    private String diagnose;
    /**眼病*/
    private String oculopathy;
    /**其他眼病*/
    private String oculopathyOther;
    /**矫正方法*/
    private String correctionMethod;
    /**效果*/
    private String effect;
    /**现视力情况*/
    private String visualAcuity;
    /**父视力情况*/
    private String fVisionCondition;
    /**父其他*/
    private String fOther;
    /**母视力情况*/
    private String mVisionCondition;
    /**母其他*/
    private String mOther;
    /**睡眠时间*/
    private String sleepingTime;
    /**用眼项目*/
    private String eyeProject;
    /**用眼项目其他*/
    private String eyeProjectOther;
    /**每次看书、作业的时长*/
    private String readingTime;
    /**看书距离*/
    private String readingDistance;
    /**单次看电视时长*/
    private String watchingTime;
    /**看电视距离*/
    private String watchingDistance;
    /**用眼姿势*/
    private String eyePosition;
    /**用眼姿势其他*/
    private String eyePositionOther;
    /**家庭环境光线*/
    private String homeLightingEnvironment;
    /**教室环境光线*/
    private String classroomLightingEnvironment;
    /**裸眼远视力（5米）： 右眼*/
    private Double rD;
    /**裸眼远视力（5米）： 左眼*/
    private Double lD;
    /**矫正远视力（5米）: 右眼*/
    private Double rCva;
    /**矫正远视力（5米）: 左眼*/
    private Double lCva;
    /**训练导师*/
    private String tutor;
    /**建表时间*/
    private Date gmtCreate;
    /**修改时间*/
    private Date gmtModified;
    /**创建用户*/
    private String createdUser;
    /**修改用户*/
    private String modifiedUser;

}
