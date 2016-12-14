package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alipay.android.phone.mrpc.core.r;
import com.squareup.okhttp.Request;
import com.bcwcar.android.bencar.R;

import com.bcwcar.android.bencar.activity.PaymentResultOrderDetailActivity;
import com.bcwcar.android.bencar.activity.PaymentResultOrderDetailActivity1;
import com.bcwcar.android.bencar.adapter.MyOrderPaymentAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;

import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;

import com.bcwcar.android.bencar.widget.XlistView.XListView;
import com.bcwcar.android.bencar.widget.XlistView.XListView.IXListViewListener;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的订单|已付款页，待付款，待评价 使用统一的页面布局，只是适配器不一样
 */
public class MyOrderCommPage extends ListFragment implements IXListViewListener {
	private static final String LOG_TAG = MyOrderCommPage.class.getSimpleName();
	List<Map<String, String>> mlistsmalldata;
	List<Map<String, String>> mListViewData;
	private String status;
	private int TotalCount = 0;
	private int currentPage = 1;
	public  ProgressDialog dialog;
    TextView textView;
	private MyOrderPaymentAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.body_my_order_xlistview, container, false);
		textView=(TextView)v.findViewById(R.id.tishixingxi);
		BaseActivity.changeFonts((ViewGroup)v);
		return v;
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// View footView =
		// getActivity().getLayoutInflater().inflate(R.layout.my_order_payment_footview,
		// getListView(), false);
		// getListView().addFooterView(footView);
         
		status = getArguments().getString(MyOrderActivity.FRAGMENT_DATA_STATUS);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d(LOG_TAG, "onItemClick() : position = " + position);
				Bundle bundle = new Bundle();
				bundle.putString("OrderId", mListViewData.get(position - 1).get("OrderId"));
				bundle.putString("payKey", mListViewData.get(position - 1).get("StatusCode"));
				if (mListViewData.get(position-1).get("OrderType").equals("3")) {
					PageUtil.jumpTo(getActivity(), PaymentResultOrderDetailActivity1.class, bundle);
				}else {
					PageUtil.jumpTo(getActivity(), PaymentResultOrderDetailActivity.class, bundle);
				}
				
				
			}
		});
		((XListView) getListView()).setXListViewListener(this);

		((XListView) getListView()).setPullLoadEnable(true);
		

	}
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	getdata();
    	System.out.println("onresume");
    }
	/**
	 * 圆形进度条
	 */
    /**
	 * 圆形进度条
	 */
//    public  void Dialogshow(String string) {
//		dialog=new ProgressDialog(getActivity());
//		
//		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		dialog.setMessage(string);
//		dialog.show();
//        
//		
//		
//		
//	}
//	public  void Dialogcancel() {
//		
//	if (dialog!=null) {
//			dialog.dismiss();;
//			
//		}
//
//	}
	void getdata() {
		BaseActivity.Dialogshow(getActivity());
		HttpOrder.getMyOrderList1(new CallbackLogic002() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				BaseActivity.Dialogcancel();
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), getString(R.string.network_error_please_try_again),
								Toast.LENGTH_SHORT).show();
					}
				});
			}

			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data, final String other) {
				// TODO Auto-generated method stub
				BaseActivity.Dialogcancel();
//				((XListView) getListView()).stopLoadMore();
//				((XListView) getListView()).stopRefresh();
				getActivity().runOnUiThread(new Runnable() {

					private String string;
                    
					@Override
					public void run() {
						try {
							JSONObject data1 = new JSONObject(other);

							String string = data1.getString("TotalCount");

							TotalCount = Integer.parseInt(string);
							 if (TotalCount - currentPage * 10 < 0) {
								((XListView) getListView()).set_booter_gone();
							}
							JSONArray arrayData = data.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA);
							mListViewData = CollectionUtil.jsonArrayToListMap(arrayData);
							
							adapter = new MyOrderPaymentAdapter(getActivity(), mListViewData);
							if (mListViewData.size()==0) {
								textView.setVisibility(View.VISIBLE);
							}else {
								textView.setVisibility(View.GONE);
							}
							setListAdapter(adapter);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});
			}

			@Override
			public void onBizFailure(final String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				BaseActivity.Dialogcancel();
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), responseDescription, Toast.LENGTH_SHORT).show();
						PageUtil.jumpTo(getActivity(), LoginActivity.class);
					}
				});
				return;

			}

		}, UserLoginStatus.get(getActivity(),"Token"), "1", "10", status);
	}

	void getdata1() {

		BaseActivity.Dialogshow(getActivity());
		HttpOrder.getMyOrderList1(new CallbackLogic002() {

			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub

				BaseActivity.Dialogcancel();
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), getString(R.string.network_error_please_try_again),
								Toast.LENGTH_SHORT).show();
					}
				});

			}

			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data, final String other) {
				// TODO Auto-generated method stub
				BaseActivity.Dialogcancel();
				getActivity().runOnUiThread(new Runnable() {

					private String string;

					@Override
					public void run() {
						try {
							JSONObject data1 = new JSONObject(other);

							String string = data1.getString("TotalCount");

							TotalCount = Integer.parseInt(string);
							 if (TotalCount - currentPage * 10 < 0) {
								((XListView) getListView()).set_booter_gone();
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
			public void onBizFailure(final String responseDescription, JSONObject data, final String responseCode) {
				// TODO Auto-generated method stub
				BaseActivity.Dialogcancel();
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), responseDescription, Toast.LENGTH_SHORT).show();
						if (responseCode.equals("101") || responseCode.equals("103") || responseCode.equals("102")) {
							PageUtil.jumpTo(getActivity(), LoginActivity.class);
						}
						
					}
				});
				return;

			}
		}, UserLoginStatus.get(getActivity(),"Token"), String.valueOf(currentPage), "10", status);
	}

	

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		currentPage = 1;
		mListViewData.clear();
		getdata1();
		((XListView) getListView()).stopLoadMore();
		((XListView) getListView()).stopRefresh();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (TotalCount - currentPage * 10 < 0) {
			Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
			((XListView) getListView()).stopLoadMore();
			((XListView) getListView()).stopRefresh();
			
			return;
		} else {
			currentPage++;
			getdata1();
			

			((XListView) getListView()).stopLoadMore();
			((XListView) getListView()).stopRefresh();
		}
	}
}