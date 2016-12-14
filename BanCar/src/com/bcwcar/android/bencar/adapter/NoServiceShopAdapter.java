package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.activity.StoreServiceNetworkActivity;
import com.bcwcar.android.bencar.activity.WangDianDatas;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.StoreToDataSave;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
/**
 * 服务网点list的适配器
 */
public class NoServiceShopAdapter extends BaseAdapter {
	private List<Map<String, String>> list;
	private Context context;
	private Activity activity;
	 public NoServiceShopAdapter(Context context,List<Map<String, String>> list,Activity activity) {
		// TODO Auto-generated constructor stub
		 this.context=context;
		 this.list=list;
		 this.activity=activity;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.wangdianlist2, parent, false);
		}
		LinearLayout list_wangdian_data=(LinearLayout)convertView.findViewById(R.id.LinearLayout_wangdian_list001);
		list_wangdian_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (String key:list.get(position).keySet()) {
					StoreToDataSave.save(context, key, list.get(position).get(key));
				}
				MainActivity.setvar(1);
				activity.finish();
			}
		});
		RatingBar ratingBar=(RatingBar)convertView.findViewById(R.id.ratingBar_shopstar001);
		ratingBar.setRating(Integer.parseInt(list.get(position).get("Score").toString()));
		SimpleDraweeView logo=(SimpleDraweeView)convertView.findViewById(R.id.wangdian_logo001);
		TextView name=(TextView)convertView.findViewById(R.id.wangdian_name001);
		TextView adrress=(TextView)convertView.findViewById(R.id.wangdian_adrress001);
		TextView km=(TextView)convertView.findViewById(R.id.TextView_km_number001);
		TextView changshang=(TextView)convertView.findViewById(R.id.wangdian_changshang001);
		ResourceUtil.setSimpleDraweeViewImage(logo, list.get(position).get("LogoUrl").toString());
		name.setText(list.get(position).get("ShopName").toString());
		adrress.setText(list.get(position).get("Address").toString());
		km.setText(list.get(position).get("Distance").toString());
		changshang.setText(list.get(position).get("ProviderNames").toString());
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

}