package com.bcwcar.android.bencar.activity;

import java.io.File;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseApplication;
import com.bcwcar.android.bencar.base.ExampleUtil;
import com.bcwcar.android.bencar.base.GPS_Positioning;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.OnlyOneDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;
import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.notification.PushNotificationMessage;
/**
 * 启动页
 */
public class SplashActivity extends InstrumentedActivity {
	private final static int SWITCH_MAINTABACTIVITY = 1000;
	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SWITCH_MAINTABACTIVITY:
				//========
				String appKey = ExampleUtil.getAppKey(getApplicationContext());
				String rid = JPushInterface.getRegistrationID(getApplicationContext());
				String deviceId = ExampleUtil.getDeviceId(getApplicationContext());
				OnlyOneDataSave.save(SplashActivity.this, "PushChannelId", rid);
				System.out.println("rid====="+rid);
				System.out.println("appKey====="+appKey);
				System.out.println("deviceId====="+deviceId);
				if (!LocationDataSave.get(getApplicationContext(), "IsFirstLogin").equals("false")) {
					LocationDataSave.save(getApplicationContext(), "IsFirstLogin", "false");
					Intent mIntent = new Intent();
					mIntent.setClass(SplashActivity.this, GuideActivity.class);
					SplashActivity.this.startActivity(mIntent);
				}else {
					Intent mIntent = new Intent();
					mIntent.setClass(SplashActivity.this, MainActivity.class);
					SplashActivity.this.startActivity(mIntent);
				};
				
				finish();
				break;
			}
		};
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qidong_page);
		OnlyOneDataSave.save(SplashActivity.this, "rongyun", "0");
		OnlyOneDataSave.save(SplashActivity.this, "hotshop", "0");
		OnlyOneDataSave.save(SplashActivity.this, "hotbrand", "0");
		OnlyOneDataSave.save(SplashActivity.this, "gg", "0");
		LocationDataSave.save(SplashActivity.this, "CityName", getString(R.string.text_moren_cityname));
		LocationDataSave.save(SplashActivity.this, "Latitude", "28.21994");
		LocationDataSave.save(SplashActivity.this, "Longitude","112.898132");
		JPushInterface.init(SplashActivity.this);
		new GPS_Positioning(SplashActivity.this, mHandler).gps();
		/**
		 * 设置接收rongyun push 消息的监听器。
		 */
		RongIM.setOnReceivePushMessageListener(new MyReceivePushMessageListener());
		connect(UserLoginStatus.get(SplashActivity.this, "RongToken"));
		
	}
	// 生成stroemanager文件夹
	public void makeRootDirectory() {
		File file = null;
		File file001 = null;
		try {
			file = new File("/sdcard/stroemanager");
			if (!file.exists()) {
				file.mkdir();
			}
			file001 = new File("/sdcard/stroemanager/test.txt");
			if (file001.exists()) {
				file001.delete();
			}
			file001.createNewFile();
		} catch (Exception e) {
			Log.i("error:", e + "");
		}
	}

	/**
	 * 建立与融云服务器的连接
	 *
	 * @param token
	 */
	private void connect(String token) {

		if (getApplicationInfo().packageName.equals(BaseApplication.getCurProcessName(getApplicationContext()))) {
			/**
			 * IMKit SDK调用第二步,建立与服务器的连接
			 */
			RongIM.connect(token, new RongIMClient.ConnectCallback() {
				/**
				 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的
				 * Token
				 */
				@Override
				public void onTokenIncorrect() {
					mHandler.sendEmptyMessageDelayed(SWITCH_MAINTABACTIVITY, 1000);
					Toast.makeText(SplashActivity.this, "聊天功能无法开启！错误码:身份过期", 1).show();
				}

				/**
				 * 连接融云成功
				 * 
				 * @param userid
				 *            当前 token
				 */
				@Override
				public void onSuccess(String userid) {
					mHandler.sendEmptyMessageDelayed(SWITCH_MAINTABACTIVITY, 1000);
					OnlyOneDataSave.save(SplashActivity.this, "rongyun", "1");
				}

				/**
				 * 连接融云失败
				 * 
				 * @param errorCode
				 *            错误码，可到官网 查看错误码对应的注释
				 */
				@Override
				public void onError(RongIMClient.ErrorCode errorCode) {
					mHandler.sendEmptyMessageDelayed(SWITCH_MAINTABACTIVITY, 1000);
					Toast.makeText(SplashActivity.this, "聊天功能无法开启！错误码="+errorCode, 1).show();
				}
			});
		}
	}


	
	private class MyReceivePushMessageListener implements RongIMClient.OnReceivePushMessageListener {

		/**
		 * 收到 push 消息的处理。
		 *
		 * @param pushNotificationMessage
		 *            push 消息实体。
		 * @return true 自己来弹通知栏提示，false 融云 SDK 来弹通知栏提示。
		 */
		@Override
		public boolean onReceivePushMessage(PushNotificationMessage pushNotificationMessage) {
			return false;
		}
	}
}
