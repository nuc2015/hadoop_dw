package cn.edu.llxy.dw.dss.dg.web.entity;

public class MetaTreeNode {
	//节点类型-缺省指标版本目录（虚拟）
	public static final String NODE_TYPE_KPI_DEF_PKG = "kpidefversion";
	//节点类型-指标版本目录(来源于版本表)
	public static final String NODE_TYPE_KPI_VER_PKG = "kpiversion";
	
	//节点类型-缺省宽表版本目录（虚拟）
	public static final String NODE_TYPE_BT_DEF_PKG = "btdefversion";	
	//节点类型-宽表版本目录(来源于版本表)
	public static final String NODE_TYPE_BT_VER_PKG = "btversion";
	
	//节点类型-主数据版本目录（虚拟）
	public static final String NODE_TYPE_MD_DEF_PKG = "mddefversion";		
	//节点类型-缺省主数据版本目录(来源于版本表)
	public static final String NODE_TYPE_MD_VER_PKG = "mdversion";
	
	
	//节点类型-缺省表目录（虚拟）
	public static final String NODE_TYPE_RELATION_TABLES = "tables";
	//节点类型-缺省视图目录（虚拟）
	public static final String NODE_TYPE_RELATION_VIEWS = "views";
	
	/**
	 * 概念模型目录
	 */
	public static final String NODE_TYPE_RELATION_CDM = "CDM";
	
	/**
	 * 应用模型目录
	 */
	public static final String NODE_TYPE_RELATION_APP = "APP";
	
	/**
	 * 物理模型目录
	 */
	public static final String NODE_TYPE_RELATION_PDM = "PDM";
	
	/**
	 * 物理数据库目录
	 */
	public static final String NODE_TYPE_RELATION_DB = "DB";
	
	/**
	 * 接口目录
	 */
	public static final String NODE_TYPE_RELATION_ITF = "ITF";
	
	/**
	 * 主机目录
	 */
	public static final String NODE_TYPE_RELATION_HOST = "HOST";
	
	//节点类型-缺省表目录（虚拟）
	public static final String NODE_TYPE_RELATION_DOMAIN = "DOMAIN";
	
	//节点类型-缺省表目录（虚拟）
	public static final String NODE_TYPE_RELATION_PDOMAIN = "PDOMAIN";
	
	public static final String NODE_TYPE_DB_CATEGORY = "CATEGORY";
	
	//节点类型-缺省存储过程目录（虚拟）
	public static final String NODE_TYPE_RELATION_PROCEDURES = "procedures";
	
	//节点类型-业务域目录（虚拟）
	public static final String NODE_TYPE_VER_TABLE_BIZ = "table_biz";
	public static final String NODE_TYPE_VER_VIEW_BIZ = "view_biz";
	public static final String NODE_TYPE_VER_PROCEDURE_BIZ = "procedure_biz";
	//节点类型-数据分层目录（虚拟）
	public static final String NODE_TYPE_VER_TABLE_LAYER = "table_layer";
	public static final String NODE_TYPE_VER_VIEW_LAYER = "view_layer";
	public static final String NODE_TYPE_VER_PROCEDURE_LAYER = "procedure_layer";
	
	//节点类型为Schema
	public static final String NODE_TYPE_SCHEMA = "relation.Schema";
	//节点类型为目录
	public static final String NODE_TYPE_PKG    = "core.BasePackage";
	
	public static final String NODE_TYPE_APPLICATION = "biz.Application";
	
	//虚拟节点
	public static final String NODETYPE_VIR_DIR = "virnode";
	
	//树节点id
	private String id;
	//树节点名称
	private String name;
	//提示
	private String title;
	//是否父节点
	private boolean isParent;
	//节点类型
	private String nodeType;
	//节点样式
	private String iconSkin;
	//节点来源:ver-版本  crt-当前版本
	private String nodeFrom;
	//节点版本
	private String nodeVersion;
	
	private boolean nocheck;
	//父节点id
	private String pId;
	
	private boolean open;
	
	//schema
	private String schema;
	//对应数据源id
	private String dsId;
	
	private boolean checked;
	
	//管理类型，对于字段，p-权限控制字段, n-普通字段
	private String mgrType;

	//字段引用值
	private String refSessionId;
	
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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeFrom() {
		return nodeFrom;
	}

	public void setNodeFrom(String nodeFrom) {
		this.nodeFrom = nodeFrom;
	}

	public String getNodeVersion() {
		return nodeVersion;
	}

	public void setNodeVersion(String nodeVersion) {
		this.nodeVersion = nodeVersion;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getDsId() {
		return dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getMgrType() {
		return mgrType;
	}

	public void setMgrType(String mgrType) {
		this.mgrType = mgrType;
	}

	public String getRefSessionId() {
		return refSessionId;
	}

	public void setRefSessionId(String refSessionId) {
		this.refSessionId = refSessionId;
	}
}
