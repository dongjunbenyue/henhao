package com.bcwcar.android.bencar.activity.MainDiscovery;

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
import org.json.JSONObject;

import com.baidu.mapapi.map.Text;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.ConversationListActivity;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.activity.SelectCarActivity;
import com.bcwcar.android.bencar.activity.ShopQuanQuitActivity;
import com.bcwcar.android.bencar.activity.MainHome.MainHome;
import com.bcwcar.android.bencar.activity.MainHome.MainHomeGuangGaoFragment;
import com.bcwcar.android.bencar.adapter.MyFragmentPagerAdapter;
import com.bcwcar.android.bencar.adapter.discoverChoiceAdapter;
import com.bcwcar.android.bencar.adapter.mainShopServiceAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.BaseApplication;
import com.bcwcar.android.bencar.base.GPS_Positioning;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpDiscover;
import com.bcwcar.android.bencar.biz.HttpMainServiceNetwork;
import com.bcwcar.android.bencar.biz.HttpUserCar;
import com.bcwcar.android.bencar.biz.HttpUserInfo;
import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.OnlyOneDataSave;
import com.bcwcar.android.bencar.datasave.QuanDingDanDataSave;
import com.bcwcar.android.bencar.datasave.StoreToDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.widget.CustomViewPager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.squareup.okhttp.Request;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnScrollChangeListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class MainDiscovery extends BaseActivity implements OnRefreshListener2 {
	// private ObservableScrollView mainScrollView;
	PullToRefreshScrollView mPullRefreshScrollView;
	ScrollView mScrollView;
	private long exitTime;
	private int top_height = 0;
	private List<Map<String, String>> list_data = new ArrayList<Map<String, String>>();
	// ============主页data=-==
	private static List<Map<String, String>> mListViewData = new ArrayList<Map<String, String>>();
	private static ListView mainListView;
	private static mainShopServiceAdapter adapter;
	// ===默认车辆
	private static SimpleDraweeView car_logo;
	private static TextView car_name;
	private static TextView car_km;
	private LinearLayout change_car_deafult;
	// ==========
	private static Activity activity;
	private TextView store_button;
	/// ===============头部广告滑动页面============
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private MyFragmentPagerAdapter myFragmentPagerAdapter;
	private List<Map<String, String>> list_gg = new ArrayList<Map<String, String>>();
	private CustomViewPager pager_top;
	private RadioGroup radiogrounp;
	private int gg_zhizhen = 0;
	private int refreshid=0;
	// 刷选
	int[] location = new int[2];
	private boolean get_hh = false;
	private String[] item = { "全部", "钣金喷漆", "镀金镀膜", "室内美容", "汽车贴膜", "抛光打蜡", "汽车精品", "抵用券", "其他" };
	private String[] smart = { "智能排序", "好评优先", "人气最高", "价格最低", "最新发布" };
	private LinearLayout mainchoice_top, choice_top, three_choice_main, gone_up;
	private RelativeLayout gone_thre_listview;
	private RelativeLayout main_ref001, main_ref002, main_ref003;
	private RelativeLayout ref001, ref002, ref003;
	private ListView main_ll001, main_ll002, main_ll003;
	private discoverChoiceAdapter adapter001, adapter002, adapter003;
	private List<Map<String, String>> list_cc001 = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_cc002 = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_cc003 = new ArrayList<Map<String, String>>();
	private int choice_location = 0;
	private TextView xx001, xx002, xx003, xxf001, xxf002, xxf003;
	private String reString001 = "0", reString002 = "", reString003 = "1";
	private int PageNum = 1, TotalCount;
	// 八大
	private TextView discovery_item001, discovery_item002, discovery_item003, discovery_item004, discovery_item005,
			discovery_item006, discovery_item007, discovery_item008;

	/// ===========
	private Timer yy;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if (gg_zhizhen == list_gg.size()) {
					gg_zhizhen = 0;
				}
				int tt = gg_zhizhen % list_gg.size();
				pager_top.setCurrentItem(tt);
				radiogrounp.check(radiogrounp.getChildAt(tt).getId());
			} else if (msg.what == 1011) {
				PageNum=1;
				mPullRefreshScrollView.onRefreshComplete();
				alldata();
			} else if (msg.what == 102) {
				mPullRefreshScrollView.onRefreshComplete();
				if (PageNum * 10 >= TotalCount) {
                PageNum=1;
				} else {
					PageNum++;
					refeshgetshopservicelist(PageNum, reString001, reString002, reString003);
				}
			}else if (msg.what==103) {
				setListViewHeight003(mainListView);
				adapter.notifyDataSetChanged();
				refreshid=1;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new GPS_Positioning(MainDiscovery.this, handler).gps();
		OnlyOneDataSave.save(MainDiscovery.this, "DiscoveryData", "1");
		mListViewData.clear();
		activity = MainDiscovery.this;
		yy = new Timer();
		initview();
		alldata();
		getnonews();
	}
   @Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	if (refreshid==1) {
		setListViewHeight003(mainListView);
		adapter.notifyDataSetChanged();
	}
	
}
	// 获取城市的区域list
	public void getCityAra() {
		Dialogshow(MainDiscovery.this);
		HttpMainServiceNetwork.getDistrictInfo002(new CallbackLogic002() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				showToast(getString(R.string.network_error_please_try_again));
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String other) {
				// TODO Auto-generated method stub
				try {
					list_cc002.clear();
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
					getusercar();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				showToast(responseDescription);
				denglu_reset(responseCode, responseDescription, MainDiscovery.this);
			}
		}, UserLoginStatus.get(MainDiscovery.this, "Token"), LocationDataSave.get(MainDiscovery.this, "CityId"),
				LocationDataSave.get(MainDiscovery.this, "CityName"));
	}

	// 获取默认车型
	public void getusercar() {
		HttpUserCar.getUserDefaultCarInfo002(new CallbackLogic002() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				showToast(getString(R.string.network_error_please_try_again));
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String other) {
				// TODO Auto-generated method stub
				try {
					getguanggaodata();
					JSONObject object = data.getJSONObject("Data");
					Map<String, String> map = new HashMap<String, String>();
					map = CollectionUtil.jsonObjectToMap_String(object);
					for (String key : map.keySet()) {
						Log.e("map遍历", "key= " + key + " and value= " + map.get(key));
						UserCarDataSave.save(MainDiscovery.this, key, map.get(key));
					}
					String string = object.getString("BuyDate").toString();
					UserCarDataSave.save(MainDiscovery.this, "BuyDate001", string);
					String[] temp = string.split("年");
					String[] temp2 = temp[1].split("月");
					UserCarDataSave.save(MainDiscovery.this, "BuyDate", temp[0] + "-" + temp2[0]);
					System.out.println("=============" + UserCarDataSave.get(MainDiscovery.this, "BuyDate"));
					car_logo.setImageURI(
							ResourceUtil.getImageUriFromAssets(UserCarDataSave.get(MainDiscovery.this, "BrandLogo")));
					car_name.setText(UserCarDataSave.get(MainDiscovery.this, "CarName"));
					car_km.setText(UserCarDataSave.get(MainDiscovery.this, "CarMiles") + "公里");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				showToast(responseDescription);
				denglu_reset(responseCode, responseDescription, MainDiscovery.this);
			}
		}, UserLoginStatus.get(MainDiscovery.this, "Token"));
	}

	// 获取广告图
	public void getguanggaodata() {
	
		HttpUserInfo.getShopShowInfo002(new CallbackLogic002() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				showToast(getString(R.string.network_error_please_try_again));
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String other) {
				// TODO Auto-generated method stub
				try {
					getshopservicelist(reString001, reString002, reString003);
					list_gg.clear();
					fragmentList.clear();
					radiogrounp.removeAllViews();
					JSONArray array = data.getJSONArray("Data");
					for (int i = 0; i < array.length(); i++) {
						list_gg.add(CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i)));
					}
					for (int j = 0; j < list_gg.size(); j++) {

						Bundle bundle = new Bundle();
						for (String key : list_gg.get(j).keySet()) {
							bundle.putCharSequence(key, list_gg.get(j).get(key));
						}
						MainDiscoveryGuangGaoFragment ff = new MainDiscoveryGuangGaoFragment().newInstance(bundle);
						fragmentList.add(ff);
						RadioButton radioButton = (RadioButton) LayoutInflater.from(MainDiscovery.this)
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
								handler.sendEmptyMessage(1);
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
				denglu_reset(responseCode, responseDescription, MainDiscovery.this);
			}
		}, UserLoginStatus.get(MainDiscovery.this, "Token"),"2");

	}

	// 获取增值服务网点列表 TODO
	public void getshopservicelist(String ServiceType, String DistrictId, String SortType) {
		HttpDiscover.getServiceShopList002(new CallbackLogic002() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				showToast(getString(R.string.network_error_please_try_again));
			}

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String other) {
				// TODO Auto-generated method stub
				try {
					Dialogcancel();
					mListViewData.clear();
					JSONObject alldata = new JSONObject(other);
					TotalCount = Integer.parseInt(alldata.getString("TotalCount").toString());
					if (TotalCount<=10) {
						mPullRefreshScrollView.setMode(Mode.PULL_DOWN_TO_REFRESH);
					}else {
						mPullRefreshScrollView.setMode(Mode.BOTH);
					}
					JSONArray array = data.getJSONArray("Data");
					for (int i = 0; i < array.length(); i++) {
						Map<String, String> map = new HashMap<String, String>();
						map = CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i));
						map.put("zhizhen", "0");
						mListViewData.add(map);
					}
					
					adapter = new mainShopServiceAdapter(getApplicationContext(), mListViewData, activity);
					mainListView.setAdapter(adapter);
					handler.sendEmptyMessage(103);
					OnlyOneDataSave.save(MainDiscovery.this, "DiscoveryData", "0");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				showToast(responseDescription);
				denglu_reset(responseCode, responseDescription, MainDiscovery.this);
			}
		}, UserLoginStatus.get(MainDiscovery.this, "Token"), "1", "10",
				"", DistrictId,
				LocationDataSave.get(MainDiscovery.this, "CityId"),
				LocationDataSave.get(MainDiscovery.this, "CityName"), "", SortType, "1",
				LocationDataSave.get(MainDiscovery.this, "Longitude"),
				LocationDataSave.get(MainDiscovery.this, "Latitude"), ServiceType);

	}

	// 获取未读消息
	public void getnonews() {
		if (RongIM.getInstance() != null) {
			RongIM.getInstance()
					.setOnReceiveUnreadCountChangedListener(new RongIM.OnReceiveUnreadCountChangedListener() {
						@Override
						public void onMessageIncreased(int i) {
							if (i != 0) {
								store_button.setVisibility(View.VISIBLE);
							} else {
								store_button.setVisibility(View.GONE);
							}
						}
					}, new Conversation.ConversationType[] { Conversation.ConversationType.PRIVATE,
							Conversation.ConversationType.DISCUSSION, Conversation.ConversationType.SYSTEM });
		}
	}

	// 刷新增值服务网点列表
	public void refeshgetshopservicelist(int pagenum, String ServiceType, String DistrictId, String SortType) {
		HttpDiscover.getServiceShopList(new CallbackLogic() {

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					TotalCount = Integer.parseInt(alldata.getString("TotalCount").toString());
					if (TotalCount<=10*PageNum) {
						mPullRefreshScrollView.setMode(Mode.PULL_DOWN_TO_REFRESH);
					}else {
						mPullRefreshScrollView.setMode(Mode.BOTH);
					}
					JSONArray array = data.getJSONArray("Data");
					for (int i = 0; i < array.length(); i++) {
						Map<String, String> map = new HashMap<String, String>();
						map = CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i));
						map.put("zhizhen", "0");
						mListViewData.add(map);
					}
					adapter.notifyDataSetChanged();
					setListViewHeight003(mainListView);
					refesh_view(0);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}, UserLoginStatus.get(MainDiscovery.this, "Token"), pagenum + "", "10",
				"", DistrictId,
				LocationDataSave.get(MainDiscovery.this, "CityId"),
				LocationDataSave.get(MainDiscovery.this, "CityName"), "", SortType, "1",
				LocationDataSave.get(MainDiscovery.this, "Longitude"),
				LocationDataSave.get(MainDiscovery.this, "Latitude"), ServiceType, MainDiscovery.this);

	}

	// 实例化控件
	public void initview() {
		// ===main
		mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		mPullRefreshScrollView.setOnRefreshListener(this);
		mPullRefreshScrollView.setRefreshingLabel("正在载入...", Mode.PULL_DOWN_TO_REFRESH);
		mPullRefreshScrollView.setPullLabel("刷新数据...", Mode.PULL_DOWN_TO_REFRESH);
		mPullRefreshScrollView.setRefreshingLabel("正在加载...", Mode.PULL_UP_TO_REFRESH);
		mPullRefreshScrollView.setPullLabel("查看更多...", Mode.PULL_UP_TO_REFRESH);
		mPullRefreshScrollView.setReleaseLabel("松开加载更多...", Mode.PULL_UP_TO_REFRESH);
		mScrollView = mPullRefreshScrollView.getRefreshableView();
		mScrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("choice_location====" + choice_location);
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					// 可以监听到ScrollView的滚动事件
					System.out.println("top_height==" + top_height);
					System.out.println("location[1]==" + location[1]);
					System.out.println("choice_location==" + choice_location);
					choice_top.getLocationOnScreen(location);
					if (!get_hh) {
						choice_location = location[1];
						get_hh = true;
					}

					if (location[1] - top_height <= 0) {
						mainchoice_top.setVisibility(View.VISIBLE);
						// choice_top.setVisibility(View.GONE);
					} else {
						// choice_top.setVisibility(View.VISIBLE);
						mainchoice_top.setVisibility(View.GONE);
					}
				}
				return false;
			}
		});
		// ======默认车辆
		car_logo = (SimpleDraweeView) findViewById(R.id.SimpleDraweeView_dicvovery_car_logo001);
		car_km = (TextView) findViewById(R.id.TextView_dicvovery_car_km001);
		car_name = (TextView) findViewById(R.id.TextView_dicvovery_car_name001);
		change_car_deafult = (LinearLayout) findViewById(R.id.LinearLayout_change_car_deafult001);
		change_car_deafult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainDiscovery.this, SelectCarActivity.class);
				intent.putExtra(BizDefineAll.BIZ_ACTION, "MainDiscovery");
				startActivity(intent);
			}
		});
		// 八大项目
		discovery_item001 = (TextView) findViewById(R.id.TextView_discovery_item001);
		discovery_item001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("0000000000000000");
				Intent intent = new Intent(MainDiscovery.this, DiscoverProjectDetailActivity.class);
				intent.putExtra("MainDiscovery", "1");
				startActivity(intent);
			}
		});
		discovery_item002 = (TextView) findViewById(R.id.TextView_discovery_item002);
		discovery_item002.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("0000000000000000");
				Intent intent = new Intent(MainDiscovery.this, DiscoverProjectDetailActivity.class);
				intent.putExtra("MainDiscovery", "2");
				startActivity(intent);
			}
		});
		discovery_item003 = (TextView) findViewById(R.id.TextView_discovery_item003);
		discovery_item003.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainDiscovery.this, DiscoverProjectDetailActivity.class);
				intent.putExtra("MainDiscovery", "3");
				startActivity(intent);
			}
		});
		discovery_item004 = (TextView) findViewById(R.id.TextView_discovery_item004);
		discovery_item004.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainDiscovery.this, DiscoverProjectDetailActivity.class);
				intent.putExtra("MainDiscovery", "4");
				startActivity(intent);
			}
		});
		discovery_item005 = (TextView) findViewById(R.id.TextView_discovery_item005);
		discovery_item005.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainDiscovery.this, DiscoverProjectDetailActivity.class);
				intent.putExtra("MainDiscovery", "5");
				startActivity(intent);
			}
		});

		discovery_item006 = (TextView) findViewById(R.id.TextView_discovery_item006);
		discovery_item006.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainDiscovery.this, DiscoverProjectDetailActivity.class);
				intent.putExtra("MainDiscovery", "6");
				startActivity(intent);
			}
		});
		discovery_item007 = (TextView) findViewById(R.id.TextView_discovery_item007);
		discovery_item007.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainDiscovery.this, DiscoverProjectDetailActivity.class);
				intent.putExtra("MainDiscovery", "7");
				startActivity(intent);
			}
		});
		discovery_item008 = (TextView) findViewById(R.id.TextView_discovery_item008);
		discovery_item008.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainDiscovery.this, DiscoverProjectDetailActivity.class);
				intent.putExtra("MainDiscovery", "0");
				startActivity(intent);
			}
		});

		// 刷选
		xx001 = (TextView) findViewById(R.id.TextView_xx_001);
		xx002 = (TextView) findViewById(R.id.TextView_xx_002);
		xx003 = (TextView) findViewById(R.id.TextView_xx_003);
		xxf001 = (TextView) findViewById(R.id.TextView_xx_fu_001);
		xxf002 = (TextView) findViewById(R.id.TextView_xx_fu_002);
		xxf003 = (TextView) findViewById(R.id.TextView_xx_fu_003);
		gone_thre_listview = (RelativeLayout) findViewById(R.id.RelativeLayout_gone001);
		gone_thre_listview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refesh_view(0);
			}
		});
		gone_up = (LinearLayout) findViewById(R.id.TextView_gone001);
		gone_up.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refesh_view(0);
			}
		});
		three_choice_main = (LinearLayout) findViewById(R.id.LinearLayout_three_listview_choice001);
		mainchoice_top = (LinearLayout) findViewById(R.id.LinearLayout_choice_maintop001);
		choice_top = (LinearLayout) findViewById(R.id.LinearLayout_choice_main001);
		main_ref001 = (RelativeLayout) findViewById(R.id.RelativeLayout_top_shaxuan001);
		main_ref002 = (RelativeLayout) findViewById(R.id.RelativeLayout_top_shaxuan002);
		main_ref003 = (RelativeLayout) findViewById(R.id.RelativeLayout_top_shaxuan003);
		ref001 = (RelativeLayout) findViewById(R.id.RelativeLayout_main_shaxuan001);
		ref002 = (RelativeLayout) findViewById(R.id.RelativeLayout_main_shaxuan002);
		ref003 = (RelativeLayout) findViewById(R.id.RelativeLayout_main_shaxuan003);
		main_ll001 = (ListView) findViewById(R.id.ListView_top_project001);
		main_ll002 = (ListView) findViewById(R.id.ListView_top_project002);
		main_ll003 = (ListView) findViewById(R.id.ListView_top_project003);
		main_ll001.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				reString001 = list_cc001.get(position).get("DistrictId").toString();
				xx001.setText(list_cc001.get(position).get("DistrictName").toString());
				xxf001.setText(list_cc001.get(position).get("DistrictName").toString());
				refresh_data_topview(1, position);
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
				xxf002.setText(list_cc002.get(position).get("DistrictName").toString());
				refresh_data_topview(2, position);
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
				xxf003.setText(list_cc003.get(position).get("DistrictName").toString());
				refresh_data_topview(3, position);
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
		ref001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mScrollView.scrollTo(0, choice_location);
				refesh_view(1);
			}
		});
		ref002.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mScrollView.scrollTo(0, choice_location);
				refesh_view(2);
			}
		});
		ref003.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mScrollView.scrollTo(0, choice_location);
				refesh_view(3);
			}
		});
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
		// 未读消息
		store_button = (TextView) findViewById(R.id.TextView_kefu_storemannager001);
		mainListView = (ListView) findViewById(R.id.ListView_main_project004);
	}

	// 刷新刷选条件的三个listview
	public void refesh_view(int tt) {

		choice_top.getLocationOnScreen(location);
		if (location[1] - top_height <= 0) {
			mainchoice_top.setVisibility(View.VISIBLE);
			// choice_top.setVisibility(View.GONE);
			System.out.println("打开");
		} else {
			// choice_top.setVisibility(View.VISIBLE);
			mainchoice_top.setVisibility(View.GONE);
			System.out.println("关闭");
		}
		if (tt == 1) {
			main_ll001.setVisibility(View.VISIBLE);
			main_ll002.setVisibility(View.GONE);
			main_ll003.setVisibility(View.GONE);
			three_choice_main.setVisibility(View.VISIBLE);
			xx001.setTextColor(getResources().getColor(R.color.mainblue));
			xx002.setTextColor(getResources().getColor(R.color.inputblack));
			xx003.setTextColor(getResources().getColor(R.color.inputblack));
			xxf001.setTextColor(getResources().getColor(R.color.mainblue));
			xxf002.setTextColor(getResources().getColor(R.color.inputblack));
			xxf003.setTextColor(getResources().getColor(R.color.inputblack));
		} else if (tt == 2) {
			main_ll001.setVisibility(View.GONE);
			main_ll002.setVisibility(View.VISIBLE);
			main_ll003.setVisibility(View.GONE);
			three_choice_main.setVisibility(View.VISIBLE);
			xx001.setTextColor(getResources().getColor(R.color.inputblack));
			xx002.setTextColor(getResources().getColor(R.color.mainblue));
			xx003.setTextColor(getResources().getColor(R.color.inputblack));
			xxf001.setTextColor(getResources().getColor(R.color.inputblack));
			xxf002.setTextColor(getResources().getColor(R.color.mainblue));
			xxf003.setTextColor(getResources().getColor(R.color.inputblack));
		} else if (tt == 3) {
			main_ll001.setVisibility(View.GONE);
			main_ll002.setVisibility(View.GONE);
			main_ll003.setVisibility(View.VISIBLE);
			three_choice_main.setVisibility(View.VISIBLE);
			xx001.setTextColor(getResources().getColor(R.color.inputblack));
			xx002.setTextColor(getResources().getColor(R.color.inputblack));
			xx003.setTextColor(getResources().getColor(R.color.mainblue));
			xxf001.setTextColor(getResources().getColor(R.color.inputblack));
			xxf002.setTextColor(getResources().getColor(R.color.inputblack));
			xxf003.setTextColor(getResources().getColor(R.color.mainblue));
		} else {
			main_ll001.setVisibility(View.GONE);
			main_ll002.setVisibility(View.GONE);
			main_ll003.setVisibility(View.GONE);
			three_choice_main.setVisibility(View.GONE);
			xx001.setTextColor(getResources().getColor(R.color.inputblack));
			xx002.setTextColor(getResources().getColor(R.color.inputblack));
			xx003.setTextColor(getResources().getColor(R.color.inputblack));
			xxf001.setTextColor(getResources().getColor(R.color.inputblack));
			xxf002.setTextColor(getResources().getColor(R.color.inputblack));
			xxf003.setTextColor(getResources().getColor(R.color.inputblack));
		}

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

	// 刷新车信息
	public static void refresh_car() {
		car_logo.setImageURI(ResourceUtil.getImageUriFromAssets(UserCarDataSave.get(activity, "BrandLogo")));
		car_name.setText(UserCarDataSave.get(activity, "CarName"));
		car_km.setText(UserCarDataSave.get(activity, "CarMiles") + "公里");

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
		setListViewHeight003(mainListView);
	}

	// listview 高度计算
	public static void setListViewHeight003(ListView listView) {
		int totalHeight = 0;
		ListAdapter listAdapter = listView.getAdapter();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItemView = listAdapter.getView(i, null, listView);
			int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
			listItemView.measure(desiredWidth, 0);
			totalHeight += listItemView.getMeasuredHeight() + 15;
			System.out.println("totalHeight=refresh=======" + totalHeight);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight+20;
		
		listView.setLayoutParams(params);
	}

	

	/***
	 * 所有数据
	 */
	public void alldata() {
		for (int i = 0; i < item.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("DistrictName", item[i]);
			map.put("DistrictId", i + "");
			if (i == 0) {
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
		getCityAra();
	}

	// 获取用户的归属信息的网店信息
	public void getshop() {
		BaseActivity.Dialogcancel();
		HttpUserInfo.getUserShopInfo(new CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				System.out.println("===" + alldata);
				try {
					JSONObject object = data.getJSONObject("Data");
					Map<String, String> map = CollectionUtil.jsonObjectToMap_String(object);
					for (String key : map.keySet()) {
						Log.e("map遍历", "key= " + key + " and value= " + map.get(key));
						StoreToDataSave.save(MainDiscovery.this, key, map.get(key));
					}
					// 判断售前
					if (StoreToDataSave.get(MainDiscovery.this, "BeforeServiceFlag").equals("1")) {
						Map<String, String> map001 = new HashMap<String, String>();
						map001.put("UserId", StoreToDataSave.get(MainDiscovery.this, "BeforeServiceEmpId"));
						map001.put("UserName", StoreToDataSave.get(MainDiscovery.this, "BeforeServiceEmpName"));
						map001.put("IconUrl", StoreToDataSave.get(MainDiscovery.this, "BeforeServiceEmpIconUrl"));
						list_data.add(map001);
					}
					// 判断售后顾问
					if (StoreToDataSave.get(MainDiscovery.this, "AfterServiceFlag").equals("1")) {
						Map<String, String> map002 = new HashMap<String, String>();
						map002.put("UserId", StoreToDataSave.get(MainDiscovery.this, "AfterServiceEmpId"));
						map002.put("UserName", StoreToDataSave.get(MainDiscovery.this, "AfterServiceEmpName"));
						map002.put("IconUrl", StoreToDataSave.get(MainDiscovery.this, "AfterServiceEmpIconUrl"));
						list_data.add(map002);
					}
					provider();
					Intent intent = new Intent(MainDiscovery.this, ConversationListActivity.class);
					startActivity(intent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, UserLoginStatus.get(MainDiscovery.this, "Token"), LocationDataSave.get(MainDiscovery.this, "Latitude"),
				LocationDataSave.get(MainDiscovery.this, "Longitude"), MainDiscovery.this);

	}

	// 设置内容提供者
	public void provider() {
		/**
		 * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
		 *
		 * @param userInfoProvider
		 *            用户信息提供者。
		 * @param isCacheUserInfo
		 *            设置是否由 IMKit 来缓存用户信息。<br>
		 *            如果 App 提供的 UserInfoProvider
		 *            每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
		 *            此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
		 * @see UserInfoProvider
		 */
		RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

			@Override
			public UserInfo getUserInfo(String userId) {
				// TODO Auto-generated method stub
				// 判断返回的userId
				Map<String, String> map = new HashMap<String, String>();
				map.put("UserId", UserLoginStatus.get(MainDiscovery.this, "UserId"));
				map.put("UserName", UserLoginStatus.get(MainDiscovery.this, "UserName"));
				map.put("IconUrl", UserLoginStatus.get(MainDiscovery.this, "IconUrl"));
				list_data.add(map);
				try {
					for (int i = 0; i < list_data.size(); i++) {
						if (list_data.get(i).get("UserId").toString().equals(userId)) {
							return new UserInfo(list_data.get(i).get("UserId").toString(),
									list_data.get(i).get("UserName").toString(), Uri.parse(Config.IMAGE_SERVER_URL + "/"
											+ list_data.get(i).get("IconUrl").toString()));
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
				return null;
			}
		}, true);

	}

	// 建立与融云服务器的连接
	private void connect(String token) {

		if (getApplicationInfo().packageName.equals(BaseApplication.getCurProcessName(getApplicationContext()))) {
			/**
			 * IMKit SDK调用第二步,建立与服务器的连接
			 */
			RongIM.connect(token, new RongIMClient.ConnectCallback() {
				/**
				 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的
				 * Token
				 */
				@Override
				public void onTokenIncorrect() {
					Toast.makeText(MainDiscovery.this, "聊天功能无法开启！错误码:身份过期", 1).show();
				}

				/**
				 * 连接融云成功
				 * 
				 * @param userid
				 *            当前 token
				 */
				@Override
				public void onSuccess(String userid) {
					OnlyOneDataSave.save(MainDiscovery.this, "rongyun", "1");
					getshop();
				}

				/**
				 * 连接融云失败
				 * 
				 * @param errorCode
				 *            错误码，可到官网 查看错误码对应的注释
				 */
				@Override
				public void onError(RongIMClient.ErrorCode errorCode) {
					Toast.makeText(MainDiscovery.this, "聊天功能无法开启！错误码=" + errorCode, 1).show();
				}
			});
		}
	}

	// ==结束当前页面
	public static void finish_page() {
		activity.finish();

	}

	// =============
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		top_height = rootView.getHeight();
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
		leftView.setText("");
		centerView.setText("发现");
		rightView.setBackgroundResource(R.drawable.chatnews);
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
				if (OnlyOneDataSave.get(MainDiscovery.this, "rongyun").equals("1")) {
					getshop();

				} else {
					connect(UserLoginStatus.get(MainDiscovery.this, "RongToken"));
				}
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.discovery_xml, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
	}

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
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(1011);
		String refresh_time= new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss", Locale.CHINA)
				.format(new Date(System.currentTimeMillis()));
		mPullRefreshScrollView.setLastUpdatedLabel("上次更新时间：\n"+refresh_time);
		GPS_Positioning gps_Positioning=new GPS_Positioning(MainDiscovery.this, handler);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(102);
	}

}
