package cn.edu.llxy.dw.dss.vo.dg.meta.relation;

import java.util.List;

public class Model {
	private List<Table> tables;
	
	private List<View> views;

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public List<View> getViews() {
		return views;
	}

	public void setViews(List<View> views) {
		this.views = views;
	}
}
