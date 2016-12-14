package com.bcwcar.android.bencar.datasave;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 定位数据保存
 * jiyouzhizhen 机油指针
 * homejudge 首页判断：未登录状态进入首页：0
 * 登陆界面登陆后：1
 * rongyun:0：成功、1：不成功
 * PushChannelId:极光推送设备id
 * hotshop:热门shop是否加载了===1/else
 * hotbrand:热门品牌是否加载了====1/else
 * gg:广告图是否加载了====1/else
 * login:是否要登陆0：不要else
 * DiscoveryData:是否获取到数据：1有、0没有
 * */
public class OnlyOneDataSave{

	public static void save(Context context,String key, String value) {
		SharedPreferences dingdan = context.getSharedPreferences("onlyone", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public static void clear(Context context,String key, String value) {
		SharedPreferences dingdan = context.getSharedPreferences("onlyone", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.clear();
		editor.commit();
	}
	public static String get(Context context,String key) {
		SharedPreferences dingdan = context.getSharedPreferences("onlyone", 0);
		String string = dingdan.getString(key, "").toString();
		return string;
	}
	
	
	
	
	
}
