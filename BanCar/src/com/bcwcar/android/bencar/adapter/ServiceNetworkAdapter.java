package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.WangDianDatas;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.util.StringUtil;
import com.bcwcar.android.bencar.widget.Distance;
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
public class ServiceNetworkAdapter extends BaseAdapter {
	private List<Map<String, String>> list;
	private Context context;
	private Activity activity;
	 public ServiceNetworkAdapter(Context context,List<Map<String, String>> list,Activity activity) {
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
					.inflate(R.layout.wangdianlist, parent, false);
		}
		LinearLayout have_quan=(LinearLayout)convertView.findViewById(R.id.LinearLayout_have_quan001);
		LinearLayout no_quan=(LinearLayout)convertView.findViewById(R.id.LinearLayout_no_quan001);
		if (list.get(position).get("TicketFlag").toString().equals("0")||StringUtil.isEmpty(list.get(position).get("TicketFlag").toString())) {
			have_quan.setVisibility(View.GONE);
			no_quan.setVisibility(View.VISIBLE);
		}else {
			have_quan.setVisibility(View.VISIBLE);
			no_quan.setVisibility(View.GONE);
		}
		
		TextView textView001=(TextView)convertView.findViewById(R.id.TextView_shuoming001);
		TextView textView002=(TextView)convertView.findViewById(R.id.TextView_shuoming002);
		textView001.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DingDanDataSave.save(context, "ShopId", list.get(position).get("ShopId").toString());
				Bundle bundle=new Bundle();
				bundle.putString(BizDefineAll.BIZ_ACTION, "MainServiceNetworkActivity");
				PageUtil.jumpTo(activity, WangDianDatas.class,bundle);
			}
		});
		LinearLayout list_wangdian_data=(LinearLayout)convertView.findViewById(R.id.LinearLayout_wangdian_list001);
		list_wangdian_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DingDanDataSave.save(context, "ShopId", list.get(position).get("ShopId").toString());
				Bundle bundle=new Bundle();
				bundle.putString(BizDefineAll.BIZ_ACTION, "MainServiceNetworkActivity");
				PageUtil.jumpTo(activity, WangDianDatas.class,bundle);
			}
		});
		RatingBar ratingBar=(RatingBar)convertView.findViewById(R.id.ratingBar_shopstar001);
		ratingBar.setRating(Integer.parseInt(list.get(position).get("Score").toString()));
		TextView yuding=(TextView)convertView.findViewById(R.id.wangdian_yuding001);
		yuding.setVisibility(View.GONE);
		SimpleDraweeView logo=(SimpleDraweeView)convertView.findViewById(R.id.wangdian_logo001);
		TextView name=(TextView)convertView.findViewById(R.id.wangdian_name001);
		TextView adrress=(TextView)convertView.findViewById(R.id.wangdian_adrress001);
		TextView km=(TextView)convertView.findViewById(R.id.TextView_km_number001);
		TextView changshang=(TextView)convertView.findViewById(R.id.wangdian_changshang001);
		ResourceUtil.setSimpleDraweeViewImage(logo, list.get(position).get("LogoUrl").toString());
		name.setText(list.get(position).get("ShopName").toString());
		adrress.setText(list.get(position).get("Address").toString());
		km.setText(new Distance().getDistance(LocationDataSave.get(context, "Longitude"), LocationDataSave.get(context, "Latitude"), list.get(position).get("Longitude").toString(), list.get(position).get("Latitude").toString()));
		changshang.setText(list.get(position).get("ProviderNames").toString());
		if(list.get(position).containsKey("TicketDesc")){				
			if (!StringUtil.isEmpty(list.get(position).get("TicketDesc").toString())) {
				textView001.setText(list.get(position).get("TicketDesc").toString());
				textView002.setText(list.get(position).get("TicketDesc").toString());
			}
		};
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

}