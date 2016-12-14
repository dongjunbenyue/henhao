package com.bcwcar.android.bencar.datasave;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 定位数据保存
 * CityName:名称
 * CityId:城市id
 * "Latitude
 * Longitude：经纬度
 * IsFirstLogin:引导页面是否首次进入判断false、true
 * */
public class LocationDataSave {

	public static void save(Context context,String key, String value) {
		SharedPreferences dingdan = context.getSharedPreferences("location", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public static void clear(Context context,String key, String value) {
		SharedPreferences dingdan = context.getSharedPreferences("location", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.clear();
		editor.commit();
	}
	public static String get(Context context,String key) {
		SharedPreferences dingdan = context.getSharedPreferences("location", 0);
		String string = dingdan.getString(key, "").toString();
		return string;
	}
	
	
	
	
	
}
