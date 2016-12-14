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
import android.widget.CheckBox;
import android.widget.TextView;

public class JiYouAdapterZhanShi extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>> list;
	private String check;
 public JiYouAdapterZhanShi(Context context,List<Map<String, Object>> list,String check) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=list;
		this.check=check;
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
		
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyou_zhanshi, parent, false);
			TextView jiyou_price=(TextView)convertView.findViewById(R.id.jiyou_bancar_price001);
		if (position==0) {
			TextView textView=(TextView)convertView.findViewById(R.id.mantenceexchange);
			textView.setVisibility(View.VISIBLE);
			jiyou_price.setVisibility(View.GONE);
		}else {
			jiyou_price.setText("+"+String.valueOf(Integer.parseInt((String) list.get(position).get("BenPrice"))-Integer.parseInt((String) list.get(0).get("BenPrice"))));
		}
		TextView xinghao=(TextView)convertView.findViewById(R.id.jiyou_xinghao);
		CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.jiyou_xuanze001);
		checkBox.setEnabled(false);
	
		xinghao.setText(list.get(position).get("Accessory").toString());
		
		if (check.equals(list.get(position).get("AccId").toString())) {
			checkBox.setChecked(true);
		}else {
			checkBox.setChecked(false);
		}
		BaseActivity.changeFonts((ViewGroup)convertView);
		
		return convertView;
	}

}
