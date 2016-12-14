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
import android.widget.TextView;

public class BaoyanglistAdapter extends BaseAdapter{
	List<Map<String, String>> list;
	Context context;
    TextView  prjTextView,accTextView,moneyText;
	public BaoyanglistAdapter(List<Map<String, String>> list,Context context) {
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
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Map<String, String> item=list.get(position);
		convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_baoyang, parent,false);
		prjTextView=(TextView)convertView.findViewById(R.id.projectName);
		accTextView=(TextView)convertView.findViewById(R.id.Accessory);
		moneyText=(TextView)convertView.findViewById(R.id.qianshu);
		prjTextView.setText(item.get("ProjectName"));
		accTextView.setText(item.get("AccName"));
		moneyText.setText("￥"+item.get("SumPrice"));
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

}
