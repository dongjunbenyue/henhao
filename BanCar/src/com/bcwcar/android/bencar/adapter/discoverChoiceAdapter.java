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
import android.widget.ImageView;
import android.widget.TextView;

public class discoverChoiceAdapter  extends BaseAdapter{
	private List<Map<String, String>> list;
	private Context context;
 public discoverChoiceAdapter(Context context,List<Map<String, String>> list) {
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
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.choice_item_xml, parent, false);
		}
			TextView name=(TextView)convertView.findViewById(R.id.textView_choice_name001);
			ImageView gg=(ImageView)convertView.findViewById(R.id.imageView_gougou);
			
			name.setText(list.get(position).get("DistrictName").toString());
	if (list.get(position).get("zhizhen").equals("1")) {
		gg.setVisibility(View.VISIBLE);
		name.setTextColor(context.getResources().getColor(R.color.mainblue));
	}else {
		gg.setVisibility(View.GONE);
		name.setTextColor(context.getResources().getColor(R.color.inputblack));
	}
			
			BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

}
