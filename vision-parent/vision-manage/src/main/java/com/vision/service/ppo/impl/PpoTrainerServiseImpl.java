package com.vision.service.ppo.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.exception.ServiceException;
import com.vision.mapper.ppo.PpoAppointmentTimeMapper;
import com.vision.mapper.ppo.PpoTrainerMapper;
import com.vision.pojo.ppo.PpoAppointmentTime;
import com.vision.pojo.ppo.PpoTrainer;
import com.vision.service.ppo.PpoTrainerService;
import com.vision.service.tool.ToolOrganizationIdList;
import com.vision.vo.PageObject;
@Service
public class PpoTrainerServiseImpl implements PpoTrainerService{
	@Autowired
	private PpoTrainerMapper ppoTrainerMapper;
	@Autowired
	private PpoAppointmentTimeMapper ppoAppointmentTimeMapper;
	@Autowired
	private ToolOrganizationIdList toolOrganizationIdList;

	/**
	 * 添加训练师
	 */
	@Override
	public int savePpoTrainer(PpoTrainer ppoTrainer) {
		if(ppoTrainer.getOrganizationId()==null||ppoTrainer.getOrganizationId()<0) {
			throw new ServiceException("组织id不能为空或id错误");
		} 
		if(ppoTrainer.getTrainerName()==null) {
			throw new ServiceException("训练师名称不能为空");
		} 
		if(ppoTrainer.getPost()==null) {
			throw new ServiceException("训练师名称职位不能空");
		}
		ppoTrainer.setTrainerState(1);
		ppoTrainer.setCreateTime(new Date());
		ppoTrainer.setModifiedTime(ppoTrainer.getCreateTime());
		int insert = ppoTrainerMapper.insert(ppoTrainer);
		return insert;
	}



	@Override
	public PageObject<PpoTrainer> findPpoTrainerAll(String trainerName, Integer pageCurrent, Integer pageSize,
			Long organizationId) {

		if(pageCurrent==null||pageCurrent<=0) throw new ServiceException("参数不合法");
		if(organizationId==null||organizationId<=0) throw new ServiceException("组织id不能为空或id错误");

		QueryWrapper<PpoTrainer> queryWrapper = new QueryWrapper<PpoTrainer>();
		
		Integer pageCount = ppoTrainerMapper.selectCount(queryWrapper);
		int startIndex=(pageCurrent-1)*pageSize;
		List<Long> findOrganizationIdList = toolOrganizationIdList.findOrganizationIdList(organizationId);
		List<PpoTrainer> findPpoTrainerAll = ppoTrainerMapper.findPpoTrainerAll(trainerName, startIndex, pageSize, findOrganizationIdList);
		PageObject<PpoTrainer> pageObject = new PageObject<PpoTrainer>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCount(pageCount);
		pageObject.setRecords(findPpoTrainerAll);
		return pageObject;
	}



	@Override
	public int doUpdatePpoTrainer(PpoTrainer ppoTrainer) {
		if(ppoTrainer.getOrganizationId()==null||ppoTrainer.getOrganizationId()<0) {
			throw new ServiceException("组织id不能为空或id错误");
		} 
		if(ppoTrainer.getTrainerName()==null) {
			throw new ServiceException("训练师名称不能为空");
		} 
		if(ppoTrainer.getPost()==null) {
			throw new ServiceException("训练师名称职位不能空");
		}
		ppoTrainer.setModifiedTime(new Date());
		int updateById = ppoTrainerMapper.updateById(ppoTrainer);
		return updateById;
	}



	@Override
	public Integer savePpoTrainer(Integer ppoAppointmentId) {
		if(ppoAppointmentId == null) {
			throw new ServiceException("参数不合法");
		}
		int deleteById = ppoTrainerMapper.deleteById(ppoAppointmentId);
		return deleteById;
	}



	@Override
	public Integer doInsertAppointmentTime(PpoAppointmentTime ppoAppointmentTime)  {
		if(ppoAppointmentTime ==null)
			throw new ServiceException("保存数据不能为空");
		if(ppoAppointmentTime.getTarinerId() == null)
			throw new ServiceException("训练师id不能为空");
		if(ppoAppointmentTime.getAppointmentTime() == null ) {
			throw new ServiceException("训练师训练开始时间或结束时间不能为空");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String format = sdf.format(ppoAppointmentTime.getAppointmentTime()); 
		try { 
			Date parse = sdf.parse(format);
			ppoAppointmentTime.setCreateTime(parse); 
		} 
		catch(Exception e) 
		{
			e.printStackTrace(); 
		}
	
		ppoAppointmentTime.setAppointmentTimeState(1);
		ppoAppointmentTime.setCreateTime(new Date());
		System.out.println(ppoAppointmentTime.getCreateTime());
		ppoAppointmentTime.setModifiedTime(ppoAppointmentTime.getCreateTime());
		int insert = ppoAppointmentTimeMapper.insertAppointmentTime(ppoAppointmentTime);
		return insert;

	}



	@Override
	public List<PpoAppointmentTime> dofindappointmentTime(Long tarinerId) {
		if(tarinerId ==null)
			throw new ServiceException("训练师id不能为空");
		QueryWrapper<PpoAppointmentTime> queryWrapper = new QueryWrapper<PpoAppointmentTime>();
		queryWrapper.eq("tariner_id", tarinerId);
		List<PpoAppointmentTime> selectList = ppoAppointmentTimeMapper.selectList(queryWrapper);
		return selectList;
	}



	@Override
	public int dodeleteAppointmentTime(Long id) {
		int deleteById = ppoAppointmentTimeMapper.deleteById(id);
		return deleteById;
	}



	@Override
	public int updateAppointmentTime(PpoAppointmentTime ppoAppointmentTime) {
		if(ppoAppointmentTime ==null)
			throw new ServiceException("保存数据不能为空");
		if(ppoAppointmentTime.getTarinerId() == null)
			throw new ServiceException("训练师id不能为空");
		if(ppoAppointmentTime.getAppointmentTime() == null ) {
			throw new ServiceException("训练师训练开始时间或结束时间不能为空");
		}
		if(ppoAppointmentTime.getId() == null ) {
			throw new ServiceException("训练师训练开始时间或结束时间主键不能为空");
		}
		int updateById = ppoAppointmentTimeMapper.updateById(ppoAppointmentTime);
		return updateById;
	}



	@Override
	public Integer dodeletePpoTrainer(Integer tarinerId) {
		if(tarinerId ==null)
			throw new ServiceException("训练师id不能为空");
		int deleteById = ppoTrainerMapper.deleteById(tarinerId);
		return deleteById;
	}





}
