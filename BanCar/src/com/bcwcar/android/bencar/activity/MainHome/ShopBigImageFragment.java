package com.bcwcar.android.bencar.activity.MainHome;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShopBigImageFragment extends Fragment {
	SimpleDraweeView carimage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.home_user_car, container, false);// 关联布局文件
		initViews(rootView);
		return rootView;
	}
	public static ShopBigImageFragment newInstance(Bundle args) {
		ShopBigImageFragment f = new ShopBigImageFragment();
		f.setArguments(args);
		return f;
	}
	private void initViews(View rootView){
		Bundle bundle = getArguments();
		 carimage=(SimpleDraweeView)rootView.findViewById(R.id.SimpleDraweeView_mainhome_user_car_show001);
		 ResourceUtil.setSimpleDraweeViewImage(carimage, bundle.getString("url"));
	}
}
