package com.haikuo.utiltools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateTool {

	private static final DateTool format = new DateTool();
	public static String ISO_DATE_PARTTEN = "yyyy-MM-dd";
	public static String ISO_DATE_TIME_PARTTEN = "yyyy-MM-dd HH:mm:ss";
	
	public static DateTool getInstance() {
		return format;
	}

	private DateTool() {

	}

	/**
	 * 计算一个日期到当前时间的年限
	 * 
	 * @param dt
	 * @return
	 */
	public static int getYearDt(Date dt) {
		long time = new Date().getTime() - dt.getTime();
		return (int) (time / (1000 * 60 * 60 * 24 * 365));
	}

	/**
	 * 计算一个日期到当前时间月份限
	 * 
	 * @param dt
	 * @return
	 */
	public static int getMothDt(Date dt) {
		long time = new Date().getTime() - dt.getTime();
		return (int) (time / (1000 * 60 * 60 * 24 * 30));
	}

	/**
	 * 计算一个日期到当前时间天数限
	 * 
	 * @param dt
	 * @return
	 */
	public static int getDayDt(Date dt) {
		long time = new Date().getTime() - dt.getTime();
		return (int) (time / (1000 * 60 * 60 * 24));
	}

	/**
	 * 格式化日期为字符串
	 * 
	 * @param date
	 *            目标日期
	 * @param partten
	 *            模式
	 * @return
	 */
	public String format(Date date, String... partten) {
		if (date == null) {
			return "";
		}
		return format.format(date, partten);
	}

	/**
	 * 获取标准格式的日期 例如:2015-03-17
	 * @param date
	 * @return
	 */
	public String formatISODate(Date date){
		return format(date, ISO_DATE_PARTTEN);
	}
	
	/**
	 * 获取标准格式的日期时间 例如:2015-03-17 15:24:32
	 * @param date
	 * @return
	 */
	public String formatISODatetime(Date date){
		return format(date, ISO_DATE_TIME_PARTTEN);
	}
	
	/**
	 * 将格式化的日期字符串解析为Date类型
	 * 
	 * @param source
	 *            格式化的日期字符串
	 * @param partten
	 *            模式
	 * @return
	 * @throws Exception
	 */
	public Date parse(String source, String... partten) throws Exception {
		if (source == null || source.equalsIgnoreCase("")) {
			return null;
		}
		return format.parse(source, partten);
	}

	
	public int reduceDays(Date date) {
		return reduceDays(new Date(), date);
	}

	/**
	 * 计算日历天数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public int reduceDay(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		int begin = c1.get(Calendar.DAY_OF_YEAR);
		int year1 = c1.get(Calendar.YEAR);
		c1.setTime(d2);
		int end = c1.get(Calendar.DAY_OF_YEAR);
		int year2 = c1.get(Calendar.YEAR);
		int res = (year1 - year2) * 365 + begin - end;
		return res;
	}

	public int reduceDay(Date date) {
		return reduceDay(new Date(), date);
	}

	public int reduceDays(Date d1, Date d2) {
		return (int) Math
				.abs(((d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000)));
	}

	public long compareNow(Date dt) {
		return dt.getTime() - new Date().getTime();
	}

	/**
	 * 获取给定时间到当前时间的月份
	 * */
	public int getMonthApart(Date newDate, Date oldDate) {
		if (newDate == null || oldDate == null) {
			return 0;
		}
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(newDate);
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(oldDate);
		int apart = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12
				+ cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
		return apart;
	}

	/**
	 * 获取给定时间到当前时间的月份
	 * */
	public int getMonthApart(Date oldDate) {
		if (oldDate == null) {
			return 0;
		}
		Date newDate = new Date();
		Calendar cal1 = new GregorianCalendar();
		cal1.setTime(newDate);
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(oldDate);
		int apart = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12
				+ cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
		return apart;
		
	}

	/**
	 * 判断一个时间是否在某个时间区间中
	 * 
	 * @param date
	 *            要比较时间
	 * @param begin
	 *            开始时间 如： 09:00
	 * @param end
	 *            结束时间如 ： 23:30
	 * @param pattern
	 *            开始时间和结束时间对应的格式 "HH:mm"
	 * @return
	 * @throws ParseException
	 */
	public boolean isInTimeZone(Date date, String begin, String end,
			String pattern) throws ParseException {
		if (date == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String srcStr = sdf.format(date);
		long srcTime = sdf.parse(srcStr).getTime();
		long endTime = sdf.parse(end).getTime();
		long beginTime = sdf.parse(begin).getTime();
		if (endTime - srcTime >= 0 && srcTime - beginTime >= 0) {
			return true;
		}

		return false;

	}

	/**
	 * 获取当前月份的第一天0点
	 * 
	 * @param date
	 *            当前时间
	 * @return 当前时间对应月份的第一天
	 * @throws Exception
	 */
	public Date getFirstDayOfMonth(Date date) throws Exception {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String timeStr = year + "-" + month + "-01" + " 00:00:00";
		Date time = format.parse(timeStr, "yyyy-MM-dd HH:mm:ss");
		return time;
	}

	/**
	 * 获取当前月份的最后一天23点59:59
	 * 
	 * @param date
	 *            当前时间
	 * @return 当前时间对应月份的第一天
	 * @throws Exception
	 */
	public Date getLastDayOfMonth(Date date) throws Exception {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		String timeStr = year + "-" + month + "-" + day + " 23:59:59";
		Date time = format.parse(timeStr, "yyyy-MM-dd HH:mm:ss");
		return time;
	}

	/**
	 * 获取当前年份的第一天0点
	 * 
	 * @param date
	 *            当前时间
	 * @return 当前时间对应月份的第一天
	 * @throws Exception
	 */
	public Date getFirstDayOfYear(Date date) throws Exception {
		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		String timeStr = year + "-01" + "-01" + " 00:00:00";
		Date time = format.parse(timeStr, "yyyy-MM-dd HH:mm:ss");
		return time;
	}

	/**
	 * 获取当前年份的最后一天的23:59:59点
	 * 
	 * @param date
	 *            当前时间
	 * @return 当前时间对应月份的第一天
	 * @throws Exception
	 */
	public Date getLastDayOfYear(Date date) throws Exception {
		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		String timeStr = year + "-12" + "-31" + " 23:59:59";
		Date time = format.parse(timeStr, "yyyy-MM-dd HH:mm:ss");
		return time;
	}

	public int getYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	public int getMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}

	public int getDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static Date addMonths(Date now, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	public Date getDateStart(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return (Date) cal.getTime().clone();
	}

	public Date getTodayBegin() {
		return getDateStart(new Date());
	}

	public Date getDateEnd(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return (Date) cal.getTime().clone();
	}

	public Date getTodayEnd() {
		return getDateStart(new Date());
	}

	public Date getWeekBegin(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar currentDate = new GregorianCalendar();
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.setTime(date);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return (Date) currentDate.getTime().clone();
	}

	public Date getWeekBegin() {
		return getWeekBegin(new Date());
	}

	public Date getWeekEnd(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar currentDate = new GregorianCalendar();
		currentDate.setTime(date);
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return (Date) currentDate.getTime().clone();
	}

	public Date getWeekEnd() {
		return getWeekEnd(new Date());
	}

	public Date getMonthBegin(Date date) {
		Calendar currentDate = new GregorianCalendar();
		currentDate.setTime(date);
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.DATE, 1);
		return (Date) currentDate.getTime().clone();
	}

	public Date getMonthBegin() {
		return getMonthBegin(new Date());
	}

	public Date getMonthEnd(Date date) {
		Calendar currentDate = new GregorianCalendar();
		currentDate.setTime(date);
		currentDate.add(Calendar.MONTH, 1);
		currentDate.set(Calendar.DATE, 1);
		currentDate.add(Calendar.DATE, -1);

		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);

		return (Date) currentDate.getTime().clone();
	}

	public Date getMonthEnd() {
		return getMonthEnd(new Date());
	}

	/**
	 * 判断当前日期是否比下个月最后一天早
	 */
	public boolean monthBefore(Date date) throws Exception {
		Date now = new Date();
		Date oldDate = getDateEnd(date);
		Date compare = addMonths(now, 1);
		compare = getLastDayOfMonth(compare);
		if (oldDate.before(compare)) {
			return true;
		}
		return false;
	}
	
	public double reduceHour(Date begin ,Date end)throws Exception{
		long times = end.getTime() - begin.getTime();
		double hour = times / (1000 * 60* 60 * 1.00);
		return hour;
	}
}
