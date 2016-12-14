package com.bcwcar.android.bencar.datasave;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用户订单数据保存
 */

//"ShopInfo": {
//    "ContactPhone": "073188292233",
//    "BrandName": "英菲尼迪",
//    "Score": 3,
//    "ShopName": "湖南兰天晟天英菲尼迪4S店",
//    "Address": "岳麓大道西3588号长张高速收费出口南侧",
//    "LogoUrl": "uploadFiles\/uploadImgs\/20160408\/49f1ed8177bb434ca3fc9522b77307f5.jpg",
//    "ProviderNames": "东风英菲尼迪,英菲尼迪(进口)",
//    "CommentCount": 1,
//    "ShopId": "cb4f4224-784a-4d66-803a-a47beb13b888",
//    "Distance": 3.7
//},
//"BeforeServiceEmpId": "",
//"BeforeServiceEmpIconUrl": "",
//"AfterServiceFlag": "1",
//"BeforeServiceFlag": "0",
//"BeforeServiceEmpName": "",
//"UserId": "c618b0a3-1c50-48c5-bc27-1da5717d5421",
//"AfterServiceEmpIconUrl": "uploadFiles\/uploadImgs\/20160618\/7a20496553f84a0188530127b7aac325.png",
//"ShopFlag": "1",
//"AfterServiceEmpName": "曹帅",
//"ShopId": "cb4f4224-784a-4d66-803a-a47beb13b888",
//"AfterServiceEmpId": "fc9271e9-15af-11e6-a37a-f079592f54ca"

//====存储售前和售后个人信息字段

//BeforeServiceData
//AfterServiceData
public class StoreToDataSave {

	public static void save(Context context,String key, String value) {
		SharedPreferences dingdan = context.getSharedPreferences("store", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public static void clear(Context context) {
		SharedPreferences dingdan = context.getSharedPreferences("store", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.clear();
		editor.commit();
	}
	public static String get(Context context,String key) {
		SharedPreferences dingdan = context.getSharedPreferences("store", 0);
		String string = dingdan.getString(key, "").toString();
		return string;
	}
	
	
	
	
	
}
