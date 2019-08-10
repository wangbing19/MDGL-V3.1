package com.vision.pojo.exp;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName(value = "exp_expert")
public class ExpExpert {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 专家姓名
     */
    private String expertName;
    /**
     * 专家电话
     */
    private String expertTel;
    /**
     * 专家信息
     */
    private String expertMessage;
    /**
     * 预约时间
     */
    private String appointmentTime;
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
        public static final String expertName = "expert_name";
        public static final String expertTel = "expert_tel";
        public static final String expertMessage = "expert_message";
        public static final String appointmentTime = "appointment_time";
        public static final String gmtCreate = "gmt_create";
        public static final String gmtModified = "gmt_modified";
        public static final String delTag = "del_tag";
    }

}
