package com.vision.service.sys.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.exception.ServiceException;
import com.vision.mapper.sys.SysMenuMapper;
import com.vision.pojo.sys.SysMenu;
import com.vision.service.sys.SysMenuService;
import com.vision.vo.Node;

@Service
public class SysMenuServiceImpl implements SysMenuService{
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Override
	public int insertMenu(SysMenu sysMenu) {
		//1.验证参数合法性
				if(sysMenu==null)
				throw new IllegalArgumentException("参数异常");
				//2.验证对象属性
				if(StringUtils.isEmpty(sysMenu.getName()))
				throw new IllegalArgumentException("菜单名不能为空");
				sysMenu.setCreatedTime(new Date());
				sysMenu.setModifiedTime(sysMenu.getCreatedTime());
				//.....
				//3.保存菜单信息到数据
				//entity.setCreatedUser(ShiroUtils.getUser().getUsername());
				//entity.setModifiedUser(ShiroUtils.getUser().getUsername());
				int rows=sysMenuMapper.insert(sysMenu);
		return rows;
	}

	@Override
	public List<Map<String, Object>> findMenu() {
		
		return null;
	}

	@Override
	public int updateMenu(SysMenu sysMenu) {
		//1.验证参数合法性
				if(sysMenu==null)
					throw new IllegalArgumentException("参数异常");
				//2.验证对象属性
				if(StringUtils.isEmpty(sysMenu.getName()))
					throw new IllegalArgumentException("菜单名不能为空");
				if(StringUtils.isEmpty(sysMenu.getId()))
					throw new IllegalArgumentException("菜单id不能为空");
				//.....
				//3.保存菜单信息到数据
				//entity.setModifiedUser(ShiroUtils.getUser().getUsername());
				sysMenu.setModifiedTime(new Date());
				int rows=sysMenuMapper.updateById(sysMenu);
				//4.返回结果
				return rows;
		
	}

	@Override
	public int deleteMenu(Long id) {
		if(id==null)
			throw new IllegalArgumentException("参数异常");
		QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<SysMenu>();
		queryWrapper.eq("parent_id", id);
		List<SysMenu> selectList = sysMenuMapper.selectList(queryWrapper);
		if(selectList.size() == 0 || selectList == null ) {
			int deleteById = sysMenuMapper.deleteById(id);
			return deleteById;
		}
		
		
		return -1;//表示有子菜单不能删除
	}

	
	/**
	 * 假如是读事务，建议readOnly属性的值为true，
	 * readOnly默认为false
	 */
	@Transactional(readOnly=true)
	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> list=sysMenuMapper.findZtreeMenuNodes();
		if(list==null||list.size()==0)
		throw new ServiceException("记录不存在");
		return list;
	}

	@Override
	public List<SysMenu> findAllMenus() {
		List<SysMenu> list=sysMenuMapper.findAllMenus();
		if(list==null||list.size()==0)
		throw new ServiceException("没有对应数据");

		return list;
	}
	
	
}
