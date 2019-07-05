package com.vision.service.sys.imp;

import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vision.exception.ServiceException;
import com.vision.mapper.sys.SysLogsMapper;
import com.vision.pojo.sys.SysLogs;
import com.vision.pojo.sys.vo.SysOrganizationLogs;
import com.vision.service.sys.SysLogsService;
import com.vision.service.tool.ToolOrganizationIdList;
import com.vision.vo.PageObject;
@Service
public class SysLogsServiceImpl implements SysLogsService{
	@Autowired
	private SysLogsMapper sysLogsMapper;
	@Autowired
	private ToolOrganizationIdList toolOrganizationIdList;
	@Override
	public int insertLogs(SysLogs sysLogs) {
		if(sysLogs.getOrganizationId() == null) {
			throw new ServiceException("组织id不能为空！");
		}
		if(sysLogs.getTime() ==  null ) {
			sysLogs.setTime(0L);
		}
		int insert = sysLogsMapper.insert(sysLogs);
		return insert;
	}
	@Override
	public PageObject<SysOrganizationLogs> findLogs(Long organizationId, Integer pageCurrent, Integer pageSize) {
		if(organizationId == null) {
			throw new ServiceException("组织id不能为空！");
		}
		List<Long> findOrganizationIdList = toolOrganizationIdList.findOrganizationIdList(organizationId);
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysOrganizationLogs> findAllLogs = sysLogsMapper.findAllLogs(findOrganizationIdList,startIndex,pageSize);
		
		//List<SysLogs> result = sysLogsMapper.findLogs(name,startIndex,pageSize);
	//	int rowCount = sysLogsMapper.getRowCount(name);
		int rowCount = sysLogsMapper.findSysLogCount(findOrganizationIdList);
		PageObject<SysOrganizationLogs> pageObject=new PageObject<>();
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(findAllLogs);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		return pageObject;
	}

}
