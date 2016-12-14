package com.bcwcar.android.bencar.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.adapter.SelectCarinfoAdapter;
import com.bcwcar.android.bencar.adapter.SelectSeriesinfoAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.SysApplication;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpUserCar;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.ListViewUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Request;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 选择车系信息及年款
 */
public class SelectSeriesinfoActivity extends BaseActivity {
	private static final String LOG_TAG = SelectSeriesinfoActivity.class.getSimpleName();
	private ListView mListView,mListView2;
	private List<Map<String, String>> mListViewData=new ArrayList<Map<String, String>>();
	private List<Map<String, String>> mListViewData_year=new ArrayList<Map<String, String>>();
	private String action;
	private SimpleDraweeView logo_SimpleDraweeView;
	private TextView name_brand,gone_TextView;
	private LinearLayout car_style_LinearLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			action = getIntent().getStringExtra("action").toString();
			if (action.equals("SelectBrandActivity")) {
				initData(UserCarDataSave.get(SelectSeriesinfoActivity.this, "BrandId"));
			}else if(action.equals("SelectCarActivity")){
				initData(UserCarDataSave.get(SelectSeriesinfoActivity.this, "BrandId"));
			}else if(action.equals("MainHomeActivity")){
				initData(UserCarDataSave.get(SelectSeriesinfoActivity.this, "BrandId"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		initview();
	}
	//实例化控件
public void initview(){
	mListView2=(ListView) findViewById(R.id.select_seriesinfo_year_style01);
	mListView2.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			Map<String, String> lineData = mListViewData_year.get(position);
			for (String key:lineData.keySet()) {
				System.out.println("key="+key+"------value="+lineData.get(key));
				UserCarDataSave.save(SelectSeriesinfoActivity.this, key, lineData.get(key));
			}
			if (action.equals("MainHomeActivity")) {
				Intent intent = new Intent(SelectSeriesinfoActivity.this, CarInformationActivity.class);
				intent.putExtra("action", "SelectSeriesinfoActivity");
				startActivity(intent);
				finish();
			}else if (action.equals("SelectCarActivity")) {
				addcar();
			}else if (action.equals("SelectBrandActivity")) {
				Intent intent = new Intent(SelectSeriesinfoActivity.this, CarInformationActivity.class);
				intent.putExtra("action", "SelectSeriesinfoActivity");
				startActivity(intent);
				finish();
				SelectBrandActivity.finishpage();
			}
		}
	});
	gone_TextView=(TextView)findViewById(R.id.TextView_gone_car_yeay_show001);
	gone_TextView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			car_style_LinearLayout.setVisibility(View.GONE);
			 Animation animation002=AnimationUtils.loadAnimation(SelectSeriesinfoActivity.this,R.anim.left_to_right);
			car_style_LinearLayout.setAnimation(animation002);
		}
	});
	car_style_LinearLayout=(LinearLayout)findViewById(R.id.LinearLayout_car_year_style001);
	logo_SimpleDraweeView=(SimpleDraweeView)findViewById(R.id.SimpleDraweeView_select_brand_logo001);
	logo_SimpleDraweeView.setImageURI(ResourceUtil.getImageUriFromAssets(UserCarDataSave.get(SelectSeriesinfoActivity.this, "IconUrl")));
	name_brand=(TextView)findViewById(R.id.TextView_select_brand_name001);
	name_brand.setText(UserCarDataSave.get(SelectSeriesinfoActivity.this, "Brand"));
	mListView = (ListView) findViewById(R.id.select_seriesinfo_lv);
	mListView.setOnItemClickListener(mListItemClickListener);
	
}
	
	private OnItemClickListener mListItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (action.equals("MainHomeActivity")) {
				UserCarDataSave.save(SelectSeriesinfoActivity.this, "SeriesId", mListViewData.get(position).get("SeriesId").toString());
				UserCarDataSave.save(SelectSeriesinfoActivity.this, "ProviderId", mListViewData.get(position).get("ProviderId").toString());
				UserCarDataSave.save(SelectSeriesinfoActivity.this, "SeriesName", mListViewData.get(position).get("SeriesName").toString());
               init_cardata(UserCarDataSave.get(SelectSeriesinfoActivity.this, "SeriesId"));
			}else if (action.equals("SelectCarActivity")) {
				UserCarDataSave.save(SelectSeriesinfoActivity.this, "SeriesId", mListViewData.get(position).get("SeriesId").toString());
				UserCarDataSave.save(SelectSeriesinfoActivity.this, "ProviderId", mListViewData.get(position).get("ProviderId").toString());
				UserCarDataSave.save(SelectSeriesinfoActivity.this, "SeriesName", mListViewData.get(position).get("SeriesName").toString());
               init_cardata(UserCarDataSave.get(SelectSeriesinfoActivity.this, "SeriesId"));
			}else if (action.equals("SelectBrandActivity")) {
				UserCarDataSave.save(SelectSeriesinfoActivity.this, "SeriesId", mListViewData.get(position).get("SeriesId").toString());
				UserCarDataSave.save(SelectSeriesinfoActivity.this, "ProviderId", mListViewData.get(position).get("ProviderId").toString());
				UserCarDataSave.save(SelectSeriesinfoActivity.this, "SeriesName", mListViewData.get(position).get("SeriesName").toString());
               init_cardata(UserCarDataSave.get(SelectSeriesinfoActivity.this, "SeriesId"));
			}

		}
	};

	// 获取车系列表,不验证城市信息
	private void initData(String mBrandId) {
		HttpUserCar.seriesinfo(new CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data,JSONObject alldata) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							mListViewData = ListViewUtil.convertToTagListViewData(data);
							SelectSeriesinfoAdapter adapter = new SelectSeriesinfoAdapter(mListViewData);
							mListView.setAdapter(adapter);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}

		}, UserLoginStatus.get(SelectSeriesinfoActivity.this,"Token"), 
				mBrandId,"0",
				LocationDataSave.get(SelectSeriesinfoActivity.this, "CityId"),
				LocationDataSave.get(SelectSeriesinfoActivity.this, "CityName"),
				SelectSeriesinfoActivity.this
				);
	}

	//获取车年款配置
	private void init_cardata(String SeriesId) {
		HttpUserCar.carinfo(new CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data,JSONObject alldata) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							mListViewData_year = CollectionUtil.jsonArrayToListMap(data.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA));
							if (mListViewData_year.size()!=0) {
								car_style_LinearLayout.setVisibility(View.VISIBLE);
								Animation animation001=AnimationUtils.loadAnimation(SelectSeriesinfoActivity.this,R.anim.right_to_left);
								car_style_LinearLayout.setAnimation(animation001);
								mListView2.setAdapter(new SelectCarinfoAdapter(mListViewData_year));
							}else {
								showToast(getString(R.string.text_no_caryear));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		}, UserLoginStatus.get(SelectSeriesinfoActivity.this,"Token"), SeriesId,SelectSeriesinfoActivity.this);
	}
	//添加用户车型
	public void addcar(){
		HttpUserCar.addUserCar(new CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
			showToast(getString(R.string.text_add_car_success));
			finish();
			SelectBrandActivity.finishpage();
			}
		}, UserLoginStatus.get(SelectSeriesinfoActivity.this,"Token"),
				UserCarDataSave.get(SelectSeriesinfoActivity.this, "CarId"),
				"5000","2000-01", SelectSeriesinfoActivity.this);
	}
	//=======================================
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
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText(getString(R.string.text_car_sersions));
		rightView.setVisibility(View.GONE);
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_select_seriesinfo, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// LayoutInflater.from(this).inflate(R.layout.common_toolbar_1,
		// toolBarParentView);
	}
}