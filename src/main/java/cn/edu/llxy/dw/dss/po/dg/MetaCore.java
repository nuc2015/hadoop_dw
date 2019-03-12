package cn.edu.llxy.dw.dss.po.dg;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PPRT_DG_META_CORE")
public class MetaCore implements java.io.Serializable{
	@Id
	@GeneratedValue(generator = "metaRelGenerator")
	@GenericGenerator(name = "metaRelGenerator", strategy = "uuid")
	private String id;
	
	private String name;
	
	private String cycle;
	
	@Column(name = "CH_NAME")
	private String chName;
	
	private String type;
	
	@Column(name = "META_MDL")
	private String metaMdl;
	
	private String entity;
	
	@Column(name = "PRT_ID")
	private String prtId;
	
	private String cmt;
	
	@Column(name = "CRT_DATE")
	private Date crtDate;
	
	@Column(name = "LAST_UPDATE")
	private Date lastUpdate;
	
	@Column(name = "BIZ_DOMAIN")
	private String bizDomain;
	
	@Column(name = "BIZ_NO")
	private String bizNo;
	
	private String namespace;
	
	@Column(name = "REF_OBJECT")
	private String refObject;
	
	//源表(视图)-抽取\接口表-加载
	@Column(name = "USE_TABLE")
	public String useTable;
	//文件名
	@Column(name = "FILE_NAME")
	public String fileName;
	//文件头
	@Column(name = "FILE_HEADER")
	public String fileHeader;
	
	//普通,重要
	public String lvel;
	
	@Column(name = "CRT_USER")
	public String crtUser;
	
	@Column(name = "TENANTID")
	private String tenantid;
	
	@Column(name = "MGR_TYPE")
	private String mgrType;
	
	@Column(name = "PREC")
	private Integer precision;
	
	@Column(name = "DATA_TYPE")
	private String dataType;
	
	private Integer length;
	
	@Column(name = "FMT_RULE")
	private String fmtRule;
	
	@Column(name = "DEFAULT_VIEW")
	private String defaultView;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	@Column(name = "LAYER")
	private String layer;
	
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
	
}

