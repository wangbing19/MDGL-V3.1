package com.vision.service.sys.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.exception.ServiceException;
import com.vision.mapper.sys.SysOrganizationMapper;
import com.vision.mapper.sys.SysUserMapper;
import com.vision.mapper.sys.SysUserRoleMapper;
import com.vision.pojo.sys.SysOrganization;
import com.vision.pojo.sys.SysUser;
import com.vision.pojo.sys.vo.SysUserOrganization;
import com.vision.service.sys.SysUserService;
import com.vision.vo.PageObject;
@Service
public class SysUserServiceImpl implements SysUserService{
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	SysOrganizationMapper sysOrganizationMapper;
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		
		//1.对参数进行校验
				if(entity==null)
				throw new IllegalArgumentException("保存对象不能为空");
				if(StringUtils.isEmpty(entity.getUserName()))
			    throw new IllegalArgumentException("用户名不能为空");
				if(StringUtils.isEmpty(entity.getPassword()))
				throw new IllegalArgumentException("密码不能为空");
				if(roleIds==null||roleIds.length==0)
			    throw new IllegalArgumentException("必须指定其角色");
				//....
				//2.保存用户自身信息
				//2.1对密码进行加密
				//使用随机字符串作为salt(盐值)
				String salt=UUID.randomUUID().toString();
				entity.setSalt(salt);
				//密码，盐值加密
				SimpleHash hash=new SimpleHash(
						"MD5",//algorithmName
						 entity.getPassword(),//source
						 salt,
						 1);//hashIterations
				entity.setPassword(hash.toHex());
				//保存用户自身信息
				//SysUser user=ShiroUtils.getUser();
				//entity.setCreatedUser(user.getUsername());
				//entity.setModifiedUser(user.getUsername());
				int rows=sysUserMapper.insertObject(entity);
				//3.保存用户与角色关系数据
				sysUserRoleMapper.insertObjects(entity.getId(),roleIds);
				return rows;
	}
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		//1.对参数进行校验
				if(entity==null)
					throw new IllegalArgumentException("保存对象不能为空");
				if(StringUtils.isEmpty(entity.getUserName()))
					throw new IllegalArgumentException("用户名不能为空");
				//....
				//2.保存用户自身信息
				int rows=sysUserMapper.updateById(entity);
				//3.保存用户与角色关系数据
				sysUserRoleMapper.deleteObjectsByUserId(entity.getId());
				sysUserRoleMapper.insertObjects(entity.getId(),roleIds);
				return rows;
	}
	@Override
	public int findObjectByColumn(String columnName, String columnValue) {
		if(StringUtils.isEmpty(columnName))
		    throw new IllegalArgumentException("参数名不能为空");
			if(StringUtils.isEmpty(columnValue))
			throw new IllegalArgumentException("参数值不能为空");
			int count=sysUserMapper.findObjectByColumn(columnName, columnValue);
			if(count>0)
			throw new ServiceException(columnValue + "已存在");
			return count;
		
	}
	@Override
	public PageObject<SysUserOrganization> findPageObjects(Long organizationId,String username, Integer pageCurrent,Integer pageSize) {
		
		//1.验证参数有效性
				if(pageCurrent==null||pageCurrent<1)
				throw new IllegalArgumentException("页码值不正确");
				//2.基于条件查询总记录数并进行验证
				int rowCount=sysUserMapper.getRowCount(username);
				if(rowCount==0)
				throw new ServiceException("没有找到对应记录");
				//3.基于条件查询当前页记录
				//int pageSize=3;
				int startIndex=(pageCurrent-1)*pageSize;
				List<SysUserOrganization> records=sysUserMapper.findPageObjects(username,
						startIndex, pageSize);
				//4.对查询结果进行封装并返回
				PageObject<SysUserOrganization> pageObject=
				new PageObject<>();
				pageObject.setRowCount(rowCount);
				pageObject.setRecords(records);
				pageObject.setPageSize(pageSize);
				pageObject.setPageCurrent(pageCurrent);
				pageObject.setPageCount((rowCount-1)/pageSize+1);
				return pageObject;
	}
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		
		//1.合法性验证
				if(id==null||id<=0)
				throw new ServiceException("参数不合法,id="+id);
				if(valid!=1&&valid!=0)
				throw new ServiceException("参数不合法,valie="+valid);
				if(StringUtils.isEmpty(modifiedUser))
				throw new ServiceException("修改用户不能为空");
				//2.执行禁用或启用操作
				int rows=0;
				try{
					
			    rows=sysUserMapper.validById(id, valid, modifiedUser);
				}catch(Throwable e){
				e.printStackTrace();
				//报警,给维护人员发短信
				throw new ServiceException("底层正在维护");
				}
				//3.判定结果,并返回
				if(rows==0)
				throw new ServiceException("此记录可能已经不存在");
				return rows;

	}
	@Override
	public Map<String, Object> findObjectById(Integer userId) {
		//1.合法性验证
		if(userId==null||userId<=0)
		throw new ServiceException(
		"参数数据不合法,userId="+userId);
		//2.业务查询
		SysUserOrganization user=
				sysUserMapper.findObjectById(userId);
		if(user==null)
		throw new ServiceException("此用户已经不存在");
		
		  List<Integer> roleIds= sysUserRoleMapper.findRoleIdsByUserId(userId);
		  //3.数据封装 
		  Map<String,Object> map=new HashMap<>(); 
		  map.put("user", user);
		  map.put("roleIds", roleIds);
		  return map;
		 


}
	@Override
	public SysUser findUserById(Integer userId) {
		if(userId==null||userId<=0)
			throw new ServiceException(
			"参数数据不合法,userId="+userId);
		SysUser selectById = sysUserMapper.selectById(userId);
		return selectById;
	}
	
	
	@Override
	public SysUser findUserByName(String userName) {
		if(userName==null||userName=="")
			throw new ServiceException(
			"参数数据不合法,userId="+userName);
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
		queryWrapper.eq("user_name", userName);
		SysUser selectOne = sysUserMapper.selectOne(queryWrapper);
		return selectOne;
	}
	@Override
	public SysUser findUserByIdWeb(Long userId) {
		//
		SysUser selectById = sysUserMapper.selectById(userId);
		SysOrganization selectById2 = sysOrganizationMapper.selectById(selectById.getOrganizationId());
		selectById.setOrganizationName(selectById2.getOrganizationName());
		selectById.setOrganizationAddress(selectById2.getOrganizationAddress());
		return selectById;
	}
}
