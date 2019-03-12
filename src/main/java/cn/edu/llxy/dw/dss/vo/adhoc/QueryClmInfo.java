package cn.edu.llxy.dw.dss.vo.adhoc;

/**
 * 查询定义
 */
public class QueryClmInfo {
	//排序符
	public final static String ODR_TYPE_DESC = "desc";
	
	public final static String ODR_TYPE_ASC  = "asc";
	
	//统计符
	public final static String STAT_TYPE_SUM = "sum";
	
	public final static String STAT_TYPE_AVG = "avg";
	
	public final static String STAT_TYPE_count = "count";
	
	//运输符
	public final static String FILTER_OPER_LT = "<";
	
	public final static String FILTER_OPER_GT = ">";
	
	public final static String FILTER_OPER_LE = "<=";
	
	public final static String FILTER_OPER_GE = ">=";
	
	public final static String FILTER_OPER_EQ = "=";
	
	public final static String FILTER_OPER_NE = "<>";
	
	public final static String FILTER_OPER_IN = "in";
	
	public final static String FILTER_OPER_NOT_IN = "not in";
	
	public final static String FILTER_OPER_BT = "between";
	
	public final static String FILTER_OPER_NULL = "is null";
	
	public final static String FILTER_OPER_NOT_NULL = "is not null";
	
	public final static String FILTER_OPER_LIKE = "like";
	
	public final static String FILTER_OPER_NOT_LIKE = "not like";
	
	public final static String FILTER_OPER_STARTWITH = "starts_with";
	
	public final static String FILTER_OPER_STARTWITH_CASE_SENSITIVE = "starts_with_case_sensitive";
	
	public final static String FILTER_OPER_ENDS_WITH = "ends_with";
	
	public final static String FILTER_OPER_ENDS_WITH_CASE_SENSITIVE = "ends_with_case_sensitive";
	
	//是否查询字段
	private boolean isQueryClm;
	
	private String dataType;
	
	//字段名
	private String clmName;
	
	//字段中文名
	private String clmCmt;
	
	//排序类型，为空不对该字段排序
	private String odrType;
	
	//是否作为过滤条件
	private boolean isFilter;
	
	//查询条件值
	private Object clmConditonValue;
	
	//运算符,sum\avg\count,为空不运算
	private String statType;
	
	//过滤操作符
	private String filterOper;
	
	//图形属性，是否kpi
	private boolean isKpi;
	//图形属性，是否维度
	private boolean isDim;
	//图形属性 ，是否数据分组，对应到图形的多组数据维度
	private boolean isDataGroup;
	
	private String viewType;
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public String getClmName() {
		return clmName;
	}

	public void setClmName(String clmName) {
		this.clmName = clmName;
	}

	public String getOdrType() {
		return odrType;
	}

	public void setOdrType(String odrType) {
		this.odrType = odrType;
	}

	public boolean isFilter() {
		return isFilter;
	}

	public void setFilter(boolean isFilter) {
		this.isFilter = isFilter;
	}

	public Object getClmConditonValue() {
		return clmConditonValue;
	}

	public void setClmConditonValue(Object clmConditonValue) {
		this.clmConditonValue = clmConditonValue;
	}

	public String getStatType() {
		return statType;
	}

	public void setStatType(String statType) {
		this.statType = statType;
	}

	public String getFilterOper() {
		return filterOper;
	}

	public void setFilterOper(String filterOper) {
		this.filterOper = filterOper;
	}

	public boolean isQueryClm() {
		return isQueryClm;
	}

	public void setQueryClm(boolean isQueryClm) {
		this.isQueryClm = isQueryClm;
	}

	public boolean isKpi() {
		return isKpi;
	}

	public void setKpi(boolean isKpi) {
		this.isKpi = isKpi;
	}

	public boolean isDim() {
		return isDim;
	}

	public void setDim(boolean isDim) {
		this.isDim = isDim;
	}

	public boolean isDataGroup() {
		return isDataGroup;
	}

	public void setDataGroup(boolean isDataGroup) {
		this.isDataGroup = isDataGroup;
	}

	public String getClmCmt() {
		return clmCmt;
	}

	public void setClmCmt(String clmCmt) {
		this.clmCmt = clmCmt;
	}
}
