package com.bcwcar.android.bencar.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.text.TextUtils;

/**
 * Android Calendar的运用
 * 
 * @author Administrator
 * 
 */
public class CalendarUtil {

	private int weeks = 0;// 用来全局控制 上一周，本周，下一周的周数变化
	private int MaxDate; // 一月最大天数
	private int MaxYear; // 一年最大天数
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) {
		CalendarUtil tt = new CalendarUtil();
		System.out.println("获取本周日期:" + tt.getCurrentWeek(-1));
		System.out.println("获取本周日期:" + tt.getCurrentWeek(0));
		System.out.println("获取本周日期:" + tt.getCurrentWeek(1));

		System.out.println("获取四个周" + tt.getFourWeekStartEnd(0));
		/*
		 * System.out.println("获取当天日期:" + tt.getNowTime("yyyy-MM-dd"));
		 * System.out.println("获取本周一日期:" + tt.getMondayOFWeek());
		 * System.out.println("获取本周日的日期:" + tt.getCurrentWeekday());
		 * System.out.println("获取上周一日期:" + tt.getPreviousWeekday());
		 * System.out.println("获取上周日日期:" + tt.getPreviousWeekSunday());
		 * System.out.println("获取下周一日期:" + tt.getNextMonday());
		 * System.out.println("获取下周日日期:" + tt.getNextSunday());
		 * System.out.println("获得相应周的周六的日期:" + tt.getNowTime("yyyy-MM-dd"));
		 * System.out.println("获取本月第一天日期:" + tt.getFirstDayOfMonth());
		 * System.out.println("获取本月最后一天日期:" + tt.getDefaultDay());
		 * System.out.println("获取上月第一天日期:" + tt.getPreviousMonthFirst());
		 * System.out.println("获取上月最后一天的日期:" + tt.getPreviousMonthEnd());
		 * System.out.println("获取下月第一天日期:" + tt.getNextMonthFirst());
		 * System.out.println("获取下月最后一天日期:" + tt.getNextMonthEnd());
		 * System.out.println("获取本年的第一天日期:" + tt.getCurrentYearFirst());
		 * System.out.println("获取本年最后一天日期:" + tt.getCurrentYearEnd());
		 * System.out.println("获取去年的第一天日期:" + tt.getPreviousYearFirst());
		 * System.out.println("获取去年的最后一天日期:" + tt.getPreviousYearEnd());
		 * System.out.println("获取明年第一天日期:" + tt.getNextYearFirst());
		 * System.out.println("获取明年最后一天日期:" + tt.getNextYearEnd());
		 * System.out.println("获取本季度第一天:" + tt.getThisSeasonFirstTime(11));
		 * System.out.println("获取本季度最后一天:" + tt.getThisSeasonFinallyTime(11));
		 * System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:" +
		 * CalendarUtil.getTwoDay("2008-12-1", "2008-9-29"));
		 * System.out.println("获取当前月的第几周：" + tt.getWeekOfMonth());
		 * System.out.println("获取当前年份：" + tt.getYear());
		 * System.out.println("获取当前月份：" + tt.getMonth());
		 * System.out.println("获取今天在本年的第几天：" + tt.getDayOfYear());
		 * System.out.println("获得今天在本月的第几天(获得当前日)：" + tt.getDayOfMonth());
		 * System.out.println("获得今天在本周的第几天：" + tt.getDayOfWeek());
		 * System.out.println("获得半年后的日期：" +
		 * tt.convertDateToString(tt.getTimeYearNext())); }
		 */
	}

	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public static int getMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	public static int getDayOfYear() {
		return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
	}

	public static int getDayOfMonth() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	public static int getDayOfWeek() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}

	public static int getWeekOfMonth() {
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}

	public static Date getTimeYearNext() {
		Calendar.getInstance().add(Calendar.DAY_OF_YEAR, 183);
		return Calendar.getInstance().getTime();
	}

	public static String convertDateToString(Date dateTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(dateTime);
	}

	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = CalendarUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}
	
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public static String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}
  public static String getTomorrowTime(String dataformat) {
	  Date date=new Date();//取时间
	  System.out.println("Calendar");
	  Calendar calendar = new GregorianCalendar();
	  System.out.println("Calendar");
	  calendar.setTime(date);
	  System.out.println("Calendar");
	  calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
	  System.out.println("Calendar");
	  date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
	  System.out.println("Calendar");
	  SimpleDateFormat formatter = new SimpleDateFormat(dataformat);
	  System.out.println("Calendar");
	  String dateString = formatter.format(date);
      return dateString;
	
}
	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		// int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		// 因为按中国礼拜一作为第一天所以这里减1
		/*if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}*/
		if (dayOfWeek == 1) {
			return dayOfWeek - 7;
		} else {
			return 2 - dayOfWeek;
		}
	}

	public String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public String getDayByIndex(int index) {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + index);
		Date monday = currentDate.getTime();

		String preMonday = sdf.format(monday);
		return preMonday;
	}

	/**
	 * 获得四周，每周从开始到结束
	 * 
	 * @param current
	 * @return
	 */
	public String getFourWeekStartEnd(int fourWeekIndex) {
		// System.out.println("获取本周日期:" + tt.getCurrentWeekStartEnd(-1));
		// System.out.println("获取本周日期:" + tt.getCurrentWeekStartEnd(0));
		// System.out.println("获取本周日期:" + tt.getCurrentWeekStartEnd(1));
		CalendarUtil tt = new CalendarUtil();
		StringBuffer sb = new StringBuffer();
		sb.append(String.valueOf(fourWeekIndex));
		for (int i = 0; i < 4; i++) {
			sb.append(" "
					+ tt.getCurrentWeekStartEnd(fourWeekIndex * 4 + i - 1));
		}
		return sb.toString();
	}

	public String getCurrentWeekStartEnd(int startDay) {
		int weekIndex = startDay * 7;
		StringBuffer sb = new StringBuffer();

		String data = getDayByIndex(weekIndex);
		sb.append(data);

		String data2 = getDayByIndex(weekIndex + 6);
		sb.append("," + data2);
		return sb.toString();
	}

	public String getCurrentWeek(int startDay) {
		int weekIndex = startDay * 7;
		StringBuffer sb = new StringBuffer();
		sb.append(startDay);
		for (int i = weekIndex; i < weekIndex + 7; i++) {
			String data = getDayByIndex(i);
			sb.append(" " + data);
		}
		return sb.toString();
	}

	public String getSaturday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public String getPreviousWeekday() {
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public String getNextMonday() {
		weeks++;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	public String getNextSunday() {

		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	private int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	public String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	private int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	public String getCurrentYearFirst() {
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	// 获得本年最后一天的日期 *
	public String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	// 获得上年第一天的日期 *
	public String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	// 获得上年最后一天的日期
	public String getPreviousYearEnd() {
		weeks--;
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
				+ (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	public String getThisSeasonFirstTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days;
		return seasonDate;

	}

	public String getThisSeasonFinallyTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	private int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	public boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	public boolean isLeapYear2(int year) {
		return new GregorianCalendar().isLeapYear(year);
	}

	public static String getWeekOfYear() {
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR));
	}

	/**
	 * 通过开始时间和结束时间得到一个字符串 xx-xx xx:xx - xx-xx xx:xx
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	/*
	 * public static String getDateByStartAndEndDate(String startDate, String
	 * endDate) { if(TextUtils.isEmpty(startDate) ||
	 * TextUtils.isEmpty(endDate)){ return ""; } L.d(" "+startDate+" "+endDate);
	 * String[] startTimes = startDate.split(" "); String[] endTimes =
	 * endDate.split(" "); //年月日 String[] startYMD = startTimes[0].split("-");
	 * String[] endYMD = endTimes[0].split("-"); //时分秒 String[] startHMS = null;
	 * String[] endHMS =null; if(startTimes.length == 2){ startHMS =
	 * startTimes[1].split(":"); } if(endTimes.length == 2){ endHMS =
	 * startTimes[1].split(":"); }
	 * 
	 * //如果只有年月日 if(startHMS != null && endHMS !=null){ return
	 * startYMD[1]+"-"+startYMD[2]+" "+startHMS[0]+":"+startHMS[1]+" - "
	 * +endYMD[1]+"-"+endYMD[2]+" "+endHMS[0]+":"+endHMS[1]; }else{ //如果有年月日时分秒
	 * return startYMD[1]+"-"+startYMD[2]+" - " +endYMD[1]+"-"+endYMD[2]; } }
	 */
	/**
	 * 通过对开始时间的处理
	 * 
	 * @param startDate
	 * @return
	 */
	public static String getDateByStart(String startDate) {
		if (TextUtils.isEmpty(startDate)) {
			return "";
		}
		// 年
		if (startDate.length() == 4) {
			startDate = startDate + "-01-01 00:00";
		}
		// 年-月
		if (startDate.length() == 7) {
			startDate = startDate + "-01 00:00";
		}
		// 年-月-日
		if (startDate.length() == 10) {
			startDate = startDate + " 00:00";
		}

		return startDate;
	}
	/**
	 * 通过对结束时间的处理
	 * 
	 * @param startDate
	 * @return
	 */
	public static String getDateByEnd(String endDate) {
		if (TextUtils.isEmpty(endDate)) {
			return "";
		}
		// 年
		if (endDate.length() == 4) {
			endDate = endDate + "-01-01 01:00";
		}
		// 年-月
		if (endDate.length() == 7) {
			endDate = endDate + "-01 01:00";
		}
		// 年-月-日
		if (endDate.length() == 10) {
			endDate = endDate + " 01:00";
		}

		return endDate;
	}
	/**
	 * 通过开始时间和结束时间得到一个字符串 xx-xx xx:xx - xx-xx xx:xx
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	/*public static String getDateByStartAndEndDate(String startDate,
			String endDate) {
		if (TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)) {
			return "";
		}
		// 年
		if (startDate.length() == 4) {
			startDate = startDate + "01-01 00:00";
		}
		if (endDate.length() == 4) {
			endDate = endDate + "01-01 01:00";
		}
		// 年-月
		if (startDate.length() == 7) {
			startDate = startDate + "-01 00:00";
		}
		if (endDate.length() == 7) {
			endDate = endDate + "-01 01:00";
		}
		// 年-月-日
		if (startDate.length() == 10) {
			startDate = startDate + " 00:00";
		}
		if (endDate.length() == 10) {
			endDate = endDate + " 01:00";
		}

		return startDate + " - " + endDate;
	}
*/
	// 判断连个时间是否为同一天
	public static boolean isOneDay(String[] oneDay, String[] twoDay) {
		if (oneDay == null || twoDay == null || oneDay.length < 3
				|| twoDay.length < 3) {
			return false;
		}
		if (Integer.valueOf(oneDay[0]).equals(Integer.valueOf(twoDay[0]))
				&& Integer.valueOf(oneDay[1])
						.equals(Integer.valueOf(twoDay[1]))
				&& Integer.valueOf(oneDay[2])
						.equals(Integer.valueOf(twoDay[2]))) {
			return true;
		}
		return false;
	}

	public static String getWeekInYear(String week) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		int weekInYear = 0;
		try {
			date = format.parse(week);
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setTime(date);
			weekInYear = calendar.get(Calendar.WEEK_OF_YEAR);
			Date now = format.parse(getNowTime("yyyy-MM-dd"));
			calendar.setTime(now);
			return week.substring(0, 4) + "-" + weekInYear;
		} catch (ParseException e) {
			return week;
		}

	}
	public static int getIntWeekInYear(String week) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		int weekInYear = 0;
		try {
			date = format.parse(week);
			Calendar calendar = Calendar.getInstance();
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setTime(date);
			weekInYear = calendar.get(Calendar.WEEK_OF_YEAR);
			Date now = format.parse(getNowTime("yyyy-MM-dd"));
			calendar.setTime(now);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return weekInYear;
	}
}
