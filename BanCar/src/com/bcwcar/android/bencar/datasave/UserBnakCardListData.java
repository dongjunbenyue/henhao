package com.bcwcar.android.bencar.datasave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.StringUtil;

import android.content.Context;
import android.content.SharedPreferences;

public class UserBnakCardListData {
//	UserBankId	N	用户银行ID
//	IdCardNo	N	身份证号
//	BankNo	N	银行账号
//	BankId	N	银行ID
//	AccountName	N	开户姓名
//	DefaultFlag	N	默认标志,0-否，1-是
//	RegBank	N	开户行
//	BankName	N	银行名称
//	BankIcon	N	银行图标
	/**
	 * 将用户钱包数据进行保存及清除
	 * 
	 */
	public static void save(Context context, String Data) {
		SharedPreferences sp = context.getSharedPreferences(BizDefineAll.USER_BANK_DATA, Context.MODE_PRIVATE);
		sp.edit().putString(BizDefineAll.USER_BANK_DATA_ALL, Data).commit();
	}
	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(BizDefineAll.USER_BANK_DATA, Context.MODE_PRIVATE);
		sp.edit().putString(BizDefineAll.USER_BANK_DATA_ALL, null).commit();
	}
	/**
	 * 判断数据是否为空
	 * */
	public static boolean isnull(Context context) {
		SharedPreferences sp = context.getSharedPreferences(BizDefineAll.USER_BANK_DATA, Context.MODE_PRIVATE);
		if (StringUtil.isEmpty(sp.getString(BizDefineAll.USER_BANK_DATA_ALL, "").toString())) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 获取Data下的字符串
	 */
	public static void getData(Context context,List<Map<String, String>> mListViewData) {
		SharedPreferences sp = context.getSharedPreferences(BizDefineAll.USER_BANK_DATA, Context.MODE_PRIVATE);
		try {
			JSONObject	jsonObject = new JSONObject(sp.getString(BizDefineAll.USER_BANK_DATA_ALL, ""));
			JSONArray array=jsonObject.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA);
			for (int i = 0; i < array.length(); i++) {
				JSONObject object=array.getJSONObject(i);
				mListViewData.add(CollectionUtil.jsonObjectToMap_String(object));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
/***
 * 获取默认的银行卡
 * **/
	public static Map<String, String> getDefaultBankCard(Context context){
		Map<String, String> map=new HashMap<String, String>();
		SharedPreferences sp = context.getSharedPreferences(BizDefineAll.USER_BANK_DATA, Context.MODE_PRIVATE);
		try {
			JSONObject	jsonObject = new JSONObject(sp.getString(BizDefineAll.USER_BANK_DATA_ALL, ""));
			JSONArray array=jsonObject.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA);
				JSONObject object=array.getJSONObject(0);
				map=CollectionUtil.jsonObjectToMap_String(object);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	
	
	
	
	
}
