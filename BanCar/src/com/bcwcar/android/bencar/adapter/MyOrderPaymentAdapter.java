package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bcwcar.android.bencar.R;


import com.bcwcar.android.bencar.activity.PaymentMethodActivity;
import com.bcwcar.android.bencar.activity.MainMy.AddProjectMyOrder;
import com.bcwcar.android.bencar.activity.MainMy.CancelOrderActivity;
import com.bcwcar.android.bencar.activity.MainMy.DaijiaActivity;
import com.bcwcar.android.bencar.activity.MainMy.EvaluateActivity;
import com.bcwcar.android.bencar.activity.MainMy.RefundList;
import com.bcwcar.android.bencar.activity.MainMy.RefundReasonActivity;
import com.bcwcar.android.bencar.activity.MainMy.ServiceActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.util.PageUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 我的订单|已付款 适配器 -->
 */
public class MyOrderPaymentAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, String>> listViewData;
	//private String status;
	public MyOrderPaymentAdapter(Context context, List<Map<String, String>> listViewData) {
		super();
		//this.status=status;
		this.context = context;
		this.listViewData = listViewData;
	}
	
	@Override
	public int getCount() {
		return listViewData.size();
	}

	@Override
	public Object getItem(int position) {
		return listViewData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder; 
		Map<String, String> item = listViewData.get(position);
		final String biaoji= listViewData.get(position).get("OrderType");
		String cc=(String)item.get("StatusCode");
		System.out.println(cc+"*****000000000000");
//	    if (convertView == null) {
	    	LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
	    	 holder = new ViewHolder();
	    	if (biaoji.equals("1")||biaoji.equals("2")) {
	    		convertView = layoutInflater.inflate(R.layout.list_item_my_order_comm, parent, false); 
	    		DingDanDataSave.save(context, "Type", "");
		        holder.carInfo = (TextView) convertView.findViewById(R.id.list_item_my_order_car_info);
//			    holder.bizInfo = (TextView) convertView.findViewById(R.id.list_item_my_order_biz_info);
			    holder.btncarname=(TextView)convertView.findViewById(R.id.carname);
	    		  //车牌号+(公里数)
	    	    String carInfo = item.get("PlateNumber") + "(" + item.get("Miles") + "KM)";
	    	    holder.carInfo.setText(carInfo);
	    	    holder.btncarname.setText(item.get("CarName"));
	    	    holder.bizInfo=(ImageView)convertView.findViewById(R.id.fuwutype);
	    	    holder.bizInfo.setImageResource(R.drawable.dingdan_qichebaoyang);
	    	    holder.orderName=(TextView)convertView.findViewById(R.id.fuwutype1);
	    	    holder.orderName.setText(item.get("ProjectName"));
			}else{
				convertView = layoutInflater.inflate(R.layout.list_item_my_order_comm1, parent, false); 
				DingDanDataSave.save(context, "Type", "1");
		        holder.carInfo = (TextView) convertView.findViewById(R.id.list_item_my_order_car_info);
//			    holder.bizInfo = (TextView) convertView.findViewById(R.id.list_item_my_order_biz_info);
			    holder.btncarname=(TextView)convertView.findViewById(R.id.carname);
			    holder.carInfo.setText(item.get("Quantity"));
			    holder.bizInfo=(ImageView)convertView.findViewById(R.id.fuwutype);
	    	    holder.bizInfo.setImageResource(R.drawable.dingdan_qichemeirong);
	    	    holder.orderName=(TextView)convertView.findViewById(R.id.fuwutype1);
	    	    holder.orderName.setText(item.get("ProjectName"));
			}
	        
	       
	        
	        holder.orderStatus = (TextView) convertView.findViewById(R.id.list_item_my_order_order_status);
	        holder.shopName = (TextView) convertView.findViewById(R.id.list_item_my_order_shop_name);
	        holder.orderAmount = (TextView) convertView.findViewById(R.id.list_item_my_order_order_amount);

		    
		   
		    holder.addProject = (LinearLayout) convertView.findViewById(R.id.list_item_my_order_layout_add_project);
//		    if (biaoji.equals("1")&&(cc.equals("1")||cc.equals("7"))) {
//		    	holder.addProject.setVisibility(View.VISIBLE);
//				}
		    holder.addProject.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Bundle bundle=new Bundle();
					bundle.putString("OrderId", listViewData.get(position).get("OrderId"));
					bundle.putString("CarId", listViewData.get(position).get("CarId"));
					bundle.putString("ShopId", listViewData.get(position).get("ShopId"));
					
					bundle.putString("PlateNumber", listViewData.get(position).get("PlateNumber"));
					bundle.putString("Gender", listViewData.get(position).get("Gender"));
					bundle.putString("Contact", listViewData.get(position).get("Contact"));
					bundle.putString("CellPhone", listViewData.get(position).get("CellPhone"));
					bundle.putString("MaintenanceTime",listViewData.get(position).get("MaintenanceTime"));
					bundle.putString("MaintenanceSpan", listViewData.get(position).get("MaintenanceSpan"));
					bundle.putString("Miles", listViewData.get(position).get("Miles"));
					PageUtil.jumpTo(context, AddProjectMyOrder.class,bundle);
					
					
				}
			});
		    
//		    holder.btnLeft = (TextView) convertView.findViewById(R.id.list_item_my_order_btn_left);
//		    holder.btnLeft.setVisibility(View.INVISIBLE);
		    
		    switch (cc) {
		    //代付款
			case "1":
				holder.btnRight = (TextView) convertView.findViewById(R.id.list_item_my_order_btn_right);
			    holder.btnRight.setVisibility(View.VISIBLE);
			    holder.btnRight.setText("去支付");
			    holder.btnLeft=(TextView)convertView.findViewById(R.id.list_item_my_order_btn_left);
			    holder.btnLeft.setText("取消订单");
			   holder.btnLeft.setVisibility(View.VISIBLE);
			    holder.btnLeft.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Bundle bundle = new Bundle();
						
						bundle.putString("OrderId", listViewData.get(position).get("OrderId"));
						bundle.putString("OrderType", biaoji);
						bundle.putString("ShopName", listViewData.get(position).get("ShopName"));
						bundle.putString("ShopAddress", listViewData.get(position).get("ShopAddress"));
						bundle.putString("LogoUrl", listViewData.get(position).get("LogoUrl"));
					PageUtil.jumpTo(context, CancelOrderActivity.class,bundle);
					}
				}
			    		
			    		);
			    holder.btnRight.setOnClickListener(new View.OnClickListener() {
			    	
			    	@Override
			    	public void onClick(View v) {
			    		Log.i("", "btnRight onClick()");
			    		Bundle bundle = new Bundle();
			    		bundle.putString("bancarPrice", listViewData.get(position).get("ActualPrice"));
			    		bundle.putString("action", "1");
			    		bundle.putString("OrderType", biaoji);
			    		bundle.putString("OrderId",listViewData.get(position).get("OrderId"));
			    		DingDanDataSave.save(context, "PaySum", listViewData.get(position).get("ActualPrice"));
			    		DingDanDataSave.save(context, "OrderId", listViewData.get(position).get("OrderId"));
			    		DingDanDataSave.save(context, "OrderType", biaoji);
			    		PageUtil.jumpTo(context, PaymentMethodActivity.class, bundle);
			    	}
			    });
				break;
				//服务中
                case "2":
                	 holder.btnRight=(TextView)convertView.findViewById(R.id.list_item_my_order_btn_right);
                	 holder.btnRight.setVisibility(View.VISIBLE);
                	 holder.btnRight.setText("订单跟踪");
                	 
                	 holder.btnRight.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Bundle bundle=new Bundle();
							bundle.putString("OrderId", listViewData.get(position).get("OrderId"));
							bundle.putString("ShopName", listViewData.get(position).get("ShopName"));
							bundle.putString("ShopAddress", listViewData.get(position).get("ShopAddress"));
							bundle.putString("LogoUrl", listViewData.get(position).get("LogoUrl"));
							bundle.putString("OrderType", biaoji);
							// TODO Auto-generated method stub
							PageUtil.jumpTo(context, ServiceActivity.class,bundle);
							
						}
					});
                	 
                     break;
                     //待评价
                case "3":
                	holder.btnRight=(TextView)convertView.findViewById(R.id.list_item_my_order_btn_right);
               	 holder.btnRight.setVisibility(View.VISIBLE);
               	 holder.btnRight.setText("去评价");
               	 holder.btnRight.setOnClickListener(new View.OnClickListener() {
 			    	
 			    	@Override
 			    	public void onClick(View v) {
 			    		Log.i("", "btnRight onClick()");
 			    		Bundle bundle = new Bundle();
 			    		bundle.putString("OrderId", listViewData.get(position).get("OrderId"));
 			    		bundle.putString("OrderType", biaoji);
 			    		PageUtil.jumpTo(context, EvaluateActivity.class, bundle);
 			    	}
 			    });
               	 break;
               	 //退款中
               	 case "4":
               		 holder.btnRight=(TextView)convertView.findViewById(R.id.list_item_my_order_btn_right);
               	 holder.btnRight.setVisibility(View.VISIBLE);
               	 holder.btnRight.setText("查看退款");
               	 holder.btnRight.setOnClickListener(new View.OnClickListener() {
 			    	
 			    	@Override
 			    	public void onClick(View v) {
 			    		Log.i("", "btnRight onClick()");
 			    		Bundle bundle = new Bundle();
 			    		bundle.putString("OrderId", listViewData.get(position).get("OrderId"));
 			    		bundle.putString("OrderType", biaoji);
 			    		bundle.putString("ShopName", listViewData.get(position).get("ShopName"));
						bundle.putString("ShopAddress", listViewData.get(position).get("ShopAddress"));
						bundle.putString("LogoUrl", listViewData.get(position).get("LogoUrl"));
 			    		PageUtil.jumpTo(context, RefundList.class, bundle);
 			    	}
 			    });
               	 break;
               	 //已退款
               	 case "5":
               		holder.btnRight=(TextView)convertView.findViewById(R.id.list_item_my_order_btn_right);
                  	 holder.btnRight.setVisibility(View.INVISIBLE);
                  	 holder.btnRight.setText("已退款");
                  	 break;
                 //已取消 	 
                 case "6":
                	 break;
                //已付款	 
                 case "7":
                	 holder.btnLeft=(TextView)convertView.findViewById(R.id.list_item_my_order_btn_left);
                	 holder.btnLeft.setText("找代驾");
                  	 holder.btnLeft.setVisibility(View.VISIBLE);
                  	 holder.btnLeft.setOnClickListener(new View.OnClickListener(){
                  		 @Override
                  		public void onClick(View v) {
                  			// TODO Auto-generated method stub
                  			Bundle bundle=new Bundle();
							bundle.putString("OrderId", listViewData.get(position).get("OrderId"));
							
							PageUtil.jumpTo(context, DaijiaActivity.class,bundle);
                  			 
                  			 
                  			
                  		}
                  	 });
                	 holder.btnRight=(TextView)convertView.findViewById(R.id.list_item_my_order_btn_right);
                	 holder.btnRight.setVisibility(View.VISIBLE);
                	 
                  	 holder.btnRight.setText("退款");
                	 holder.btnRight.setOnClickListener(new View.OnClickListener() {
						
						
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Bundle bundle=new Bundle();
							bundle.putString("OrderId", listViewData.get(position).get("OrderId"));
							bundle.putString("ShopName", listViewData.get(position).get("ShopName"));
							bundle.putString("ShopAddress", listViewData.get(position).get("ShopAddress"));
							bundle.putString("LogoUrl", listViewData.get(position).get("LogoUrl"));
							bundle.putString("OrderType", biaoji);
							PageUtil.jumpTo(context, RefundReasonActivity.class,bundle);
							
						}
					});
                  	  break;
                  	  //已评价
                  	  case "8":
                  		  break;
			default:
				break;
			}
		    
		    
	        convertView.setTag(holder);
//	    } else { 
//	        holder = (ViewHolder) convertView.getTag();
//	    }
		
	   // Map<String, String> item = listViewData.get(position);
	    
	    holder.orderStatus.setText(item.get("StatusName"));
	    holder.shopName.setText( item.get("ShopName"));
	    holder.orderAmount.setText("¥" +item.get("ActualPrice"));
	  
//	    holder.bizInfo.setText(item.get("OrderTime"));
	   
	    BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}
	
	private static class ViewHolder { 
		TextView orderName;
	    TextView orderStatus;
	    TextView shopName;
	    TextView orderAmount;
	    TextView carInfo;
	    ImageView bizInfo;
	    TextView btnLeft;
	    TextView btnRight;
	    TextView btncarname;
	    LinearLayout addProject;
	}
}