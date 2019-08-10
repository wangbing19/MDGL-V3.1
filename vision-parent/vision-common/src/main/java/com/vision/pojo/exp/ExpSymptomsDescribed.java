package com.vision.pojo.exp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName(value = "exp_symptoms_described")
public class ExpSymptomsDescribed {
    /**
     * id
     */
    @TableId(type= IdType.AUTO)
    private Integer id;
    /**
     * 远程诊断表序号
     */
    private Integer remoteDiagnoseId;
    /**
     * 症状描述
     */
    private String symptomsDescribed;
    /**
     * 症状备注
     */
    private String symptomsRemark;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 删除标识，默认为0
     */
    private int delTag;

    public class Meta {
        public static final String id = "id";
        public static final String remoteDiagnoseId = "remote_diagnose_id";
        public static final String symptomsDescribed = "symptoms_described";
        public static final String symptomsRemark = "symptoms_remark";
        public static final String gmtCreate = "gmt_create";
        public static final String gmtModified = "gmt_modified";
        public static final String delTag = "del_tag";
    }
}
