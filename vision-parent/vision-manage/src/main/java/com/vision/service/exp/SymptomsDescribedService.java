package com.vision.service.exp;


import java.util.List;

import com.vision.dto.SymptomsDescribedDto;
import com.vision.pojo.exp.ExpSymptomsDescribed;

public interface SymptomsDescribedService {

    /**
     * @Author lihd
     * @Description 根据id查询症状描述数据
     * @Date 2019/8/7 20:32
     * @param id
     * @return
     */
    ExpSymptomsDescribed selectSym(Integer id);

    /**
     * @Author lihd
     * @Description 新增症状描述数据
     * @Date 2019/8/7 20:32
     * @param symptomsDescribedDto
     * @return
     */
    Boolean doInsertSym(SymptomsDescribedDto symptomsDescribedDto);

    /**
     * @Author lihd
     * @Description 修改症状描述数据
     * @Date 2019/8/7 20:32
     * @param symptomsDescribedDto
     * @return
     */
    Boolean doUpdateSym(SymptomsDescribedDto symptomsDescribedDto);

    /**
     * @Author lihd
     * @Description 根据id批量删除症状描述数据
     * @Date 2019/8/7 20:33
     * @param remoteDiagnoseId 远程诊断id
     * @return
     */
    Boolean doDeleteSym(Integer[] remoteDiagnoseId);

	List<ExpSymptomsDescribed> selectSymAll(ExpSymptomsDescribed expSymptomsDescribed);

}
