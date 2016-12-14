package com.bcwcar.android.bencar.activity.MainMy;

import com.alipay.android.phone.mrpc.core.r;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class AboutUsSecret extends BaseActivity{
    WebView webview;
    
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
		centerView.setText(R.string.about_us_privacy_statement);
		centerView.getPaint().setFakeBoldText(true);  //中文字体加粗
		rightView.setVisibility(View.GONE);
		// leftView.setVisibility(View.GONE);
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.body_about_us_secret, bodyParentView);
		webview=(WebView)findViewById(R.id.about_us_webview);
		WebSettings wSet = webview.getSettings();  
		        wSet.setJavaScriptEnabled(true);  
		          
		        webview.loadUrl("file:///android_asset/yinsishengming.html");  
		        changeFonts(bodyParentView);

	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}



	

}
