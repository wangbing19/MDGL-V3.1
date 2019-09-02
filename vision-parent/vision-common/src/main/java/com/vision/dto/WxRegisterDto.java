package com.vision.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName WxRegisterDto
 * @Description
 * @Author lihd
 * @Date 2019/8/20 20:45
 * @Version 3.1
 */
@Data
public class WxRegisterDto {

    @NotNull(message = "手机号不能为空")
    private String phone;
    @NotNull(message = "密码不能为空")
    private String password;
}
