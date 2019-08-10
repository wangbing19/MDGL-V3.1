package com.vision.controller.exp;

import com.vision.dto.ExpExpertDto;
import com.vision.dto.PageDto;
import com.vision.pojo.exp.ExpExpert;
import com.vision.service.exp.ExpertService;
import com.vision.vo.JsonResult;
import com.vision.vo.Node;
import com.vision.vo.PageObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @ClassName ExpertController
 * @Description 专家类前端控制器
 * @Author lihd
 * @Date 2019/7/30 20:48
 * @Version 3.1
 */
@RestController
@RequestMapping("/exp")
public class ExpertController {
    @Resource
    private ExpertService expertService;

    /**ztree树显示*/
    @RequestMapping("/find/ztree")
    public JsonResult doFindZTreeNodes(){
        Node[] selectExpName = expertService.selectExpName();
        return JsonResult.oK(selectExpName);
    }

    /**
     * 远程诊断分页
     */
    @RequestMapping("/find/page")
    public JsonResult doLimitExp(String expertName, @Valid @RequestBody PageDto pageDto) {
        PageObject<ExpExpert> pageObject = expertService.limitExp(expertName, pageDto);
        return JsonResult.oK(pageObject);
    }


    /**
     * 通过id查询专家表数据
     */
    @GetMapping("/inquire/{id}")
    public JsonResult doSelectExp(@PathVariable("id") Integer id) {
        ExpExpert entity = expertService.doSelectExp(id);
        return JsonResult.oK(entity);
    }

    /**
     * 删除
     */
    @DeleteMapping("/del")
    public JsonResult doDelete(@RequestBody Integer... ids) {
        Boolean aBoolean = expertService.doDeleteExp(ids);
        return JsonResult.oK(aBoolean);
    }


    /**
     * 添加
     */
    @GetMapping("/add")
    public JsonResult doInsertExp(@Valid @RequestBody ExpExpertDto expExpertDto) {
        Boolean aBoolean = expertService.doSaveObject(expExpertDto);
        return JsonResult.oK(aBoolean);
    }


    @PutMapping("/modifier")
    public JsonResult doUpdateMessage(@Valid @RequestBody ExpExpertDto expExpertDto) {
        Boolean aBoolean = expertService.doUpdateMessage(expExpertDto);
        return JsonResult.oK(aBoolean);
    }
}
