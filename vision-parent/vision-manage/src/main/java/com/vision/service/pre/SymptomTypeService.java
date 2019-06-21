package com.vision.service.pre;

import java.util.List;

import com.vision.vo.pre.SymptomAllMsg;

public interface SymptomTypeService {
	/**内嵌套查询症状类型数据**/
	List<SymptomAllMsg> findAllObjects();

}
