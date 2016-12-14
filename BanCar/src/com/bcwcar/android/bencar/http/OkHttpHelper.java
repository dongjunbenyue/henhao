package com.bcwcar.android.bencar.http;

import java.io.IOException;

import java.util.Map;


import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainMy.LoginActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.BizGlobal;
import com.bcwcar.android.bencar.datasave.OnlyOneDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.util.StringUtil;
import com.bcwcar.android.bencar.util.UrlUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class OkHttpHelper {

	private static final String LOG_TAG = OkHttpClient.class.getSimpleName();

	public static final OkHttpHelper getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static class SingletonHolder {
		private static final OkHttpHelper INSTANCE = new OkHttpHelper();
	}

	private Handler mMainThreadHandler;
	private OkHttpClient mOkHttpClient;
	private ImagePipelineConfig mImagePipelineConfig;

	public OkHttpHelper() {
		mMainThreadHandler = new Handler(Looper.getMainLooper());
		mOkHttpClient = new OkHttpClient();
		mImagePipelineConfig = OkHttpImagePipelineConfigFactory
				.newBuilder(ResourceUtil.getBaseAppContext(), mOkHttpClient).build();
		Fresco.initialize(ResourceUtil.getBaseAppContext(), mImagePipelineConfig);
	}

	public void addGetRequest(CallbackLogic callbackLogic, String baseUrl, Map<String, String> paramsKeyValue,
			Context context) {
		BaseActivity.Dialogshow(context);
		addRequest("GET", callbackLogic, baseUrl, context, paramsKeyValue);
		StringUtil.map_key_value(paramsKeyValue, baseUrl);
	}

	public void addPostRequest(CallbackLogic callbackLogic, String baseUrl, Map<String, String> paramsKeyValue,
			Context context) {
		BaseActivity.Dialogshow(context);
		addRequest("POST", callbackLogic, baseUrl, context, paramsKeyValue);
		StringUtil.map_key_value(paramsKeyValue, baseUrl);
	}

	private void addRequest(String method, final CallbackLogic callbackLogic, String baseUrl, final Context context,
			Map<String, String> paramsKeyValue) {

		Request request = createRequest(method, baseUrl, paramsKeyValue);
		Callback callback = new Callback() {

			@Override
			public void onResponse(Response response) throws IOException {
				String responseBody = response.body().string();
				Log.i(getClass().getSimpleName(), "onResponse() ： responseBody = " + responseBody);
				final BizGlobal bizGlobal = new BizGlobal(responseBody);
				final String responseCode = bizGlobal.getResponseCode();
				final String OrderId = bizGlobal.getOrderId();

				if (responseCode.equals(BizDefineAll.BIZ_SUCCESSFUL)) {
					System.out.println("BIZ_SUCCESSFUL");
					BaseActivity.Dialogcancel();
					// 业务逻辑成功
					if (callbackLogic != null) {
						// 运行在UI线程
						mMainThreadHandler.post(new Runnable() {
							@Override
							public void run() {
								callbackLogic.onBizSuccess(bizGlobal.getResponseDescription(),
										bizGlobal.getResponseData(), bizGlobal.getResponseAll());
							}
						});
					}
				} else {
					// 只要responseCode不为BIZ_SUCCESSFUL(0)，就表示业务逻辑失败。。。
					if (callbackLogic != null) {
						System.out.println("BIZ_fail");
						BaseActivity.Dialogcancel();
						Log.d(LOG_TAG, bizGlobal.getResponseDescription());
						// 运行在UI线程
						mMainThreadHandler.post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(context, bizGlobal.getResponseDescription(), 0).show();
								if (responseCode.equals("101") || responseCode.equals("103") || responseCode.equals("102")) {
									UserLoginStatus.clear(context);
									System.out.println(OnlyOneDataSave.get(context, "login")+"****************************");
									if (!OnlyOneDataSave.get(context, "login").equals("0")) {
										
										Intent intent = new Intent(context, LoginActivity.class);
										context.startActivity(intent);
									}
									OnlyOneDataSave.save(context, "login", "1");
								}

							}
						});
					}
				}
			}

			@Override
			public void onFailure(final Request request, final IOException e) {
				if (callbackLogic != null) {
					System.out.println("onFailure");
					OnlyOneDataSave.save(context, "login", "1");
					BaseActivity.Dialogcancel();
					// 运行在UI线程
					mMainThreadHandler.post(new Runnable() {
						@Override
						public void run() {
							// Log.w(LOG_TAG, "Network error, please try
							// again!");
							Toast.makeText(context, context.getString(R.string.network_error_please_try_again), 0)
									.show();
						}
					});
				}
			}
		};

		mOkHttpClient.newCall(request).enqueue(callback);
	}

	private Request createRequest(String method, String baseUrl, Map<String, String> paramsKeyValue) {
		if (method.equalsIgnoreCase("GET")) {
			String fullUrl = UrlUtil.concatUrlAndParams(baseUrl, UrlUtil.convertMapToHttpParams(paramsKeyValue));
			Request.Builder builder = new Request.Builder();
			builder.addHeader("appkey", Des3.getmima());
			return builder.url(fullUrl).get().build();
		} else if (method.equalsIgnoreCase("POST")) {
			FormEncodingBuilder postFormBuilder = new FormEncodingBuilder();
			for (Map.Entry<String, String> entry : paramsKeyValue.entrySet()) {
				postFormBuilder.add(entry.getKey(), entry.getValue());
			}
			// Log.w(getClass().getSimpleName(), "baseUrl = " + baseUrl);
			// Log.w(getClass().getSimpleName(), "paramsKeyValue = " +
			// paramsKeyValue);
			Request.Builder builder = new Request.Builder();
			builder.addHeader("appkey", Des3.getmima());
			return builder.url(baseUrl).post(postFormBuilder.build()).build();
		} else {
			// 只支持GET请求和POST请求
			throw new RuntimeException("only support GET or POST, method = " + method + ", baseUrl = " + baseUrl
					+ ", paramsKeyValue = " + paramsKeyValue);
		}
	}

	public interface CallbackLogic {
		void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata);

		// void onBizFailure(String responseDescription, JSONObject data,String
		// responseCode);
		//
		// void onNetworkError(Request request, IOException e);
	}
}
