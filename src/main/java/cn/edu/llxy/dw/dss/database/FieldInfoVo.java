package cn.edu.llxy.dw.dss.database;

public class FieldInfoVo {

	private String name,typedesc,comments, originalColumnType, originalColumnTypeDesc,tableName;
	private int index, type,  length,  precision,pointer;
	private boolean pk;
	
	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public FieldInfoVo(String name, int length) {
		super();
		this.name = name;
		this.length = length;
	}

	public FieldInfoVo(String name, String typedesc, int length,int precision) {
		super();
		this.name = name;
		this.typedesc = typedesc;
		this.length = length;
		this.precision=precision;
	}
	
	public FieldInfoVo(int index,String name, String typedesc, int type, int length,
			int precision,String comments,String originalColumnType, String originalColumnTypeDesc) {
		super();
		this.index=index;
		this.name = name;
		this.typedesc = typedesc;
		this.type = type;
		this.length = length;
		this.precision = precision;
		this.comments=comments;
		this.originalColumnType = originalColumnType;
		this.originalColumnTypeDesc = originalColumnTypeDesc;
	}
	public String getTypedesc() {
		return typedesc;
	}
	public void setTypedesc(String typedesc) {
		this.typedesc = typedesc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + length;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + precision;
		result = prime * result + type;
		result = prime * result
				+ ((typedesc == null) ? 0 : typedesc.hashCode());
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
		FieldInfoVo other = (FieldInfoVo) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (length != other.length)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (precision != other.precision)
			return false;
		if (type != other.type)
			return false;
		if (typedesc == null) {
			if (other.typedesc != null)
				return false;
		} else if (!typedesc.equals(other.typedesc))
			return false;
		return true;
	}

	public int getPointer() {
		return pointer;
	}

	public void setPointer(int pointer) {
		this.pointer = pointer;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getOriginalColumnType() {
		return originalColumnType;
	}

	public void setOriginalColumnType(String originalColumnType) {
		this.originalColumnType = originalColumnType;
	}

	public String getOriginalColumnTypeDesc() {
		return originalColumnTypeDesc;
	}

	public void setOriginalColumnTypeDesc(String originalColumnTypeDesc) {
		this.originalColumnTypeDesc = originalColumnTypeDesc;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
