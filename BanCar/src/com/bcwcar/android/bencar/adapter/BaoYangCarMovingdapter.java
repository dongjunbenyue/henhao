package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.alipay.sdk.app.p;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaoYangCarMovingdapter extends BaseAdapter {

	private List<Map<String, String>> list_gridview;
	private Context context;
	public BaoYangCarMovingdapter(Context context,List<Map<String, String>> list_gridview) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list_gridview=list_gridview;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_gridview.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list_gridview.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.baoyang_huandong_item_xml, parent, false);
			BaseActivity.changeFonts((ViewGroup)convertView);
		}
		TextView km=(TextView)convertView.findViewById(R.id.TextView_baoyangmoving_km_number001);
		TextView price=(TextView)convertView.findViewById(R.id.TextView_baoyangmoving_price001);
		TextView back_pricce=(TextView)convertView.findViewById(R.id.TextView_baoyangmoving_price_to_back001);
		km.setText(list_gridview.get(position).get("name").toString());
		ImageView aicar=(ImageView)convertView.findViewById(R.id.ImageView_aicar_get001);
		if (position==0) {
			km.setText(context.getString(R.string.text_get_aicar));
			aicar.setVisibility(View.VISIBLE);
			back_pricce.setVisibility(View.GONE);
			price.setVisibility(View.GONE);
		}else {
			aicar.setVisibility(View.GONE);
			back_pricce.setVisibility(View.VISIBLE);
			price.setVisibility(View.VISIBLE);
		}
		
		
		
//		SimpleDraweeView logo = (SimpleDraweeView) convertView.findViewById(R.id.SimpleDraweeView_hoting_brand_logo);
////		String logoFullUrl = "asset:///" + item.get("First") + "/" + item.get("IconUrl");
//		String logoFullUrl = "asset:///A/" + list_gridview.get(position).get("icon");
//		logo.setImageURI(Uri.parse(logoFullUrl));
//		TextView brandName = (TextView) convertView.findViewById(R.id.TextView_hoting_brand_name);
//		brandName.setText(list_gridview.get(position).get("name"));
		return convertView;
	}

}
