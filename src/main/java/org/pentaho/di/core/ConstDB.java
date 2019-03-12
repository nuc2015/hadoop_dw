/*
 * Copyright (c) 2010 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the GNU Lesser General Public License, Version 2.1. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.gnu.org/licenses/lgpl-2.1.txt. The Original Code is Pentaho 
 * Data Integration.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the GNU Lesser Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 */
package org.pentaho.di.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.database.SAPR3DatabaseMeta;

public class ConstDB {

	public static final Map<String,String> hbaseFieldTypeMap = new HashMap<String,String>();
	public static final Map<String,String> oarcleFieldTypeMap = new HashMap<String,String>();
	public static final Map<String,String> db2FieldTypeMap = new HashMap<String,String>();
	public static final Map<String,String> mysqlFieldTypeMap = new HashMap<String,String>();
	
	static{
		//NoSql 数据库
		//一、HBase数据库
		hbaseFieldTypeMap.put("String", "String"); //存放列族名
		//二、Hive 如果使用mysql数据库存储元数据，则采用mysql语法
		//二、Hive 如果使用deber数据库存储元数据，后续扩充
		
		
		//关系型数据库
		//一、oracle字段类型
		//http://www.cnblogs.com/Mr_JinRui/archive/2011/04/06/2006819.html
		//1、字符类型
		oarcleFieldTypeMap.put("CHAR", "CHAR(N)");  //char(n) n=1 to 2000字节 定长字符串，n字节长，如果不指定长度，缺省为1个字节长（一个汉字为2字节） 
		//oarcleFieldTypeMap.put("NCHAR", "NCHAR(N)"); //根据字符集而定的固定长度字符串    最大长度2000    bytes
		//oarcleFieldTypeMap.put("VARCHAR", "VARCHAR(N)");
		oarcleFieldTypeMap.put("VARCHAR2", "VARCHAR2(N)"); // n=1 to 4000字节 可变长的字符串，具体定义时指明最大长度n，可做索引的最大长度3209。
		oarcleFieldTypeMap.put("NVARCHAR2", "NVARCHAR2(N)");
		//2、数字类型
		oarcleFieldTypeMap.put("INTEGER", "INTEGER|INTEGER(N)"); //整数类型    小的整数     
		oarcleFieldTypeMap.put("NUMBER", "NUMBER|NUMBER(P,S)"); //P=1 to 38  S=-84 to 127 可变长的数值列，允许0、正值及负值，P是所有有效数字的位数，S是小数点以后的位数。
		oarcleFieldTypeMap.put("DECIMAL", "DECIMAL|DECIMAL(P,S)"); //数字类型    P为整数位，S为小数位 
		oarcleFieldTypeMap.put("FLOAT", "FLOAT|FLOAT(P,S)"); //浮点数类型    NUMBER(38)，双精度      
		//oarcleFieldTypeMap.put("REAL", "REAL|REAL(P,S)"); //实数类型    NUMBER(63)，精度更高    
		oarcleFieldTypeMap.put("DOUBLE", "DOUBLE|DOUBLE(P,S)");
		oarcleFieldTypeMap.put("BINARY_FLOAT", "BINARY_FLOAT"); //不常用
		oarcleFieldTypeMap.put("BINARY_DOUBLE", "BINARY_DOUBLE"); //不常用
		//3、日期类型
		oarcleFieldTypeMap.put("DATE", "DATE"); //日期（日-月-年）    DD-MM-YYYY（HH-MI-SS）
		oarcleFieldTypeMap.put("TIMESTAMP", "TIMESTAMP");
		//4、大对象类型
		oarcleFieldTypeMap.put("BLOB", "BLOB"); //二进制数据    最大长度4G
		oarcleFieldTypeMap.put("CLOB", "CLOB"); //字符数据    最大长度4G         
		//oarcleFieldTypeMap.put("NCLOB", "NCLOB"); //nclob 无 三种大型对象(LOB)，用来保存较大的图形文件或带格式的文本文件，如Miceosoft Word文档，以及音频、视频等非文本文件，最大长度是4GB。
		//oarcleFieldTypeMap.put("BFILE", "BFILE"); //存放在数据库外的二进制数据    最大长度4G         
		oarcleFieldTypeMap.put("LONG", "LONG"); // LONG    超长字符串    最大长度2G（231-1）    足够存储大部头著作; long是一种较老的数据类型，将来会逐渐被BLOB、CLOB、NCLOB等大的对象数据类型所取代。
		oarcleFieldTypeMap.put("RAW", "RAW(N)");  //固定长度的二进制数据    最大长度2000 bytes 可存放多媒体图象声音等; raw(n) n=1 to 2000 可变长二进制数据，在具体定义字段的时候必须指明最大长度n，Oracle 8i用这种格式来保存较小的图形文件或带格式的文本文件，如Miceosoft Word文档。
		oarcleFieldTypeMap.put("LONG RAW", "LONG RAW"); //long raw 无 可变长二进制数据，最大长度是2GB。Oracle 8i用这种格式来保存较大的图形文件或带格式的文本文件，如Miceosoft Word文档，以及音频、视频等非文本文件。
		//oarcleFieldTypeMap.put("BFILE", "BFILE"); //在数据库外部保存的大型二进制对象文件，最大长度是4GB ;BFILE数据类型用做指向存储在Oracle数据库以外的文件的指针。

		
		
		//二、db2字段类型
		//http://blog.csdn.net/dlodj/article/details/7033619
		//1、字符类型
		db2FieldTypeMap.put("CHAR", "CHAR(N)"); //定长字符串，长度范围1~254，常用
		db2FieldTypeMap.put("VARCHAR", "VARCHAR(N)"); //变长字符串，长度范围1~32672，常用		
		//2、数字类型
		db2FieldTypeMap.put("SMALLINT", "SMALLINT|SMALLINT(N)"); //短整型，范围为-32768~+32767，一遍用的较少
		db2FieldTypeMap.put("INT", "INT|INT(N)");//整型，4个字节，范围为-2147483648~+2147483647，一遍多用于做自动生成的序列，或者用作表记录的id使用。
		db2FieldTypeMap.put("INTEGER", "INTEGER|INTEGER(N)");//整型，4个字节，范围为-2147483648~+2147483647，一遍多用于做自动生成的序列，或者用作表记录的id使用。
		db2FieldTypeMap.put(" BIGINT", "BIGINT|BIGINT(N)"); //大整型，8个字节，精度为19位，够大了，一般较少使用。
		/** 4. DECIMAL(P,S)  ---小数型，其中P为精度，S为小数位数，隐含小数点(小数点不计入位数)。
		 *  比如M DECIMAL(5,2),那么就是指定M的精度为5位(除小数点外所有数字的位数不能超过5),否则插入时会发生数据位溢出;而小数的位数不能超过2,否则插入时会截断小数位。
			比如：M: 123.45(成功)  M:12.345(成功,但是截断为12.34)  M:1234.5(失败,整数位超标,溢出报错) */
		db2FieldTypeMap.put("DECIMAL", "DECIMAL|DECIMAL(P,S)"); 
		db2FieldTypeMap.put("REAL", "REAL|REAL(P,S)"); //单精度浮点型，不常用
		db2FieldTypeMap.put("DOUBLE", "DOUBLE|DOUBLE(P,S)"); //双精度浮点型，不常用		
		//3、日期类型
		db2FieldTypeMap.put("DATE", "DATE"); //占4个字节   插入格式为 'YYYY-MM-DD'
		db2FieldTypeMap.put("TIME", "TIME"); //占3个字节   插入格式为 'HH:MM:SS'
		db2FieldTypeMap.put("TIMESTAMP", "TIMESTAMP"); //占10个字节 插入格式为 'YYYY-MM-DD HH:MM:SS'		
		//4、大对象类型
		db2FieldTypeMap.put("CLOB", "CLOB");


		//三、mysql字段类型
		//http://www.chinaz.com/program/2009/0105/59154.shtml
		//1、字符类型
		mysqlFieldTypeMap.put("CHAR", "CHAR(N)"); //一个定长字符串，当存储时，总是是用空格填满右边到指定的长度。
		mysqlFieldTypeMap.put("NCHAR", "NCHAR(N)");
		mysqlFieldTypeMap.put("VARCHAR", "VARCHAR(N)"); //一个变长字符串。注意：当值被存储时，尾部的空格被删除(这不同于ANSI SQL规范)。M的范围是1 ～ 255个字符。
		//2、数字类型
		mysqlFieldTypeMap.put("TINYINT", 	"TINYINT|TINYINT(N)");     	//一个很小的整数。有符号的范围是-128到127，无符号的范围是0到255。
		mysqlFieldTypeMap.put("SMALLINT", 	"SMALLINT|SMALLINT(N)");  	//一个小整数。有符号的范围是-32768到32767，无符号的范围是0到65535。
		mysqlFieldTypeMap.put("MEDIUMINT",	"MEDIUMINT|MEDIUMINT(M) "); //一个中等大小整数。有符号的范围是-8388608到8388607，无符号的范围是0到16777215。
		mysqlFieldTypeMap.put("INT", 		"INT|INT(N)");     			//一个正常大小整数。有符号的范围是-2147483648到2147483647，无符号的范围是0到4294967295。
		//mysqlFieldTypeMap.put("INTEGER", 	"INTEGER|INTEGER(N)");     	//这是INT的一个同义词。
		mysqlFieldTypeMap.put("BIGINT", 	"BIGINT|BIGINT(N)");     	//一个大整数。有符号的范围是-9223372036854775808到9223372036854775807，无符号的范围是0到18446744073709551615。
		mysqlFieldTypeMap.put("FLOAT", 		"FLOAT|FLOAT(P,S)"); 		//一个小(单精密)浮点数字。不能无符号。允许的值是-3.402823466E+38到-1.175494351E-38，0 和1.175494351E-38到3.402823466E+38。
		mysqlFieldTypeMap.put("DOUBLE", 	"DOUBLE|DOUBLE(P,S)"); 		//一个正常大小(双精密)浮点数字。不能无符号。允许的值是-1.7976931348623157E+308到-2.2250738585072014E-308、 0和2.2250738585072014E-308到1.7976931348623157E+308。
		//mysqlFieldTypeMap.put("REAL", 		"REAL|REAL(P,S)");   		//这些是DOUBLE同义词。
		mysqlFieldTypeMap.put("DECIMAL", 	"DECIMAL|DECIMAL(P,S)");   	//一个未压缩(unpack)的浮点数字。不能无符号。行为如同一个CHAR列：“未压缩”意味着数字作为一个字符串被存储，值的每一位使用一个字符。小数点，并且对于负数，“-”符号不在M中计算。
		//mysqlFieldTypeMap.put("NUMERIC", 	"NUMERIC|NUMERIC(P,S)");   	//这是DECIMAL的一个同义词。
		//3、日期类型
		mysqlFieldTypeMap.put("DATE", "DATE"); 	//支持的范围是'1000-01-01'到'9999-12-31'。MySQL以'YYYY-MM-DD'格式来显示DATE值，但是允许你使用字符串或数字把值赋给DATE列。
		mysqlFieldTypeMap.put("TIME", "TIME"); 	//个时间。范围是'-838:59:59'到'838:59:59'。MySQL以'HH:MM:SS'格式来显示TIME值，但是允许你使用字符串或数字把值赋给TIME列。
		mysqlFieldTypeMap.put("DATETIME", "DATETIME"); //一个日期和时间组合。支持的范围是'1000-01-01 00:00:00'到'9999-12-31 23:59:59'。MySQL以'YYYY-MM-DD HH:MM:SS'格式来显示DATETIME值，但是允许你使用字符串或数字把值赋给DATETIME的列。
		mysqlFieldTypeMap.put("TIMESTAMP", "TIMESTAMP");//一个时间戳记。范围是'1970-01-01 00:00:00'到2037年的某时。MySQL以YYYYMMDDHHMMSS、YYMMDDHHMMSS、YYYYMMDD或YYMMDD 格式来显示TIMESTAMP值，取决于是否M是14(或省略)、12、8或6，但是允许你使用字符串或数字把值赋给TIMESTAMP列。
		mysqlFieldTypeMap.put("YEAR", "YEAR(YY)|YEAR(YYYY)"); 	//一个2或4位数字格式的年(缺省是4位)。允许的值是1901到2155，和0000(4位年格式)，如果你使用2位，1970-2069( 70-69)。
		//4、大对象类型
		mysqlFieldTypeMap.put("BLOB", "BLOB"); //一个BLOB或TEXT列，最大长度为65535(2^16-1)个字符。
		mysqlFieldTypeMap.put("TEXT", "TEXT");
		mysqlFieldTypeMap.put("TINYBLOB", "TINYBLOB"); //一个BLOB或TEXT列，最大长度为255(2^8-1)个字符。
		mysqlFieldTypeMap.put("TINYTEXT", "TINYTEXT"); 
		mysqlFieldTypeMap.put("MEDIUMBLOB", "MEDIUMBLOB"); //一个BLOB或TEXT列，最大长度为16777215(2^24-1)个字符。
		mysqlFieldTypeMap.put("MEDIUMTEXT", "MEDIUMTEXT"); 
		mysqlFieldTypeMap.put("LONGBLOB", "LONGBLOB"); //一个BLOB或TEXT列，最大长度为4294967295(2^32-1)个字符。
		mysqlFieldTypeMap.put("LONGTEXT", "LONGTEXT"); 
		//mysqlFieldTypeMap.put("ENUM", "ENUM(...)"); //ENUM('value1','value2',...) 枚举。一个仅有一个值的字符串对象，这个值式选自与值列表'value1'、'value2', ...,或NULL。一个ENUM最多能有65535不同的值。
		//mysqlFieldTypeMap.put("SET", "SET(...)"); //SET('value1','value2',...) 一个集合。能有零个或多个值的一个字符串对象，其中每一个必须从值列表'value1', 'value2', ...选出。一个SET最多能有64个成员。

	}
	
	/**
	 * Select the SAP ERP databases in the List of databases.
	 * @param databases All the databases
	 * @return SAP ERP databases in a List of databases.
	 */
	public static final List<DatabaseMeta> selectSAPR3Databases(List<DatabaseMeta> databases)
	{
		List<DatabaseMeta> sap = new ArrayList<DatabaseMeta>();

		for (DatabaseMeta db : databases)
		{
			if (db.getDatabaseInterface() instanceof SAPR3DatabaseMeta) {
				sap.add(db);
			}
		}

		return sap;
	}

}
