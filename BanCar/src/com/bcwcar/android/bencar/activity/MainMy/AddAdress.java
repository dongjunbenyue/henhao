package com.bcwcar.android.bencar.activity.MainMy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.NumberPicker.Formatter;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;

import com.bcwcar.android.bencar.adapter.CityAreaLianDongAdapter;
import com.bcwcar.android.bencar.adapter.CityAreaLianDongAdapter2;
import com.bcwcar.android.bencar.adapter.CityAreaLianDongAdapter3;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpUserInfo;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.squareup.okhttp.Request;

public class AddAdress extends BaseActivity {
	
   private List<Map<String, String>> list_ProvinceName=new ArrayList<Map<String, String>>();
   private List<Map<String, String>> list_city=new ArrayList<Map<String, String>>();
   private List<Map<String, String>> list_area=new ArrayList<Map<String, String>>();
   private LinearLayout city_xuanze,city_done;
   private RelativeLayout city_show;
   private TextView city_result,quxiao,queren,tijiao;
   private TextView show001,show002,show003;
   private int zhizhen_ProvinceName=0;
   private int zhizhen_city=0;
   private int zhizhen_area=0;
   
   private ListView listview_ProvinceName,listview_city,listview_area;
 private CityAreaLianDongAdapter	adapter001;
 private CityAreaLianDongAdapter2	adapter002;
 private CityAreaLianDongAdapter3	adapter003;
 private EditText name,phone,address_deatil,youbian;
private InputMethodManager imm;
 
   
	@Override
public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
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
		centerView.setText("新增地址");
		rightView.setVisibility(View.GONE);
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AddAdress.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.address_add, bodyParentView);
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
		city_item();
		list_city(0);
		list_area(0);
		initview();
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
	}
	
	
	public void initview(){
		city_done=(LinearLayout)findViewById(R.id.LinearLayout_city_liandong_test001);
		city_done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				city_xuanze.setVisibility(View.GONE);
			}
		});
		name=(EditText)findViewById(R.id.EditText_address_name001);
		phone=(EditText)findViewById(R.id.EditText_address_phone001);
		address_deatil=(EditText)findViewById(R.id.EditText_address_detail001);
		youbian=(EditText)findViewById(R.id.EditText_address_youbian001);
		tijiao=(TextView)findViewById(R.id.TextView_address_restaul001);
		tijiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String contact=name.getText().toString();
				String contactPhone=phone.getText().toString();
				String address=address_deatil.getText().toString();
				String zipCode=youbian.getText().toString();
				if (contact.equals("")&&contactPhone.equals("")&&address.equals("")&&zipCode.equals("")) {
					showToast("信息不完整");
				}else{
					if (list_area.isEmpty()) {
						initdata(contact, contactPhone, address, zipCode, list_ProvinceName.get(zhizhen_ProvinceName).get("ProvinceId").toString(),
								list_ProvinceName.get(zhizhen_ProvinceName).get("ProvinceName").toString(),
								list_city.get(zhizhen_city).get("CityId").toString(), 
								list_city.get(zhizhen_city).get("CityName").toString(),"","");

					}else {
						
						initdata(contact, contactPhone, address, zipCode, list_ProvinceName.get(zhizhen_ProvinceName).get("ProvinceId").toString(),
								list_ProvinceName.get(zhizhen_ProvinceName).get("ProvinceName").toString(),
								list_city.get(zhizhen_city).get("CityId").toString(), 
								list_city.get(zhizhen_city).get("CityName").toString(), 
								list_area.get(zhizhen_area).get("DistrictId").toString(), 
								list_area.get(zhizhen_area).get("DistrictName").toString());
					}
				}
					
				}
		});
		show001=(TextView)findViewById(R.id.TextView_city_show001);
		show002=(TextView)findViewById(R.id.TextView_city_show002);
		show003=(TextView)findViewById(R.id.TextView_city_show003);
		
		city_xuanze=(LinearLayout)findViewById(R.id.LinearLayout_city_liandong001);//pop弹窗，城市选择
		city_show=(RelativeLayout)findViewById(R.id.RelativeLayout_city_view_show001);//点击显示
		city_show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(imm != null) {   
					imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);   
				                    }  


				city_xuanze.setVisibility(View.VISIBLE);
			}
		});
		city_result=(TextView)findViewById(R.id.TextView_city_result001);//城市结果展示textview
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
				show_textview();
				city_xuanze.setVisibility(View.GONE);
			}
		});
		
		
		listview_ProvinceName=(ListView)findViewById(R.id.ListView_city001);
		listview_city=(ListView)findViewById(R.id.ListView_city002);
		listview_area=(ListView)findViewById(R.id.ListView_city003);
		adapter001=new CityAreaLianDongAdapter(getApplicationContext(), list_ProvinceName);
		listview_ProvinceName.setAdapter(adapter001);
		adapter002=new CityAreaLianDongAdapter2(getApplicationContext(), list_city);
		listview_city.setAdapter(adapter002);
		adapter003=new CityAreaLianDongAdapter3(getApplicationContext(), list_area);
		listview_area.setAdapter(adapter003);
		
		listview_ProvinceName.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				zhizhen_ProvinceName=position;
				show001.setText("");
				show001.setText(list_ProvinceName.get(position).get("ProvinceName").toString());
				list_city.clear();
				list_area.clear();
				list_city(position);
				adapter002.notifyDataSetChanged();
				adapter003.notifyDataSetChanged();
			}
		});
		listview_city.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				zhizhen_city=position;
				show002.setText("");
				show002.setText(list_city.get(position).get("CityName").toString());
				list_area.clear();
				list_area(position);
				adapter003.notifyDataSetChanged();
			}
		});
		listview_area.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				zhizhen_area=position;
				show003.setText("");
				show003.setText(list_area.get(position).get("DistrictName").toString());
			}
		});
	}
	
	
	//提交地址信息
	public  void initdata(String contact, String contactPhone,
			String address, String zipCode, String provinceId, String provinceName, String cityId, String cityName,
			String districtId, String districtName){
		
		HttpUserInfo.addAddress(new CallbackLogic() {
			
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
				// TODO Auto-generated method stub
				Dialogcancel();
				finish();
			}
			
			
		}, UserLoginStatus.get(AddAdress.this, "Token"), contact, contactPhone, address, zipCode, provinceId, provinceName, cityId, cityName, districtId, districtName,getApplicationContext());
		
	}
	
	//提交地址信息
	public  void initdata001(String contact, String contactPhone,
			String address, String zipCode, String provinceId, String provinceName, String cityId, String cityName){
		
		HttpUserInfo.addAddress001(new CallbackLogic() {
			
			
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
				// TODO Auto-generated method stub
				Dialogcancel();
				finish();
			}
			
			
		},UserLoginStatus.get(AddAdress.this, "Token"), contact, contactPhone, address, zipCode, provinceId, provinceName, cityId, cityName,getApplicationContext());
		
	}

		//获取城市联动列表
		public void city_item(){
			 try {  
				            InputStream is = getAssets().open("SCQ.txt");  
				            int size = is.available();  
				            byte[] buffer = new byte[size];  
				            is.read(buffer);  
				            is.close();  
				  String text = new String(buffer, "utf-8");  
				  JSONObject object=new JSONObject(text);
				  JSONArray array=object.getJSONArray("Data");
				  for (int i = 0; i < array.length(); i++) {
					JSONObject object2=array.getJSONObject(i);
					Map<String, String> map=new HashMap<String, String>();
					map.put("ProvinceId", object2.getString("ProvinceId").toString());
					map.put("ProvinceName", object2.getString("ProvinceName").toString());
					map.put("Citys", object2.getString("Citys").toString());
					list_ProvinceName.add(map);
				}
				  
				             
				        } catch (Exception e) {  
				            // Should never happen!  
				            throw new RuntimeException(e);  
				        } 
			
		}
		//省份-------城市
		public void list_city(int tt){
			try {
				String citylist=list_ProvinceName.get(tt).get("Citys").toString();
				JSONArray array=new JSONArray(citylist);
				for (int i = 0; i <array.length(); i++) {
					JSONObject object=array.getJSONObject(i);
					
					Map<String, String> map=new HashMap<String, String>();
					map.put("CityId", object.getString("CityId").toString());
					map.put("CityName", object.getString("CityName").toString());
					if (object.isNull("Districts")) {
						
					}else {
						map.put("Districts", object.getString("Districts").toString());
						
					}
					list_city.add(map);
					
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//城市   -------区域
		public void list_area(int tt){
			try {
				if (list_city.get(tt).get("Districts")==null) {
					
				}else {
					
					String citylist=list_city.get(tt).get("Districts").toString();
					JSONArray array=new JSONArray(citylist);
					for (int i = 0; i <array.length(); i++) {
						JSONObject object=array.getJSONObject(i);
						
						Map<String, String> map=new HashMap<String, String>();
						map.put("DistrictName", object.getString("DistrictName").toString());
						map.put("DistrictId", object.getString("DistrictId").toString());
						list_area.add(map);
						
					}
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		

		
	     




	
	//选择的地址信息显示
	public void show_textview(){
		if (list_area.isEmpty()) {
			city_result.setText(list_ProvinceName.get(zhizhen_ProvinceName).get("ProvinceName").toString()
					+list_city.get(zhizhen_city).get("CityName").toString());
		}else {
			city_result.setText(list_ProvinceName.get(zhizhen_ProvinceName).get("ProvinceName").toString()
					+list_city.get(zhizhen_city).get("CityName").toString()
					+list_area.get(zhizhen_area).get("DistrictName").toString());
			
		}
		
	}
	
	
}
