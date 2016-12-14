package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.baidu.mapapi.map.Text;
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
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
/**
 * 服务网点list的适配器
 */
public class mainShopServiceItemAdapter002 extends BaseAdapter {
	private List<Map<String, String>> list;
	private Context context;
	 public mainShopServiceItemAdapter002(Context context,List<Map<String, String>> list) {
		// TODO Auto-generated constructor stub
		 this.context=context;
		 this.list=list;
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
					.inflate(R.layout.wangdianlistdiscoveryitem, parent, false);
		}
		SimpleDraweeView logo=(SimpleDraweeView)convertView.findViewById(R.id.SimpleDraweeView_logo001);
		TextView name=(TextView)convertView.findViewById(R.id.TextView_discovery_project_name001);
		TextView price=(TextView)convertView.findViewById(R.id.TextView_ticket_price001);
		TextView total_price=(TextView)convertView.findViewById(R.id.TextView_ticket_nomarl_price001);
		TextView saled=(TextView)convertView.findViewById(R.id.TextView_ticket_saled001);
		name.setText(list.get(position).get("AccName").toString());
		price.setText("￥"+list.get(position).get("ActualPrice").toString());
		total_price.setText("￥"+list.get(position).get("TotalPrice").toString());
		saled.setText("已售"+list.get(position).get("ApplyNum").toString());
		ResourceUtil.setSimpleDraweeViewImage(logo, list.get(position).get("IconUrl").toString());
		BaseActivity.changeFonts(parent);
		return convertView;
	}

}