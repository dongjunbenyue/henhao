package com.bcwcar.android.bencar.activity.MainDiscovery;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainHome.AdActivity;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.util.StringUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainDiscoveryGuangGaoFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.home_gianggao_xml3, container, false);// 关联布局文件
		initViews(rootView);
		return rootView;
	}
	public static MainDiscoveryGuangGaoFragment newInstance(Bundle args) {
		MainDiscoveryGuangGaoFragment f = new MainDiscoveryGuangGaoFragment();
		f.setArguments(args);
		return f;
	}
	private void initViews(View convertView){
		final Bundle bundle = getArguments();
		SimpleDraweeView image=(SimpleDraweeView)convertView.findViewById(R.id.SimpleDraweeView_mainhome_car_show001);
		image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!StringUtil.isEmpty(bundle.getString("PageUrl"))) {
					
					/*Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(bundle.getString("PageUrl")));*/
					Intent it = new Intent(getActivity(),AdActivity.class);   
					it.putExtra("PageUrl", bundle.getString("PageUrl"));
					getActivity().startActivity(it);

				}
			}
		});
		ResourceUtil.setSimpleDraweeViewImage(image, bundle.getString("PicUrl"));
	}
}
