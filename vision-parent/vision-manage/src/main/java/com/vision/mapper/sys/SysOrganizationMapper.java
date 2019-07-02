package com.vision.mapper.sys;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.sys.SysOrganization;

public interface SysOrganizationMapper extends BaseMapper<SysOrganization>{

	List<SysOrganization> selectOrganizationIdList(List<Long> list);

}
