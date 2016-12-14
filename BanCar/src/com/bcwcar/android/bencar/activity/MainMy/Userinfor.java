package com.bcwcar.android.bencar.activity.MainMy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import com.bcwcar.android.bencar.adapter.CityAreaLianDongAdapter;
import com.bcwcar.android.bencar.adapter.CityAreaLianDongAdapter2;
import com.bcwcar.android.bencar.adapter.HangyeAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpLogin;
import com.bcwcar.android.bencar.biz.HttpUserInfo;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class Userinfor extends BaseActivity implements OnValueChangeListener, OnScrollListener{

	private RelativeLayout gotocamera,gotochangeimg,gotochangenick,gotochangbirth,gotochangsign,gotochangedistrict,
	gotochangsex,gotochangadress,shurukuang,changehangye;
	private LinearLayout zhaoxiang_bendi;
	private LinearLayout zhaoxiang_quxiao;
	
	private LinearLayout zhaoxiang;
	private Object bendi_path;
	// 城市联动选择
		private List<Map<String, String>> list_ProvinceName = new ArrayList<Map<String, String>>();
		private List<Map<String, String>> list_city = new ArrayList<Map<String, String>>();
		private int zhizhen_ProvinceName = 0;
		private int zhizhen_city = 0;
		private int zhizhen_area = 0;
		private ListView listview_ProvinceName, listview_city, listview_area;
	private String mTimeString001 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
			.format(new Date(System.currentTimeMillis()));
	private SimpleDraweeView SimpleDraweeView_usericon;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
	private static final int PHOTO_REQUEST_GALLERY = 2;
	private int birthday_year = 1990, birthday_month = 1, birthday_day = 1;
	String imgurl,biaoji="0";
	
	private String service_path,shurukuang_zhizhen;
	private Handler mhandler = new Handler() {
		

		public void handleMessage(Message msg) {
			try {
				
				if (msg.what == 101) {
					JSONObject object = new JSONObject(msg.obj.toString());
					service_path = object.getString("PicUrl");
					UserLoginStatus.save(getApplicationContext(), "IconUrl", service_path);
					System.out.println("储存的值==" + UserLoginStatus.get(getApplicationContext(), "IconUrl"));
					ResourceUtil.setSimpleDraweeViewImage(SimpleDraweeView_usericon,
							UserLoginStatus.get(getApplicationContext(), "IconUrl"));
					
					UserLoginStatus.save(getApplicationContext(), "change", "1");
					System.out.println("1111111");
				} else if (msg.what == 100) {
					
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};
	private TextView textcancel,textconfirm,textphone,nickname;
	private EditText editresult;
	private TextView textsex;
	private TextView textbirth;
	private TextView textdis;
	private TextView textsign;
	private RelativeLayout RelativeLayout_date_choice,changsex;
	private TextView date_choice_quxiao,hangyetext;
	private View date_choice_queren;
	private NumberPicker birthday_numberPickerYear;
	private NumberPicker birthday_numberPickerMonth;
	private NumberPicker birthday_numberPickerDay;
	private int today_day;
	private int today_month;
	private int today_year;
	private InputMethodManager imm;
	private TextView show001;
	private TextView show002;
	private RelativeLayout city_xuanze;
	private TextView quxiao;
	private TextView queren;
	private CityAreaLianDongAdapter adapter001;
	private CityAreaLianDongAdapter2 adapter002;
	private HangyeAdapter hangyeAdapter;
	private ListView listview1;
	private List<Map<String, String>> list_ProvinceName1=new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    	try {
			String[] temp = mTimeString001.split("-");
			today_day = Integer.parseInt(temp[2]);
			today_month = Integer.parseInt(temp[1]);
			today_year = Integer.parseInt(temp[0]);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	city_item();
    	initview();
    	doLoggedOnAction();
    	
    }
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		changeFonts((ViewGroup)rootView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		
		TextView leftView = (TextView) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		centerView.setText(R.string.personaldata);
		centerView.getPaint().setFakeBoldText(true);
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserLoginStatus.get(getApplicationContext(), "change").equals("1")) {
					
					dialog_zidingyi();
				} else {
					
          finish();
				}
				
			}
		});
	}
    public void initview() {
    	SimpleDraweeView_usericon=(SimpleDraweeView)findViewById(R.id.SimpleDraweeView_user_info_usericon001);
		gotocamera=(RelativeLayout)findViewById(R.id.RelativeLayout_camera_show001);
		gotochangenick=(RelativeLayout)findViewById(R.id.changenick);
	    hangyetext=(TextView)findViewById(R.id.texthangye);
	    changsex=(RelativeLayout)findViewById(R.id.changsex);
		shurukuang = (RelativeLayout) findViewById(R.id.LinearLayout_shuru_liandong001);// 输入框弹出
		textcancel=(TextView)findViewById(R.id.textview_shuru_quxiao001);
		textconfirm=(TextView)findViewById(R.id.textview_shuru_queren001);
		nickname=(TextView)findViewById(R.id.userinfornick);
		textsex=(TextView)findViewById(R.id.textsex);
		textbirth=(TextView)findViewById(R.id.textbirth);
		textdis=(TextView)findViewById(R.id.textdistrict);
		textsign=(TextView)findViewById(R.id.textsign);
		editresult=(EditText)findViewById(R.id.EditText_freee_xml001);
		gotochangbirth=(RelativeLayout)findViewById(R.id.changbirth);
		textphone=(TextView)findViewById(R.id.userinfor_telephone);
		gotochangsex=(RelativeLayout)findViewById(R.id.changsex);
		gotochangeimg=(RelativeLayout)findViewById(R.id.gotochangeimg);
		gotochangadress=(RelativeLayout)findViewById(R.id.changadress);
		gotochangedistrict=(RelativeLayout)findViewById(R.id.changdistrict);
		gotochangsign=(RelativeLayout)findViewById(R.id.changsign);
		listview1=(ListView)findViewById(R.id.hangyelist);
		changehangye=(RelativeLayout)findViewById(R.id.changhangye);
		changehangye.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listview1.setVisibility(View.VISIBLE);
				hangyeAdapter=new HangyeAdapter(Userinfor.this, list_ProvinceName1);
				listview1.setAdapter(hangyeAdapter);
			}
		});
		
		
		listview1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				UserLoginStatus.save(Userinfor.this, "Job", list_ProvinceName1.get(position).get("IndustryName"));
				hangyetext.setText(UserLoginStatus.get(getApplicationContext(), "Job"));
				UserLoginStatus.save(getApplicationContext(), "change", "1");
				listview1.setVisibility(View.GONE);
				
			}
		});
		changsex.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(Userinfor.this, SelectMale.class);
			}
		});
		gotochangadress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Userinfor.this, UserAdress.class);
				intent.putExtra("action", "1");
				startActivity(intent);
			}
		});
		gotochangbirth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RelativeLayout_date_choice.setVisibility(View.VISIBLE);
			}
		});
		// 时间选择器
				RelativeLayout_date_choice = (RelativeLayout) findViewById(R.id.LinearLayout_user_info_birthdaydate001);// 出生日期选择父类
				date_choice_quxiao = (TextView) findViewById(R.id.textview_user_info_brithdaydate_quxiao001);
				date_choice_quxiao.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						RelativeLayout_date_choice.setVisibility(View.GONE);
					}
				});
				date_choice_queren = (TextView) findViewById(R.id.textview_user_info_brithdaydate_queren001);
				date_choice_queren.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						UserLoginStatus.save(getApplicationContext(), "change", "1");
						String string = birthday_year + "-" + format001(birthday_month) + "-" + format001(birthday_day);
						
						UserLoginStatus.save(getApplicationContext(), "BirthDate", string);
						textbirth.setText(string);
						RelativeLayout_date_choice.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                setVisibility(View.GONE);
					}
				});
				birthday_numberPickerYear = (NumberPicker) findViewById(R.id.NumberPicker_user_info_brithdaydate001);
				birthday_numberPickerMonth = (NumberPicker) findViewById(R.id.NumberPicker_user_info_brithdaydate002);
				birthday_numberPickerDay = (NumberPicker) findViewById(R.id.NumberPicker_user_info_brithdaydate003);
				initNumberPicker(today_year);
				birthday_numberPickerDay.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
					}
				});
				birthday_numberPickerMonth.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
					}
				});
				birthday_numberPickerYear.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
					}
				});
				// changeFonts(birthday_numberPickerDay);
				// changeFonts(birthday_numberPickerMonth);
				// changeFonts(birthday_numberPickerYear);
				setNumberPickerDividerColor(birthday_numberPickerDay);
				setNumberPickerDividerColor(birthday_numberPickerMonth);
				setNumberPickerDividerColor(birthday_numberPickerYear);
				gotochangedistrict.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						city_xuanze.setVisibility(View.VISIBLE);
					}
				});
				// 城市联动
				show001 = (TextView) findViewById(R.id.TextView_city_show001);
				show002 = (TextView) findViewById(R.id.TextView_city_show002);
				city_xuanze = (RelativeLayout) findViewById(R.id.LinearLayout_city_liandong001);// pop弹窗，城市选择
				quxiao = (TextView) findViewById(R.id.textview_city_quxiao001);
				quxiao.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						city_xuanze.setVisibility(View.GONE);
					}
				});
				queren = (TextView) findViewById(R.id.textview_city_queren001);
				queren.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						if (show001.getText().toString().equals(getString(R.string.text_city_other))
								|| show002.getText().toString().equals(getString(R.string.text_city_other))) {
							showToast(getString(R.string.text_city_please));
						} else {
							UserLoginStatus.save(getApplicationContext(), "change", "1");
							System.out.println("2222222");
							UserLoginStatus.save(getApplicationContext(), "ProvinceName",
									list_ProvinceName.get(zhizhen_ProvinceName).get("ProvinceName").toString());
							UserLoginStatus.save(getApplicationContext(), "CityName",
									list_city.get(zhizhen_city).get("CityName").toString());
						
							show_textview();
							city_xuanze.setVisibility(View.GONE);
						}
					}
				});
				listview_ProvinceName = (ListView) findViewById(R.id.ListView_city001);
				listview_city = (ListView) findViewById(R.id.ListView_city002);
				System.out.println("adpter====="+list_ProvinceName);
				adapter001 = new CityAreaLianDongAdapter(getApplicationContext(), list_ProvinceName);
				listview_ProvinceName.setAdapter(adapter001);
				
				adapter002 = new CityAreaLianDongAdapter2(getApplicationContext(), list_city);
				listview_city.setAdapter(adapter002);
				listview_ProvinceName.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						zhizhen_ProvinceName = position;
						UserLoginStatus.save(getApplicationContext(), "ProvinceName",
								list_ProvinceName.get(zhizhen_ProvinceName).get("ProvinceName").toString());
						show001.setText(list_ProvinceName.get(position).get("ProvinceName").toString());
						list_city.clear();
						list_city(position);
						adapter002.notifyDataSetChanged();
					}
				});
				listview_city.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						UserLoginStatus.save(getApplicationContext(), "CityName",
								list_city.get(zhizhen_city).get("CityName").toString());
						zhizhen_city = position;
						show002.setText("");
						show002.setText(list_city.get(position).get("CityName").toString());
					}
				});
		gotochangenick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shurukuang.setVisibility(View.VISIBLE);
				shurukuang_zhizhen="0";
			}
		});
		gotochangsign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			shurukuang.setVisibility(View.VISIBLE);	
			shurukuang_zhizhen="1";
			}
		});
		textcancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			shurukuang.setVisibility(View.GONE);	
			}
		});
		textconfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserLoginStatus.save(getApplicationContext(), "change", "1");
				System.out.println("33333333");
				// TODO Auto-generated method stub
				if (editresult.getText().toString().equals("")) {
					showToast("没有输入，请重新填写");
					return;
				}
				shurukuang.setVisibility(View.GONE);
				if (shurukuang_zhizhen.equals("0")) {
				UserLoginStatus.save(getApplicationContext(), "NickName", editresult.getText().toString());
				nickname.setText(editresult.getText().toString());
				
				}else if (shurukuang_zhizhen.equals("1")) {
					UserLoginStatus.save(getApplicationContext(), "Signatrue", editresult.getText().toString());
					textsign.setText(UserLoginStatus.get(getApplicationContext(), "Signatrue"));
				}
			}
		});
		
		gotochangeimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotocamera.setVisibility(View.VISIBLE);
			}
		});
		
		zhaoxiang = (LinearLayout) findViewById(R.id.LinearLayout_camera_show_zhaoxiang001);
		zhaoxiang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 先验证手机是否有sdcard
				gotocamera.setVisibility(View.GONE);
				String status = Environment.getExternalStorageState();
				if (status.equals(Environment.MEDIA_MOUNTED)) {

					try {
						File dir = new File(Environment.getExternalStorageDirectory() + "/bcwcar");
						if (!dir.exists())
							dir.mkdirs();

						Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						File f = new File(dir, "bcwcar.png");// localTempImgDir和localTempImageFileName是自己定义的名字
						Uri u = Uri.fromFile(f);
						intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
						startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
					} catch (ActivityNotFoundException e) {
						// TODO Auto-generated catch block
						showToast("没有找到储存目录");
					}
				} else {
					showToast("没有储存卡");
				}
			}
		});
		zhaoxiang_bendi = (LinearLayout) findViewById(R.id.LinearLayout_camera_show_bendi001);
		zhaoxiang_bendi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotocamera.setVisibility(View.GONE);
				Intent intent2 = new Intent(Intent.ACTION_PICK, null);
				intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent2, PHOTO_REQUEST_GALLERY);
			}
		});
		zhaoxiang_quxiao = (LinearLayout) findViewById(R.id.LinearLayout_camera_show_quxiao001);
		zhaoxiang_quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotocamera.setVisibility(View.GONE);
			}
		});
	}
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	
    }
   public void initdata(){
	   ResourceUtil.setSimpleDraweeViewImage(SimpleDraweeView_usericon,
				UserLoginStatus.get(getApplicationContext(), "IconUrl"));
	    
   nickname.setText(UserLoginStatus.get(getApplicationContext(), "NickName")+"");
   if (UserLoginStatus.get(getApplicationContext(),"Gender").equals("0")) {
	textsex.setText("女");
	}else {
		textsex.setText("男");
	}
   textsign.setText(UserLoginStatus.get(getApplicationContext(), "Signatrue")+"");
   textbirth.setText(UserLoginStatus.get(getApplicationContext(), "BirthDate")+"");
   textdis.setText(UserLoginStatus.get(getApplicationContext(), "ProvinceName")+UserLoginStatus.get(getApplicationContext(), "CityName"));
   hangyetext.setText(UserLoginStatus.get(getApplicationContext(), "Job"));
	textphone.setText(UserLoginStatus.get(getApplicationContext(),"UserMobile"));
	city_item1();
   }
	// 获取用户数据
	private void doLoggedOnAction() {
		HttpUserInfo.getUserDetail(new CallbackLogic() {

			

			@Override
			public void onBizSuccess(String responseDescription, final JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				runOnUiThread( new Runnable() {
					public void run() {
						JSONObject objData;
						try {
							objData = data.getJSONObject(BizDefineAll.BIZ_RESPONSE_DATA);
							for (String key : CollectionUtil.jsonObjectToMap(objData).keySet()) {
								UserLoginStatus.save(getApplicationContext(),key, CollectionUtil.jsonObjectToMap(objData).get(key));
							}
							initdata();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
			}

		}, UserLoginStatus.get(Userinfor.this,"Token"), UserLoginStatus.get(Userinfor.this,"UserId"),Userinfor.this);
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	initdata();
	
}
 // 获取城市联动列表
 	public void city_item() {
 		try {
 			InputStream is = getAssets().open("SCQ.txt");
 			int size = is.available();
 			byte[] buffer = new byte[size];
 			is.read(buffer);
 			is.close();
 			String text = new String(buffer, "utf-8");
 			JSONObject object = new JSONObject(text);
 			JSONArray array = object.getJSONArray("Data");
 			for (int i = 0; i < array.length(); i++) {
 				JSONObject object2 = array.getJSONObject(i);
 				Map<String, String> map = new HashMap<String, String>();
 				map.put("ProvinceId", object2.getString("ProvinceId").toString());
 				map.put("ProvinceName", object2.getString("ProvinceName").toString());
 				map.put("Citys", object2.getString("Citys").toString());
 				list_ProvinceName.add(map);
 			}
          System.out.println("cityitem"+list_ProvinceName);
 		} catch (Exception e) {
 			// Should never happen!
 			throw new RuntimeException(e);
 		}

 	}
 	 // 获取行业列表
 	public void city_item1() {
 		try {
 			InputStream is = getAssets().open("hysj.json");
 			int size = is.available();
 			byte[] buffer = new byte[size];
 			is.read(buffer);
 			is.close();
 			String text = new String(buffer, "utf-8");
 			JSONObject object = new JSONObject(text);
 			JSONArray array = object.getJSONArray("Data");
 			for (int i = 0; i < array.length(); i++) {
 				JSONObject object2 = array.getJSONObject(i);
 				Map<String, String> map = new HashMap<String, String>();
 				map.put("IndustryId", object2.getString("IndustryId").toString());
 				map.put("IndustryName", object2.getString("IndustryName").toString());
 			
 				list_ProvinceName1.add(map);
 			}
          System.out.println("cityitem"+list_ProvinceName1);
 		} catch (Exception e) {
 			// Should never happen!
 			throw new RuntimeException(e);
 		}

 	}
 	// 省份-------城市
 	public void list_city(int tt) {
 		try {
 			String citylist = list_ProvinceName.get(tt).get("Citys").toString();
 			JSONArray array = new JSONArray(citylist);
 			for (int i = 0; i < array.length(); i++) {
 				JSONObject object = array.getJSONObject(i);

 				Map<String, String> map = new HashMap<String, String>();
 				map.put("CityId", object.getString("CityId").toString());
 				map.put("CityName", object.getString("CityName").toString());
 				list_city.add(map);

 			}

 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 	}

 // 选择的地址信息显示
 	public void show_textview() {
 		textdis.setText(list_ProvinceName.get(zhizhen_ProvinceName).get("ProvinceName").toString()
 				+ list_city.get(zhizhen_city).get("CityName").toString());
 	}

    
 // NumberPicker默认值
 	private void initNumberPicker(int max) {
 		// 出生日期
 		birthday_numberPickerYear.setOnValueChangedListener(this);
 		birthday_numberPickerYear.setOnScrollListener(this);
 		birthday_numberPickerYear.setMaxValue(max);
 		birthday_numberPickerYear.setMinValue(1900);
 		birthday_numberPickerYear.setValue(1990);

 		birthday_numberPickerMonth.setOnValueChangedListener(this);
 		birthday_numberPickerMonth.setOnScrollListener(this);
 		birthday_numberPickerMonth.setMaxValue(12);
 		birthday_numberPickerMonth.setMinValue(1);
 		birthday_numberPickerMonth.setValue(1);

 		birthday_numberPickerDay.setOnValueChangedListener(this);
 		birthday_numberPickerDay.setOnScrollListener(this);
 		birthday_numberPickerDay.setMaxValue(31);
 		birthday_numberPickerDay.setMinValue(1);
 		birthday_numberPickerDay.setValue(1);
 	}

    /**
	 * 按正方形裁切图片
	 */
	public static Bitmap ImageCrop(Bitmap bitmap) {
		int w = bitmap.getWidth(); // 得到图片的宽，高
		int h = bitmap.getHeight();

		int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

		int retX = w > h ? (w - h) / 2 : 0;// 基于原图，取正方形左上角x坐标
		int retY = w > h ? 0 : (h - w) / 2;

		// 下面这句是关键
		return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
	}
	// an按uri 获取bitmap
		private Bitmap getBitmapFromUri(Uri uri) {
			try {
				// 读取uri所在的图片
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
				return bitmap;
			} catch (Exception e) {
				Log.e("[Android]", e.getMessage());
				Log.e("[Android]", "目录为：" + uri);
				e.printStackTrace();
				return null;
			}
		}
		/**
		 * 将图片保存到本地图片上
		 */
		public String savePublishPicture(Bitmap bitmap) {
			
			String publishfilename = System.currentTimeMillis() + ".png";
			File f = new File(Environment.getExternalStorageDirectory(), "/bcwcar/" + publishfilename);
			System.out.println(f.getAbsolutePath() + "====================================================");
			try {
				if (f.exists()) {
					f.delete();
				}
				f.createNewFile();
				FileOutputStream fOut = null;
				fOut = new FileOutputStream(f);
				BufferedOutputStream bos = new BufferedOutputStream(fOut);
				compressImage(bitmap).compress(Bitmap.CompressFormat.JPEG, 100, bos);// 把Bitmap对象解析成流
				fOut.flush();
				fOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("//////////////////////////////");
				send(url_adress, f.getAbsolutePath());
			
			}
			return f.getAbsolutePath();
		}
 // 调用系统相机
 	@Override
 	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 		// TODO Auto-generated method stub

 		switch (requestCode) {
 		case PHOTO_REQUEST_TAKEPHOTO:// 照相
 			
 			try {
				// u就是拍摄获得的原始图片的uri，剩下的你想干神马坏事请便……
				
				File f = new File(Environment.getExternalStorageDirectory(), "/bcwcar/bcwcar.png");
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inSampleSize = 2; //这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰
				//返回原图解码之后的bitmap对象
				Bitmap bm = BitmapFactory.decodeFile(f.getAbsolutePath(), opts);
				System.out.println(bm.getByteCount()+"******************daxiao");
				bendi_path= savePublishPicture(ImageCrop(bm));
				System.out.println("bendi********************"+bendi_path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			break;

 		case PHOTO_REQUEST_GALLERY:// 本地
 			if (data != null) {
				Bitmap bm = null;
				// 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
				ContentResolver resolver = getContentResolver();
				// 此处的用于判断接收的Activity是不是你想要的那个
				try {
					Uri originalUri = data.getData(); // 获得图片的uri
//					bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
					// 这里开始的第二部分，获取图片的路径：
					String[] proj = { MediaStore.Images.Media.DATA };
					// 好像是android多媒体数据库的封装接口，具体的看Android文档
					Cursor cursor = managedQuery(originalUri, proj, null, null, null);
					// 按我个人理解 这个是获得用户选择的图片的索引值
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					// 将光标移至开头 ，这个很重要，不小心很容易引起越界
					cursor.moveToFirst();
					// 最后根据索引值获取图片路径
					String path = cursor.getString(column_index);
					  System.out.println("本地图片的绝对路劲=="+path);
						BitmapFactory.Options opts = new BitmapFactory.Options();
						opts.inSampleSize = 2; //这个的值压缩的倍数（2的整数倍），数值越小，压缩率越小，图片越清晰
						//返回原图解码之后的bitmap对象
						bm = BitmapFactory.decodeFile(path, opts);
						
						bendi_path = savePublishPicture(ImageCrop(bm));
						System.out.println("本地图片保存后的绝对路劲=="+bendi_path);
				} catch (Exception e) {
					Log.e("TAG-->Error", e.toString());
				}
			}
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.userinfor, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}
	public void tijiao(){
		HttpUserInfo.updateUserInfo(new CallbackLogic() {
			
			

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				System.out.println("xingbie"+UserLoginStatus.get(getApplicationContext(), "Gender"));
				finish();
			}
		}, UserLoginStatus.get(Userinfor.this,"Token"), UserLoginStatus.get(getApplicationContext(), "NickName"), UserLoginStatus.get(getApplicationContext(), "CarUrl"),
				UserLoginStatus.get(getApplicationContext(), "CarDesc"), UserLoginStatus.get(getApplicationContext(), "IconUrl"),  UserLoginStatus.get(getApplicationContext(), "Gender"), UserLoginStatus.get(getApplicationContext(), "BirthDate"), 
				 "", UserLoginStatus.get(getApplicationContext(), "ProvinceName"), "", UserLoginStatus.get(getApplicationContext(), "CityName"),UserLoginStatus.get(getApplicationContext(), "Job") , UserLoginStatus.get(getApplicationContext(), "Signatrue"), "", Userinfor.this);
	}
	// ================时间控件监听
		public String format001(int value) {
			// TODO Auto-generated method stub
			if (value < 10) {
				return "0" + value;
			}
			return String.valueOf(value);
		}

		@Override
		public void onScrollStateChange(NumberPicker view, int scrollState) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			// TODO Auto-generated method stub
			int viewId = picker.getId();
			if (R.id.NumberPicker_user_info_brithdaydate001 == viewId) {
				if (newVal >= today_year) {
					birthday_numberPickerYear.setValue(today_year);
					birthday_year = today_year;
					if (birthday_month >= today_month) {
						birthday_numberPickerMonth.setValue(today_month);
						birthday_month = today_month;
						if (birthday_day >= today_day) {
							birthday_numberPickerDay.setValue(today_day);
							birthday_day = today_day;
							return;
						}
					}
				}
				birthday_year = newVal;
			} else if (R.id.NumberPicker_user_info_brithdaydate002 == viewId) {
				if (newVal >= today_month && birthday_year == today_year) {
					birthday_numberPickerMonth.setValue(today_month);
					birthday_month = today_month;
					return;
				}
				birthday_month = newVal;
			} else if (R.id.NumberPicker_user_info_brithdaydate003 == viewId) {
				if (newVal >= today_day && birthday_month == today_month && birthday_year == today_year) {
					birthday_numberPickerDay.setValue(today_day);
					birthday_day = today_day;
					return;
				}
				birthday_day = newVal;
			}
		}
	/**
	 * 上传图片
	 */
	// 提交图片

	public  void send(final String url, final String post_pic) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Message message = new Message();
					message.what = 101;
					System.out.println("10101010101");
					message.obj = upload(UserLoginStatus.get(Userinfor.this,"Token"), url, post_pic);
					System.out.println("2020202020202");
					mhandler.sendMessage(message);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			
			if (UserLoginStatus.get(getApplicationContext(), "change").equals("1")) {
				dialog_zidingyi();
				
			} else {
				return super.onKeyDown(keyCode, event);

			}
			System.out.println(UserLoginStatus.get(getApplicationContext(), "change")+"*************************");
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
	// ==========提示用户是否保存信息
	public void dialog_zidingyi() {
		// 自定义布局
		View layout = this.getLayoutInflater().inflate(R.layout.mydialog, null);
		TextView queren = (TextView) layout.findViewById(R.id.TextView_mydialog_queren001);
		TextView quxiao = (TextView) layout.findViewById(R.id.TextView_mydialog_quxiao001);

		AlertDialog.Builder builder = new AlertDialog.Builder(Userinfor.this,
				AlertDialog.THEME_DEVICE_DEFAULT_DARK);
		final AlertDialog dialog_save = builder.create();
		dialog_save.setView(layout);
		queren.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_save.cancel();
				UserLoginStatus.save(getApplicationContext(), "change", "0");
				tijiao();
			}

		});
		quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserLoginStatus.save(getApplicationContext(), "change", "0");
				dialog_save.cancel();

			}
		});
		// 透明
		Window window = dialog_save.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.9f;
		window.setAttributes(lp);
		dialog_save.show();

	}
}
