package com.vision.vo.pre;



import com.vision.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
public class SymptomAllMsg extends BasePojo{
	/**症状id*/
	private Long id;
	/**父级症状id*/
	private Long parentId;
	/**症状描述显示状态1 显示 0不显示*/
	private Integer disStatus;
	/**症状名称*/
	private String symptomName;
	/**父级症状名称*/
	private String symptomParentName;
}
