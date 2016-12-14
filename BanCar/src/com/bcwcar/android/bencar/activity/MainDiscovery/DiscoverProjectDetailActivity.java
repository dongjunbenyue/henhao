package com.bcwcar.android.bencar.activity.MainDiscovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.adapter.DiscoverItemDetailAdapter;
import com.bcwcar.android.bencar.adapter.discoverChoiceAdapter;
import com.bcwcar.android.bencar.adapter.mainShopServiceAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.GPS_Positioning;
import com.bcwcar.android.bencar.biz.HttpDiscover;
import com.bcwcar.android.bencar.biz.HttpMainServiceNetwork;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.OnlyOneDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.widget.XlistView.XListView;
import com.bcwcar.android.bencar.widget.XlistView.XListView.IXListViewListener;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DiscoverProjectDetailActivity extends BaseActivity implements IXListViewListener {

	// 刷选
	private String[] item = { "全部", "钣金喷漆", "镀金镀膜", "室内美容", "汽车贴膜", "抛光打蜡", "汽车精品", "抵用券", "其他" };
	private String[] smart = { "智能排序", "好评优先", "人气最高", "价格最低", "最新发布" };
	private LinearLayout three_choice_main, gone_up;
	private RelativeLayout gone_thre_listview;
	private RelativeLayout main_ref001, main_ref002, main_ref003;
	private TextView xx001, xx002, xx003;
	private ListView main_ll001, main_ll002, main_ll003;
	private discoverChoiceAdapter adapter001, adapter002, adapter003;
	private List<Map<String, String>> list_cc001 = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_cc002 = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_cc003 = new ArrayList<Map<String, String>>();
	// ============主页listview=-==
	private static List<Map<String, String>> mListViewData = new ArrayList<Map<String, String>>();
	private static XListView mainListView;
	private static DiscoverItemDetailAdapter adapter;
	private int PageNum = 1, TotalCount;
	private String reString001 = "0", reString002 = "", reString003 = "1";
	// =========
	private static Activity activity;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new GPS_Positioning(DiscoverProjectDetailActivity.this, handler).gps();
		reString001=getIntent().getStringExtra("MainDiscovery").toString();
		mListViewData.clear();
		activity = DiscoverProjectDetailActivity.this;
		test();
		initview();
		getCityAra();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refesh_view(0);
	}

	// 实例化
	public void initview() {
		// 刷选
		xx001 = (TextView) findViewById(R.id.TextView_xx_001);
		xx001.setText(list_cc001.get(Integer.parseInt(reString001)).get("DistrictName").toString());
		xx002 = (TextView) findViewById(R.id.TextView_xx_002);
		xx003 = (TextView) findViewById(R.id.TextView_xx_003);
		gone_thre_listview = (RelativeLayout) findViewById(R.id.RelativeLayout_gone001);
		gone_thre_listview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refesh_view(0);
			}
		});
		gone_up = (LinearLayout) findViewById(R.id.LinearLayout_TextView_gone001);
		gone_up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refesh_view(0);
			}
		});
		three_choice_main = (LinearLayout) findViewById(R.id.LinearLayout_three_listview_choice001);
		main_ref001 = (RelativeLayout) findViewById(R.id.RelativeLayout_top_shaxuan001);
		main_ref002 = (RelativeLayout) findViewById(R.id.RelativeLayout_top_shaxuan002);
		main_ref003 = (RelativeLayout) findViewById(R.id.RelativeLayout_top_shaxuan003);
		main_ll001 = (ListView) findViewById(R.id.ListView_top_project001);
		main_ll002 = (ListView) findViewById(R.id.ListView_top_project002);
		main_ll003 = (ListView) findViewById(R.id.ListView_top_project003);
		main_ll001.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				reString001 = list_cc001.get(position).get("DistrictId").toString();
				xx001.setText(list_cc001.get(position).get("DistrictName").toString());
				refresh_data_topview(1, position);
				refesh_view(0);
				mListViewData.clear();
				refeshgetshopservicelist(1, reString001, reString002, reString003);
			}
		});
		main_ll002.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				reString002 = list_cc002.get(position).get("DistrictId").toString();
				xx002.setText(list_cc002.get(position).get("DistrictName").toString());
				refresh_data_topview(2, position);
				refesh_view(0);
				mListViewData.clear();
				refeshgetshopservicelist(1, reString001, reString002, reString003);
			}
		});
		main_ll003.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				reString003 = list_cc003.get(position).get("DistrictId").toString();
				xx003.setText(list_cc003.get(position).get("DistrictName").toString());
				refresh_data_topview(3, position);
				refesh_view(0);
				mListViewData.clear();
				refeshgetshopservicelist(1, reString001, reString002, reString003);
			}
		});
		main_ref001.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refesh_view(1);
			}
		});
		main_ref002.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refesh_view(2);
			}
		});
		main_ref003.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refesh_view(3);
			}
		});
		// ========主listview
		mainListView = (XListView) findViewById(R.id.ListView_top_project_detail001);
		mainListView.setXListViewListener(this);
		mainListView.setPullLoadEnable(true);
		mainListView.setPullRefreshEnable(true);

	}

	// 刷新店的数据 是否展开
	public static void refresh_data(int postion) {
		if (mListViewData.get(postion).get("zhizhen").equals("0")) {
			for (int i = 0; i < mListViewData.size(); i++) {
				mListViewData.get(i).put("zhizhen", "0");
			}
			mListViewData.get(postion).put("zhizhen", "1");
		} else {

			for (int i = 0; i < mListViewData.size(); i++) {
				mListViewData.get(i).put("zhizhen", "0");
			}
		}
		adapter.notifyDataSetChanged();
	}

	// 刷新店的数据 是否展开
	public void refresh_data_topview(int listnum, int position) {
		if (listnum == 1) {

			for (int i = 0; i < list_cc001.size(); i++) {
				list_cc001.get(i).put("zhizhen", "0");
			}
			list_cc001.get(position).put("zhizhen", "1");
			adapter001.notifyDataSetChanged();
		} else if (listnum == 2) {

			for (int i = 0; i < list_cc002.size(); i++) {
				list_cc002.get(i).put("zhizhen", "0");
			}
			list_cc002.get(position).put("zhizhen", "1");
			adapter002.notifyDataSetChanged();
		} else if (listnum == 3) {

			for (int i = 0; i < list_cc003.size(); i++) {
				list_cc003.get(i).put("zhizhen", "0");
			}
			list_cc003.get(position).put("zhizhen", "1");
			adapter003.notifyDataSetChanged();
		}

	}

	// 测试数据
	public void test() {
		for (int i = 0; i < item.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("DistrictName", item[i]);
			map.put("DistrictId", i + "");
			if (reString001.equals(i+"")) {
				map.put("zhizhen", "1");
			} else {
				map.put("zhizhen", "0");
			}
			list_cc001.add(map);
		}
		for (int i = 0; i < smart.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("DistrictName", smart[i]);
			map.put("DistrictId", (i + 1) + "");
			if (i == 0) {
				map.put("zhizhen", "1");
			} else {
				map.put("zhizhen", "0");
			}
			list_cc003.add(map);
		}
	}

	// 获取城市的区域list
	public void getCityAra() {
		HttpMainServiceNetwork.getDistrictInfo(new CallbackLogic() {

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					JSONArray array = data.getJSONArray("Data");
					Map<String, String> map = new HashMap<String, String>();
					map.put("DistrictName", "全部");
					map.put("zhizhen", "1");
					map.put("DistrictId", "");
					list_cc002.add(map);
					for (int i = 0; i < array.length(); i++) {
						Map<String, String> map001 = new HashMap<String, String>();
						map001 = CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i));
						map001.put("zhizhen", "0");
						list_cc002.add(map001);
					}
					adapter001 = new discoverChoiceAdapter(getApplicationContext(), list_cc001);
					adapter002 = new discoverChoiceAdapter(getApplicationContext(), list_cc002);
					adapter003 = new discoverChoiceAdapter(getApplicationContext(), list_cc003);
					main_ll001.setAdapter(adapter001);
					main_ll002.setAdapter(adapter002);
					main_ll003.setAdapter(adapter003);
					setListViewHeight(main_ll001);
					setListViewHeight(main_ll002);
					setListViewHeight(main_ll003);
					getshopservicelist(reString001, reString002, reString003);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}, UserLoginStatus.get(DiscoverProjectDetailActivity.this, "Token"),
				LocationDataSave.get(DiscoverProjectDetailActivity.this, "CityId"),
				LocationDataSave.get(DiscoverProjectDetailActivity.this, "CityName"),
				DiscoverProjectDetailActivity.this);

	}

	// 获取增值服务网点列表 TODO
	public void getshopservicelist(String ServiceType, String DistrictId, String SortType) {
		HttpDiscover.getServiceShopList(new CallbackLogic() {

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					JSONArray array = data.getJSONArray("Data");
					if (array.length()==0) {
						mainListView.set_booter_text("没有更多数据");
					}else if (array.length()<=10) {
						mainListView.set_booter_gone();
						mainListView.stopLoadMore();
						mainListView.setPullLoadEnable(false);
					}else {
						mainListView.set_booter_text("查看更多");
						mainListView.setPullLoadEnable(true);
					}
					for (int i = 0; i < array.length(); i++) {
						Map<String, String> map = new HashMap<String, String>();
						map = CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i));
						map.put("zhizhen", "0");
						mListViewData.add(map);
					}
					adapter = new DiscoverItemDetailAdapter(getApplicationContext(), mListViewData, activity);
					mainListView.setAdapter(adapter);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}, UserLoginStatus.get(DiscoverProjectDetailActivity.this, "Token"), "1", "10",
				"", DistrictId,
				LocationDataSave.get(DiscoverProjectDetailActivity.this, "CityId"),
				LocationDataSave.get(DiscoverProjectDetailActivity.this, "CityName"), "", SortType, "1",
				LocationDataSave.get(DiscoverProjectDetailActivity.this, "Longitude"),
				LocationDataSave.get(DiscoverProjectDetailActivity.this, "Latitude"), ServiceType,
				DiscoverProjectDetailActivity.this);

	}

	// 刷新增值服务网点列表
	public void refeshgetshopservicelist(int pagenum, String ServiceType, String DistrictId, String SortType) {
		HttpDiscover.getServiceShopList(new CallbackLogic() {

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					TotalCount = Integer.parseInt(alldata.getString("TotalCount").toString());
					JSONArray array = data.getJSONArray("Data");
					if (array.length()==0) {
						mainListView.set_booter_text("没有更多数据");
					}else if (array.length()<=10) {
						mainListView.set_booter_gone();
						mainListView.stopLoadMore();
						mainListView.setPullLoadEnable(false);
					}else {
						mainListView.set_booter_text("查看更多");
						mainListView.setPullLoadEnable(true);
					}
					for (int i = 0; i < array.length(); i++) {
						Map<String, String> map = new HashMap<String, String>();
						map = CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i));
						map.put("zhizhen", "0");
						mListViewData.add(map);
					}
					mainListView.stopRefresh();
					mainListView.stopLoadMore();
					adapter.notifyDataSetChanged();
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}, UserLoginStatus.get(DiscoverProjectDetailActivity.this, "Token"), pagenum + "", "10",
				"", DistrictId,
				LocationDataSave.get(DiscoverProjectDetailActivity.this, "CityId"),
				LocationDataSave.get(DiscoverProjectDetailActivity.this, "CityName"), "", SortType, "1",
				LocationDataSave.get(DiscoverProjectDetailActivity.this, "Longitude"),
				LocationDataSave.get(DiscoverProjectDetailActivity.this, "Latitude"), ServiceType,
				DiscoverProjectDetailActivity.this);
	}

	// 刷新刷选条件的三个listview
	public void refesh_view(int tt) {
		if (tt == 1) {
			main_ll001.setVisibility(View.VISIBLE);
			main_ll002.setVisibility(View.GONE);
			main_ll003.setVisibility(View.GONE);
			three_choice_main.setVisibility(View.VISIBLE);
			xx001.setTextColor(getResources().getColor(R.color.mainblue));
			xx002.setTextColor(getResources().getColor(R.color.inputblack));
			xx003.setTextColor(getResources().getColor(R.color.inputblack));
		} else if (tt == 2) {
			main_ll001.setVisibility(View.GONE);
			main_ll002.setVisibility(View.VISIBLE);
			main_ll003.setVisibility(View.GONE);
			three_choice_main.setVisibility(View.VISIBLE);
			xx001.setTextColor(getResources().getColor(R.color.inputblack));
			xx002.setTextColor(getResources().getColor(R.color.mainblue));
			xx003.setTextColor(getResources().getColor(R.color.inputblack));
		} else if (tt == 3) {
			main_ll001.setVisibility(View.GONE);
			main_ll002.setVisibility(View.GONE);
			main_ll003.setVisibility(View.VISIBLE);
			three_choice_main.setVisibility(View.VISIBLE);
			xx001.setTextColor(getResources().getColor(R.color.inputblack));
			xx002.setTextColor(getResources().getColor(R.color.inputblack));
			xx003.setTextColor(getResources().getColor(R.color.mainblue));
		} else {
			main_ll001.setVisibility(View.GONE);
			main_ll002.setVisibility(View.GONE);
			main_ll003.setVisibility(View.GONE);
			three_choice_main.setVisibility(View.GONE);
			xx001.setTextColor(getResources().getColor(R.color.inputblack));
			xx002.setTextColor(getResources().getColor(R.color.inputblack));
			xx003.setTextColor(getResources().getColor(R.color.inputblack));
		}

	}

	// =============
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
		centerView.setText("分类列表");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		rightView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DiscoverProjectDetailActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.discover_item_show_detail, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mListViewData.clear();
		GPS_Positioning gps_Positioning=new GPS_Positioning(DiscoverProjectDetailActivity.this, handler);
		PageNum = 1;
		mainListView.set_booter_text("查看更多");
		refeshgetshopservicelist(PageNum, reString001, reString002, reString003);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (PageNum * 10 >= TotalCount) {
			mainListView.set_booter_text("没有更多数据");
		} else {
			mainListView.set_booter_text("查看更多");
			PageNum++;
			refeshgetshopservicelist(PageNum, reString001, reString002, reString003);
		}
	}

}
