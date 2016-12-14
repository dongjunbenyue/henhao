package com.bcwcar.android.bencar.activity.MainMy;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.PageUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutUs extends BaseActivity{
     RelativeLayout privacy_statement,introduce,suggest,jionus,common_problem;
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
		centerView.setText("关于我们");
		rightView.setVisibility(View.GONE);
		
	changeFonts(titleParentView);	
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.aboutus, bodyParentView);
		changeFonts(bodyParentView);
		privacy_statement=(RelativeLayout)findViewById(R.id.privacy_statement);
		privacy_statement.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			PageUtil.jumpTo(AboutUs.this, AboutUsSecret.class);	
			}
		});
		introduce=(RelativeLayout)findViewById(R.id.introduce);
		introduce.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		PageUtil.jumpTo(AboutUs.this, BcwIntroduce.class);		
			}
		});
		suggest=(RelativeLayout)findViewById(R.id.suggest);
		suggest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			PageUtil.jumpTo(AboutUs.this, Acitivityfankui.class);	
			}
		});
		jionus=(RelativeLayout)findViewById(R.id.jionus);
		jionus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(AboutUs.this, JoinStore4SActivity.class);
			}
		});
		common_problem=(RelativeLayout)findViewById(R.id.common_problem);
		common_problem.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(AboutUs.this, FaqActivity.class);
			}
		});
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}

}
