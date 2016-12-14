package com.bcwcar.android.bencar.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainMy.ConfermOrder;
import com.bcwcar.android.bencar.adapter.TicketDingDanAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpWallet;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.QuanDingDanDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.squareup.okhttp.Request;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TicketListActivity extends BaseActivity{
private ListView mlistview;
private TicketDingDanAdapter ticketAdapter;
private List<Map<String, String>> resultList =new ArrayList<Map<String, String>>();
private TextView no_ticket;
private String action;
private String PaySum,ShopId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			action=getIntent().getStringExtra("action");
			PaySum=getIntent().getStringExtra("PaySum");
			if (action.equals("FillInOrderActivity")) {
				ShopId=DingDanDataSave.get(TicketListActivity.this, "ShopId");
			}else {
				ShopId=QuanDingDanDataSave.get(TicketListActivity.this, "ShopId");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		no_ticket=(TextView)findViewById(R.id.TextView_no_ticket001);
		
		mlistview=(ListView)findViewById(R.id.ListView_ticket_listview001);
		mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (action.equals("FillInOrderActivity")) {
					for (String key:resultList.get(position).keySet()) {
						DingDanDataSave.save(TicketListActivity.this, key, resultList.get(position).get(key));
					}
					FillInOrderActivity.refresh_ticket();
					finish();
				}else {
					for (String key:resultList.get(position).keySet()) {
						QuanDingDanDataSave.save(TicketListActivity.this, key, resultList.get(position).get(key));
					}
					ConfermOrder.refresh_ticket();
					finish();
				}
				
			}
		});
		getData();
	}
	//获取用户可用抵用券
	private void getData() {
		
		HttpWallet.getUserTicketList(new CallbackLogic002() {
			@Override
			public void onNetworkError(Request request, IOException e) {
			}
			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data,String other) {
				
				try {
					JSONArray arrayData=data.getJSONArray("Data");
					resultList = CollectionUtil.jsonArrayToListMap(arrayData);
					if (resultList.size()==0) {
						no_ticket.setVisibility(View.VISIBLE);
					}else {
						for (String key:resultList.get(0).keySet()) {
							DingDanDataSave.save(TicketListActivity.this, key, "");
							QuanDingDanDataSave.save(TicketListActivity.this, key,"");
						}
						no_ticket.setVisibility(View.GONE);
					}
					ticketAdapter=new TicketDingDanAdapter(TicketListActivity.this,resultList);
					mlistview.setAdapter(ticketAdapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				
				return;

			}
			
		}, UserLoginStatus.get(TicketListActivity.this, "Token"),ShopId,PaySum);
	}

	
	//=====================
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
	rightView.setVisibility(View.GONE);
	leftView.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
	centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
	centerView.setText("可用抵用券");
	rightView.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(TicketListActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	});
	changeFonts((ViewGroup)rootView);
}
@Override
public void setPageBody(ViewGroup bodyParentView) {
	
	LayoutInflater.from(this).inflate(R.layout.ticket_list_xml, bodyParentView);
	changeFonts(bodyParentView);
	
}
@Override
public void setPageToolBar(ViewGroup toolBarParentView) {
	
}
	
	
}
