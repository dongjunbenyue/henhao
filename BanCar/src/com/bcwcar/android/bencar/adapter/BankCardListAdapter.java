package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.StringUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * 选择默认车辆list的适配器
 */
public class BankCardListAdapter extends BaseAdapter {
	private List<Map<String, String>> listViewdata;
	private Context context;
	public BankCardListAdapter(Context context,List<Map<String, String>> listViewData) {
		super();
		this.context=context;
		this.listViewdata = listViewData;
	}

	@Override
	public int getCount() {
		return listViewdata.size();
	}

	@Override
	public Object getItem(int position) {
		return listViewdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		Map<String, String> item = listViewdata.get(position);
		if (convertView==null) {
			convertView = layoutInflater.inflate(R.layout.list_item_my_money_bank_card, parent, false);
		}
		TextView bank_name=(TextView)convertView.findViewById(R.id.list_item_my_money_bank_card_center_top);
		TextView bank_num=(TextView)convertView.findViewById(R.id.list_item_my_money_bank_card_right);
		bank_name.setText(listViewdata.get(position).get("BankName").toString());
		if (listViewdata.get(position).get("BankNo").toString().length()<16) {
			bank_num.setText("未知错误");
		}else {
			bank_num.setText(card_num_scert(listViewdata.get(position).get("BankNo").toString()));
		}
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

	//保留后4位
	public String card_num_scert(String string){
		String string001=string.substring(string.length()-4,string.length());
		return string001;
	}
}