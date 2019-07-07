package com.vision.service.res;


import java.util.List;

import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.res.ResSymptomType;
import com.vision.vo.PageObject;

public interface ResSymptomTypeSvervise {

	/**分页查询资源配置*/
	PageObject<ResSymptomType> getSymptomTypeList(CusVo cusVo);
	/**根据id查询该配置表信息
	 * @param orgId */
	ResSymptomType getSymptomTypeById(Integer id, Integer orgId);
	/**修改资源配置信息*/
	Integer updateSymptomType(ResSymptomType resSymptomType);
	/**新增资源配置信息*/
	Integer addSymptomType(ResSymptomType resSymptomType);
	/**根据id删除资源配置信息*/
	Integer deleteSymptomType(Integer id, Integer orgId);
	/**查询门店下所有资源配置*/
	List<ResSymptomType> getSymptomTypeListByOrgId(Integer orgId);
	

}
