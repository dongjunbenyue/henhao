package com.bcwcar.android.bencar.activity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainMy.EvaluateActivity;
import com.bcwcar.android.bencar.activity.MainMy.RefundList;
import com.bcwcar.android.bencar.activity.MainMy.RefundReasonActivity;
import com.bcwcar.android.bencar.adapter.BaoyanglistAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.squareup.okhttp.Request;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayout;
import android.text.StaticLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
/**
 * 订单详情页
 */
public class PaymentResultOrderDetailActivity extends BaseActivity {
	private static final String LOG_TAG = PaymentResultOrderDetailActivity.class.getSimpleName();
	
	LinearLayout fapiaoline;
	private ListView listView;
	private List<Map<String, String>> data1;
	private LinearLayout textbuttonfuji;
	private LinearLayout mianfei_text;
	private LinearLayout linnn1, linearLayouttopay;
private Button quzhifu;
  	String string111=null;
    double ddDouble;
	int kkk=0;
	private TextView payStatus11;
	private TextView zongjia;
	private TextView youhuiquan;
    private SimpleDraweeView wangdian_logo0011;
	private TextView carType,serviceshopname,textbutton;

	private TextView carNumber,benchequanma;

	private TextView distance;
	private TextView fapiao;
	private TextView maintenanceTime;

	private TextView orderNo;

	private TextView paystatus;

	private TextView toatlePrice;

	private TextView diyongquannum;

	private TextView ordertime;

	private TextView contactName;

	private TextView phoneNumber;

	private TextView shifujine;

	private TextView shopadress;

	private LinearLayout linebenchequanma;
	
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button)rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
		// leftView.setVisibility(View.GONE);
		leftView.setText("");
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText(getString(R.string.shop4s_maintain_payment_result_order_details_title));
		rightView.setBackgroundResource(R.drawable.icon_dianhua);
		rightView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent dialNumber = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "400-018-5025"));
				startActivity(dialNumber);
			}
		});
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_shop4s_maintain_payment_result_order_detail, bodyParentView);
		DingDanDataSave.save(getApplicationContext(), "Type", "");
		
		
		final String OrderId = getIntent().getStringExtra("OrderId");
		wangdian_logo0011=(SimpleDraweeView)findViewById(R.id.wangdian_logo0011);
		benchequanma=(TextView)findViewById(R.id.benchequanma);
		carType = (TextView) findViewById(R.id.carName);
		carNumber = (TextView) findViewById(R.id.plateNumber);
		distance = (TextView) findViewById(R.id.miles);
		maintenanceTime = (TextView) findViewById(R.id.MaintenanceTime);
		payStatus11=(TextView)findViewById(R.id.dingdanzhuangtia);
		orderNo = (TextView) findViewById(R.id.orderNo);
		textbuttonfuji=(LinearLayout)findViewById(R.id.textbuttonfuji);
		textbutton=(TextView)findViewById(R.id.textbutton);
		serviceshopname=(TextView)findViewById(R.id.serviceshopname);
		paystatus = (TextView) findViewById(R.id.payStatus);
		toatlePrice = (TextView) findViewById(R.id.ourPrice);
		shifujine=(TextView)findViewById(R.id.shifujine);
		diyongquannum = (TextView) findViewById(R.id.orderTypeName);
		ordertime = (TextView) findViewById(R.id.orderTime);
		listView = (ListView) findViewById(R.id.listservice);
		contactName = (TextView) findViewById(R.id.contactName1111);
		phoneNumber = (TextView) findViewById(R.id.contactNumber1111);
		zongjia = (TextView) findViewById(R.id.zongjia);
		shopadress=(TextView)findViewById(R.id.shopadress);
		fapiaoline=(LinearLayout)findViewById(R.id.linefapiao);
		youhuiquan=(TextView)findViewById(R.id.youhuiquan);
		fapiao=(TextView)findViewById(R.id.fapiaotaitou);
		linebenchequanma=(LinearLayout)findViewById(R.id.linebenchequanma);
		kkk=(int)ddDouble;
		
		
		HttpOrder.getMyOrder(new CallbackLogic() {


			

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
             					
					final JSONObject data1 = data.getJSONObject(BizDefineAll.BIZ_RESPONSE_DATA);
					ResourceUtil.setSimpleDraweeViewImage(wangdian_logo0011, data1.get("LogoUrl").toString());
					ordertime.setText(data1.get("OrderTime").toString());
					serviceshopname.setText(data1.get("ShopName").toString());
					shopadress.setText(data1.get("ShopAddress").toString());
					youhuiquan.setText("-￥"+data1.getString("TicketValue").toString());
					phoneNumber.setText(data1.get("CellPhone").toString());
					payStatus11 .setText( "订单状态："+data1.getString("StatusName").toString());
					carType.setText(data1.getString("CarName").toString());
					string111=data1.getString("Miles");
					carNumber.setText(data1.getString("PlateNumber").toString());
					ddDouble=Double.valueOf(string111).doubleValue();
					diyongquannum.setText("￥"+data1.getString("TicketValue").toString());
					System.out.println("TicketValue+++++++"+data1.getString("TicketValue").toString());
					kkk=(int)ddDouble;
					distance.setText(kkk+"公里");
					shifujine.setText("￥"+data1.getString("ActualPrice").toString());
					maintenanceTime.setText(data1.getString("MaintenanceTime").toString());
					orderNo.setText(data1.getString("OrderNo").toString());
					zongjia.setText("￥"+data1.getString("OurPrice").toString());
					System.out.println("OrderCoupon"+data1.optString("OrderCoupon"));
					benchequanma.setText(data1.optString("OrderCoupon"));
					if (data1.getString("BillFlag").equals("1")) {
						fapiaoline.setVisibility(View.VISIBLE);
						fapiao.setText(data1.getString("BillHead").toString());

					} else {
                        
                        fapiaoline.setVisibility(View.GONE);
					}
					
					toatlePrice.setText("￥"+data1.getString("ActualPrice").toString());
					paystatus.setText(data1.getString("OrderTypeName").toString());
					contactName.setText(data1.getString("Contact").toString());
					JSONArray array = data1.getJSONArray("Projects");
					List<Map<String, String>> listdata = CollectionUtil.jsonArrayToListMap(array);
					BaoyanglistAdapter adapter = new BaoyanglistAdapter(listdata, getApplicationContext());

					listView.setAdapter(adapter);
					setListViewHeight(listView);
					System.out.println(OrderId+"**********************");
					System.out.println(OrderId+"**********************1");
                    if (data1.getString("StatusName").toString().equals("退款中")) {
						textbuttonfuji.setVisibility(View.VISIBLE);
						textbutton.setText("查看退款");
						textbutton.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Bundle bundle = new Bundle();
		 			    		bundle.putString("OrderId", OrderId);
		 			    		PageUtil.jumpTo(PaymentResultOrderDetailActivity.this, RefundList.class, bundle);
							}
						});
					}else if (data1.getString("StatusName").toString().equals("未预约")) {
						System.out.println("ssdsdsdsdsdsdsdsdsdsd");
						textbuttonfuji.setVisibility(View.VISIBLE);
						textbutton.setText("退款");
						textbutton.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Bundle bundle=new Bundle();
								bundle.putString("OrderId", OrderId);
								PageUtil.jumpTo(PaymentResultOrderDetailActivity.this, RefundReasonActivity.class,bundle);
							}
						});
					}else if (data1.getString("StatusName").toString().equals("待付款")) {
						textbuttonfuji.setVisibility(View.VISIBLE);
						textbutton.setText("立即支付");
						linebenchequanma.setVisibility(View.GONE);
						textbutton.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Bundle bundle = new Bundle();
					    		try {
									bundle.putString("bancarPrice", data1.get("ActualPrice").toString());
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					    		try {
									DingDanDataSave.save(PaymentResultOrderDetailActivity.this, "PaySum", data1.get("ActualPrice").toString());
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					    		DingDanDataSave.save(PaymentResultOrderDetailActivity.this, "OrderId", OrderId);
					    		System.out.println(OrderId+"**********************");
					    		PageUtil.jumpTo(PaymentResultOrderDetailActivity.this, PaymentMethodActivity.class, bundle);
							}
						});
						
						
					}else if (data1.getString("StatusName").toString().equals("结算中")) {
						textbuttonfuji.setVisibility(View.VISIBLE);
						textbutton.setText("立即评价");
						textbutton.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Bundle bundle = new Bundle();
		 			    		bundle.putString("OrderId",OrderId);
		 			    		PageUtil.jumpTo(PaymentResultOrderDetailActivity.this, EvaluateActivity.class, bundle);
							}
						});
						
					}
					

				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}, UserLoginStatus.get(PaymentResultOrderDetailActivity.this,"Token"), OrderId,PaymentResultOrderDetailActivity.this);
		
		
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// LayoutInflater.from(this).inflate(R.layout.common_toolbar_1,
		// toolBarParentView);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
		params.height = totalHeight ;
		listView.setLayoutParams(params);

	}
}