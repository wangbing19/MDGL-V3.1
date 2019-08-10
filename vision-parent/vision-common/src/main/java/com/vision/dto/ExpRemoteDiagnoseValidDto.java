package com.vision.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName ExpRemoteDiagnoseDto
 * @Description
 * @Author lihd
 * @Date 2019/8/5 19:54
 * @Version 3.1
 */
@Data
public class ExpRemoteDiagnoseValidDto {
    /**
     * 诊断表id
     */
    @NotNull(message = "诊断表id不能为空")
    Integer id;
    /**
     * 专家回复状态0表示未解决
     */
    @NotNull(message = "专家回复状态不能为空")
    Integer valid;
    /**
     * 修改用户账号
     */
    @NotNull(message = "修改用户账号不能为空")
    String modifiedUser;
}
