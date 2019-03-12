package cn.edu.llxy.dw.dss.vo.dg;

import java.util.Date;

public class MetaCoreVo {
	/**
	 * 元数据管理类型-公共
	 */
	public final static String META_MGR_TYPE_PUBLIC = "o";
	
	/**
	 * 元数据管理类型-个人
	 */
	public final static String META_MGR_TYPE_PRIVATE = "p";
	
	/**
	 * 元数据管理类型-企业
	 */
	public final static String META_MGR_TYPE_ENTERPRISE  = "e";
	
	//数据沙盒
	public final static String DATA_BOX  = "dataBox";
	//公共共享沙盒
	public final static String COMMON_BOX  = "bgdb";
	
	private String id;
	
	private String name;
	
	private String cycle;
	
	private String chName;
	
	private String type;
	
	private String metaMdl;
	
	private String entity;
	
	private String prtId;
	
	private String cmt;
	
	private Date crtDate;
	
	private Date lastUpdate;
	
	//业务分域
	private String bizDomain;
	
	private String bizNo;
	
	private String namespace;
	
	private String refObject;
	
	//源表(视图)-抽取\接口表-加载
	public String useTable;
	//文件名
	public String fileName;
	//文件头
	public String fileHeader;
	
	//普通,重要
	public String lvel;
	
	public String crtUser;
	
	private String tenantid;

	//业务类型，如个人元数据、公共元数据、管理元数据等
	private String mgrType;
	
	private Integer precision;
	
	private String dataType;
	
	private Integer length;
	
	private String fmtRule;
	
	private String defaultView;
	
	private String remarks;
	
	//功能分层
	private String layer;
	
	//for jquery taginput
	private String label;
	
	private String value;
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

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

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getChName() {
		return chName;
	}

	public void setChName(String chName) {
		this.chName = chName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMetaMdl() {
		return metaMdl;
	}

	public void setMetaMdl(String metaMdl) {
		this.metaMdl = metaMdl;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getPrtId() {
		return prtId;
	}

	public void setPrtId(String prtId) {
		this.prtId = prtId;
	}

	public String getCmt() {
		return cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getBizDomain() {
		return bizDomain;
	}

	public void setBizDomain(String bizDomain) {
		this.bizDomain = bizDomain;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getRefObject() {
		return refObject;
	}

	public void setRefObject(String refObject) {
		this.refObject = refObject;
	}

	public String getUseTable() {
		return useTable;
	}

	public void setUseTable(String useTable) {
		this.useTable = useTable;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileHeader() {
		return fileHeader;
	}

	public void setFileHeader(String fileHeader) {
		this.fileHeader = fileHeader;
	}

	public String getLvel() {
		return lvel;
	}

	public void setLvel(String lvel) {
		this.lvel = lvel;
	}

	public String getCrtUser() {
		return crtUser;
	}

	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public String getMgrType() {
		return mgrType;
	}

	public void setMgrType(String mgrType) {
		this.mgrType = mgrType;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getFmtRule() {
		return fmtRule;
	}

	public void setFmtRule(String fmtRule) {
		this.fmtRule = fmtRule;
	}

	public String getDefaultView() {
		return defaultView;
	}

	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	@Override
	public String toString() {
		return "MetaCoreVo{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", cycle='" + cycle + '\'' +
				", chName='" + chName + '\'' +
				", type='" + type + '\'' +
				", metaMdl='" + metaMdl + '\'' +
				", entity='" + entity + '\'' +
				", prtId='" + prtId + '\'' +
				", cmt='" + cmt + '\'' +
				", crtDate=" + crtDate +
				", lastUpdate=" + lastUpdate +
				", bizDomain='" + bizDomain + '\'' +
				", bizNo='" + bizNo + '\'' +
				", namespace='" + namespace + '\'' +
				", refObject='" + refObject + '\'' +
				", useTable='" + useTable + '\'' +
				", fileName='" + fileName + '\'' +
				", fileHeader='" + fileHeader + '\'' +
				", lvel='" + lvel + '\'' +
				", crtUser='" + crtUser + '\'' +
				", tenantid='" + tenantid + '\'' +
				", mgrType='" + mgrType + '\'' +
				", precision=" + precision +
				", dataType='" + dataType + '\'' +
				", length=" + length +
				", fmtRule='" + fmtRule + '\'' +
				", defaultView='" + defaultView + '\'' +
				", remarks='" + remarks + '\'' +
				", layer='" + layer + '\'' +
				", label='" + label + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
