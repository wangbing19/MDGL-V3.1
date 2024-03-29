package com.vision.controller.exp;

import com.vision.dto.ExpertReplyDto;
import com.vision.exception.ServiceException;
import com.vision.pojo.exp.ExpExpertReply;
import com.vision.service.exp.ExpertReplyService;
import com.vision.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @ClassName ExpertReplyController
 * @Description 专家回复前端控制器
 * @Author lihd
 * @Date 2019/7/30 20:49
 * @Version 3.1
 */
@RestController
@RequestMapping("/rep")
public class ExpertReplyController {
    @Resource
    private ExpertReplyService expertReplyService;

    /**
     * 通过remoteDiagnoseId查询专家回复的数据
     */
    @RequestMapping("/inquire")
    public JsonResult selectRep(ExpertReplyDto expertReplyDto) {
    	
    	List<ExpExpertReply> expExpertReply = expertReplyService.selectRep(expertReplyDto);
        return JsonResult.oK(expExpertReply);
    }


    /**
     * 专家回复新增
     */
    @RequestMapping("/add")
    public JsonResult doInsertRep( ExpertReplyDto expertReplyDto) {
        Boolean aBoolean;
        try {
            aBoolean = expertReplyService.doInsertRep(expertReplyDto);
            
            return JsonResult.oK(aBoolean);
        } catch (ServiceException e) {
            e.printStackTrace();
            return JsonResult.oK(e.getMessage());
        }
    }


    /**
     * 删除
     */
    @DeleteMapping("/del")
    public JsonResult doDeleteRep(@RequestBody Integer... id) {
        Boolean aBoolean = expertReplyService.doDeleteRep(id);
        return JsonResult.oK(aBoolean);
    }

    /**
     * 专家回复修改
     */
    @PutMapping("/modifier")
    public JsonResult doUpdateRep(@Valid @RequestBody ExpertReplyDto expertReplyDto) {
        Boolean aBoolean = expertReplyService.doUpdateRep(expertReplyDto);
        return JsonResult.oK(aBoolean);
    }
}
