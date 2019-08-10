package com.vision.service.pre.impl;

import org.springframework.stereotype.Service;

import com.vision.mapper.pre.SymptomDescMapper;
import com.vision.pojo.pre.SymptomDesc;
import com.vision.service.pre.SymptomDescService;

import javax.annotation.Resource;

@Service
public class SymptomDescServiceImpl implements SymptomDescService {
    @Resource
    private SymptomDescMapper symptomDescMapper;

    @Override
    /**根据症状id查询症状描述信息*/
    public SymptomDesc findSymptomDescObjectByid(Long id) {
        try {
            SymptomDesc symptomDesc = symptomDescMapper.findSymptomDescObjectByid(id);
            return symptomDesc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
