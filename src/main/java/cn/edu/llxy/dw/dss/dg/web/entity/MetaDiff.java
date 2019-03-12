package cn.edu.llxy.dw.dss.dg.web.entity;

public class MetaDiff {
	//序号
	private String idx;
	//元数据字段
	private String metaClm;
	//部署字段
	private String dplClm;
	//是否一致  0-不一致  1-一致
	private String diffResult;
	//差异详情
	private String diffCmt;

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getMetaClm() {
		return metaClm;
	}

	public void setMetaClm(String metaClm) {
		this.metaClm = metaClm;
	}

	public String getDplClm() {
		return dplClm;
	}

	public void setDplClm(String dplClm) {
		this.dplClm = dplClm;
	}

	public String getDiffResult() {
		return diffResult;
	}

	public void setDiffResult(String diffResult) {
		this.diffResult = diffResult;
	}

	public String getDiffCmt() {
		return diffCmt;
	}

	public void setDiffCmt(String diffCmt) {
		this.diffCmt = diffCmt;
	}
}
