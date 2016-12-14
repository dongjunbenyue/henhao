package com.bcwcar.android.bencar.wxapi;


import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.activity.PayQuanActivity;

import com.bcwcar.android.bencar.activity.PaymentResultOrderDetailActivity;
import com.bcwcar.android.bencar.activity.PaymentResultOrderDetailActivity1;
import com.bcwcar.android.bencar.activity.MainMy.Balance;
import com.bcwcar.android.bencar.activity.MainMy.MyWalletCouponActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.biz.HttpWallet;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.QuanDingDanDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.PageUtil;

import com.squareup.okhttp.Request;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	private TextView textView;

	private int responsecode;
    
//    private static final String LOG_TAG = PaymentResultActivity.class.getSimpleName();

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WXPayEntryActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText(getString(R.string.shop4s_maintain_payment_result_title));
		
		rightView.setVisibility(View.GONE);
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
//		 LayoutInflater.from(this).inflate(R.layout.activity_test,
//		 bodyParentView);
		LayoutInflater.from(this).inflate(R.layout.body_shop4s_maintain_payment_result, bodyParentView);
		RelativeLayout button = (RelativeLayout) findViewById(R.id.RelativeLayout_pay_suesscc001);
		textView=(TextView)findViewById(R.id.textjieguo);
		if (responsecode!=0) {
         textView.setText("支付失败");			
		}
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("OrderId",  DingDanDataSave.get(WXPayEntryActivity.this, "OrderId"));
				if (DingDanDataSave.get(getApplicationContext(), "Type").equals("")) {
					PageUtil.jumpTo(WXPayEntryActivity.this, PaymentResultOrderDetailActivity.class, bundle);
				}else {
					PageUtil.jumpTo(WXPayEntryActivity.this, PaymentResultOrderDetailActivity1.class, bundle);
				}
				
			}
		});
		changeFonts(bodyParentView);
	}


	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// LayoutInflater.from(this).inflate(R.layout.common_toolbar_1,
		// toolBarParentView);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		api = WXAPIFactory.createWXAPI(this, ConstantWeiXin.APP_ID);
        api.handleIntent(getIntent(), this);
		super.onCreate(savedInstanceState);
		

	}
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		responsecode=resp.errCode;
		if (resp.errCode==0) {
   if (WithdrawData.getWithdrawData(getApplicationContext(), "ChargeFlag").endsWith("1")) {
	 HttpWallet.confirmrecharge(new CallbackLogic() {
		
		@Override
		public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
			// TODO Auto-generated method stub
		  showToast("充值成功");
		  Intent intent=new Intent(WXPayEntryActivity.this, MainActivity.class);
		  startActivity(intent);
		  finish();
		}
	}, UserLoginStatus.get(WXPayEntryActivity.this, "Token"), WithdrawData.getWithdrawData(getApplicationContext(), "RechargeId"), "1", WXPayEntryActivity.this);  
	
}	else if (WithdrawData.getWithdrawData(getApplicationContext(), "ChargeFlag").endsWith("0")) {
	
	HttpOrder.commitOrder(new CallbackLogic() {
		@Override
		public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
			// TODO Auto-generated method stub
			showToast("支付成功");
			
		}
	}, UserLoginStatus.get(WXPayEntryActivity.this, "Token"), DingDanDataSave.get(WXPayEntryActivity.this, "OrderId"), "1",DingDanDataSave.get(WXPayEntryActivity.this, "Type"), WXPayEntryActivity.this);

}else if (WithdrawData.getWithdrawData(getApplicationContext(), "ChargeFlag").endsWith("2")) {
	HttpWallet.confirmUserTicket(new CallbackLogic() {
		
		@Override
		public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
			// TODO Auto-generated method stub
			showToast("支付成功");
			Intent intent = new Intent(WXPayEntryActivity.this, MyWalletCouponActivity.class);
			intent.putExtra(BizDefineAll.BIZ_ACTION, "PayQuanActivity");
			startActivity(intent);
			finish();
		}
	}, UserLoginStatus.get(WXPayEntryActivity.this, "Token"), QuanDingDanDataSave.get(WXPayEntryActivity.this, "UserTicketId"), "1", WXPayEntryActivity.this);
}		
   
//		
		
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("结果");
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
//		}
	}else {
		showToast("支付失败");
		finish();
	}
		
}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			
			Intent intent=new Intent(WXPayEntryActivity.this, MainActivity.class);
			 startActivity(intent);
			 finish();
		
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
}