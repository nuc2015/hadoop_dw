package cn.edu.llxy.dw.dss.po.sys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 用户实体类
 */
@Entity
@Table
public class User {
	//主键id
	@Id
	@GeneratedValue
	private String id;
	
	private String name;
	
	private String pwd;

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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
