package com.bcwcar.android.bencar.activity.MainHome;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.CarInformationActivity;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.activity.MainServiceNetworkActivity;
import com.bcwcar.android.bencar.activity.SelectBrandActivity;
import com.bcwcar.android.bencar.activity.SelectCityActivity;
import com.bcwcar.android.bencar.activity.SelectSeriesinfoActivity;
import com.bcwcar.android.bencar.activity.SplashActivity;
import com.bcwcar.android.bencar.activity.WangDianDatas;
import com.bcwcar.android.bencar.activity.MainDiscovery.MainDiscovery;
import com.bcwcar.android.bencar.activity.MainMy.LoginActivity;
import com.bcwcar.android.bencar.adapter.HotingCarBrandAdapter;
import com.bcwcar.android.bencar.adapter.HotingCarShopAdapter;
import com.bcwcar.android.bencar.adapter.MyFragmentPagerAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.ExampleUtil;
import com.bcwcar.android.bencar.base.GPS_Positioning;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpMainServiceNetwork;
import com.bcwcar.android.bencar.biz.HttpUserCar;
import com.bcwcar.android.bencar.biz.HttpUserInfo;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.OnlyOneDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.util.StringUtil;
import com.bcwcar.android.bencar.widget.CustomViewPager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.squareup.okhttp.Request;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.jpush.android.api.JPushInterface;
import qrcode.activity.CaptureActivity;

public class MainHome extends BaseActivity implements OnRefreshListener2{
	private String refresh_time= new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss", Locale.CHINA).format(new Date(System.currentTimeMillis()));;
	PullToRefreshScrollView mPullRefreshScrollView;
	ScrollView mScrollView;
	private long exitTime;
	private static TextView leftView;
	/// ===============头部广告滑动页面============
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private MyFragmentPagerAdapter myFragmentPagerAdapter;
	private List<Map<String, String>> list_gg = new ArrayList<Map<String, String>>();
	private CustomViewPager pager_top;
	private RadioGroup radiogrounp;
	/// ===============GridView热门品牌==================
	private GridView mgridview;
	private List<Map<String, String>> list_hoting = new ArrayList<Map<String, String>>();
	private LinearLayout add_car_LinearLayout;
	private RelativeLayout shop_more_brand;
	// ===============listview 门店列表=============
	private RelativeLayout shop_more;
	private ListView mlistview;
	private List<Map<String, String>> list_hoting_shop = new ArrayList<Map<String, String>>();
	// ======默认车型=====
	private static SimpleDraweeView car_logo;
	private RelativeLayout defulat_car_RelativeLayout, no_login_RelativeLayout;
	private static TextView car_year_TextView;
	private static TextView car_chepai_TextView;
	private Timer yy;
	private int zhizhen_Timer = 0;
	private int gg_zhizhen = 0;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				System.out.println("============-=-=090989564654");
				if (gg_zhizhen == list_gg.size()) {
					gg_zhizhen = 0;
				}
				int tt = gg_zhizhen % list_gg.size();
				pager_top.setCurrentItem(tt);
				radiogrounp.check(radiogrounp.getChildAt(tt).getId());
				gg_zhizhen++;
			} else if (msg.what == 1011) {
				mPullRefreshScrollView.onRefreshComplete();
				getguanggaodata();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new GPS_Positioning(MainHome.this, handler).gps();
		initview();
		yy = new Timer();
		getguanggaodata();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (UserLoginStatus.get(MainHome.this, "CarCount").equals("0")
				|| StringUtil.isEmpty(UserLoginStatus.get(MainHome.this, "CarCount"))) {
			defulat_car_RelativeLayout.setVisibility(View.GONE);
			no_login_RelativeLayout.setVisibility(View.VISIBLE);
		} else {
			defulat_car_RelativeLayout.setVisibility(View.VISIBLE);
			no_login_RelativeLayout.setVisibility(View.GONE);
		}
		if (zhizhen_Timer == 1) {
			zhizhen_Timer = 2;
			yy.cancel();
		} else if (zhizhen_Timer == 2) {
			zhizhen_Timer = 1;
			yy.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					handler.sendEmptyMessage(1);
				}
			}, 1000, 5000);
		}
	}

	// 实例化控件
	public void initview() {
		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setOnRefreshListener(this);
	mPullRefreshScrollView.setRefreshingLabel("正在载入...", Mode.PULL_DOWN_TO_REFRESH);
	mPullRefreshScrollView.setPullLabel("松开刷新数据", Mode.PULL_DOWN_TO_REFRESH);
		mPullRefreshScrollView.setMode(Mode.PULL_DOWN_TO_REFRESH);
		mScrollView = mPullRefreshScrollView.getRefreshableView();
		
		// 未登录状态头部广告滑动页面================
		radiogrounp = (RadioGroup) findViewById(R.id.radiogroup001);
		pager_top = (CustomViewPager) findViewById(R.id.viewpager_home_buttom);
		pager_top.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				radiogrounp.check(radiogrounp.getChildAt(arg0).getId());

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		// ===============GridView热门品牌==================
		add_car_LinearLayout = (LinearLayout) findViewById(R.id.LinearLayout_add_love_car001);
		add_car_LinearLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainHome.this, SelectBrandActivity.class);
				intent.putExtra("action", "MainHomeActivity");
				startActivity(intent);
			}
		});
		no_login_RelativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_no_login_adcar001);

		mgridview = (GridView) findViewById(R.id.GridView_hoting_brand001);
		mgridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				Map<String, String> lineData = list_hoting.get(position);
				for (String key : lineData.keySet()) {
					System.out.println("key=" + key + "------value=" + lineData.get(key));
					UserCarDataSave.save(MainHome.this, key, lineData.get(key));
				}
				UserCarDataSave.save(MainHome.this, "BrandLogo", lineData.get("IconUrl"));
				Intent intent = new Intent(MainHome.this, SelectSeriesinfoActivity.class);
				intent.putExtra(BizDefineAll.BIZ_ACTION, "MainHomeActivity");
				startActivity(intent);
			}
		});
		shop_more_brand = (RelativeLayout) findViewById(R.id.RelativeLayout_shop_brand_more001);
		shop_more_brand.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(MainHome.this, SelectBrandActivity.class);
				intent.putExtra("action", "MainHomeActivity");
				startActivity(intent);
			}
		});
		// ========热门4s店 列表=============
		mlistview = (ListView) findViewById(R.id.ListView_neardy_4s_shop001);
		mlistview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				DingDanDataSave.save(MainHome.this, "ShopId", list_hoting_shop.get(position).get("ShopId").toString());
				Bundle bundle = new Bundle();
				bundle.putString(BizDefineAll.BIZ_ACTION, "MainHomeActivity");
				PageUtil.jumpTo(MainHome.this, WangDianDatas.class, bundle);
			}
		});
		shop_more = (RelativeLayout) findViewById(R.id.RelativeLayout_shop_more001);
		shop_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainHome.this, MainServiceNetworkActivity.class);
				startActivity(intent);
			}
		});
		// ================默认车型==============
		defulat_car_RelativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_user_cars001);
		defulat_car_RelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainHome.this, CarInformationActivity.class);
				intent.putExtra("action", "MainHomeActivity");
				startActivity(intent);
			}
		});
		car_logo = (SimpleDraweeView) findViewById(R.id.SimpleDraweeView_usercar_brand_logo);
		car_year_TextView = (TextView) findViewById(R.id.TextView_usercar_info001);
		car_chepai_TextView = (TextView) findViewById(R.id.TextView_usercar_info_usercar_card001);
	}


	// 获取广告图
	public void getguanggaodata() {
		Dialogshow(MainHome.this);
		HttpUserInfo.getShopShowInfo002(new CallbackLogic002() {
			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				showToast(getString(R.string.network_error_please_try_again));
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String alldata) {
				// TODO Auto-generated method stub
				try {
					getshoplist();
					list_gg.clear();
					fragmentList.clear();
					radiogrounp.removeAllViews();
					OnlyOneDataSave.save(MainHome.this, "gg", "1");
					JSONArray array = data.getJSONArray("Data");
					for (int i = 0; i < array.length(); i++) {
						list_gg.add(CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i)));
					}
					for (int j = 0; j < list_gg.size(); j++) {

						Bundle bundle = new Bundle();
						for (String key : list_gg.get(j).keySet()) {
							bundle.putCharSequence(key, list_gg.get(j).get(key));
						}
						MainHomeGuangGaoFragment ff = new MainHomeGuangGaoFragment().newInstance(bundle);
						fragmentList.add(ff);
						RadioButton radioButton = (RadioButton) LayoutInflater.from(MainHome.this)
								.inflate(R.layout.radiobutton_xml_item, null);
						radiogrounp.addView(radioButton);
					}
					myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
					pager_top.setAdapter(myFragmentPagerAdapter);
					if (list_gg.size() > 1) {
						yy.schedule(new TimerTask() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							}
						}, 1000, 5000);
					} else {
						pager_top.setCurrentItem(0);
						radiogrounp.check(radiogrounp.getChildAt(0).getId());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				showToast(responseDescription);
				denglu_reset(responseCode, responseDescription, MainHome.this);
			}

		}, UserLoginStatus.get(MainHome.this, "Token"),"1");

	}

	// 热门门店
	public void getshoplist() {
		HttpMainServiceNetwork.getShopList002(new CallbackLogic002() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				showToast(getString(R.string.network_error_please_try_again));
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String other) {
				// TODO Auto-generated method stub
				try {
					gethotbrand();
					list_hoting_shop.clear();
					OnlyOneDataSave.save(MainHome.this, "hotshop", "1");
					JSONArray array = data.getJSONArray("Data");
					for (int i = 0; i < array.length(); i++) {
						list_hoting_shop.add(CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i)));
					}
					mlistview.setAdapter(new HotingCarShopAdapter(MainHome.this, list_hoting_shop, MainHome.this));
					BaseActivity.setListViewHeight(mlistview);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				showToast(responseDescription);
				denglu_reset(responseCode, responseDescription, MainHome.this);
			}
		}, UserLoginStatus.get(MainHome.this, "Token"), "1", "10", "", "",
				LocationDataSave.get(MainHome.this, "CityId"), LocationDataSave.get(MainHome.this, "CityName"), "", "2",
				"1", LocationDataSave.get(MainHome.this, "Latitude"), LocationDataSave.get(MainHome.this, "Longitude"));

	}

	// 获取热门品牌
	public void gethotbrand() {
		HttpUserCar.getHotBrandList002(new CallbackLogic002() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				showToast(getString(R.string.network_error_please_try_again));
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String other) {
				// TODO Auto-generated method stub
				try {
					list_hoting.clear();
					getusercar();
					JSONArray object = data.getJSONArray("Data");
					for (int i = 0; i < object.length(); i++) {
						list_hoting.add(CollectionUtil.jsonObjectToMap_String(object.getJSONObject(i)));
					}
					mgridview.setAdapter(new HotingCarBrandAdapter(MainHome.this, list_hoting));
					BaseActivity.setGridViewHeight(mgridview);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				showToast(responseDescription);
				denglu_reset(responseCode, responseDescription, MainHome.this);
			}
		}, UserLoginStatus.get(MainHome.this, "Token"), LocationDataSave.get(MainHome.this, "CityName"));
	}
	// 获取默认车型
		public void getusercar() {
			// ======================================
			HttpUserCar.getUserDefaultCarInfo002(new CallbackLogic002() {

				@Override
				public void onNetworkError(Request request, IOException e) {
					// TODO Auto-generated method stub
					Dialogcancel();
					showToast(getString(R.string.network_error_please_try_again));
				}

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, String other) {
					// TODO Auto-generated method stub
					try {
						Dialogcancel();
						JSONObject object = data.getJSONObject("Data");
						Map<String, String> map = new HashMap<String, String>();
						map = CollectionUtil.jsonObjectToMap_String(object);
						for (String key : map.keySet()) {
							Log.e("map遍历", "key= " + key + " and value= " + map.get(key));
							UserCarDataSave.save(MainHome.this, key, map.get(key));
						}
						String string = object.getString("BuyDate").toString();
						UserCarDataSave.save(MainHome.this, "BuyDate001", string);
						String[] temp = string.split("年");
						String[] temp2 = temp[1].split("月");
						UserCarDataSave.save(MainHome.this, "BuyDate", temp[0] + "-" + temp2[0]);
						System.out.println("=============" + UserCarDataSave.get(MainHome.this, "BuyDate"));
						car_logo.setImageURI(
								ResourceUtil.getImageUriFromAssets(UserCarDataSave.get(MainHome.this, "BrandLogo")));
						car_year_TextView.setText(UserCarDataSave.get(MainHome.this, "CarName"));
						car_chepai_TextView.setText(UserCarDataSave.get(MainHome.this, "CarMiles") + "公里");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
					// TODO Auto-generated method stub
					Dialogcancel();
					showToast(responseDescription);
					denglu_reset(responseCode, responseDescription, MainHome.this);
				}
			}, UserLoginStatus.get(MainHome.this, "Token"));
		}

	// 设置城市名
	public static void set_city(String string) {
		leftView.setText(string);
	}

	/// =======================================固定样式==============
	// 返回键重写
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				showToast(getString(R.string.press_agin_to_back));
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();//这句代码要注析
	}

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_home, titleParentView);
		changeFonts((ViewGroup) rootView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		leftView = (TextView) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		leftView.setText(LocationDataSave.get(MainHome.this, "CityName"));
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserLoginStatus.isLoggedOn(getApplicationContext())) {

					Intent selectCityIntent = new Intent(MainHome.this, SelectCityActivity.class);
					selectCityIntent.putExtra("data", LocationDataSave.get(MainHome.this, "adress"));
					selectCityIntent.putExtra("action", "MainHomeFinishActivity");
					startActivity(selectCityIntent);
				} else {
					Toast.makeText(getApplicationContext(), "请先登录", 1).show();
					Intent selectCityIntent = new Intent(MainHome.this, LoginActivity.class);
					startActivity(selectCityIntent);
				}
			}
		});
//		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		rightView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainHome.this, CaptureActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.main_home_xml, bodyParentView);
		changeFonts((ViewGroup) view);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

	
	


	@Override
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(1011);
		refresh_time= new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss", Locale.CHINA)
				.format(new Date(System.currentTimeMillis()));
		mPullRefreshScrollView.setLastUpdatedLabel("上次更新时间：\n"+refresh_time);
		GPS_Positioning gps_Positioning=new GPS_Positioning(MainHome.this, handler);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		// TODO Auto-generated method stub
		mPullRefreshScrollView.onRefreshComplete();
	}

}
