package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.StringUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 选择车型详细情况list的适配器
 */
public class SelectCarinfoAdapter extends BaseAdapter {
	private List<Map<String, String>> listViewdata;
	public SelectCarinfoAdapter(List<Map<String, String>> listViewData) {
		super();
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
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		Map<String, String> item = listViewdata.get(position);
		convertView = layoutInflater.inflate(R.layout.list_item_select_brand, parent, false);
		SimpleDraweeView logo = (SimpleDraweeView) convertView.findViewById(R.id.select_brand_logo);
		logo.setVisibility(View.GONE);
		TextView brandName = (TextView) convertView.findViewById(R.id.select_brand_name);
		brandName.setText(item.get("CarName"));
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}
}