package com.bcwcar.android.bencar.util;

import java.util.Map;

import android.util.Log;

public class StringUtil {
	public static boolean isEmpty(String str) {
		if(str == null || str.length() == 0) {
			return true;
		}
		
		return false;
	}
	/**
	 * 遍历map数据
	 * 
	 * */
	public static void  map_key_value(Map<String, String> paramsKeyValue,String baseUrl){
		Log.e("map遍历", "baseUrl= "+baseUrl);
		for (String key : paramsKeyValue.keySet()) {
			Log.e("map遍历", "key= "+ key + " and value= " + paramsKeyValue.get(key));
		}
	}
	/**
	 * String转double转int
	 * 
	 * */
	public static int  string_to_int(String string){
		int intgeo = 0;
		  if (string.contains(".")) {
			  String str = string.substring(0, string.indexOf("."));
			   intgeo = Integer.parseInt(str);
			   return intgeo;
		}
		  intgeo = Integer.parseInt(string);
		  return intgeo;
	}
	
	/**
	 * 业务相关
	 * 将“2014年12月”替换成“2014-12”
	 */
	public static String yearMonthTo(String yearMonthString) {
		return yearMonthString.replace("年", "-").replace("月", "");
	}
	
	/**
	 * 业务相关
	 * 将“2014-12”替换成“2014年12月”
	 */
	public static String yearMonthFrom(String str) {
		return str.replace("-", "年") + "月";
	}
}