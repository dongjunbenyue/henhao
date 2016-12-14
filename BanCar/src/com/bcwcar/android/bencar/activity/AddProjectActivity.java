package com.bcwcar.android.bencar.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.adapter.AddAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.ShareSDKUtil;
import com.squareup.okhttp.Request;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


//<style name="MyRatingBar" parent="@android:style/Widget.RatingBar">    
//<item name="android:progressDrawable">@drawable/myratingbar</item>    
//<item name="android:minHeight">20dp</item>    
//<item name="android:maxHeight">20dp</item>    
//</style> 
//<style name="MyRatingBar001" parent="@android:style/Widget.RatingBar">    
//<item name="android:progressDrawable">@drawable/myratingbar001</item>    
//<item name="android:minHeight">20dp</item>    
//<item name="android:maxHeight">20dp</item>    
//</style> 
/**
 * 添加项目页
 */
public class AddProjectActivity extends BaseActivity {
	private static final String LOG_TAG = AddProjectActivity.class.getSimpleName();
	private List<Map<String, Object>> listViewData = new ArrayList<Map<String, Object>>();
	private AddAdapter adapter;
	private String carid;
	private ListView mainListView;
	private static List<String> list_num = new ArrayList<String>();
	private static TextView total_num,baoyan;

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
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
		centerView.setText(getString(R.string.add_project_title));
		rightView.setVisibility(View.GONE);
		// rightView.setText(getString(R.string.add_maintenance_track_title_right));
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		View view = LayoutInflater.from(this).inflate(R.layout.body_smart_maintenance_add_project, bodyParentView);
		changeFonts(bodyParentView);

	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		carid = intent.getStringExtra("carid");
		list_num = intent.getStringArrayListExtra("list_num");
		initdata(carid);

		mainListView = (ListView) findViewById(R.id.add_project_list);
		mainListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				CheckBox checkBox = (CheckBox) view.findViewById(R.id.add_project_check);
			}
		});
		// 初始化列表
		total_num = (TextView) findViewById(R.id.add_project_num);
		 baoyan = (TextView)findViewById(R.id.add_project_go);
		baoyan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});


	}

	// listview 高度计算
		public static void setListViewHeight(ListView listView) {
			int totalHeight = 0;
			ListAdapter listAdapter = listView.getAdapter();
			for (int i = 0; i < listAdapter.getCount(); i++) {

				View listItemView = listAdapter.getView(i, null, listView);
				int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
				listItemView.measure(desiredWidth, 0);
				totalHeight += listItemView.getMeasuredHeight();
			}
			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight+20;
			listView.setLayoutParams(params);

		}
	
	public static void add(int pos) {
		total_num.setText(pos + "");

	}

	public void initdata(String string) {

		HttpOrder.getCarProPriceList(new CallbackLogic() {

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
				// TODO Auto-generated method stub
				Dialogcancel();
				json_jiexi(data);
				adapter = new AddAdapter(listViewData, getApplicationContext(), list_num);
				mainListView.setAdapter(adapter);
				setListViewHeight(mainListView);
			}
		}, UserLoginStatus.get(AddProjectActivity.this, "Token"), string,AddProjectActivity.this);

	}

	public void json_jiexi(JSONObject data) {
		try {
			JSONArray jsonArray = new JSONArray(data.getString("Data").toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ProjectName", object.getString("ProjectName"));
				map.put("ProjectId", object.getString("ProjectId"));
				map.put("ProBenefit", object.getString("ProBenefit"));
				map.put("ProjectOrder", object.getString("ProjectOrder"));
				if (object.isNull("Projects")) {
					Toast.makeText(getApplicationContext(), "无报价", Toast.LENGTH_SHORT).show();
					map.put("Discount", "0.00");
					map.put("AccNum", "0.00");// "ShareDiscount": "0%",
					map.put("ShareDiscount", "0.00%");
					map.put("ShopPrice", "0.00");
					map.put("BenPrice", "0.00");
					map.put("DeleteFlag", "");
					map.put("UserBenifit", "0.00");
					map.put("TimePrice", "0.00");
					map.put("TimeDiscount", "0.00");
					map.put("AfterDiscountTimePrice", "0.00");
					map.put("Accessory", "");
					map.put("TotalBenifit", "0.00");
					map.put("BenDiscount", "0.00");
					map.put("TotalPrice", "0.00");
					map.put("SalePrice", "0.00");
					map.put("AfterDiscountSalePrice", "0.00");
					map.put("AccId", "");
				} else {
					String Projects = object.getString("Projects");
					JSONArray projectsJSONArray = object.getJSONArray("Projects");
					JSONObject object2 = projectsJSONArray.getJSONObject(0);
					map.put("Discount", object2.getString("Discount").toString());
					map.put("AccNum", object2.getString("AccNum").toString());
					map.put("ShareDiscount", object2.getString("ShareDiscount").toString());
					map.put("ShopPrice", object2.getString("ShopPrice").toString());
					map.put("BenPrice", object2.getString("BenPrice").toString());
					map.put("DeleteFlag", object2.getString("DeleteFlag").toString());
					map.put("UserBenifit", object2.getString("UserBenifit").toString());
					map.put("TimePrice", object2.getString("TimePrice").toString());
					map.put("TimeDiscount", object2.getString("TimeDiscount").toString());
					map.put("AfterDiscountTimePrice", object2.getString("AfterDiscountTimePrice").toString());
					map.put("Accessory", object2.getString("Accessory").toString());
					map.put("TotalBenifit", object2.getString("TotalBenifit").toString());
					map.put("BenDiscount", object2.getString("BenDiscount").toString());
					map.put("TotalPrice", object2.getString("TotalPrice").toString());
					map.put("SalePrice", object2.getString("SalePrice").toString());
					map.put("AfterDiscountSalePrice", object2.getString("AfterDiscountSalePrice").toString());
					map.put("AccId", object2.getString("AccId").toString());
				}
				listViewData.add(map);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
