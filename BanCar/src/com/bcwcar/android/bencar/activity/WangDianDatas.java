package com.bcwcar.android.bencar.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.R.string;
import com.bcwcar.android.bencar.activity.MainDiscovery.MainDiscovery;
import com.bcwcar.android.bencar.activity.MainMy.FreeMantence;
import com.bcwcar.android.bencar.activity.MainMy.LoginActivity;
import com.bcwcar.android.bencar.adapter.ShopDianPingAdapter;
import com.bcwcar.android.bencar.adapter.ShopQuanAdapter;
import com.bcwcar.android.bencar.adapter.mainShopServiceItemAdapter;
import com.bcwcar.android.bencar.adapter.mainShopServiceItemAdapter002;
import com.bcwcar.android.bencar.base.BaiDuNavigation;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.GPS_Positioning;
import com.bcwcar.android.bencar.base.ImageFileCache;
import com.bcwcar.android.bencar.base.ImageGetFromHttp;
import com.bcwcar.android.bencar.base.ImageMemoryCache;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpMainServiceNetwork;
import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.QuanDingDanDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.AsyncBitmapLoader;
import com.bcwcar.android.bencar.http.AsyncBitmapLoader.ImageCallBack;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.NumberFormatTest;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Request;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableLayout.LayoutParams;

public class WangDianDatas extends BaseActivity {
	private ImageView muhu;
	private Bitmap bitmap;
	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;
	private ImageView up_down;
	private String mapflag="0";
	// =====
	private SimpleDraweeView shoplogo_SimpleDraweeView;
	private RatingBar myratingbar;
	private TextView shopname, shopadress, shop_image_numbers,loading_more,project_num,more_project_num;
	private ShopDianPingAdapter dianPingAdapter;
	private ShopQuanAdapter quanAdapter;
	private mainShopServiceItemAdapter002 adapter_jingping;
	// ===========
	private Map<String, String> map = new HashMap<String, String>();
	private List<Map<String, String>> list_project = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_project_ing = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list002 = new ArrayList<Map<String, String>>();
	private TextView phone, adrress, yuding001, changsahgnname, changsahgncar, shop_dianping_number;
	private LinearLayout dianhua, dingwei_daohang,more_linearlayout,more_project_hanzi;
	private static String[] ShopPics;
	private BaiDuNavigation openBaiduMap;
	private ListView quan_listview, dianping_listview;
	private  boolean jingping_isshow=false;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 101) {
               
                	openBaiduMap = new BaiDuNavigation(getApplicationContext(),
    						Double.valueOf(LocationDataSave.get(WangDianDatas.this, "Latitude")).doubleValue(),
    						Double.valueOf(LocationDataSave.get(WangDianDatas.this, "Longitude")).doubleValue(),
    						Double.valueOf(map.get("Latitude").toString()).doubleValue(),
    						Double.valueOf(map.get("Longitude").toString()).doubleValue());
   				    openBaiduMap.startNavi();
    				System.out.println(map.get("Latitude").toString()+","+map.get("Longitude").toString());
			
				// ===========
			} else if (msg.what == 0) {
				System.out.println("=================" + msg.obj.toString());
				bitmap = (Bitmap) msg.obj;
				if (bitmap != null) {
					blur(bitmap, muhu,WangDianDatas.this);
					fileCache.saveBitmap(bitmap, Config.IMAGE_SERVER_URL + "/" + map.get("LogoUrl").toString());
					memoryCache.addBitmapToCache(Config.IMAGE_SERVER_URL + "/" + map.get("LogoUrl").toString(), bitmap);

				}
			}
		};

	};
	// ============
	private String action;
	private String totalcount;
	private int current=1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		list_project.clear();
		memoryCache = new ImageMemoryCache(this);
		fileCache = new ImageFileCache();
		try {
			action = getIntent().getStringExtra(BizDefineAll.BIZ_ACTION);
		} catch (Exception e) {
			// TODO: handle exception
		}
		initview();
		initdata();
		getshoppinglun();
		
		
	}
   @Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	
	
}
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
		// leftView.setVisibility(View.GONE);
		leftView.setText("");

		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("4S店信息");
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WangDianDatas.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.wang_dian_xiang_xi001, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

	// 实例化控件
	public void initview() {
		 up_down=(ImageView)findViewById(R.id.ImageView_up_or_down001);
		 more_project_hanzi=(LinearLayout)findViewById(R.id.LinearLayout_more_project_text_hanzi001);
		more_project_num=(TextView)findViewById(R.id.TextView_num_text001);
		more_linearlayout=(LinearLayout)findViewById(R.id.LinearLayout_more_project001);
		more_linearlayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (list_project.size()>=2) {
					if (jingping_isshow) {
						jingping_isshow=false;
						list_project_ing.clear();
						for (int i =0; i <2; i++) {
							list_project_ing.add(list_project.get(i));
						}
						adapter_jingping.notifyDataSetChanged();
						setListViewHeight(quan_listview);
						more_project_num.setText(new NumberFormatTest().foematInteger(list_project.size()-2)+"");
						more_project_hanzi.setVisibility(View.VISIBLE);
						up_down.setBackgroundResource(R.drawable.down);
					}else {
						list_project_ing.clear();
						for (int i =0; i <list_project.size(); i++) {
							list_project_ing.add(list_project.get(i));
						}
						adapter_jingping.notifyDataSetChanged();
						setListViewHeight(quan_listview);
						jingping_isshow=true;
						more_project_hanzi.setVisibility(View.GONE);
						up_down.setBackgroundResource(R.drawable.up);
					}
				}
			}
		});
		project_num=(TextView)findViewById(R.id.TextView_qita_number001);
		loading_more=(TextView)findViewById(R.id.TextView_loading_more_pinglun001);
		loading_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int all=Integer.parseInt(totalcount);
				if (current*10>all) {
					loading_more.setText("没用更多数据");
					showToast("没用更多数据");
				}else {
					regetshoppinglun(current);
					current++;
				}
			}
		});
		muhu = (ImageView) findViewById(R.id.ImageView_muhu001);

		shop_dianping_number = (TextView) findViewById(R.id.TextView_shop_dianping_number001);
		shoplogo_SimpleDraweeView = (SimpleDraweeView) findViewById(R.id.wangdian_logo001);
		shoplogo_SimpleDraweeView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WangDianDatas.this, ShopImageShowActivity.class);
				intent.putExtra("image", ShopPics);
				startActivity(intent);
			}
		});
		shopadress = (TextView) findViewById(R.id.TextView_shopadress001);
		shopname = (TextView) findViewById(R.id.TextView_shop_info_shopname001);
		myratingbar = (RatingBar) findViewById(R.id.ratingBar_shopstar_info001);
		shop_image_numbers = (TextView) findViewById(R.id.TextView_shop_show_number001);
		dingwei_daohang = (LinearLayout) findViewById(R.id.dingwei_daohang0011);
		dingwei_daohang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new GPS_Positioning(WangDianDatas.this, mHandler).gps();
				
				
			}
		});
		dianhua = (LinearLayout) findViewById(R.id.LinearLayout_boda_dianhua001);
		dianhua.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 拨打电话
				String phoneNumber = phone.getText().toString();
				if (!phoneNumber.matches("\\d+")) {
					Toast.makeText(WangDianDatas.this, R.string.shop4s_info_call_phone_error, Toast.LENGTH_SHORT)
							.show();
				} else {
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
					startActivity(intent);
				}

			}
		});
		phone = (TextView) findViewById(R.id.Textview_wangdian_xiangxi_phone);
		adrress = (TextView) findViewById(R.id.Textview_wangdian_xiangxi_adrress);
		changsahgnname = (TextView) findViewById(R.id.TextView_changshang_name);
		changsahgncar = (TextView) findViewById(R.id.TextView_changshang_car_brand);
		yuding001 = (TextView) findViewById(R.id.wangdian_xiangxi_yuding001);
		if (action.equals("MainDiscovery")) {
			yuding001.setVisibility(View.GONE);
		}else if (action.equals("DiscoverProjectDetailActivity")) {
			yuding001.setVisibility(View.GONE);
		}else {
			yuding001.setVisibility(View.VISIBLE);
		}
		yuding001.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserLoginStatus.isLoggedOn(WangDianDatas.this)) {

					if (action.equals("MainHomeActivity")) {

						for (String key : map.keySet()) {
							Log.e("map遍历", "key= " + key + " and value= " + map.get(key));
							DingDanDataSave.save(WangDianDatas.this, key, map.get(key));
						}
						Intent intent = new Intent(WangDianDatas.this, CarInformationActivity.class);
						intent.putExtra(BizDefineAll.BIZ_ACTION, "WangDianDatas");
						startActivity(intent);
						finish();

					} else if (action.equals("SelectShop4S")) {

						for (String key : map.keySet()) {
							Log.e("map遍历", "key= " + key + " and value= " + map.get(key));
							DingDanDataSave.save(WangDianDatas.this, key, map.get(key));
						}
						SelectShop4S.finish_page();
						finish();
					} else if (action.equals("MainServiceNetworkActivity")) {

						for (String key : map.keySet()) {
							Log.e("map遍历", "key= " + key + " and value= " + map.get(key));
							DingDanDataSave.save(WangDianDatas.this, key, map.get(key));
						}
						Intent intent = new Intent(WangDianDatas.this, CarInformationActivity.class);
						intent.putExtra(BizDefineAll.BIZ_ACTION, "WangDianDatas");
						startActivity(intent);
						finish();
						MainServiceNetworkActivity.finish_page();
					}
				} else {
					Intent intent = new Intent(WangDianDatas.this, LoginActivity.class);
					startActivity(intent);

				}
			}
		});

		// =============test
		dianping_listview = (ListView) findViewById(R.id.ListView_shop_dianping001);
		dianping_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub

			}
		});
		quan_listview = (ListView) findViewById(R.id.ListView_shop_quan001);
		quan_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (action.equals("SelectShop4S")) {
					showToast("请前往发现中心购买，谢谢合作");
				}else {
					for (String key : list_project.get(position).keySet()) {
						QuanDingDanDataSave.save(WangDianDatas.this, key, list_project.get(position).get(key));
					}
					for(String key:map.keySet()){
						QuanDingDanDataSave.save(WangDianDatas.this, key,map.get(key));
					}
					
					Intent intent = new Intent(WangDianDatas.this, ShopQuanQuitActivity.class);
					startActivity(intent);
				}
				}
		});
	}
	// 点评listview 高度计算
	public static void setListViewHeight_other(ListView listView) {
		int totalHeight = 0;
		ListAdapter listAdapter = listView.getAdapter();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItemView = listAdapter.getView(i, null, listView);
			TextView content = (TextView) listItemView.findViewById(R.id.TextView_shopadress001);
			int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
			listItemView.measure(desiredWidth, 0);
			totalHeight += listItemView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + 20;
		listView.setLayoutParams(params);
	}

	// 获取网点详情
	public void initdata() {
		HttpMainServiceNetwork.getShopDetails(new CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				jiexi_json(data);
				 adapter_jingping=new mainShopServiceItemAdapter002(getApplicationContext(), list_project_ing);
				quan_listview.setAdapter(adapter_jingping);
				setListViewHeight(quan_listview);
				project_num.setText(list_project.size()+"");
				if (list_project.size()>2) {
					more_project_num.setText(new NumberFormatTest().foematInteger(list_project.size()-2)+"");
				}else {
					more_linearlayout.setVisibility(View.GONE);
				}
				// /======
				shop_image_numbers.setText(ShopPics.length + "");
				ResourceUtil.setSimpleDraweeViewImage(shoplogo_SimpleDraweeView, map.get("LogoUrl").toString());
				myratingbar.setRating(Integer.parseInt(map.get("Score").toString()));
				shopname.setText(map.get("ShopName").toString());
				phone.setText(map.get("ContactPhone").toString());
				shopadress.setText(map.get("Address").toString());
				adrress.setText(map.get("Address").toString());
				changsahgncar.setText(map.get("SeriesNames").toString());
				changsahgnname.setText(map.get("ProviderNames").toString());
				bitmap = getBitmap(Config.IMAGE_SERVER_URL + "/" + map.get("LogoUrl").toString());
				if (bitmap != null) {
					blur(bitmap, muhu,WangDianDatas.this);
				}

			}

		}, UserLoginStatus.get(WangDianDatas.this, "Token"), DingDanDataSave.get(WangDianDatas.this, "ShopId"),
				WangDianDatas.this);

	}

	//获取网店评论列表
	public void getshoppinglun(){
		Dialogcancel();
		HttpMainServiceNetwork.getshopPinglun(new CallbackLogic() {
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				try {
					 totalcount=alldata.getString("TotalCount").toString();
					 shop_dianping_number.setText(totalcount);
					JSONArray array=data.getJSONArray("Data");
					for (int i = 0; i < array.length(); i++) {
						list002.add(CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i)));
					}
					dianPingAdapter = new ShopDianPingAdapter(list002, WangDianDatas.this);
					dianping_listview.setAdapter(dianPingAdapter);
					setListViewHeight_other(dianping_listview);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}, UserLoginStatus.get(WangDianDatas.this, "Token"), "1", "10", DingDanDataSave.get(WangDianDatas.this, "ShopId"), WangDianDatas.this);
		
	}
	//刷新获取网店评论列表
		public void regetshoppinglun(int tt){
			Dialogcancel();
			HttpMainServiceNetwork.getshopPinglun(new CallbackLogic() {
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
					try {
						JSONArray array=data.getJSONArray("Data");
						for (int i = 0; i < array.length(); i++) {
							list002.add(CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i)));
						}
						dianPingAdapter.notifyDataSetChanged();
						setListViewHeight_other(dianping_listview);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}, UserLoginStatus.get(WangDianDatas.this, "Token"), String.valueOf(tt), "10", DingDanDataSave.get(WangDianDatas.this, "ShopId"), WangDianDatas.this);
		}
	// 下载图片
 	public Bitmap getBitmap(String url) {
		// 从内存缓存中获取图片
		System.out.println("// 从内存缓存中获取图片" + url);
		Bitmap result = memoryCache.getBitmapFromCache(url);
		if (result == null) {
			// 文件缓存中获取
			System.out.println("// 文件缓存中获取" + url);
			result = fileCache.getImage(url);
			if (result == null) {
				// 从网络获取
				ImageGetFromHttp.downloadBitmap(url, mHandler);
			} else {
				// 添加到内存缓存
				memoryCache.addBitmapToCache(url, result);
			}
		}
		return result;
	}

	public void jiexi_json(JSONObject data) {
		try {
			String string = data.getString("Data").toString();
			JSONObject object = new JSONObject(string);
			JSONArray array = object.getJSONArray("ServiceData");
			if (array.length() != 0) {
				for (int i = 0; i < array.length(); i++) {
					list_project.add(CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i)));
				}
			} 
			if (list_project.size()>=2) {
				for (int i = 0; i < 2; i++) {
					more_linearlayout.setVisibility(View.VISIBLE);
					list_project_ing.add(list_project.get(i));
				}
			}else {
				more_linearlayout.setVisibility(View.GONE);
				if (list_project.size()!=0) {
					
					for (int i = 0; i < list_project.size(); i++) {
						list_project_ing.add(list_project.get(i));
					}
				}
			}
			map = CollectionUtil.jsonObjectToMap_String(object);
			if (object.isNull("ShopPics")) {
			} else {
				JSONArray jsonArray2 = object.getJSONArray("ShopPics");
				ShopPics = new String[jsonArray2.length()];
				for (int j = 0; j < jsonArray2.length(); j++) {
					JSONObject jsonObject = jsonArray2.getJSONObject(j);
					ShopPics[j] = jsonObject.getString("url").toString();
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
