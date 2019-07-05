package com.vision.service.sys;

import java.util.List;
import java.util.Map;

import com.vision.pojo.sys.SysMenu;
import com.vision.vo.Node;

public interface SysMenuService {

	int insertMenu(SysMenu sysMenu);

	List<Map<String, Object>> findMenu();

	int updateMenu(SysMenu sysMenu);

	int deleteMenu(Long id);

	List<Node> findZtreeMenuNodes();
	
	List<SysMenu> findAllMenus();
	
}
