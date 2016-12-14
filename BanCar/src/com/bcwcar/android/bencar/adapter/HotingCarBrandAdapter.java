package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HotingCarBrandAdapter extends BaseAdapter {

	private List<Map<String, String>> list_hoting;
	private Context context;
	public HotingCarBrandAdapter(Context context,List<Map<String, String>> list_hoting) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list_hoting=list_hoting;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_hoting.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list_hoting.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hoting_car_item_xml, parent, false);
			BaseActivity.changeFonts((ViewGroup)convertView);
		}
		SimpleDraweeView logo = (SimpleDraweeView) convertView.findViewById(R.id.SimpleDraweeView_hoting_brand_logo);
		String logoFullUrl = "asset:///" + list_hoting.get(position).get("First") + "/" + list_hoting.get(position).get("IconUrl");
		logo.setImageURI(Uri.parse(logoFullUrl));
		TextView brandName = (TextView) convertView.findViewById(R.id.TextView_hoting_brand_name);
		brandName.setText(list_hoting.get(position).get("Brand").toString());
		return convertView;
	}

}
