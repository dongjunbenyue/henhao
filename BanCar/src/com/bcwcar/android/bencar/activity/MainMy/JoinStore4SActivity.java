package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.activity.SelectCityActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpMainServiceNetwork;
import com.bcwcar.android.bencar.biz.HttpUserCar;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.ListViewUtil;
import com.bcwcar.android.bencar.widget.PullDoorView;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.squareup.okhttp.Request;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.framework.i;

/**
 * 加盟4s店页
 */
public class JoinStore4SActivity extends BaseActivity {
	private static final String LOG_TAG = JoinStore4SActivity.class.getSimpleName();
	public EditText editTextName, editTextPhone;
	private List<Map<String, String>> listdata, mSpinnerBrandData;
	public ImageView imageView;
	private static TextView editTextCity;
	private static TextView editTextBrand;
	private PullDoorView tuituitui;
	private AnimationDrawable aimss;

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
		// leftView.setVisibility(View.GONE);
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
		centerView.setText("4S店申请入驻");
		
		changeFonts(titleParentView);
	}
	public static void set_city(String string) {
		editTextCity.setText(string);
	}
	public static void set_brand(String string){
		editTextBrand.setText(string);}
	private void initview() {
		tuituitui=(PullDoorView)findViewById(R.id.tuituitui);
	    Animation animation=AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		tuituitui.startAnimation(animation);
		editTextCity = (TextView) findViewById(R.id.cityname);
		
		editTextCity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(JoinStore4SActivity.this, SelectCityActivity.class);
				intent.putExtra("action", "JoinStore4SActivity");
				startActivity(intent);
			}
		});
		
		editTextBrand = (TextView) findViewById(R.id.brandname);
		editTextBrand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(JoinStore4SActivity.this, SelectBrandActivity002.class);
				startActivity(intent);
			}
		});

		editTextName = (EditText) findViewById(R.id.contactname);
		editTextPhone = (EditText) findViewById(R.id.contactPhone);

	}
   
	public static String readLocalJson(Context context, String fileName) {
		String jsonString = "";
		String resultString = "";
		try {
			InputStream inputStream = context.getResources().getAssets().open(fileName);
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			resultString = new String(buffer, "UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resultString;
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
	View view=	LayoutInflater.from(this).inflate(R.layout.body_shop4s_join, bodyParentView);
	     
		imageView = (ImageView) findViewById(R.id.chenggong);
		changeFonts(bodyParentView);

	}


	public void onViewClick(View view) {
		switch (view.getId()) {
		case R.id.shop4s_join_bottom:
			String cityName = editTextCity.getText().toString();
			String brandName = editTextBrand.getText().toString();
			String contactName = editTextName.getText().toString();
			String contactPhone = editTextPhone.getText().toString();
			if (cityName.equals("")|| brandName.equals("") || contactName.equals("") ||contactPhone.equals("")) {
				showToast("请填写完整的数据");
			} else {
				tijiao_data(UserLoginStatus.get(getApplicationContext(), "CityName"), UserLoginStatus.get(getApplicationContext(), "CityId"), UserLoginStatus.get(getApplicationContext(), "BrandId"),
						UserLoginStatus.get(getApplicationContext(), "Brand"), contactName,
						contactPhone);
			}

			break;
		case R.id.chenggong:
			imageView.setVisibility(view.GONE);
			Intent intent = new Intent();
			intent.setClass(JoinStore4SActivity.this, MainActivity.class);
			startActivity(intent);
		default:
			break;
		}

	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// 没有工具栏，不用设置！
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initview();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		if (!get_jiameng_data("CityName").equals("")) {
//			
//			editTextCity.setText(get_jiameng_data("CityName"));
//		}else {
//			editTextCity.setHint(getString(R.string.shop4s_join_city_hint));
//		}
//if (!get_jiameng_data("Brand").equals("")) {
//			
//			editTextBrand.setText(get_jiameng_data("Brand"));
//		}else {
//			editTextBrand.setHint(getString(R.string.shop4s_join_brand_hint));
//		}
	}

	public void tijiao_data(String cityName, String cityId, String brandId, String brandName, String contactName,
			String contactPhone) {
		
		HttpMainServiceNetwork.addShop(new CallbackLogic() {

			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				showToast("提交成功");
				finish();
			}
		}, UserLoginStatus.get(getApplicationContext(), "Token"), cityName, cityId, brandId, brandName, contactName,
				contactPhone,JoinStore4SActivity.this);
	}
}
