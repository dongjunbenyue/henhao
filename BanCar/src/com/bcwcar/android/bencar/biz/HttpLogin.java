package com.bcwcar.android.bencar.biz;

import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.config.Constant;
import com.bcwcar.android.bencar.http.OkHttpHelper;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;

import android.content.Context;

/**
 * 注册登录  所有网络请求业务
 */
public class HttpLogin {
	/**
	 * 获取手机验证码
	 */
	public static void getVerifyCode(CallbackLogic callbackLogic, String mobileNumber,String codeType,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_CHECKCODE;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"UserMobile", mobileNumber,"CodeType",codeType),context);
	}
	
	/**
	 * 验证手机验证码是否正确，验证码类型(CodeType)目前有0、1、2三种，用于验证注册、找回密码、其它
	 * 分别对应常量IS_CHECKCODE_RIGHT_CODETYPE_REG、IS_CHECKCODE_RIGHT_CODETYPE_FIND_PWD、IS_CHECKCODE_RIGHT_CODETYPE_OTHER
	 */
	public static void checkVerifyCodeRight(CallbackLogic callbackLogic, String mobileNumber, String codeType, String CheckCode,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_IS_CHECKCODE_RIGHT;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"UserMobile", mobileNumber,
					"CodeType", codeType,
					"CheckCode", CheckCode,
					"ClientType", BizDefineAll.DEVICE_TYPE),context);
	}
	
	/**
	 * 执行注册
	 */
	public static void toReg(CallbackLogic callbackLogic, String nickName, String password,Context context,String CheckCode,String UserMobile) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_TO_REG;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"UserMobile",UserMobile,	
					"NickName", nickName,
					"UserPass", password,
					
					"CheckCode",CheckCode,
					"ClientType", BizDefineAll.DEVICE_TYPE),context);
	}
	/**
	 * 执行登陆
	 */
	public static void doRequestToLogin(CallbackLogic callbackLogic, String mobileNumber, String password,String PushChannelId,String LoginType,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_TO_LOGIN;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"UserMobile", mobileNumber,
					"UserPass", password,
					"ClientType", BizDefineAll.DEVICE_TYPE,"PushChannelId",PushChannelId,"LoginType",LoginType),context);
	}
	
	/**
	 * 执行找回密码，流程是先获取手机验证码->校验是否正确->校验正确的token作为找回密码的token使用
	 */
	public static void doRequestFindPwd(CallbackLogic callbackLogic, String token, String newPwd,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_FIND_PWD;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token,
					"NewPwd", newPwd,
					"ClientType", BizDefineAll.DEVICE_TYPE),context);
	}
//	/**
//	 * 执行修改密码，必须是登录状态才能修改密码
//	 */
//	public static void doRequestPwdReset(CallbackLogic callbackLogic, String token, String newPwd) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_PWD_RESET;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//					"Token", token,
//					"NewPwd", newPwd,
//					"ClientType", BizDefineAll.DEVICE_TYPE));
//	}
}