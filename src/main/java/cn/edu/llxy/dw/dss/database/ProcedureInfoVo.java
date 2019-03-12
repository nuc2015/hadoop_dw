package cn.edu.llxy.dw.dss.database;

import org.pentaho.di.core.database.util.ColumnTypeUtil;

public class ProcedureInfoVo {
	private String procedureName;// 过程名称
	private String columnName;// 列/参数名称
	private Long columnType;// 列/参数的种类
	private String columnTypeName;// 列/参数的种类
	private Long dateType;// 来自 java.sql.Types 的 SQL 类型
	private String typeName;// SQL 类型名称，对于 UDT 类型，类型名称是完全限定的
	private String defaultValue;// 列的默认值，当该值在单引号内时被解释为一个字符串（可以为 null）

	public ProcedureInfoVo(String procedureName, String columnName, Long columnType, Long dateType, String typeName, String defaultValue) {
		super();
		this.procedureName = procedureName;
		this.columnName = columnName;
		this.columnType = columnType;
		this.columnTypeName = ColumnTypeUtil.getColumnTypeName(columnType);
		this.dateType = dateType;
		this.typeName = typeName;
		this.defaultValue = defaultValue;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Long getColumnType() {
		return columnType;
	}

	public void setColumnType(Long columnType) {
		this.columnType = columnType;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	public Long getDateType() {
		return dateType;
	}

	public void setDateType(Long dateType) {
		this.dateType = dateType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result + ((procedureName == null) ? 0 : procedureName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcedureInfoVo other = (ProcedureInfoVo) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (procedureName == null) {
			if (other.procedureName != null)
				return false;
		} else if (!procedureName.equals(other.procedureName))
			return false;
		return true;
	}

}
