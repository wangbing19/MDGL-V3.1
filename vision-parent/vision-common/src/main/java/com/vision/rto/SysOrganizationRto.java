package com.vision.rto;

import lombok.Data;

/**
 * @ClassName SysOrganizationRto
 * @Description SysOrganizationRto返回类
 * @Author lihd
 * @Date 2019/8/28 19:30
 * @Version 3.1
 */
@Data
public class SysOrganizationRto {
    /**
     * 门店id
     */
    private Long organizationId;
    /**
     * 门店名称
     */
    private String organizationName;
    /**
     * 门店父级id
     */
    private Long organizationParentId;
}
