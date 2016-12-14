package com.bcwcar.android.bencar.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Toast;

import com.alipay.android.phone.mrpc.core.n;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.bcwcar.android.bencar.datasave.LocationDataSave;

/**
 * //*定位当前城市
 */
public class GPS_Positioning{
	private String str;
	private Context context;
	public LocationClient mLocationClient = null;
	private Handler handler;

	public GPS_Positioning(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}

	// 定位
	public void gps() {
		mLocationClient = new LocationClient(context); // 声明LocationClient类
		LocationClientOption locOption = new LocationClientOption();
		locOption.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		locOption.setCoorType("bd09ll");// 设置定位结果类型
		locOption.setScanSpan(5000);// 设置发起定位请求的间隔时间,ms
		locOption.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		locOption.setNeedDeviceDirect(true);// 设置返回结果包含手机的方向
		mLocationClient.setLocOption(locOption);
		mLocationClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation bDLocation) {
				// TODO Auto-generated method stub
				if (bDLocation==null) {
					Toast.makeText(context, "定位失败", 1).show();
					return;
				}
				str = bDLocation.getAddrStr().toString();
				String strw = str.substring(str.indexOf("省") + 1);
				String[] nStrings = strw.split("市");
				LocationDataSave.save(context, "Latitude", bDLocation.getLatitude() + "");
				LocationDataSave.save(context, "Longitude", bDLocation.getLongitude() + "");
				LocationDataSave.save(context, "CityName", nStrings[0] + "市");
				handler.sendEmptyMessage(101);
				mLocationClient.stop();
			}
		});
		mLocationClient.start();

	}
	

}
