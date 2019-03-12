package cn.edu.llxy.dw.dss.database;

import java.util.Date;

public class ColumnData {

	private Object Col_Vlaue;
	private ColumnModel Col_Meta;

	/**
	 * @param colVlaue
	 * @param colMeta
	 */
	public ColumnData(Object colVlaue, ColumnModel colMeta) {
		super();
		Col_Vlaue = colVlaue;
		Col_Meta = colMeta;
	}
	
	public ColumnData clone(ColumnData data){
		Object value=data.getCol_Vlaue();
		ColumnModel model=data.getCol_Meta();
		Object clonevalue;
		switch (model.getCol_Type()) {
		  case ColumnModel.TYPE_NONE:
		  case ColumnModel.TYPE_STRING: 
          case ColumnModel.TYPE_NUMBER: 
          case ColumnModel.TYPE_INTEGER: 
          case ColumnModel.TYPE_BOOLEAN:
          case ColumnModel.TYPE_BIGNUMBER: 
             clonevalue=value;break;
          case ColumnModel.TYPE_DATE:
        	  clonevalue=new Date( ((Date)value).getTime() );break;
		default:
			clonevalue=value; break;
		}
		ColumnData clonedata=new ColumnData(clonevalue, model);
		return clonedata;
	}

	public Object getCol_Vlaue() {
		return Col_Vlaue;
	}

	public void setCol_Vlaue(Object colVlaue) {
		Col_Vlaue = colVlaue;
	}

	public ColumnModel getCol_Meta() {
		return Col_Meta;
	}

	public void setCol_Meta(ColumnModel colMeta) {
		Col_Meta = colMeta;
	}

	@Override
	public String toString() {
		switch (Col_Meta.getCol_Type()) {
		case ColumnModel.TYPE_NONE:
			return Col_Vlaue.toString();
		case ColumnModel.TYPE_STRING:
			return Col_Vlaue.toString();
		case ColumnModel.TYPE_NUMBER:
			return String.valueOf(Col_Vlaue);
		case ColumnModel.TYPE_INTEGER:
			return String.valueOf(Col_Vlaue);
		case ColumnModel.TYPE_BOOLEAN:
			return String.valueOf(Col_Vlaue);
		case ColumnModel.TYPE_BIGNUMBER:
			return String.valueOf(Col_Vlaue);
		case ColumnModel.TYPE_DATE:
			return String.valueOf(Col_Vlaue);//there is check date format ,if include format str, before parse date
		default:
			return Col_Vlaue.toString();
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Col_Meta == null) ? 0 : Col_Meta.hashCode());
		result = prime * result + ((Col_Vlaue == null) ? 0 : Col_Vlaue.hashCode());
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
		ColumnData other = (ColumnData) obj;
		if (Col_Meta == null) {
			if (other.Col_Meta != null)
				return false;
		} else if (!Col_Meta.equals(other.Col_Meta))
			return false;
		if (Col_Vlaue == null) {
			if (other.Col_Vlaue != null)
				return false;
		} else if (!Col_Vlaue.equals(other.Col_Vlaue))
			return false;
		return true;
	}
}