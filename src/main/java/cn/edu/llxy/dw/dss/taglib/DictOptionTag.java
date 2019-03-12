package cn.edu.llxy.dw.dss.taglib;

import java.io.*;
import java.util.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import cn.edu.llxy.dw.dss.base.brick.service.IDictService;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;


public class DictOptionTag extends TagSupport {

	private String dictCode;

	private String scopeId = null;

	private String selectedCode = "";

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
				//out.print("<option selected=\'selected\'></option>");
				for (Map.Entry<String, String> entry : codeMap.entrySet()) {

					if (selectedCode.equals(entry.getKey())) {
						out.print("<option selected=\'selected\' value=\'" + entry.getKey() + "\'>" + entry.getValue()
								+ "</option>");
					} else {
						out.print("<option value=\'" + entry.getKey() + "\'>" + entry.getValue() + "</option>");
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

}
