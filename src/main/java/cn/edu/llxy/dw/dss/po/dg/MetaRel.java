package cn.edu.llxy.dw.dss.po.dg;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PPRT_DG_META_REL")
public class MetaRel {
	
	@Id
	@GeneratedValue(generator = "metaRelGenerator")
	@GenericGenerator(name = "metaRelGenerator", strategy = "uuid")
	private String rid;
	
	private String src;
	
	@Column(name = "SRC_NAME")
	private String srcName;
	
	private String dest;
	
	@Column(name = "DEST_NAME")
	private String destName;
	
	@Column(name = "REL_TYPE")
	private String relType;
	
	private String cmt;
	
	/**
	 * 计算方式 如：avg，sum，count等
	 */
	@Column(name = "CAL_TYPE")
	private String calType;
	
	/**
	 * 关系详情，如对应字段
	 */
	@Column(name = "REL_DTL")
	private String relDtl;
	
	/**
	 * 关系条件 ，如指标宽表关系中的 操作条件 
	 */
	@Column(name = "REL_CND")
	private String relCnd;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CRT_DATE")
	private Date crtDate;
	
	/**
	 * 创建人
	 */
	@Column(name = "CRT_USER")
	private String crtUser;
	
	/**
	 * 关系组
	 */
	private String rgroup;
	
	/**
	 * 引用JOB
	 */
	@Column(name = "REF_JOB")
	private String refJob;
	
	@Transient
	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
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

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
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