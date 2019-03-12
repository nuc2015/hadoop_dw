package cn.edu.llxy.dw.dss.taglib;

import java.io.*;
import java.util.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import cn.edu.llxy.dw.dss.base.brick.service.IDictService;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;


/**
 * @project: sc
 * @description: 
 */
public class DictSelectTag extends TagSupport {
	
	/**
	 * 锟斤拷锟斤拷锟斤拷锟结交name
	 */
	private String name;
	/**
	 * 锟斤拷签锟侥憋拷
	 */
	private String label;
	/**
	 * 锟街碉拷锟斤拷锟�
	 */
	private String dictCode;
	/**
	 * 锟斤拷选锟斤拷锟斤拷值锟斤拷锟斤拷
	 */
	private String selectedCode = "";
	/**
	 * 锟斤拷证锟斤拷锟�
	 */
	private String rules;
	/**
	 * 锟斤拷证锟斤拷锟斤拷锟较�
	 */
	private String title;
	
	/**
	 * 级联下级菜单
	 */
	private String cascade;
	
	/**
	 * 锟斤拷围锟斤拷锟斤拷
	 */
	private String scopeId = null;
	/**
	 * 锟街碉拷锟斤拷锟紹eanName
	 */
	private String dvs = "dictService";

	public int doStartTag() throws JspException {
		if (dictCode == null || name == null)	return SKIP_BODY;
		genSelectHead();
		genOption();
		return SKIP_BODY;
	}
	
	public int doEndTag(){
		getSelectFoot();
		return EVAL_PAGE;
	}
	
	private void genOption(){
		WebApplicationContext wac = (WebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		IDictService dictViewService = (IDictService) wac.getBean(dvs);
		
		Map<String, String> codeMap = new HashMap();
		try {
			codeMap = dictViewService.getDictMap(dictCode);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JspWriter out = pageContext.getOut();
		if (codeMap.size() > 0) {
			for (Map.Entry<String, String> entry : codeMap.entrySet()) {
				try {
					if(selectedCode.equals(entry.getKey())){
						out.print("<option selected=\'selected\' value=\'" + entry.getKey() + "\'>"+ entry.getValue() + "</option>");
					}else{
						out.print("<option value=\'" + entry.getKey() + "\'>"+ entry.getValue() + "</option>");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void getSelectFoot() {
		JspWriter out = pageContext.getOut();
		try {
			out.print("</select>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void genSelectHead() {
		JspWriter out = pageContext.getOut();
		try {
			if(getLabel() == null) this.setLabel("");
			out.print("<label for=\'"+getName()+"\'>"+getLabel()+"</label>");
			out.print("<select id=\'"+getName()+"\' name=\'"+getName()+"\'");
			if(this.getRules() != null) out.print(" rules=\'" +this.getRules()+ "\'");
			if(this.getTitle() != null) out.print(" title=\'"+this.getTitle()+"\'");
			out.print(">");
			out.print("<option selected=\'selected\'></option>");
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getScopeId() {
		return scopeId;
	}

	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}

}
