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

public class HotingCarShopAdapter extends BaseAdapter {

	private List<Map<String, String>> list_hoting;
	private Context context;
	private Activity activity;
	public HotingCarShopAdapter(Context context,List<Map<String, String>> list_hoting,Activity activity) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list_hoting=list_hoting;
		this.activity=activity;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_hoting.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list_hoting.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wangdianlist, parent, false);
			BaseActivity.changeFonts((ViewGroup)convertView);
		}
		LinearLayout have_quan=(LinearLayout)convertView.findViewById(R.id.LinearLayout_have_quan001);
		LinearLayout no_quan=(LinearLayout)convertView.findViewById(R.id.LinearLayout_no_quan001);
		if (list_hoting.get(position).get("TicketFlag").toString().equals("0")||StringUtil.isEmpty(list_hoting.get(position).get("TicketFlag").toString())) {
			have_quan.setVisibility(View.GONE);
			no_quan.setVisibility(View.VISIBLE);
		}else {
			have_quan.setVisibility(View.VISIBLE);
			no_quan.setVisibility(View.GONE);
		}
		System.out.println("shuju"+list_hoting.get(position).toString());
		System.out.println("ticketflag"+list_hoting.get(position).get("TicketFlag").toString()+"*****************");
		TextView textView001=(TextView)convertView.findViewById(R.id.TextView_shuoming001);
		TextView textView002=(TextView)convertView.findViewById(R.id.TextView_shuoming002);
		textView001.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DingDanDataSave.save(context, "ShopId", list_hoting.get(position).get("ShopId").toString());
				Bundle bundle=new Bundle();
				bundle.putString(BizDefineAll.BIZ_ACTION, "MainHomeActivity");
				PageUtil.jumpTo(activity, WangDianDatas.class,bundle);
			}
		});
		LinearLayout list_wangdian_data=(LinearLayout)convertView.findViewById(R.id.LinearLayout_wangdian_list001);
		list_wangdian_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DingDanDataSave.save(context, "ShopId", list_hoting.get(position).get("ShopId").toString());
				Bundle bundle=new Bundle();
				bundle.putString(BizDefineAll.BIZ_ACTION, "MainHomeActivity");
				PageUtil.jumpTo(activity, WangDianDatas.class,bundle);
			}
		});
		RatingBar ratingBar=(RatingBar)convertView.findViewById(R.id.ratingBar_shopstar001);
		ratingBar.setRating(Integer.parseInt(list_hoting.get(position).get("Score").toString()));
		TextView yuding=(TextView)convertView.findViewById(R.id.wangdian_yuding001);
		yuding.setVisibility(View.GONE);
		SimpleDraweeView logo=(SimpleDraweeView)convertView.findViewById(R.id.wangdian_logo001);
		TextView name=(TextView)convertView.findViewById(R.id.wangdian_name001);
		TextView adrress=(TextView)convertView.findViewById(R.id.wangdian_adrress001);
		TextView km=(TextView)convertView.findViewById(R.id.TextView_km_number001);
		TextView changshang=(TextView)convertView.findViewById(R.id.wangdian_changshang001);
		ResourceUtil.setSimpleDraweeViewImage(logo, list_hoting.get(position).get("LogoUrl").toString());
		name.setText(list_hoting.get(position).get("ShopName").toString());
		changshang.setText(list_hoting.get(position).get("ProviderNames").toString());
		adrress.setText(list_hoting.get(position).get("Address").toString());
		km.setText(new Distance().getDistance(LocationDataSave.get(context, "Longitude"), LocationDataSave.get(context, "Latitude"), list_hoting.get(position).get("Longitude").toString(), list_hoting.get(position).get("Latitude").toString())+"");
		
			if(list_hoting.get(position).containsKey("TicketDesc")){				
				if (!StringUtil.isEmpty(list_hoting.get(position).get("TicketDesc").toString())) {
					textView001.setText(list_hoting.get(position).get("TicketDesc").toString());
					textView002.setText(list_hoting.get(position).get("TicketDesc").toString());
				}
			};
		
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

}
