package com.bcwcar.android.bencar.datasave;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.DataSave.UserLogin;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.StringUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserLoginStatus {
	/**
	 * sp键名
	 * 昵称=NickName
	 * 签名=Sign
	 * 头像地址=IconUrl
	 * 加盟城市=CityName
	 * 加盟城市id=CityId
	 * 推送标志 PushTag
	 * 登录 Logindj
	 */
//	"Data": {
//    "UserMobile": "13307318971",
//    "UserName": "",
//    "CarCount": "10",
//    "Token": "8fytT46kqCIosAgND6cLpbuS81tJ7TOD7ayRVOhmDOV7jIqUsmwzclHqad8GWDyjT_TDn4hzfjl2vZoLCdmQ31GaC93Zmr1hgzPmYiwkGi6xN2kbrdO_-0GwEuhU_OpwRdBmNzPfR900dR-dF1R1z-kTLFok21R4-FwGN-BW",
//    "Gender": "1",
//    "CarDesc": "Cayman",
//    "NickName": "卒7",
//    "IconUrl": "uploadFiles/uploadImgs/20160429/c643688f692b4f129b1ed694632dd550.jpg",
//    "RongToken": "whqbUC4UecUx9L32ceXlCezq9jOKAH6PYS3n3nU3IpbaKZh0jYU/J74BrMZlhGxTvm/+uMYt2mmOA6gyTMQJZueAZeF5haTGrTyT+UcwcDt1JdTx5tlwIAMc5bdlZEGDMo2G1vsUcVg=",
//    "AfterServiceFlag": "0",
//    "Score": "95010",
//    "BeforeServiceFlag": "0",
//    "InviteKey": "a1c457d49fac1376360cffe5a30708a1",
//    "UserId": "172de8f4-74e9-45ff-94b7-0e0d18beced0",
//    "ShopFlag": "1",
//    "ShopId": "d22b9148-d486-4475-b89b-a0e15bb5d7fd"
//     Job 行业
	/**
	 * 通过判断存储的Token值和UserId值是否为null或空字符串，来确认用户登录状态。
	 * @param context
	 * @return
	 */
	public static boolean isLoggedOn(Context context) {
		if(StringUtil.isEmpty(get(context, "Token"))) {
			return false;
		}
		if(StringUtil.isEmpty(get(context, "UserId"))) {
			return false;
		}
		
		return true;
	}
	public static void save(Context context,String key, String value) {
		SharedPreferences dingdan = context.getSharedPreferences("userinfo", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public static void clear(Context context) {
		SharedPreferences dingdan = context.getSharedPreferences("userinfo", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.clear();
		editor.commit();
	}
	public static String get(Context context,String key) {
		SharedPreferences dingdan = context.getSharedPreferences("userinfo", 0);
		String string = dingdan.getString(key, "").toString();
		return string;
	}
	
}