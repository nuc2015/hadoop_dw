package cn.edu.llxy.dw.dss.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 日期时间处理工具类 这里面的有些方法引用apache.commons.lang-2.3里的DateUtils类
 */
public class DateUtil extends Date {
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_FORMAT_SIMPLE = "yyyy-MM-dd";
	
	public static final String DATE_FORMAT_TINY = "yyyyMMdd";
	
	public static final String DATE_FORMAT_TINY_MON = "yyyyMM";
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 获取当前时间剩余所有日
	 * @param skip
	 * @return
	 */
	public static List<Date> getRemainDayOfMonth(int skip){
		Date now = new Date();
		Date start =  addDays(now, skip);
		Date monthEnd = getMonthEnd(now);
		
		List<Date> remainDates = new ArrayList();
		
		while (!start.after(monthEnd)) {
			remainDates.add(start);
			start = getNextDay(start);
        }
		
		return remainDates;
	}
	
	/**
	 * 获取当前时间剩余所有日
	 * @param skip
	 * @return
	 */
	public static List<String> getRemainMonthOfYear(int skip, String fmt){
		Date now = new Date();
		Date start =  addMonths(now, skip);
		List<String> remainDates = new ArrayList();
		Date last =  getCurrYearLast();
		
		while (!start.after(last)) {
			remainDates.add(getDate(start, fmt));
			start = getNextMonth(start);
        }
		
		return remainDates;
	}
	
	/**
	 * 获取当前时间剩余所有日
	 * @param skip
	 * @return
	 */
	public static List<String> getRemainMonthOfYear(String xstart, int skip, String fmt){
		Date now = DateUtil.parseDate(xstart, "yyyyMM");
		Date start =  addMonths(now, skip);
		List<String> remainDates = new ArrayList();
		Date last =  getCurrYearLast();
		
		while (!start.after(last)) {
			remainDates.add(getDate(start, fmt));
			start = getNextMonth(start);
        }
		
		return remainDates;
	}
	
	/**
	 * 获取当前时间
	 * @param skip
	 * @param fmt
	 * @return
	 */
	public static List<String> getRemainDayOfMonth(int skip, String fmt){
		Date now = new Date();
		Date start =  addDays(now, skip);
		Date monthEnd = getMonthEnd(now);
		
		List<String> remainDates = new ArrayList();
		
		while (!start.after(monthEnd)) {
			remainDates.add(getDate(start, fmt));
            start = getNextDay(start);
        }
		
		return remainDates;
	}

	private static Date getMonthStart(Date date) {
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         calendar.add(Calendar.MONTH, -1);
         int index = calendar.get(Calendar.DAY_OF_MONTH);
         calendar.add(Calendar.DATE, (1 - index));
         return calendar.getTime();
    }
	 
	private static Date getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (-index));
		return calendar.getTime();
	}
	
	public static Date getCurrYearLast(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }
	
	public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }
	 
	private static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
	
	private static Date getNextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}
	
	
	/**
	 * 构造函数
	 */
	public DateUtil() {
		super(getSystemCalendar().getTime().getTime());
	}
	
	public static String getMonday(String format){
	    GregorianCalendar time1 = new GregorianCalendar();
	    time1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    return getTimeString(time1, format);
	}
	
	public static String getMonday(String format, int weekOfDay){
	    GregorianCalendar time1 = new GregorianCalendar();
	    time1.set(Calendar.DAY_OF_WEEK, weekOfDay);
	    return getTimeString(time1, format);
	}

	/**
	 * 取当前系统的时间(时间类型为Calendar)。
	 *
	 * @return Calendar 当前系统时间
	 */
	public static Calendar getSystemCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 取当前系统的时间
	 *
	 * @return 时间Date
	 */
	public java.sql.Date getSqlDate() {
		return new java.sql.Date(this.getTime());
	}

	public static java.sql.Date getJavaSqlDate() {
		DateUtil dt = new DateUtil();
		return dt.getSqlDate();
	}

	/**
	 * 取当前系统的时间(时间类型为Date)。
	 *
	 * @return Date 当前系统时间
	 */
	public static Date getSystemDate() {
		return new Date();
	}

	/**
	 * 获得增加一定天数的日期（返回Date类型日期）,引用org.apache.commons.lang.time.DateUtils类addDays(
	 * Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 *
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            天数 - 天数可以为负数
	 * @return Date 返回增加天数后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addDays(Date date, int amount) {
		return DateUtils.addDays(date, amount);
	}

	/**
	 * 获得增加几个月的日期（返回Date类型日期）,引用org.apache.commons.lang.time.DateUtils类addMonths
	 * (Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 *
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            增加的月数 - 月数可以为负数
	 * @return Date 返回增加几个月后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addMonths(Date date, int amount) {
		GregorianCalendar cal = new GregorianCalendar();
		if (date == null) {
			cal.setTime(new DateUtil());
		} else {
			cal.setTime(date);
		}
		cal.add(Calendar.MONTH, amount);
		return cal.getTime();
	}

	/**
	 * 获得增加几个小时后的日期（返回Date类型日期）,引用org.apache.commons.lang.time.
	 * DateUtils类addHours(Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 *
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            增加的小时数 - 小时数可以为负数
	 * @return Date 返回增加几个小时后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addHours(Date date, int amount) {
		return DateUtils.addHours(date, amount);
	}

	/**
	 * 获得增加n毫秒后的日期（返回Date类型日期）,引用org.apache.commons.lang.time.
	 * DateUtils类addMilliseconds(Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 *
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            增加的毫秒数 - 毫秒数可以为负数
	 * @return Date 返回增加n毫秒后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addMilliseconds(Date date, int amount) {
		return DateUtils.addMilliseconds(date, amount);
	}

	/**
	 * 获得增加n秒后的日期（返回Date类型日期）,引用org.apache.commons.lang.time.
	 * DateUtils类addSeconds(Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 *
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            增加的秒数 - 秒数可以为负数
	 * @return Date 返回增加n秒后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addSeconds(Date date, int amount) {
		return DateUtils.addSeconds(date, amount);
	}

	/**
	 * 获得增加几个星期后的日期（返回Date类型日期）,引用org.apache.commons.lang.time.
	 * DateUtils类addWeeks(Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 *
	 * @param date
	 *            date 原日期 - 不能为null
	 * @param amount
	 *            增加的星期数 - 星期数可以为负数
	 * @return Date 返回增加几个星期后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addWeeks(Date date, int amount) {
		return DateUtils.addWeeks(date, amount);
	}

	/**
	 * 获得增加几年后的日期（返回Date类型日期）,引用org.apache.commons.lang.time.DateUtils类addYears(
	 * Date date, int amount)方法。 <br>
	 * 原始的日期没有改变 <br>
	 *
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            增加的年数 - 年数可以为负数
	 * @return Date 返回增加几年后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addYears(Date date, int amount) {
		return DateUtils.addYears(date, amount);
	}

	/**
	 * 获得增加n分钟后的日期（返回Date类型日期）,引用org.apache.commons.lang.time.
	 * DateUtils类addMinutes(Date date, int amount)方法。 <br>
	 *
	 * @param date
	 *            原日期 - 不能为null
	 * @param amount
	 *            增加的分钟数 - 分钟数可以为负数
	 * @return Date 返回增加n分钟后的日期
	 * @throws 如果Date为空时抛出IllegalArgumentException异常
	 */
	public static Date addMinutes(Date date, int amount) {
		return DateUtils.addMinutes(date, amount);
	}

	/**
	 * 取得当前时间n分钟前的时间。
	 *
	 * @param amount
	 *            分钟
	 * @return Date 返回n分钟前的时间
	 */
	public static Date addMinutes(int amount) {
		Date tsp = getSystemDate();
		long lngTime = tsp.getTime() - amount * 60 * 1000;
		return new Date(lngTime);

	}

	/**
	 * 取得两个日期的天数差（取得date1和date2日期之间的天数差）。
	 *
	 * @param date1
	 *            开始日期
	 * @param date2
	 *            结束日期
	 * @return long 返回日期2-日期1的天数差
	 */
	public static long getDaysDifference(Date date1, Date date2) {
		Date date1_day = DateUtil.truncate(date1, Calendar.DATE);
		Date date2_day = DateUtil.truncate(date2, Calendar.DATE);

		long lTime1 = date1_day.getTime();
		long lTime2 = date2_day.getTime();

		return (lTime2 - lTime1) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 取得两个日期的小时时差（取得date1日期和date2日期之间的小时差）。
	 *
	 * @param date1
	 *            开始日期
	 * @param date2
	 *            结束日期
	 * @return long 返回日期2-日期1的小时时差
	 */
	public static long getHoursDifference(Date date1, Date date2) {
		long lTime1 = date1.getTime();
		long lTime2 = date2.getTime();
		return (lTime2 - lTime1) / (1000 * 60 * 60);
	}

	/**
	 * 取得两个时间的分钟差。
	 *
	 * @param date1
	 *            开始日期
	 * @param date2
	 *            结束日期
	 * @return long 返回日期2-日期1的分钟差
	 */
	public static long getMinutesDifference(Date date1, Date date2) {
		long lTime1 = date1.getTime();
		long lTime2 = date2.getTime();
		return (lTime2 - lTime1) / (1000 * 60);
	}
	/**
	 * 取得两个时间的秒差。
	 *
	 * @param date1
	 *            开始日期
	 * @param date2
	 *            结束日期
	 * @return long 返回日期2-日期1的秒差
	 */
	public static long getSencondDifference(Date date1, Date date2) {
		long lTime1 = date1.getTime();
		long lTime2 = date2.getTime();
		return (lTime2 - lTime1) / (1000);
	}

	/**
	 * 取得给定日期当月的最后一天日期。
	 *
	 * @param dateS
	 *            当月日期
	 * @return Date 返回当月的最后一天
	 */
	public static Date getMonthLastDay(Date dateS) {
		GregorianCalendar gc = new GregorianCalendar();
		if (dateS == null)
			gc.setTime(new DateUtil());
		else
			gc.setTime(dateS);

		gc.add(Calendar.MONTH, 1);
		int days = gc.get(Calendar.DAY_OF_MONTH);
		gc.add(Calendar.DATE, -days);
		Date dateTemp = gc.getTime();
		return dateTemp;
	}

	/**
	 * @param date
	 * @return 该日期是当月倒数第几天
	 */
	public static int indexOfLast(Date date) {
		Date last_day = getMonthLastDay(date);
		return (int) getDaysDifference(date, last_day) + 1;
	}

	/**
	 * 按field（小时和月）类型截去日期(向前截取)，引用org.apache.commons.lang.time.
	 * DateUtils类truncate(Date date, int field)方法。 <br>
	 * 例：如果现在的时间是2002 3 28 13:45:01.231, 按小时截取将返回2002 3 28
	 * 13:00:00.000，按月截取则返回2002 3 1 00:00:00.000。 <br>
	 *
	 * @param date
	 *            与工作相关的日期
	 * @param field
	 *            截取类型
	 * @return Date 返回一个新的日期
	 * @throws 如果参数Date为空时抛出IllegalArgumentException异常
	 *             。
	 */
	public static Date truncate(Date date, int field) {
		return DateUtils.truncate(date, field);
	}

	/**
	 * 比较两个日期对象是否在同一天（不比较时间），引用org.apache.commons.lang.time.DateUtils类isSameDay(
	 * Date date1, Date date2)方法。 <br>
	 * 如：28 Mar 2002 13:45 和 28 Mar 2002 06:01比返回true. 28 Mar 2002 13:45和12 Mar
	 * 2002 13:45比返回false. <br>
	 *
	 * @param date1
	 *            日期1 - 不能为null
	 * @param date2
	 *            日期2 - 不能为null
	 * @return boolean 同一天返回true, 否则返回false.
	 * @throws 如果Date1和Date2有一个为空时抛出IllegalArgumentException异常
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		return DateUtils.isSameDay(date1, date2);
	}

	/**
	 * 判断传入日期是否在今天之前，在今天之前（包括今天）返回true，否则返回false。 <br>
	 *
	 * @param date
	 *            要的比较日期
	 * @return boolean 在今天之前（包括今天）返回true，否则返回false。
	 */
	public static boolean isBeforeToday(Date date) {
		boolean blnReturn = true;
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			Calendar c2 = Calendar.getInstance();
			c2.setTime(DateUtil.getGMT8Time());

			blnReturn = c.before(c2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return blnReturn;
	}

	/**
	 * 判断传入日期是否在今天之后，在今天之后(不包括今天)返回true，在今天之前(包括今天)则返回false。 <br>
	 *
	 * @param date
	 *            要的比较日期
	 * @return boolean 在今天之后(不包括今天)返回true，在今天之前(包括今天)则返回false
	 */
	public static boolean isAfterToday(Date date) {
		boolean blnReturn = true;
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);

			Calendar c2 = Calendar.getInstance();
			c2.setTime(DateUtil.getGMT8Time());

			blnReturn = c.after(c2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return blnReturn;
	}

	/**
	 * 日期格式化（对各种不同日期字符串格式化），按照parsePatterns参数给定的格式模式进行格式，引用org.apache.commons.
	 * lang.time.DateUtils类isSameDay(String str, String[] parsePatterns)方法。 <br>
	 *
	 * @param str
	 *            需格式的日期串 - 不能为null
	 * @param parsePatterns
	 *            日期格式模式(查看SimpleDateFormat) - 不能为null
	 * @return Date 返回格式后的日期
	 * @throws IllegalArgumentException
	 *             如果日期串为空时抛出IllegalArgumentException异常，
	 * @throws ParseException
	 *             如果parsePatterns数组中没有格式模式则抛出ParseException异常
	 */
	public static Date parseDate(String str, String[] parsePatterns) {
		try {
			if (str == null || parsePatterns == null) {
				throw new IllegalArgumentException("str and Patterns must not be null");
			}
			return DateUtils.parseDate(str, parsePatterns);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Unable to parse the date: " + str);
		}
	}
	
	/**
	 * 将字符串转换为日期对象
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String date, String pattern)
    {
		if(date == null || date.length() <= 0){
			return null;
		}
		
		if(date.length() < 8){
			date += "21";
		}
			
        SimpleDateFormat sdf = null;
        if(pattern == null || pattern.equals("")){
            sdf = new SimpleDateFormat(DATE_FORMAT);
        }
        else{
            sdf = new SimpleDateFormat(pattern);
        }
        try
        {
            return sdf.parse(date);
        }
        catch(ParseException e)
        {
        	e.printStackTrace();
            return null;
        }
    }

	public static int getDaysOfMonth(int year, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2: {
			if (year % 4 != 0)
				return 28;
			if (year % 400 == 0)
				return 29;
			if (year % 100 == 0)
				return 28;
			return 29;
		}
		default:
			return 0;
		}
	}

	/**
	 * Description: 获取GMT8时间
	 *
	 * @return 将当前时间转换为GMT8时区后的Date
	 */
	public static Date getGMT8Time() {
		Date gmt8 = null;
		try {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"), Locale.CHINESE);
			Calendar day = Calendar.getInstance();
			day.set(Calendar.YEAR, cal.get(Calendar.YEAR));
			day.set(Calendar.MONTH, cal.get(Calendar.MONTH));
			day.set(Calendar.DATE, cal.get(Calendar.DATE));
			day.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
			day.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
			day.set(Calendar.SECOND, cal.get(Calendar.SECOND));
			gmt8 = day.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			gmt8 = null;
		}
		return gmt8;
	}

	public static Date getMaxDate() {
		Calendar day = Calendar.getInstance();
		day.set(Calendar.YEAR, 2999);
		return truncate(day.getTime(), Calendar.YEAR);
	}
	
	public static String getDate(Date date, String pattern)
	{
		if(date == null)
			return "";
		if(pattern == null)
			pattern = DATE_FORMAT;
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	
	public static String getCurrentMonthDate(String dateFomat){

		GregorianCalendar now = new GregorianCalendar();
		return getTimeString(now, dateFomat);
	}
	
	public static String getTimeString(Calendar date, String format) {
		if (format == null || format.equals(""))
			format = "yyyyMMdd";
		String str = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			str = (String) sdf.format(date.getTime());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}
	
	
	public static String getPreDay(String dealDate,String format,int mons){
		
		int year = 0;
		int mon = 0;
		int day = 0;
		try{
			if(format.equals("yyyy-MM-dd")){
				year = Integer.parseInt(dealDate.substring(0,4));
				mon = Integer.parseInt(dealDate.substring(5,7));
				day = Integer.parseInt(dealDate.substring(8,9));
			}
			if(format.equals("yyyyMMdd")){
				year = Integer.parseInt(dealDate.substring(0,4));
				mon = Integer.parseInt(dealDate.substring(4,6));
				day = Integer.parseInt(dealDate.substring(6,8));
			}
		}catch(Exception ex){ex.printStackTrace();}
		mon --;
		SimpleDateFormat   df   =   new   SimpleDateFormat(format);   
		GregorianCalendar  gc   =   new   GregorianCalendar(year,mon,day);   
		gc.add(2, -mons);
		return df.format(gc.getTime());
	}
	
	public static String getPreMon(String dealDate,int mons){
		int year = Integer.parseInt(dealDate.substring(0,4));
		int mon = Integer.parseInt(dealDate.substring(4,6));
		mon --;
		int day = 1;
		SimpleDateFormat   df   =   new   SimpleDateFormat("yyyyMMdd");   
		GregorianCalendar  gc   =   new   GregorianCalendar(year,mon,day);   
		gc.add(2, -mons);
		return df.format(gc.getTime()).substring(0, 6);
	}
	
	public static Date getDate(String dealDate,int mons){
		int year = Integer.parseInt(dealDate.substring(0,4));
		int mon = Integer.parseInt(dealDate.substring(4,6));
		mon --;
		int day = 1;  
		GregorianCalendar  gc   =   new   GregorianCalendar(year,mon,day);   
		gc.add(2, -mons);
		return gc.getTime();
	}
	
	public static ArrayList getPreMons(int offset,int mons){
		ArrayList dateList = new ArrayList();
		Date date = new Date(); 
		SimpleDateFormat   format   =   new   SimpleDateFormat("yyyyMM");
		String dealDate = format.format(date);
		for(int i=0;i<mons;i++){
			dateList.add(getPreMon(dealDate,i+1));
		}
		return dateList;
	}
	
	public static String getYesterday(){
	    GregorianCalendar time1 = new GregorianCalendar();
	    time1.add(GregorianCalendar.DATE, -1);
	    return getTimeString(time1, "yyyy-MM-dd");
	}
	public static String getYesterday(String format){
	    GregorianCalendar time1 = new GregorianCalendar();
	    time1.add(GregorianCalendar.DATE, -1);
	    return getTimeString(time1, format);
	}
	public static String getToday(){
	    GregorianCalendar time1 = new GregorianCalendar();
	    return getTimeString(time1, "yyyy-MM-dd");
	}
	public static String getToday(String format){
	    GregorianCalendar time1 = new GregorianCalendar();
	    return getTimeString(time1, format);
	}
	public static String getLastDay(String format,int arg){
	    GregorianCalendar time2 = new GregorianCalendar();
	    time2.add(GregorianCalendar.DATE,arg);
	    return getTimeString(time2,format);    
	}
	public static String getBeforeYesterday(){
	    GregorianCalendar time1 = new GregorianCalendar();
	    time1.add(GregorianCalendar.DATE, -2);
	    return getTimeString(time1, "yyyy-MM-dd");
	}  
	public static String getBeforeYesterday(String format){
	    GregorianCalendar time1 = new GregorianCalendar();
	    time1.add(GregorianCalendar.DATE, -2);
	    return getTimeString(time1, format);
	} 
	public static String getLastWeek(String format){
	    GregorianCalendar time1 = new GregorianCalendar();
	    time1.add(GregorianCalendar.DATE, -8);
	    return getTimeString(time1, format);
	}
	public static String getLastMonth(){
	    GregorianCalendar time2 = new GregorianCalendar();
	    time2.add(GregorianCalendar.MONTH,-1);
	    return getTimeString(time2,"yyyyMM");    
	}
	public static String getLastMonth(String format){
	    GregorianCalendar time2 = new GregorianCalendar();
	    time2.add(GregorianCalendar.MONTH,-1);
	    return getTimeString(time2,format);    
	}
	public static String getLastMonth(String format,int arg){
	    GregorianCalendar time2 = new GregorianCalendar();
	    time2.add(GregorianCalendar.MONTH,arg);
	    return getTimeString(time2,format);    
	}
	public static String getLastMonth1(){
	    GregorianCalendar time1 = new GregorianCalendar();
	    time1.add(GregorianCalendar.DATE, -30);
	    return getTimeString(time1, "yyyy-MM-dd");     
	}

	public static String getBeginMonth(String str) {     
	    return str.substring(0,4) + "01" ;      
	}
	public static String getBeginDay(String str) {     
	    return str.substring(0,6) + "01" ;      
	}

	
	/*
	 * @得到两个月份之间的月份
	 * @ return yyyyMM
	 * */
	public static ArrayList getSpaceMons(String start_date,String end_date){
		ArrayList dateList = new ArrayList();   
		       SimpleDateFormat  sdf=new SimpleDateFormat("yyyyMM");      
		        GregorianCalendar gc1=new GregorianCalendar();
		        GregorianCalendar gc2=new GregorianCalendar();      
		        try {
					gc1.setTime(sdf.parse(start_date));
					gc2.setTime(sdf.parse(end_date));    
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}      
		        do{      
		            GregorianCalendar gc3=(GregorianCalendar)gc1.clone();      
		            dateList.add(getTimeString(gc3, "yyyyMM"));      
		            gc1.add(Calendar.MONTH, 1);                   
		         }while(!gc1.after(gc2));      
		return dateList;
	}
	/*
	 * @得到两个月份之间的天数
	 * @ return yyyyMM
	 * */
	public static ArrayList getSpaceDays(String start_date,String end_date){
		ArrayList dateList = new ArrayList();   
		       SimpleDateFormat  sdf=new SimpleDateFormat("yyyyMMdd");      
		        GregorianCalendar gc1=new GregorianCalendar();
		        GregorianCalendar gc2=new GregorianCalendar();      
		        try {
					gc1.setTime(sdf.parse(start_date));
					gc2.setTime(sdf.parse(end_date));    
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}      
		        do{      
		            GregorianCalendar gc3=(GregorianCalendar)gc1.clone();      
		            dateList.add(getTimeString(gc3, "yyyyMMdd"));      
		            gc1.add(Calendar.DAY_OF_MONTH, 1);                   
		         }while(!gc1.after(gc2));      
		return dateList;
	}
	/*
	 * @得到两个时间之间的秒数
	 * @ return yyyyMMdd 
	 * */
	public static long getSpaceSecond (String start_date,String end_date){
		ArrayList dateList = new ArrayList();   
		       SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      
		        GregorianCalendar gc1=new GregorianCalendar();
		        GregorianCalendar gc2=new GregorianCalendar();      
		        try {
					gc1.setTime(sdf.parse(start_date));
					gc2.setTime(sdf.parse(end_date));    
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}      
		             
		return gc2.getTime().getTime()-(gc1.getTime().getTime());
	}
	
	
	
	public static String getCurrentTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
	 /*
	 * @获取服务的时间yyyymmddhhmmss
	 */
		public static  String   getCurrentTime1(){
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			return dateFormat.format(new Date());	
		}
		
		 /*
		 * @获取服务的时间mmddhhmm
		 */
			public static  String   getCurrentTime2(){
				DateFormat dateFormat = new SimpleDateFormat("MMddHHmm");
				return dateFormat.format(new Date());	
			}
					
		
		/*
		 * @得到昨天
		 * @ return yyyyMMdd
		 * */
		public static String getYesterday_ymd(){
		    GregorianCalendar time1 = new GregorianCalendar();
		    time1.add(GregorianCalendar.DATE, -1);
		    return getTimeString(time1, "yyyyMMdd");
		}
		
		/*
		 * @得到今天
		 * @ return yyyyMMdd
		 * */
		public static String getToday_ymd(){
		    GregorianCalendar time1 = new GregorianCalendar();
		    return getTimeString(time1, "yyyyMMdd");
		}
		public static String getPreMon_ymd(String dealDate,int mons){
			int year = Integer.parseInt(dealDate.substring(0,4));
			int mon = Integer.parseInt(dealDate.substring(4,6));
			mon --;
			int day = Integer.parseInt(dealDate.substring(6,8));
			SimpleDateFormat   df   =   new   SimpleDateFormat("yyyyMMdd");   
			GregorianCalendar  gc   =   new   GregorianCalendar(year,mon,day);   
			gc.add(GregorianCalendar.MONTH, -mons);
			return df.format(gc.getTime()).substring(0, 8);
		}
		
		
		//得到当前的时间，为4Alog提供文件名
		public static String getCurrentHours(){
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH");
			return dateFormat.format(new Date());
		}
		
		public static String getMonth(int i,String format){
		    GregorianCalendar time2 = new GregorianCalendar();
		    time2.add(GregorianCalendar.MONTH,i);
		    return getTimeString(time2,format);    
		}
		
		  public static String getCurrentMinutes() {
			    DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
			    return dateFormat.format(new Date());
			  }
		  
		  public static String getCurrentMinutesLog() {
			    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
			    return dateFormat.format(new Date());
			  }
		  
		  public static String getCalendarString(int i,int type,String format){
			    GregorianCalendar time1 = new GregorianCalendar();
			    time1.add(type, -i);
			    return getTimeString(time1, format);
		  }
		  public static GregorianCalendar getCalendar(int i,int type){
			    GregorianCalendar time1 = new GregorianCalendar();
			    time1.add(type, -i);
			    return time1;
		  }
		  public static String[] getCalendars(GregorianCalendar time1,int end,int type,String format){
			    String[] times=new String[end];
			    for(int i=0;i<end;i++){
			    	time1.add(type, -1);
			    	times[i]=getTimeString(time1, format);
			    }
			    return times;
		  }
		  //比较日期,将传入日期与当天比,为真是早于当天,为假为晚于当天,当天也为真.
		public static boolean checkDate(String date_,String format){
			Date nowdate=new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
			Date d;
			boolean flag=false;
			try {
				d = sdf.parse(date_);
				flag = d.before(nowdate);
			} catch (ParseException e) {
			}
			return flag;
		}  
		

		/**
		 * 横线分割的标准时间
		 */
		private static final SimpleDateFormat DATE_STANDARD_FORMAT = new SimpleDateFormat("yyyy-MM-dd" );
		/**
		 * 日期格式模板格式化 如：2015-09-09
		 * @params date 要格式化日期Date对象
		 */
		public static synchronized String getStandardDate(Date date)
		{
			if(date == null){
				return "";
			}
			return DATE_STANDARD_FORMAT.format(date);
		}


		/**
		 * 横线分割的标准时间
		 */
		private static final SimpleDateFormat DATE_TIME_STANDARD_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
		/**
		 * 日期格式模板格式化 如：2015-09-09
		 * @params date 要格式化日期Date对象
		 */
		public static synchronized String getStandardDateTime(Date date)
		{
			if(date == null){
				return "";
			}
			return DATE_TIME_STANDARD_FORMAT.format(date);
		}
		
		
		public static void main(String[] args){
			String x = "safdafd   #abcefg..";
			
			x = x.replaceAll("#abc", "111");
			
			System.out.println(x);
		}
}
