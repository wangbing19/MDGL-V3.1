package com.vision.service.wx;

import com.vision.dto.WxRegisterDto;
import com.vision.vo.JsonResult;

public interface WxService {
    /**
     * @Author lihd
     * @Description 微信小程序注册接口
     * @Date 2019/8/21 20:09
     * @param wxRegisterDto
     * @return
     */
    Boolean register(WxRegisterDto wxRegisterDto);

    /**
     * @Author lihd
     * @Description 微信小程序登陆
     * @Date 2019/8/21 21:09
     * @param wxRegisterDto
     * @return
     */
    JsonResult login(WxRegisterDto wxRegisterDto);
}
