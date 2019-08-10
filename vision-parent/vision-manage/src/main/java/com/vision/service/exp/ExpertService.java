package com.vision.service.exp;

import com.vision.dto.ExpExpertDto;
import com.vision.dto.PageDto;
import com.vision.pojo.exp.ExpExpert;
import com.vision.vo.Node;
import com.vision.vo.PageObject;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ExpertService {
    /**
     * @param
     * @return
     * @Author lihd
     * @Description ztree树
     * @Date 2019/8/10 18:01
     */
    Node[] selectExpName();

    /**
     * @param expertName 专家姓名
     * @param pageDto
     * @return
     * @Author lihd
     * @Description
     * @Date 2019/8/10 17:28
     */
    PageObject<ExpExpert> limitExp(String expertName, PageDto pageDto);

    /**
     * @param id 专家表id
     * @return
     * @Author lihd
     * @Description 根据id查询专家表信息
     * @Date 2019/8/1 20:15
     */
    ExpExpert doSelectExp(Integer id);

    /**
     * @param ids 专家id数组
     * @return
     * @Author lihd
     * @Description 根据id数组删除专家信息
     * @Date 2019/8/1 20:16
     */
    Boolean doDeleteExp(Integer[] ids);

    /**
     * @param expExpertDto 专家信息dto类
     * @return
     * @Author lihd
     * @Description 添加专家信息
     * @Date 2019/8/1 20:43
     */
    Boolean doSaveObject(ExpExpertDto expExpertDto);

    /**
     * @param expExpertDto 专家信息dto类
     * @return
     * @Author lihd
     * @Description 修改专家信息
     * @Date 2019/8/1 20:44
     */
    Boolean doUpdateMessage(ExpExpertDto expExpertDto);
}
