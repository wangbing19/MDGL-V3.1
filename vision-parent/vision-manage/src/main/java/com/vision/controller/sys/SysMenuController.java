package com.vision.controller.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.vision.pojo.sys.SysMenu;
import com.vision.pojo.sys.vo.SysOrganizationLogs;
import com.vision.service.sys.SysMenuService;
import com.vision.util.GetMenusTreeData;
import com.vision.util.GetTreeData;
import com.vision.vo.JsonResult;
import com.vision.vo.Node;
import com.vision.vo.PageObject;
import com.vision.vo.TreeMenus;
import com.vision.vo.TreeStructure;

@Controller
@RequestMapping("sysMenu")
public class SysMenuController {
	@Autowired
	private SysMenuService sysMenuService;
	
	
	/**
	 * 添加菜单
	 * @param sysMenu
	 * @return
	 */
	@RequestMapping("doInsertMenu")
	@ResponseBody
	public JsonResult insertMenu(SysMenu sysMenu) {
		try {
			
			int reult = sysMenuService.insertMenu(sysMenu);
			return JsonResult.oK(reult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "菜单添加失败!");
		
	}
	
	/**
	 * 查询所有菜单目录，按树形结构返回数据
	 * @return
	 */
	@RequestMapping("doFindMenu")
	@ResponseBody
	public JsonResult findMenu() {
		try {
			List<SysMenu> list = sysMenuService.findAllMenus();
			List<TreeStructure<SysMenu>> result = new ArrayList<>();
			for(int i = 0; i < list.size(); i++) {
				TreeStructure<SysMenu> treeStructure = new TreeStructure<>();
				treeStructure.setId((Long)list.get(i).getId());
				
				treeStructure.setParentId((Long)list.get(i).getParentId());
				treeStructure.setData(list.get(i));
				result.add(treeStructure);
			}
			
			GetTreeData<SysMenu> tree = new GetTreeData<>();
			TreeStructure<SysMenu> treeData = tree.getTree(result);
			
			
			
			List<TreeMenus<SysMenu>> result1 = new ArrayList<>();
			for(int i = 0; i < list.size() ; i++) {
				TreeMenus<SysMenu> treeStructure = new TreeMenus<>();
				treeStructure.setKey((Long)list.get(i).getId());
				treeStructure.setTitle(list.get(i).getName());
				treeStructure.setValue((Long)list.get(i).getId());
				treeStructure.setpId((Long)list.get(i).getParentId());
				 result1.add(treeStructure);
			}
			
			GetMenusTreeData<SysMenu> tree1 = new GetMenusTreeData();
			TreeMenus<SysMenu> tree2 = tree1.getTree(result1);
			
			
			return JsonResult.oK(tree2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "菜单查询失败!");
		
	}
	

	@RequestMapping("doUpdateMenu")
	@ResponseBody
	public JsonResult updateMenu(SysMenu sysMenu) {
		try {
			int result = sysMenuService.updateMenu(sysMenu);
			
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "菜单查询失败!");
		
	}
	
	/**
	 * 删除菜单信息
	 * @param id
	 * @return
	 */
	@RequestMapping("doDeleteMenu")
	@ResponseBody
	public JsonResult deleteMenu(Long id) {
		try {
			int result = sysMenuService.deleteMenu(id);
			
			
			if(result == -1) {
				return JsonResult.build(400, "该菜单下有子菜单不能删除");
			}
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "菜单查询失败!");
		
	}
	
	/**
	 * 删除菜单信息
	 * @param id
	 * @return
	 */
	@RequestMapping("doFindMenuOne")
	@ResponseBody
	public JsonResult findMenuOne(Long id) {
		try {
			SysMenu result = sysMenuService.findMenuOne(id);
			
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "菜单查询失败!");
		
	}
	
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes(){
		try {
			List<Node> list =  sysMenuService.findZtreeMenuNodes();
			

			List<TreeStructure<Node>> result = new ArrayList<>();
			for(int i = 0; i < list.size(); i++) {
				TreeStructure<Node> treeStructure = new TreeStructure<>();
				treeStructure.setId((Long)list.get(i).getId());
				
				treeStructure.setParentId((Long)list.get(i).getParentId());
				treeStructure.setData(list.get(i));
				result.add(treeStructure);
			}
			
			GetTreeData<Node> tree = new GetTreeData<>();
			TreeStructure<Node> treeData = tree.getTree(result);
			
			return JsonResult.oK(treeData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return JsonResult.build(201, "节点查询错误！");
	}
	
	/**
	 * 菜单信息
	 * @param id
	 * @return
	 */
	@RequestMapping("doFindMenuList")
	@ResponseBody
	public JsonResult findMenuList(Integer pageCurrent,Integer pageSize) {
		try {
			PageObject<SysMenu> result = sysMenuService.findMenuList(pageCurrent,pageSize);
			
			return JsonResult.oK(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonResult.build(201, "菜单查询失败!");
		
	}
}
