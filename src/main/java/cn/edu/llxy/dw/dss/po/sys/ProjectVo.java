package cn.edu.llxy.dw.dss.po.sys;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "omc_project")
public class ProjectVo {
	@Id
	private String id;
	private String name;
	private String description;
	private String demander;
	private String demander_user;
	private String developer;
	private String note;
	private String hadoop_user;
	private String owner_group;
	private String create_user;
	private Date create_time;
	private String update_user;
	private Date update_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDemander() {
		return demander;
	}

	public void setDemander(String demander) {
		this.demander = demander;
	}

	public String getDemander_user() {
		return demander_user;
	}

	public void setDemander_user(String demander_user) {
		this.demander_user = demander_user;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getHadoop_user() {
		return hadoop_user;
	}

	public void setHadoop_user(String hadoop_user) {
		this.hadoop_user = hadoop_user;
	}

	public String getOwner_group() {
		return owner_group;
	}

	public void setOwner_group(String owner_group) {
		this.owner_group = owner_group;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
