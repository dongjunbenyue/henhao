package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.FillInOrderActivity;
import com.bcwcar.android.bencar.activity.PaymentMethodActivity;
import com.bcwcar.android.bencar.adapter.Add1Adapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.squareup.okhttp.Request;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddProjectMyOrder extends BaseActivity{
	private static double bancar_toytal;
	private String OrderId;
	private String CarId;
	private List<Map<String, String>>  listlist=new ArrayList<>();
	private String ShopName;
	
	private String ShopAddress;
	private String CellPhone;
	private String Contact;
	private String Gender;
	private String PlateNumber;
	private String Miles;
	private String ShopId;
	private String MaintenanceSpan;
	private String MaintenanceTime;
	private static List<Map<String, String>>  listdata= new ArrayList<>();
	private static TextView total_num;
	private static double gongxiang_total;
	

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
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
		centerView.setText(getString(R.string.add_project_title));
		rightView.setVisibility(View.GONE);
		changeFonts(titleParentView);
	}
	// 提交订单
		public void tijiao(String plateNumber,String OrderId, String carId, String miles,
				String contact, String maintenanceTime, String MaintenanceSpan,String ticketId,String TicketType,
				String billFlag, String billHead, String cellPhone,
				String shopId, String gender,String accIds) {
			HttpOrder.addOrder(new CallbackLogic() {
				
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
					// TODO Auto-generated method stub
					try {
						Map<String, String> map=CollectionUtil.jsonObjectToMap_String(alldata);
						for(String key:map.keySet()){
							DingDanDataSave.save(AddProjectMyOrder.this,key, map.get(key));
						}
						System.out.println("8888888"+map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					PageUtil.jumpTo(AddProjectMyOrder.this, PaymentMethodActivity.class);
				}
			}, UserLoginStatus.get(AddProjectMyOrder.this, "Token"), plateNumber, OrderId, carId, miles, contact, maintenanceTime, MaintenanceSpan, ticketId, TicketType, billFlag, billHead, cellPhone, shopId, gender, accIds,"", AddProjectMyOrder.this);
		}
		// 获取订单id 供其他类调用
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.body_smart_maintenance_add_project, bodyParentView);
		TextView baoyan = (TextView) view.findViewById(R.id.add_project_go);
		final ListView listView=(ListView)view.findViewById(R.id.add_project_list);
		total_num = (TextView) findViewById(R.id.add_project_num);
		baoyan.setOnClickListener(new OnClickListener() {

			

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (total_num.getText().toString().equals("0")) {
					showToast("请选择项目");
				}else {
					
					PageUtil.jumpTo(AddProjectMyOrder.this, PaymentMethodActivity.class);
					
					
					tijiao(PlateNumber, OrderId, CarId, Miles, Contact, MaintenanceTime, MaintenanceSpan, "", "", "0", "", CellPhone, ShopId, Gender, save_dingdan(listdata));
					listdata.clear();
					finish();
				}
			}
		});
	OrderId=	getIntent().getStringExtra("OrderId");
	CarId=getIntent().getStringExtra("CarId");
	ShopId=getIntent().getStringExtra("ShopId");
	ShopAddress=getIntent().getStringExtra("ShopAddress");
	CellPhone=getIntent().getStringExtra("CellPhone");
	Contact=getIntent().getStringExtra("Contact");
	Gender=getIntent().getStringExtra("Gender");
	PlateNumber=getIntent().getStringExtra("PlateNumber");
	Miles=getIntent().getStringExtra("Miles");
	MaintenanceSpan=getIntent().getStringExtra("MaintenanceSpan");
	MaintenanceTime=getIntent().getStringExtra("MaintenanceTime");
//	set_dingdan_data("PlateNumber", PlateNumber);
//	set_dingdan_data("Gender", Gender);
//	set_dingdan_data("Contact", Contact);
//	set_dingdan_data("CellPhone", CellPhone);
//	set_dingdan_data("Miles", Miles);
//	set_dingdan_data("OrderId", OrderId);
//	set_dingdan_data("CarId", CarId);
//	set_dingdan_data("ShopName", ShopName);
//	set_dingdan_data("Address", ShopAddress);
//	set_dingdan_data("MaintenanceTime", getIntent().getStringExtra("MaintenanceTime"));
//	set_dingdan_data("MaintenanceSpan", getIntent().getStringExtra("MaintenanceSpan"));
//	Dialogshow(getString(R.string.jiazai_data));
	HttpOrder.getZhuiJia(new CallbackLogic() {
		
		

		

		@Override
		public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
			// TODO Auto-generated method stub
			try {
				System.out.println(data);
			
				JSONArray mMaintenanceProject = data.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA);
				json_zhineng(mMaintenanceProject);
				System.out.println("**********************"+listlist);
				listView.setAdapter(new Add1Adapter(listlist, getApplicationContext()));
				setListViewHeight(listView);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}, UserLoginStatus.get(AddProjectMyOrder.this, "Token"), OrderId, CarId,AddProjectMyOrder.this);
	changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}
	public void json_zhineng(JSONArray mMaintenanceProject) {
		
			try {
				
				for (int i = 0; i < mMaintenanceProject.length(); i++) {
					JSONObject object = mMaintenanceProject.getJSONObject(i);
					Map<String, String> map = new HashMap<String, String>();
					map.put("ProjectName", object.getString("ProjectName"));
					map.put("ProjectId", object.getString("ProjectId"));
					map.put("ProBenefit", object.getString("ProBenefit"));
					map.put("ProjectOrder", object.getString("ProjectOrder"));
					if (object.isNull("Projects")) {
						Toast.makeText(getApplicationContext(), "无报价", Toast.LENGTH_SHORT).show();
						map.put("Discount", "");
						map.put("AccNum", "");//"ShareDiscount": "0%",
						map.put("ShareDiscount", "0.00%");
						map.put("ShopPrice", "0.00");
						map.put("DiscountPrice", "0.00");
						map.put("DeleteFlag", "");
						map.put("UserBenifit", "");
						map.put("TimePrice", "");
						map.put("TimeDiscount", "");
						map.put("AfterDiscountTimePrice", "");
						map.put("Accessory", "");
						map.put("TotalBenifit", "");
						map.put("BenDiscount", "");
						map.put("TotalPrice", "");
						map.put("SalePrice", "");
						map.put("AfterDiscountSalePrice", "");
						map.put("AccId", "");
					} else {
//						String Projects = object.getString("Projects");
//						JSONArray projectsJSONArray = object.getJSONArray("Projects");
//						JSONObject object2 = projectsJSONArray.getJSONObject(0);
//						map.put("Discount",object2.getString("Discount").toString());
//						map.put("AccNum", object2.getString("AccNum").toString());
//						map.put("ShareDiscount",object2.getString("ShareDiscount").toString());
//						map.put("ShopPrice", object2.getString("ShopPrice").toString());
//						map.put("BenPrice", object2.getString("BenPrice").toString());
//						map.put("DeleteFlag",object2.getString("DeleteFlag").toString());
//						map.put("UserBenifit",object2.getString("UserBenifit").toString());
//						map.put("TimePrice", object2.getString("TimePrice").toString());
//						map.put("TimeDiscount", object2.getString("TimeDiscount").toString());
//						map.put("AfterDiscountTimePrice",object2.getString("AfterDiscountTimePrice").toString());
//						map.put("Accessory", object2.getString("Accessory").toString());
//						map.put("TotalBenifit",object2.getString("TotalBenifit").toString());
//						map.put("BenDiscount", object2.getString("BenDiscount").toString());
//						map.put("TotalPrice", object2.getString("TotalPrice").toString());
//						map.put("SalePrice", object2.getString("SalePrice").toString());
//						map.put("AfterDiscountSalePrice", object2.getString("AfterDiscountSalePrice").toString());
//						map.put("AccId", object2.getString("AccId").toString());
						String Projects = object.getString("Projects");
						JSONArray projectsJSONArray = object.getJSONArray("Projects");
						JSONObject object2 = projectsJSONArray.getJSONObject(0);
						map=CollectionUtil.jsonObjectToMap(object2);
						map.put("IconUrl", object.optString("IconUrl", ""));
					}
					listlist.add(map);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	
	public static void tongji(List<Map<String, String>> list_zhineng) {
		double s4_total = 0.00;
		bancar_toytal=0.00;
		 gongxiang_total=0.00;
		String gongxiang;
		for (int i = 0; i < list_zhineng.size(); i++) {
			
			String bancar = list_zhineng.get(i).get("DiscountPrice").toString();
			
			
			bancar_toytal += Double.valueOf(bancar).doubleValue();
		}
		add();
		}

	public static void add() {
		total_num.setText(bancar_toytal + "");

	}
//	public static void add(Map<String, Object> list) {
//		listdata.add(list);
//		
//		adapter_zhineng.notifyDataSetChanged();
//		setListViewHeight(lv);
//		tongji();
//	}
	public static void add1(Map<String, String> list) {
		listdata.add(list);
		tongji(listdata);
	}
	public static void delete(int position) {
		listdata.remove(position);
		tongji(listdata);
	}
	
	public static void setListViewHeight(ListView listView) {
		int totalHeight = 0;
		ListAdapter listAdapter = listView.getAdapter();
		for (int i = 0; i < listAdapter.getCount(); i++) {

			View listItemView = listAdapter.getView(i, null, listView);
			int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
			listItemView.measure(desiredWidth, 0);
			totalHeight += listItemView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;
		listView.setLayoutParams(params);

	}

	public void onViewClick(View view) {
		// TODO Auto-generated method stub
		
	}
	public String save_dingdan(List<Map<String, String>> list_zhineng) {
		
		StringBuffer sb16 = new StringBuffer();
		for (int i = 0; i < list_zhineng.size(); i++) {
                     
			
			sb16.append(list_zhineng.get(i).get("AccId"));
			sb16.append(",");

		}
		if (list_zhineng.size()>0) {
			
			sb16.deleteCharAt(sb16.length() - 1);
		}
		
//
//		set_dingdan_data("ProjectNames", sb1.toString());
//		set_dingdan_data("SharePrices", sb2.toString());
//		set_dingdan_data("ProjectIds", sb3.toString());
//		set_dingdan_data("Discounts", sb4.toString());
//		set_dingdan_data("AccNums", sb5.toString());
//		set_dingdan_data("ShopPrices", sb6.toString());
//		set_dingdan_data("BenPrices", sb7.toString());
//		set_dingdan_data("TimePrices", sb9.toString());
//		set_dingdan_data("TimeDiscounts", sb10.toString());
//		set_dingdan_data("AfterDiscountTimePrices", sb11.toString());
//		set_dingdan_data("AccNames", sb12.toString());
//		set_dingdan_data("SumPrices", sb13.toString());
//		set_dingdan_data("SalePrices", sb14.toString());
//		set_dingdan_data("AfterDiscountSalePrices", sb15.toString());
//		set_dingdan_data("AccIds", sb16.toString());
		return sb16.toString();
	}
	

}
