package com.vision.mapper.pre;

import java.util.List;

import com.vision.vo.pre.SymptomAllMsg;

public interface SymptomTypeMapper {
	/**内嵌套查询症状类型数据**/
	List<SymptomAllMsg> findAllObjects();

}
