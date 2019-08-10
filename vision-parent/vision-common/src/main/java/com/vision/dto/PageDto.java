package com.vision.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @ClassName PageDto
 * @Description 后端分页Dto
 * @Author lihd
 * @Date 2019/8/5 19:54
 * @Version 3.1
 */
@Data
public class PageDto {
    /**
     * 页数
     */
    @NotNull(message = "页数不能为空")
    @Min(value = 1, message = "页数必须大于1")
    private Integer pageCurrent;
    /**
     * 每页的大小
     */
    @NotNull(message = "每页最大显示数不能为空")
    @Min(value = 1, message = "每页最大显示数必须大于1")
    @Max(value = 100, message = "每页最大显示数不能大于100")
    private Integer pageSize;
}