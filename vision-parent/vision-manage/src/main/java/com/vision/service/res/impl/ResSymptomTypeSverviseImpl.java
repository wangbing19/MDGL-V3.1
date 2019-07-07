package com.vision.service.res.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.vision.mapper.res.ResSymptomTypeMapper;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.pojo.res.ResSymptomType;
import com.vision.service.res.ResSymptomTypeSvervise;
import com.vision.vo.PageObject;
@Service
public class ResSymptomTypeSverviseImpl implements ResSymptomTypeSvervise{
	
	@Autowired
	ResSymptomTypeMapper resSymptomTypeMapper;
	
	/**分页查询资源配置*/
	@Override
	public PageObject<ResSymptomType> getSymptomTypeList(CusVo cusVo) {
		PageObject<ResSymptomType> pageObject = new PageObject<>();
		String title = cusVo.getTitle();
		if("".equals(title)) {
			title = null;
		}
		Integer pageCurrent = cusVo.getPageCurrent();
		Integer orgId = cusVo.getOrgId();
		Integer pageSize = cusVo.getPageSize();
		
		//2.依据条件获取总记录数并进行验证
		int rowCount = resSymptomTypeMapper.getRowCount(title, orgId);
		if(rowCount==0) {
			pageObject.setRowCount(rowCount);
			pageObject.setRecords(null);
			pageObject.setPageCurrent(pageCurrent);
			pageObject.setPageSize(pageSize);
			return pageObject;
		}
			
		int startIndex = (pageCurrent-1)*pageSize;
		List<ResSymptomType> records =
				resSymptomTypeMapper.findPageObjects(title, startIndex, pageSize, orgId);
		//4.对查询结果进行封装并返回
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);

		return pageObject;
	}

	/**
	 * 根据id查询该配置表信息
	 */
	@Override
	public ResSymptomType getSymptomTypeById(Integer id, Integer orgId) {
		QueryWrapper<ResSymptomType> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("org_id", orgId);
		queryWrapper.eq("is_delete", 0);
		ResSymptomType resSymptomType = resSymptomTypeMapper.selectOne(queryWrapper);
		return resSymptomType;
	}

	/**修改资源配置信息*/
	@Override
	public Integer updateSymptomType(ResSymptomType resSymptomType) {
		resSymptomType.setGmtCreate(new Date());
		int row = resSymptomTypeMapper.updateById(resSymptomType);
		return row;
	}

	/**
	 * 新增资源配置信息
	 */
	@Override
	public Integer addSymptomType(ResSymptomType resSymptomType) {
		resSymptomType.setGmtCreate(new Date());
		resSymptomType.setGmtModified(resSymptomType.getGmtCreate());
		int insert = resSymptomTypeMapper.insert(resSymptomType);
		return insert;
	}

	/**
	 * 根据id删除资源配置信息
	 */
	@Override
	public Integer deleteSymptomType(Integer id, Integer orgId) {
		ResSymptomType symptomType = new ResSymptomType();
		symptomType.setIsDelete(1);
		
		UpdateWrapper<ResSymptomType> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("id", id);
		updateWrapper.eq("org_id", orgId);
		
		int update = resSymptomTypeMapper.update(symptomType, updateWrapper);
		return update;
	}

	/**
	 * 查询门店下所有资源配置
	 * @param orgId
	 * @return	List<ResSymptomType>
	 */
	@Override
	public List<ResSymptomType> getSymptomTypeListByOrgId(Integer orgId) {
		QueryWrapper<ResSymptomType> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("org_id", orgId);
		queryWrapper.eq("is_delete", 0);
		queryWrapper.eq("state", 1);
		List<ResSymptomType> list = resSymptomTypeMapper.selectList(queryWrapper);
		return list;
	}

}
