package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alipay.android.phone.mrpc.core.x;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.PaymentResultOrderDetailActivity;
import com.bcwcar.android.bencar.activity.PaymentResultOrderDetailActivity1;
import com.bcwcar.android.bencar.adapter.MyOrderPaymentAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.widget.XlistView.XListView;
import com.bcwcar.android.bencar.widget.XlistView.XListView.IXListViewListener;
import com.squareup.okhttp.Request;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyOrderAll extends BaseActivity implements IXListViewListener{

	List<Map<String, String>> mlistsmalldata;
	List<Map<String, String>> mListViewData;
	private String status;
	private int TotalCount = 0;
	private int currentPage = 1;
	XListView xlistview;
	private MyOrderPaymentAdapter adapter;
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true);  //中文字体加粗
		centerView.setText(getString(R.string.my_order_topbar_payment));
		rightView.setVisibility(View.GONE);
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.allorder, bodyParentView);
	}
   @Override
   protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	xlistview=(XListView)findViewById(R.id.allorder);
	xlistview.setXListViewListener(this);
    xlistview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			Bundle bundle = new Bundle();
			bundle.putString("OrderId", mListViewData.get(position - 1).get("OrderId"));
			bundle.putString("payKey", mListViewData.get(position - 1).get("StatusCode"));
			if (mListViewData.get(position-1).get("OrderType").equals("3")) {
				PageUtil.jumpTo(MyOrderAll.this, PaymentResultOrderDetailActivity1.class, bundle);
			}else {
				PageUtil.jumpTo(MyOrderAll.this, PaymentResultOrderDetailActivity.class, bundle);
			}
		}
	});
	xlistview.setPullLoadEnable(true);
	
}
   @Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	getdata();
}
   void getdata() {
		
		HttpOrder.getMyOrderList1(new CallbackLogic002() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				Dialogcancel();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MyOrderAll.this, getString(R.string.network_error_please_try_again),
								Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data, final String other) {
				// TODO Auto-generated method stub
				Dialogcancel();
//				xlistview.stopLoadMore();
//				xlistview.stopRefresh();
				runOnUiThread(new Runnable() {

					private String string;
				
                   
					@Override
					public void run() {
						try {
							JSONObject data1 = new JSONObject(other);

							String string = data1.getString("TotalCount");

							TotalCount = Integer.parseInt(string);
							 if (TotalCount - currentPage * 10 < 0) {
								(xlistview).set_booter_gone();
							}
							JSONArray arrayData = data.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA);
							mListViewData = CollectionUtil.jsonArrayToListMap(arrayData);
							adapter = new MyOrderPaymentAdapter(MyOrderAll.this, mListViewData);
							xlistview.setAdapter(adapter);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});
			}

			@Override
			public void onBizFailure(final String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				Dialogcancel();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MyOrderAll.this, responseDescription, Toast.LENGTH_SHORT).show();
						PageUtil.jumpTo(MyOrderAll.this, LoginActivity.class);
					}
				});
				return;

			}

		}, UserLoginStatus.get(MyOrderAll.this,"Token"), "1", "10", "0");
	}
   void getdata1() {

		Dialogshow(MyOrderAll.this);
		HttpOrder.getMyOrderList1(new CallbackLogic002() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

				Dialogcancel();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MyOrderAll.this, getString(R.string.network_error_please_try_again),
								Toast.LENGTH_SHORT).show();
					}
				});

			}

			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data, final String other) {
				// TODO Auto-generated method stub
				Dialogcancel();
				runOnUiThread(new Runnable() {

					

					@Override
					public void run() {
						try {
							JSONObject data1 = new JSONObject(other);

							String string = data1.getString("TotalCount");

							TotalCount = Integer.parseInt(string);
							 if (TotalCount - currentPage * 10 < 0) {
								xlistview.set_booter_gone();
							}
							JSONArray arrayData = data.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA);
							for (int i = 0; i < arrayData.length(); i++) {
								mListViewData.add(CollectionUtil.jsonObjectToMap(arrayData.getJSONObject(i)));

							}
							adapter.notifyDataSetChanged();

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});
			}

			@Override
			public void onBizFailure(final String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				Dialogcancel();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MyOrderAll.this, responseDescription, Toast.LENGTH_SHORT).show();
						PageUtil.jumpTo(MyOrderAll.this, LoginActivity.class);
					}
				});
				return;

			}
		}, UserLoginStatus.get(MyOrderAll.this,"Token"), String.valueOf(currentPage), "10", "0");
	}
   @Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		currentPage = 1;
		mListViewData.clear();
		getdata1();
		xlistview.stopLoadMore();
		xlistview.stopRefresh();
	}

	
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (TotalCount - currentPage * 10 < 0) {
			Toast.makeText(MyOrderAll.this, "没有更多数据", Toast.LENGTH_SHORT).show();
			xlistview.stopLoadMore();
			xlistview.stopRefresh();
			
			return;
		} else {
			currentPage++;
			getdata1();
			

			xlistview.stopLoadMore();
			xlistview.stopRefresh();
		}
	}
	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}

}
