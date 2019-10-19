package com.vision.controller.exp;

import com.vision.dto.ExpRemoteDiagnoseValidDto;
import com.vision.dto.PageDto;
import com.vision.dto.RemoteDiagnoseDto;
import com.vision.pojo.exp.ExpRemoteDiagnose;
import com.vision.pojo.sys.SysMenu;
import com.vision.rto.ExpRemoteDiagnoseRto;
import com.vision.service.exp.RemoteDiagnoseService;
import com.vision.util.GetMenusTreeData;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;
import com.vision.vo.TreeDiaMenus;
import com.vision.vo.TreeMenus;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @ClassName RemoteDiagnoseController
 * @Description 远程诊断前端控制器
 * @Author lihd
 * @Date 2019/7/30 20:50
 * @Version 3.1
 */
@RestController
@RequestMapping("/dia")
public class RemoteDiagnoseController {
    @Resource
    private RemoteDiagnoseService remoteDiagnoseService;

    /**
     * 远程诊断分页
     */
    @GetMapping("/find/page")
    public JsonResult doFindPageObjects(String customerName, Integer registerParentid,@Valid @RequestBody PageDto pageDto) {
        PageObject<ExpRemoteDiagnoseRto> pageObject =
                remoteDiagnoseService.findPageObjects(customerName,registerParentid ,pageDto);
        System.out.println(pageObject.getRowCount());
        return JsonResult.oK(pageObject);
    }

    /**
     * 远程诊断查询
     */
    @RequestMapping("/inquire")
    public JsonResult doSelect( Integer id) {
        ExpRemoteDiagnoseRto expRemoteDiagnoseRto = remoteDiagnoseService.select(id);
        return JsonResult.oK(expRemoteDiagnoseRto);
    }

    /**
     * 修改解决状态
     */
    @PutMapping("/valid")
    public JsonResult doValidById(ExpRemoteDiagnoseValidDto expRemoteDiagnoseValidDto) {
        Boolean aBoolean = remoteDiagnoseService.validById(expRemoteDiagnoseValidDto);
        return JsonResult.oK(aBoolean);
    }

    /**
     * 删除
     */
    @RequestMapping("/del")
    public JsonResult doDelete(@RequestBody Integer... ids) {
        Boolean aBoolean = remoteDiagnoseService.doDelete(ids);
        return JsonResult.oK(aBoolean);
    }

    /**
     * 添加
     */
    @RequestMapping("/add")
    public JsonResult doSaveObject( RemoteDiagnoseDto remoteDiagnoseDto) {
    	remoteDiagnoseDto.setRegisterUser("admin");
    	remoteDiagnoseDto.setRegisterUserId(1L);
        Boolean aBoolean = remoteDiagnoseService.doSaveObject(remoteDiagnoseDto);
        return JsonResult.oK(aBoolean);
    }

    /**
     * 修改
     */
    @RequestMapping("/modifier")
    public JsonResult doUpdate(RemoteDiagnoseDto remoteDiagnoseDto) {
        Boolean aBoolean = remoteDiagnoseService.doUpdate(remoteDiagnoseDto);
        return JsonResult.oK(aBoolean);
    }
    
    /**
     * 用于导航名称使用
     */
    @RequestMapping("/RDMenus")
    public JsonResult doRemoteDiagnoseMenus(ExpRemoteDiagnoseRto expRemoteDiagnoseRto) {//后面考虑用户去重
    	List<ExpRemoteDiagnose> list = remoteDiagnoseService.doRemoteDiagnoseMenus(expRemoteDiagnoseRto);
    	
    	List<TreeDiaMenus<ExpRemoteDiagnose>> result1 = new ArrayList<>();
    	TreeDiaMenus<ExpRemoteDiagnose> treeDiadata = new TreeDiaMenus<ExpRemoteDiagnose>();
    	treeDiadata.setTitle("所有用户");
    	treeDiadata.setKey(0L);
		for(int i = 0; i < list.size() ; i++) {
			TreeDiaMenus<ExpRemoteDiagnose> treeStructure = new TreeDiaMenus<>();
			
			
			 ExpRemoteDiagnose expRemoteDiagnose = list.get(i);
			 Integer id = expRemoteDiagnose.getId();
			 treeStructure.setId(expRemoteDiagnose.getId().longValue());
			treeStructure.setKey(id.longValue());
			treeStructure.setTitle(list.get(i).getRegisterUser());
			treeStructure.setValue(expRemoteDiagnose.getRegisterUser());
			treeStructure.setpId(1L);
			result1.add(treeStructure);
		}
		treeDiadata.setChildren(result1);
		//GetMenusTreeData<ExpRemoteDiagnose> tree1 = new GetMenusTreeData();
	//	TreeDiaMenus<ExpRemoteDiagnose> treeDia = tree1.getTreeDia(result1);
	
		
        return JsonResult.oK(treeDiadata);
    }
    
    /**
     * 远程诊断查询
     */
    @RequestMapping("/userNme")
    public JsonResult doSelectUserName(ExpRemoteDiagnoseRto expRemoteDiagnoseRto,Integer pageCurrent,Integer pageSize) {
    
    	PageObject<ExpRemoteDiagnoseRto> list = remoteDiagnoseService.doSelectUserName(expRemoteDiagnoseRto,pageCurrent,pageSize);
        
        
        return JsonResult.oK(list);
    }
}
