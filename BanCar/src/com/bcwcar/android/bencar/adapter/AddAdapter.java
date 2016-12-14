package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.AddProjectActivity;
import com.bcwcar.android.bencar.activity.SmartMaintenanceActivity;
import com.bcwcar.android.bencar.base.BaseActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class AddAdapter extends BaseAdapter {
	private List<Map<String, Object>> listViewData;
	private Context context;
	private List<String> list_num;
	private int tt = 0;

	public AddAdapter(List<Map<String, Object>> listViewData, Context context, List<String> list_num) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listViewData = listViewData;
		this.list_num = list_num;
		tt = list_num.size();
		AddProjectActivity.add(tt);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listViewData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listViewData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.list_item_smart_maintenance_add_project, parent, false);
		}
		TextView name = (TextView) convertView.findViewById(R.id.add_project_title);
		TextView price = (TextView) convertView.findViewById(R.id.add_project_price);
		final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.add_project_check);
		name.setText(listViewData.get(position).get("ProjectName").toString());
		price.setText(listViewData.get(position).get("BenPrice").toString());
		if (listViewData.get(position).get("ProjectName").toString().equals("机油")) {
			checkBox.setChecked(true);
		}
	chek_show(position, checkBox);
		checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listViewData.get(position).get("ProjectOrder").toString().equals("1")||listViewData.get(position).get("ProjectOrder").toString().equals("2")) {
					Toast.makeText(context, "此项不能删除", Toast.LENGTH_SHORT).show();
					checkBox.setChecked(true);
				}
			}
		});
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub

				if (isChecked) {
					add_chek_show(position, checkBox);
					tt = tt + 1;
					AddProjectActivity.add(tt);
//					SmartMaintenanceActivity.add(listViewData.get(position));
				} else {
					delet_chek_show(position, checkBox);
					tt = tt - 1;
					AddProjectActivity.add(tt);
//					SmartMaintenanceActivity.delete(listViewData.get(position).get("ProjectId").toString());

				}
			}
		});
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

	
	
	public  void chek_show(final int postion,final CheckBox checkBox){
		for (int i = 0; i < list_num.size(); i++) {
			if (listViewData.get(postion).get("ProjectId").toString().equals(list_num.get(i).toString())) {
				checkBox.setChecked(true);
			}
		}
		
	}
	public  void add_chek_show(final int postion,final CheckBox checkBox){
		list_num.add(listViewData.get(postion).get("ProjectId").toString());
		for (int i = 0; i < list_num.size(); i++) {
			if (listViewData.get(postion).get("ProjectId").toString().equals(list_num.get(i).toString())) {
				checkBox.setChecked(true);
			}
		}
		
	}
	public  void delet_chek_show(final int postion,final CheckBox checkBox){
		for (int i = 0; i < list_num.size(); i++) {
			if (listViewData.get(postion).get("ProjectId").toString().equals(list_num.get(i).toString())) {
				list_num.remove(i);
				checkBox.setChecked(false);
			}
		}
		
	}
	
}
