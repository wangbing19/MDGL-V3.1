package com.vision.pojo.exp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName(value = "exp_remote_diagnose")
public class ExpRemoteDiagnose {
    /**
     * 诊断表id
     */
    @TableId(type= IdType.AUTO)
    private Integer id;
    /**
     * 门店登录账号
     */
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
    private String deptSite;
    /**
     * 门店名称
     */
    private String deptName;
    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 客户电话
     */
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
    private String sendName;
    /**
     * 发送人电话
     */
    private String sendTel;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 删除标识，默认为0
     */
    private int delTag;

    public class Meta {
        public static final String id = "id";
        public static final String registerUser = "register_user";
        public static final String registerParentid = "register_parentid";
        public static final String modifiedUser = "modified_user";
        public static final String deptSite = "dept_site";
        public static final String deptName = "dept_name";
        public static final String customerName = "customer_name";
        public static final String customerTel = "customer_tel";
        public static final String expertId = "expert_id";
        public static final String valid = "valid";
        public static final String timeNumber = "time_number";
        public static final String sendName = "send_name";
        public static final String sendTel = "send_tel";
        public static final String gmtCreate = "gmt_create";
        public static final String gmtModified = "gmt_modified";
        public static final String delTag = "del_tag";
    }
}
