package com.vision.dto;

import lombok.Data;

/**
 * @ClassName SymptomsDescribedDto
 * @Description
 * @Author lihd
 * @Date 2019/8/7 19:53
 * @Version 3.1
 */
@Data
public class SymptomsDescribedDto {
    /**
     * id
     */
    private Integer id;
    /**
     * 远程诊断表序号
     */
    private Integer remoteDiagnoseId;
    private Long registerUserId;
    
    /**
     * 症状描述
     */
    private String symptomsDescribed;
    /**
     * 症状备注
     */
    private String symptomsRemark;
}
