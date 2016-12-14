package com.bcwcar.android.bencar.adapter;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BankListAdapter  extends BaseAdapter{
	private List<Map<String, String>> mListViewData;
	private Context context;
	public  BankListAdapter(Context context,List<Map<String, String>> mListViewData) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.mListViewData=mListViewData;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListViewData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListViewData.get(position);
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
			convertView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.bank_car_listview_item, parent, false);
		}
		ImageView logo=(ImageView)convertView.findViewById(R.id.imageView_bank_item_logo001);
		TextView name=(TextView)convertView.findViewById(R.id.TextView_bank_item_logo001);
		name.setText(mListViewData.get(position).get("BankName").toString());
		String logoFullUrl = "bank/" + mListViewData.get(position).get("BankIcon").toString();
		try {
			InputStream inputStream=context.getAssets().open(logoFullUrl);
			Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
			logo.setImageBitmap(bitmap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

}
