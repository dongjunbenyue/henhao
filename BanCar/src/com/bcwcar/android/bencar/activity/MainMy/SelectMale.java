package com.bcwcar.android.bencar.activity.MainMy;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class SelectMale extends BaseActivity{

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		changeFonts((ViewGroup)rootView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		
		TextView leftView = (TextView) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		centerView.setText("选择性别");
		centerView.getPaint().setFakeBoldText(true);
		rightView.setVisibility(View.GONE);
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
		LayoutInflater.from(this).inflate(R.layout.selectmale, bodyParentView);
		CheckBox checkBoxnan,checkBoxnv;
		checkBoxnan=(CheckBox)findViewById(R.id.checknan);
		checkBoxnv=(CheckBox)findViewById(R.id.checknv);
		checkBoxnan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
				UserLoginStatus.save(getApplicationContext(), "Gender", "1");
				
				UserLoginStatus.save(getApplicationContext(), "change", "1");
				System.out.println("xingbie***"+UserLoginStatus.get(getApplicationContext(), "Gender"));
				finish();
				}
			}
		});
		checkBoxnv.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					UserLoginStatus.save(getApplicationContext(), "Gender", "0");
					UserLoginStatus.save(getApplicationContext(), "change", "1");
					finish();
				}
			}
		});
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}

}
