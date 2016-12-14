package com.bcwcar.android.bencar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainDiscovery.MainDiscovery;
import com.bcwcar.android.bencar.activity.MainHome.MainHome;
import com.bcwcar.android.bencar.activity.MainMy.MainMyActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.BaseApplication;
import com.bcwcar.android.bencar.base.ExampleUtil;
import com.bcwcar.android.bencar.base.GPS_Positioning;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpUserInfo;
import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.OnlyOneDataSave;
import com.bcwcar.android.bencar.datasave.StoreToDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.StringUtil;

import android.app.TabActivity;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	private static final String LOG_TAG = MainActivity.class.getSimpleName();
	// ============store
	private RelativeLayout noservice_RelativeLayout, noservice_brand_RelativeLayout, submit_success_RelativeLayout,
			have_kefu_RelativeLayout;
	private static RelativeLayout noservice_4s_RelativeLayout;
	private static TextView submit, brand, shopname, sales_befor, sales_after, change_guishu;
	private static Context context;
	private boolean sales_befor_zhizhen = false;
	private boolean sales_after_zhizhen = false;
	private List<Map<String, String>> list_data = new ArrayList<Map<String, String>>();
	// =============
	private static Intent i_home = null;
	private static RadioGroup mTabButtonGroup;
	private RadioButton rButton1, rButton2, rButton3;
	private static TabHost mTabHost;
	public static final String TAB_HOME = "home";
	public static final String TAB_DISCOVERY = "discovery";
	public static final String TAB_MINE = "mine";
	private TextView store_button;
	private long tt, yy;
	int left, top, right, bottom;
	int lastX, lastY;
	private ImageView store;
	Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		context = MainActivity.this;
		new GPS_Positioning(MainActivity.this, mHandler).gps();
		initView();
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
		String appKey = ExampleUtil.getAppKey(getApplicationContext());
		String rid = JPushInterface.getRegistrationID(getApplicationContext());
		String deviceId = ExampleUtil.getDeviceId(getApplicationContext());
		OnlyOneDataSave.save(MainActivity.this, "PushChannelId", rid);
		System.out.println("rid=====" + rid);
		System.out.println("appKey====="+appKey);
		System.out.println("deviceId====="+deviceId);
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	
		if (UserLoginStatus.isLoggedOn(getApplicationContext())) {
			store.setVisibility(View.VISIBLE);
		}else {
			store.setVisibility(View.GONE);
		}
	
}
	// fufuzhi

	public static void setvar(int tt) {
		if (tt == 0) {
			noservice_4s_RelativeLayout.setClickable(true);
			brand.setText(StoreToDataSave.get(context, "Brand"));
		} else if (tt == 1) {
			shopname.setText(StoreToDataSave.get(context, "ShopName"));
		}

	}

	private void initView() {
		// ======store
		change_guishu = (TextView) findViewById(R.id.TextView_genghuan_guishu001);
		change_guishu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				noservice_RelativeLayout.setVisibility(View.VISIBLE);
				have_kefu_RelativeLayout.setVisibility(View.GONE);
			}
		});
		noservice_RelativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_no_service_xml001);
		noservice_RelativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				noservice_RelativeLayout.setVisibility(View.GONE);
			}
		});
		noservice_brand_RelativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_store_brand001);
		noservice_brand_RelativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, SelectBrandActivity.class);
				intent.putExtra(BizDefineAll.BIZ_ACTION, "MainActivity");
				startActivity(intent);
			}
		});
		noservice_4s_RelativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_store_4s001);
		noservice_4s_RelativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!StringUtil.isEmpty(brand.getText().toString())) {
					Intent intent = new Intent(MainActivity.this, StoreServiceNetworkActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(context, "请先选择品牌", 1).show();
				}
			}
		});
		submit = (TextView) findViewById(R.id.TextView_store_submit001);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HttpUserInfo.updateCustomerBelongShop(new CallbackLogic() {
					@Override
					public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
						// TODO Auto-generated method stub
						noservice_RelativeLayout.setVisibility(View.GONE);
						submit_success_RelativeLayout.setVisibility(View.VISIBLE);
					}
				}, UserLoginStatus.get(MainActivity.this, "Token"), StoreToDataSave.get(MainActivity.this, "ShopId"),
						context);
			}
		});
		submit_success_RelativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_no_service_submit_success001);
		submit_success_RelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submit_success_RelativeLayout.setVisibility(View.GONE);
			}
		});
		brand = (TextView) findViewById(R.id.TextView_no_service_brand001);
		shopname = (TextView) findViewById(R.id.TextView_no_service_shopname001);
		// =======有归属===
		have_kefu_RelativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_have_service_xml001);
		have_kefu_RelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				have_kefu_RelativeLayout.setVisibility(View.GONE);
			}
		});
		sales_after = (TextView) findViewById(R.id.TextView_sales_chat002);
		sales_after.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sales_after_zhizhen) {
					have_kefu_RelativeLayout.setVisibility(View.GONE);
					RongIM.getInstance().startPrivateChat(MainActivity.this,
							StoreToDataSave.get(MainActivity.this, "AfterServiceEmpId"),
							StoreToDataSave.get(MainActivity.this, "AfterServiceEmpName"));
				} else {
					Toast.makeText(MainActivity.this, "暂无专属顾问，会尽快为您安排，请谅解！", 1).show();
				}
			}
		});
		sales_befor = (TextView) findViewById(R.id.TextView_sales_chat001);
		sales_befor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sales_befor_zhizhen) {
					have_kefu_RelativeLayout.setVisibility(View.GONE);
					RongIM.getInstance().startPrivateChat(MainActivity.this,
							StoreToDataSave.get(MainActivity.this, "BeforeServiceEmpId"),
							StoreToDataSave.get(MainActivity.this, "BeforeServiceEmpName"));
				} else {
					Toast.makeText(MainActivity.this, "暂无专属顾问，会尽快为您安排，请谅解！", 1).show();
				}
			}
		});

		// ===========底部button==============
		mTabButtonGroup = (RadioGroup) findViewById(R.id.common_toolbar_container);
		BaseActivity.changeFonts(mTabButtonGroup);
		rButton1 = (RadioButton) findViewById(R.id.common_toolbar_1_home);
		rButton2 = (RadioButton) findViewById(R.id.common_toolbar_1_discovery);
		rButton3 = (RadioButton) findViewById(R.id.common_toolbar_1_mine);
		i_home = new Intent(this, MainHome.class);
		Intent i_discovery = new Intent(this, MainDiscovery.class);
		Intent i_mine = new Intent(this, MainMyActivity.class);

		mTabHost = getTabHost();
		mTabHost.addTab(mTabHost.newTabSpec(TAB_HOME).setIndicator(TAB_HOME).setContent(i_home));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_DISCOVERY).setIndicator(TAB_DISCOVERY).setContent(i_discovery));

		mTabHost.addTab(mTabHost.newTabSpec(TAB_MINE).setIndicator(TAB_MINE).setContent(i_mine));

		mTabHost.setCurrentTabByTag(TAB_HOME);

		mTabButtonGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.common_toolbar_1_home:
					mTabHost.setCurrentTabByTag(TAB_HOME);
					changeTextColor(1);
					break;
				case R.id.common_toolbar_1_discovery:
					mTabHost.setCurrentTabByTag(TAB_DISCOVERY);
					changeTextColor(2);
					break;

				case R.id.common_toolbar_1_mine:
					mTabHost.setCurrentTabByTag(TAB_MINE);
					changeTextColor(3);
					break;

				default:
					break;
				}
			}
		});
		// =======客服按钮=============
		DisplayMetrics dm = getResources().getDisplayMetrics();
		final int screenWidth = dm.widthPixels;
		final int screenHeight = dm.heightPixels - dm.heightPixels / 10;
		store_button = (TextView) findViewById(R.id.TextView_kefu_storemannager001);
		store = (ImageView) findViewById(R.id.ImageView_kefu_storemannager001);
		store.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int ea = event.getAction();
				Log.i("TAG", "Touch:" + ea);
				switch (ea) {
				case MotionEvent.ACTION_DOWN:
					System.out.println("ACTION_DOWN");
					tt = System.currentTimeMillis();
					lastX = (int) event.getRawX();
					lastY = (int) event.getRawY();
					break;

				/**
				 * layout(l,t,r,b) l Left position, relative to parent t Top
				 * position, relative to parent r Right position, relative to
				 * parent b Bottom position, relative to parent
				 */
				case MotionEvent.ACTION_MOVE:
					System.out.println("ACTION_MOVE");
					int dx = (int) event.getRawX() - lastX;
					int dy = (int) event.getRawY() - lastY;
					left = v.getLeft() + dx;
					top = v.getTop() + dy;
					right = v.getRight() + dx;
					bottom = v.getBottom() + dy;
					if (left < 0) {
						left = 0;
						right = left + v.getWidth();
					}
					if (right > screenWidth) {
						right = screenWidth;
						left = right - v.getWidth();
					}
					if (top < 0) {
						top = 0;
						bottom = top + v.getHeight();
					}
					if (bottom > screenHeight) {
						bottom = screenHeight;
						top = bottom - v.getHeight();
					}
					v.layout(left, top, right, bottom);
					Log.i("", "position：" + left + ", " + top + ", " + right + ", " + bottom);
					lastX = (int) event.getRawX();
					lastY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_UP:
					if (left <= (screenWidth - v.getWidth()) / 2) {
						left = 0;
						right = left + v.getWidth();
					} else {
						right = screenWidth;
						left = right - v.getWidth();
					}
					v.layout(left, top, right, bottom);

					yy = System.currentTimeMillis();
					if (yy - tt <= 200) {
						if (OnlyOneDataSave.get(MainActivity.this, "rongyun").equals("1")) {
							getshop();
						} else {
							connect(UserLoginStatus.get(MainActivity.this, "RongToken"));
						}
					} else {
						System.out.println("moving===");
					}
					System.out.println("ACTION_UP");
					break;
				}
				return false;
			}
		});
	store.setClickable(true);
	}

	// 获取用户的归属信息的网店信息
	public void getshop() {
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
						StoreToDataSave.save(MainActivity.this, key, map.get(key));
					}
					// 判断售前
					if (StoreToDataSave.get(MainActivity.this, "BeforeServiceFlag").equals("1")) {
						sales_befor_zhizhen = true;
						Map<String, String> map001 = new HashMap<String, String>();
						map001.put("UserId", StoreToDataSave.get(MainActivity.this, "BeforeServiceEmpId"));
						map001.put("UserName", StoreToDataSave.get(MainActivity.this, "BeforeServiceEmpName"));
						map001.put("IconUrl", StoreToDataSave.get(MainActivity.this, "BeforeServiceEmpIconUrl"));
						list_data.add(map001);
						getEmployeeDetail("BeforeServiceData",
								StoreToDataSave.get(MainActivity.this, "BeforeServiceEmpId"));
						try {
							JSONObject object2 = new JSONObject(
									StoreToDataSave.get(MainActivity.this, "BeforeServiceData"));
							String sex = object2.getString("Gender");
							if (sex.equals("0")) {
								settextview_topimage(sales_befor, R.drawable.kefu_nv);
							} else {
								settextview_topimage(sales_befor, R.drawable.kefu_nan);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					} else {
						sales_befor_zhizhen = false;
						settextview_topimage(sales_befor, R.drawable.kefu_nan_hui);
					}
					// 判断售后顾问
					if (StoreToDataSave.get(MainActivity.this, "AfterServiceFlag").equals("1")) {
						sales_after_zhizhen = true;
						Map<String, String> map002 = new HashMap<String, String>();
						map002.put("UserId", StoreToDataSave.get(MainActivity.this, "AfterServiceEmpId"));
						map002.put("UserName", StoreToDataSave.get(MainActivity.this, "AfterServiceEmpName"));
						map002.put("IconUrl", StoreToDataSave.get(MainActivity.this, "AfterServiceEmpIconUrl"));
						list_data.add(map002);
						getEmployeeDetail("AfterServiceData",
								StoreToDataSave.get(MainActivity.this, "AfterServiceEmpId"));
						try {
							JSONObject object2 = new JSONObject(
									StoreToDataSave.get(MainActivity.this, "AfterServiceData"));
							String sex = object2.getString("Gender");
							if (sex.equals("0")) {
								settextview_topimage(sales_after, R.drawable.kefu_nv);
							} else {
								settextview_topimage(sales_after, R.drawable.kefu_nan);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					} else {
						sales_after_zhizhen = false;
						settextview_topimage(sales_after, R.drawable.kefu_nan_hui);
					}

					// 判断是否有归属
					if (StoreToDataSave.get(MainActivity.this, "ShopFlag").equals("1")) {
						have_kefu_RelativeLayout.setVisibility(View.VISIBLE);
					} else if (StoreToDataSave.get(MainActivity.this, "ShopFlag").equals("0")) {
						noservice_RelativeLayout.setVisibility(View.VISIBLE);
					}
					provider();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, UserLoginStatus.get(MainActivity.this, "Token"), LocationDataSave.get(MainActivity.this, "Latitude"),
				LocationDataSave.get(MainActivity.this, "Longitude"), MainActivity.this);

	}

	// 获取客服信息
	public void getEmployeeDetail(final String service, String ServiceEmpId) {
		BaseActivity.Dialogcancel();
		HttpUserInfo.getEmployee(new CallbackLogic() {

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					JSONObject object = data.getJSONObject("Data");
					StoreToDataSave.save(MainActivity.this, service, object.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, UserLoginStatus.get(MainActivity.this, "Token"), ServiceEmpId, MainActivity.this);

	}

	// 设置textview的上部图标
	public void settextview_topimage(TextView view, int id) {
		Resources res = getResources();
		Drawable img = res.getDrawable(id);
		// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
		view.setCompoundDrawables(null, img, null, null); // 设置左图标
	}

	/**
	 * 建立与融云服务器的连接
	 *
	 * @param token
	 */
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
					System.out.println("聊天功能无法开启！错误码:身份过期");
				}

				/**
				 * 连接融云成功
				 * 
				 * @param userid
				 *            当前 token
				 */
				@Override
				public void onSuccess(String userid) {
					OnlyOneDataSave.save(MainActivity.this, "rongyun", "1");
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
					System.out.println("聊天功能无法开启！错误码=" + errorCode);
				}
			});
		}
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
				map.put("UserId", UserLoginStatus.get(MainActivity.this, "UserId"));
				map.put("UserName", UserLoginStatus.get(MainActivity.this, "UserName"));
				map.put("IconUrl", UserLoginStatus.get(MainActivity.this, "IconUrl"));
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

	// ======================
	public static void sethome() {

		mTabButtonGroup.check(R.id.common_toolbar_1_home);
	}

	private void changeTextColor(int index) {
		rButton1.setTextColor(getResources().getColor(R.color.inputblack));
		rButton2.setTextColor(getResources().getColor(R.color.inputblack));
		rButton3.setTextColor(getResources().getColor(R.color.inputblack));

		switch (index) {
		case 1:
			rButton1.setTextColor(getResources().getColor(R.color.mainblue));

			break;
		case 2:
			rButton2.setTextColor(getResources().getColor(R.color.mainblue));

			break;

		case 3:
			rButton3.setTextColor(getResources().getColor(R.color.mainblue));

			break;

		default:
			break;
		}
	}

}