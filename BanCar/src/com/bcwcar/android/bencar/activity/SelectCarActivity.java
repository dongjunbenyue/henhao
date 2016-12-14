package com.bcwcar.android.bencar.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainDiscovery.MainDiscovery;
import com.bcwcar.android.bencar.activity.MainHome.MainHomeFinishActivity;
import com.bcwcar.android.bencar.adapter.SelectCarAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpUserCar;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 选择默认车辆页
 */
public class SelectCarActivity extends BaseActivity {
	private static final String LOG_TAG = SelectCarActivity.class.getSimpleName();
	private SwipeMenuListView mListView;
	private List<Map<String, String>> mListViewData=new ArrayList<Map<String, String>>();
	private SelectCarAdapter selectCarAdapter;
	private String action;
	private TextView nocard;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			action=getIntent().getStringExtra(BizDefineAll.BIZ_ACTION);
		} catch (Exception e) {
			// TODO: handle exception
		}
		nocard=(TextView)findViewById(R.id.TextView_no_news001);
		mListView = (SwipeMenuListView) findViewById(R.id.listView_delete001);
		mListView.setOnItemClickListener(mListItemClickListener);
		shanchu_listview_item();
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	mListViewData.clear();
	initData();
}

	// 点击事件
	private OnItemClickListener mListItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			//设置默认车辆
			Map<String, String> lineData = mListViewData.get(position);
			for (String key : lineData.keySet()) {
				UserCarDataSave.save(SelectCarActivity.this, key, lineData.get(key));
			}
			String string=lineData.get("BuyDate").toString();
			UserCarDataSave.save(SelectCarActivity.this, "BuyDate001",string);
			String[] temp=string.split("年");
			String[] temp2=temp[1].split("月");
			UserCarDataSave.save(SelectCarActivity.this, "BuyDate", temp[0]+"-"+temp2[0]);
//			UserCarDataSave.save(SelectCarActivity.this, "CarMiles", temp[0]+"-"+temp2[0]);
			updatausercar();
		}
	};
public  void updatausercar(){
		
		HttpUserCar.updateUserCar(new CallbackLogic() {
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				if (action.equals("MainHomeFinishActivity")) {
					MainHomeFinishActivity.refresh();
					finish();
				}else if (action.equals("CarInformationActivity")) {
					CarInformationActivity.refresh();
					finish();
				}else if (action.equals("MainDiscovery")) {
					MainDiscovery.refresh_car();
					finish();
				}
			}
		}, UserLoginStatus.get(SelectCarActivity.this, "Token"), 
UserCarDataSave.get(SelectCarActivity.this, "UserCarId"), 
UserCarDataSave.get(SelectCarActivity.this, "CarId"),
UserCarDataSave.get(SelectCarActivity.this, "CarMiles"),
UserCarDataSave.get(SelectCarActivity.this, "BuyDate"),"1",
SelectCarActivity.this);
	}
	
	// 删除按钮及滑动的创建
	public void shanchu_listview_item() {
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				/**
				 * 子项的添加 删除子项按钮
				 **/

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(219, 79, 45)));
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_dellete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		mListView.setMenuCreator(creator);

		// step 2. listener item click event
		// 点击删除
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							if (position==0) {
								showToast("默认不能删除");
							}else {
								HttpUserCar.deleteUserCar(new CallbackLogic() {
									
									@Override
									public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
										// TODO Auto-generated method stub
										mListViewData.remove(position);
										selectCarAdapter.notifyDataSetChanged();
										Dialogcancel();
										
									}
								}, UserLoginStatus.get(SelectCarActivity.this,"Token"),
										mListViewData.get(position).get("UserCarId"),SelectCarActivity.this);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}

					}

				});

			}
		});

		// set SwipeListener
		mListView.setOnSwipeListener(new OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});

	}
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}
	
	// 获取用户的车型列表
private void initData() {
		HttpUserCar.getUserCarList(new CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data,JSONObject alldata) {
				Dialogcancel();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							Log.w(LOG_TAG, "data = " + data);
							JSONArray arrayData = data.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA);
							mListViewData = CollectionUtil.jsonArrayToListMap(arrayData);
							selectCarAdapter = new SelectCarAdapter(getApplicationContext(), mListViewData);
							mListView.setAdapter(selectCarAdapter);
							if (mListViewData.size()!=0) {
								nocard.setVisibility(View.GONE);
							}else {
								nocard.setVisibility(View.VISIBLE);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		}, UserLoginStatus.get(SelectCarActivity.this,"Token"), "1", "20",SelectCarActivity.this);
		
		
//		HttpWallet.getUserBankList(new CallbackLogic() {
//			@Override
//			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
//				// TODO Auto-generated method stub
//				try {
//					Log.w(LOG_TAG, "data = " + data);
//					JSONArray arrayData = data.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA);
//					mListViewData = CollectionUtil.jsonArrayToListMap(arrayData);
////					selectCarAdapter = new SelectCarAdapter(getApplicationContext(), mListViewData);
////					mListView.setAdapter(selectCarAdapter);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				adapter=new MyMoneyBankCardAdapter(getApplicationContext(), mListViewData);
//				mListView.setAdapter(adapter);
//			}
//		}, UserLoginStatus.get(SelectCarActivity.this,"Token"), "1", "20",SelectCarActivity.this);
	}

	// 头部
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText(getString(R.string.select_car_title));
		rightView.setText(getString(R.string.select_car_add_car));
		rightView.setClickable(true);
		rightView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 跳转到添加车辆页面
				Bundle bundle = new Bundle();
				bundle.putString("action", "SelectCarActivity");
				PageUtil.jumpTo(SelectCarActivity.this, SelectBrandActivity.class, bundle);
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.activity_list, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// LayoutInflater.from(this).inflate(R.layout.common_toolbar_1,
		// toolBarParentView);
	}

}
