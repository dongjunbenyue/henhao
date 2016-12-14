package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShopQuanAdapter extends BaseAdapter {
	private List<Map<String, String>> listViewData;
	private Context context;

	public ShopQuanAdapter(List<Map<String, String>> listViewData, Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listViewData = listViewData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listViewData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listViewData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.shop_quan_item_xml, parent, false);
		}
		TextView name=(TextView)convertView.findViewById(R.id.TextView_ticket_name001);
		TextView price=(TextView)convertView.findViewById(R.id.TextView_ticket_price001);
		TextView nomarl_price=(TextView)convertView.findViewById(R.id.TextView_ticket_nomarl_price001);
		TextView stop_date=(TextView)convertView.findViewById(R.id.TextView_ticket_stop_date001);
		name.setText(listViewData.get(position).get("TicketName").toString());
		price.setText("￥"+listViewData.get(position).get("TotalSum").toString());
		nomarl_price.setText("￥"+listViewData.get(position).get("ActualSum").toString());
		stop_date.setText(listViewData.get(position).get("EndDate").toString());
		
		
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

	
	
}
