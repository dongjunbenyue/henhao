package com.bcwcar.android.bencar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainHome.MainHome;
import com.bcwcar.android.bencar.activity.MainMy.JoinStore4SActivity;
import com.bcwcar.android.bencar.adapter.CityLianDongListAdapater2;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.SysApplication;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CitySecondPage extends BaseActivity {
	private ListView listview_city;
	private CityLianDongListAdapater2 adapater;
	private List<Map<String, String>> list_city = new ArrayList<Map<String, String>>();
	private String action;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			action = getIntent().getStringExtra("action");
			list_city();
		} catch (Exception e) {
			// TODO: handle exception
		}
		listview_city = (ListView) findViewById(R.id.ListView_city_secong_page_xml);
		adapater = new CityLianDongListAdapater2(getApplicationContext(), list_city);
		listview_city.setAdapter(adapater);
		listview_city.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
					
				
				if (list_city.get(position).get("CityName").toString()
						.equals(getString(R.string.text_moren_cityname))) {
					
					LocationDataSave.save(CitySecondPage.this, "CityId",
							list_city.get(position).get("CityId").toString());
					LocationDataSave.save(CitySecondPage.this, "CityName",
							list_city.get(position).get("CityName").toString());
					if (action.equals("MainHomeFinishActivity")) {
						MainHome.set_city(LocationDataSave.get(CitySecondPage.this, "CityName"));
					} else if (action.equals("MainHomeActivity")) {
						MainHome.set_city(LocationDataSave.get(CitySecondPage.this, "CityName"));
					}else if (action.equals("JoinStore4SActivity")) {
						UserLoginStatus.save(CitySecondPage.this, "CityId", list_city.get(position).get("CityId").toString());
						UserLoginStatus.save(CitySecondPage.this, "CityName", list_city.get(position).get("CityName").toString());
					    JoinStore4SActivity.set_city(list_city.get(position).get("CityName").toString());
					}
					finish();
				} else {
					
					if (action.equals("JoinStore4SActivity")) {
						 UserLoginStatus.save(CitySecondPage.this, "CityId", list_city.get(position).get("CityId").toString());
						 UserLoginStatus.save(CitySecondPage.this, "CityName", list_city.get(position).get("CityName").toString());
					 JoinStore4SActivity.set_city(list_city.get(position).get("CityName").toString());
					}else {
						showToast(getString(R.string.text_city_service_no));
					}
					finish();
					// set_city_data("CityId",
					// list_city.get(position).get("CityId").toString());
					// set_city_data("CityName",
					// list_city.get(position).get("CityName").toString());
					// MainHomeActivity.set_city(list_city.get(position).get("CityName").toString());
					// MainServiceNetworkActivity.refesh();
					// finish();
				}
				 
			}
		});
	}

	// 省份-------城市
	public void list_city() {
		try {

			String citylist = getIntent().getStringExtra("Citys").toString();
			JSONArray array = new JSONArray(citylist);
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);

				Map<String, String> map = new HashMap<String, String>();
				map.put("CityId", object.getString("CityId").toString());
				map.put("CityName", object.getString("CityName").toString());
				if (object.isNull("Districts")) {

				} else {
					map.put("Districts", object.getString("Districts").toString());

				}
				list_city.add(map);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		leftView.setText("");
		leftView.setHeight(20);
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		leftView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				showToast("nihao");
				return true;
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText(getString(R.string.select_city_title));
		rightView.setText("");
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.city_second_page_xml, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

}
