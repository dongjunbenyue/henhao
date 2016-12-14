package com.bcwcar.android.bencar.activity.fragment;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class bannerfragment4 extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 View rootView = inflater.inflate(R.layout.frangment4, container, false);// 关联布局文件
			
			BaseActivity.changeFonts((ViewGroup)rootView);
			return rootView;
	}

}
