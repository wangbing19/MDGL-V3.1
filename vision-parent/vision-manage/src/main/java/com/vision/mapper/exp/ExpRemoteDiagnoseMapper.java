package com.vision.mapper.exp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.exp.ExpExpertReply;
import com.vision.pojo.exp.ExpRemoteDiagnose;
import com.vision.rto.ExpRemoteDiagnoseRto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExpRemoteDiagnoseMapper extends BaseMapper<ExpRemoteDiagnose> {
    ExpRemoteDiagnoseRto select(Integer id);
    /**
     * 查询对应客户姓名的数量,如果没输入客户姓名则查询远程诊断表中的所有数量
     * @param customerName:客户姓名
     * @return
     */
    int getRowCount(
            @Param("customerName") String customerName,
            @Param("registerParentid")Integer registerParentid);

    /**
     *不输入客户名字的话默认是查询出全部信息
     * @param customerName 客户名字
     * @param startIndex 当前页的第一个信息对应的门店诊断序号
     * @param pageSize 每一页显示的数量
     * @return
     */
    List<ExpRemoteDiagnoseRto> findPageObjects(
            @Param("customerName") String customerName,
            @Param("startIndex")Integer startIndex,
            @Param("pageSize")Integer pageSize,
            @Param("registerParentid")Integer registerParentid);
	List<ExpRemoteDiagnoseRto> doSelectUserName( @Param("registerUser")String registerUser,@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);
}
