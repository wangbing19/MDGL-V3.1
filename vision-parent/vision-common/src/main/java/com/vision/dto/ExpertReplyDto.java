package com.vision.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @ClassName ExpertReplyDto
 * @Description
 * @Author lihd
 * @Date 2019/8/2 19:21
 * @Version 3.1
 */
@Data
public class ExpertReplyDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 远程诊断表序号
     */
    @NotNull(message = "远程诊断表序号不能为空")
    private Integer remoteDiagnoseId;
    /**
     * 症状描述
     */
    @NotNull(message = "症状描述不能为空")
    private String expertReply;
    /**
     * 症状备注
     */
    private String expertRemark;
    /**
     * 删除标识，默认为0
     */
    private int delTag;
}
