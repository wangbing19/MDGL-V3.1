package com.vision.service.sys.imp;

import java.util.ArrayList;
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
import com.vision.mapper.sys.SysRoleMenusMapper;
import com.vision.pojo.sys.SysMenu;
import com.vision.pojo.sys.vo.SysOrganizationLogs;
import com.vision.pojo.sys.vo.SysRoleMenus;
import com.vision.service.sys.SysMenuService;
import com.vision.vo.AntCheckbox;
import com.vision.vo.Node;
import com.vision.vo.PageObject;

@Service
public class SysMenuServiceImpl implements SysMenuService{
	@Autowired
	private SysMenuMapper sysMenuMapper;
	private SysRoleMenusMapper sysRoleMenusMapper;
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
				if(StringUtils.isEmpty(sysMenu.getParentId()))
					throw new IllegalArgumentException("菜单父级id不能为空");
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

	@Override
	public SysMenu findMenuOne(Long id) {
		SysMenu selectById = sysMenuMapper.selectById(id);
		QueryWrapper<SysMenu> queryWrapper= new QueryWrapper<SysMenu>();
		queryWrapper.eq("id", selectById.getParentId());
		SysMenu selectOne = sysMenuMapper.selectOne(queryWrapper);
		selectById.setParventName(selectOne.getName());
		return selectById;
	}

	@Override
	public PageObject<SysMenu> findMenuList(Integer pageCurrent, Integer pageSize) {
		PageObject<SysMenu> pageObject=new PageObject<>();
		int startIndex=(pageCurrent-1)*pageSize;
		Integer rowCount = sysMenuMapper.selectCount(null);
		//List<SysMenu> selectList=sysMenuMapper.findMenuList(startIndex,pageSize);
		List<SysMenu> selectList=sysMenuMapper.findMenuParventList(startIndex,pageSize);
		
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(selectList);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		return pageObject;
	}

	@Override
	public List<AntCheckbox> findMenuCheckboxAll() {
	
		List<SysMenu> selectList = sysMenuMapper.selectList(null);
		String[] result = new String[selectList.size()];
		AntCheckbox antCheckbox = new AntCheckbox();
		List<AntCheckbox> result1 = new ArrayList<AntCheckbox>();
		for(int i =0; i < selectList.size(); i++) {
			result[i] = selectList.get(i).getName();
			antCheckbox.setId(selectList.get(i).getId());
			antCheckbox.setTitle(selectList.get(i).getName());
			result1.add(antCheckbox);
		}
		return result1;
	}

	@Override
	public String[] findMenuCheckbox(Long id) {
	//	List<Long> findMenuIdsByRoleId = sysRoleMenusMapper.findMenuIdsByRoleId(id);
		QueryWrapper<SysRoleMenus> queryWrapper= new QueryWrapper<SysRoleMenus>();
		queryWrapper.eq("role_id", id);
		List<SysRoleMenus> selectList2 = sysRoleMenusMapper.selectList(queryWrapper);
//		List<SysMenu> selectList = sysRoleMenusMapper.findMenuCheckbox(id);
//		System.out.println(selectList.toString());
		return null;
	}

	
	
	
}
