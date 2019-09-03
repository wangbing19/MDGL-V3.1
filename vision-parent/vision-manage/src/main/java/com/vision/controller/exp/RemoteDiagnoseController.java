package com.vision.controller.exp;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONArray;
import com.vision.dto.ExpRemoteDiagnoseValidDto;
import com.vision.dto.PageDto;
import com.vision.dto.RemoteDiagnoseDto;
import com.vision.rto.ExpRemoteDiagnoseRto;
import com.vision.rto.SysOrganizationRto;
import com.vision.service.exp.RemoteDiagnoseService;
import com.vision.util.ListToTreeUtil;
import com.vision.vo.JsonResult;
import com.vision.vo.PageObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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
    @RequestMapping("/inquire/{id}")
    public JsonResult doSelect(@PathVariable("id") Integer id) {
        ExpRemoteDiagnoseRto expRemoteDiagnoseRto = remoteDiagnoseService.select(id);
        return JsonResult.oK(expRemoteDiagnoseRto);
    }

    /**
     * 修改解决状态
     */
    @PutMapping("/valid")
    public JsonResult doValidById(@Valid @RequestBody ExpRemoteDiagnoseValidDto expRemoteDiagnoseValidDto) {
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
    public JsonResult doSaveObject(@Valid @RequestBody RemoteDiagnoseDto remoteDiagnoseDto) {
        Boolean aBoolean = remoteDiagnoseService.doSaveObject(remoteDiagnoseDto);
        return JsonResult.oK(aBoolean);
    }

    /**
     * 修改
     */
    @RequestMapping("/modifier")
    public JsonResult doUpdate(@RequestBody RemoteDiagnoseDto remoteDiagnoseDto) {
        Boolean aBoolean = remoteDiagnoseService.doUpdate(remoteDiagnoseDto);
        return JsonResult.oK(aBoolean);
    }

    @GetMapping("/find/{userId}")
    public JsonResult find(@PathVariable("userId") Integer userId){
        List<SysOrganizationRto> result = remoteDiagnoseService.find(userId);
        JSONArray objects = ListToTreeUtil.listToTree
                (JSONArray.parseArray(JSON.toJSONString(result)), "organizationId", "organizationParentId", "child");
        return JsonResult.oK(objects);
    }


}
