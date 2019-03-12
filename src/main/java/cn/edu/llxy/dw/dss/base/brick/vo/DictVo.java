package cn.edu.llxy.dw.dss.base.brick.vo;

import java.util.List;

public class DictVo {
	private String classCode;

	private String name;
	
	private List<DictCodeMapVo> codeMapVos;

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

	public List<DictCodeMapVo> getCodeMapVos() {
		return codeMapVos;
	}

	public void setCodeMapVos(List<DictCodeMapVo> codeMapVos) {
		this.codeMapVos = codeMapVos;
	}
}
