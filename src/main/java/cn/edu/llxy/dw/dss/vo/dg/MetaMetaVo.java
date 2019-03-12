package cn.edu.llxy.dw.dss.vo.dg;

import java.util.Date;

public class MetaMetaVo {
	public static final String META_MDL_PKG = "core.BasePackage";
	//指标
	public static final String META_MDL_KPI = "biz.Kpi";
	//宽表
	public static final String META_MDL_BIGTABLE = "biz.BigTable";
	//主数据
	public static final String META_MDL_MASTERDATA = "biz.MasterData";
	//关系表
	public static final String META_MDL_TABLE = "relation.Table";
	//关系Schema
	public static final String META_MDL_SCHEME = "relation.Schema";
	//关系数据源
	public static final String META_MDL_DATASOURCE = "relation.DataSource";
	//关系字段
	public static final String META_MDL_COLUMN = "relation.Column";
	
	private String name;
	
	private String chName;
	
	private String cmt;
	
	private String def;
	
	private Date crtDate;
	
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
