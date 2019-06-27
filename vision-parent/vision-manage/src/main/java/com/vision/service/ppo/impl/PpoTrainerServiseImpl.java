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
import com.vision.vo.PageObject;
@Service
public class PpoTrainerServiseImpl implements PpoTrainerService{
	@Autowired
	private PpoTrainerMapper ppoTrainerMapper;
	private PpoAppointmentTimeMapper ppoAppointmentTimeMapper;
	
	
	/**
	 * 添加训练师
	 */
	@Override
	public int savePpoTrainer(PpoTrainer ppoTrainer) {
		
		ppoTrainer.setCreateTime(new Date());
		ppoTrainer.setModifiedTime(ppoTrainer.getCreateTime());
		int insert = ppoTrainerMapper.insert(ppoTrainer);
		return insert;
	}



	@Override
	public PageObject<PpoTrainer> findPpoTrainerAll(String trainerName, Integer pageCurrent, Integer pageSize,
			Long organizationId) {
		
			 if(pageCurrent==null||pageCurrent<=0) throw new ServiceException("参数不合法");
		
		 
		QueryWrapper<PpoTrainer> queryWrapper = new QueryWrapper<PpoTrainer>();
		queryWrapper.eq("organization_id", organizationId);
		Integer pageCount = ppoTrainerMapper.selectCount(queryWrapper);
		int startIndex=(pageCurrent-1)*pageSize;
		List<PpoTrainer> findPpoTrainerAll = ppoTrainerMapper.findPpoTrainerAll(trainerName, startIndex, pageSize, organizationId);
		PageObject<PpoTrainer> pageObject = new PageObject<PpoTrainer>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCount(pageCount);
		pageObject.setRecords(findPpoTrainerAll);
		return pageObject;
	}



	@Override
	public int doUpdatePpoTrainer(PpoTrainer ppoTrainer) {
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
		return null;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ppoAppointmentTime.setCreateTime(new Date());
		ppoAppointmentTime.setModifiedTime(ppoAppointmentTime.getCreateTime());
		int insert = ppoAppointmentTimeMapper.insert(ppoAppointmentTime);
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




	
}
