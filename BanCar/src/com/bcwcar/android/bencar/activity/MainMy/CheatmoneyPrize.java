package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.PageUtil;
import com.squareup.okhttp.Request;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheatmoneyPrize extends BaseActivity{
     private static Context context;
	 WebView webview;
     TextView textview,textView2;
     String  paiming;
     String qianshu="0.00";
     
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true);  //中文字体加粗
		centerView.setText("奖品详情");
		
		rightView.setVisibility(View.GONE);
		changeFonts(titleParentView);
	}
	
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		
		LayoutInflater.from(this).inflate(R.layout.cheatmoneyjiangpin, bodyParentView);
		changeFonts(bodyParentView);
		
}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}


	public void onViewClick(View view) 
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		paiming=WithdrawData.getWithdrawData(getApplicationContext(), "SortOrder");
		if (paiming.equals("1")) {
			qianshu="138.00";
			
		}else if (paiming.equals("2")) {
			qianshu="108.00";
		} else if (paiming.equals("3")) {
			qianshu="72.8";
		} 
		 context=CheatmoneyPrize.this;
		 
		 webview=(WebView)findViewById(R.id.cheatmoneywebview);
			textview=(TextView)findViewById(R.id.shengqinglingjiang);
			textView2=(TextView)findViewById(R.id.jiazhiqianshu);
			textView2.setText("￥"+qianshu);
			WebSettings settings = webview.getSettings();
			settings.setJavaScriptEnabled(true);
			textview.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle bundle=new Bundle();
					bundle.putString("action", "2");
					PageUtil.jumpTo(CheatmoneyPrize.this, UserAdress.class,bundle);
					finish();
				}
			});
			
			//webview.loadUrl("http://abc.bcwcar.com/banner/jiangp/"+get_userbank_data("SortOrder")+".html");

			webview.loadUrl("http://www.bcwcar.com/banner/jiangp/"+paiming+".html");
			webview.setWebViewClient(new WebViewClient(){
				@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon) {
					// TODO Auto-generated method stub
					super.onPageStarted(view, url, favicon);
					
				}
				@Override
				public void onPageFinished(WebView view, String url) {
					// TODO Auto-generated method stub
					super.onPageFinished(view, url);
					Dialogcancel();
				}
			});
	}
}
