package com.vision.service.pre.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.exception.ServiceException;
import com.vision.mapper.pre.SymptomDescMapper;
import com.vision.mapper.pre.SymptomTypeMapper;
import com.vision.pojo.pre.SymptomDesc;
import com.vision.pojo.pre.SymptomType;
import com.vision.pojo.sys.vo.SysOrganizationLogs;
import com.vision.service.pre.SymptomTypeService;
import com.vision.vo.PageObject;
import com.vision.vo.pre.SymptomAllMsg;
@Service
public class SymptomTypeServiceImpl implements SymptomTypeService{
	@Autowired
	private SymptomTypeMapper symptomTypeMapper;
	@Autowired
	private SymptomDescMapper symptomDescMapper;
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

	@Override
	/**根据症状id删除症状*/
	public int deleteSymptomObjectById(Long[] ids) {
		try {
			if(ids==null) throw new ServiceException("请先选择要删除的对象");
			for (int i = 0; i < ids.length; i++) {
				
				//1、根据症状id查询该症状信息
				SymptomType symptomType = symptomTypeMapper.findSymptomObjectById(ids[i]);
				if(symptomType==null) throw new ServiceException("您要删除的信息不存在");
				//2、获取需要删除的症状的父级id
				Long parentId = symptomType.getParentId();
				//3、判断该症状有没有子症状
				int row = symptomTypeMapper.getRowDataById(ids[i]);
				//3.1)如果有，则不能删除
				if(row!=0) return 0;
				//3.2)如果没有，则直接删除
				symptomTypeMapper.deleteSymptomObjectById(ids[i]);
				//4、根据parent_id查询是否还存在子症状
				int rowParent = symptomTypeMapper.getRowDataById(parentId);
				if(rowParent==0) {
					//如果没有子症状，则更新症状描述显示
					symptomTypeMapper.updateDisStatusById(parentId, 1);
				}						
				//5、删除症状后，判断症状描述表中有无该症状描述，有则删除
				symptomDescMapper.deleteDescObjectBySymptomId(ids[i]);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	/**新增症状类型对象*/
	public void insertSymptomObject(SymptomType symptomType ,String desc) {
		try {
			//1将新的症状对象新增到数据库中
			Date nowDate = new Date();
		//	SymptomType symptomType = new SymptomType();
			symptomType.setCreateTime(nowDate);
			symptomType.setModifiedTime(symptomType.getCreateTime());
			symptomType.setDisStatus(1);//新增的症状类型是最下级目录，没有子症状，所有默认需要显示症状描述
			//symptomType.setParentId(parentId);
			//symptomType.setSymptomName(symptomName);
			symptomTypeMapper.insertSymptomObject(symptomType);
			symptomTypeMapper.insert(symptomType);
			
			//2、更新parent_id对应的症状显示症状描述状态为0
			int disStatus=0;
			symptomTypeMapper.updateDisStatusById(symptomType.getParentId(),disStatus);
			//3、新增症状描述表对象
			SymptomDesc symptomDesc = new SymptomDesc();
			symptomDesc.setCreateTime(nowDate);
			symptomDesc.setModifiedTime(symptomDesc.getCreateTime());
			symptomDesc.setSymptomId(symptomType.getId());
			symptomDesc.setSymptomDesc(desc);
			symptomDescMapper.insertSymptomDescObject(symptomDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	/**修改症状类型对象*/
	public void updateSymptomObject(SymptomType symptomType,String desc) {
		//获取症状id
		Long id = symptomType.getId();
		//获取修改后的症状名称
		String symptomName = symptomType.getSymptomName();
		
		try {
			
			symptomTypeMapper.updateById(symptomType);
			//修改症状类型表中症状名称
			//symptomTypeMapper.updateSymptomObject(id,symptomName);
			//修改症状描述表中该症状的描述信息
			symptomDescMapper.updateSymptomDescObject(id,desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	/**根据症状id查询症状类型信息*/
	public SymptomType findSymptomObjectById(Long id) {
		try {
			SymptomType symptomType = symptomTypeMapper.findSymptomObjectById(id);
			QueryWrapper<SymptomDesc> queryWrapper = new QueryWrapper<SymptomDesc>();
			queryWrapper.eq("symptom_id", id);
			SymptomDesc selectOne = symptomDescMapper.selectOne(queryWrapper);
			symptomType.setDesc(selectOne.getSymptomDesc());
			return symptomType;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PageObject<SymptomType> findAllObjectsList(Integer pageCurrent,Integer pageSize) {
	
		int startIndex=(pageCurrent-1)*pageSize;
		Integer rowCount = symptomTypeMapper.selectCount(null);
		//List<SymptomType> selectList = symptomTypeMapper.selectList(null);
		List<SymptomType> selectList=symptomTypeMapper.findAllObjectsList( startIndex, pageSize);
		PageObject<SymptomType> pageObject=new PageObject<>();
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(selectList);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		return pageObject;
	}
}
