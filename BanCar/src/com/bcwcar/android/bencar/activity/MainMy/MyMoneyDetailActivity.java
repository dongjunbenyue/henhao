package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.adapter.JinEAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpWallet;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.widget.XlistView.XListView;
import com.bcwcar.android.bencar.widget.XlistView.XListView.IXListViewListener;
import com.google.gson.JsonObject;
import com.squareup.okhttp.Request;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.NumberPicker.Formatter;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;

/**
 * 我的金额明细页
 */
public class MyMoneyDetailActivity extends BaseActivity
		implements IXListViewListener{
	private static final String LOG_TAG = MyMoneyDetailActivity.class.getSimpleName();

	private TextView mSelectDateText, today_time, time_xuanze, quxiao, queren, cash;
	private String search_time = new SimpleDateFormat("yyyy-MM", Locale.CHINA)
			.format(new Date(System.currentTimeMillis()));
	private String search_time001 = new SimpleDateFormat("yyyy年MM月", Locale.CHINA)
			.format(new Date(System.currentTimeMillis()));
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private XListView xlistview;
	private JinEAdapter jinEAdapter;
	private LinearLayout shijianxuanzeqi;
	private NumberPicker mNumberPickerYear, mNumberPickerMonth;
	private int mNumberPickerYearValue;
	private int mNumberPickerMonthValue;
	private int page = 1;
	private int pagenum = 10;
	private int TotalCount = 0;

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("消费明细");
		rightView.setText("金额说明");
		rightView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 显示 和隐藏还有问题。。。修改中
				Intent intent = new Intent(MyMoneyDetailActivity.this, JinEShuoMing.class);
				startActivity(intent);
				finish();

			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_my_money_detail, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// LayoutInflater.from(this).inflate(R.layout.common_toolbar_1,
		// toolBarParentView);
	}

	// baseUrl==http://120.76.41.174/AppFrameWork/app/userBank/getUserWalletList
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initview();
		initdata("1", "10");
		
	}

	
	// 实例化控件
	public void initview() {
		
		xlistview = (XListView) findViewById(R.id.XListView_money_detail001);
		
		xlistview.setXListViewListener(this);
		xlistview.setPullLoadEnable(true);

	}

	// 时间转换
	public void time(String search) {
		try {
			String[] temp = search.split("-");
			mNumberPickerYearValue = Integer.valueOf(temp[0]).intValue();
			mNumberPickerMonthValue = Integer.valueOf(temp[1]).intValue();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 获取用户金额明细数据
	public void initdata(String PageNum, String PageCount) {
		
		HttpWallet.getUserWalletList(new CallbackLogic() {
			
			
			
		

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					

					String string = alldata.getString("TotalCount");

					TotalCount = Integer.parseInt(string);
					 if (TotalCount - page * 10 < 0) {
						xlistview.set_booter_gone();
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				jiexi_json(data);
			}
			
		

			
		}, UserLoginStatus.get(MyMoneyDetailActivity.this, "Token"), PageNum, PageCount,"2016-5",MyMoneyDetailActivity.this);

	}

	// 数据解析
	public void jiexi_json(JSONObject data) {
		try {
			JSONObject object = data.getJSONObject("Data");
			String cash_total = object.getString("Cash");
			
			JSONArray array = object.getJSONArray("CashList");
			for (int i = 0; i < array.length(); i++) {
				JSONObject object2 = array.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Description", object2.get("Description").toString());
				map.put("WalletTime", object2.get("WalletTime").toString());
				map.put("Wallet", object2.get("Wallet").toString());
				list.add(map);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jinEAdapter = new JinEAdapter(getApplicationContext(), list);
		xlistview.setAdapter(jinEAdapter);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		xlistview.set_booter_text("查看更多");
		page = 1;
		list.clear();
		initdata(String.valueOf(page), String.valueOf(pagenum)
				);
		
		jinEAdapter.notifyDataSetChanged();
		xlistview.stopLoadMore();
		xlistview.stopRefresh();
	}

	@Override
	public void onLoadMore() {
		
		if (TotalCount - page * 10< 0) {
			xlistview.set_booter_text("没有更多数据了");
			xlistview.stopLoadMore();
			xlistview.stopRefresh();
			return;
		} else {
		
		
		page++;
		initdata(String.valueOf(page), String.valueOf(pagenum));
		
		jinEAdapter.notifyDataSetChanged();
		xlistview.stopLoadMore();
		xlistview.stopRefresh();
		}
	}


}
