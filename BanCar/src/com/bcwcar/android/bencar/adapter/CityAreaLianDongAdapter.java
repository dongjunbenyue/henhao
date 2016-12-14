package com.bcwcar.android.bencar.adapter;

import java.util.ArrayList;
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

public class CityAreaLianDongAdapter extends BaseAdapter {
	private List<Map<String, String>> list;
	Context context;
	public CityAreaLianDongAdapter(Context context,List<Map<String, String>> list){
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
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_liandong_item, parent, false);
		}
		TextView name=(TextView)convertView.findViewById(R.id.textView_liangdong001);
		name.setText(list.get(position).get("ProvinceName").toString());
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

}
