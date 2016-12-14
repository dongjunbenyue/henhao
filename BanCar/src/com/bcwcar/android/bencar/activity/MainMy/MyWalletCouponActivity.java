package com.bcwcar.android.bencar.activity.MainMy;


import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.adapter.TicketAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpWallet;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.widget.XlistView.XListView;
import com.bcwcar.android.bencar.widget.XlistView.XListView.IXListViewListener;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.ListView;
import android.widget.TextView;
/**
 * 我的钱包|我的抵用券页
 * 
 */
public class MyWalletCouponActivity extends BaseActivity {
	private static final String LOG_TAG = MyWalletCouponActivity.class.getSimpleName();
	
	private ListView ticketListView;
	private TicketAdapter ticketAdapter;
	
	private  String action;

	private TextView textview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		action=getIntent().getStringExtra(BizDefineAll.BIZ_ACTION);
		ticketListView = (ListView) findViewById(R.id.my_coupon_list);
		textview=(TextView)findViewById(R.id.tishixingxi);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getData();
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
				if (action.equals("PayQuanActivity")) {
					Intent intent=new Intent(MyWalletCouponActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
				else if (action.equals("MainMyActivity")) {
					finish();
				}
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("我的抵用券");
		rightView.setText("抵用券规则");
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyWalletCouponActivity.this, JiFenShuoMing.class);
				startActivity(intent);
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_my_coupon, bodyParentView);
		changeFonts(bodyParentView);
	}

	
	public void onViewClick(View view) {
		switch(view.getId()){
			case R.id.common_title_1_left_view:
				finish();
				break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (action.equals("PayQuanActivity")) {
				Intent intent=new Intent(MyWalletCouponActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
			else if (action.equals("MainMyActivity")) {
				finish();
			}
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
	
	
	
	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
//		LayoutInflater.from(this).inflate(R.layout.common_toolbar_1, toolBarParentView);
	}
	
	private void getData() {
		
		HttpWallet.getUserTicketList(new CallbackLogic002() {
			
			@Override
			public void onNetworkError(Request request, IOException e) {
				
				
			
				
			}
			
			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data,String other) {
				Log.e(LOG_TAG, data.toString());
				
				try {
					
					

					
					JSONArray arrayData=data.getJSONArray("Data");
					final List<Map<String, String>> resultList = CollectionUtil.jsonArrayToListMap(arrayData);
					System.out.println("**"+resultList);
					ticketAdapter=new TicketAdapter(MyWalletCouponActivity.this,resultList);
					ticketListView.setAdapter(ticketAdapter);
					if (resultList.size()==0) {
						textview.setVisibility(View.VISIBLE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			
			
			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				showToast(responseDescription);
				if (responseCode.equals("101")||responseCode.equals("102")||responseCode.equals("103")) {
					PageUtil.jumpTo(MyWalletCouponActivity.this, LoginActivity.class);
				}
				return;

			}
			
		}, UserLoginStatus.get(MyWalletCouponActivity.this, "Token"), "","");
	}

	
	
}