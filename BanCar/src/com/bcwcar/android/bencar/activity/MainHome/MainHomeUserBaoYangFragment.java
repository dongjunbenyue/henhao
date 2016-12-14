package com.bcwcar.android.bencar.activity.MainHome;

import java.util.List;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.facebook.drawee.view.SimpleDraweeView;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainHomeUserBaoYangFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View convertView = inflater.inflate(R.layout.baoyang_huandong_item_xml, container, false);// 关联布局文件
		initViews(convertView);
		return convertView;
	}
	public static MainHomeUserBaoYangFragment newInstance(Bundle args) {
		MainHomeUserBaoYangFragment f = new MainHomeUserBaoYangFragment();
		f.setArguments(args);
		return f;
	}
	private void initViews(View convertView){
		Bundle bundle = getArguments();
		TextView km=(TextView)convertView.findViewById(R.id.TextView_baoyangmoving_km_number001);
		TextView price=(TextView)convertView.findViewById(R.id.TextView_baoyangmoving_price001);
		TextView back_pricce=(TextView)convertView.findViewById(R.id.TextView_baoyangmoving_price_to_back001);
		ImageView aicar=(ImageView)convertView.findViewById(R.id.ImageView_aicar_get001);
		km.setText(bundle.get("km").toString()+"");
		price.setText(bundle.get("money").toString()+"");
		back_pricce.setText(bundle.get("back").toString()+"");
		
		if (bundle.get("postion").toString().equals("0")) {
			km.setText(getActivity().getString(R.string.text_get_aicar));
			aicar.setVisibility(View.VISIBLE);
			back_pricce.setVisibility(View.GONE);
			price.setVisibility(View.GONE);
		}else {
			aicar.setVisibility(View.GONE);
			back_pricce.setVisibility(View.VISIBLE);
			price.setVisibility(View.VISIBLE);
		}
		
	}
}
