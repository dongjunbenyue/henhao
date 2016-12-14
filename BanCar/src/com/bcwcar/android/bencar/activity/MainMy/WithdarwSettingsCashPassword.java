package com.bcwcar.android.bencar.activity.MainMy;



import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.widget.KeyboardUtil;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WithdarwSettingsCashPassword extends BaseActivity {
	KeyboardUtil keyboardUtil;
	private String action;
	private TextView tt001,tt002;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			action=getIntent().getStringExtra("action").toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		keyboardUtil=new KeyboardUtil(WithdarwSettingsCashPassword.this, WithdarwSettingsCashPassword.this,action);
		keyboardUtil.showKeyboard();
		//============
		tt001=(TextView)findViewById(R.id.TextView_password_change001);
		tt002=(TextView)findViewById(R.id.TextView_password_change002);
		if (action.equals("pay")) {
			tt001.setText(getString(R.string.text_settings_pay_password_text001));
			tt002.setText(getString(R.string.text_settings_pay_password_text002));
		}else if (action.equals("PayQuanActivity")) {
			tt001.setText(getString(R.string.text_settings_pay_password_text001));
			tt002.setText(getString(R.string.text_settings_pay_password_text002));
		}else {
			tt001.setText(getString(R.string.text_settings_pay_password_set_text001));
			tt002.setText(getString(R.string.text_settings_pay_password_set_text002));
		}
	}

		
		

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		changeFonts((ViewGroup) rootView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.setText("支付");
		
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.pay_password_settings_xml, bodyParentView);
		changeFonts((ViewGroup) view);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}
}
