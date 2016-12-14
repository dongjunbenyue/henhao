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
public class TicketDiscoverAdapter extends BaseAdapter {
	 Context context;
	 List<Map<String, String>> list;
	public TicketDiscoverAdapter(Context context,List<Map<String, String>> list) {
		this.context=context;
		this.list=list;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
	    if (convertView == null) {
	    	LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
	        convertView = layoutInflater.inflate(R.layout.all_shop_ticket_item, parent, false); 
	        holder = new ViewHolder();
	        holder.shopname = (TextView) convertView.findViewById(R.id.TextView_all_shop_ticket_shopname001);
	        holder.ticket_price = (TextView) convertView.findViewById(R.id.TextView_all_shop_ticket_ticketprice001);
	        holder.ticket_price002 = (TextView) convertView.findViewById(R.id.TextView_all_shop_ticket_ticketprice0002);
	        holder.start_date = (TextView) convertView.findViewById(R.id.TextView_all_shop_ticket_date001);
	        holder.end_date = (TextView) convertView.findViewById(R.id.TextView_all_shop_ticket_date002);
	        holder.ticketname=(TextView)convertView.findViewById(R.id.TextView_all_shop_ticket_ticketname001);
	        convertView.setTag(holder);
	    } else { 
	        holder = (ViewHolder) convertView.getTag();
	    }
		
		Map<String, String> lineData = list.get(position);
	   
	    holder.shopname.setText(lineData.get("ShopName"));
	    holder.ticket_price.setText(lineData.get("ActualSum").toString());
	    holder.ticket_price.getPaint().setFakeBoldText(true); // 中文字体加粗
	    holder.start_date.setText(lineData.get("StartDate").toString());
	    holder.end_date.setText(lineData.get("EndDate"));
	    holder.ticketname.setText(lineData.get("TicketName"));
	    BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}
	
	private static class ViewHolder { 
		TextView shopname,ticket_price,start_date,end_date,ticketname,ticket_price002;
		
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