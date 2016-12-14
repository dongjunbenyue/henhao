package com.bcwcar.android.bencar.base;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.mapapi.SDKInitializer;
import com.bcwcar.android.bencar.adapter.MyTextMessageItemProvider;
import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.config.Config.Environment;
import com.bcwcar.android.bencar.http.OkHttpHelper;
import com.bcwcar.android.bencar.wxapi.ConstantWeiXin;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youzan.sdk.YouzanSDK;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import io.rong.imkit.RongIM;

public class BaseApplication extends Application {
	// private static final String LOG_TAG =
	// BaseApplication.class.getSimpleName();
	private static BaseApplication INSTANCE;
	private boolean isDownload;
	public static Typeface tf;

	private String UA;

	@Override
	public void onCreate() {
		super.onCreate();
		isDownload = false;
		INSTANCE = this;
		UA = "kdtUnion_17c24c6cae8e8867ec1462931756191";
		// 初始化融云sdk
		/**
		 * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
		 * io.rong.push 为融云 push 进程名称，不可修改。
		 */
		if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))
				|| "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
			/**
			 * IMKit SDK调用第一步 初始化
			 */
			RongIM.init(this);
			RongIM.getInstance().registerMessageTemplate(new MyTextMessageItemProvider());
			// @ 消息模板展示
			// RongContext.getInstance().setDefaultMessageTemplate(new
			// testadapter());
			// RongContext.getInstance().registerConversationTemplate(new
			// WODE());
		}

		// 微信
		final IWXAPI wxApi = WXAPIFactory.createWXAPI(this, ConstantWeiXin.APP_ID, false);
		wxApi.registerApp(ConstantWeiXin.APP_ID);

		// SLog.v(LOG_TAG, "onCreate() : ALLOW_CRASH_HANDLER = " +
		// Config.ALLOW_CRASH_HANDLER);
		if (Config.ALLOW_CRASH_HANDLER) {
			// 异常处理，注册CrashHandler
			CrashHandler.getInstance().init(this);
		}
		try {
			init();
		} catch (Exception e) {
			// TODO: handle exception
		}
		tf = Typeface.createFromAsset(getAssets(), "HYQiH18030F45TTF.ttf");
	}

	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}

	/**
	 * 不使用任何参数，得到Application级别的上下文（全局的）
	 */

	public static BaseApplication getBaseAppContext() {
		return INSTANCE;
	}

	/**
	 * 不使用任何参数，得到Application级别的资源引用（全局的）
	 **/
	public static Resources getBaseAppResources() {
		return INSTANCE.getResources();
	}

	public static Typeface geTypeface11() {

		return tf;

	}

	/**
	 * 获得当前进程的名字
	 *
	 * @param context
	 * @return 进程号
	 */
	public static String getCurProcessName(Context context) {

		int pid = android.os.Process.myPid();

		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {

			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}

	private void init() {
		// 百度地图
		SDKInitializer.initialize(getApplicationContext());
		// 设置运行环境
		Config.setEnvironment(Environment.DEV);

		// 网络请求初始化
		OkHttpHelper.getInstance();

		// 分享平台初始化
		ShareSDK.initSDK(this);
		// 友赞
		YouzanSDK.init(this, UA);
		// 极光推送
		JPushInterface.setDebugMode(true);
		JPushInterface.init(getApplicationContext());
		JPushInterface.setLatestNotificationNumber(getApplicationContext(), 3);

	}
}