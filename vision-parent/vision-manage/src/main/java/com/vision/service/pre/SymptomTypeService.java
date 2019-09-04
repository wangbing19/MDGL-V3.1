package com.vision.service.pre;

import java.util.List;

import com.vision.pojo.pre.SymptomType;
import com.vision.vo.PageObject;
import com.vision.vo.pre.SymptomAllMsg;

public interface SymptomTypeService {
	/**内嵌套查询症状类型数据**/
	List<SymptomAllMsg> findAllObjects();
	/**根据症状id删除症状
	 * @return */
	int deleteSymptomObjectById(Long id);
	/**新增症状类型对象*/
	void insertSymptomObject(String symptomName, Long parentId,String desc);
	/**修改症状类型对象*/
	void updateSymptomObject(SymptomType symptomType,String symptomDesc);
	/**根据症状id查询症状类型信息*/
	SymptomType findSymptomObjectById(Long id);
	PageObject<SymptomType> findAllObjectsList(Integer pageCurrent,Integer pageSize);

}
