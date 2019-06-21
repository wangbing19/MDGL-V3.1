package com.vision.service.pre.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.mapper.pre.SymptomTypeMapper;
import com.vision.service.pre.SymptomTypeService;
import com.vision.vo.pre.SymptomAllMsg;
@Service
public class SymptomTypeServiceImpl implements SymptomTypeService{
	@Autowired
	private SymptomTypeMapper symptomTypeMapper;

	@Override
	/**内嵌套查询症状类型数据**/
	public List<SymptomAllMsg> findAllObjects() {
		
		try {
			List<SymptomAllMsg> symptomList = symptomTypeMapper.findAllObjects();
			if(symptomList==null||symptomList.size()==0) {
				return null;
			}
			return symptomList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
