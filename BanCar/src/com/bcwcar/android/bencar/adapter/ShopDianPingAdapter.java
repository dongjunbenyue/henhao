package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.SmartMaintenanceActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ShopDianPingAdapter extends BaseAdapter {
	private List<Map<String, String>> listViewData;
	private Context context;

	public ShopDianPingAdapter(List<Map<String, String>> listViewData, Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.listViewData = listViewData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listViewData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listViewData.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_dian_ping_item_xml, parent, false);
		}
		SimpleDraweeView head=(SimpleDraweeView)convertView.findViewById(R.id.SimpleDraweeView_shop_dianping_user_head001);
		TextView content=(TextView)convertView.findViewById(R.id.TextView_shop_content001);
		TextView name=(TextView)convertView.findViewById(R.id.TextView_pingluner_name001);
		TextView time=(TextView)convertView.findViewById(R.id.TextView_punling_time001);
		RatingBar xing=(RatingBar)convertView.findViewById(R.id.ratingBar_shoppinglun_info001);
		ResourceUtil.setSimpleDraweeViewImage(head, listViewData.get(position).get("IconUrl").toString());
		content.setText(listViewData.get(position).get("Comment").toString());
		time.setText(listViewData.get(position).get("ScoreTime").toString());
		if (listViewData.get(position).get("CommentFlag").toString().equals("1")) {
			
			name.setText(listViewData.get(position).get("NickName").toString());
		}else {
			name.setText("匿名");
		}
		
		xing.setRating(Float.parseFloat(listViewData.get(position).get("Score").toString()));
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}

	
	
}
