package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Sminadop extends BaseAdapter{
	private static String[] ShopPics;
private Context context;
     public Sminadop(Context context,String[] ShopPics) {
		// TODO Auto-generated constructor stub
    	 this.context=context;
    	 this.ShopPics=ShopPics;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ShopPics.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ShopPics[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_image_shoe_item, parent,false);
		SimpleDraweeView image=(SimpleDraweeView)convertView.findViewById(R.id.shop_image001);
		ResourceUtil.setSimpleDraweeViewImage(image, ShopPics[position]);
		return convertView;
	}

}
