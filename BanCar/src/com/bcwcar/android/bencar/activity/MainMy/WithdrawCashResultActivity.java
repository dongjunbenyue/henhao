package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpWallet;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.squareup.okhttp.Request;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WithdrawCashResultActivity  extends BaseActivity{
private String action;
private  LinearLayout pay_success_LinearLayout;
private RelativeLayout pay_fail_RelativeLayout;
private TextView bankname_TextView,banknum_TextView,cashsum_TextView,finish;


@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			action=getIntent().getStringExtra("action").toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		initview();
		if (action.equals("success")) {
			pay_success_LinearLayout.setVisibility(View.VISIBLE);
			pay_fail_RelativeLayout.setVisibility(View.GONE);
			getwithdrawdetail(getIntent().getStringExtra("ApplyId").toString());
		}else if (action.equals("fail")) {
			pay_success_LinearLayout.setVisibility(View.GONE);
			pay_fail_RelativeLayout.setVisibility(View.VISIBLE);
		}
		
	}

//实力化

public void initview(){
	pay_fail_RelativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout_pay_fail001);
	pay_success_LinearLayout=(LinearLayout)findViewById(R.id.LinearLayout_pay_success001);
	bankname_TextView=(TextView)findViewById(R.id.TextView_withdraw_bank_name001);
	banknum_TextView=(TextView)findViewById(R.id.TextView_withdraw_bank_endnum001);
	cashsum_TextView=(TextView)findViewById(R.id.TextView_withdraw_cash_num001);
	finish=(TextView)findViewById(R.id.TextView_finish_to_mainactivity001);
	finish.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(WithdrawCashResultActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
	});
	
}
//获取提现详情
public void getwithdrawdetail(String ApplyId){
	
	HttpWallet.getCashWithdrawDetail(new CallbackLogic() {
		
		

		@Override
		public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
			// TODO Auto-generated method stub
			try {
				JSONObject object=data.getJSONObject(BizDefineAll.BIZ_RESPONSE_DATA);
				String bankname=object.getString("BankName");
				String bankno=object.getString("BankNo");
				String cash=object.getString("CashSum").toString();
 				String banknum_last_four=bankno.substring(bankno.length()-4,bankno.length());
				bankname_TextView.setText(bankname);
				banknum_TextView.setText(banknum_last_four);
				cashsum_TextView.setText(cash);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}, UserLoginStatus.get(WithdrawCashResultActivity.this, "Token"), ApplyId,WithdrawCashResultActivity.this);
	
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
		rightView.setVisibility(View.GONE);
		leftView.setText(getString(R.string.text_withdraw_cashdetails));
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(WithdrawCashResultActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		centerView.setVisibility(View.GONE);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.withdraw_cash_result, bodyParentView);
		changeFonts((ViewGroup) view);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			Intent intent=new Intent(WithdrawCashResultActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
		
			return super.onKeyDown(keyCode, event);
		}
}
