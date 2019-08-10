package com.vision.service.cus;

import com.vision.pojo.cus.CusConsultation;
import com.vision.pojo.cus.vo.CusVo;
import com.vision.vo.PageObject;

public interface CusConsultationService {


    /**
     * 基于用户/电话及当前页码值条件查询用户信息
     */
    PageObject<CusConsultation> getConsultation(CusVo cusVo);

    /**
     * 将CusCustomer类型数据添加到数据库
     */
    Integer addConsultation(CusConsultation cusConsultation);

    /**
     * 基于id删除咨询表信息
     */
    Integer deleteConsultation(Integer id, Integer orgId);

    /**
     * 基于咨询表id,查询相关id所有信息
     */
    CusConsultation getConsultationById(Integer id, Integer orgId);

    /**
     * 基于咨询表id更改用户信息
     */
    Integer updateConsultation(CusConsultation cusConsultation);
}
