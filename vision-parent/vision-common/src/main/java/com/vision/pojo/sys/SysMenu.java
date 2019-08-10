package com.vision.pojo.sys;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("sys_menus")
public class SysMenu {
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单url: log/doFindPageObjects.do
     */
    private String url;
    /**
     * 菜单类型(两种:按钮,普通菜单)
     */
    private Integer type = 1;
    /**
     * 排序(序号)
     */
    private Integer sort;
    /**
     * 备注
     */
    private String note;
    /**
     * 上级菜单id
     */
    private Long parentId;
    /**
     * 菜单对应的权限标识(sys:log:delete)
     */
    private String permission;
    /**
     * 创建用户
     */
    private String createdUser;
    /**
     * 修改用户
     */
    private String modifiedUser;
    private Date createdTime;
    private Date modifiedTime;
}
