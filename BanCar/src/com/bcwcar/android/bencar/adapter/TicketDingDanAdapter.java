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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 抵用券列表的适配器
 */
public class TicketDingDanAdapter extends BaseAdapter {
	 Context context;
	 List<Map<String, String>> list;
	public TicketDingDanAdapter(Context context,List<Map<String, String>> list) {
		this.context=context;
		this.list=list;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
	    if (convertView == null) {
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
	        holder = (ViewHolder) convertView.getTag();
	    }
		
	    @SuppressWarnings("unchecked")
		Map<String, String> lineData = list.get(position);
	   
	    holder.amount.setText("￥"+lineData.get("TicketSum"));
	    holder.title.setText("门店："+lineData.get("ShopName").toString());
	    holder.typeDesc.setText("使用："+lineData.get("TicketDesc").toString());
	    holder.expirationTime.setText("期限："+lineData.get("StartDate")+"至"+lineData.get("EndDate"));
	    holder.cardtype.setText(lineData.get("TicketName"));
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
}