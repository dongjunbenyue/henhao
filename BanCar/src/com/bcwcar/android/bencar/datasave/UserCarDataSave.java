package com.bcwcar.android.bencar.datasave;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用户车辆数据保存
 * 冲突：
 * 1：BrandLogo(*)	N	Logo文件------IconUrl	Y	Logo文件名
 * 2:CarMiles(*)-----Miles
 */
//BrandId	N	品牌ID
//First	N	首字母
//Brand	N	品牌名称
//IconUrl	Y	Logo文件名
//SeriesId	N	车系ID
//ProviderId	 N	厂商ID
//SeriesName	N	车系名称
//CarId	N	车型ID
//CarName	N	车型名称
//CarYear 	N	年款
//BuyDate:购车时间
//Miles  公里数
//PlateNumber 车牌号
//PlateNumber001 发动机号（暂定）
//PlateNumber002 车架号（暂定）
//CityCar  车辆归属地（暂定）
//===============默认车型数据=================

//"CarId": "375baa8a-905b-11e5-b805-f079592f54ca",
//"BrandLogo": "yingfei.png",
//"UserId": "172de8f4-74e9-45ff-94b7-0e0d18beced0",
//"CarMiles": 5000,
//"DefaultFlag": "0",
//"CarName": "英菲尼迪EX 2013款 EX25 两驱优雅版",
//"UserCarId": "cfe7eadd-6f30-479e-8fed-eede6e24ec27",
//"BuyDate": "2000-01"
//BuyDate001:2000年01月
public class UserCarDataSave {

	public static void save(Context context,String key, String value) {
		SharedPreferences dingdan = context.getSharedPreferences("usercar", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public static void clear(Context context) {
		SharedPreferences dingdan = context.getSharedPreferences("usercar", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.clear();
		editor.commit();
	}
	public static String get(Context context,String key) {
		SharedPreferences dingdan = context.getSharedPreferences("usercar", 0);
		String string = dingdan.getString(key, "").toString();
		return string;
	}
	
	
	
	
	
}
