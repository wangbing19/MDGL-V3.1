package com.vision.pojo.res;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("res_project_config")
public class ResProjectConfig {
	@TableId(type=IdType.AUTO)
	private Integer id;
	/** 门店用户id*/
	private Integer userId;
	/**症状名称*/
	private String projectName;
	/**症状描述*/
	private String projectChoose;
	/**是否生效*/
	private Integer projectState;
	/**表创建时间*/
	private Date gmtCreate;
	/**表修改时间*/
	private Date gmtModified;
}
