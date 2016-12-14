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

public class ServiceAdapter extends BaseAdapter{
	Context context;
	List<Map<String, String>> listdata;
    public ServiceAdapter(Context context,List<Map<String, String>> listdata) {
    	this.context=context;
    	this.listdata=listdata;
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		Map<String, String> item=listdata.get(position);
		if (convertView==null) {
			convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.servicelistchild, parent,false);
			holder=new ViewHolder();
			holder.timeText=(TextView)convertView.findViewById(R.id.time);
			holder.contentText=(TextView)convertView.findViewById(R.id.content);
			
			convertView.setTag(holder);
			
		} else {
    holder = (ViewHolder)convertView.getTag();
		}
		holder.timeText.setText(item.get("OperTime"));
		holder.contentText.setText(item.get("Description"));
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}
   public static  class ViewHolder  {
	   TextView timeText,contentText;
	   
	   
	
}
}
