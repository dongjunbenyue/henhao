package com.bcwcar.android.bencar.activity.MainHome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.R.string;
import com.bcwcar.android.bencar.activity.SelectCarActivity;
import com.bcwcar.android.bencar.activity.SelectCityActivity;
import com.bcwcar.android.bencar.activity.SelectShop4S;
import com.bcwcar.android.bencar.activity.SmartMaintenanceActivity;
import com.bcwcar.android.bencar.activity.MainMy.LoginActivity;
import com.bcwcar.android.bencar.adapter.MyFragmentPagerAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpUserCar;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.widget.CustomViewPager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonArray;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import qrcode.activity.CaptureActivity;

/**
 * 主页|Home页
 */
public class MainHomeFinishActivity extends Fragment {

	/// ===============未登录状态头部广告滑动页面============
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private MyFragmentPagerAdapter myFragmentPagerAdapter;
	private CustomViewPager pager_top;
	private RadioGroup radiogrounp;
	private int[] radioButtonID = new int[] { R.id.radio001, R.id.radio002 };
	private String url001 = "asset:///image/car_hei.png";
	private String url002 = "asset:///image/car_hong.png";
	/// ===============GridView滑动显示保养方案==================
	private CustomViewPager huandong_viewpager;
	private List<Map<String, String>> list_baoyang = new ArrayList<Map<String, String>>();
	private List<Fragment> fragmentList_baoyang = new ArrayList<Fragment>();
	private MyFragmentPagerAdapter myFragmentPagerBaoYang;
	private TextView km_befor, money_befor, back_befor, km_after, money_after, back_after;
	private ImageView aicar_image;
	// ===查看保养方案==
	private TextView car_case;
	private int tt = 0;
	// ======默认车型=====
	private static SimpleDraweeView car_logo;
	private RelativeLayout defulat_car_RelativeLayout;
	private static TextView car_year_TextView;
	private static TextView car_chepai_TextView;
	private static Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.main_home_xml002, container, false);// 关联布局文件
		BaseActivity.changeFonts((ViewGroup) rootView);
		context=getActivity();
		testdata();
		initview(rootView);
		getusercar();
		return rootView;
	}


	public void initview(View view) {
		// ===查看保养方案==
		car_case = (TextView) view.findViewById(R.id.TextView_chakan_car_case001);
		car_case.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),SmartMaintenanceActivity.class);
				intent.putExtra(BizDefineAll.BIZ_ACTION, "MainHomeFinishActivity");
				startActivity(intent);
				
			}
		});
		// 未登录状态头部广告滑动页面================
		radiogrounp = (RadioGroup)view. findViewById(R.id.radiogroup001);
		pager_top = (CustomViewPager) view.findViewById(R.id.viewpager_home_buttom);
		Bundle bundle=new Bundle();
		bundle.putCharSequence("url", url001);
		MainHomeUserCarFragment ff = new MainHomeUserCarFragment().newInstance(bundle);
		fragmentList.add(ff);
		Bundle bundle2=new Bundle();
		bundle2.putCharSequence("url", url002);
		MainHomeUserCarFragment ff2 = new MainHomeUserCarFragment().newInstance(bundle2);
		fragmentList.add(ff2);
		myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList);
		pager_top.setAdapter(myFragmentPagerAdapter);
		pager_top.setCurrentItem(0);
		radiogrounp.check(radioButtonID[0]);
		pager_top.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				radiogrounp.check(radioButtonID[arg0]);
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
		/// ===============GridView滑动显示保养方案==================
		km_after = (TextView) view.findViewById(R.id.TextView_baoyangmoving_km_number_after001);
		km_befor = (TextView) view.findViewById(R.id.TextView_baoyangmoving_km_number_before001);
		money_after = (TextView) view.findViewById(R.id.TextView_baoyangmoving_price_after001);
		money_befor = (TextView) view.findViewById(R.id.TextView_baoyangmoving_price_before001);
		back_after = (TextView) view.findViewById(R.id.TextView_baoyangmoving_price_to_back_after001);
		back_befor = (TextView) view.findViewById(R.id.TextView_baoyangmoving_price_to_back_before001);
		aicar_image = (ImageView) view.findViewById(R.id.ImageView_aicar_get_before001);
		huandong_viewpager = (CustomViewPager) view.findViewById(R.id.viewpager_home_baoyang001);
		for (int i = 0; i < list_baoyang.size(); i++) {
			Bundle bundle01=new Bundle();
			bundle01.putCharSequence("km", 1000+i+"");
			bundle01.putCharSequence("money", "￥100"+i+"");
			bundle01.putCharSequence("back", "￥"+10+i+"");
			bundle01.putCharSequence("postion", i+"");
			MainHomeUserBaoYangFragment listFragment = new MainHomeUserBaoYangFragment().newInstance(bundle01);
			fragmentList_baoyang.add(listFragment);
		}
		myFragmentPagerBaoYang = new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList_baoyang);
		huandong_viewpager.setAdapter(myFragmentPagerBaoYang);
		huandong_viewpager.setCurrentItem(1);
		if (list_baoyang.size() >= 3) {
			textview_set(1);
		}
		huandong_viewpager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int postion) {
				// TODO Auto-generated method stub
				// radiogrounp.check(radioButtonID[arg0]);
				if (postion == 0) {
					huandong_viewpager.setCurrentItem(1);
				} else if (postion == 1) {
					money_befor.setVisibility(View.VISIBLE);
					back_befor.setVisibility(View.GONE);
					aicar_image.setVisibility(View.VISIBLE);
					textview_set(postion);
				} else if (postion == fragmentList_baoyang.size() - 1) {
					money_befor.setVisibility(View.VISIBLE);
					back_befor.setVisibility(View.VISIBLE);
					aicar_image.setVisibility(View.GONE);
					km_befor.setText(list_baoyang.get(postion - 1).get("km"));
					money_befor.setText(list_baoyang.get(postion - 1).get("money"));
					back_befor.setText(list_baoyang.get(postion - 1).get("back"));
					money_after.setText("");
					km_after.setText("");
					back_after.setText("");
				} else {
					money_befor.setVisibility(View.VISIBLE);
					back_befor.setVisibility(View.VISIBLE);
					aicar_image.setVisibility(View.GONE);
					textview_set(postion);
				}
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
		// ================默认车型==============
		defulat_car_RelativeLayout = (RelativeLayout) view.findViewById(R.id.RelativeLayout_user_cars001);
		defulat_car_RelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), SelectCarActivity.class);
				intent.putExtra("action", "MainHomeFinishActivity");
				startActivity(intent);
			}
		});
		car_logo = (SimpleDraweeView) view.findViewById(R.id.SimpleDraweeView_usercar_brand_logo);
		car_year_TextView = (TextView)view. findViewById(R.id.TextView_usercar_info001);
		car_chepai_TextView = (TextView) view.findViewById(R.id.TextView_usercar_info_usercar_card001);
	}

	// 方案滚动显示
	public void textview_set(int postion) {
		km_befor.setText(list_baoyang.get(postion - 1).get("km"));
		money_befor.setText(list_baoyang.get(postion - 1).get("money"));
		back_befor.setText(list_baoyang.get(postion - 1).get("back"));
		money_after.setText(list_baoyang.get(postion + 1).get("money"));
		km_after.setText(list_baoyang.get(postion + 1).get("km"));
		back_after.setText(list_baoyang.get(postion + 1).get("back"));

	}

	// 获取默认车型
	public void getusercar() {
		BaseActivity.Dialogcancel();
		HttpUserCar.getUserDefaultCarInfo(new CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					JSONObject object = data.getJSONObject("Data");
					Map<String, String> map = new HashMap<String, String>();
					map = CollectionUtil.jsonObjectToMap_String(object);
					for (String key : map.keySet()) {
						Log.e("map遍历", "key= " + key + " and value= " + map.get(key));
						UserCarDataSave.save(getActivity(), key, map.get(key));
					}
					String string=object.getString("BuyDate").toString();
					String[] temp=string.split("年");
					String[] temp2=temp[1].split("月");
					UserCarDataSave.save(getActivity(), "BuyDate", temp[0]+"-"+temp2[0]);
					System.out.println("============="+UserCarDataSave.get(getActivity(), "BuyDate"));
					car_logo.setImageURI(
							ResourceUtil.getImageUriFromAssets(UserCarDataSave.get(getActivity(), "BrandLogo")));
					car_year_TextView.setText(UserCarDataSave.get(getActivity(), "CarName"));
					car_chepai_TextView.setText(UserCarDataSave.get(getActivity(), "CarMiles") + "KM");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},UserLoginStatus.get(getActivity(),"Token"), getActivity());

	}
	public static void refresh(){
		car_logo.setImageURI(
				ResourceUtil.getImageUriFromAssets(UserCarDataSave.get(context, "BrandLogo")));
		car_year_TextView.setText(UserCarDataSave.get(context, "CarName"));
		car_chepai_TextView.setText(UserCarDataSave.get(context, "CarMiles") + "KM");
		
	}
	public void testdata() {
		Map<String, String> map001 = new HashMap<String, String>();
		map001.put("money", "");
		map001.put("back", "");
		map001.put("km", "爱车到手");
		list_baoyang.add(map001);
		for (int i = 0; i < 10; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("money", "¥400");
			map.put("back", "返 ¥100");
			map.put("km", (1000 + i) + "km");
			list_baoyang.add(map);
		}

	}

}