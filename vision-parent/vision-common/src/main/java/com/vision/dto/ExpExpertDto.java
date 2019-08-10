package com.vision.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName ExpExpertDto
 * @Description
 * @Author lihd
 * @Date 2019/8/1 19:59
 * @Version 3.1
 */
@Data
public class ExpExpertDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 专家姓名
     */
    @NotNull(message = "专家姓名不能为空")
    private String expertName;
    /**
     * 专家电话
     */
    @NotNull(message = "专家电话不能为空")
    private String expertTel;
    /**
     * 专家信息
     */
    @NotNull(message = "专家信息不能为空")
    private String expertMessage;
    /**
     * 预约时间
     */
    @NotNull(message = "专家预约时间不能为空")
    private String appointmentTime;
}
