package com.bcwcar.android.bencar.adapter;

import java.util.List;

import com.bcwcar.android.bencar.base.BaseActivity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class CalendarAdapter extends PagerAdapter {
	private int mChildCount = 0;
	private List datasList;
	
	public CalendarAdapter(Context context, List datasList) {
		this.datasList = datasList;
	}
	
	@Override
	public int getCount() {
		if (datasList == null)
			return 0;
		return datasList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) (arg1));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = initPagerView(position);
		container.addView(view);
		BaseActivity.changeFonts((ViewGroup)view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public void notifyDataSetChanged() {
		mChildCount = getCount();
		super.notifyDataSetChanged();
	}

	@Override
	public int getItemPosition(Object object) {
		if (mChildCount > 0) {
			mChildCount--;
			return POSITION_NONE;
		}
		return super.getItemPosition(object);
	}
	public abstract View initPagerView(int position);
}
