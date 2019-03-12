package cn.edu.llxy.dw.dss.dg.web.entity;

import java.util.List;

import cn.edu.llxy.dw.dss.vo.dg.MetaMetaVo;
import cn.edu.llxy.dw.dss.vo.dg.MetaRelVo;
import cn.edu.llxy.dw.dss.vo.dg.meta.relation.Column;

public class MetaNode {
	//属性值列表
	private List<MetaProp> properties;
	//元模型
	private MetaMetaVo meta;
	//元数据路径
	private String nameSpace;
	
	private List<Column> columns;
	
	private List keys;
	
	private List index;
	
	private String nodeId;
	
	private String name;
	
	private String chName;
	
	private String result;
	
	private String msg;
	
	private String metaMdl;
	
	private String sql;
	
	private String dbType;
	
	private String[] models;
	
	private String pkinfo;
	
	private List<MetaRelVo> relations;
	
	public String getPkinfo() {
		return pkinfo;
	}
	public void setPkinfo(String pkinfo) {
		this.pkinfo = pkinfo;
	}
	public String[] getModels() {
		return models;
	}
	public void setModels(String[] models) {
		this.models = models;
	}
	public List<MetaRelVo> getRelations() {
		return relations;
	}
	public void setRelations(List<MetaRelVo> relations) {
		this.relations = relations;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getMetaMdl() {
		return metaMdl;
	}
	public void setMetaMdl(String metaMdl) {
		this.metaMdl = metaMdl;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * 被依赖对象
	 */
	private List dependee;
	
	/**
	 * 依赖对象
	 */
	private List depend;
	
	public List<MetaProp> getProperties() {
		return properties;
	}
	public void setProperties(List<MetaProp> properties) {
		this.properties = properties;
	}
	public MetaMetaVo getMeta() {
		return meta;
	}
	public void setMeta(MetaMetaVo meta) {
		this.meta = meta;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public List getKeys() {
		return keys;
	}
	public void setKeys(List keys) {
		this.keys = keys;
	}
	public List getIndex() {
		return index;
	}
	public void setIndex(List index) {
		this.index = index;
	}
	public List getDependee() {
		return dependee;
	}
	public void setDependee(List dependee) {
		this.dependee = dependee;
	}
	public List getDepend() {
		return depend;
	}
	public void setDepend(List depend) {
		this.depend = depend;
	}
}
