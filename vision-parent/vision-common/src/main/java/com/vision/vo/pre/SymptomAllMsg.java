package com.vision.vo.pre;



import com.vision.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
public class SymptomAllMsg extends BasePojo{
	private Long id;
	private Long parentId;
	private String symptomName;
	private String symptomParentName;
}
