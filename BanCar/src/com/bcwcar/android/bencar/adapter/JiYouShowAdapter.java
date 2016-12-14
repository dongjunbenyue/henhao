package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import com.alipay.sdk.app.p;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.datasave.OnlyOneDataSave;
import com.bcwcar.android.bencar.util.StringUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JiYouShowAdapter extends BaseAdapter {

	private List<Map<String, String>> list_gridview;
	private Context context;

	public JiYouShowAdapter(Context context, List<Map<String, String>> list_gridview) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list_gridview = list_gridview;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_gridview.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list_gridview.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jiyou_item_gridview_xml, parent,
					false);
			BaseActivity.changeFonts((ViewGroup) convertView);
		}
		RelativeLayout fulei = (RelativeLayout) convertView.findViewById(R.id.RelativeLayout_jiyou_show001);
		ImageView gou = (ImageView) convertView.findViewById(R.id.ImageView_xuangzhong_state001);
		TextView Accessory001 = (TextView) convertView.findViewById(R.id.textView_jiyou_item_miaosu001);
		TextView price = (TextView) convertView.findViewById(R.id.textView_jiyou_price_item001);
		TextView tag = (TextView) convertView.findViewById(R.id.textView_tst_remen_tag001);
		if (position == 0) {
			tag.setText("推荐");
		} else {
			tag.setText("精品");
		}
		if (!StringUtil.isEmpty(OnlyOneDataSave.get(context, "jiyouzhizhen"))) {
			if (position==Integer.parseInt(OnlyOneDataSave.get(context, "jiyouzhizhen"))) {
				fulei.setBackgroundResource(R.drawable.yuanjiao_biankuang_lankuang_bai);
				gou.setVisibility(View.VISIBLE);
			}
		}
		
		price.setText(list_gridview.get(position).get("SalePrice_After").toString());
		String Accessory = list_gridview.get(position).get("Accessory").toString();
		Accessory001.setText(Accessory);
		return convertView;
	}
}
