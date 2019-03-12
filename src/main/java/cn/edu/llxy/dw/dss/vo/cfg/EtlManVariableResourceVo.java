package cn.edu.llxy.dw.dss.vo.cfg;

import java.util.Date;

public class EtlManVariableResourceVo {

	private String id;
	private String varName;
	private String varCategory;
	private String varKey;
	private String varValue;
	private String varDescribe;
	private String updateTime;


	private Date createTime;
	private String createUserId;
	private String createMttId;
	private String updateUserId;
	private String updateMttId;
	
	/** default constructor */
	public EtlManVariableResourceVo() {
	}

	/** minimal constructor */
	public EtlManVariableResourceVo(String id) {
		this.id = id;
	}

	/** full constructor */
	public EtlManVariableResourceVo(String id, String varName, String varCategory, String varKey, String varValue,
			String varDescribe) {
		this.id = id;
		this.varName = varName;
		this.varCategory = varCategory;
		this.varKey = varKey;
		this.varValue = varValue;
		this.varDescribe = varDescribe;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVarName() {
		return this.varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getVarCategory() {
		return this.varCategory;
	}

	public void setVarCategory(String varCategory) {
		this.varCategory = varCategory;
	}

	public String getVarKey() {
		return this.varKey;
	}

	public void setVarKey(String varKey) {
		this.varKey = varKey;
	}

	public String getVarValue() {
		return this.varValue;
	}

	public void setVarValue(String varValue) {
		this.varValue = varValue;
	}

	public String getVarDescribe() {
		return this.varDescribe;
	}

	public void setVarDescribe(String varDescribe) {
		this.varDescribe = varDescribe;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateMttId() {
		return createMttId;
	}

	public void setCreateMttId(String createMttId) {
		this.createMttId = createMttId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateMttId() {
		return updateMttId;
	}

	public void setUpdateMttId(String updateMttId) {
		this.updateMttId = updateMttId;
	}

	
}