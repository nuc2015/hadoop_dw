package cn.edu.llxy.dw.dss.vo.adhoc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import cn.edu.llxy.dw.dss.vo.dg.meta.relation.Column;

public class QueryInfo implements Serializable {

	private static final long serialVersionUID = -7004937801799106802L;

	public static final String CHART_CLM_TYPE_DATAGROUP = "dataGroup";//数据分组
	
	public static final String CHART_CLM_TYPE_XAXIS = "xaxis";//x-轴
	
	public static final String CHART_CLM_TYPE_YAXIS = "yaxis";//y-轴
	
	public static final String CHART_CLM_TYPE_YAXIS_NAME = "yaxisNames";
	
	//数据源ID
	private String dsId;
	//表名
	private String tableName;
	//过滤条件
	private String filters;
	//排序定义
	private String odrs;
	//查询字段
	private String queryClms;
	
	//未分页前SQL，该SQL不完整不能执行
	private String querySql;
	
	//查询参数
	private List<Object> queryParameters;
	
	//查询字段定义
	private List<QueryClmInfo> queryClmInfos;
	
	//当前分页数
	private Integer current;
	
	//每页条数
	private Integer rowCount;
	
	private Integer total;
	
	//数据库类型
	private String dsType;
	
	//是否包含Order by
	private boolean containOrderBy;
	
	//是否统计所有的查询记录数
	private boolean countTotal;
	
	//查询记录
	private List<Map<String, Object>> value;
	
	//平面查询结构（供数据导出用）
	private List<Object[]> plainValue;
	
	private Integer[] colTypes;
	
	private Integer[] precision;
	
	private String reportName;
	
	private String chartOrd;
	
	private String chartWidth;
	
	private String tagClm;
	
	private List<Column> dbMetas;
	
	public List<Column> getDbMetas() {
		return dbMetas;
	}

	public void setDbMetas(List<Column> dbMetas) {
		this.dbMetas = dbMetas;
	}

	public String getTagClm() {
		return tagClm;
	}

	public void setTagClm(String tagClm) {
		this.tagClm = tagClm;
	}

	public String getChartOrd() {
		return chartOrd;
	}

	public void setChartOrd(String chartOrd) {
		this.chartOrd = chartOrd;
	}

	public String getChartWidth() {
		return chartWidth;
	}

	public void setChartWidth(String chartWidth) {
		this.chartWidth = chartWidth;
	}

	public Integer[] getPrecision() {
		return precision;
	}

	public void setPrecision(Integer[] precision) {
		this.precision = precision;
	}

	public Integer[] getColTypes() {
		return colTypes;
	}

	public void setColTypes(Integer[] colTypes) {
		this.colTypes = colTypes;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public List<Object[]> getPlainValue() {
		return plainValue;
	}

	public void setPlainValue(List<Object[]> plainValue) {
		this.plainValue = plainValue;
	}

	//查询记录
	private List<Map<String, Object>> chartValue;
	
	private Map<String, List<Map<String, Object>>> chartValues;
	
	//字段元数据
	private List<Column> metas;
	
	//查询结果元数据
	private List<Column> resultMetas;
	
	//图形
	private ChartData chartData;
	
	private List<ChartData> chartDatas;
	
	private String chartType;
	
	private String mapType = "china";
	
	//用户数据权限过滤字段
	private List<String> sessionFilterClms;
	
	public List<ChartData> getChartDatas() {
		return chartDatas;
	}

	public void setChartDatas(List<ChartData> chartDatas) {
		this.chartDatas = chartDatas;
	}

	public Map<String, List<Map<String, Object>>> getChartValues() {
		return chartValues;
	}

	public void setChartValues(Map<String, List<Map<String, Object>>> chartValues) {
		this.chartValues = chartValues;
	}

	public List<String> getSessionFilterClms() {
		return sessionFilterClms;
	}

	public void setSessionFilterClms(List<String> sessionFilterClms) {
		this.sessionFilterClms = sessionFilterClms;
	}

	private Map<String, Object> chartTypeIdx = new HashMap();
	
	
	//将查询结果转换为图形展示数据
		public void generateMultiChartObject(){
			chartDatas = new ArrayList();
			Map.Entry<String, List<Map<String, Object>>> entry;
			for(Iterator<Map.Entry<String, List<Map<String, Object>>>> ite = chartValues.entrySet().iterator(); ite.hasNext();){
				entry = ite.next();
				chartValue = entry.getValue();
				
				Map<String, Object> row = chartValue.get(0);
				if(row.size() > 1){
					Object dimIdx = chartTypeIdx.get(CHART_CLM_TYPE_XAXIS);
					Object kpiIdx = chartTypeIdx.get(CHART_CLM_TYPE_YAXIS);
					Object kpiNames = chartTypeIdx.get(CHART_CLM_TYPE_YAXIS_NAME);
					Object dataGroupIdx = chartTypeIdx.get(CHART_CLM_TYPE_DATAGROUP);
					List kpiIdxList;List kpiNamesList = null;int kpiIdxInt = 0, dimIdxInt = 0, dataGroupIdxInt = 0;
					
					if(kpiNames != null)
						kpiNamesList = (List)kpiNames;
					
					//two column
					if(row.size() == 2){
						if(dimIdx == null){
							if(kpiIdx != null){
								kpiIdxList = (List)kpiIdx;
								
								//get the kpi Index
								kpiIdxInt = (Integer)kpiIdxList.get(0);
								dimIdxInt = kpiIdxInt == 1 ? 2:1;
							}
						}else{
							dimIdxInt = (Integer)dimIdx;
							kpiIdxInt = dimIdxInt == 1 ? 2:1;
						}
						
						ChartData chartData = new ChartData();
						chartData.setTitle(entry.getKey());
						
						List<String> legend = new ArrayList();
						List<String> dataGroup = new ArrayList();
						List<Map<String, Object>> datas = new ArrayList();
						
						List<Serie> series = new ArrayList();String dimValue ="default";
						if("pie".equals(chartType)){
							for(Map<String, Object> m:chartValue){
								//dimValue = m.get(dimIdxInt+"")+"";
								Serie s = getSerie(dimValue, series);
								if(s == null){
									s = new Serie();
									s.setName(dimValue);
									s.setType(chartType);
									s.setData(new ArrayList());
									
									Label label = new Label();
									label.setEmphasis(new Emphasis());
									label.setNormal(new Normal());
									s.setLabel(label);
									
									series.add(s);
								}
								
								legend.add(m.get(dimIdxInt+"")+"");
								
								Map<String, Object> me = new HashMap();
								me.put("name", m.get(dimIdxInt+"")+"");
								me.put("value", m.get(kpiIdxInt+""));
								s.getData().add(me);
							}
							
							chartData.setChartType(chartType);
							chartData.setLegends(legend.toArray(new String[0]));
							chartData.setSeries(series);
							
							chartDatas.add(chartData);
						}else if("bar".equals(chartType) || "line".equals(chartType) || "map".equals(chartType) 
								|| "stackedBar".equals(chartType) || "scatter".equals(chartType)){
							String kpiValue = kpiNamesList.get(kpiIdxInt-1)+"";
							dataGroup.add(kpiValue);
							chartData.setDimElements(dataGroup.toArray(new String[0]));
							
							for(Map<String, Object> m:chartValue){
								dimValue = m.get(dimIdxInt+"")+"";
								Serie s = getSerie(kpiValue, series);
								if(s == null){
									s = new Serie();
									s.setName(kpiValue);
									if("stackedBar".equals(chartType)){
										s.setType("bar");
										s.setStack("总量");
									}else{
										s.setType(chartType);
									}
									s.setData(new ArrayList());
									if("map".equals(chartType)){
										s.setMapType(this.getMapType());
									}
									series.add(s);
								}
								
								legend.add(dimValue);
								
								Map<String, Object> me = new HashMap();
								me.put("name", dimValue);
								me.put("value", m.get(kpiIdxInt+""));
								s.getData().add(me);
							}
							
							if("stackedBar".equals(chartType)){
								chartData.setChartType("bar");
							}else{
								chartData.setChartType(chartType);
							}
							
							chartData.setLegends(legend.toArray(new String[0]));
							chartData.setSeries(series);
							
							chartDatas.add(chartData);
						}
					}else{//more 
						if(dimIdx != null){
							if(dataGroupIdx != null)
								dataGroupIdxInt = (Integer)dataGroupIdx;
							
								if(kpiIdx != null){
								kpiIdxList = (List)kpiIdx;
								
								chartData = new ChartData();
								List<String> legend = new ArrayList();
								List<Map<String, Object>> datas = new ArrayList();
								
								chartData.setTitle(entry.getKey());
								
								List<Serie> series = new ArrayList();String dimValue, groupValue = null ;
								List<String> dataGroup = new ArrayList();boolean groupCreated = false;int skip = 0;
								if("pie".equals(chartType) || "bar".equals(chartType) || "line".equals(chartType) || "map".equals(chartType) 
										|| "stackedBar".equals(chartType) || "scatter".equals(chartType)){
									for(Map<String, Object> m:chartValue){
										dimValue = m.get(dimIdx+"")+"";
										
										if(!legend.contains(dimValue))
											legend.add(dimValue);
										
										if(dataGroupIdxInt > 0 ){
											groupValue = m.get(dataGroupIdxInt+"")+"";
											if(!dataGroup.contains(groupValue)){
												dataGroup.add(groupValue);
											}
										}
										
										if(dataGroupIdxInt <= 0){
											for(int x=0;x<kpiIdxList.size(); x++){
												if(!groupCreated){
													dataGroup.add(kpiNamesList.get(x)+"");
												}
												Serie s = getSerie(kpiNamesList.get(x)+"", series);
												if(s == null){
													s = new Serie();
													
													s.setName(kpiNamesList.get(x)+"");
													if("stackedBar".equals(chartType)){
														s.setType("bar");
														s.setStack("总量");
													}else if("pie".equals(chartType)){
														s.setRadius(new Integer[]{40,55});
														s.setType(chartType);
														s.setCenter(new String[]{(30+30*skip)+"%","50%"});
														
														Label label = new Label();
														label.setEmphasis(new Emphasis());
														label.setNormal(new Normal());
														s.setLabel(label);
														
														skip ++;
													}else{
														s.setType(chartType);
													}
													s.setData(new ArrayList());
													
													if("map".equals(chartType)){
														s.setMapType(this.getMapType());
													}
													
													series.add(s);
												}
												
												Map<String, Object> me = new HashMap();
												me.put("name", dimValue);
												me.put("value", m.get(kpiIdxList.get(x)+""));
												s.getData().add(me);
											}
										}else{
											if(kpiIdxList.size() == 1){
												kpiIdxInt = (Integer)kpiIdxList.get(0);
											}
											
											Serie s = getSerie(groupValue, series);
											if(s == null){
												s = new Serie();
												
												s.setName(groupValue);
												if("stackedBar".equals(chartType)){
													s.setType("bar");
													s.setStack("总量");
												}if("pie".equals(chartType)){
													s.setType(chartType);
													
													s.setCenter(new String[]{(10+30*skip)+"%","30%"});
													skip ++;
													
												}else{
													s.setType(chartType);
												}
												s.setData(new ArrayList());
												
												if("map".equals(chartType)){
													s.setMapType(this.getMapType());
												}
												
												series.add(s);
											}
											
											Map<String, Object> me = new HashMap();
											me.put("name", dimValue);
											me.put("value", m.get(kpiIdxInt+""));
											s.getData().add(me);
										}
										
										groupCreated = true;
									}
									
									chartData.setDimElements(dataGroup.toArray(new String[0]));
									if("stackedBar".equals(chartType)){
										chartData.setChartType("bar");
									}else{
										chartData.setChartType(chartType);
									}
									chartData.setLegends(legend.toArray(new String[0]));
									chartData.setSeries(series);
									
									chartDatas.add(chartData);
								}
							}
						}else{
							if(kpiIdx != null){
								kpiIdxList = (List)kpiIdx;
								
								chartData = new ChartData();
								List<String> legend = new ArrayList();
								List<Map<String, Object>> datas = new ArrayList();
								
								List<Serie> series = new ArrayList();String dimValue ;
								
								//Object kpiNames = chartTypeIdx.get(CHART_CLM_TYPE_YAXIS_NAME);
								//List kpiNamesList = null;
								if(kpiNames != null)
									kpiNamesList = (List)kpiNames;
								if("bar".equals(chartType) || "line".equals(chartType) || "map".equals(chartType) 
										|| "stackedBar".equals(chartType) || "scatter".equals(chartType)){
									boolean loopOnce = false;
									for(Map<String, Object> m:chartValue){
										for(int x=0;x<kpiIdxList.size(); x++){
											dimValue = kpiNamesList.get(x)+"";
											if(!loopOnce){
												legend.add(dimValue);
											}
											Serie s = getSerie(dimValue, series);
											if(s == null){
												s = new Serie();
												s.setName(dimValue);
												if("stackedBar".equals(chartType)){
													s.setType("bar");
													s.setStack("总量");
												}else{
													s.setType(chartType);
												}
												s.setData(new ArrayList());
												if("map".equals(chartType)){
													s.setMapType(this.getMapType());
												}
												series.add(s);
											}
											
											Map<String, Object> me = new HashMap();
											me.put("name", dimValue);
											me.put("value", m.get(kpiIdxList.get(x)));
											
											s.getData().add(me);
											
											series.add(s);
										}
										
										
										loopOnce = true;
									}
									
									if("stackedBar".equals(chartType)){
										chartData.setChartType("bar");
									}else{
										chartData.setChartType(chartType);
									}
									chartData.setLegends(legend.toArray(new String[0]));
									chartData.setSeries(series);
									
									chartDatas.add(chartData);
								}
							}
						}
					}
				}
			}
		}
	
	
		//将查询结果转换为图形展示数据
		public void generateChartObject(){
			if(chartValue == null || chartValue.size() <= 0){
				
			}else{
				Map<String, Object> row = chartValue.get(0);
				if(row.size() > 1){
					Object dimIdx = chartTypeIdx.get(CHART_CLM_TYPE_XAXIS);
					Object kpiIdx = chartTypeIdx.get(CHART_CLM_TYPE_YAXIS);
					Object kpiNames = chartTypeIdx.get(CHART_CLM_TYPE_YAXIS_NAME);
					Object dataGroupIdx = chartTypeIdx.get(CHART_CLM_TYPE_DATAGROUP);
					List kpiIdxList;List kpiNamesList = null;int kpiIdxInt = 0, dimIdxInt = 0, dataGroupIdxInt = 0;
					
					if(kpiNames != null)
						kpiNamesList = (List)kpiNames;
					
					//two column
					if(row.size() == 2){
						if(dimIdx == null){
							if(kpiIdx != null){
								kpiIdxList = (List)kpiIdx;
								
								//get the kpi Index
								kpiIdxInt = (Integer)kpiIdxList.get(0);
								dimIdxInt = kpiIdxInt == 1 ? 2:1;
							}
						}else{
							dimIdxInt = (Integer)dimIdx;
							kpiIdxInt = dimIdxInt == 1 ? 2:1;
						}
						
						chartData = new ChartData();
						List<String> legend = new ArrayList();
						List<String> dataGroup = new ArrayList();
						List<Map<String, Object>> datas = new ArrayList();
						
						List<Serie> series = new ArrayList();String dimValue ="default";
						if("pie".equals(chartType)){
							for(Map<String, Object> m:chartValue){
								//dimValue = m.get(dimIdxInt+"")+"";
								Serie s = getSerie(dimValue, series);
								if(s == null){
									s = new Serie();
									s.setName(dimValue);
									s.setType(chartType);
									s.setData(new ArrayList());
									series.add(s);
								}
								
								legend.add(m.get(dimIdxInt+"")+"");
								
								Map<String, Object> me = new HashMap();
								me.put("name", m.get(dimIdxInt+"")+"");
								me.put("value", m.get(kpiIdxInt+""));
								s.getData().add(me);
							}
							
							chartData.setChartType(chartType);
							chartData.setLegends(legend.toArray(new String[0]));
							chartData.setSeries(series);
						}else if("bar".equals(chartType) || "line".equals(chartType) || "map".equals(chartType) 
								|| "stackedBar".equals(chartType) || "scatter".equals(chartType)){
							int xkpiIdxInt = 1;
							if(kpiNamesList.size() == 1){
								xkpiIdxInt = 1;
							}else{
								xkpiIdxInt = kpiIdxInt;
							}
							
							String kpiValue = kpiNamesList.get(xkpiIdxInt-1)+"";
							dataGroup.add(kpiValue);
							chartData.setDimElements(dataGroup.toArray(new String[0]));
							
							for(Map<String, Object> m:chartValue){
								dimValue = m.get(dimIdxInt+"")+"";
								Serie s = getSerie(kpiValue, series);
								if(s == null){
									s = new Serie();
									s.setName(kpiValue);
									if("stackedBar".equals(chartType)){
										s.setType("bar");
										s.setStack("总量");
									}else{
										s.setType(chartType);
									}
									s.setData(new ArrayList());
									if("map".equals(chartType)){
										s.setMapType(this.getMapType());
									}
									series.add(s);
								}
								
								legend.add(dimValue);
								
								Map<String, Object> me = new HashMap();
								me.put("name", dimValue);
								me.put("value", m.get(kpiIdxInt+""));
								s.getData().add(me);
							}
							
							if("stackedBar".equals(chartType)){
								chartData.setChartType("bar");
							}else{
								chartData.setChartType(chartType);
							}
							
							chartData.setLegends(legend.toArray(new String[0]));
							chartData.setSeries(series);
						}
					}else{//more 
						if(dimIdx != null){
							if(dataGroupIdx != null)
								dataGroupIdxInt = (Integer)dataGroupIdx;
							
								if(kpiIdx != null){
								kpiIdxList = (List)kpiIdx;
								
								chartData = new ChartData();
								List<String> legend = new ArrayList();
								List<Map<String, Object>> datas = new ArrayList();
								
								List<Serie> series = new ArrayList();String dimValue, groupValue = null ;
								List<String> dataGroup = new ArrayList();boolean groupCreated = false;int skip = 0;int xheight = 0;int xskip = 0;
								if("pie".equals(chartType) || "bar".equals(chartType) || "line".equals(chartType) || "map".equals(chartType) 
										|| "stackedBar".equals(chartType) || "scatter".equals(chartType)){
									for(Map<String, Object> m:chartValue){
										dimValue = m.get(dimIdx+"")+"";
										
										if(!legend.contains(dimValue))
											legend.add(dimValue);
										
										if(dataGroupIdxInt > 0 ){
											groupValue = m.get(dataGroupIdxInt+"")+"";
											if(!dataGroup.contains(groupValue)){
												dataGroup.add(groupValue);
											}
										}
										
										if(dataGroupIdxInt <= 0){
											for(int x=0;x<kpiIdxList.size(); x++){
												if(!groupCreated){
													dataGroup.add(kpiNamesList.get(x)+"");
												}
												Serie s = getSerie(kpiNamesList.get(x)+"", series);
												if(s == null){
													s = new Serie();
													
													s.setName(kpiNamesList.get(x)+"");
													if("stackedBar".equals(chartType)){
														s.setType("bar");
														s.setStack("总量");
													}else if("pie".equals(chartType)){
														s.setRadius(new Integer[]{40,55});
														s.setType(chartType);
														s.setCenter(new String[]{(30+30*skip)+"%","50%"});
														skip ++;
													}else{
														s.setType(chartType);
													}
													s.setData(new ArrayList());
													
													if("map".equals(chartType)){
														s.setMapType(this.getMapType());
													}
													
													series.add(s);
												}
												
												Map<String, Object> me = new HashMap();
												me.put("name", dimValue);
												me.put("value", m.get(kpiIdxList.get(x)+""));
												s.getData().add(me);
											}
										}else{
											if(kpiIdxList.size() == 1){
												kpiIdxInt = (Integer)kpiIdxList.get(0);
											}
											
											
											Serie s = getSerie(groupValue, series);
											if(s == null){
												s = new Serie();
												
												s.setName(groupValue);
												if("stackedBar".equals(chartType)){
													s.setType("bar");
													s.setStack("总量");
												}if("pie".equals(chartType)){
													s.setType(chartType);
													
													xheight = skip/4 ;
													
													xskip = skip%4;
													s.setCenter(new String[]{(10+25*xskip)+"%",(xheight*45 + 25) + "%" });
													s.setRadius(new Integer[]{40,65});
													
													skip ++;
													
												}else{
													s.setType(chartType);
												}
												s.setData(new ArrayList());
												
												if("map".equals(chartType)){
													s.setMapType(this.getMapType());
												}
												
												series.add(s);
											}
											
											Map<String, Object> me = new HashMap();
											me.put("name", dimValue);
											me.put("value", m.get(kpiIdxInt+""));
											s.getData().add(me);
										}
										
										groupCreated = true;
									}
									
									chartData.setDimElements(dataGroup.toArray(new String[0]));
									if("stackedBar".equals(chartType)){
										chartData.setChartType("bar");
									}else{
										chartData.setChartType(chartType);
									}
									chartData.setLegends(legend.toArray(new String[0]));
									chartData.setSeries(series);
								}
							}
						}else{
							if(kpiIdx != null){
								kpiIdxList = (List)kpiIdx;
								
								chartData = new ChartData();
								List<String> legend = new ArrayList();
								List<Map<String, Object>> datas = new ArrayList();
								
								List<Serie> series = new ArrayList();String dimValue ;
								
								//Object kpiNames = chartTypeIdx.get(CHART_CLM_TYPE_YAXIS_NAME);
								//List kpiNamesList = null;
								if(kpiNames != null)
									kpiNamesList = (List)kpiNames;
								if("bar".equals(chartType) || "line".equals(chartType) || "map".equals(chartType) 
										|| "stackedBar".equals(chartType) || "scatter".equals(chartType)){
									boolean loopOnce = false;
									for(Map<String, Object> m:chartValue){
										for(int x=0;x<kpiIdxList.size(); x++){
											dimValue = kpiNamesList.get(x)+"";
											if(!loopOnce){
												legend.add(dimValue);
											}
											Serie s = getSerie(dimValue, series);
											if(s == null){
												s = new Serie();
												s.setName(dimValue);
												if("stackedBar".equals(chartType)){
													s.setType("bar");
													s.setStack("总量");
												}else{
													s.setType(chartType);
												}
												s.setData(new ArrayList());
												if("map".equals(chartType)){
													s.setMapType(this.getMapType());
												}
												series.add(s);
											}
											
											Map<String, Object> me = new HashMap();
											me.put("name", dimValue);
											me.put("value", m.get(kpiIdxList.get(x)));
											
											s.getData().add(me);
											
											series.add(s);
										}
										
										
										loopOnce = true;
									}
									
									if("stackedBar".equals(chartType)){
										chartData.setChartType("bar");
									}else{
										chartData.setChartType(chartType);
									}
									chartData.setLegends(legend.toArray(new String[0]));
									chartData.setSeries(series);
								}
							}
						}
					}
				}
			}
		}
		
	//将查询结果转换为图形展示数据
	public void generateChartObject2(){
		if(chartValue == null || chartValue.size() <= 0){
			
		}else{
			Map<String, Object> row = chartValue.get(0);
			if(row.size() > 1){
				Object dimIdx = chartTypeIdx.get(CHART_CLM_TYPE_XAXIS);
				Object kpiIdx = chartTypeIdx.get(CHART_CLM_TYPE_YAXIS);
				Object kpiNames = chartTypeIdx.get(CHART_CLM_TYPE_YAXIS_NAME);
				Object dataGroupIdx = chartTypeIdx.get(CHART_CLM_TYPE_DATAGROUP);
				List kpiIdxList;List kpiNamesList = null;int kpiIdxInt = 0, dimIdxInt = 0, dataGroupIdxInt = 0;Object tv;
				
				if(kpiNames != null)
					kpiNamesList = (List)kpiNames;
				
				//two column
				if(row.size() == 2){
					if(dimIdx == null){
						if(kpiIdx != null){
							kpiIdxList = (List)kpiIdx;
							
							//get the kpi Index
							kpiIdxInt = (Integer)kpiIdxList.get(0);
							dimIdxInt = kpiIdxInt == 1 ? 2:1;
						}
					}else{
						dimIdxInt = (Integer)dimIdx;
						kpiIdxInt = dimIdxInt == 1 ? 1:2;
					}
					
					chartData = new ChartData();
					List<String> legend = new ArrayList();
					List<String> dataGroup = new ArrayList();
					List<Map<String, Object>> datas = new ArrayList();
					
					List<Serie> series = new ArrayList();String dimValue ="default";
					if("pie".equals(chartType)){
						for(Map<String, Object> m:chartValue){
							//dimValue = m.get(dimIdxInt+"")+"";
							Serie s = getSerie(dimValue, series);
							if(s == null){
								s = new Serie();
								s.setName(dimValue);
								s.setType(chartType);
								s.setData(new ArrayList());
								Label label = new Label();
								label.setEmphasis(new Emphasis());
								label.setNormal(new Normal());
								s.setLabel(label);
								series.add(s);
							}
							
							legend.add(m.get(dimIdxInt+"")+"");
							
							Map<String, Object> me = new HashMap();
							me.put("name", m.get(dimIdxInt+"")+"");
							
							tv =  m.get((kpiIdxInt-1)+"");
							if(tv == null || tv.equals("") || tv.equals("null")){
								tv = "0";
							}
							
							me.put("value", tv);
							s.getData().add(me);
						}
						
						chartData.setChartType(chartType);
						chartData.setLegends(legend.toArray(new String[0]));
						chartData.setSeries(series);
					}else if("bar".equals(chartType) || "line".equals(chartType) || "map".equals(chartType) 
							|| "stackedBar".equals(chartType) || "scatter".equals(chartType)){
						String kpiValue = kpiNamesList.get(kpiIdxInt-1)+"";
						dataGroup.add(kpiValue);
						chartData.setDimElements(dataGroup.toArray(new String[0]));
						
						for(Map<String, Object> m:chartValue){
							dimValue = m.get(dimIdxInt+"")+"";
							Serie s = getSerie(kpiValue, series);
							if(s == null){
								s = new Serie();
								s.setName(kpiValue);
								if("stackedBar".equals(chartType)){
									s.setType("bar");
									s.setStack("总量");
								}else{
									s.setType(chartType);
								}
								s.setData(new ArrayList());
								if("map".equals(chartType)){
									s.setMapType(this.getMapType());
								}
								series.add(s);
							}
							
							legend.add(dimValue);
							
							Map<String, Object> me = new HashMap();
							me.put("name", dimValue);
							me.put("value", m.get(kpiIdxInt-1)+"");
							s.getData().add(me);
						}
						
						if("stackedBar".equals(chartType)){
							chartData.setChartType("bar");
						}else{
							chartData.setChartType(chartType);
						}
						
						chartData.setLegends(legend.toArray(new String[0]));
						chartData.setSeries(series);
					}
				}else{//more 
					if(dimIdx != null){
						if(dataGroupIdx != null)
							dataGroupIdxInt = (Integer)dataGroupIdx;
						
							if(kpiIdx != null){
							kpiIdxList = (List)kpiIdx;
							
							chartData = new ChartData();
							List<String> legend = new ArrayList();
							List<Map<String, Object>> datas = new ArrayList();
							
							List<Serie> series = new ArrayList();String dimValue, groupValue = null ;
							List<String> dataGroup = new ArrayList();boolean groupCreated = false;int skip = 0;
							if("pie".equals(chartType) || "bar".equals(chartType) || "line".equals(chartType) || "map".equals(chartType) 
									|| "stackedBar".equals(chartType) || "scatter".equals(chartType)){
								for(Map<String, Object> m:chartValue){
									dimValue = m.get(dimIdx+"")+"";
									
									if(!legend.contains(dimValue))
										legend.add(dimValue);
									
									if(dataGroupIdxInt > 0 ){
										groupValue = m.get(dataGroupIdxInt+"")+"";
										if(!dataGroup.contains(groupValue)){
											dataGroup.add(groupValue);
										}
									}
									
									if(dataGroupIdxInt <= 0){
										for(int x=0;x<kpiIdxList.size(); x++){
											if(!groupCreated){
												dataGroup.add(kpiNamesList.get(x)+"");
											}
											Serie s = getSerie(kpiNamesList.get(x)+"", series);
											if(s == null){
												s = new Serie();
												
												s.setName(kpiNamesList.get(x)+"");
												if("stackedBar".equals(chartType)){
													s.setType("bar");
													s.setStack("总量");
												}else if("pie".equals(chartType)){
													s.setRadius(new Integer[]{40,55});
													s.setType(chartType);
													Label label = new Label();
													label.setEmphasis(new Emphasis());
													label.setNormal(new Normal());
													s.setLabel(label);
													s.setCenter(new String[]{(30+30*skip)+"%","50%"});
													skip ++;
												}else{
													s.setType(chartType);
												}
												s.setData(new ArrayList());
												
												if("map".equals(chartType)){
													s.setMapType(this.getMapType());
												}
												
												series.add(s);
											}
											
											Map<String, Object> me = new HashMap();
											me.put("name", dimValue);
											me.put("value", m.get(kpiIdxList.get(x)+""));
											s.getData().add(me);
										}
									}else{
										if(kpiIdxList.size() == 1){
											kpiIdxInt = (Integer)kpiIdxList.get(0);
										}
										
										
										Serie s = getSerie(groupValue, series);
										if(s == null){
											s = new Serie();
											
											s.setName(groupValue);
											if("stackedBar".equals(chartType)){
												s.setType("bar");
												s.setStack("总量");
											}if("pie".equals(chartType)){
												s.setType(chartType);
												
												s.setCenter(new String[]{(10+30*skip)+"%","30%"});
												skip ++;
												
											}else{
												s.setType(chartType);
											}
											s.setData(new ArrayList());
											
											if("map".equals(chartType)){
												s.setMapType(this.getMapType());
											}
											
											series.add(s);
										}
										
										Map<String, Object> me = new HashMap();
										me.put("name", dimValue);
										me.put("value", m.get(kpiIdxInt+""));
										s.getData().add(me);
									}
									
									groupCreated = true;
								}
								
								chartData.setDimElements(dataGroup.toArray(new String[0]));
								if("stackedBar".equals(chartType)){
									chartData.setChartType("bar");
								}else{
									chartData.setChartType(chartType);
								}
								chartData.setLegends(legend.toArray(new String[0]));
								chartData.setSeries(series);
							}
						}
					}else{
						if(kpiIdx != null){
							kpiIdxList = (List)kpiIdx;
							
							chartData = new ChartData();
							List<String> legend = new ArrayList();
							List<Map<String, Object>> datas = new ArrayList();
							
							List<Serie> series = new ArrayList();String dimValue ;
							
							//Object kpiNames = chartTypeIdx.get(CHART_CLM_TYPE_YAXIS_NAME);
							//List kpiNamesList = null;
							if(kpiNames != null)
								kpiNamesList = (List)kpiNames;
							if("bar".equals(chartType) || "line".equals(chartType) || "map".equals(chartType) 
									|| "stackedBar".equals(chartType) || "scatter".equals(chartType)){
								boolean loopOnce = false;
								for(Map<String, Object> m:chartValue){
									for(int x=0;x<kpiIdxList.size(); x++){
										dimValue = kpiNamesList.get(x)+"";
										if(!loopOnce){
											legend.add(dimValue);
										}
										Serie s = getSerie(dimValue, series);
										if(s == null){
											s = new Serie();
											s.setName(dimValue);
											if("stackedBar".equals(chartType)){
												s.setType("bar");
												s.setStack("总量");
											}else{
												s.setType(chartType);
											}
											s.setData(new ArrayList());
											if("map".equals(chartType)){
												s.setMapType(this.getMapType());
											}
											series.add(s);
										}
										
										Map<String, Object> me = new HashMap();
										me.put("name", dimValue);
										me.put("value", m.get(kpiIdxList.get(x)));
										
										s.getData().add(me);
										
										series.add(s);
									}
									
									
									loopOnce = true;
								}
								
								if("stackedBar".equals(chartType)){
									chartData.setChartType("bar");
								}else{
									chartData.setChartType(chartType);
								}
								chartData.setLegends(legend.toArray(new String[0]));
								chartData.setSeries(series);
							}
						}
					}
				}
			}
		}
	}
	
	private Serie getSerie(String dim, List<Serie> series)
	{
		for(Serie s:series){
			if(s.getName().equals(dim)){
				return s;
			}
		}
		
		return null;
	}
	
	/**
	 * 比较查询定义是否相同
	 */
	public boolean equals(Object o){
		if(o instanceof QueryInfo){
			QueryInfo compareObj = (QueryInfo)o;
			
			if((this.getDsId() != null && this.getDsId().equals(compareObj.getDsId())) && (this.getTableName() != null && this.getTableName().equals(compareObj.getTableName()))
					&& (this.getFilters() != null && this.getFilters().equals(compareObj.getFilters())) && (this.odrs != null && this.odrs.equals(compareObj.getOdrs()))
					&& (this.getQueryClms() != null && this.getQueryClms().equals(compareObj.getQueryClms()))){
				return true;
			}else
				return false;
		}else
			return false;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public List<Object> getQueryParameters() {
		return queryParameters;
	}

	public void setQueryParameters(List<Object> queryParameters) {
		this.queryParameters = queryParameters;
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public String getDsType() {
		return dsType;
	}

	public void setDsType(String dsType) {
		this.dsType = dsType;
	}

	public boolean isContainOrderBy() {
		return containOrderBy;
	}

	public void setContainOrderBy(boolean containOrderBy) {
		this.containOrderBy = containOrderBy;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public boolean isCountTotal() {
		return countTotal;
	}

	public void setCountTotal(boolean countTotal) {
		this.countTotal = countTotal;
	}

	public List<Column> getMetas() {
		return metas;
	}

	public void setMetas(List<Column> metas) {
		this.metas = metas;
	}

	public List<Column> getResultMetas() {
		return resultMetas;
	}

	public void setResultMetas(List<Column> resultMetas) {
		this.resultMetas = resultMetas;
	}

	public List<QueryClmInfo> getQueryClmInfos() {
		return queryClmInfos;
	}

	public void setQueryClmInfos(List<QueryClmInfo> queryClmInfos) {
		this.queryClmInfos = queryClmInfos;
	}

	public String getDsId() {
		return dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getOdrs() {
		return odrs;
	}

	public void setOdrs(String odrs) {
		this.odrs = odrs;
	}

	public String getQueryClms() {
		return queryClms;
	}

	public void setQueryClms(String queryClms) {
		this.queryClms = queryClms;
	}

	public List<Map<String, Object>> getValue() {
		return value;
	}

	public void setValue(List<Map<String, Object>> value) {
		this.value = value;
	}

	public ChartData getChartData() {
		return chartData;
	}

	public void setChartData(ChartData chartData) {
		this.chartData = chartData;
	}

	public Map<String, Object> getChartTypeIdx() {
		return chartTypeIdx;
	}

	public void setChartTypeIdx(Map<String, Object> chartTypeIdx) {
		this.chartTypeIdx = chartTypeIdx;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	public String getMapType() {
		return mapType;
	}

	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

	public List<Map<String, Object>> getChartValue() {
		return chartValue;
	}

	public void setChartValue(List<Map<String, Object>> chartValue) {
		this.chartValue = chartValue;
	}
	
	public int hashCode() {
		  return 42;
	}
}
