package com.bcwcar.android.bencar.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.adapter.WangDianListAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpMainServiceNetwork;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
/**
 * 选择4S店
 */
public class SelectShop4S extends BaseActivity {
	private ListView lv;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
private  static Activity activity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity=SelectShop4S.this;
		initView();
		initdata();
	}

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
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
		centerView.setText("选择4s店");
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SelectShop4S.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.store4s_select, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

	public void initView() {
		// TODO Auto-generated method stub
		lv = (ListView) findViewById(R.id.wangdian_listview001);
	}

	public void initdata() {
		HttpMainServiceNetwork.getShowShopListByCar(new CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				Dialogcancel();
				jiexi_json(data);
				WangDianListAdapter wangDianListAdapter = new WangDianListAdapter(getApplicationContext(), list,
						SelectShop4S.this);
				lv.setAdapter(wangDianListAdapter);
			}
		}, UserLoginStatus.get(SelectShop4S.this,"Token"),DingDanDataSave.get(SelectShop4S.this, "AccIds"), "1", "20",
				UserCarDataSave.get(SelectShop4S.this, "CarId"), "",LocationDataSave.get(SelectShop4S.this, "CityId"),LocationDataSave.get(SelectShop4S.this, "CityName"),LocationDataSave.get(SelectShop4S.this, "Longitude"),LocationDataSave.get(SelectShop4S.this, "Latitude"), SelectShop4S.this);
	}
//==结束当前页面
	public static void finish_page(){
		activity.finish();
		
	}
	// 数据解析
	public void jiexi_json(JSONObject data) {
		try {
			JSONArray jsonArray = data.getJSONArray("Data");
			list = CollectionUtil.jsonArrayToListMap(jsonArray);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
