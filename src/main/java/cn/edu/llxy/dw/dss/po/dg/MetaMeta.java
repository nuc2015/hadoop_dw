package cn.edu.llxy.dw.dss.po.dg;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PPRT_DG_META_META")
public class MetaMeta implements java.io.Serializable{
	@Id
	@GeneratedValue(generator = "metaGenerator")    
	@GenericGenerator(name = "metaGenerator", strategy = "assigned")
	private String name;
	
	@Column(name = "CH_NAME")
	private String chName;
	
	private String cmt;
	
	private String def;
	
	@Column(name = "CRT_DATE")
	private Date crtDate;
	
	@Column(name = "SUB_NODE")
	private String subNode;
	
	//实现实例
	private String clz;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getCmt() {
		return cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public String getDef() {
		return def;
	}

	public void setDef(String def) {
		this.def = def;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public String getSubNode() {
		return subNode;
	}

	public void setSubNode(String subNode) {
		this.subNode = subNode;
	}
	
	public String getClz() {
		return clz;
	}

	public void setClz(String clz) {
		this.clz = clz;
	}
}