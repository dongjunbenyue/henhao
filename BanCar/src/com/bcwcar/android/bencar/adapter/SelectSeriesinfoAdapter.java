package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.StringUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 选择车系list的适配器
 */
public class SelectSeriesinfoAdapter extends BaseAdapter {
	private List<Map<String, String>> listViewdata;
	public SelectSeriesinfoAdapter(List<Map<String, String>> listViewData) {
		super();
		this.listViewdata = listViewData;
	}

	@Override
	public int getCount() {
		return listViewdata.size();
	}

	@Override
	public Object getItem(int position) {
		return listViewdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public boolean isEnabled(int position) {
		Map<String, String> item = listViewdata.get(position);
		if(!StringUtil.isEmpty(item.get("TagTitle"))) {  //是Tag
			return false;
		}
		
		return super.isEnabled(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		Map<String, String> item = listViewdata.get(position);
		boolean isTag = !StringUtil.isEmpty(item.get("TagTitle"));
		if(isTag) {
    		convertView = layoutInflater.inflate(R.layout.list_item_select_brand_tag, parent, false);
    		TextView tagTitle = (TextView) convertView.findViewById(R.id.select_brand_tag_title);
    		tagTitle.setText(item.get("TagTitle"));
    	} else {
    		convertView = layoutInflater.inflate(R.layout.list_item_select_brand, parent, false);
    		SimpleDraweeView logo = (SimpleDraweeView) convertView.findViewById(R.id.select_brand_logo);
    		logo.setVisibility(View.GONE);
    		TextView brandName = (TextView) convertView.findViewById(R.id.select_brand_name);
    		brandName.setText(item.get("SeriesName"));
    	}
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}
}