package com.vision.service.pre;

import com.vision.pojo.pre.SymptomDesc;

public interface SymptomDescService {
	/**根据症状id查询症状描述信息*/
	SymptomDesc findSymptomDescObjectByid(Long id);

}
