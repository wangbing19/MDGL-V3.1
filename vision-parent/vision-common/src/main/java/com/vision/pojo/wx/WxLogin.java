package com.vision.pojo.wx;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @ClassName wxLogin
 * @Description
 * @Author lihd
 * @Date 2019/8/21 20:13
 * @Version 3.1
 */
@Data
@Accessors(chain = true)
@TableName(value = "wx_login")
public class WxLogin {
    private Long id;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
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
        public static final String phone = "phone";
        public static final String password = "password";
        public static final String gmtCreate = "gmt_create";
        public static final String gmtModified = "gmt_modified";
        public static final String delTag = "del_tag";
    }
}
