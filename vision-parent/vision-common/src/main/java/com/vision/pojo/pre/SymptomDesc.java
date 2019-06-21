package com.vision.pojo.pre;

import org.apache.commons.pool2.BaseObject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("symptom_desc")
public class SymptomDesc extends BaseObject {
	@TableId(type=IdType.AUTO)
	private Long id;
	private Long symptomId;
	private String desc;
}
