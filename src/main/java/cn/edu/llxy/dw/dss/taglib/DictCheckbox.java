package cn.edu.llxy.dw.dss.taglib;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import cn.edu.llxy.dw.dss.base.brick.service.IDictService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DictCheckbox extends TagSupport {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典定义编码
	 */
	private String dictCode = null;

	/**
	 * 字典范围
	 */
	private String scopeId = null;

	/**
	 * 选中值
	 */
	private String selectedCode = "";
	
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 锟街碉拷锟斤拷锟紹eanName
	 */
	private String dvs = "dictService";

	public int doStartTag() throws JspException {
		if (dictCode == null)
			return SKIP_BODY;
		WebApplicationContext wac = (WebApplicationContext) WebApplicationContextUtils
				.getWebApplicationContext(pageContext.getServletContext());
		IDictService dictViewService = (IDictService) wac.getBean(dvs);
		Map<String, String> codeMap = null;
		try {
			codeMap = dictViewService.getDictMap(dictCode);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JspWriter out = pageContext.getOut();
		try {
			if (codeMap.size() > 0) {
				//out.print("<checkbox selected=\'selected\'></option>");
				for (Map.Entry<String, String> entry : codeMap.entrySet()) {

					if (selectedCode.equals(entry.getKey())) {
						out.print("<input type='checkbox' checked=\'true\' name=\'"+getName()+"\' id=\'"+getName()+"\' value=\'" + entry.getKey() + "\'/>" + entry.getValue()
								);
					} else {
						out.print("<input type='checkbox' name=\'"+getName()+"\' id=\'"+getName()+"\' value=\'" + entry.getKey() + "\'/>" + entry.getValue());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getSelectedCode() {
		return selectedCode;
	}

	public void setSelectedCode(String selectedCode) {
		this.selectedCode = selectedCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScopeId() {
		return scopeId;
	}

	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}

	public String getDvs() {
		return dvs;
	}

	public void setDvs(String dvs) {
		this.dvs = dvs;
	}
	
}
