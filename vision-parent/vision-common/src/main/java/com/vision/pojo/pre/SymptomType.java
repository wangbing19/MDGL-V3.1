package com.vision.pojo.pre;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vision.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("symptom_type")
public class SymptomType extends BasePojo{
	@TableId(type=IdType.AUTO)
	/**症状类型表主键id*/
	private Long id;
	/**父级症状id*/
	private Long parentId;
	/**症状描述显示状态1 显示 0不显示*/
	private Integer disStatus;
	/**是否有处方状态1 有 0无*/
	private Integer descStart;
	
	/**症状名称*/
	private String symptomName;
}
