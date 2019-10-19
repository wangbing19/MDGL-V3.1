package com.vision.service.exp.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.vision.dto.SymptomsDescribedDto;
import com.vision.mapper.exp.ExpSymptomsDescribedMapper;
import com.vision.pojo.exp.ExpExpert;
import com.vision.pojo.exp.ExpSymptomsDescribed;
import com.vision.service.exp.SymptomsDescribedService;
import com.vision.util.BooleanUtil;
import com.vision.util.CaseBeanUtils;
import com.vision.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SymptomsDescribedServiceIpml implements SymptomsDescribedService {

    @Resource
    private ExpSymptomsDescribedMapper expSymptomsDescribedMapper;

    /**
     * 通过id查询症状描述表中的数据
     */
    @Override
    public ExpSymptomsDescribed selectSym(Integer id) {
        QueryWrapper<ExpSymptomsDescribed> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ExpSymptomsDescribed.Meta.remoteDiagnoseId, id).eq(ExpSymptomsDescribed.Meta.delTag, 0);
        ExpSymptomsDescribed entity = expSymptomsDescribedMapper.selectOne(queryWrapper);
        return entity;
    }

    /**
     * 症状描述表添加数据
     */
    @Override
    public Boolean doInsertSym(SymptomsDescribedDto symptomsDescribedDto) {
        ExpSymptomsDescribed expSymptomsDescribed =
                CaseBeanUtils.entityToClass(symptomsDescribedDto, ExpSymptomsDescribed.class);
        expSymptomsDescribed.setGmtCreate(new Date());
        expSymptomsDescribed.setGmtModified(expSymptomsDescribed.getGmtCreate());
        int i = expSymptomsDescribedMapper.insert(expSymptomsDescribed);
        return BooleanUtil.bool(i);
    }

    /**
     * 症状描述表修改数据
     */
    @Override
    public Boolean doUpdateSym(SymptomsDescribedDto symptomsDescribedDto) {
        ExpSymptomsDescribed expSymptomsDescribed =
                CaseBeanUtils.entityToClass(symptomsDescribedDto, ExpSymptomsDescribed.class);
        expSymptomsDescribed.setGmtCreate(new Date());
        expSymptomsDescribed.setGmtModified(expSymptomsDescribed.getGmtCreate());
        UpdateWrapper<ExpSymptomsDescribed> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("remote_diagnose_id", expSymptomsDescribed.getRemoteDiagnoseId());
        int i = expSymptomsDescribedMapper.update(expSymptomsDescribed, updateWrapper);
        return BooleanUtil.bool(i);
    }

    /**
     * 症状描述表删除数据
     */
    @Override
    public Boolean doDeleteSym(Integer... remoteDiagnoseId) {
        List<Integer> list = expSymptomsDescribedMapper.selectSymId(remoteDiagnoseId);
        List<ExpSymptomsDescribed> expSymptomsDescribeds = expSymptomsDescribedMapper.selectBatchIds(list);
        if (ListUtils.isArrayNotEmpty(expSymptomsDescribeds)) {
            expSymptomsDescribeds.stream().forEach(expSymptomsDescribed -> {
                expSymptomsDescribed.setDelTag(1);
                expSymptomsDescribedMapper.updateById(expSymptomsDescribed);
            });
        }
        return true;
    }

	@Override
	public List<ExpSymptomsDescribed> selectSymAll(ExpSymptomsDescribed expSymptomsDescribed) {
		QueryWrapper<ExpSymptomsDescribed> queryWrapper = new QueryWrapper<ExpSymptomsDescribed>();
		queryWrapper.eq("register_user_id", expSymptomsDescribed.getRegisterUserId()).eq("remote_diagnose_id", expSymptomsDescribed.getRemoteDiagnoseId());
		List<ExpSymptomsDescribed> selectList = expSymptomsDescribedMapper.selectList(queryWrapper);
		return selectList;
	}
}
