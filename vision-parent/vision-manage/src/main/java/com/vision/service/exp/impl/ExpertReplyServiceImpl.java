package com.vision.service.exp.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.dto.ExpertReplyDto;
import com.vision.exception.ServiceException;
import com.vision.mapper.exp.ExpertReplyMapper;
import com.vision.pojo.exp.ExpExpertReply;
import com.vision.service.exp.ExpertReplyService;
import com.vision.service.exp.RemoteDiagnoseService;
import com.vision.util.BooleanUtil;
import com.vision.util.CaseBeanUtils;
import com.vision.util.ListUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ExpertReplyServiceImpl implements ExpertReplyService {

    @Resource
    private ExpertReplyMapper expertReplyMapper;
    @Resource
    private RemoteDiagnoseService remoteDiagnoseService;

    @Override
    public ExpExpertReply selectRep(Integer id) {
        QueryWrapper<ExpExpertReply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ExpExpertReply.Meta.remoteDiagnoseId, id).eq(ExpExpertReply.Meta.delTag,0);
        ExpExpertReply entity = expertReplyMapper.selectOne(queryWrapper);
        return entity;
    }

    @Override
    public Boolean doInsertRep(ExpertReplyDto expertReplyDto) throws ServiceException{
        Boolean aBoolean = remoteDiagnoseService.selectId(expertReplyDto.getRemoteDiagnoseId());
        if (!aBoolean){
            throw new ServiceException("当前诊断表不存在");
        }
        ExpExpertReply expExpertReply = CaseBeanUtils.entityToClass(expertReplyDto, ExpExpertReply.class);
        int i = expertReplyMapper.insert(expExpertReply);
        return BooleanUtil.bool(i);
    }

    @Override
    public Boolean doDeleteRep(Integer[] ids) {
        List<Integer> list = Arrays.asList(ids);
        List<ExpExpertReply> expExperts = expertReplyMapper.selectBatchIds(list);
        if (ListUtils.isArrayNotEmpty(expExperts)) {
            expExperts.stream().forEach(expExpertReply -> {
                expExpertReply.setDelTag(1);
                expExpertReply.setGmtModified(new Date());
                expertReplyMapper.updateById(expExpertReply);
            });
        }
        return true;
    }

    @Override
    public Boolean doUpdateRep(ExpertReplyDto expertReplyDto) {
        ExpExpertReply expExpertReply = CaseBeanUtils.entityToClass(expertReplyDto, ExpExpertReply.class);
        expExpertReply.setDelTag(0);
        expExpertReply.setGmtModified(new Date());
        int i = expertReplyMapper.updateById(expExpertReply);
        return BooleanUtil.bool(i);
    }
}
