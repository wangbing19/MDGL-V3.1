package com.vision.service.wx.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.dto.WxRegisterDto;
import com.vision.mapper.wx.WxMapper;
import com.vision.pojo.wx.WxLogin;
import com.vision.service.wx.WxService;
import com.vision.util.BooleanUtil;
import com.vision.util.CaseBeanUtils;
import com.vision.vo.JsonResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.annotation.Resource;

/**
 * @ClassName wxServiceImpl
 * @Description
 * @Author lihd
 * @Date 2019/8/21 19:58
 * @Version 3.1
 */
@Service
public class WxServiceImpl implements WxService {
    @Resource
    private WxMapper wxMapper;

    @Override
    public Boolean register(WxRegisterDto wxRegisterDto) {
        WxLogin wxLogin = CaseBeanUtils.entityToClass(wxRegisterDto, WxLogin.class);
        /*密码加密*/
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(wxRegisterDto.getPassword());
        wxLogin.setPassword(encode);
        int i = wxMapper.insert(wxLogin);
        return BooleanUtil.bool(i);
    }

    @Override
    public JsonResult login(WxRegisterDto wxRegisterDto) {
        QueryWrapper<WxLogin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(WxLogin.Meta.phone, wxRegisterDto.getPhone()).eq(WxLogin.Meta.delTag, 0);
        WxLogin wxLogin = wxMapper.selectOne(queryWrapper);
        if (wxLogin == null) {
            return JsonResult.error("没有该账号，请检查手机号");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(wxRegisterDto.getPassword(),wxLogin.getPassword())){
            return JsonResult.error("密码不正确，请检查密码");
        }
        return null;
    }
}
