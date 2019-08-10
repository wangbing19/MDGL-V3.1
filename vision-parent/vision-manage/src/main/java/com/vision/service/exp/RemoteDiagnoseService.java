package com.vision.service.exp;

import com.vision.dto.ExpRemoteDiagnoseValidDto;
import com.vision.dto.PageDto;
import com.vision.dto.RemoteDiagnoseDto;
import com.vision.rto.ExpRemoteDiagnoseRto;
import com.vision.vo.PageObject;

public interface RemoteDiagnoseService {

    PageObject<ExpRemoteDiagnoseRto> findPageObjects(String customerName, Integer registerParentid, PageDto pageDto);

    /**
     * @param id
     * @return
     * @Author lihd
     * @Description 通过id查询远程诊断数据
     * @Date 2019/8/7 20:29
     */
    ExpRemoteDiagnoseRto select(Integer id);

    /**
     * @param
     * @return
     * @Author lihd
     * @Description 修改远程诊断状态
     * @Date 2019/8/7 20:30
     */
    Boolean validById(ExpRemoteDiagnoseValidDto expRemoteDiagnoseValidDto);

    /**
     * @param ids
     * @return
     * @Author lihd
     * @Description 根据id批量删除远程诊断表数据
     * @Date 2019/8/7 20:30
     */
    Boolean doDelete(Integer[] ids);

    /**
     * @param remoteDiagnoseDto
     * @return
     * @Author lihd
     * @Description 新增远程诊断数据
     * @Date 2019/8/7 20:31
     */
    Boolean doSaveObject(RemoteDiagnoseDto remoteDiagnoseDto);

    /**
     * @param remoteDiagnoseDto
     * @return
     * @Author lihd
     * @Description 修改远程诊断数据
     * @Date 2019/8/7 20:31
     */
    Boolean doUpdate(RemoteDiagnoseDto remoteDiagnoseDto);

    /**
     * @param remoteDiagnoseId
     * @return
     * @Author lihd
     * @Description 查询该id是否存在
     * @Date 2019/8/10 14:51
     */
    Boolean selectId(Integer remoteDiagnoseId);
}
