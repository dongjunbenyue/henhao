package com.bcwcar.android.bencar.activity.MainMy;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.mapapi.map.Text;
import com.bcwcar.android.bencar.R;

import com.bcwcar.android.bencar.activity.MainActivity;

import com.bcwcar.android.bencar.adapter.UserAdressAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpUserInfo;
import com.bcwcar.android.bencar.biz.HttpWallet;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.squareup.okhttp.Request;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.authorize.e;

public class UserAdress extends BaseActivity {
	private static List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private ListView listView;
	private static UserAdressAdapter adapter;
	private TextView add_adress;
	private static int tt,yy;
	// 0***积分兑换页面跳转 1*****个人中心编辑页面跳转
	private String action ;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				initvar();
			}

		};
	};

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		// leftView.setVisibility(View.GONE);
		action = getIntent().getStringExtra("action").toString();
		leftView.setText("");
		rightView.setText("提交");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		if (action.equals("1")) {
			centerView.setText("我的地址");
			rightView.setVisibility(View.GONE);
		}else {
			centerView.setText("选择收货地址");
		}
		
		rightView.setOnClickListener(new OnClickListener() {
            
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String AddressId=WithdrawData.getWithdrawData(getApplicationContext(), "AddressId");
				
				if (action.equals("0")) {
					//选择正确地址后调用
//					duihuan_persent(AddressId, AddressName);
					
				}else if (action.equals("2")) {
				cheatmoneylingjiang(AddressId);
					
					
				}
				
			}
		});
   changeFonts(titleParentView);
	}
	
	 public  void cheatmoneylingjiang(String AddressId ) {
		   HttpWallet.cheatmoneyduijiang(new CallbackLogic002() {
			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"网络错误", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String other) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"领奖成功", Toast.LENGTH_LONG).show();
				System.out.println("response===="+responseDescription);
				Intent intent=new Intent(UserAdress.this,FreeMantence.class);
				startActivity(intent);
				finish();
			}
			
			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), responseDescription, Toast.LENGTH_SHORT).show();
				return;
			}
		}, UserLoginStatus.get(getApplicationContext(), "Token"), AddressId);
		
	}
	
	
	
//	public   void duihuan_persent(String AddressId,String AddressName){
//		HttpWallet.exchangeScoreToPresent(new CallbackLogic002() {
//			
//			@Override
//			public void onNetworkError(Request request, IOException e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onBizSuccess(String responseDescription, JSONObject data, String other) {
//				// TODO Auto-generated method stub
//				Toast.makeText(UserAdress.this, "兑换成功", Toast.LENGTH_LONG).show();
//				Intent intent=new Intent(UserAdress.this,MainActivity.class);
//				startActivity(intent);
//				finish();
//			}
//			
//			@Override
//			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
//				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), responseDescription, Toast.LENGTH_SHORT).show();
//				return;
//
//			}
//		}, UserLoginStatus.getLoggedToken(getApplicationContext()), AddressId, AddressName, get_lingjiang_data("PresentCode"));
//		
//	}
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.user_adress_xml, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {

			
		} catch (Exception e) {
			// TODO: handle exception
		}
		initview();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		list.clear();
		initdata();
	}

	// 实例化控件
	public void initview() {
		listView = (ListView) findViewById(R.id.ListView_user_adress_list001);
		add_adress = (TextView) findViewById(R.id.textView_user_adress_add001);
		add_adress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserAdress.this, AddAdress.class);
				startActivity(intent);
			}
		});

	}

	// 赋值
	public void initvar() {
		adapter = new UserAdressAdapter(this, list, UserAdress.this, action);
		listView.setAdapter(adapter);

	}

	// 删除地址记录
	public static void dele_user_address(int postion) {
		list.remove(postion);
		adapter.notifyDataSetChanged();

	}

	// 获取用户地址
	public void initdata() {
		
		HttpUserInfo.getAddressList(new CallbackLogic() {

			

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
				// TODO Auto-generated method stub
				Dialogcancel();
				try {
					JSONArray array = data.getJSONArray("Data");
					for (int i = 0; i < array.length(); i++) {
						JSONObject object = array.getJSONObject(i);
						System.out.println(object+"===================");
						list.add(CollectionUtil.jsonObjectToMap(object));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendEmptyMessage(1);

			}

			
		}, UserLoginStatus.get(UserAdress.this,"Token"),UserLoginStatus.get(UserAdress.this,"UserId"),UserAdress.this);

	}
}
