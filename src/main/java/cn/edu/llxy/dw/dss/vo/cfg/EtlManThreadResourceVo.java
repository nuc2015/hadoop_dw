package cn.edu.llxy.dw.dss.vo.cfg;

import java.util.Date;

public class EtlManThreadResourceVo {

	private String id;
	private String groupName;
	private Integer amount;
	private String notes;
	

	private Date createTime;
	private String createUserId;
	private String createMttId;
	private Date updateTime;
	private String updateUserId;
	private String updateMttId;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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