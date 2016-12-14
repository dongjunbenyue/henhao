package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.alipay.android.phone.mrpc.core.r;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RankAdapter extends BaseAdapter {
    List<Map<String, String>> listdata;
    Context context;
	public RankAdapter( List<Map<String, String>> listdata,
    Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.listdata=listdata;
		System.out.println("********-********");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
			convertView=LayoutInflater.from(context).inflate(R.layout.list_item_ranklist, parent,false);
			
	
		Map<String, String> item=listdata.get(position);
		SimpleDraweeView simpleDraweeView=(SimpleDraweeView)convertView.findViewById(R.id.paimingtouxiang);
		TextView textView1=(TextView)convertView.findViewById(R.id.rankTextview);
		TextView textView2=(TextView)convertView.findViewById(R.id.Rankname) ;
		TextView textView3=(TextView)convertView.findViewById(R.id.invitenum);
		ImageView imageView=(ImageView)convertView.findViewById(R.id.rank); 
		
		if (position==0) {
			System.out.println("///////////////+///////////////");
			imageView.setBackgroundResource(R.drawable.icon_diyi);
			textView1.setVisibility(View.GONE);
			
		} else if (position==1){
			imageView.setBackgroundResource(R.drawable.icon_dier);
			textView1.setVisibility(View.GONE);
		}else if (position==2) {
			textView1.setVisibility(View.GONE);
			imageView.setBackgroundResource(R.drawable.icon_disan);
		}else if (position>2)
			
		{
			textView1.setText(String.valueOf(position+1));
			imageView.setVisibility(View.GONE);
		}
		ResourceUtil.setSimpleDraweeViewImage(simpleDraweeView, item.get("IconUrl"));
		textView2.setText(item.get("UserName"));
		textView3.setText(item.get("InvitedCount"));
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

}
