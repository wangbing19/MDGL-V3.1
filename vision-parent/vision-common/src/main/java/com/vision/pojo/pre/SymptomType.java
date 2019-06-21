package com.vision.pojo.pre;

import com.baomidou.mybatisplus.annotation.TableName;
import com.vision.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("symptom_type")
public class SymptomType extends BasePojo{
	private Long id;
	private Long parentId;
	private Integer disStatus;
	private String symptomName;
	private Long symptomDescId;
}
