package com.bcwcar.android.bencar.datasave;

import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.util.StringUtil;

import android.content.Context;
import android.content.SharedPreferences;

public class UserWalletListData {
	// 参数类型 参数名 是否可空 参数说明
	// String ResponseDescription Y 操作返回信息
	// String ResponseCode N 操作成功标识，0：成功，1：失败
	// String TotalCount N 总记录数
	// String TotalPage N 总页数
	// Object Data Y 结果对象
	/**====================================*/
	// 子类型:Data 参数名 是否可空 参数说明
	// String Cash N 用户总现金
	// String CardCount N 银行卡数量
	// String CashFlag N 支付密码标志，0-否，1-是
	// String CashPass N 支付密码
	
	//CashList【】列表键值：
	// 子类型:CashList 参数名 是否可空 参数说明
	// String Wallet N 用户单次金额
	// String WalletTime N 金额时间
	// String Description N 金额描述
	
	/**
	 * 将用户钱包数据进行保存及清除
	 * 
	 */
	public static void save(Context context, String Data) {
		SharedPreferences sp = context.getSharedPreferences(BizDefineAll.USER_WALLET_DATA, Context.MODE_PRIVATE);
		sp.edit().putString(BizDefineAll.USER_WALLET_DATA_ALL, Data).commit();
	}
	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(BizDefineAll.USER_WALLET_DATA, Context.MODE_PRIVATE);
		sp.edit().putString(BizDefineAll.USER_WALLET_DATA_ALL, null).commit();
	}
	/**
	 * 判断数据是否为空
	 * */
	public static boolean isnull(Context context) {
		SharedPreferences sp = context.getSharedPreferences(BizDefineAll.USER_WALLET_DATA, Context.MODE_PRIVATE);
		if (StringUtil.isEmpty(sp.getString(BizDefineAll.USER_WALLET_DATA_ALL, "").toString())) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 获取Data下的字符串
	 */
	public static String getData(Context context) {
		SharedPreferences sp = context.getSharedPreferences(BizDefineAll.USER_WALLET_DATA, Context.MODE_PRIVATE);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(sp.getString(BizDefineAll.USER_WALLET_DATA_ALL, ""));
			String data=jsonObject.getString(BizDefineAll.BIZ_RESPONSE_DATA);
			return data;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/***
	 * 获取用户data下的数据
	 * **/
	public static String getWalletItemData(Context context,String keyvalue) {
		String data=getData(context);
		if(!StringUtil.isEmpty(data)) {
			try {
				return new JSONObject(data).optString(keyvalue);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return "";
		
	}
	
	
	
	
}
