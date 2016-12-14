package com.bcwcar.android.bencar.base;

import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
	protected Context context;
	private List<T> itemList;
	
	public Context getContext() {
		return context;
	}

	public MyBaseAdapter(Context context){
		this.context = context;
	}
	
	public List<T> getItemList() {
		return itemList;
	}
	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}

	@Override
	public int getCount() {
		if(itemList != null && itemList.size() > 0)
			return itemList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
