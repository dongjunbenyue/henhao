package com.bcwcar.android.bencar.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainMy.LoginActivity;
import com.bcwcar.android.bencar.adapter.HotingCarBrandAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.SysApplication;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpUserCar;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.StoreToDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.ListViewUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.StringUtil;
import com.bcwcar.android.bencar.widget.XlistView.MyLetterListView;
import com.bcwcar.android.bencar.widget.XlistView.MyLetterListView.OnTouchingLetterChangedListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 选择车系品牌
 */
public class SelectBrandActivity extends BaseActivity {
	private static final String LOG_TAG = SelectBrandActivity.class.getSimpleName();
	private ListView mListView;
	private List<Map<String, String>> mListViewData;
	private ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	private MyLetterListView letterListView = null;
	private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
	private String[] sections;// 存放存在的汉语拼音首字母
	private TextView overlay;
	private Handler handler;
	private OverlayThread overlayThread;
	private BaseAdapter adapter;
	private int idweizhi = 0;
	WindowManager windowManager;
	private String action;
	private static Activity activity;
	/// ===============GridView热门品牌==================
		private GridView mgridview;
		private List<Map<String, String>> list_hoting = new ArrayList<Map<String, String>>();
		private HotingCarBrandAdapter hotingadapter_brand;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity=SelectBrandActivity.this;
		try {
		action = getIntent().getStringExtra(BizDefineAll.BIZ_ACTION).toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		mListView = (ListView)findViewById(R.id.select_brand_lv);
		mListView.setOnItemClickListener(mListItemClickListener);
		letterListView = (MyLetterListView) findViewById(R.id.ListView_car_name001);
		letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
		initOverlay();
		handler = new Handler();
		overlayThread = new OverlayThread();
		mgridview = (GridView) findViewById(R.id.GridView_hoting_brand002);
		mgridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Map<String, String> lineData = list_hoting.get(position);
				if (action.equals("MainActivity")) {
					for (String key:lineData.keySet()) {
						StoreToDataSave.save(SelectBrandActivity.this, key, lineData.get(key));
					}
					MainActivity.setvar(0);
					finish();
				}else {
					for (String key:lineData.keySet()) {
						System.out.println(key+"======"+lineData.get(key));
						UserCarDataSave.save(SelectBrandActivity.this, key, lineData.get(key));
					}
					UserCarDataSave.save(SelectBrandActivity.this, "BrandLogo", lineData.get("IconUrl"));
					Intent intent=new Intent(SelectBrandActivity.this,SelectSeriesinfoActivity.class);
					intent.putExtra(BizDefineAll.BIZ_ACTION, "SelectBrandActivity");
					startActivity(intent);
				}
				
				
				
			}
		});
		gethotbrand();
		initData();
	}

	//获取热门品牌
			public void gethotbrand(){
				BaseActivity.Dialogcancel();
				HttpUserCar.getHotBrandList(new CallbackLogic() {
					
					@Override
					public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
						// TODO Auto-generated method stub
						try {
							JSONArray object=data.getJSONArray("Data");
							for (int i = 0; i < object.length(); i++) {
								list_hoting.add(CollectionUtil.jsonObjectToMap_String(object.getJSONObject(i)));
							}
							hotingadapter_brand = new HotingCarBrandAdapter(SelectBrandActivity.this, list_hoting);
							mgridview.setAdapter(hotingadapter_brand);
							setGridViewHeight(mgridview);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}, UserLoginStatus.get(SelectBrandActivity.this, "Token"), LocationDataSave.get(SelectBrandActivity.this, "CityName"), SelectBrandActivity.this);
				
			}
	public static void finishpage(){
		activity.finish();
	}
	// listview数据tiao转
	private OnItemClickListener mListItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (position > idweizhi) {

				showToast("暂未开通");
			} else {
				if (action.equals("MainHomeActivity")) {
					Map<String, String> lineData = mListViewData.get(position);
					for (String key:lineData.keySet()) {
						UserCarDataSave.save(SelectBrandActivity.this, key, lineData.get(key));
					}
					Bundle bundle = new Bundle();
					bundle.putString("action", "SelectBrandActivity");
					PageUtil.jumpTo(SelectBrandActivity.this, SelectSeriesinfoActivity.class, bundle);
				} else if (action.equals("SelectCarActivity")) {
					Map<String, String> lineData = mListViewData.get(position);
					for (String key:lineData.keySet()) {
						UserCarDataSave.save(SelectBrandActivity.this, key, lineData.get(key));
					}
					Bundle bundle = new Bundle();
					bundle.putString("action", action);
					PageUtil.jumpTo(SelectBrandActivity.this, SelectSeriesinfoActivity.class, bundle);
				} else if (action.equals("MainActivity")) {
					Map<String, String> lineData = mListViewData.get(position);
					for (String key:lineData.keySet()) {
						StoreToDataSave.save(SelectBrandActivity.this, key, lineData.get(key));
					}
					MainActivity.setvar(0);
					finish();
				}else {

//					Map<String, String> lineData = mListViewData.get(position);
//					set_user_car_data("Brand", lineData.get("Brand"));
//					set_user_car_data("BrandId", lineData.get("BrandId"));
//					set_user_car_data("BrandLogo", lineData.get("IconUrl"));
//					Bundle bundle = new Bundle();
//					bundle.putString("action", "0");
//					PageUtil.jumpTo(SelectBrandActivity.this, SelectSeriesinfoActivity.class, bundle);
				}
			}
		}
	};

	// 侧边栏出触摸事件
	private class LetterListViewListener implements OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				mListView.setSelection(position);
				overlay.setText(sections[position] );
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				handler.postDelayed(overlayThread, 1000);
			}
		}

	}
	

	// 设置overlay不可见
	private class OverlayThread implements Runnable {

		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}
	// 初始化汉语拼音首字母弹出提示框
	private void initOverlay() {
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		 windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}



	// 获取所有品牌的列表
	private void initData() {
		Dialogcancel();
	HttpUserCar.brandinfo(new CallbackLogic() {
		@Override
		public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
			// TODO Auto-generated method stub
			try {
				mListViewData = ListViewUtil.convertToTagListViewData(data);
				ListAdapter001 adapter = new ListAdapter001(mListViewData);
				mListView.setAdapter(adapter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}, SelectBrandActivity.this,UserLoginStatus.get(SelectBrandActivity.this,"Token"),LocationDataSave.get(SelectBrandActivity.this, "CityId"),LocationDataSave.get(SelectBrandActivity.this, "CityName"));
	}
//=====================================================
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		rightView.setText(getString(R.string.text_code_kown_car));
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText(getString(R.string.text_car_brand));
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SelectBrandActivity.this,FillSelectActivity.class);
				startActivity(intent);
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_select_brand, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
	}
	/**
	 * 
	 * listview的适配器 ListViewAdapter
	 * 
	 * @author sy
	 * 
	 */
	private class ListAdapter001 extends BaseAdapter {
		private List<Map<String, String>> listViewdata;
		private TextView textView222;
		private String biaoji="1";
		public ListAdapter001(List<Map<String, String>> listViewdata) {
			this.listViewdata = listViewdata;
			alphaIndexer = new HashMap<String, Integer>();
			sections = new String[listViewdata.size()];

			for (int i = 0; i < listViewdata.size(); i++) {

				String currentStr = listViewdata.get(i).get("TagTitle");
				alphaIndexer.put(currentStr, i);
				sections[i] = currentStr;
			}
			if (alphaIndexer.get("#") != null) {
				idweizhi = alphaIndexer.get("#");
			}

		}
        
		@Override
		public int getCount() {
			return listViewdata.size();
		}

		@Override
		public Object getItem(int position) {
			return listViewdata.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public boolean isEnabled(int position) {
			Map<String, String> item = listViewdata.get(position);
			if (!StringUtil.isEmpty(item.get("TagTitle"))) { // 是Tag
				return false;
			}

			return super.isEnabled(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
			Map<String, String> item = listViewdata.get(position);
			boolean isTag = !StringUtil.isEmpty(item.get("TagTitle"));
			if (isTag) {
				convertView = layoutInflater.inflate(R.layout.list_item_select_brand_tag, parent, false);
				TextView tagTitle = (TextView) convertView.findViewById(R.id.select_brand_tag_title);
				tagTitle.setText(item.get("TagTitle"));
				if (item.get("TagTitle").equals("#")) {
					biaoji="0";
					
				}else {
					biaoji="1";
				}
				
			} else {
				convertView = layoutInflater.inflate(R.layout.list_item_select_brand, parent, false);
				SimpleDraweeView logo = (SimpleDraweeView) convertView.findViewById(R.id.select_brand_logo);
				String logoFullUrl = "asset:///" + item.get("First") + "/" + item.get("IconUrl");
				logo.setImageURI(Uri.parse(logoFullUrl));
				TextView brandName = (TextView) convertView.findViewById(R.id.select_brand_name);
				brandName.setText(item.get("Brand"));
				textView222=(TextView)convertView.findViewById(R.id.lingjuan);
				if (position>=idweizhi) {
					textView222.setVisibility(View.GONE);
				}else {
					textView222.setVisibility(View.GONE);
				}
			}
			BaseActivity.changeFonts((ViewGroup)convertView);
			return convertView;
		}

	}

}