package com.vision.mapper.wx;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.wx.WxLogin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxMapper extends BaseMapper<WxLogin> {
}
