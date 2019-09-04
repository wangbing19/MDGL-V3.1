package com.vision.mapper.pre;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.ppo.PpoAppointmentOrder;
import com.vision.pojo.pre.SymptomType;
import com.vision.vo.pre.SymptomAllMsg;

public interface SymptomTypeMapper extends BaseMapper<SymptomType>{
	/**内嵌套查询症状类型数据**/
	List<SymptomAllMsg> findAllObjects();
	/**根据症状id删除症状*/
	void deleteSymptomObjectById(@Param("id")Long id);
	/**判断该症状子症状个数*/
	int getRowDataById(@Param("id")Long id);
	/**新增症状类型对象*/
	void insertSymptomObject(SymptomType symptomType);
	/**根据症状id更新症状描述显示状态*/
	void updateDisStatusById(@Param("parentId")Long parentId,@Param("disStatus")int disStatus);
	/**根据症状id查询该症状信息*/
	SymptomType findSymptomObjectById(@Param("id")Long id);
	/**根据症状id修改症状名称*/
	void updateSymptomObject(@Param("id")Long id,@Param("symptomName") String symptomName);
	List<SymptomType> findAllObjectsList(@Param("startIndex")int startIndex, @Param("pageSize")Integer pageSize);
	

}
