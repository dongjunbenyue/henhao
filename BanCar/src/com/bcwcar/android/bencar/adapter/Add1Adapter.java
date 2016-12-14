package com.bcwcar.android.bencar.adapter;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainMy.AddProjectMyOrder;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Add1Adapter extends BaseAdapter{
     List<Map<String, String>> listdata;
     
     Context context;
      int kk=0;
     
	public Add1Adapter(List<Map<String, String>> listdata, Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		
		this.listdata=listdata;
		
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_smart_maintenance_add_project, parent, false);
		}
		TextView name=(TextView)convertView.findViewById(R.id.add_project_title);
		TextView price=(TextView)convertView.findViewById(R.id.add_project_price);
		final CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.add_project_check);
		SimpleDraweeView logo=(SimpleDraweeView)convertView.findViewById(R.id.select_baoyang_image001);
		logo.setImageURI(ResourceUtil.getImageUriFromAssetsbaoyang(listdata.get(position).get("IconUrl").toString()));
		name.setText(listdata.get(position).get("ProjectName").toString());
		
		System.out.println(listdata.get(position).get("DiscountPrice").toString()+"***********");
		price.setText(listdata.get(position).get("DiscountPrice").toString());
		
	    checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if (isChecked) {
					kk++;
//					AddProjectMyOrder.add(kk);
				AddProjectMyOrder.add1(listdata.get(position));
					
				}else {	
					kk--;
//					AddProjectMyOrder.add(kk);
					AddProjectMyOrder.delete(kk);
					
					
				}
			}
		});
	    BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}
	

}
