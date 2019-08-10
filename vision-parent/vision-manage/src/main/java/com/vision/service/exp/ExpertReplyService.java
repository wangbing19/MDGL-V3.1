package com.vision.service.exp;


import com.vision.dto.ExpertReplyDto;
import com.vision.exception.ServiceException;
import com.vision.pojo.exp.ExpExpertReply;

public interface ExpertReplyService {

    /**
     * @param remoteDiagnoseId 远程诊断表id
     * @return
     * @Author lihd
     * @Description 根据remoteDiagnoseId查询专家回复表数据
     * @Date 2019/8/7 20:27
     */
    ExpExpertReply selectRep(Integer remoteDiagnoseId);

    /**
     * @param entity
     * @return
     * @Author lihd
     * @Description 新增专家回复数据
     * @Date 2019/8/7 20:27
     */
    Boolean doInsertRep(ExpertReplyDto entity) throws ServiceException;

    /**
     * @param id
     * @return
     * @Author lihd
     * @Description 根据id批量删除专家回复数据
     * @Date 2019/8/7 20:28
     */
    Boolean doDeleteRep(Integer[] id);

    /**
     * @param entity
     * @return
     * @Author lihd
     * @Description 修改专家回复数据
     * @Date 2019/8/7 20:28
     */
    Boolean doUpdateRep(ExpertReplyDto entity);

}
