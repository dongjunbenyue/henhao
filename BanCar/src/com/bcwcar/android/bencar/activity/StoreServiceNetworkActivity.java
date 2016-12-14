package com.bcwcar.android.bencar.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.adapter.NoServiceShopAdapter;
import com.bcwcar.android.bencar.adapter.ServiceNetworkAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpMainServiceNetwork;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.StoreToDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.widget.XlistView.XListView;
import com.bcwcar.android.bencar.widget.XlistView.XListView.IXListViewListener;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

/**
 * 管家服务网点页
 */
public class StoreServiceNetworkActivity extends BaseActivity implements IXListViewListener {
	private static final String LOG_TAG = StoreServiceNetworkActivity.class.getSimpleName();
private int TotalCount=0;
private int currentPage=1;
	private XListView mListView;
	private static List<Map<String, String>> mListViewData = new ArrayList<Map<String, String>>();
private NoServiceShopAdapter adapter;
private  static Activity activity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mListViewData.clear();
		activity=StoreServiceNetworkActivity.this;
		 initview();
		 getshoplist();
	}
	//实例化控件
	public void initview(){
		mListView = (XListView) findViewById(R.id.service_network_list);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);
		
	}
	//获取商店列表
	public void getshoplist(){
		HttpMainServiceNetwork.getShopList(new CallbackLogic() {
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				
				System.out.println("==========="+alldata);
				try {
					TotalCount=Integer.parseInt(alldata.getString("TotalCount").toString());
					JSONArray array=data.getJSONArray("Data");
					for (int i = 0; i < array.length(); i++) {
						mListViewData.add(CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i)));
					}
					adapter = new NoServiceShopAdapter(StoreServiceNetworkActivity.this, mListViewData,StoreServiceNetworkActivity.this);
					mListView.setAdapter(adapter);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, UserLoginStatus.get(StoreServiceNetworkActivity.this,"Token"), 
				"1", "20", 
				StoreToDataSave.get(StoreServiceNetworkActivity.this, "BrandId"), "",
				LocationDataSave.get(StoreServiceNetworkActivity.this, "CityId"),
				LocationDataSave.get(StoreServiceNetworkActivity.this, "CityName"),
				"", "2", "1",
				LocationDataSave.get(StoreServiceNetworkActivity.this, "Latitude"),
				LocationDataSave.get(StoreServiceNetworkActivity.this, "Longitude"),
				StoreServiceNetworkActivity.this);
		
	}
	//获取商店列表
		public void getshoplist_refresh(String PageNum){
			HttpMainServiceNetwork.getShopList(new CallbackLogic() {
				
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
					// TODO Auto-generated method stub
					try {
						JSONArray array=data.getJSONArray("Data");
						for (int i = 0; i < array.length(); i++) {
							mListViewData.add(CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i)));
						}
						adapter.notifyDataSetChanged();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, UserLoginStatus.get(StoreServiceNetworkActivity.this,"Token"), 
					PageNum, "20", StoreToDataSave.get(StoreServiceNetworkActivity.this, "BrandId"),
					"", LocationDataSave.get(StoreServiceNetworkActivity.this, "CityId"),
					LocationDataSave.get(StoreServiceNetworkActivity.this, "CityName"), "", "2", "1",
					LocationDataSave.get(StoreServiceNetworkActivity.this, "Latitude"),
					LocationDataSave.get(StoreServiceNetworkActivity.this, "Longitude"),
					StoreServiceNetworkActivity.this);
			
		}


		
		//=================
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		rightView.setVisibility(View.GONE);
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("服务网点");
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_main_service_network, bodyParentView);
		changeFonts(bodyParentView);

	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {

	}


	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		mListViewData.clear();
		getshoplist_refresh(String.valueOf(currentPage));
		mListView.set_booter_text("查看更多");
		mListView.stopLoadMore();
		mListView.stopRefresh();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
		if (TotalCount - currentPage * 20 < 0) {
			mListView.set_booter_text("没有更多数据");
			mListView.stopLoadMore();
			mListView.stopRefresh();
			return;
		} else {
			currentPage++;
			mListViewData.clear();
			getshoplist_refresh(String.valueOf(currentPage));
			adapter.notifyDataSetChanged();
			mListView.set_booter_text("查看更多");
			mListView.stopLoadMore();
			mListView.stopRefresh();
		}

	}

}