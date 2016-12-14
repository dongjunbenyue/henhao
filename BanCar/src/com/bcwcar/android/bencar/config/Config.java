package com.bcwcar.android.bencar.config;

public class Config {
	public static enum Environment {
		
		DEV,
		PROD
	}
	private static Environment mApplicaionEnvironment = null;
	private static final String LOG_TAG = Config.class.getSimpleName();
	/**
	 * 设置APP的运行环境
	 * @param env
	 */
	public synchronized static void setEnvironment(Environment env) {
		//SLog.v(LOG_TAG, "setEnvironment()");
		//如果设置过一次，再次设置不会生效。
		if(mApplicaionEnvironment != null) {
			//SLog.w(LOG_TAG, "setEnvironment() : mApplicaionEnvironment has been initialized before!");
			return;
		}
		mApplicaionEnvironment = env;
		initEnvironment();
		
	}
	//http://120.76.41.174:80/AppFrameWork
	private static void initEnvironment() {
		switch (mApplicaionEnvironment) {
/*
 * 正式服务器
 */		
		case DEV:
			ALLOW_LOG = true;
			ALLOW_CRASH_HANDLER = false;
			DATA_SERVER_URL = "http://www.bcwcar.com/BenCar";//正式服务器
			
			IMAGE_SERVER_URL = "http://file.bcwcar.com";
			break;
/*
 * 测试服务器
 */
		case PROD:
			ALLOW_LOG = true;
			ALLOW_CRASH_HANDLER = false;
			DATA_SERVER_URL = "http://www.bcwcar.cn/BenCar";
			IMAGE_SERVER_URL = "http://file.bcwcar.com";
			break;
		default:
			break;
		}
	}
	
	/**
	 * 是否允许SLog类打印日志
	 */
	public static boolean ALLOW_LOG;
	/**
	 * 是否开启全局的CrashHandler来捕获未处理的异常
	 */
	public static boolean ALLOW_CRASH_HANDLER;
	/**
	 * 接口数据请求服务器地址
	 */
	public static String DATA_SERVER_URL;
	/**
	 * 图片服务器地址
	 */
	public static String IMAGE_SERVER_URL;
}