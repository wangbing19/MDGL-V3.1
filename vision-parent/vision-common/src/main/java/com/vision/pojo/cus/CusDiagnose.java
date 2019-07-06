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
@TableName("cus_diagnose")
public class CusDiagnose {

	/**客户姓名*/
	@TableField(exist=false)	//mybatis入库操作时,忽略该字段
	private String customerName;
	/**客户电话*/
	@TableField(exist=false)	//mybatis入库操作时,忽略该字段
	private String customerTel;
	
	/**客户姓名*/
	@TableField(exist=false)	//mybatis入库操作时,忽略该字段
	private String name;
	/**客户电话*/
	@TableField(exist=false)	//mybatis入库操作时,忽略该字段
	private String tel;
	
	
    @TableId(type = IdType.AUTO)
    private int id; /**序号*/
    /**门店id*/
    private Integer orgId;
    /**客户表id*/
    private Integer customerId;
    /**右眼球面镜（近视）*/
    private Double rDs;
    /**右眼柱面镜（散光）*/
    private Double rDc;
    /**右眼x轴*/
    private Double rX;
    /**右眼屈光力（度数）*/
    private Double rD;
    /**左眼球面镜（近视）*/
    private Double lDs;
    /**左眼柱面镜（散光）*/
    private Double lDc;
    /**左眼x轴*/
    private Double lX;
    /**左眼屈光力（度数）*/
    private Double lD;
    /**瞳距*/
    private Double pupilDistance;
    /**右眼镜度数*/
    private Double rGlassesD;
    /**右眼镜散光*/
    private Double rGlassesDc;
    /**左眼镜度数*/
    private Double lGlassesD;
    /**左眼镜散光*/
    private Double lGlassesDc;
    /**眼位检查*/
    private String eyePositionExamination;
    /**同视视*/
    private String synoptophore;
    /**融合视*/
    private String fusionvision;
    /**立体视*/
    private String stereopsis;
    /**屈光不正*/
    private String ametropia;
    /**弱视程度*/
    private String amblyopiaDegree;
    /**弱视性质*/
    private String amblyopia;
    /**弱视预后*/
    private String amblyopiaPrognosis;
    /**斜视*/
    private String strabismus;
    /**视光其他诊断*/
    private String visionOther;
    /**训练过程家长配合事项*/
    private String parentCooperationDuringTraining;
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
