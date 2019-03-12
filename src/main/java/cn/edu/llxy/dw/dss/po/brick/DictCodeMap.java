package cn.edu.llxy.dw.dss.po.brick;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pprt_core_dict_code_map")
public class DictCodeMap {
	@Id
	@GeneratedValue(generator = "default")
	private int cid;
	
	private String code;
	
	@Column(name="class_code")
	private String classCode;
	
	private String name;
	
	@Column(name="quick_code")
	private String quickCode;

	@Column(name="ord_num")
	private String ordNum;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuickCode() {
		return quickCode;
	}

	public void setQuickCode(String quickCode) {
		this.quickCode = quickCode;
	}

	public String getOrdNum() {
		return ordNum;
	}

	public void setOrdNum(String ordNum) {
		this.ordNum = ordNum;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
}
