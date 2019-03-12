package cn.edu.llxy.dw.dss.vo.adhoc;

import java.util.List;

/**
 * 图形对象
 */
public class ChartData {
	private String[] legends ;
	
	private String chartType;
	
	private String[] dimElements;
	
	private List<Serie> series;
	
	private String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String[] getDimElements() {
		return dimElements;
	}

	public void setDimElements(String[] dimElements) {
		this.dimElements = dimElements;
	}

	public List<Serie> getSeries() {
		return series;
	}

	public void setSeries(List<Serie> series) {
		this.series = series;
	}

	public String[] getLegends() {
		return legends;
	}

	public void setLegends(String[] legends) {
		this.legends = legends;
	}
}
