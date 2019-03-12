package cn.edu.llxy.dw.dss.po.dg;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统参数表，用于存储系统运行时的参数设置
 */
@Entity
@Table(name = "PPRT_DG_PARA")
public class Para {
	@Id
	@GeneratedValue(generator = "paraGenerator")    
	@GenericGenerator(name = "paraGenerator", strategy = "assigned")
	private String name;
	
	private String value;
	
	private String cmt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCmt() {
		return cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
}
