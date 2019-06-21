package com.vision.vo;

import java.io.Serializable;
import java.util.Date;

public class DiagnosisDate implements Serializable{
	private static final long serialVersionUID = 1L;
	/**症状id*/
	private Integer id;
	/**症状名称*/
	private String symptomName;
	/**父级症状id*/
	private Integer parentId;
	/**显示类型 1显示症状描述 0不显示症状描述*/
	private Integer disType;
	/**症状描述*/
	private String diagnosisDesc;
	/**创建时间*/
	private Date gmtCreate;
	/**修改时间*/
	private Date gmtModified;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSymptomName() {
		return symptomName;
	}
	public void setSymptomName(String symptomName) {
		this.symptomName = symptomName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getDisType() {
		return disType;
	}
	public void setDisType(Integer disType) {
		this.disType = disType;
	}
	public String getDiagnosisDesc() {
		return diagnosisDesc;
	}
	public void setDiagnosisDesc(String diagnosisDesc) {
		this.diagnosisDesc = diagnosisDesc;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	@Override
	public String toString() {
		return "DiagnosisDate [id=" + id + ", symptomName=" + symptomName + ", parentId=" + parentId + ", disType="
				+ disType + ", diagnosisDesc=" + diagnosisDesc + ", gmtCreate=" + gmtCreate + ", gmtModified="
				+ gmtModified + "]";
	}
	
	
}
