package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.SmartMaintenanceActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SuggestProjectAdapter2 extends BaseAdapter {
private Context context;
private List<Map<String, String>> list;
 public SuggestProjectAdapter2(Context context,List<Map<String, String>> list) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.suggestproject_item_xml, parent, false);
		}
		LinearLayout  ll=(LinearLayout)convertView.findViewById(R.id.LinearLayout_zong_dd001);
		SimpleDraweeView logo=(SimpleDraweeView)convertView.findViewById(R.id.select_baoyang_image001);
		TextView name = (TextView) convertView.findViewById(R.id.TextView_suggestion_project_name001);
		TextView binfit = (TextView) convertView.findViewById(R.id.TextView_suggestion_project_binfit001);
		TextView price = (TextView) convertView.findViewById(R.id.TextView_suggestion_project_price001);
		TextView timeprice=(TextView)convertView.findViewById(R.id.TextView_suggestion_project_price002);
		final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox_project_check);
		
	ll.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					if (checkBox.isChecked()) {
//					checkBox.setChecked(false);	
//					}else {
//						checkBox.setChecked(true);	
//					}
				}
			});
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					
					if (isChecked) {
						SmartMaintenanceActivity.add(list.get(position));
					} else {
						SmartMaintenanceActivity.delete(list.get(position).get("ProjectId").toString());
					}
				}
			});
			logo.setImageURI(ResourceUtil.getImageUriFromAssetsbaoyang(list.get(position).get("IconUrl").toString()));
			name.setText(list.get(position).get("ProjectName").toString());
			binfit.setText(list.get(position).get("ProBenefit").toString());
			price.setText("￥"+list.get(position).get("SalePrice_After").toString());
		    timeprice.setText("工时费￥"+list.get(position).get("TimePrice_After").toString());
		
		
		
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}
	
}