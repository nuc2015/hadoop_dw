package cn.edu.llxy.dw.dss.database;

import java.util.List;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;


public class ConvertModelUtil {

	
	/**
	 * convert row data to object array 
	 * @param rowdata
	 * @return
	 */
	public static Object [] ConvertRowDataToDataArray(RowData rowdata){
		if(rowdata.getColumn_Datas()==null)
			return null;
		int length=rowdata.getColumn_Datas().length;
		ColumnData [] columentdatas=rowdata.getColumn_Datas();
		Object [] objectarray=new Object[length];
		for (int i = 0; i < objectarray.length; i++) {
			objectarray[i]=columentdatas[i].getCol_Vlaue();
		}
		return objectarray;
	}
	/**
	 * convert rowmodel to rowmetainterface
	 * @param rowmodel
	 * @return
	 */
	public static RowMetaInterface ConvertRowModeToRMI(RowModel rowmodel){
		RowMetaInterface rowMeta = new RowMeta();
		List<ColumnModel> cols=rowmodel.getCol_Models();
		int fieldNr = 1;
		for (int i = 0; i < cols.size(); i++) {
			String name=new String(cols.get(i).getCol_Name());
            
            if (Const.isEmpty(name) || Const.onlySpaces(name))
            {
                name = "Field"+fieldNr;
                fieldNr++;
            }
            
			ValueMetaInterface v = new ValueMeta(name, cols.get(i).getCol_Type());
			rowMeta.addValueMeta(v);			
		}
		return rowMeta;
	}
}
