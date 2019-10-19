package com.vision.pojo;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true) //链式加载
public class BaseNewPojo {
	private Date gmtCreate; //创建时间
	private Date gmtModified; //修改时间
}
