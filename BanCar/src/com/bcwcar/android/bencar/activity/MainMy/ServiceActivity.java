package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.adapter.ServiceAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ServiceActivity extends BaseActivity {
	String OrderId;
	ListView listView;
	List<Map<String, String>> mlistdata;
	private String Type;

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
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
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("订单跟踪");
		rightView.setVisibility(View.GONE);
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		View pageView = LayoutInflater.from(this).inflate(R.layout.activity_service, bodyParentView);
		listView = (ListView) pageView.findViewById(R.id.serviceListview);
		OrderId = getIntent().getStringExtra("OrderId");
		init();
   changeFonts(bodyParentView);
   
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

	
	public void onViewClick(View view) {
		// TODO Auto-generated method stub

	}

	public void init() {
		Type=DingDanDataSave.get(getApplicationContext(), "Type");
	
		HttpOrder.getService(new CallbackLogic() {

			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					
					JSONArray jsonArray = data.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA);
					mlistdata = CollectionUtil.jsonArrayToListMap(jsonArray);
					ServiceAdapter adapter = new ServiceAdapter(getApplicationContext(), mlistdata);
					listView.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}, UserLoginStatus.get(ServiceActivity.this,"Token"), OrderId,ServiceActivity.this,Type);
	String shopname1=getIntent().getStringExtra("ShopName");
	String shopadress1=getIntent().getStringExtra("ShopAddress");
	String shopurl=getIntent().getStringExtra("LogoUrl");
	SimpleDraweeView logo=(SimpleDraweeView)findViewById(R.id.wangdian_logo0011);
	TextView shopname=(TextView)findViewById(R.id.serviceshopname);
	TextView shopadress=(TextView)findViewById(R.id.shopadress);
	ResourceUtil.setSimpleDraweeViewImage(logo, shopurl);
	shopadress.setText(shopadress1);
	shopname.setText(shopname1);
	
	}

}
