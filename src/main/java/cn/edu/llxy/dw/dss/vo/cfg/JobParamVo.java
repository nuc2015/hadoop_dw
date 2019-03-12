package cn.edu.llxy.dw.dss.vo.cfg;

public class JobParamVo {
	private int index;
	private String type;
	private String name;
	private String desc;
	private String value;
	private String valueKey;
	private TableVarVo tableVarVo;
	private TimeSpliterVo timeSpliterVo;

	public JobParamVo(String type, String name, String desc, String value) {
		super();
		this.type = type;
		this.name = name;
		this.desc = desc;
		this.value = value;
	}

	public JobParamVo(int index, String type, String name, String desc, String value, String valueKey) {
		super();
		this.index = index;
		this.type = type;
		this.name = name;
		this.desc = desc;
		this.value = value;
		this.valueKey = valueKey;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValueKey() {
		return valueKey;
	}

	public void setValueKey(String valueKey) {
		this.valueKey = valueKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TableVarVo getTableVarVo() {
		return tableVarVo;
	}

	public void setTableVarVo(TableVarVo tableVarVo) {
		this.tableVarVo = tableVarVo;
	}

	public TimeSpliterVo getTimeSpliterVo() {
		return timeSpliterVo;
	}

	public void setTimeSpliterVo(TimeSpliterVo timeSpliterVo) {
		this.timeSpliterVo = timeSpliterVo;
	}

}
