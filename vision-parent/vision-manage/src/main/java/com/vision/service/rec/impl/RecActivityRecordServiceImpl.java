package com.vision.service.rec.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.controller.rec.RecActivityUserRecord;
import com.vision.exception.ServiceException;
import com.vision.mapper.rec.RecActivityRecordMapper;
import com.vision.pojo.rec.RecPayUser;
import com.vision.service.rec.RecActivityRecordService;
import com.vision.service.tool.ToolOrganizationIdList;
import com.vision.vo.PageObject;

@Service
public class RecActivityRecordServiceImpl implements RecActivityRecordService{
	@Autowired
	private RecActivityRecordMapper recActivityRecordMapper;
    @Autowired
    private ToolOrganizationIdList toolOrganizationIdList;
	@Override
	/**根据活动记录的id删除客户充值记录*/
	public int deleteActivityRecordByid(Long id) {
		try {
			RecPayUser recPayUser = recActivityRecordMapper.findRecActivityRecordById(id);
			if(recPayUser==null) {
				return 2;			
			}
			//查看要删除的客户充值记录属于哪个门店下的
			Long userId = recPayUser.getUserId();
			//获取当前登录用户的id
			//杜健华假设
			Long nowUserId = 2L;
			if(userId!=nowUserId) {
				throw new ServiceException("没有权限删除,该客户记录不是本店充值的记录");
			}else if(userId.equals(nowUserId)){
				recActivityRecordMapper.deleteActivityRecordByid(id);
				return 1;
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	/**分页查询所有的充值记录*/
	//pageCurrent 当前页的页码  pageSize页面大小（显示几条数据）
	public PageObject<RecPayUser> findAllActivityRecords(Integer pageCurrent, Integer pageSize) {
		if(pageCurrent==null||pageCurrent<=0 && pageSize==null||pageSize<=0 ) {
			throw new ServiceException("参数不合法");
		}
		int startIndex=(pageCurrent-1)*pageSize;//计算当前页起始下标
		//杜健华假设
		Long userId = 1L;
		Long orgId = 2L;
		//查询分页查询的数据的总条数
		int rowCount = 1;
		rowCount = recActivityRecordMapper.getPageCount(userId,orgId);	
		//分页查询所有记录
		//根据门店id获取所有子门店的id
		toolOrganizationIdList.findOrganizationIdList(orgId);
		List<RecPayUser> records = recActivityRecordMapper.findLimitRecActivityRecords(startIndex,pageSize);
		PageObject<RecPayUser> pageObject = new PageObject<>();
		//设置总页数
		pageObject.setPageCount(rowCount/pageSize+1);
		//设置当前页数
		pageObject.setPageCurrent(pageCurrent);
		//设置页面显示的条数（大小）
		pageObject.setPageSize(pageSize);
		//设置总记录数
		pageObject.setRowCount(rowCount);
		//设置查询记录
		pageObject.setRecords(records);
		return pageObject;
	}
}
