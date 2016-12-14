package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.MyBaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

/**
 * 抵用券列表的适配器
 */
public class TicketAdapter extends MyBaseAdapter<Map<String,String>> {
	 Context context;
	 List<Map<String, String>> list;
	public TicketAdapter(Context context,List<Map<String, String>> list) {
		super(context);
		this.context=context;
		this.list=list;
		System.out.println("99999900");
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("99999911");
		ViewHolder holder;
	    if (convertView == null) {
	    	System.out.println("99999911");
	    	LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
	        convertView = layoutInflater.inflate(R.layout.list_item_my_coupon, parent, false); 
	        holder = new ViewHolder();
	        holder.amount = (TextView) convertView.findViewById(R.id.list_item_my_coupon_amount);
	        holder.title = (TextView) convertView.findViewById(R.id.list_item_my_coupon_title);
	        holder.typeDesc = (TextView) convertView.findViewById(R.id.list_item_my_coupon_typeDesc);
	        holder.expirationTime = (TextView) convertView.findViewById(R.id.list_item_my_coupon_expirationTime);
	        holder.cardtype=(TextView)convertView.findViewById(R.id.cardtype);
	      
	        convertView.setTag(holder);
	    } else { 
	    	System.out.println("99999933");
	        holder = (ViewHolder) convertView.getTag();
	    }
		
	   
	    System.out.println("99999922");
		Map<String, String> lineData = list.get(position);
	    System.out.print("LINEData"+lineData);
	    holder.amount.setText("￥"+lineData.get("TicketSum"));
	    holder.title.setText("门店："+lineData.get("ShopName").toString());
	    holder.typeDesc.setText("使用："+lineData.get("TicketDesc").toString());
	    holder.expirationTime.setText("期限："+lineData.get("StartDate")+"至"+lineData.get("EndDate"));
	    if (lineData.get("TicketType").equals("1")) {
	    	 holder.cardtype.setText("商家卡券");
		} else if (lineData.get("TicketType").equals("2")) {
			 holder.cardtype.setText("系统卡券");
		}
	   
	    BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}
	
	private static class ViewHolder { 
		TextView amount;
		TextView title;
		TextView typeDesc;
		TextView expirationTime;
		TextView cardtype;
		
	}
}