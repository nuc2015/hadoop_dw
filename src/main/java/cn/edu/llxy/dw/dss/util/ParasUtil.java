package cn.edu.llxy.dw.dss.util;


import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


public class ParasUtil {
	private static final String INTERFACE = "\\$\\{interface\\}";
	private static final String BATCHNO = "\\$\\{bno\\}";

	private static final String SNO = "\\$\\{sno\\}";
	private static final String INTERFACEFULL = "\\$\\{interface_sno\\}";
	private static final String SUBSYS = "\\$\\{subsys\\}";
	private static final String COUNT = "\\$\\{count:(\\d)\\}";
	private static final String TimePatternStr = "\\$\\{time:(?:([-L]?\\d{0,2}))?(?:(-?\\d{0,2})h)?(?:(-?\\d{0,2})m)?(?:(yyyy|yy)(?:\\((-?\\d*)\\))?)?([-/.: ]?)(?:(MM)(?:\\((-?\\d*)\\))?)?([-/.: ]?)(?:(dd)(?:\\((-?\\d*)\\))?)?([-/.: ]?)(HH)?([-/.: ]?)(?:(mm)(?:\\[(\\d*)\\])?)?([-/.: ]?)(ss)?\\}";
	private static final String SubstrPattenStr = "\\$\\{substr:\\((\\d+),?(\\d+)?\\)\\}";
	private static final String SeqPatternStr = "\\$\\{sequence:(\\d)\\}";
	private static final String GlobalSeqPatternStr = "\\$\\{global_sequence\\}";
	private static final String PluginSeqPatternStr = "\\$\\{plugin_sequence:(\\d)\\}";
	private static final String IncrementPatternStr = "\\$\\{increment:(.+?)\\}";
	private static final String PostfixPatternStr = "(\\.[^\\.]+)$";
	private static final String WithoutPostfixPatternStr = "(.*)\\.[^\\.]+$";
	private static final String ReTransNoStr = "\\$\\{retrans_no\\}";
	private static final String SERAILNo = "\\$\\{serial_no\\}";

	private static final Pattern TimePattern = Pattern.compile(TimePatternStr);
	private static final Pattern CountPattern = Pattern.compile(COUNT);
	private static final Pattern SeqPattern = Pattern.compile(SeqPatternStr);
	private static final Pattern GlobalSeqPattern = Pattern.compile(GlobalSeqPatternStr);
	private static final Pattern PluginSeqPattern = Pattern.compile(PluginSeqPatternStr);
	private static final Pattern SubstrPattern = Pattern.compile(SubstrPattenStr);
	private static final Pattern PostfixPattern = Pattern.compile(PostfixPatternStr);
	private static final Pattern IncrementPattern = Pattern.compile(IncrementPatternStr);

	private static final String getDateFromFilename = "^\\w{6}(\\d{8})";
	private static final Pattern getDateFromFilenamePattern = Pattern.compile(getDateFromFilename);

	private static final String getDateFromFilename6 = "^\\w{6}(\\d{6})";
	private static final Pattern getDateFromFilenamePattern6 = Pattern.compile(getDateFromFilename6);

	private static final String dateFunc = "\\$\\{date\\}";
	private static final Pattern dateFuncPattern = Pattern.compile(dateFunc);

	private static final String DateStrFromLoadCheckFilename = ".*(\\d{8})$";
	private static final Pattern DateStrFromLoadPattern = Pattern.compile(DateStrFromLoadCheckFilename);
	private static final String DateStrFromLoadCheckFilenameW = ".*txDate=(\\d{8})$";
	private static final Pattern DateStrFromLoadPatternW = Pattern.compile(DateStrFromLoadCheckFilenameW);

	private final static String YEAR_INCREASE = "1";
	private final static String MONTH_INCREASE = "3";
	private final static String DAY_INCREASE = "5";
	private final static String HOUR_INCREASE = "7";

	private final static int amount = -1;

	public static String replaceReTransNo(String paras, String reTransNo) {
		if (paras == null)
			return null;

		if (reTransNo != null)
			paras = paras.replaceAll(ReTransNoStr, reTransNo);

		return paras;
	}

	public static String relpaceSerialNo(String paras, String serialNo) {
		if (paras == null)
			return null;

		if (serialNo != null)
			paras = paras.replaceAll(SERAILNo, serialNo);

		return paras;
	}

	public static String getDateStrFromLoadCheckFilenameW(String retStr) {
		Matcher matcher = DateStrFromLoadPatternW.matcher(retStr);
		if (!matcher.find())
			return null;
		return matcher.group(1);
	}

	public static String getDateStrFromLoadCheckFilename(String filename) {
		Matcher matcher = DateStrFromLoadPattern.matcher(filename);
		if (!matcher.find())
			return null;
		return matcher.group(1);
	}

	public static String getDateStrFromFilename(String filename) {
		Matcher matcher = getDateFromFilenamePattern.matcher(filename);
		if (!matcher.find())
			return null;
		return matcher.group(1);
	}

	public static String getDateStrFromFilename6(String filename) {
		Matcher matcher = getDateFromFilenamePattern6.matcher(filename);
		if (!matcher.find())
			return null;
		return matcher.group(1);
	}

	public static String getDateFunc(String jobParas, String dateStr) {
		Matcher matcher = dateFuncPattern.matcher(jobParas);
		if (!matcher.find())
			return null;
		return matcher.replaceAll(dateStr);
	}

	public static String getIncrementSQL(String sqlTemplate, String jobType, Date date) {
		Matcher matcher = IncrementPattern.matcher(sqlTemplate);
		if (!matcher.find())
			return sqlTemplate;
		String colName = matcher.group(1);
		String dateStr = colName + " >= " + getToDateStr(getStartDateStr(jobType, date)) + " and " + colName + " < "
				+ getToDateStr(getEndDateStr(jobType, date));

		return matcher.replaceAll(dateStr);
	}

	// kuka add sql services replace startdate and enddate
	public static String getIncrementSQL(String sqlTemplate, String jobType, Date startdate, Date enddate) {
		Matcher matcher = IncrementPattern.matcher(sqlTemplate);
		if (!matcher.find())
			return sqlTemplate;
		String colName = matcher.group(1);
		String dateStr = colName + " >= " + getToDateStr(DateFormatUtil.formatLong19(startdate)) + " and " + colName
				+ " < " + getToDateStr(DateFormatUtil.formatLong19(enddate));

		return matcher.replaceAll(dateStr);
	}

	private static String getToDateStr(String dateStr) {
		return "to_date('" + dateStr + "','YYYY-MM-DD HH24:MI:SS')";
	}

	private static String getStartDateStr(String jobType, Date date) {
		// 0：年全 1：年增 2：月全 3：月增 4：日全 5：日增 6：时全 7：时增
		Date start;
		if (jobType.equalsIgnoreCase(YEAR_INCREASE)) {
			start = DateUtil.addYears(date, amount);
			start = DateUtil.truncate(start, Calendar.YEAR);
			return DateFormatUtil.formatLong19(start);
		}
		if (jobType.equalsIgnoreCase(MONTH_INCREASE)) {
			start = DateUtil.addMonths(date, amount);
			start = DateUtil.truncate(start, Calendar.MONTH);
			return DateFormatUtil.formatLong19(start);
		}
		if (jobType.equalsIgnoreCase(DAY_INCREASE)) {
			start = DateUtil.addDays(date, amount);
			start = DateUtil.truncate(start, Calendar.DATE);
			return DateFormatUtil.formatLong19(start);
		}
		if (jobType.equalsIgnoreCase(HOUR_INCREASE)) {
			start = DateUtil.addHours(date, amount);
			start = DateUtil.truncate(start, Calendar.HOUR);
			return DateFormatUtil.formatLong19(start);
		}
		return DateFormatUtil.formatLong19(date);
	}

	private static String getEndDateStr(String jobType, Date date) {
		// 0：年全 1：年增 2：月全 3：月增 4：日全 5：日增 6：时全 7：时增
		Date start;
		if (jobType.equalsIgnoreCase(YEAR_INCREASE)) {
			start = DateUtil.truncate(date, Calendar.YEAR);
			return DateFormatUtil.formatLong19(start);
		}
		if (jobType.equalsIgnoreCase(MONTH_INCREASE)) {
			start = DateUtil.truncate(date, Calendar.MONTH);
			return DateFormatUtil.formatLong19(start);
		}
		if (jobType.equalsIgnoreCase(DAY_INCREASE)) {
			start = DateUtil.truncate(date, Calendar.DATE);
			return DateFormatUtil.formatLong19(start);
		}
		if (jobType.equalsIgnoreCase(HOUR_INCREASE)) {
			start = DateUtil.truncate(date, Calendar.HOUR);
			return DateFormatUtil.formatLong19(start);
		}
		return DateFormatUtil.formatLong19(date);
	}

	public static String getPostfix(String fileName) {
		Matcher matcher = PostfixPattern.matcher(fileName);
		if (!matcher.find())
			return "";
		return matcher.group(1);
	}

	public static String getWithoutPostfix(String fileName) {
		return fileName.replaceFirst(WithoutPostfixPatternStr, "$1");
	}

	public static String getSubstrReplace(String oldStr, String subString) {
		String lastStr = oldStr;
		String newStr = getSubstrReplaceFirst(lastStr, subString);
		while (!newStr.equals(lastStr)) {
			lastStr = newStr;
			newStr = getSubstrReplaceFirst(newStr, subString);
		}
		return newStr;
	}

	public static String getSubstrReplaceFirst(String oldStr, String fullString) {
		if (fullString == null)
			return oldStr;
		Matcher matcher = SubstrPattern.matcher(oldStr);
		if (!matcher.find())
			return oldStr;

		int length = fullString.length();

		String from = matcher.group(1);
		int fromIn = Integer.parseInt(from);
		if (fromIn >= length)
			return matcher.replaceFirst(fullString);

		String to = matcher.group(2);
		if (StringUtils.isNotEmpty(to)) {
			int toIn = Integer.parseInt(to);
			if (toIn > length) {
				toIn = length;
			}
			if (fromIn >= toIn) {
				return matcher.replaceFirst(fullString);
			}
			return matcher.replaceFirst(fullString.substring(fromIn, toIn));
		} else {
			return matcher.replaceFirst(fullString.substring(fromIn));
		}
	}

	public static String getSubstrVar(String oldStr, String fullString) {
		if (fullString == null) {
			return null;
		}

		Matcher matcher = SubstrPattern.matcher(oldStr);
		if (!matcher.find()) {
			return null;
		}

		int length = fullString.length();

		String from = matcher.group(1);
		int fromIn = Integer.parseInt(from);
		if (fromIn >= length) {
			return fullString;
		}

		String to = matcher.group(2);
		if (StringUtils.isNotEmpty(to)) {
			int toIn = Integer.parseInt(to);
			if (toIn > length) {
				toIn = length;
			}
			if (fromIn >= toIn) {
				return fullString;
			}
			return fullString.substring(fromIn, toIn);
		} else {
			return fullString.substring(fromIn);
		}
	}

	public static String repalceParas(String oldStr, String interfaceId, String interfaceNo, String subsys) {
		if (oldStr == null)
			return null;

		if (interfaceId != null)
			oldStr = oldStr.replaceAll(INTERFACE, interfaceId);
		if (interfaceNo != null)
			oldStr = oldStr.replaceAll(SNO, interfaceNo);
		if (interfaceId != null && interfaceNo != null)
			oldStr = oldStr.replaceAll(INTERFACEFULL, interfaceId + interfaceNo);
		if (subsys != null)
			oldStr = oldStr.replaceAll(SUBSYS, subsys);

		return oldStr;
	}

	// kuka add replace batach no
	public static String repalceBatchNO(String oldStr, String batchNo) {
		if (oldStr == null)
			return null;

		if (batchNo != null)
			oldStr = oldStr.replaceAll(BATCHNO, batchNo);
		return oldStr;
	}

	/**
	 * replace string include sno varibale
	 *
	 * @param oldStr
	 * @param sno
	 * @return
	 */
	public static String relacesno(String oldStr, String sno) {
		if (oldStr == null)
			return null;

		if (sno != null)
			oldStr = oldStr.replaceAll(SNO, sno);
		return oldStr;
	}

	/**
	 * ${count:3}，如果值 为5，补齐为3位，返回005；如果值 为10005，超出3位，则直接返回10005
	 *
	 * @param oldStr
	 * @param countValue
	 * @return
	 */
	public static String getCountReplace(String oldStr, String countValue) {
		Matcher matcher = CountPattern.matcher(oldStr);
		if (!matcher.find())
			return oldStr;
		String ln = matcher.group(1);
		int length = Integer.parseInt(ln);
		if (countValue.length() < length) {
			for (int i = countValue.length(); i < length; i++)
				countValue = "0" + countValue;
		}
		return matcher.replaceFirst(countValue);
	}

	public static String getSeqReplace(String oldStr, Integer seqNo) {
		Matcher matcher = SeqPattern.matcher(oldStr);
		if (!matcher.find())
			return oldStr;
		String seqStr = "0";
		if (seqNo != null)
			seqStr = seqNo.toString();
		String ln = matcher.group(1);
		int length = Integer.parseInt(ln);
		if (seqStr.length() < length) {
			for (int i = seqStr.length(); i < length; i++)
				seqStr = "0" + seqStr;
		}

		return getSeqReplace(matcher.replaceFirst(seqStr), seqNo);
	}

	public static String getSeqPatternStr(String oldStr) {
		Matcher matcher = SeqPattern.matcher(oldStr);
		if (!matcher.find())
			return oldStr;

		String ln = matcher.group(1);

		return matcher.replaceFirst("\\\\d{" + ln + "}");
	}

	public static int getSeqPatternLn(String oldStr) {
		Matcher matcher = SeqPattern.matcher(oldStr);
		if (!matcher.find())
			return 0;

		String ln = matcher.group(1);

		return Integer.parseInt(ln);
	}

	public static String removeSeqPatternStr(String oldStr) {
		Matcher matcher = SeqPattern.matcher(oldStr);
		if (!matcher.find())
			return oldStr;
		String retStr = matcher.replaceFirst("");

		return retStr;
	}

	public synchronized static String replaceGlobalSeq(String oldStr, Long seq) {
		Matcher matcher = GlobalSeqPattern.matcher(oldStr);
		if (!matcher.find())
			return oldStr;
		return matcher.replaceFirst(seq.toString());
	}

	public static String replacePluginSeq(String oldStr, Long seq) {
		Matcher matcher = PluginSeqPattern.matcher(oldStr);
		if (!matcher.find())
			return oldStr;
		String seqStr = "0";
		if (seq != null)
			seqStr = seq.toString();
		String ln = matcher.group(1);
		int length = Integer.parseInt(ln);
		if (seqStr.length() < length) {
			for (int i = seqStr.length(); i < length; i++)
				seqStr = "0" + seqStr;
		}
		return matcher.replaceFirst(seqStr);
	}

	public static String getFirstTimeStrFromFileName(String oldStr, Date date) {
		if (date == null)
			return "";
		Matcher matcher = TimePattern.matcher(oldStr);
		if (!matcher.find())
			return "";

		String replaceStr = getReplaceTimeStr(date, matcher);

		return replaceStr;
	}

	private static String getReplaceTimeStr(Date date, Matcher matcher) {
		Date thisDate = new Date(date.getTime());

		String replaceStr = "";
		String[] matcherGroup = new String[19];

		for (int i = 1; i < matcherGroup.length; i++)
			matcherGroup[i] = matcher.group(i);

		if (!StringUtil.isEmpty(matcherGroup[1])) {
			if(matcherGroup[1].startsWith("L")){
				int ld = Integer.parseInt(matcherGroup[1].substring(1));
				Calendar dateTime = Calendar.getInstance();
				dateTime.setTime(thisDate);
				int td = dateTime.get(Calendar.DAY_OF_MONTH);
				thisDate = DateUtil.addDays(thisDate, -ld-td);
			}else{
				thisDate = DateUtil.addDays(thisDate, Integer.parseInt(matcherGroup[1]));
			}
		}
		if (matcherGroup[2] != null) {
			thisDate = DateUtil.addHours(thisDate, Integer.parseInt(matcherGroup[2]));
		}
		if (matcherGroup[3] != null) {
			thisDate = DateUtil.addMinutes(thisDate, Integer.parseInt(matcherGroup[3]));
		}
		if (matcherGroup[4] != null) {
			if (matcherGroup[5] != null)
				thisDate = DateUtil.addYears(thisDate, Integer.parseInt(matcherGroup[5]));
		}
		if (matcherGroup[7] != null) {
			if (matcherGroup[8] != null)
				thisDate = DateUtil.addMonths(thisDate, Integer.parseInt(matcherGroup[8]));
		}
		if (matcherGroup[10] != null) {
			if (matcherGroup[11] != null)
				thisDate = DateUtil.addDays(thisDate, Integer.parseInt(matcherGroup[11]));
		}
		String dateStr = DateFormatUtil.formatDateShort14(thisDate);
		String yStr = matcherGroup[4];
		if (yStr != null) {
			String year;
			if (yStr.equals("yy"))
				year = dateStr.substring(2, 4);
			else
				year = dateStr.substring(0, 4);

			replaceStr += year;
		}
		if (matcherGroup[6] != null)
			replaceStr += matcherGroup[6];

		if (matcherGroup[7] != null) {
			String month = dateStr.substring(4, 6);
			replaceStr += month;
		}
		if (matcherGroup[9] != null)
			replaceStr += matcherGroup[9];

		if (matcherGroup[10] != null) {
			String day = dateStr.substring(6, 8);
			replaceStr += day;
		}

		if (matcherGroup[12] != null)
			replaceStr += matcherGroup[12];

		if (matcherGroup[13] != null) {
			String hour = dateStr.substring(8, 10);
			replaceStr += hour;
		}
		if (matcherGroup[14] != null)
			replaceStr += matcherGroup[14];
		if (matcherGroup[15] != null) {
			String minute = dateStr.substring(10, 12);
			if (matcherGroup[16] != null) {
				int tmpI = Integer.parseInt(matcherGroup[16]);
				int minI = Integer.parseInt(minute);
				int trunI = minI - minI % tmpI;
				if (trunI < 10) {
					replaceStr += "0";
				}
				replaceStr += String.valueOf(trunI);
			} else
				replaceStr += minute;
		}
		if (matcherGroup[17] != null)
			replaceStr += matcherGroup[17];
		if (matcherGroup[18] != null) {
			String second = dateStr.substring(12, 14);
			replaceStr += second;
		}
		return replaceStr;
	}

	public static String getTimeReplace(String oldStr, Date date) {
		String lastStr = oldStr;
		String newStr = getTimeReplaceFirst(lastStr, date);
		while (newStr != null && !newStr.equals(lastStr)) {
			lastStr = newStr;
			newStr = getTimeReplaceFirst(newStr, date);
		}
		return newStr;
	}

	public static boolean regexTime(String oldStr) {
		Matcher matcher = TimePattern.matcher(oldStr);
		if (!matcher.find()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @param oldStr
	 * @param date
	 */
	private static String getTimeReplaceFirst(String oldStr, Date date) {
		{
			if (date == null)
				return oldStr;
			Matcher matcher = TimePattern.matcher(oldStr);
			if (!matcher.find())
				return oldStr;

			String replaceStr = getReplaceTimeStr(date, matcher);

			return matcher.replaceFirst(replaceStr);
		}
	}

	public static void main(String[] args) {
		String str = "dir.${time:yyyy.MM.dd}";
		Date date = new Date();
		System.out.println(date);
		System.out.println(ParasUtil.getTimeReplace(str, date));
	}

}

