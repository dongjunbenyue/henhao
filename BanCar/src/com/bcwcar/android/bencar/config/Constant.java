package com.bcwcar.android.bencar.config;
/**
 * 各种常量定义
 */
public class Constant {
	/**
	 * 版本更新地址
	 */
	public static final String versionCode_url="http://7xoo32.com1.z0.glb.clouddn.com/BanCar.apk";
	/***
	 * 分享链接
	 * */
	public final static String URLSHARE="http://www.bcwcar.com/BenCar/app/sendUInvitation?InviteCode=";
	/**
	 * 请求超时的时间
	 */
	public static final int HTTP_REQUEST_TIMEOUT = 15 * 1000;
	/**
	 * 最大重试次数，可以设置成0表示从来都不重试。
	 */
	public static final int HTTP_REQUEST_MAX_RETRIES = 0;
}