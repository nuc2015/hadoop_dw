package cn.edu.llxy.dw.dss.database;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.llxy.dw.dss.exception.CheckedException;
import cn.edu.llxy.dw.dss.message.LogMessage;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.pentaho.di.core.row.ValueMeta;

import cn.edu.llxy.dw.dss.util.DateFormatUtil;
import cn.edu.llxy.dw.dss.util.NumberUtil;
import cn.edu.llxy.dw.dss.util.StringUtil;

public class ColumnModel {

	private static Logger log = Logger.getLogger(ColumnData.class);

	public static final int TYPE_NONE = 0;

	public static final int TYPE_NUMBER = 1;

	public static final int TYPE_STRING = 2;

	public static final int TYPE_DATE = 3;

	public static final int TYPE_BOOLEAN = 4;

	public static final int TYPE_INTEGER = 5;

	public static final int TYPE_BIGNUMBER = 6;

	public static final int TYPE_SERIALIZABLE = 7;

	public static final int TYPE_BINARY = 8;

	public static final String trimtypeCode[] = { "NONE", "LEFT", "RIGHT", "BOTH" };
	public static final String[] types = new String[] { "NONE", "NUMBER", "STRING", "DATE", "BOOLEAN", "INTEGER", "BIGNUMBER", "SERIALIZABLE", "BINARY" };
	public static final String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy", "dd-MM-yyyy", "yyyy/MM/dd", "yyyy-MM-dd", "yyyyMMdd", "ddMMyyyy" };
	public static final String[] numberFormats = new String[] { "", "#", "#.#", "#.##", "#.00", "#%", "###,###", "###,###.##", "###,###.00", "#.#%", "#.##%" };

	private int Col_Index;
	private String Col_Name;
	private int Col_Type;
	private String Col_Format;

	private ValueMeta valuemeta;
	/**
	 * @param colIndex
	 * @param colName
	 * @param colType
	 * @param colFormat
	 */
	public ColumnModel(int colIndex, String colName, int colType, String colFormat) {
		super();
		Col_Index = colIndex;
		Col_Name = colName;
		Col_Type = colType;
		Col_Format = colFormat;
		valuemeta=new ValueMeta(colName, colType);
		valuemeta.setStorageMetadata(valuemeta);
	}

	public ColumnModel clone(){
		try {
			ColumnModel columnmodel=new ColumnModel(Col_Index, Col_Name, Col_Type, Col_Format);
			return columnmodel;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public int getCol_Index() {
		return Col_Index;
	}

	public void setCol_Index(int colIndex) {
		Col_Index = colIndex;
	}

	public String getCol_Name() {
		return Col_Name;
	}

	public void setCol_Name(String colName) {
		Col_Name = colName;
	}

	public int getCol_Type() {
		return Col_Type;
	}

	public void setCol_Type(int colType) {
		Col_Type = colType;
	}

	public String getCol_Format() {
		return Col_Format;
	}

	public void setCol_Format(String colFormat) {
		Col_Format = colFormat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Col_Format == null) ? 0 : Col_Format.hashCode());
		result = prime * result + Col_Index;
		result = prime * result + ((Col_Name == null) ? 0 : Col_Name.hashCode());
		result = prime * result + Col_Type;
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
		ColumnModel other = (ColumnModel) obj;
		if (Col_Format == null) {
			if (other.Col_Format != null)
				return false;
		} else if (!Col_Format.equals(other.Col_Format))
			return false;
		if (Col_Index != other.Col_Index)
			return false;
		if (Col_Name == null) {
			if (other.Col_Name != null)
				return false;
		} else if (!Col_Name.equals(other.Col_Name))
			return false;
		if (Col_Type != other.Col_Type)
			return false;
		return true;
	}

	public static int getTrimType(String trimType) {
		if (trimtypeCode[0].equalsIgnoreCase(trimType)) {
			return 0;
		} else if (trimtypeCode[1].equalsIgnoreCase(trimType)) {
			return 1;
		} else if (trimtypeCode[2].equalsIgnoreCase(trimType)) {
			return 2;
		} else if (trimtypeCode[3].equalsIgnoreCase(trimType)) {
			return 3;
		} else {
			return 0;
		}
	}

	/**
	 * 0:"NONE"; 1:"NUMBER"; 2:"STRING", 3:"DATE", 4:"BOOLEAN", 5:"INTEGER", 6:"BIGNUMBER", 7:"SERIALIZABLE", 8:"BINARY"
	 * 
	 * @param type
	 * @return
	 */
	public static int getType(String type) {
		if (types[0].equalsIgnoreCase(type)) {
			return TYPE_NONE;
		} else if (types[1].equalsIgnoreCase(type)) {
			return TYPE_NUMBER;
		} else if (types[2].equalsIgnoreCase(type)) {
			return TYPE_STRING;
		} else if (types[3].equalsIgnoreCase(type)) {
			return TYPE_DATE;
		} else if (types[4].equalsIgnoreCase(type)) {
			return TYPE_BOOLEAN;
		} else if (types[5].equalsIgnoreCase(type)) {
			return TYPE_INTEGER;
		} else if (types[6].equalsIgnoreCase(type)) {
			return TYPE_BIGNUMBER;
		} else {
			return TYPE_NONE;
		}
	}

	public static boolean validType(String value, Integer type) {
		log.debug(LogMessage.getString("column.type.valid"));
		switch (type) {
		case TYPE_NUMBER:// NUMBER
			if (NumberUtils.isNumber(value)) {
				return true;
			} else {
				return false;
			}
		case TYPE_STRING:// STRING
			return true;
		case TYPE_DATE:// DATE
			if (DateFormatUtil.isValidDate(value)) {
				return true;
			} else {
				return false;
			}
		case TYPE_BOOLEAN:// BOOLEAN
			if (StringUtil.isBoolean(value)) {
				return true;
			} else {
				return false;
			}
		case TYPE_INTEGER:// INTEGER
			if (NumberUtil.isInteger(value)) {
				return true;
			} else {
				return false;
			}
		case TYPE_BIGNUMBER:// BIGNUMBER
			if (NumberUtil.isNumber(value)) {
				return true;
			} else {
				return false;
			}
		default:// NONE
			return false;
		}
	}

	public static String formatValue(String value, Integer type, String format) throws CheckedException {
		log.debug(LogMessage.getString("column.value.format"));
		switch (type) {
		case TYPE_NUMBER:// NUMBER
			if (NumberUtil.isNumber(value)) {
				if (StringUtil.isEmpty(format)) {
					return value;
				}
				return NumberUtil.format(format, value);
			} else {
				throw new CheckedException(LogMessage.getString("column.value.format.invalid"));
			}
		case TYPE_STRING:// STRING
			return value;
		case TYPE_DATE:// DATE
			if (DateFormatUtil.isValidDate(value)) {
				if (StringUtil.isEmpty(format)) {
					return value;
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				return DateFormatUtil.format(DateFormatUtil.parseDate(value, dateFormat), dateFormat);
			} else {
				throw new CheckedException(LogMessage.getString("column.value.format.invalid"));
			}
		case TYPE_BOOLEAN:// BOOLEAN
			if (StringUtil.isBoolean(value)) {
				return value;
			} else {
				throw new CheckedException(LogMessage.getString("column.value.format.invalid"));
			}
		case TYPE_INTEGER:// INTEGER
			if (NumberUtil.isInteger(value)) {
				if (StringUtil.isEmpty(format)) {
					return value;
				}
				return NumberUtil.format(format, value);
			} else {
				throw new CheckedException(LogMessage.getString("column.value.format.invalid"));
			}
		case TYPE_BIGNUMBER:// BIGNUMBER
			if (NumberUtil.isNumber(value)) {
				if (StringUtil.isEmpty(format)) {
					return value;
				}
				return NumberUtil.format(format, value);
			} else {
				throw new CheckedException(LogMessage.getString("column.value.format.invalid"));
			}
		default:// NONE
			throw new CheckedException(LogMessage.getString("column.value.format.invalid"));
		}
	}
	
	public static Object getValue(String valueStr, Integer type, String format) throws CheckedException {
		switch (type) {
		case TYPE_NUMBER:// NUMBER
			if (!NumberUtil.isNumber(valueStr)) {
				throw new CheckedException(LogMessage.getString("column.value.format.invalid"));
			}
			return Double.parseDouble(valueStr);
		case TYPE_STRING:// STRING
			return valueStr;
		case TYPE_DATE:// DATE
			if (!DateFormatUtil.isValidDate(valueStr)) {
				throw new CheckedException(LogMessage.getString("column.value.format.invalid"));
			} 
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return DateFormatUtil.parseDate(valueStr, dateFormat);
		case TYPE_BOOLEAN:// BOOLEAN
			if (StringUtil.isBoolean(valueStr)) {
				throw new CheckedException(LogMessage.getString("column.value.format.invalid"));
			} 
			return StringUtil.toBoolean(valueStr);
		case TYPE_INTEGER:// INTEGER
			if (!NumberUtil.isInteger(valueStr)) {
				throw new CheckedException(LogMessage.getString("column.value.format.invalid"));
			} 
			return Integer.parseInt(valueStr);
		case TYPE_BIGNUMBER:// BIGNUMBER
			if (!NumberUtil.isNumber(valueStr)) {
				throw new CheckedException(LogMessage.getString("column.value.format.invalid"));
			}
			return BigDecimal.valueOf(Double.parseDouble(valueStr));
		default:// NONE
			throw new CheckedException(LogMessage.getString("column.value.format.invalid"));
		}
	}


	public static int getValueFromSQLType(int type) throws SQLException {
		int valtype;
		switch (type) {
		case java.sql.Types.CHAR:
		case java.sql.Types.VARCHAR:
		case java.sql.Types.LONGVARCHAR: // Character Large Object
		case java.sql.Types.CLOB:
			valtype = TYPE_STRING;
			break;

		case java.sql.Types.BIGINT:
		case java.sql.Types.INTEGER:
		case java.sql.Types.SMALLINT:
		case java.sql.Types.TINYINT:
			valtype = TYPE_INTEGER;
			break;

		case java.sql.Types.DECIMAL:
		case java.sql.Types.DOUBLE:
		case java.sql.Types.FLOAT:
		case java.sql.Types.REAL:
		case java.sql.Types.NUMERIC:
			valtype = TYPE_NUMBER;
			break;

		case java.sql.Types.DATE:
		case java.sql.Types.TIME:
		case java.sql.Types.TIMESTAMP:
			valtype = TYPE_DATE;
			break;

		case java.sql.Types.BOOLEAN:
		case java.sql.Types.BIT:
			valtype = TYPE_BOOLEAN;
			break;

		case java.sql.Types.BINARY:
		case java.sql.Types.BLOB:
		case java.sql.Types.VARBINARY:
		case java.sql.Types.LONGVARBINARY:
			valtype = TYPE_BINARY;
			break;

		default:
			valtype = TYPE_STRING;
			break;
		}

		return valtype;
	}
	
	/**
	 * immport kittle ValueMetaInterface Mehtod--by qinpeng
	 * @throws Exception 
	 */
  /* Conversion methods */
    
    public Object cloneValueData(Object object) throws Exception{
    	return valuemeta.cloneValueData(object);
    }

    /** Convert the supplied data to a String compatible with version 2.5. */
    public String getCompatibleString(Object object) throws Exception{
    	return valuemeta.getCompatibleString(object);
    }

    /** Convert the supplied data to a String */
    public String getString(Object object)throws Exception{
    	return valuemeta.getString(object);
    }

    /** convert the supplied data to a binary string representation (for writing text) */
    public byte[] getBinaryString(Object object) throws Exception{
    	return valuemeta.getBinary(object);
    }
    
    /** Convert the supplied data to a Number */
    public Double getNumber(Object object) throws Exception{
    	return valuemeta.getNumber(object);
    }

    /** Convert the supplied data to a BigNumber */
    public BigDecimal getBigNumber(Object object) throws Exception{
    	return valuemeta.getBigNumber(object);
    }

    /** Convert the supplied data to an Integer*/
    public Long getInteger(Object object) throws Exception{
    	return valuemeta.getInteger(object);
    }

    /** Convert the supplied data to a Date */
    public Date getDate(Object object) throws Exception{
    	return valuemeta.getDate(object);
    }

    /** Convert the supplied data to a Boolean */
    public Boolean getBoolean(Object object) throws Exception{
    	return valuemeta.getBoolean(object);
    }

    /** Convert the supplied data to binary data */
    public byte[] getBinary(Object object) throws Exception{
    	return valuemeta.getBinary(object);
    }
    
    /**
     * Checks wheter or not the value is a String.
     * @return true if the value is a String.
     */
    public boolean isString(){
    	return valuemeta.isString();
    }

    /**
     * Checks whether or not this value is a Date
     * @return true if the value is a Date
     */
    public boolean isDate(){
    	return valuemeta.isDate();
    }

    /**
     * Checks whether or not the value is a Big Number
     * @return true is this value is a big number
     */
    public boolean isBigNumber(){
    	return valuemeta.isBigNumber();
    }

    /**
     * Checks whether or not the value is a Number
     * @return true is this value is a number
     */
    public boolean isNumber(){
    	return valuemeta.isNumber();
    }

    /**
     * Checks whether or not this value is a boolean
     * @return true if this value has type boolean.
     */
    public boolean isBoolean(){
    	return valuemeta.isBoolean();
    }

    /**
     * Checks whether or not this value is of type Serializable
     * @return true if this value has type Serializable
     */
    public boolean isSerializableType(){
    	return valuemeta.isSerializableType();
    }

    /**
     * Checks whether or not this value is of type Binary
     * @return true if this value has type Binary
     */
    public boolean isBinary(){
    	return valuemeta.isBinary();
    }
    
    /**
     * Checks whether or not this value is an Integer
     * @return true if this value is an integer
     */
    public boolean isInteger(){
    	return valuemeta.isInteger();
    }

    /**
     * Checks whether or not this Value is Numeric
     * A Value is numeric if it is either of type Number or Integer
     * @return true if the value is either of type Number or Integer
     */
    public boolean isNumeric(){
    	return valuemeta.isNumeric();
    }
    
    public int compare(Object data1, Object data2) throws Exception{
    	return valuemeta.compare(data1, data2);
    }

    /**
     * Compare 2 values of the same data type
     * @param data1 the first value
     * @param meta2 the second value's metadata
     * @param data2 the second value
     * @return 0 if the values are equal, -1 if data1 is smaller than data2 and +1 if it's larger.
     * @throws Exception In case we get conversion errors
     */
    public int compare(Object data1, ColumnModel meta2, Object data2) throws Exception{
    		return valuemeta.compare(data1, meta2.valuemeta, data2);
    }
    
    /**
     * Convert the specified data to the data type specified in this object.
     * @param meta2 the metadata of the object to be converted
     * @param data2 the data of the object to be converted
     * @return the object in the data type of this value metadata object
     * @throws Exception in case there is a data conversion error
     */
    public Object convertData(ColumnModel meta2, Object data2) throws Exception{
    	return  valuemeta.convertData(meta2.valuemeta, data2);
    }

    /**
     * Convert the specified data to the data type specified in this object.
     * For String conversion, be compatible with version 2.5.2.
     * 
     * @param meta2 the metadata of the object to be converted
     * @param data2 the data of the object to be converted
     * @return the object in the data type of this value metadata object
     * @throws Exception in case there is a data conversion error
     */
    public Object convertDataCompatible(ColumnModel meta2, Object data2) throws Exception{
    	return valuemeta.convertDataCompatible(meta2.valuemeta, data2);
    }

    /**
     * Convert an object to the data type specified in the conversion metadata
     * @param data The data
     * @return The data converted to the conversion data type
     * @throws Exception in case there is a conversion error.
     */
//    public Object convertDataUsingConversionMetaData(Object data) throws Exception{
//    	return valuemeta.convertDataUsingConversionMetaData(data);
//    }
    /**
     * Convert the specified string to the data type specified in this object.
     * @param pol the string to be converted
     * @param convertMeta the metadata of the object (only string type) to be converted
     * @param nullif set the object to null if pos equals nullif (IgnoreCase)
     * @param ifNull set the object to ifNull when pol is empty or null
     * @param trim_type the trim type to be used (ValueMetaInterface.TRIM_TYPE_XXX)
     * @return the object in the data type of this value metadata object
     * @throws Exception in case there is a data conversion error
     */
    public Object convertDataFromString(String pol, ColumnModel convertMeta, String nullif, String ifNull, int trim_type) throws Exception{
    	return valuemeta.convertDataFromString(pol, convertMeta.valuemeta, nullif, ifNull, trim_type);
    }
    
    /**
     * Converts the specified data object to the normal storage type.
     * @param object the data object to convert
     * @return the data in a normal storage type
     * @throws Exception In case there is a data conversion error.
     */
    public Object convertToNormalStorageType(Object object) throws Exception{
    	return valuemeta.convertToNormalStorageType(object);
    }
    
    /**
     * Convert the given binary data to the actual data type.<br> 
     * - byte[] --> Long (Integer)<br>
     * - byte[] --> Double (Number)<br>
     * - byte[] --> BigDecimal (BigNumber)<br>
     * - byte[] --> Date (Date)<br>
     * - byte[] --> Boolean (Boolean)<br>
     * - byte[] --> byte[] (Binary)<br>
     * <br>
     * @param binary the binary data read from file or database
     * @return the native data type after conversion
     * @throws Exception in case there is a data conversion error
     */
    public Object convertBinaryStringToNativeType(byte[] binary) throws Exception{
    	return valuemeta.convertBinaryStringToNativeType(binary);
    }

    /**
     * Convert a normal storage type to a binary string object. (for comparison reasons)
     * @param object The object expressed in a normal storage type 
     * @return a binary string
     * @throws Exception in case there is a data conversion error
     */
    public Object convertNormalStorageTypeToBinaryString(Object object) throws Exception{
    	return valuemeta.convertNormalStorageTypeToBinaryString(object);
    }
    
    /**
     * Converts the specified data object to the binary string storage type.
     * @param object the data object to convert
     * @return the data in a binary string storage type
     * @throws Exception In case there is a data conversion error.
     */
    public Object convertToBinaryStringStorageType(Object object) throws Exception{
    		return valuemeta.convertToBinaryStringStorageType(object);
    }
    
    /**
     * Calculate the hashcode of the specified data object
     * @param object the data value to calculate a hashcode for 
     * @return the calculated hashcode
     * @throws Exception in case there is a data conversion error
     */
    public int hashCode(Object object) throws Exception{
    	return valuemeta.hashCode(object);
    }
    
    /**
     * Create an old-style value for backward compatibility reasons
     * @param data the data to store in the value
     * @return a newly created Value object
     * @throws Exception in case there is a data conversion problem 
     */
    public Object createOriginalValue(Object data) throws Exception{
    	return valuemeta.getValueData(valuemeta.createOriginalValue(data));
    }
    
    /**
     * Extracts the primitive data from an old style Value object 
     * @param value the old style Value object 
     * @return the value's data, NOT the meta data.
     * @throws Exception  case there is a data conversion problem
     
    public Object getValueData(Value value) throws Exception{
    	
    }*/
}