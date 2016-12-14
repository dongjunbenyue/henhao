package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
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
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.util.StringUtil;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 登陆页面
 */
public class LoginActivity extends BaseActivity {
	private EditText mPhoneNumber, mPassword,testpassword;
	private TextView login,get_code,textview_changlogin;
	private RelativeLayout login_change,password_login,code_login;
private boolean change=true;
private long exitTime;
private int zhizhen=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initview();
	}
	//选择
public  void text_change(){
	if (change) {
		textview_changlogin.setText(getString(R.string.text_login_mima));
	}else {
		textview_changlogin.setText(getString(R.string.text_login_code));
	}
}
	// 实例化控件
	/**
	 * 
	 */
	public void initview() {
		textview_changlogin=(TextView)findViewById(R.id.TextView_login_change_text001);
		login_change=(RelativeLayout)findViewById(R.id.RelativeLayout_login_change_text001);
		login_change.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (change) {
					change=false;
					code_login.setVisibility(View.GONE);
					password_login.setVisibility(View.VISIBLE);
				}else {
					change=true;
					code_login.setVisibility(View.VISIBLE);
					password_login.setVisibility(View.GONE);
					login_change.setVisibility(View.GONE);
				}
				text_change();
			}
		});
		password_login=(RelativeLayout)findViewById(R.id.RelativeLayout_password_login001);
		code_login=(RelativeLayout)findViewById(R.id.RelativeLayout_code_login001);
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
		mPhoneNumber = (EditText) findViewById(R.id.login_phone_number);
		mPassword = (EditText) findViewById(R.id.login_password);
		testpassword = (EditText) findViewById(R.id.login_password002);
		login = (TextView) findViewById(R.id.login_goto_my_page);
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isMobileNO(mPhoneNumber.getText().toString())) {
				if (change) {
					if (StringUtil.isEmpty(mPassword.getText().toString())) {
						showToast(getString(R.string.hints_edittext_all_data));
						
					} else {
						login001(mPhoneNumber.getText().toString(),mPassword.getText().toString(),"2");
					}
				}else {
					if (StringUtil.isEmpty(testpassword.getText().toString())) {
						showToast(getString(R.string.hints_edittext_all_data));
						
					} else {
						login002(mPhoneNumber.getText().toString(), testpassword.getText().toString()
								,"1");
					}
				}
				}else {
					showToast(getString(R.string.hints_edittext_true_phone));
				}
				
			}
		});

	}
	// 获取手机验证码
	public void getphone_mark() {
		
		HttpLogin.getVerifyCode(new CallbackLogic() {

			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				showToast("验证码已经发送，请注意查收！");
			}
		}, mPhoneNumber.getText().toString(),"5", LoginActivity.this);

	}
	// 登陆接口
			public void login001(String mobileNumber, String password,String logintype) {
				
				HttpLogin.doRequestToLogin(new CallbackLogic() {
					
					@Override
					public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
						// TODO Auto-generated method stub
						try {
							JSONObject object =data.getJSONObject("Data");
							Map<String, String> map=CollectionUtil.jsonObjectToMap_String(object);
							for(String key:map.keySet()){
								UserLoginStatus.save(LoginActivity.this, key, map.get(key).toString());
							}
							OnlyOneDataSave.save(LoginActivity.this, "homejudge", "1");
							finish();
						} catch (Exception e) {
							// TODO: handle exception
						}
						
					}
				}, mobileNumber, password,OnlyOneDataSave.get(getApplicationContext(), "PushChannelId"),logintype,LoginActivity.this);
				// MyMD5.MD5Encode(password, "utf-8")
			}

	// 登陆接口
		public void login002(String mobileNumber, String password,String logintype) {
			
			HttpLogin.doRequestToLogin(new CallbackLogic() {
				
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
					// TODO Auto-generated method stub
					try {
						JSONObject object =data.getJSONObject("Data");
						Map<String, String> map=CollectionUtil.jsonObjectToMap_String(object);
						for(String key:map.keySet()){
							UserLoginStatus.save(LoginActivity.this, key, map.get(key).toString());
						}
						OnlyOneDataSave.save(LoginActivity.this, "homejudge", "1");
						finish();
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
			}, mobileNumber, MD5Encode(password,"utf-8"),OnlyOneDataSave.get(getApplicationContext(), "PushChannelId"),logintype,LoginActivity.this);
			// MyMD5.MD5Encode(password, "utf-8")
		}



	@Override
	public void setPageTitle(ViewGroup titleParentView) {
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
				OnlyOneDataSave.save(LoginActivity.this, "login", "0");
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		centerView.setText("登录");
		rightView.setText("注册");
		rightView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(LoginActivity.this, Regist.class);
				finish();
			}
		});
		centerView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (zhizhen==0) {
					exitTime=System.currentTimeMillis();
				}
				zhizhen++;
					if ((System.currentTimeMillis() - exitTime) < 5000) {
						if (zhizhen==5) {
						if (change) {
							change=false;
							code_login.setVisibility(View.GONE);
							password_login.setVisibility(View.VISIBLE);
							login_change.setVisibility(View.VISIBLE);
						}
						text_change();
						}
					}else {
						zhizhen=0;
					}
					
					
					
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_login, bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// 没有工具栏，不用设置！
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			OnlyOneDataSave.save(LoginActivity.this, "login", "0");
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
