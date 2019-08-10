package com.vision.service.exp.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vision.dto.ExpExpertDto;
import com.vision.dto.PageDto;
import com.vision.exception.ServiceException;
import com.vision.mapper.exp.ExpertMapper;
import com.vision.pojo.exp.ExpExpert;
import com.vision.service.exp.ExpertService;
import com.vision.util.BooleanUtil;
import com.vision.util.CaseBeanUtils;
import com.vision.util.ListUtils;
import com.vision.vo.Node;
import com.vision.vo.PageObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName ExpertServiceImpl
 * @Description
 * @Author lihd
 * @Date 2019/7/31 19:11
 * @Version 3.1
 */
@Service
public class ExpertServiceImpl implements ExpertService {

    @Resource
    private ExpertMapper expertMapper;

    @Override
    public Node[] selectExpName() {
        return expertMapper.selectExpName();
    }

    /**
     * 通过获取的专家姓名和当前页数进行分页查询
     */
    @Override
    public PageObject<ExpExpert> limitExp(String expertName, PageDto pageDto) {
        QueryWrapper<ExpExpert> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ExpExpert.Meta.delTag, 0).like(ExpExpert.Meta.expertName, expertName);
        int rowCount = expertMapper.selectCount(queryWrapper);
        if (pageDto.getPageSize() == null) {
            pageDto.setPageSize(10);
        }
        int startIndex = (pageDto.getPageCurrent() - 1) * pageDto.getPageSize();
        //依据条件获取当前页数据
        List<ExpExpert> records =
                expertMapper.limitExp(expertName, startIndex, pageDto.getPageSize());
        //封装数据
        PageObject<ExpExpert> pageObject = new PageObject<>();
        pageObject.setPageCurrent(pageDto.getPageCurrent());//当前页码值
        pageObject.setRowCount(rowCount);//总记录数
        pageObject.setPageSize(pageDto.getPageSize());//每一页显示条数
        pageObject.setRecords(records);//当前页显示数据
        return pageObject;
    }

    @Override
    public ExpExpert doSelectExp(Integer id) {
        QueryWrapper<ExpExpert> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ExpExpert.Meta.id, id).eq(ExpExpert.Meta.delTag, 0);
        ExpExpert expExpert = expertMapper.selectOne(queryWrapper);
        return expExpert;
    }

    /**
     * 删除
     */
    @Override
    public Boolean doDeleteExp(Integer[] ids) {
        List<Integer> list = Arrays.asList(ids);
        List<ExpExpert> expExperts = expertMapper.selectBatchIds(list);
        if (ListUtils.isArrayNotEmpty(expExperts)) {
            expExperts.stream().forEach(expExpert -> {
                expExpert.setDelTag(1);
                expertMapper.updateById(expExpert);
            });
        }
        return true;
    }

    /**
     * 添加
     */
    @Override
    public Boolean doSaveObject(ExpExpertDto expExpertDto) {
        ExpExpert expExpert = CaseBeanUtils.entityToClass(expExpertDto, ExpExpert.class);
        //执行添加
        int i = expertMapper.insert(expExpert);
        return BooleanUtil.bool(i);
    }

    @Override
    public Boolean doUpdateMessage(ExpExpertDto expExpertDto) {
        ExpExpert expExpert = CaseBeanUtils.entityToClass(expExpertDto, ExpExpert.class);
        int i = expertMapper.updateById(expExpert);
        return BooleanUtil.bool(i);
    }

}
