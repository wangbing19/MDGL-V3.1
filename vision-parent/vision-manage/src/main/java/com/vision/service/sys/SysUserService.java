package com.vision.service.sys;

import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.sys.SysUser;
import com.vision.pojo.sys.vo.SysUserOrganization;
import com.vision.vo.PageObject;

public interface SysUserService{

	int saveObject(SysUser entity, Integer[] roleIds);

	int updateObject(SysUser entity, Integer[] roleIds);

	int findObjectByColumn(String columnName, String columnValue);

	PageObject<SysUserOrganization> findPageObjects(Long organizationId,String username, Integer pageCurrent,Integer pageSize);

	int validById(Integer id, Integer valid, String string);

	Map<String, Object> findObjectById(Integer id);

	SysUser findUserById(Integer userId);

	SysUser findUserByName(String userName);

	SysUser findUserByIdWeb(Long userId);

    /**
     * @param column  列名
     * @param valueOf 列值
     * @return
     * @Author lihd
     * @Description 根据列名和列值获取账号信息
     * @Date 2019/9/2 19:56
     */
    SysUser findColumn(String column, String valueOf);
}
