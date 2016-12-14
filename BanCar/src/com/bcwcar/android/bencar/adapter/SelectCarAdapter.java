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
public class SelectCarAdapter extends BaseAdapter {
	private List<Map<String, String>> listViewdata;
	private Context context;
	public SelectCarAdapter(Context context,List<Map<String, String>> listViewData) {
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
			convertView = layoutInflater.inflate(R.layout.list_item_select_car, parent, false);
		}
		SimpleDraweeView logo = (SimpleDraweeView) convertView.findViewById(R.id.list_item_select_car_logo);
		String brandLogo = item.get("BrandLogo");
		String firstLetter = brandLogo.substring(0, 1).toUpperCase(Locale.US);
		Log.i("", "firstLetter = " + firstLetter);
		String logoFullUrl = "asset:///" + firstLetter + "/" + item.get("BrandLogo");
		logo.setImageURI(Uri.parse(logoFullUrl));
		TextView isDefault = (TextView) convertView.findViewById(R.id.list_item_select_car_is_default);
		CheckBox isChecked = (CheckBox) convertView.findViewById(R.id.list_item_select_car_checked);
		if(item.get("DefaultFlag").equals("1")) {
			isDefault.setVisibility(View.VISIBLE);
			isChecked.setChecked(true);
		}else {
			isDefault.setVisibility(View.GONE);
			isChecked.setChecked(false);
		}
		TextView carName = (TextView) convertView.findViewById(R.id.list_item_select_car_CarName);
		carName.setText(item.get("CarName"));
		TextView carMiles = (TextView) convertView.findViewById(R.id.list_item_select_car_CarMiles);
		carMiles.setText("行驶里程"+item.get("CarMiles")+"公里");
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}
}