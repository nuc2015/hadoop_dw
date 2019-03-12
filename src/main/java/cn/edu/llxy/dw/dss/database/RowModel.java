package cn.edu.llxy.dw.dss.database;

import java.util.ArrayList;
import java.util.List;

import cn.edu.llxy.dw.dss.exception.CheckedException;
import org.apache.log4j.Logger;


public class RowModel {

	private Logger log = Logger.getLogger(RowSet.class);

	public static int NONE_TYPE = -1;
	public static int NONE_TXT = 0;
	public static int NONE_TABLE = 1;
	public static int NONE_XML = 2;
	public static int NONE_EXCEL = 3;
	public static int NONE_CVS = 4;

	/**
	 * 行里面的列数字
	 */
	private int Col_Size;
	/**
	 * 行里面的所有列模型
	 */
	private List<ColumnModel>  Col_Models;
	/**
	 *组件的uid
	 */
	private String Step_Uid;
	/**
	 * 数据来源类型，xml,txt,xml,excel
	 */
	private int DataSrcType;
	/**
	 * 来源文件，例如 文本：201011.dat，表：test table
	 */
	private String ResourceSource;

	public RowModel(){
		Col_Models=new ArrayList<ColumnModel>();
	}
	/**
	 * @param colSize
	 * @param colModels
	 * @param stepUid
	 */
	public RowModel(int colSize, List<ColumnModel>  colModels, String stepUid) {
		super();
		Col_Size = colSize;
		Col_Models = colModels;
		Step_Uid = stepUid;
	}

	public RowModel(int colSize, List<ColumnModel> colModels, String stepUid, int dataSrcType, String resourceSource) {
		super();
		Col_Size = colSize;
		Col_Models = colModels;
		Step_Uid = stepUid;
		DataSrcType = dataSrcType;
		ResourceSource = resourceSource;
	}

	/**
	 * clone一组数据返回，对于多输入的时候，需要复制数据
	 * 
	 * @param row
	 * @return
	 */
	public RowData[] cloneRow(RowData[] rows) {
		RowData [] clonerowDatas=new RowData[rows.length];
		for (int i = 0; i < rows.length; i++) {
			clonerowDatas[i]=rows[i].clone(rows[i]);
		}
		return clonerowDatas;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public RowModel clone(){
		RowModel rowmodel=new RowModel();
		for (int i = 0; i < size(); i++) {
			ColumnModel colunmmodel=Col_Models.get(i);
			rowmodel.addColumnModel(colunmmodel);
		}
		return rowmodel;
	}
	
	public void addColumnModel(ColumnModel columnmodel){
		if(!exists(columnmodel)){
			Col_Models.add(columnmodel);
		}
		//if exist ranme columnmodel add to list
	}
	
	public void addColumnModel(int index,ColumnModel columnmodel){
		if(!exists(columnmodel)){
			Col_Models.add(index,columnmodel);
		}
		//if exist ranme columnmodel add to list
	}
	
	/**
	 * @param index
	 * @return
	 */
	public ColumnModel getColumnModel(int index){
		return Col_Models.get(index);
	}
	
	public void setRowModel(int index,ColumnModel columnmodel){
		Col_Models.set(index, columnmodel);
	}
	
	public void clear()
    {
		Col_Models.clear();
    }
	
	public void removeColumnModel(String columnName) throws CheckedException {
		int index=indexOfValue(columnName);
		if(index<0) throw new CheckedException("Unable to find value columnmodel with name '"+columnName+"', so I can't delete it.");
		Col_Models.remove(index);
	}
	
	 public int indexOfValue(String columnName){
		int index=0;
		 for (ColumnModel columnmodel : Col_Models) {
				if(columnName.equalsIgnoreCase(columnmodel.getCol_Name())) return index;
				else
					index++;
			}
	        return -1;
	    }
	/**
	 * @param columnName
	 * @return
	 */
	public ColumnModel serachColumnModel(String columnName){
		for (ColumnModel column : Col_Models) {
			if(column.getCol_Name().equalsIgnoreCase(columnName)) 
				return column;
		}
		return null;
	}
	
	/**
	 * @param columnt
	 * @return
	 */
	public boolean exists(ColumnModel columnt){
		return serachColumnModel(columnt.getCol_Name())!=null;
	}
	
	/**
	 * @return
	 */
	public boolean isEmpty()
    {
        return size()==0;
    }
	
	public int size(){
		return Col_Models.size();
	}
	

	public int getCol_Size() {
		return Col_Size;
	}

	public void setCol_Size(int colSize) {
		Col_Size = colSize;
	}



	public String getStep_Uid() {
		return Step_Uid;
	}

	public void setStep_Uid(String stepUid) {
		Step_Uid = stepUid;
	}

	public int getDataSrcType() {
		return DataSrcType;
	}

	public void setDataSrcType(int dataSrcType) {
		DataSrcType = dataSrcType;
	}

	public String getResourceSource() {
		return ResourceSource;
	}

	public void setResourceSource(String resourceSource) {
		ResourceSource = resourceSource;
	}
	public List<ColumnModel> getCol_Models() {
		return Col_Models;
	}
	public void setCol_Models(List<ColumnModel> col_Models) {
		Col_Models = col_Models;
	}

}