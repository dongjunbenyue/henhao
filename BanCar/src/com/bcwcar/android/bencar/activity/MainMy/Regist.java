package com.bcwcar.android.bencar.activity.MainMy;

import java.util.Map;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpLogin;
import com.bcwcar.android.bencar.datasave.OnlyOneDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.StringUtil;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Regist extends BaseActivity{
	private EditText mPhoneNumber, mPassword,testpassword;
	private TextView login,get_code,textview_changlogin,zhucetext,nickname;
	private LinearLayout zhuceline;
	private RelativeLayout nickline;
	
	private CheckBox checkBoxzhuce;
	String  mima="abc1234";
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
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Regist.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("注册");
		rightView.setText("");
		rightView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(Regist.this, LoginActivity.class);
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.body_login, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initview();
	}
	// 获取手机验证码
		public void getphone_mark() {
			
			HttpLogin.getVerifyCode(new CallbackLogic() {

				
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
					// TODO Auto-generated method stub
					showToast("验证码已经发送，请注意查收！");
				}
			}, mPhoneNumber.getText().toString(),"0", Regist.this);

		}
		
	  /**
	   * 确认注册
	   */
		public void toReg(){
			HttpLogin.toReg(new CallbackLogic() {
				
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
					// TODO Auto-generated method stub
					showToast("注册成功");
					try {
						JSONObject object =data.getJSONObject("Data");
						Map<String, String> map=CollectionUtil.jsonObjectToMap_String(object);
						for(String key:map.keySet()){
							UserLoginStatus.save(Regist.this, key, map.get(key).toString());
						}
						OnlyOneDataSave.save(Regist.this, "homejudge", "1");
						finish();
					} catch (Exception e) {
						// TODO: handle exception
					}
					finish();
				}
			}, nickname.getText().toString(), MD5Encode(mima,"utf-8"), Regist.this,mPassword.getText().toString(),mPhoneNumber.getText().toString());
		}
		public void initview(){
			mPhoneNumber = (EditText) findViewById(R.id.login_phone_number);
			mPassword = (EditText) findViewById(R.id.login_password);
			nickline=(RelativeLayout)findViewById(R.id.nicknameline);
			nickname=(EditText)findViewById(R.id.nickname);
			zhuceline=(LinearLayout)findViewById(R.id.zhucexieyiline);
			zhuceline.setVisibility(View.VISIBLE);
			nickline.setVisibility(View.VISIBLE);
			checkBoxzhuce=(CheckBox)findViewById(R.id.checkboxzhuce);
			zhucetext=(TextView)findViewById(R.id.textzhucexieyi);
			zhucetext.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				PageUtil.jumpTo(Regist.this, AboutUsSecret002.class);	
				}
			});
			login = (TextView) findViewById(R.id.login_goto_my_page);
			get_code=(TextView)findViewById(R.id.TextView_set_withdraw_password_get_confirmationnum001);
			get_code.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!StringUtil.isEmpty(mPhoneNumber.getText().toString())) {
						getphone_mark();
					}
				}
			});
			login.setText("注册");
			login.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (checkBoxzhuce.isChecked()) {
						toReg();
					}
					
				}
			});
		}
}
