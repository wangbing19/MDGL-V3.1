package com.vision.controller.exp;

import com.vision.dto.SymptomsDescribedDto;
import com.vision.pojo.exp.ExpSymptomsDescribed;
import com.vision.service.exp.SymptomsDescribedService;
import com.vision.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName SymptomsDescribedController
 * @Description 症状描述前端控制器
 * @Author lihd
 * @Date 2019/7/30 20:50
 * @Version 3.1
 */
@RestController
@RequestMapping("/sym")
public class SymptomsDescribedController {

    @Resource
    private SymptomsDescribedService symptomsDescribedService;

    /**
     * 通过id查询症状描述表中的数据
     */
    @RequestMapping("/inquire/{id}")
    public JsonResult selectSym(@PathVariable("id") Integer id) {
        ExpSymptomsDescribed expSymptomsDescribed = symptomsDescribedService.selectSym(id);
        return JsonResult.oK(expSymptomsDescribed);
    }


    /**
     * 症状描述表添加数据
     */
    @RequestMapping("/add")
    public JsonResult doInsertSym(@RequestBody SymptomsDescribedDto symptomsDescribedDto) {
        Boolean aBoolean = symptomsDescribedService.doInsertSym(symptomsDescribedDto);
        return JsonResult.oK(aBoolean);
    }


    /**
     * 症状描述表修改数据
     */
    @RequestMapping("/modifier")
    public JsonResult doUpdateSym(@RequestBody SymptomsDescribedDto symptomsDescribedDto) {
        Boolean aBoolean = symptomsDescribedService.doUpdateSym(symptomsDescribedDto);
        return JsonResult.oK(aBoolean);
    }
}
