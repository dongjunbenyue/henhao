package com.bcwcar.android.bencar.util;

import java.util.Calendar;

public class DateUtil {
	/**
	 * 根据出生日期计算年龄
	 * @param birthTimeMillis
	 * @return
	 */
	public static int calAgeFromBirthday(long birthTimeMillis) {
		System.out.println(birthTimeMillis);
		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTimeInMillis(birthTimeMillis);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth 
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth 
				age--;
			}
		}
		
//		return age;
		return age < 0 ? 0 : age;
	}
}