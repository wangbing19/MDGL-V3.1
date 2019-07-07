package com.vision.pojo.sys.vo;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class SysRoleMenus {
	private Long id;
	private Long roleId;
	private Long menusId;
}
