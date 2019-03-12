package cn.edu.llxy.dw.dss.database;

import java.util.Arrays;

import org.apache.log4j.Logger;

public class RowData {
	private Logger log=Logger.getLogger(RowData.class);
	
	private RowModel rowmodel;
	private ColumnData [] Column_Datas;

	private String LineValue;
	private long LineNr;

	
	public ColumnData getColunmDataByIndex(int index){
		if(index>Column_Datas.length)
			return null;
		else
			return Column_Datas[index];
	}
	
	public ColumnData getColunmDataByName(String name){
		for (ColumnData columndata : Column_Datas) {
			if(columndata.getCol_Meta().getCol_Name().equalsIgnoreCase(name))
				return columndata;
		}
		return null;
	}
	/**
	 * @param columnDatas
	 */
	public RowData(ColumnData[] columnDatas,RowModel rowmodel) {
		super();
		Column_Datas = columnDatas;
		this.rowmodel=rowmodel;
	}
	
	/**
	 * @param columnDatas
	 * @param lineValue
	 * @param lineNr
	 */
	public RowData(ColumnData[] columnDatas,RowModel rowmodel, String lineValue, long lineNr) {
		super();
		Column_Datas = columnDatas;
		this.rowmodel=rowmodel;
		LineValue = lineValue;
		LineNr = lineNr;
	}
	
	/**
	 * @param rowdata
	 * @return
	 */
	public RowData clone(RowData rowdata){
		
		ColumnData [] clonecoldatas=clonecoldata(rowdata.getColumn_Datas());
		String linevalue=rowdata.getLineValue();
		long linenr=rowdata.getLineNr(); 
		RowData clonerowdata=new RowData(clonecoldatas,rowmodel, linevalue, linenr);
		return clonerowdata;
	}
	
	private ColumnData [] clonecoldata(ColumnData [] cols){
		ColumnData [] columnDatas=new ColumnData[cols.length];
		for (int i = 0; i < cols.length; i++) {
			columnDatas[i]=cols[i].clone(cols[i]);
		}
		return columnDatas;
	}
	
//	public RowModel getRow_Model() {
//		return Row_Model;
//	}
//	public void setRow_Model(RowModel rowModel) {
//		Row_Model = rowModel;
//	}
	public ColumnData[] getColumn_Datas() {
		return Column_Datas;
	}
	public void setColumn_Datas(ColumnData[] columnDatas) {
		Column_Datas = columnDatas;
	}
	
	 @Override
	    public String toString() {
	        StringBuffer buffer = new StringBuffer();
	        for (int i=0;i<rowmodel.getCol_Size();i++)
	        {
	            if (i>0) buffer.append(", ");
	            buffer.append( "[" );
	            buffer.append(Column_Datas[i].toString());
	            buffer.append( "]" );
	        }
	        return buffer.toString();
	    }
	 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(Column_Datas);
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
		RowData other = (RowData) obj;
		if (!Arrays.equals(Column_Datas, other.Column_Datas))
			return false;
		return true;
	}

	public String getLineValue() {
		return LineValue;
	}

	public void setLineValue(String lineValue) {
		LineValue = lineValue;
	}

	public long getLineNr() {
		return LineNr;
	}

	public void setLineNr(long lineNr) {
		LineNr = lineNr;
	}

	public RowModel getRowmodel() {
		return rowmodel;
	}

	public void setRowmodel(RowModel rowmodel) {
		this.rowmodel = rowmodel;
	}
	
}
