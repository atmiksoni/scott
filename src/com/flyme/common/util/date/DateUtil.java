package com.flyme.common.util.date;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.common.util.map.CriterionMap;

public class DateUtil {
	public static DateTimeFormatter yyyyMd = DateTimeFormatter.ofPattern("yyyy-M-d");
	public static DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static DateTimeFormatter yyyyMM = DateTimeFormatter.ofPattern("yyyy-MM");
	public static DateTimeFormatter dd = DateTimeFormatter.ofPattern("dd");
	public static DateTimeFormatter MMdd = DateTimeFormatter.ofPattern("MM-dd");
	public static DateTimeFormatter yyyy年MM月dd日 = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
	public static DateTimeFormatter nyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
	public static DateTimeFormatter nyyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	public static DateTimeFormatter yyyyMMddHHmmssSSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	public static DateTimeFormatter yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static DateTimeFormatter yyyyMMddHHmmss2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	public static DateTimeFormatter HHmm = DateTimeFormatter.ofPattern("HH:mm");
	public static DateTimeFormatter HH = DateTimeFormatter.ofPattern("HH");

	/**
	 * 日期转字符串 yyyy-MM-dd
	 */
	public static LocalDate parseDate(String date) {
		LocalDate localDate = LocalDate.parse(date.replaceAll("\\.", "-").trim(), yyyyMd);
		return localDate;
	}

	/**
	 * 日期转字符串 yyyy-MM-dd
	 */
	public static LocalDate parseDate(String date, DateTimeFormatter format) {
		TemporalAccessor temporalAccessor = format.parse(date);
		return LocalDate.from(temporalAccessor);
	}

	/**
	 * 日期转字符串 yyyy-MM-dd
	 */
	public static LocalDateTime parseDateTime(String date, DateTimeFormatter format) {
		String[] d = date.split(":");
		if (d.length == 2) {
			date += ":00";
		}
		TemporalAccessor temporalAccessor = format.parse(date);
		return LocalDateTime.from(temporalAccessor);
	}

	/**
	 * 字符串转日期 yyyy-MM-dd
	 */
	public static LocalDate toDate(String date) {
		if (date.length() < 5) {
			date = date + "-01-01";
		}
		if (date.length() < 8) {
			date = date + "-01";
		}
		TemporalAccessor temporalAccessor = yyyyMd.parse(date);

		return LocalDate.from(temporalAccessor);
	}

	/**
	 * 字符串(yyyy-MM-dd HH:mm:ss型)转日期 yyyy-MM-dd
	 */
	public static LocalDate toDateByDateTime(String date) {
		String[] d = date.split(":");
		if (d.length == 2) {
			date += ":00";
		}
		TemporalAccessor temporalAccessor = yyyyMMddHHmmss.parse(date);
		return LocalDate.from(temporalAccessor);
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 */
	public static String dataToStr(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 */
	public static String dateToStr(Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 字符串(yyyy-MM-dd HH:mm:ss型)LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(String date) {
		String[] d = date.split(":");
		if (d.length == 2) {
			date += ":00";
		}
		TemporalAccessor temporalAccessor = yyyyMMddHHmmss.parse(date);
		return LocalDateTime.from(temporalAccessor);
	}

	/**
	 * 字符串(yyyy-MM-dd HH:mm:ss型)LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime2(String date) {

		TemporalAccessor temporalAccessor = yyyyMMddHHmmss2.parse(date);
		return LocalDateTime.from(temporalAccessor);
	}

	/**
	 * 字符串(yyyy-MM-dd HH:mm:ss型)LocalDateTime
	 */
	public static LocalDateTime getLocalDateTime(String date) {
		TemporalAccessor temporalAccessor = nyyyyMMddHHmmss.parse(date);
		return LocalDateTime.from(temporalAccessor);
	}

	/**
	 * 字符串(yyyy-MM-dd)LocalDate
	 */
	public static LocalDate getLocalDate(String date) {
		TemporalAccessor temporalAccessor = yyyyMMdd.parse(date);
		return LocalDate.from(temporalAccessor);
	}

	public static LocalTime toTime(String time) {
		TemporalAccessor temporalAccessor = HHmm.parse(time);
		return LocalTime.from(temporalAccessor);
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	
	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException 
	 */
	public static Date strToDate(String strDate) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date strtodate = formatter.parse(strDate);
		return strtodate;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 日期转字符串 yyyy-MM-dd
	 */
	public static String format(LocalDate date) {
		return date.format(yyyyMMdd);
	}

	/**
	 * 格式化日期
	 */
	public static String format(LocalDate date, DateTimeFormatter formatter) {
		return date.format(formatter);
	}

	/**
	 * 格式化时间
	 */
	public static String format(LocalDateTime datetime, DateTimeFormatter formatter) {
		return datetime.format(formatter);
	}

	/**
	 * 日期转字符串 yyyy-MM-dd
	 */
	public static String format2(LocalDateTime date) {
		return date.format(yyyyMMddHHmmss);
	}

	/**
	 * 获取当前月yyyy-MM
	 */
	public static String getMonth() {
		LocalDate localDate = LocalDate.now();
		return localDate.format(yyyyMM);
	}

	/**
	 * 获取当前日期yyyy-MM-dd
	 */
	public static String getDate() {
		LocalDate localDate = LocalDate.now();
		return localDate.format(yyyyMMdd);
	}

	/**
	 * 格式化日期
	 */
	public static String getDate(String data, DateTimeFormatter formatter) {
		LocalDate localDate = toDate(data);
		return localDate.format(formatter);
	}

	/**
	 * 格式化日期
	 */
	public static String getDateTime(String data, DateTimeFormatter formatter) {
		String str = "";
		if (ObjectUtils.isNotEmpty(data)) {
			LocalDateTime localDateTime = toLocalDateTime(data);
			str = localDateTime.format(formatter);
		}
		return str;
	}

	/**
	 * 获取当前日期yyyy-MM-dd hh:mm:ss
	 */
	public static String getDateTime() {
		LocalTime localtime = LocalTime.now();
		LocalDateTime localDateTime = localtime.atDate(getLocalDate());
		return localDateTime.format(yyyyMMddHHmmss);
	}

	public static String getCurrentTimeMillis() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取当前日期yyyy年MM月dd日
	 */
	public static String getChDate() {
		LocalDate localDate = getLocalDate();
		return localDate.format(yyyy年MM月dd日);
	}

	/**
	 * 获取当前日期yyyyMMddhhmmss
	 */
	public static String getLangDate() {
		LocalTime localtime = LocalTime.now();
		LocalDateTime localDateTime = localtime.atDate(getLocalDate());
		return localDateTime.format(nyyyyMMddHHmmss);
	}

	/**
	 * 获取SAAS当前日期YYYYYMMDDHHmmssfff
	 */
	public static String getSAASDate() {
		LocalTime localtime = LocalTime.now();
		LocalDateTime localDateTime = localtime.atDate(getLocalDate());
		return localDateTime.format(yyyyMMddHHmmssSSS);
	}

	/**
	 * 获取当前日期yyyyMMdd
	 */
	public static String getSimDate() {
		LocalTime localtime = LocalTime.now();
		LocalDateTime localDateTime = localtime.atDate(getLocalDate());
		return localDateTime.format(nyyyMMdd);
	}

	/**
	 * 获取时间
	 */
	public static LocalDateTime getLocalDateTime() {
		LocalTime localtime = LocalTime.now();
		LocalDateTime localDateTime = localtime.atDate(getLocalDate());
		return localDateTime;
	}

	/**
	 * 获取当前日期yyyy年MM月dd日
	 */
	public static LocalDate getLocalDate() {
		LocalDate localDate = LocalDate.now();
		return localDate;
	}

	/**
	 * 当前日期往后推算
	 */
	public static LocalDate nextDays(long add) {
		LocalDate today = getLocalDate();
		return today.plusDays(add);
	}

	/**
	 * 获取当前日期yyyy-MM-dd hh:mm:ss
	 */
	public static String getLocalDateTime(long minusHours, Boolean plus) {
		LocalTime localtime = LocalTime.now();
		LocalDateTime localDateTime = localtime.atDate(getLocalDate());
		if (plus) {
			localDateTime = localDateTime.plusHours(minusHours);
		} else {
			localDateTime = localDateTime.minusHours(minusHours);
		}
		return localDateTime.format(yyyyMMddHHmmss);
	}

	/**
	 * 获取当前日期yyyy-MM-dd hh:mm:ss
	 */
	public static String minutes(long minutes, Boolean plus) {
		LocalTime localtime = LocalTime.now();
		LocalDateTime localDateTime = localtime.atDate(getLocalDate());
		if (plus) {
			localDateTime = localDateTime.plusMinutes(minutes);
		} else {
			localDateTime = localDateTime.minusMinutes(minutes);
		}
		return localDateTime.format(yyyyMMddHHmmss);
	}

	/**
	 * 一天的结束
	 */
	public static String getStrEndOfDay(String date) {
		return dateToStr(getEndOfDay(date));
	}

	public static Date getEndOfDay(String date) {
		LocalDate localDate = LocalDate.parse(date);
		return getEndOfDay(localDate);
	}

	public static Date getEndOfDay(TemporalAccessor date) {
		LocalDate localDate = LocalDate.from(date);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1L).minusNanos(1L).toInstant());
	}

	// 一天的开始
	public static String getStrBeginOfDay(String date) {
		return dateToStr(getStartOfDay(date));
	}

	public static Date getStartOfDay(String date) {
		LocalDate localDate = LocalDate.parse(date);
		return getStartOfDay(localDate);
	}

	public static Date getStartOfDay(TemporalAccessor date) {
		LocalDate localDate = LocalDate.from(date);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 当前日期往前推算月
	 */
	public static String preStrMonths(String date, long pre) {
		String nextDay = "";
		if (ObjectUtils.isNotEmpty(date)) {
			LocalDate today = toDate(date);
			nextDay = today.minusMonths(pre).toString();
		}
		return nextDay;
	}

	/**
	 * 当前日期往前推算年
	 */
	public static String preStrYears(String date, long pre) {
		String nextDay = "";
		if (ObjectUtils.isNotEmpty(date)) {
			LocalDate today = toDate(date);
			nextDay = today.minusYears(pre).toString();
		}
		return nextDay;
	}

	/**
	 * 当前日期往前推算
	 */
	public static LocalDate preDays(long min) {
		LocalDate today = getLocalDate();
		return today.minusDays(min);
	}

	/**
	 * 当前日期往后推算n年
	 */
	public static LocalDate nextYear(long add) {
		LocalDate today = getLocalDate();
		return today.plusYears(add);
	}

	/**
	 * 当前日期往后推算n年
	 */
	public static LocalDate preYear(long add) {
		LocalDate today = getLocalDate();
		return today.minusYears(add);
	}

	public static LocalTime getNowTime() {
		return LocalTime.now();
	}

	/**
	 * 当前日期往后推算n周
	 */
	public static LocalDate nextWeek(long add) {
		LocalDate today = getLocalDate();
		return today.plusWeeks(add);

	}

	/**
	 * 当前日期往前推算n周
	 */
	public static LocalDate preWeek(long add) {
		LocalDate today = getLocalDate();
		return today.minusWeeks(add);
	}

	/**
	 * 日期往后推算n个月
	 */
	public static String nextStrMonths(String date, long add) {
		LocalDate today = toDate(date);
		String test = getLastDayOfMonthOfStr(today.toString());
		if (test.equals(date)) {
			return getLastDayOfMonthOfStr(today.plusMonths(add).toString());
		} else {
			return today.plusMonths(add).toString();
		}
	}

	/**
	 * 日期往后推算n个月
	 */
	public static String nextMonth(String date, long n) {
		LocalDate today = toDate(date);
		return today.plusMonths(n).toString();
	}

	/**
	 * 根据当前日期往后推算一个月
	 */
	public static String nextMonth(String s) {
		String d = "";
		LocalDate date = toDate(s);
		Boolean isLeapYear = isLeapYear(date);
		if (isLeapYear) {
			d = nextStrDays(nextMonth(format(date), 1), -1);
		} else {
			String monthDay = getMonthDay(s);
			if (ObjectUtils.isNotEmpty(monthDay)) {
				if (monthDay.equals("01-29") || monthDay.equals("01-30")) {
					d = nextMonth(format(date), 1);
				} else {
					d = nextStrDays(nextMonth(format(date), 1), -1);
				}
			}
		}
		return d;
	}

	/**
	 * 当前日期往后推算
	 */
	public static String nextStrDays(String date, long add) {
		String nextDay = "";
		if (ObjectUtils.isNotEmpty(date)) {
			LocalDate today = toDate(date);
			nextDay = today.plusDays(add).toString();
		}
		return nextDay;
	}
	
	/**
	 * 当前日期往后推算
	 * @throws ParseException 
	 */
	public static String nextStrMinute(String date, long add) throws ParseException {
		Date temp=strToDate(date);
		long curren = temp.getTime();
		curren += add * 60 * 1000;
		temp=new Date(curren);
		return dateToStr(temp);
	}

	/* 获取月日 */
	public static String getMonthDay(String date) {
		String monthDay = "";
		if (ObjectUtils.isNotEmpty(date)) {
			monthDay = date.substring(date.indexOf("-") + 1);
		}
		return monthDay;
	}

	/**
	 * 3年2月1天后
	 */
	public static LocalDate custom(long add) {
		LocalDate today = getLocalDate();
		return today.plus(Period.of(3, 2, 1));
	}

	/**
	 * 获取月份
	 */
	public static int getMonth(LocalDate localDate) {
		return localDate.getMonthValue();
	}

	/**
	 * 获取月份
	 */
	public static int getMonth(String date) {
		int month = 0;
		if (ObjectUtils.isNotEmpty(date)) {
			LocalDate localDate = toDate(date);
			month = localDate.getMonthValue();
		}
		return month;
	}

	/**
	 * 获取月份
	 */
	public static int getNowMonth() {
		int month = 0;
		LocalDate localDate = getLocalDate();
		month = localDate.getMonthValue();
		return month;
	}

	/**
	 * 获取月份
	 */
	public static int getYear(LocalDate localDate) {
		return localDate.getYear();
	}

	/**
	 * 获取月份
	 */
	public static String getYear(String date) {
		int year = toDate(date).getYear();
		return ObjectUtils.getString(year, "0");
	}

	/**
	 * 获取年
	 */
	public static int getYear() {
		return getLocalDate().getYear();
	}

	/**
	 * 获取年
	 */
	public static String getStrYear() {
		return ObjectUtils.getString(getLocalDate().getYear(), "");
	}

	/**
	 * 获取月份
	 */
	public static int getDayofMonth(LocalDate localDate) {
		return localDate.getDayOfMonth();
	}

	/**
	 * 获取月份
	 */
	public static int getDayofMonth(String localDate) {
		return toDate(localDate).getDayOfMonth();
	}

	/**
	 * 得到某年某月的第一天
	 */
	public static LocalDate getFirstDayOfMonth(int y, int m) {
		Year year = Year.of(y);
		Month month = Month.of(m);
		YearMonth yearMonth = year.atMonth(month);
		yearMonth.lengthOfMonth();
		LocalDate localDate = yearMonth.atDay(1);
		return localDate.with(TemporalAdjusters.firstDayOfMonth());
	}

	/**
	 * 得到某年某月的最后一天
	 */
	public static LocalDate getLastDayOfMonth(int y, int m) {
		Year year = Year.of(y);
		Month month = Month.of(m);
		YearMonth yearMonth = year.atMonth(month);
		LocalDate localDate = yearMonth.atDay(1);
		return localDate.with(TemporalAdjusters.lastDayOfMonth());
	}

	/**
	 * 得到某年某月的最后一天
	 */
	public static LocalDate getLastDayOfMonth(String y, String m) {
		Year year = Year.of(ObjectUtils.getInteger(y));
		Month month = Month.of(ObjectUtils.getInteger(m));
		YearMonth yearMonth = year.atMonth(month);
		LocalDate localDate = yearMonth.atDay(1);
		return localDate.with(TemporalAdjusters.lastDayOfMonth());
	}

	/**
	 * 得到某年某月的最后一天
	 */
	public static String getStrLastDayOfMonth(String y, String m) {
		Year year = Year.of(ObjectUtils.getInteger(y));
		Month month = Month.of(ObjectUtils.getInteger(m));
		YearMonth yearMonth = year.atMonth(month);
		LocalDate localDate = yearMonth.atDay(1);
		return localDate.with(TemporalAdjusters.lastDayOfMonth()).toString();
	}

	/**
	 * 判断是否是闰年
	 */
	public static Boolean isLeapYear(LocalDate localDate) {
		return localDate.isLeapYear();
	}

	/**
	 * 计算两个日期之间的间隔天数
	 */
	public static long daysBetweenDateTime(String s, String e) {

		LocalDate beginDate = LocalDate.parse(s, yyyyMMddHHmmss);
		LocalDate endDate = LocalDate.parse(e, yyyyMMddHHmmss);
		return ChronoUnit.DAYS.between(beginDate, endDate);
	}

	/**
	 * 获得与现在相差时间
	 */
	public static String getDistanceTime(String begin, String end) {
		long rstime = -1L;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		StringBuffer sb = new StringBuffer();
		if (ObjectUtils.isNotEmpty(begin)) {
			LocalDateTime endTime = DateUtil.toLocalDateTime(end);
			LocalDateTime beginTime = DateUtil.toLocalDateTime(begin);
			rstime = ChronoUnit.SECONDS.between(beginTime, endTime) * 1000;
			day = rstime / (24 * 60 * 60 * 1000);
			hour = (rstime / (60 * 60 * 1000) - day * 24);
			min = ((rstime / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (rstime / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			if (day > 0) {
				sb.append(day + "天");
			}
			if (hour > 0) {
				sb.append(hour + "时");
			}
			if (min > 0) {
				sb.append(min + "分");
			}
			if (sec > 0) {
				sb.append(sec + "秒");
			}
		}
		return sb.toString();
	}

	/**
	 * 计算两个日期之间的间隔小时
	 */
	public static long HoursBetweenDateTime(String s, String e) {
		LocalDateTime beginDate = toLocalDateTime(s);
		LocalDateTime endDate = toLocalDateTime(e);
		return ChronoUnit.HOURS.between(beginDate, endDate);
	}

	/**
	 * 根据相差的小时换算成天
	 */
	public static long daysBetween(String s, String e) {
		LocalDateTime beginDate = toLocalDateTime(s);
		LocalDateTime endDate = toLocalDateTime(e);
		// Long hour = new Long(ChronoUnit.HOURS.between(beginDate, endDate));
		Long minnuts = new Long(ChronoUnit.MINUTES.between(beginDate, endDate));
		Double b = minnuts.doubleValue();
		Double a = b / 60 / 24;
		Double day = Math.ceil(a);
		return day.intValue();
	}

	/**
	 * 计算两个日期之间的间隔分
	 */
	public static long MinutesBetweenDateTime(String s, String e) {
		LocalDateTime beginDate = toLocalDateTime(s);
		LocalDateTime endDate = toLocalDateTime(e);
		Duration duration=Duration.between(beginDate, endDate);
		return duration.toMinutes();
	}

	/**
	 * 计算两个精确时间之间的间隔天数
	 */
	public static long daysBetween(String a, String b, DateTimeFormatter format) {
		long n = 0;
		if (ObjectUtils.allNotEmpty(a, b)) {
			if (a.indexOf(":") != -1) {
				LocalDateTime a1 = parseDateTime(a, format);
				LocalDateTime b1 = parseDateTime(b, format);
				n = ChronoUnit.DAYS.between(a1, b1);
			} else {
				LocalDate a1 = parseDate(a, format);
				LocalDate b1 = parseDate(b, format);
				n = ChronoUnit.DAYS.between(a1, b1);
			}
		}
		if (n <= 0) {
			n = 1;
		}
		return n;
	}

	/**
	 * 根据日期字符串判断当月第几周
	 */
	public static int getWeek(String str) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 第几周
		int week = calendar.get(Calendar.WEEK_OF_MONTH);
		return week;
	}

	/**
	 * 计算两个日期之间的间隔天数
	 */
	public static long daysBetween(LocalDate s, LocalDate e) {
		return ChronoUnit.DAYS.between(s, e);
	}

	/**
	 * 计算两个日期之间的间隔月数
	 */
	public static long monthBetween(String s, String e) {
		LocalDate beginDate = getFirstDayOfMonth(s);
		LocalDate endDate = getFirstDayOfMonth(e);
		return ChronoUnit.MONTHS.between(beginDate, endDate);
	}

	/**
	 * 计算两个日期之间的间隔月数
	 */
	public static long monthBetweenDatetime(String s, String e) {
		LocalDate beginDate = getFirstDayOfMonth4datetime(s);
		LocalDate endDate = getFirstDayOfMonth4datetime(e);
		return ChronoUnit.MONTHS.between(beginDate, endDate);
	}

	/**
	 * 计算某年某月有多少天
	 */
	public static int getLength(int y, int m) {
		Year year = Year.of(y);
		Month month = Month.of(m);
		YearMonth yearMonth = year.atMonth(month);
		return yearMonth.lengthOfMonth();
	}

	/**
	 * 获取某年第一天日期
	 */
	public static LocalDate getCurrYearFirst(int year) {
		Year y = Year.of(year);
		YearMonth yearMonth = y.atMonth(1);
		LocalDate localDate = yearMonth.atDay(1);
		return localDate;
	}

	/**
	 * 获取某年最后一天日期
	 */
	public static LocalDate getCurrYearLast(int year) {
		Year y = Year.of(year);
		YearMonth yearMonth = y.atMonth(12);
		LocalDate localDate = yearMonth.atDay(31);
		return localDate;
	}

	/**
	 * 判断a日期是否b结束日期
	 */
	public static Boolean lt(LocalDate a, LocalDate b) {
		return a.isBefore(b);
	}

	/**
	 * a小于b
	 */
	public static Boolean lt(LocalDateTime a, LocalDateTime b) {
		return a.isBefore(b);
	}

	/**
	 * a小于b
	 */
	public static Boolean lt(String a, String b) {
		return lt(parseDate(a), parseDate(b));
	}

	/**
	 * a大于b
	 */
	public static Boolean gt(LocalDate a, LocalDate b) {
		return a.isAfter(b);
	}

	/**
	 * a大于b
	 */
	public static Boolean gt(LocalDateTime a, LocalDateTime b) {
		return a.isAfter(b);
	}

	/**
	 * a大于b
	 */
	public static Boolean gt(String a, String b) {
		return gt(parseDate(a), parseDate(b));
	}

	/**
	 * a大于等于b
	 */
	public static Boolean ge(LocalDateTime a, LocalDateTime b) {
		Boolean tag = false;
		if (a.isAfter(b) || a.isEqual(b)) {
			tag = true;
		}
		return tag;
	}

	public static Boolean ge(String a, String b, DateTimeFormatter format) {
		Boolean tag = false;
		if (ObjectUtils.allNotEmpty(a, b)) {
			if (a.indexOf(":") != -1) {
				LocalDateTime a1 = parseDateTime(a, format);
				LocalDateTime b1 = parseDateTime(b, format);
				if (a1.isAfter(b1) || a1.isEqual(b1)) {
					tag = true;
				}
			} else {
				LocalDate a1 = parseDate(a, format);
				LocalDate b1 = parseDate(b, format);
				if (a1.isAfter(b1) || a1.isEqual(b1)) {
					tag = true;
				}
			}
		}
		return tag;
	}

	public static Boolean le(String a, String b, DateTimeFormatter format) {
		Boolean tag = false;
		if (ObjectUtils.allNotEmpty(a, b)) {
			if (a.indexOf(":") != -1) {
				LocalDateTime a1 = parseDateTime(a, format);
				LocalDateTime b1 = parseDateTime(b, format);
				if (a1.isBefore(b1) || a1.isEqual(b1)) {
					tag = true;
				}
			} else {
				LocalDate a1 = parseDate(a, format);
				LocalDate b1 = parseDate(b, format);
				if (a1.isBefore(b1) || a1.isEqual(b1)) {
					tag = true;
				}
			}
		}
		return tag;
	}

	public static Boolean gt(String a, String b, DateTimeFormatter format) {
		Boolean tag = false;
		if (ObjectUtils.allNotEmpty(a, b)) {
			if (a.indexOf(":") != -1) {
				LocalDateTime a1 = parseDateTime(a, format);
				LocalDateTime b1 = parseDateTime(b, format);
				if (a1.isAfter(b1)) {
					tag = true;
				}
			} else {
				LocalDate a1 = parseDate(a, format);
				LocalDate b1 = parseDate(b, format);
				if (a1.isAfter(b1)) {
					tag = true;
				}
			}
		}
		return tag;
	}

	public static Boolean lt(String a, String b, DateTimeFormatter format) {
		Boolean tag = false;
		if (ObjectUtils.allNotEmpty(a, b)) {
			if (isDataTime(a)) {
				LocalDateTime a1 = parseDateTime(a, format);
				LocalDateTime b1 = parseDateTime(b, format);
				if (a1.isBefore(b1)) {
					tag = true;
				}
			} else {
				LocalDate a1 = parseDate(a, format);
				LocalDate b1 = parseDate(b, format);
				if (a1.isBefore(b1)) {
					tag = true;
				}
			}
		}
		return tag;
	}

	/** 当前日期往后推算n天 */
	public static String plusDays(String a, long n) {
		String str = "";
		if (ObjectUtils.isEmpty(a)) {
			return str;
		}
		if (isDataTime(a)) {
			LocalDateTime date = toLocalDateTime(a);
			date = date.plusDays(n);
			str = format(date, yyyyMMddHHmmss);
		} else {
			LocalDate localDate = getLocalDate(a);
			localDate = localDate.plusDays(n);
			str = format(localDate, yyyyMMdd);

		}
		return str;
	}

	/** 当前日期往前推算n天 */
	public static String minusDays(String a, long n) {
		String str = "";
		if (ObjectUtils.isEmpty(a)) {
			return str;
		}
		if (isDataTime(a)) {
			LocalDateTime date = toLocalDateTime(a);
			date = date.minusDays(n);
			str = format(date, yyyyMMddHHmmss);
		} else {
			LocalDate localDate = getLocalDate(a);
			localDate = localDate.minusDays(n);

		}
		return str;
	}

	/** 判断当前日期是否是datetime */
	public static Boolean isDataTime(String date) {
		Boolean tag = false;
		if (date.indexOf(":") != -1) {
			tag = true;
		}
		return tag;
	}

	/**
	 * a《data《b
	 */
	public static Boolean isBetween(String date, String a, String b) {
		Boolean tag = false;
		if (gt(date, a) && lt(date, b)) {
			tag = true;
		}
		return tag;
	}

	/**
	 * a《data《b
	 */
	public static Boolean isBetween2(String date, String a, String b) {
		Boolean tag = false;
		if (ge(date, a, yyyyMMdd) && le(date, b, yyyyMMdd)) {
			tag = true;
		}
		return tag;
	}
	/**
	 * a《data《b
	 * HHmm类型
	 * @throws ParseException 
	 */ 
	public static Boolean isBetween3(String date, String a, String b) throws ParseException {
		Boolean tag = false;

	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		 long now = sdf.parse(date).getTime();
	        long start = sdf.parse(a).getTime();
	        long end = sdf.parse(b).getTime();
	        if (b.equals("00:00")) {
	            b = "24:00";
	        }
	        if (end < start) {
	            if (now >= end && now < start) {
	                return false;
	            } else {
	                return true;
	            }
	        } 
	        else {
	            if (now >= start && now < end) {
	                return true;
	            } else {
	                return false;
	            }
	        }
	}

	/**
	 * 获取本月第一天
	 */
	public static LocalDate getFirstDayOfMonth() {
		LocalDate localDate = getLocalDate();
		return localDate.with(TemporalAdjusters.firstDayOfMonth());
	}

	/**
	 * 获取本月第一天
	 */
	public static String getStrFirstDayOfMonth() {
		return format(getFirstDayOfMonth());
	}

	/**
	 * 获取本月最后一天
	 */
	public static String getStrLastDayOfMonth() {
		return format(getLastDayOfMonth());
	}

	/**
	 * 获取某日期改月份第一天
	 */
	public static LocalDate getFirstDayOfMonth(String date) {
		LocalDate localDate;
		if (ObjectUtils.isNotEmpty(date)) {
			localDate = toDate(date);
		} else {
			localDate = getLocalDate();
		}
		return localDate.with(TemporalAdjusters.firstDayOfMonth());
	}

	/**
	 * 获取某日期改月份第一天,参数为详细时间
	 */
	public static LocalDate getFirstDayOfMonth4datetime(String date) {
		LocalDate localDate;
		if (ObjectUtils.isNotEmpty(date)) {
			localDate = toDateByDateTime(date);
		} else {
			localDate = getLocalDate();
		}
		return localDate.with(TemporalAdjusters.firstDayOfMonth());
	}

	/**
	 * 获取某日期改月份第一天
	 */
	public static LocalDate getFirstDayOfMonth(LocalDate date) {
		return date.with(TemporalAdjusters.firstDayOfMonth());
	}

	/**
	 * 获取本年第一天
	 */
	public static LocalDate getFirstDayOfYear() {
		LocalDate localDate = getLocalDate();
		return localDate.with(TemporalAdjusters.firstDayOfYear());
	}

	/**
	 * 获取本月最后一天
	 */
	public static LocalDate getLastDayOfMonth() {
		LocalDate localDate = getLocalDate();
		return localDate.with(TemporalAdjusters.lastDayOfMonth());
	}

	/**
	 * 获取某日期的最后一天
	 */
	public static LocalDate getLastDayOfMonth(String date) {
		LocalDate localDate = toDate(date);
		return localDate.with(TemporalAdjusters.lastDayOfMonth());
	}

	/**
	 * 获取某日期的最后一天
	 */
	public static String getLastDayOfMonthOfStr(String date) {
		return getLastDayOfMonth(date).toString();
	}

	/**
	 * 获取某日期改月份的第一天
	 */
	public static String getFirstDayOfMonthOfStr(String date) {
		return getFirstDayOfMonth(date).toString();
	}

	/**
	 * 获取本周第一天
	 */
	public static LocalDate getFirstDayOfWeek() {
		return getLocalDate().with(DayOfWeek.of(1));
	}

	/**
	 * 获取本周最后一天
	 */
	public static LocalDate getLastDayOfWeek() {
		return getLocalDate().with(DayOfWeek.of(7));
	}

	/**
	 * 获取当前日期所在周
	 */
	public static String getDayOfWeek(String date) {
		if (ObjectUtils.isEmpty(date)) {
			return "";
		}
		int dw = toLocalDateTime(date).getDayOfWeek().getValue();// 星期几
		return "周" + ConvertUtils.numToWeek(dw);
	}

	/**
	 * 获取当前日期所在周
	 */
	public static Integer getDayAtWeek(String date) {
		int dw = toDate(date).getDayOfWeek().getValue();
		return dw;
	}

	/**
	 * 获取该日期的天数
	 */
	public static int lengthOfMonth(String date) {
		LocalDate localDate = toDate(date);
		return localDate.lengthOfMonth();
	}

	/**
	 * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
	 */
	public static String getQuarter(String date) {
		String season = "";
		int month = getMonth(parseDate(date));
		switch (month) {
		case 1:
		case 2:
		case 3:
			season = "第一季度";
			break;
		case 4:
		case 5:
		case 6:
			season = "第二季度";
			break;
		case 7:
		case 8:
		case 9:
			season = "第三季度";
			break;
		case 10:
		case 11:
		case 12:
			season = "第四季度";
			break;
		default:
			break;
		}
		return season;
	}

	/**
	 * 根据类型返回cq查询条件
	 */
	public static CriterionMap getMapDate(CriterionMap map, String type) {
		if (ObjectUtils.isNotEmpty(type)) {
			String beginDate = "";
			String today = getDate();// 当天
			String first_week = getFirstDayOfWeek().toString();// 本周第一天
			String first_month = format(getFirstDayOfMonth());// 当月第一天
			String first_year = format(getFirstDayOfYear());// 当年第一天
			String tomorrow = format(nextDays(1l));// 获取明日
			if (!type.equals("Customo")) {
				switch (type) {
				case "Day":
					beginDate = today;
					break;
				case "Week":
					beginDate = first_week;
					break;
				case "Month":
					beginDate = first_month;
					break;
				case "Year":
					beginDate = first_year;
					break;
				}
				map.put("beginDate", beginDate);
				map.put("endDate", tomorrow);
			}
		}
		return map;
	}

	/**
	 * 获得两个日期之间所有天，yyyy-MM-dd格式
	 * 
	 * @param dBegin
	 * @param dEnd
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getDaysBetween(String Beginstr, String Endstr) {
		List<String> lDate = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dBegin = null;
		Date dEnd = null;
		try {
			dBegin = sdf.parse(Beginstr);
			dEnd = sdf.parse(Endstr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		lDate.add(Beginstr);
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(dEnd);
		while (dEnd.after(calBegin.getTime())) {
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(sdf.format(calBegin.getTime()));
		}
		return lDate;
	}

	/**
	 * 获得两个日期之间所有月，输入yyyy-MM-dd格式，输出yyyy-MM格式
	 * 
	 */
	public static List<String> getMonthBetween(String minDate, String maxDate) {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		try {
			min.setTime(sdf.parse(minDate));
			max.setTime(sdf.parse(maxDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	/**
	 * 判断当前日期是否是上半年
	 */
	public static boolean isUpYear() {
		boolean tag = false;
		int month = ObjectUtils.getInteger(getNowMonth());
		if (month <= 6) {
			tag = true;
		}
		return tag;
	}

	/**
	 * 根据上传时间获取月最后一天
	 */
	public static String getLastDayOfMonthByUpload(String time) {
		String[] times = null;
		if (time.indexOf(".") > -1) {
			times = time.split("\\.");
		}
		if (time.indexOf("-") > -1) {
			times = time.split("-");
		}
		return getStrLastDayOfMonth(times[0], times[1]);
	}

	/**
	 * 获得与现在相差秒数，在现在时间前的为负数
	 */
	public static long getSecondsBetweenNow(String date) {
		long rstime = -1L;
		if (ObjectUtils.isNotEmpty(date)) {
			LocalDateTime nowtime = getLocalDateTime();
			LocalDateTime mtime = DateUtil.toLocalDateTime(date);
			rstime = ChronoUnit.SECONDS.between(nowtime, mtime);
		}
		return rstime;
	}

	/**
	 * 显示时间，如果与当前时间差别小于一天，则自动用**秒(分，小时)前
	 */
	public static String showTime(String date) {
		String r = "";
		if (ObjectUtils.isNotEmpty(date)) {
			LocalDateTime nowtime = getLocalDateTime();
			LocalDateTime mtime = DateUtil.toLocalDateTime(date);
			long result = ChronoUnit.SECONDS.between(mtime, nowtime);
			if (result < 60) {// 一分钟内
				if (result < 5) {
					r = "刚刚";
				} else {
					r = result + "秒前";
				}
			} else if (result >= 60 && result < 3600) {// 一小时内
				long seconds = result / 60;
				r = seconds + "分钟前";
			} else if (result >= 3600 && result < 86400) {// 一天内
				long seconds = result / 3600;
				r = seconds + "小时前";
			} else if (result >= 86400 && result < 1702967) {// 三十天内
				long seconds = result / 86400;
				r = seconds + "天前";
			} else {// 日期格式
				r = DateUtil.toDateByDateTime(date).toString();
			}
		}
		return r;
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	public static void main(String[] args) throws ParseException {
		BigDecimal overTime=new BigDecimal(60);
		BigDecimal result=overTime.multiply(new BigDecimal(10.00)).divide(new BigDecimal(60));//1倍工资
		System.out.println(result);
		System.out.println(nextStrMinute("2018-09-15 09:50:27",60));
		 String startDate= DateUtil.getDate()+" "+"09:33"+":00";
		 String endDate= DateUtil.getDate()+" "+"13:33"+":00";
		System.out.println(MinutesBetweenDateTime("2017-12-29 09:33:20","2017-12-29 13:23:26"));
		System.out.println(MinutesBetweenDateTime(startDate,endDate));
		/*System.out.println(MinutesBetweenDateTime("2017-12-29 14:33:20","2017-12-29 17:33:26"));
		
		String date = "2015.12.31";
		System.out.println("当前日期：" + parseDate(date.replaceAll("\\.", "-")));
		System.out.println("当前月份：" + getMonth(getLocalDate()));
		System.out.println("当前年度：" + getYear(getLocalDate()));
		System.out.println("当前天：" + getDayofMonth(getLocalDate()));
		System.out.println("某年某月第一天：" + getLastDayOfMonth(2014, 12));
		System.out.println("某年某月最后一天：" + getLastDayOfMonth(2014, 12));

		System.out.println("计算某月的天数：" + getLength(2014, 2));
		System.out.println("计算2个日期相差的月数：" + monthBetween("2014-03-31", "2014-02-01"));
		System.out.println("某年第一天：" + getCurrYearFirst(2011));
		System.out.println("某年最后一天：" + getCurrYearLast(2011));
		System.out.println("判断是第几季度：" + getQuarter("2011-12-01"));

		System.out.println("获取本周第一天" + getFirstDayOfWeek());
		System.out.println("获取本周最后一天" + getLastDayOfWeek());
		System.out.println("获取本年第一天" + getFirstDayOfYear());
		System.out.println("某个日期往后推" + nextStrMonths("2016-03-29", 12).toString());
		System.out.println("当前时间" + getDate("2016-12-01", dd));
		System.out.println("当前时间" + getDateTime("2016-12-01 12:00:25", MMdd));
		System.out.println("日期推算" + plusDays("2016-12-01 12:00:25", 1));
		System.out.println("一天的結束" + getStrEndOfDay(getDate()));
		System.out.println("一天的開始" + getStrBeginOfDay(getDate()));
		System.out.println("2个日期相隔小时" + daysBetween("2016-12-01 04:30:00", "2016-12-02 04:31:00"));
		System.out.println("今天是" + DateUtil.getDateTime("2016-12-01 12:00:25", DateUtil.MMdd) + "," + DateUtil.getDayOfWeek("2016-12-01 12:00:25") + " " + DateUtil.getDateTime("2016-12-01 12:00:25", DateUtil.HHmm));
		String day = DateUtil.format(getLocalDate("2016-12-01"), DateUtil.dd);
		System.out.println(ObjectUtils.getInteger(day));*/
	}
}
