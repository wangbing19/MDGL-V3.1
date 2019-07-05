package com.vision.mapper.sys;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.sys.SysMenu;
import com.vision.vo.Node;

public interface SysMenuMapper extends BaseMapper<SysMenu>{

	List<Map<String, Object>> findObjects();

	List<Node> findZtreeMenuNodes();

	void insertObject(Long id, Integer[] menuIds);

	SysMenu queryAllObjects();
	List<SysMenu> findAllMenus();
	
	
}
