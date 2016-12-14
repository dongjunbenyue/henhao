package com.bcwcar.android.bencar.activity.MainHome;

import com.bcwcar.android.bencar.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class AdActivity extends Activity{
	
	private WebView webView;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_daijia);
		
		String url=getIntent().getStringExtra("PageUrl");
		System.out.println(url);
		 webView=(WebView)findViewById(R.id.webView);
		Loading(url);
	}
	// 返回键重写
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if (webView.canGoBack()) {
					webView.goBack();
				} else {
					finish();
				}
				return false;
			}
			return super.onKeyDown(keyCode, event);
		}
		public void Loading(String url) {
			WebSettings webSettings = webView.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
			webSettings.setLoadWithOverviewMode(true);
			webSettings.setBuiltInZoomControls(true);
			webSettings.setSupportZoom(true);
			webSettings.setLoadsImagesAutomatically(true); // 支持自动加载图片
			webView.requestFocusFromTouch();
			// 这样就可以使用window.injs来调用它的方法
			// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
			webView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					// TODO Auto-generated method stub
					// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
					System.out.println(url);
					view.loadUrl(url);
					return true;
				}

				@Override
				public void onPageFinished(WebView view, String url) {
					// TODO Auto-generated method stub
					super.onPageFinished(view, url);
				}
			});
			webView.setWebChromeClient(new WebChromeClient() {

				 public void onProgressChanged(WebView view, int newProgress) {
           // TODO Auto-generated method stub
           if (newProgress == 100) {
               // 网页加载完成
           	
            
           } else {
               // 加载中
           	
           }

				 }
				@Override
				public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
					System.out.println("==========弹窗001===========" + url);
					// TODO Auto-generated method stub
					return super.onJsAlert(view, url, message, result);
				}

				@Override
				public void onReceivedTitle(WebView view, String title) {
					// TODO Auto-generated method stub
					super.onReceivedTitle(view, title);
					System.out.println("title001===============" + title);

				}
			});
			webView.loadUrl(url);
		}
}
