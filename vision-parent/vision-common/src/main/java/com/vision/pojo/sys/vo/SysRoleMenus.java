package com.vision.pojo.sys.vo;

import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("sys_role_menus")
public class SysRoleMenus {
	private Long id;
	private Long roleId;
	private Long menusId;
}
