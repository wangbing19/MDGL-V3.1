package com.vision.service.sys;

import java.util.List;
import java.util.Map;

import com.vision.pojo.sys.SysMenu;
import com.vision.vo.AntCheckbox;
import com.vision.vo.Node;
import com.vision.vo.PageObject;

public interface SysMenuService {

	int insertMenu(SysMenu sysMenu);

	List<Map<String, Object>> findMenu();

	int updateMenu(SysMenu sysMenu);

	int deleteMenu(Long id);

	List<Node> findZtreeMenuNodes();
	
	List<SysMenu> findAllMenus();

	SysMenu findMenuOne(Long id);

	PageObject<SysMenu> findMenuList(Integer pageCurrent, Integer pageSize);

	List<AntCheckbox> findMenuCheckboxAll();

	String[] findMenuCheckbox(Long id);
	
}
