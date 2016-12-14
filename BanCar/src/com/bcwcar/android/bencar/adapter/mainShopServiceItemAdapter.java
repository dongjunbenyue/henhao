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
public class mainShopServiceItemAdapter extends BaseAdapter {
	private List<Map<String, String>> list;
	private Context context;
	 public mainShopServiceItemAdapter(Context context,List<Map<String, String>> list) {
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
		System.out.println(list.get(position).toString());
		SimpleDraweeView logo=(SimpleDraweeView)convertView.findViewById(R.id.SimpleDraweeView_logo001);
		TextView name=(TextView)convertView.findViewById(R.id.TextView_discovery_project_name001);
		TextView price=(TextView)convertView.findViewById(R.id.TextView_ticket_price001);
		TextView total_price=(TextView)convertView.findViewById(R.id.TextView_ticket_nomarl_price001);
		TextView saled=(TextView)convertView.findViewById(R.id.TextView_ticket_saled001);
		name.setText(list.get(position).get("AccName").toString());
		price.setText("￥"+list.get(position).get("ActualPrice").toString());
		total_price.setText("￥"+list.get(position).get("TotalPrice").toString());
		saled.setText("已售"+list.get(position).get("ApplyNum").toString());
		String ticketflag=list.get(position).get("TicketFlag").toString();
		System.out.println("ticketflag******"+list.get(position).get("TicketFlag").toString());
		if(ticketflag.equals("1")){
		logo.setImageURI(ResourceUtil.getImageUriFromAssetsfuwu("banjinpenqi.png"))	;
		}else if (ticketflag.equals("2")) {
			logo.setImageURI(ResourceUtil.getImageUriFromAssetsfuwu("dujindumo.png"))	;
		}else if (ticketflag.equals("3")) {
			logo.setImageURI(ResourceUtil.getImageUriFromAssetsfuwu("shineiqingjie.png"));
		}else if (ticketflag.equals("4")) {
			logo.setImageURI(ResourceUtil.getImageUriFromAssetsfuwu("qichetiemo.png"));
		}else if (ticketflag.equals("5")) {
			logo.setImageURI(ResourceUtil.getImageUriFromAssetsfuwu("paoguangdala.png"));
		}else if (ticketflag.equals("6")) {
			logo.setImageURI(ResourceUtil.getImageUriFromAssetsfuwu("qichejingpin.png"));
		}else if (ticketflag.equals("7")) {
			logo.setImageURI(ResourceUtil.getImageUriFromAssetsfuwu("diyongquan.png"));
		}else {
			logo.setImageURI(ResourceUtil.getImageUriFromAssetsfuwu("qita.png"));
		}
		
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

}