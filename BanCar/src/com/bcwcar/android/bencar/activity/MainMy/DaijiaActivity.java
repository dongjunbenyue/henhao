package com.bcwcar.android.bencar.activity.MainMy;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.GPS_Positioning;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/*
 * 代驾板块
 */
public class DaijiaActivity extends BaseActivity {

    private WebView webview;
    private String url,lon,lat,phone;
 
 	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		
		
		
		
		lat=	LocationDataSave.get(DaijiaActivity.this, "Latitude");
			lon=LocationDataSave.get(DaijiaActivity.this, "Longitude");
		
		webview=(WebView)findViewById(R.id.webView);
			WebSettings settings = webview.getSettings();
			settings.setJavaScriptEnabled(true);
			phone=UserLoginStatus.get(getApplicationContext(), "UserMobile");
			
			webview.loadUrl("http://h5.edaijia.cn/app/index.html?=01051254&lng="+lon+"&lat="+lat+"&phone="+phone);
         
			
			webview.setWebChromeClient(new WebChromeClient(){
					 public void onProgressChanged(WebView view, int newProgress) {
	            // TODO Auto-generated method stub
	            if (newProgress == 100) {
	                // 网页加载完成
	            	
	             
	            } else {
	                // 加载中
	            	
	            }

					 }}
	);
		Bundle bundle=new Bundle();
		bundle.getString("order_id");
		

	}
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		// leftView.setVisibility(View.GONE);
		leftView.setText("");
		centerView.setText("e代驾");
		leftView.setOnClickListener(new OnClickListener() {
            
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		rightView.setVisibility(View.GONE);
		changeFonts(titleParentView);
		
	}
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_daijia, bodyParentView);
		changeFonts(bodyParentView);
	}
	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}
	
    
	
}
