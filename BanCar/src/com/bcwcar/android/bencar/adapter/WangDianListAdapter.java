package com.bcwcar.android.bencar.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baidu.mapapi.map.Text;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.oned.rss.FinderPattern;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.FillInOrderActivity;
import com.bcwcar.android.bencar.activity.GongXiangShuoMing;
import com.bcwcar.android.bencar.activity.SelectShop4S;
import com.bcwcar.android.bencar.activity.WangDianDatas;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.util.StringUtil;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.jpush.a.a.c;
import io.rong.imkit.widget.provider.PrivateConversationProvider;

public class WangDianListAdapter extends BaseAdapter {
	private List<Map<String, String>> list;
	private Context context;
	private Activity activity;
	 public WangDianListAdapter(Context context,List<Map<String, String>> list,Activity activity) {
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
		RelativeLayout  item=(RelativeLayout)convertView.findViewById(R.id.RelativeLayout_item001);
		item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Map<String, String> map=list.get(position);
				for(String key:map.keySet()){
					Log.e("map遍历", "key= "+ key + " and value= " + map.get(key));
					DingDanDataSave.save(context, key, map.get(key));
				}
				DingDanDataSave.save(context, "ShopData", "1");
				DingDanDataSave.save(context, "OrderDiscountProPrice", DingDanDataSave.get(context, "DiscountPrice"));
				DingDanDataSave.save(context, "XianShiFlag", "1");
				activity.finish();
			}
		});
		LinearLayout list_wangdian_data=(LinearLayout)convertView.findViewById(R.id.LinearLayout_wangdian_list001);
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
		changshang.setText(list.get(position).get("BrandName").toString());
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

}
