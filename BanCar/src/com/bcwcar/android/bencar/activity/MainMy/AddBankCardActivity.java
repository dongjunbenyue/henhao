package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpWallet;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.squareup.okhttp.Request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * 添加银行卡页
 */
public class AddBankCardActivity extends BaseActivity {
	final int REQUEST_CODE = 1;
	private static TextView mBankName;
	private EditText kaihuhang;
	private EditText car_num;
	private EditText carid;
	private EditText name;
	private Button tijiao;
	private static Map<String, String> map_getinfo = new HashMap<String, String>();
private String get_action=null;
private RelativeLayout gotoselectbank;
	@Override
	public void setPageTitle(ViewGroup titleParentView) {

		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
		// leftView.setVisibility(View.GONE);
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("添加银行卡");
		
		changeFonts(titleParentView);

	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_add_bank_card, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// LayoutInflater.from(this).inflate(R.layout.common_toolbar_1,
		// toolBarParentView);
	}

	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (REQUEST_CODE == requestCode && Activity.RESULT_OK == resultCode) {
			String bankName = data.getStringExtra("dataObject");
			mBankName.setText(bankName);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
Intent intent=getIntent();
get_action=intent.getStringExtra("Activity");

		initview();
	}

	// dedao得到用户选择的银行卡的相关信息
	public static void get_bank_info(Map<String, String> map) {
		map_getinfo = map;
		mBankName.setText(map.get("BankName").toString());

	}

	// 实例化控件
	private void initview() {
		mBankName = (TextView) findViewById(R.id.add_bank_card_bank_name);
		kaihuhang = (EditText) findViewById(R.id.add_bank_card_numm001);
		car_num = (EditText) findViewById(R.id.add_bank_card_numm002);
		name = (EditText) findViewById(R.id.add_bank_card_numm003);
		carid = (EditText) findViewById(R.id.add_bank_card_numm004);
		gotoselectbank=(RelativeLayout)findViewById(R.id.add_bank_card_select_bank_layout);
        gotoselectbank.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent newIntent = new Intent(AddBankCardActivity.this, SelectBankActivity.class);
				startActivityForResult(newIntent, REQUEST_CODE);	
			}
		}); 
			
		tijiao = (Button) findViewById(R.id.add_bank_card_go);
		tijiao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!kaihuhang.getText().toString().equals("") && !car_num.getText().toString().equals("")
						&& !name.getText().toString().equals("") && !carid.getText().toString().equals("")) {

					initdata(map_getinfo.get("BankId"), kaihuhang.getText().toString(), car_num.getText().toString(),
							name.getText().toString(), carid.getText().toString());
				} else {
					showToast("信息不完整!");
				}
			}
		});
	}

	// 提交银行卡信息
	private void initdata(String BankId, String RegBank, String BankNo, String AccountName, String IdCardNo) {
		
		HttpWallet.addUserBank(new CallbackLogic() {

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				showToast("添加成功");
				finish();
			}
		}, UserLoginStatus.get(AddBankCardActivity.this, "Token"), BankId, RegBank, BankNo, AccountName, IdCardNo,AddBankCardActivity.this);

	}

}
