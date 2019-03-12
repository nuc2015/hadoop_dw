package cn.edu.llxy.dw.dss.po.brick;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pprt_core_dict")
public class Dict {
	@Id
	@GeneratedValue(generator = "default")
	@Column(name="class_code")
	private String classCode;

	private String name;
	
	@OneToMany    
    @JoinColumn(name="class_code")
	private List<DictCodeMap> codeMaps;

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DictCodeMap> getCodeMaps() {
		return codeMaps;
	}

	public void setCodeMaps(List<DictCodeMap> codeMaps) {
		this.codeMaps = codeMaps;
	}
}

