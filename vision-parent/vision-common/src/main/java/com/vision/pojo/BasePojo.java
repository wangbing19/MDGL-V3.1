package com.vision.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

//pojo基类，完成2个任务，2个日期，实现序列化
@Data
@Accessors(chain=true) //链式加载
public class BasePojo implements Serializable{
	private Date createTime; //创建时间
	private Date modifiedTime; //修改时间
}
