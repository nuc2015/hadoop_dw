package org.pentaho.di.core.database.util;

public final class ColumnTypeUtil {

	private static final String procedureColumnUnknown = "Unknown";
	private static final String procedureColumnIn = "IN";
	private static final String procedureColumnInOut = "INOUT";
	private static final String procedureColumnOut = "OUT";
	private static final String procedureColumnReturn = "Return";
	private static final String procedureColumnResult = "Result";

	public static String getColumnTypeName(long columnType) {
		if (columnType == java.sql.DatabaseMetaData.procedureColumnIn) {
			return procedureColumnIn;
		} else if (columnType == java.sql.DatabaseMetaData.procedureColumnInOut) {
			return procedureColumnInOut;
		} else if (columnType == java.sql.DatabaseMetaData.procedureColumnOut) {
			return procedureColumnOut;
		} else if (columnType == java.sql.DatabaseMetaData.procedureColumnReturn) {
			return procedureColumnReturn;
		} else if (columnType == java.sql.DatabaseMetaData.procedureColumnResult) {
			return procedureColumnResult;
		} else {
			return procedureColumnUnknown;
		}
	}
}
