package com.bcwcar.android.bencar.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 需要android.permission.ACCESS_NETWORK_STATE权限
 * @author Administrator
 *
 */
public class NetworkUtil {
	private static final String LOG_TAG = NetworkUtil.class.getSimpleName();
	
	/**
	 * 判断网络是否连通（包括WiFi、移动数据等）
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) 
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			//SLog.v(LOG_TAG, "isNetworkConnected() return true");
			return true;
		} else {
			//SLog.v(LOG_TAG, "isNetworkConnected() return false");
			return false;
		}
	}
}
