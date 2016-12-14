package com.bcwcar.android.bencar.util;

public class VerifyUtil {
	private static final String LOG_TAG = VerifyUtil.class.getSimpleName();
	
	public static void main(String args[]) {
		System.out.println(verifyPhoneVerifyCode("123456"));
		System.out.println(verifyPhoneVerifyCode("w12345"));
		System.out.println(verifyPhoneVerifyCode("12345p"));
		System.out.println(verifyPhoneVerifyCode("x2345x"));
	}
	
	public static boolean verifyPhoneVerifyCode(String verifyCode) {
		return verifyCode.matches("^\\d{6}$");  //六位数字
	}
}