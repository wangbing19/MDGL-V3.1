package com.vision.pojo.pre;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vision.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("symptom_desc")
public class SymptomDesc extends BasePojo {
	@TableId(type=IdType.AUTO)
	/**症状描述表主键id*/
	private Long id;
	/**症状id*/
	private Long symptomId;
	/**症状描述内容*/
	private String symptomDesc;
}
