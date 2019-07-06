package com.vision.pojo.res;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@TableName("res_symptom_type")
public class ResSymptomType {
	/**症状类型实体类,封装实体数据*/
	@TableId(type=IdType.AUTO)
	private Integer id;
	private Integer orgId;
	/**服务名称*/
	private String title;
	/**描述*/
	private String describes;
	/**是否生效*/
	private Integer state;
	/**表创建时间*/
	private Date gmtCreate;
	/**表修改时间*/
	private Date gmtModified;
	 /**创建用户*/
    private String createdUser;
    /**修改用户*/
    private String modifiedUser;
	/**是否删除 0未删除，1已删除*/
    private Integer isDelete;
}
