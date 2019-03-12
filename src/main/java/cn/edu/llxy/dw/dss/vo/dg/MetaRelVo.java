package cn.edu.llxy.dw.dss.vo.dg;

import java.util.Date;

public class MetaRelVo {
	//应用指标关系
	public static final String REL_TYPE_APP_REF_KPI = "ARK";//dest:app,src:kpi
	//指标宽表关系
	public static final String REL_TYPE_KPI_REF_BG = "KRB";
	//宽表流程关系
	public static final String REL_TYPE_BG_REF_FLOW = "BRF";
	//接口层映射关系
	public static final String REL_TYPE_INF_REF_FILE = "IRF";
	//整合层映射关系
	public static final String REL_TYPE_DWD_REF_INF = "DRI";//dest:dwd,src:inf
	//中间层映射关系
	public static final String REL_TYPE_MID_REF_DWD = "MRD";//dest:mid,src:dwd
	
	//宽表和稽核间的关系
	public static final String REL_TYPE_BG_REF_RULE = "BRU";

	private String rid;
	
	private String src;
	
	private String srcName;
	
	private String dest;
	
	private String destName;
	
	private String relType;
	
	private String cmt;
	
	/**
	 * 计算方式 如：avg，sum，count等
	 */
	private String calType;
	
	/**
	 * 关系详情，如对应字段
	 */
	private String relDtl;
	
	/**
	 * 关系条件 ，如指标宽表关系中的 操作条件 
	 */
	private String relCnd;
	
	/**
	 * 创建时间
	 */
	private Date crtDate;
	
	/**
	 * 创建人
	 */
	private String crtUser;
	
	/**
	 * 关系组
	 */
	private String rgroup;
	
	/**
	 * 引用 对象 
	 */
	private String refJob;
	
	public String getRefJob() {
		return refJob;
	}

	public void setRefJob(String refJob) {
		this.refJob = refJob;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getRelType() {
		return relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	public String getCmt() {
		return cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public String getCalType() {
		return calType;
	}

	public void setCalType(String calType) {
		this.calType = calType;
	}

	public String getRelDtl() {
		return relDtl;
	}

	public void setRelDtl(String relDtl) {
		this.relDtl = relDtl;
	}

	public String getRelCnd() {
		return relCnd;
	}

	public void setRelCnd(String relCnd) {
		this.relCnd = relCnd;
	}

	public Date getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}

	public String getCrtUser() {
		return crtUser;
	}

	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}

	public String getRgroup() {
		return rgroup;
	}

	public void setRgroup(String rgroup) {
		this.rgroup = rgroup;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getSrcName() {
		return srcName;
	}

	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}
}
