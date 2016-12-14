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

public class JinEAdapter  extends BaseAdapter{
	private List<Map<String, Object>> list;
	private Context context;
 public JinEAdapter(Context context,List<Map<String, Object>> list) {
		// TODO Auto-generated constructor stub
	 this.context=context;
	 this.list=list;
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
		// TODO Auto-generated method stub
			if (convertView==null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jin_e_item, parent, false);
		}
			TextView Description=(TextView)convertView.findViewById(R.id.textView_jin_e_item001);
			TextView WalletTime=(TextView)convertView.findViewById(R.id.textView_jin_e_item002);
			TextView Wallet=(TextView)convertView.findViewById(R.id.textView_jin_e_item003);
			
			Description.setText(list.get(position).get("Description").toString());
			WalletTime.setText(list.get(position).get("WalletTime").toString());
			Wallet.setText("￥"+list.get(position).get("Wallet").toString());
			
			BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

}
