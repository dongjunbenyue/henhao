package com.bcwcar.android.bencar.activity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainMy.MyOrderActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.squareup.okhttp.Request;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
 * 付款结果页
 */
public class PaymentResultActivity extends BaseActivity {
	private static final String LOG_TAG = PaymentResultActivity.class.getSimpleName();
	private String OrderId = null;

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
		
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PaymentResultActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText(getString(R.string.shop4s_maintain_payment_result_title));
		
		rightView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			MainActivity.sethome() ;
				Intent intent = new Intent(PaymentResultActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_shop4s_maintain_payment_result, bodyParentView);
		RelativeLayout button = (RelativeLayout) findViewById(R.id.RelativeLayout_pay_suesscc001);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("OrderId", getIntent().getStringExtra("OrderId"));
				if (DingDanDataSave.get(getApplicationContext(), "Type").equals("")) {
					PageUtil.jumpTo(PaymentResultActivity.this, PaymentResultOrderDetailActivity.class, bundle);
				}else {
					PageUtil.jumpTo(PaymentResultActivity.this, PaymentResultOrderDetailActivity1.class, bundle);
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
		OrderId = getIntent().getStringExtra("OrderId");
		super.onCreate(savedInstanceState);
		
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				Intent intent=new Intent(PaymentResultActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
}