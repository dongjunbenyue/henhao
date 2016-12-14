package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.R.id;
import com.bcwcar.android.bencar.activity.BankCarActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpWallet;
import com.bcwcar.android.bencar.datasave.UserBnakCardListData;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.datasave.UserWalletListData;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.StringUtil;
import com.squareup.okhttp.Request;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * withdraw提现
 * */
public class WithdrawalsActivity extends BaseActivity{
private RelativeLayout bank_card_RelativeLayout1,sum_cash_RelativeLayout;
private static TextView bank_card_TextView;
private TextView next_page_TextView;
private TextView sum_cash_TextView;
private EditText withdarwedit;
private LinearLayout bank_card_RelativeLayout;
private static Map<String, String> map_getinfo = new HashMap<String, String>();//银行mamap
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		map_getinfo=UserBnakCardListData.getDefaultBankCard(getApplicationContext());
		initview();
	}
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			initdata();
		}
	//实例化控件
	public void initview(){
		bank_card_RelativeLayout=(LinearLayout)findViewById(R.id.bank_card_RelativeLayout);
		bank_card_RelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(WithdrawalsActivity.this,BankCarActivity.class);
				intent.putExtra(BizDefineAll.BIZ_ACTION, "WithdrawalsActivity");
				startActivity(intent);
				
			}
		});
		
		bank_card_TextView=(TextView)findViewById(R.id.TextView_withdraw_cash_bankcard001);
		if (map_getinfo.containsKey("BankNo")&&map_getinfo.containsKey("BankName")) {
			String str=map_getinfo.get("BankNo").toString();
			bank_card_TextView.setText(map_getinfo.get("BankName").toString()+"("+str.substring(str.length()-4,str.length())+")");
		}
		withdarwedit=(EditText)findViewById(R.id.tixianjine);
		sum_cash_TextView=(TextView)findViewById(R.id.EditText_withdraw_cash_sumcash001);
		next_page_TextView=(TextView)findViewById(R.id.TextView_withdraw_cash_next001);
		next_page_TextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String cash_num=withdarwedit.getText().toString();
				for (String key : map_getinfo.keySet()) {
				WithdrawData.setWithdrawData(getApplicationContext(), key, map_getinfo.get(key));
				}
				WithdrawData.setWithdrawData(getApplicationContext(), "CashSum",cash_num);
				if (StringUtil.isEmpty(cash_num)) {
					showToast(getString(R.string.text_cashsum_no));
					return;
				}
				if (StringUtil.string_to_int(cash_num)<100) {
					showToast("提现金额必须大于100");
					return;
				}
				if (StringUtil.isEmpty(bank_card_TextView.getText().toString())) {
					showToast(getString(R.string.text_choice_bank));
					return;
				}
				int cash_num_int=Integer.parseInt(cash_num);
				if (cash_num_int==0) {
					showToast(getString(R.string.text_cashsum_no));
					return;
				}
				if (cash_num_int>StringUtil.string_to_int(UserWalletListData.getWalletItemData(getApplicationContext(), "Wallet"))) {
					showToast(getString(R.string.text_cashsum_enough));
				}else {
					String string=UserLoginStatus.get(WithdrawalsActivity.this, "PayFlag");
					if (string.equals("1")){//设置了支付密码
						Intent intent=new Intent(WithdrawalsActivity.this,WithdarwSettingsCashPassword.class);
						intent.putExtra("action", "pay");
						startActivity(intent);
					}else{//没有设置支付密码
						Intent intent=new Intent(WithdrawalsActivity.this,WithdrawCashPasswordPhone.class);
						startActivity(intent);
					}
				}
			}
		});
	}
	
//	//获取用户钱包列表
//	public void getUserWalletList(){
//		Dialogshow(WithdrawalsActivity.this, 0);
//		HttpWallet.getUserWalletList(new CallbackLogic() {
//			
//			@Override
//			public void onNetworkError(Request request, IOException e) {
//				// TODO Auto-generated method stub
//				Dialogcancel();
//				showToast(getString(R.string.network_error_please_try_again));
//			}
//			
//			@Override
//			public void onBizSuccess(String responseDescription, JSONObject data, String all_data) {
//				// TODO Auto-generated method stub
//				Dialogcancel();
//				UserWalletListData.save(getApplicationContext(), data.toString());
//				System.out.println("Cash="+UserWalletListData.getWalletItemData(getApplicationContext(), "Cash"));
//				int tt=StringUtil.string_to_int(UserWalletListData.getWalletItemData(getApplicationContext(), "Cash"));
//				System.out.println("tt="+tt);
//				sum_cash_TextView.setHint(getString(R.string.text_Current_balance)+tt);
//			}
//			
//			@Override
//			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
//				// TODO Auto-generated method stub
//				Dialogcancel();
//				showToast(responseDescription);
//				denglu_reset(responseCode, responseDescription, WithdrawalsActivity.this);
//			}
//		}, UserInfoData.getUserInfo(getApplicationContext(), "Token"), "1", "10", "2016-04");
//		
//	}
//	
	public void initdata(){
HttpWallet.getUserExp(new CallbackLogic() {
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				UserWalletListData.save(getApplicationContext(), data.toString());
				System.out.println("Cash="+UserWalletListData.getWalletItemData(getApplicationContext(), "Wallet"));
				
				sum_cash_TextView.setText(UserWalletListData.getWalletItemData(getApplicationContext(), "Wallet"));
			}
		}, UserLoginStatus.get(WithdrawalsActivity.this,"Token"), WithdrawalsActivity.this);
		
	};
	//得到用户选择的银行卡的相关信息
	public static void get_bank_info(Map<String, String> map) {
				map_getinfo = map;
				if (map_getinfo.containsKey("BankNo")&&map_getinfo.containsKey("BankName")) {
					String str=map_getinfo.get("BankNo").toString();
					bank_card_TextView.setText(map_getinfo.get("BankName").toString()+"(尾数"+str.substring(str.length()-4,str.length())+")");
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
		centerView.setText("零钱提现");
		rightView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(WithdrawalsActivity.this,JinEShuoMing.class);
				startActivity(intent);
			}
		});
		rightView.setVisibility(View.GONE);
		
		leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.withdraw, bodyParentView);
		changeFonts((ViewGroup) view);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView){
		// TODO Auto-generated method stub
		
	}

}
