package cn.edu.llxy.dw.dss.po.dg;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "pprt_dg_meta_exprule")
public class MetaExportRule {
	@Id
	@GeneratedValue(generator = "metaRelGenerator")
	@GenericGenerator(name = "metaRelGenerator", strategy = "uuid")
	private String code;
	
	@Column(name = "meta_model")
	private String metaMdl;
	
	@Column(name = "meta_sheet_name")
	private String sheetName;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMetaMdl() {
		return metaMdl;
	}

	public void setMetaMdl(String metaMdl) {
		this.metaMdl = metaMdl;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
}
