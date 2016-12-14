package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;

import org.json.JSONObject;

import com.baidu.mapapi.map.Marker;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpLogin;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.StringUtil;
import com.squareup.okhttp.Request;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WithdrawCashPasswordPhone extends BaseActivity {

	private TextView finish_textview, user_phone, get_mark;
	private EditText mark_confirmation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initview();
	}

	// 实例化控件
	public void initview() {
		finish_textview = (TextView) findViewById(R.id.TextView_set_withdraw_password001);
		finish_textview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!StringUtil.isEmpty(mark_confirmation.getText().toString())) {
					CheckCodeisright(mark_confirmation.getText().toString());
				}
			}
		});
		user_phone = (TextView) findViewById(R.id.withdraw_userphone001);
		user_phone.setText(UserLoginStatus.get(WithdrawCashPasswordPhone.this, "UserMobile"));
		get_mark = (TextView) findViewById(R.id.TextView_set_withdraw_password_get_confirmationnum001);
		get_mark.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getphone_mark();

			}
		});
		mark_confirmation = (EditText) findViewById(R.id.EditText_phone_conformation_vecode001);

	}

	// 获取手机验证码
	public void getphone_mark() {
		
//		HttpLogin.getCheckCode(new CallbackLogic() {
//
//			@Override
//			public void onNetworkError(Request request, IOException e) {
//				// TODO Auto-generated method stub
//				showToast(getString(R.string.network_error_please_try_again));
//			}
//
//			@Override
//			public void onBizSuccess(String responseDescription, JSONObject data, String all_data) {
//				// TODO Auto-generated method stub
//				Dialogcancel();
//				showToast(getString(R.string.text_phone_Verification_send_success));
//			}
//
//			@Override
//			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
//				// TODO Auto-generated method stub
//				showToast(responseDescription);
//				Dialogcancel();
//				denglu_reset(responseCode, responseDescription, WithdrawCashPasswordPhone.this);
//			}
//		}, UserInfoData.getUserInfo(getApplicationContext(), "Token"),
//				UserInfoData.getUserInfo(getApplicationContext(), "UserMobile"), "3");
		HttpLogin.getVerifyCode(new CallbackLogic() {
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				
			}
		}, UserLoginStatus.get(WithdrawCashPasswordPhone.this, "UserMobile"), "2", WithdrawCashPasswordPhone.this);

	}

	// 验证验证码是否正确
	public void CheckCodeisright(String CheckCode) {
		
		HttpLogin.checkVerifyCodeRight(new CallbackLogic() {

			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				Dialogcancel();
				Intent intent = new Intent(WithdrawCashPasswordPhone.this, WithdarwSettingsCashPassword.class);
				intent.putExtra("action", "set");
				startActivity(intent);
				finish();
			}
		}, UserLoginStatus.get(WithdrawCashPasswordPhone.this, "UserMobile"),
				"2",  CheckCode,WithdrawCashPasswordPhone.this);
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
		centerView.setText(getString(R.string.text_confirmation_phonenum));
		rightView.setText("");
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.withdraw_cash_password_confirmation, bodyParentView);
		changeFonts((ViewGroup) view);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

}
