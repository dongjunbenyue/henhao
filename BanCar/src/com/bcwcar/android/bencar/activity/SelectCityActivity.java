package com.bcwcar.android.bencar.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainHome.MainHome;
import com.bcwcar.android.bencar.activity.MainMy.JoinStore4SActivity;
import com.bcwcar.android.bencar.adapter.CityLianDongListAdapater;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.GPS_Positioning;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * 选择城市页
 */
public class SelectCityActivity extends BaseActivity {
	private List<Map<String, String>> list_ProvinceName = new ArrayList<Map<String, String>>();
	private ListView listview_ProvinceName;
	private CityLianDongListAdapater adapter001;
	private String action;
	private LinearLayout line11;
	private TextView cityname, location, location_data;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 101) {
				location_data.setVisibility(View.VISIBLE);
				location_data.setText("城市：" + LocationDataSave.get(SelectCityActivity.this, "CityName") + "     纬度："
						+ LocationDataSave.get(SelectCityActivity.this, "Latitude") + "     经度："
						+ LocationDataSave.get(SelectCityActivity.this, "Longitude"));
				cityname.setText(LocationDataSave.get(SelectCityActivity.this, "CityName"));

			}

		};

	};

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		leftView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		leftView.setText("");
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText(getString(R.string.select_city_title));
		rightView.setText("");
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_select_city, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			action = getIntent().getStringExtra("action");
		} catch (Exception e) {
			// TODO: handle exception
		}
		city_item();
		initview();
		if (action.equals("JoinStore4SActivity")) {
			line11.setVisibility(View.GONE);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		location_data.setVisibility(View.GONE);
	}

	// 实例化控件
	public void initview() {
		location_data = (TextView) findViewById(R.id.TextView_location_data001);
		location_data.setVisibility(View.VISIBLE);
		location = (TextView) findViewById(R.id.TextView_location_refresh001);
		location.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new GPS_Positioning(SelectCityActivity.this, handler).gps();
			}
		});
		cityname = (TextView) findViewById(R.id.select_city_location);
		cityname.setText(LocationDataSave.get(SelectCityActivity.this, "CityName"));
		line11 = (LinearLayout) findViewById(R.id.view_test001);
		cityname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (action.equals("MainHomeFinishActivity")) {
					MainHome.set_city(LocationDataSave.get(SelectCityActivity.this, "CityName"));
				} else if (action.equals("MainHomeActivity")) {
					MainHome.set_city(LocationDataSave.get(SelectCityActivity.this, "CityName"));
				}
				finish();
			}
		});
		listview_ProvinceName = (ListView) findViewById(R.id.city_list);
		adapter001 = new CityLianDongListAdapater(getApplicationContext(), list_ProvinceName);
		listview_ProvinceName.setAdapter(adapter001);
		listview_ProvinceName.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				LocationDataSave.save(SelectCityActivity.this, "ProvinceId",
						list_ProvinceName.get(position).get("ProvinceId").toString());
				LocationDataSave.save(SelectCityActivity.this, "ProvinceName",
						list_ProvinceName.get(position).get("ProvinceName").toString());
				Intent resultIntent = new Intent(SelectCityActivity.this, CitySecondPage.class);
				Bundle bundle = new Bundle();
				bundle.putString("Citys", list_ProvinceName.get(position).get("Citys").toString());
				bundle.putString("action", action);
				resultIntent.putExtras(bundle);
				startActivity(resultIntent);
				finish();
			}
		});
	}

	// 获取城市联动列表
	public void city_item() {
		try {
			InputStream is = getAssets().open("SCQ.txt");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String text = new String(buffer, "utf-8");
			JSONObject object = new JSONObject(text);
			JSONArray array = object.getJSONArray("Data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject object2 = array.getJSONObject(i);
				Map<String, String> map = new HashMap<String, String>();
				map.put("ProvinceId", object2.getString("ProvinceId").toString());
				map.put("ProvinceName", object2.getString("ProvinceName").toString());
				map.put("Citys", object2.getString("Citys").toString());
				list_ProvinceName.add(map);
			}

		} catch (Exception e) {
			// Should never happen!
			throw new RuntimeException(e);
		}

	}

}
