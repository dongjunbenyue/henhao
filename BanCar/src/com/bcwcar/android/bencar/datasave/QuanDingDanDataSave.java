package com.bcwcar.android.bencar.datasave;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 券订单数据保存
 */

//发现页面
//ShopId	N	网点ID
//Address	N	地址
//ShopName	N	网点名称
//Latitude	N	地址纬度
//Longitude	N	地址经度
//Distance	N	距离，单位是公里
//Score	N	网点评分
//		
//参数名	是否可空	参数说明
//AccName	N	服务名称
//AccId	N	服务ID
//ProjectId	N	项目ID
//ApplyNum	N	销售量
//TotalPrice	N	原价
//ActualPrice	N	促销价
//IconUrl	N	图片路径


public class QuanDingDanDataSave {

	public static void save(Context context,String key, String value) {
		SharedPreferences dingdan = context.getSharedPreferences("quandingdan", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public static void clear(Context context) {
		SharedPreferences dingdan = context.getSharedPreferences("quandingdan", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.clear();
		editor.commit();
	}
	public static String get(Context context,String key) {
		SharedPreferences dingdan = context.getSharedPreferences("quandingdan", 0);
		String string = dingdan.getString(key, "").toString();
		return string;
	}
	
	
	
	
	
}
