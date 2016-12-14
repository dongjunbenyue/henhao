package com.bcwcar.android.bencar.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.activity.ShopQuanQuitActivity;
import com.bcwcar.android.bencar.activity.StoreServiceNetworkActivity;
import com.bcwcar.android.bencar.activity.WangDianDatas;
import com.bcwcar.android.bencar.activity.MainDiscovery.DiscoverProjectDetailActivity;
import com.bcwcar.android.bencar.activity.MainDiscovery.MainDiscovery;
import com.bcwcar.android.bencar.activity.MainHome.MainHome;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpDiscover;
import com.bcwcar.android.bencar.biz.HttpMainServiceNetwork;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.QuanDingDanDataSave;
import com.bcwcar.android.bencar.datasave.StoreToDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.NumberFormatTest;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.widget.Distance;
import com.facebook.drawee.view.SimpleDraweeView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 服务网点list的适配器
 */
public class DiscoverItemDetailAdapter extends BaseAdapter {
	private List<Map<String, String>> list;
	private Context context;
	private Activity activity;

	public DiscoverItemDetailAdapter(Context context, List<Map<String, String>> list, Activity activity) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wangdianlistdiscovery, parent,
					false);
		}
		final List<Map<String, String>> mListViewData = new ArrayList<Map<String, String>>();
		
		try {
			JSONArray array = new JSONArray(list.get(position).get("ServiceData").toString());
			for (int i = 0; i < array.length(); i++) {
				mListViewData.add(CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i)));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LinearLayout shop_data = (LinearLayout) convertView.findViewById(R.id.LinearLayout_shop_data_show001);
		shop_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DingDanDataSave.save(activity, "ShopId", list.get(position).get("ShopId").toString());
				Intent intent = new Intent(activity, WangDianDatas.class);
				intent.putExtra(BizDefineAll.BIZ_ACTION, "DiscoverProjectDetailActivity");
				activity.startActivity(intent);
			}
		});
		TextView shopname = (TextView) convertView.findViewById(R.id.TextView_disconery_shop_name001);
		TextView distance = (TextView) convertView.findViewById(R.id.TextView_shop_distance_num001);
		TextView address = (TextView) convertView.findViewById(R.id.TextView_shop_address_num001);
		RatingBar starnum = (RatingBar) convertView.findViewById(R.id.RatingBar_discover_main_star_num001);
		// 4s店信息
		shopname.setText(list.get(position).get("ShopName").toString());
		distance.setText(new Distance().getDistance(LocationDataSave.get(context, "Longitude"), LocationDataSave.get(context, "Latitude"), list.get(position).get("Longitude").toString(), list.get(position).get("Latitude").toString())+"km");
		address.setText(list.get(position).get("Address").toString());
		starnum.setRating(Integer.parseInt(list.get(position).get("Score").toString()));

		TextView num = (TextView) convertView.findViewById(R.id.TextView_num_text001);
		LinearLayout more_project = (LinearLayout) convertView.findViewById(R.id.LinearLayout_more_project001);
		LinearLayout more_project_hanzi = (LinearLayout) convertView
				.findViewById(R.id.LinearLayout_more_project_text_hanzi001);
		final ListView mListView = (ListView) convertView.findViewById(R.id.ListView_discover_item_project001);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				// TODO Auto-generated method stub
				for (String key : mListViewData.get(pos).keySet()) {
					QuanDingDanDataSave.save(activity, key, mListViewData.get(pos).get(key));
				}
				for(String key:list.get(position).keySet()){
					QuanDingDanDataSave.save(activity, key,list.get(position).get(key));
				}
				getshopdata(mListViewData.get(pos).get("AccId").toString(), LocationDataSave.get(activity, "Longitude"), LocationDataSave.get(activity, "Latitude"));
			}
		});
		ImageView up_down = (ImageView) convertView.findViewById(R.id.ImageView_up_or_down001);
		more_project.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DiscoverProjectDetailActivity.refresh_data(position);
			}
		});
		if (mListViewData.size() <= 2) {

			mainShopServiceItemAdapter adapter = new mainShopServiceItemAdapter(context, mListViewData);
			mListView.setAdapter(adapter);
			more_project.setVisibility(View.GONE);
		} else {
			if (list.get(position).get("zhizhen").equals("1")) {

				mainShopServiceItemAdapter adapter = new mainShopServiceItemAdapter(context, mListViewData);
				mListView.setAdapter(adapter);
				up_down.setBackgroundResource(R.drawable.up);
				more_project_hanzi.setVisibility(View.GONE);
			} else {
				final List<Map<String, String>> mListViewData002 = new ArrayList<Map<String, String>>();
				mListViewData002.add(mListViewData.get(0));
				mListViewData002.add(mListViewData.get(1));
				mainShopServiceItemAdapter adapter = new mainShopServiceItemAdapter(context, mListViewData002);
				mListView.setAdapter(adapter);
				up_down.setBackgroundResource(R.drawable.down);
				more_project_hanzi.setVisibility(View.VISIBLE);
			}
			more_project.setVisibility(View.VISIBLE);
			num.setText(new NumberFormatTest().foematInteger(mListViewData.size() - 2) + "");
		}
		BaseActivity.setListViewHeight(mListView);
		return convertView;
	}
	// 获取商店信息
	public void getshopdata(String ShopId) {
		HttpMainServiceNetwork.getShopDetails(new CallbackLogic() {

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					JSONObject object = data.getJSONObject("Data");
					Map<String, String> map = CollectionUtil.jsonObjectToMap_String(object);
					for (String key : map.keySet()) {
						QuanDingDanDataSave.save(activity, key, map.get(key));
					}
					PageUtil.jumpTo(activity, ShopQuanQuitActivity.class);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}, UserLoginStatus.get(activity, "Token"), ShopId, activity);

	}
	// 获取商店信息
			public void getshopdata(String accid,String lon,String lan) {
				HttpDiscover.getServiceDetail(new CallbackLogic() {

					@Override
					public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
						// TODO Auto-generated method stub
						try {
							JSONObject object = data.getJSONObject("Data");
							System.out.println("serview"+object.toString());
							Map<String, String> map = CollectionUtil.jsonObjectToMap_String(object);
							for (String key : map.keySet()) {
								QuanDingDanDataSave.save(activity, key, map.get(key));
							}
							PageUtil.jumpTo(activity, ShopQuanQuitActivity.class);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}, UserLoginStatus.get(activity, "Token"), accid, lon, lan, activity);

			}
}