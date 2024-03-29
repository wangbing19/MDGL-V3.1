package com.vision.service.exp.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.vision.dto.ExpRemoteDiagnoseValidDto;
import com.vision.dto.PageDto;
import com.vision.dto.RemoteDiagnoseDto;
import com.vision.exception.ServiceException;
import com.vision.mapper.exp.ExpRemoteDiagnoseMapper;
import com.vision.mapper.exp.ExpSymptomsDescribedMapper;
import com.vision.pojo.exp.ExpExpert;
import com.vision.pojo.exp.ExpRemoteDiagnose;
import com.vision.pojo.exp.ExpSymptomsDescribed;
import com.vision.pojo.ppo.PpoTrainer;
import com.vision.pojo.sys.SysUser;
import com.vision.rto.ExpRemoteDiagnoseRto;
import com.vision.service.exp.ExpertReplyService;
import com.vision.service.exp.RemoteDiagnoseService;
import com.vision.service.exp.SymptomsDescribedService;
import com.vision.util.BooleanUtil;
import com.vision.util.CaseBeanUtils;
import com.vision.util.ListUtils;
import com.vision.vo.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class RemoteDiagnoseServiceImpl implements RemoteDiagnoseService {

    @Resource
    private ExpRemoteDiagnoseMapper remoteDiagnoseMapper;

    @Autowired
    private ExpertReplyService expertReplyService;

    @Autowired
    private SymptomsDescribedService symptomsDescribedService;
    @Autowired
    private ExpSymptomsDescribedMapper expSymptomsDescribedMapper;
    

    /**
     * 分页
     */
    @Override
    public PageObject<ExpRemoteDiagnoseRto> findPageObjects(String customerName, Integer registerParentid, PageDto pageDto) {
//         获取登录用户的账号
//        SysUser user=ShiroUtils.getUser();
//        Integer parentId = user.getParentId();
//         2.依据条件获取总记录数
        int rowCount = remoteDiagnoseMapper.getRowCount(customerName, registerParentid);
        // 3.判断记录是否存在
        if (rowCount == 0) {
            throw new ServiceException("您要查询记录不存在");
        }
        // 4.计算每一页的开始下标
        if (pageDto.getPageSize() == null) {
            pageDto.setPageSize(10);
        }
        int startIndex = (pageDto.getPageCurrent() - 1) * pageDto.getPageSize();
        // 5.依据条件获取当前页数据
        List<ExpRemoteDiagnoseRto> records = remoteDiagnoseMapper.findPageObjects(customerName, startIndex, pageDto.getPageSize()
                , registerParentid);// 获取父级id
        // 6.封装数据
        PageObject<ExpRemoteDiagnoseRto> pageObject = new PageObject<>();
        pageObject.setPageCurrent(pageDto.getPageCurrent());
        pageObject.setRowCount(rowCount);
        pageObject.setPageSize(pageDto.getPageSize());
        pageObject.setRecords(records);
        return pageObject;
    }

    /**
     * 通过id获取要修改的数据在页面显示
     */
    @Override
    public ExpRemoteDiagnoseRto select(Integer id) {
        ExpRemoteDiagnoseRto expRemoteDiagnoseRto = remoteDiagnoseMapper.select(id);
        return expRemoteDiagnoseRto;
    }

    /**
     * 修改解决状态
     */
    @Override
    public Boolean validById(ExpRemoteDiagnoseValidDto expRemoteDiagnoseValidDto) {
        ExpRemoteDiagnose expRemoteDiagnose =
                CaseBeanUtils.entityToClass(expRemoteDiagnoseValidDto, ExpRemoteDiagnose.class);
        expRemoteDiagnose.setGmtModified(new Date());
        int i = remoteDiagnoseMapper.updateById(expRemoteDiagnose);
        return BooleanUtil.bool(i);
    }

    /**
     * 删除
     */
    @Override
    public Boolean doDelete(Integer[] ids) {
        List<Integer> list = Arrays.asList(ids);
        List<ExpRemoteDiagnose> expRemoteDiagnoses = remoteDiagnoseMapper.selectBatchIds(list);
        if (ListUtils.isArrayNotEmpty(expRemoteDiagnoses)) {
            expRemoteDiagnoses.stream().forEach(expRemoteDiagnose -> {
                expRemoteDiagnose.setDelTag(1);
                remoteDiagnoseMapper.updateById(expRemoteDiagnose);
            });
        }
        expertReplyService.doDeleteRep(ids);
        symptomsDescribedService.doDeleteSym(ids);
        return true;
    }

    /**
     * 添加
     */
    @Override
    public Boolean doSaveObject(RemoteDiagnoseDto remoteDiagnoseDto) {
        ExpRemoteDiagnose expRemoteDiagnose = CaseBeanUtils.entityToClass(remoteDiagnoseDto, ExpRemoteDiagnose.class);
        expRemoteDiagnose.setGmtCreate(new Date());
        expRemoteDiagnose.setGmtModified(expRemoteDiagnose.getGmtCreate());
        int i = remoteDiagnoseMapper.insert(expRemoteDiagnose);
       
        ExpSymptomsDescribed expSymptomsDescribed = new ExpSymptomsDescribed();
        expSymptomsDescribed.setRegisterUserId(remoteDiagnoseDto.getRegisterUserId());
        expSymptomsDescribed.setRemoteDiagnoseId(expRemoteDiagnose.getId());
        expSymptomsDescribedMapper.insert(expSymptomsDescribed);
        return BooleanUtil.bool(i);
    }

    /**
     * 将修改后的数据放入数据库
     */
    @Override
    public Boolean doUpdate(RemoteDiagnoseDto remoteDiagnoseDto) {
        ExpRemoteDiagnose expRemoteDiagnose = CaseBeanUtils.entityToClass(remoteDiagnoseDto, ExpRemoteDiagnose.class);
        if (expRemoteDiagnose.getId() != null) {
            int i = remoteDiagnoseMapper.updateById(expRemoteDiagnose);
            return BooleanUtil.bool(i);
        }
        return false;
    }

    @Override
    public Boolean selectId(Integer remoteDiagnoseId) {
        ExpRemoteDiagnose expRemoteDiagnose = remoteDiagnoseMapper.selectById(remoteDiagnoseId);
        if (expRemoteDiagnose != null) {
            return true;
        }
        return false;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpRemoteDiagnose> doRemoteDiagnoseMenus(ExpRemoteDiagnoseRto expRemoteDiagnoseRto) {
		QueryWrapper queryWrapper= new QueryWrapper<ExpRemoteDiagnose>();
		queryWrapper.orderByAsc("gmt_modified");
		List<ExpRemoteDiagnose> selectList = remoteDiagnoseMapper.selectList(queryWrapper);
		
		
		return selectList;
	}

	@Override
	public PageObject<ExpRemoteDiagnoseRto> doSelectUserName(ExpRemoteDiagnoseRto expRemoteDiagnoseRto,Integer pageCurrent,Integer pageSize) {
		int startIndex=(pageCurrent-1)*pageSize;
		QueryWrapper<ExpRemoteDiagnose> queryWrapper= new QueryWrapper<ExpRemoteDiagnose>();
		queryWrapper.eq("register_user", expRemoteDiagnoseRto.getRegisterUser());
		Integer pageCount = remoteDiagnoseMapper.selectCount(queryWrapper);
		List<ExpRemoteDiagnoseRto> selectList1 = remoteDiagnoseMapper.doSelectUserName(expRemoteDiagnoseRto.getRegisterUser(),startIndex,pageSize);
		
		PageObject<ExpRemoteDiagnoseRto> pageObject = new PageObject<ExpRemoteDiagnoseRto>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setPageCount(pageCount);
		pageObject.setRecords(selectList1);
		return pageObject;
	}

}
