package com.vision.service.sys.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.exception.ServiceException;
import com.vision.mapper.sys.SysRoleMapper;
import com.vision.mapper.sys.SysRoleMenusMapper;
import com.vision.mapper.sys.SysUserMapper;
import com.vision.mapper.sys.SysUserRoleMapper;
import com.vision.pojo.ppo.PpoTrainer;
import com.vision.pojo.sys.SysRole;
import com.vision.pojo.sys.SysRoleUser;
import com.vision.pojo.sys.vo.SysRoleMenus;
import com.vision.pojo.sys.vo.SysRoleOrganizationResult;
import com.vision.service.sys.SysRoleService;
import com.vision.vo.CheckBox;
import com.vision.vo.PageObject;


@Service
public class SysRoleServiceImpl implements SysRoleService{
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysRoleMenusMapper sysRoleMenusMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	SysUserRoleMapper 	sysUserRoleMapper;
	@Override
	public int doInsertRole(SysRole sysRole,Integer[] menuIds) {
		//1.参数验证
				if(sysRole==null)
					throw new IllegalArgumentException("保存对象不能为空");
				if(StringUtils.isEmpty(sysRole.getName()))
					throw new IllegalArgumentException("名字不能为空");
				if(menuIds==null||menuIds.length==0)
					throw new ServiceException("必须为角色赋予权限");
				//2.保存角色自身信息
				//sysRole.setName("admin");//暂时写死
		int result = sysRoleMapper.insertObject(sysRole);
		//3.保存角色和菜单关系数据
		sysRoleMenusMapper.insertObject(sysRole.getId(),menuIds);
		return result;
	}
	
	@Override
	public int deleteRole(Integer id) {
		//1.参数合法性校验
		if(id==null||id<1)
		throw new IllegalArgumentException("id值不合法");
		//2.删除角色自身信息
		int rows=sysRoleMapper.deleteObject(id);
		if(rows==0)
		throw new ServiceException("记录可能已经不存在");
		//3.删除角色菜单关系数据
		sysRoleMenusMapper.deleteObjectsByRoleId(id);
		QueryWrapper queryWrapper = new QueryWrapper<SysRoleUser>();
		queryWrapper.eq("role_id", id);
		//4.删除角色用户关系数据 
		sysUserRoleMapper.delete(queryWrapper);
		//int result = sysRoleMenusMapper.deleteRoleById(id);
		
		return rows;
	}
	
	@Override
	public int updateRole(SysRole sysRole, Integer[] menuIds) {
		//1.参数验证
		if(sysRole.getId() == null) {
			throw new IllegalArgumentException("角色id不能为空");
		}
				if(sysRole==null)
				throw new IllegalArgumentException("保存对象不能为空");
				if(StringUtils.isEmpty(sysRole.getName()))
				throw new IllegalArgumentException("名字不能为空");
				if(menuIds==null||menuIds.length==0)
				throw new ServiceException("必须为角色赋予权限");
				//2.保存角色自身信息
				//int rows=sysRoleMapper.updateById(sysRole);
				sysRole.setModifiedTime(new Date());
				int rows=sysRoleMapper.updateObject(sysRole);
				//3.保存角色和菜单关系数据
				//3.1删除原先关系数据
				sysRoleMenusMapper.deleteObjectsByRoleId(sysRole.getId());
				//3.2添加新的关系数据
				sysRoleMenusMapper.insertObject(sysRole.getId(),
						menuIds);
				return rows;
		
	}
	
	@Override
	public List<CheckBox> findObjects() {
		
		List<CheckBox> result = sysRoleMapper.findObjects();
		return result;
	}
	
	@Override
	public SysRoleOrganizationResult findObjectById(Integer id) {
		
		if(id==null||id<1)
			throw new IllegalArgumentException("id值无效");
		SysRoleOrganizationResult result=sysRoleMapper.findObjectById(id);
			if(result==null)
			throw new ServiceException("记录可能已经不存在");
			return result;
	}
	
	@Override
	public List<SysRole> findObjectByIds(Integer[] ids) {
				ArrayList<Integer> arrayList = new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			Integer integer = ids[i];
			arrayList.add(integer);
		}
		List<SysRole> selectBatchIds = sysRoleMapper.selectBatchIds(arrayList);
		return selectBatchIds;
	}
	
	@Override
	public PageObject<SysRole> doFindRoleAll(Integer pageCurrent,Integer pageSize) {
		
		 
		int startIndex=(pageCurrent-1)*pageSize;
		QueryWrapper<SysRole> queryWrapper = new QueryWrapper<SysRole>();
		Integer pageCount = sysRoleMapper.selectCount(queryWrapper);
		List<SysRole> selectList = sysRoleMapper.doFindRoleAll(startIndex, pageSize);
		PageObject<SysRole> pageObject = new PageObject<SysRole>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCount(pageCount);
		pageObject.setRecords(selectList);
		
		
		return pageObject;
	}
	
	
	
}
