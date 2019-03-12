package cn.edu.llxy.dw.dss.po.sys;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "v_hadoopgroup_project_relation")
public class HadoopGroup {
	@Column(name = "userName")
	private String userName;
	@Column(name = "userGroup")
	private String userGroup;
	@Id
	@GeneratedValue(generator = "hadoopUserGenerator")
	@GenericGenerator(name = "hadoopUserGenerator", strategy = "uuid")
	private String projectId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
