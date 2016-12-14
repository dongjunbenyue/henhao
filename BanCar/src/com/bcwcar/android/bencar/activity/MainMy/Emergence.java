package com.bcwcar.android.bencar.activity.MainMy;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Emergence extends BaseActivity{

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
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
		centerView.setText("紧急救援");
		rightView.setVisibility(View.GONE);
		
	changeFonts(titleParentView);	
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.emergency, bodyParentView);
		changeFonts(bodyParentView);
		LinearLayout linearLayout=(LinearLayout)findViewById(R.id.callphone);
		linearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4000185025"));
				startActivity(intent);
			}
		});
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}
  @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	System.out.println("oncreate+++++++");
}
  @Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	System.out.println("onPause+++++++");
}
  @Override
protected void onStart() {
	// TODO Auto-generated method stub
	super.onStart();
	System.out.println("onstart+++++++");
}
  @Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	System.out.println("onstop+++++++");
}
  @Override
protected void onRestart() {
	// TODO Auto-generated method stub
	super.onRestart();
	System.out.println("onrestart+++++++");
}
 @Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	System.out.println("onresume+++++++");
}
}
