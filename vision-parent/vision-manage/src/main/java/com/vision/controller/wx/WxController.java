package com.vision.controller.wx;

import com.vision.dto.WxRegisterDto;
import com.vision.service.wx.WxService;
import com.vision.vo.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @ClassName WxController
 * @Description 微信小程序接口
 * @Author lihd
 * @Date 2019/8/20 20:42
 * @Version 3.1
 */
@RestController
@RequestMapping("/wx")
public class WxController {

    @Resource
    private WxService wxService;

    @PostMapping("/register")
    public JsonResult register(@Valid WxRegisterDto wxRegisterDto) {
        Boolean aBoolean = wxService.register(wxRegisterDto);
        if (aBoolean) {
            return JsonResult.oK();
        }
        return JsonResult.error();
    }

    @PostMapping("/login")
    public JsonResult login(@Valid WxRegisterDto wxRegisterDto){
        JsonResult jsonResult = wxService.login(wxRegisterDto);
        return JsonResult.oK();
    }
}
