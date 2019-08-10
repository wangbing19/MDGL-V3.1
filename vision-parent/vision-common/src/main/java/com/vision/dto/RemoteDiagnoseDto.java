package com.vision.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @ClassName RemoteDiagnoseDto
 * @Description
 * @Author lihd
 * @Date 2019/8/5 20:00
 * @Version 3.1
 */
@Data
public class RemoteDiagnoseDto {
    /**
     * 诊断表id
     */
    private Integer id;
    /**
     * 门店登录账号
     */
    @NotNull(message = "门店登录账号不能为空")
    private String registerUser;
    /**
     * 门店账号对应的父级账号id
     */
    private Integer registerParentid;
    /**
     * 修改时用户的账号
     */
    private String modifiedUser;
    /**
     * 门店地址
     */
    @NotNull(message = "门店地址不能为空")
    private String deptSite;
    /**
     * 门店名称
     */
    @NotNull(message = "门店名称不能为空")
    private String deptName;
    /**
     * 客户姓名
     */
    @NotNull(message = "客户姓名不能为空")
    private String customerName;
    /**
     * 客户电话
     */
    @NotNull(message = "客户电话不能为空")
    private String customerTel;
    /**
     * 专家表序号
     */
    private Integer expertId;
    /**
     * 专家回复状态0表示未解决
     */
    private Integer valid;
    /**
     * 第几次远程诊断
     */
    private Integer timeNumber;
    /**
     * 发送人姓名
     */
    @NotNull(message = "发送人姓名不能为空")
    private String sendName;
    /**
     * 发送人电话
     */
    @NotNull(message = "发送人电话不能为空")
    private String sendTel;
}
