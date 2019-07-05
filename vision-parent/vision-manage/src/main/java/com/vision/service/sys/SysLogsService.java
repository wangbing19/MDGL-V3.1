package com.vision.service.sys;

import com.vision.pojo.sys.SysLogs;
import com.vision.pojo.sys.vo.SysOrganizationLogs;
import com.vision.vo.PageObject;

public interface SysLogsService {

	int insertLogs(SysLogs sysLogs);

	PageObject<SysOrganizationLogs> findLogs(Long organizationId, Integer pageCurrent, Integer pageSize);

}
