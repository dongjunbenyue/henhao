package com.bcwcar.android.bencar.http;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.alipay.android.phone.mrpc.core.s;
import com.bcwcar.android.bencar.activity.FillInOrderActivity;

import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.BizGlobal;

import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.util.UrlUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

public class OkHttpHelper002 {

	private static final String LOG_TAG = OkHttpClient.class.getSimpleName();

	public static final OkHttpHelper002 getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static class SingletonHolder {
		private static final OkHttpHelper002 INSTANCE = new OkHttpHelper002();
	}

	private Handler mMainThreadHandler;
	private OkHttpClient mOkHttpClient;
	private ImagePipelineConfig mImagePipelineConfig;

	private OkHttpHelper002() {
		// 初始化
		mMainThreadHandler = new Handler(Looper.getMainLooper());
		mOkHttpClient = new OkHttpClient();
		mImagePipelineConfig = OkHttpImagePipelineConfigFactory
				.newBuilder(ResourceUtil.getBaseAppContext(), mOkHttpClient).build();
		Fresco.initialize(ResourceUtil.getBaseAppContext(), mImagePipelineConfig);
	}

	public void addGetRequest(CallbackLogic002 callbackLogic, String baseUrl, Map<String, String> paramsKeyValue) {

		addRequest("GET", callbackLogic, baseUrl, paramsKeyValue);
	}

	public void addPostRequest(CallbackLogic002 callbackLogic, String baseUrl, Map<String, String> paramsKeyValue) {
		addRequest("POST", callbackLogic, baseUrl, paramsKeyValue);
		
		for (String key : paramsKeyValue.keySet()) {
			
		}

	}
	public void addPostRequest1(CallbackLogic002 callbackLogic, String baseUrl, Map<String, String> paramsKeyValue) {
		addRequest1("POST", callbackLogic, baseUrl, paramsKeyValue);
		
		for (String key : paramsKeyValue.keySet()) {
			
		}

	}

	private void addRequest(String method, final CallbackLogic002 callbackLogic, String baseUrl,
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
					Log.d(LOG_TAG, bizGlobal.getResponseDescription());
					// 业务逻辑成功
					if (callbackLogic != null) {
						// 运行在UI线程
						mMainThreadHandler.post(new Runnable() {
							@Override
							public void run() {
								callbackLogic.onBizSuccess(bizGlobal.getResponseDescription(),
										bizGlobal.getResponseData(), bizGlobal.getResponseAll1());
							}
						});
					}
				} else {
					// 只要responseCode不为BIZ_SUCCESSFUL(0)，就表示业务逻辑失败。。。
					if (callbackLogic != null) {
						Log.d(LOG_TAG, bizGlobal.getResponseDescription());
						// 运行在UI线程
						mMainThreadHandler.post(new Runnable() {
							@Override
							public void run() {
								if (responseCode.equals("101") || responseCode.equals("103")) {
									// 对responseCode
									// 101,103进行统一处理，需要清除token并提示用户跳转到登录页面。
									// 101表示用户在别的地方登录，103表示用户token无效。
									//System.out.println("用户没有登陆或者用户手机在其他终端登录");
									
								}
								callbackLogic.onBizFailure(bizGlobal.getResponseDescription(),
										bizGlobal.getResponseData(),bizGlobal.getResponseCode());
							}
						});
					}
				}
			}

			@Override
			public void onFailure(final Request request, final IOException e) {
				if (callbackLogic != null) {
					// 运行在UI线程
					mMainThreadHandler.post(new Runnable() {
						@Override
						public void run() {
							//Log.w(LOG_TAG, "Network error, please try again!");
							callbackLogic.onNetworkError(request, e);
						}
					});
				}
			}
		};

		mOkHttpClient.newCall(request).enqueue(callback);
	}
	private void addRequest1(String method, final CallbackLogic002 callbackLogic, String baseUrl,
			Map<String, String> paramsKeyValue) {

		Request request = createRequest(method, baseUrl, paramsKeyValue);
		Callback callback = new Callback() {

			@Override
			public void onResponse(Response response) throws IOException {
				final String responseBody = response.body().string();
				Log.i(getClass().getSimpleName(), "onResponse() ： responseBody = " + responseBody);
				
				
				
					
					// 业务逻辑成功
					if (callbackLogic != null) {
						// 运行在UI线程
						mMainThreadHandler.post(new Runnable() {
							@Override
							public void run() {
								try {
									callbackLogic.onBizSuccess("",
											new JSONObject(responseBody), "");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
					}
				}

			@Override
			public void onFailure(final Request request, final IOException e) {
				if (callbackLogic != null) {
					// 运行在UI线程
					mMainThreadHandler.post(new Runnable() {
						@Override
						public void run() {
							//Log.w(LOG_TAG, "Network error, please try again!");
							callbackLogic.onNetworkError(request, e);
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
			System.out.println("fullurl=="+fullUrl);
			return new Request.Builder().url(fullUrl).get().build();
		} else if (method.equalsIgnoreCase("POST")) {
			FormEncodingBuilder postFormBuilder = new FormEncodingBuilder();
			for (Map.Entry<String, String> entry : paramsKeyValue.entrySet()) {
				postFormBuilder.add(entry.getKey(), entry.getValue());
			}
			Log.w(getClass().getSimpleName(), "baseUrl = " + baseUrl);
			Log.w(getClass().getSimpleName(), "paramsKeyValue = " + paramsKeyValue);
			return new Request.Builder().url(baseUrl).post(postFormBuilder.build()).build();
		} else {
			// 只支持GET请求和POST请求
			throw new RuntimeException("only support GET or POST, method = " + method + ", baseUrl = " + baseUrl
					+ ", paramsKeyValue = " + paramsKeyValue);
		}
	}

	public interface CallbackLogic002 {
		void onBizSuccess(String responseDescription, JSONObject data, String other);

		void onBizFailure(String responseDescription, JSONObject data,String responseCode);

		void onNetworkError(Request request, IOException e);
	}
}
