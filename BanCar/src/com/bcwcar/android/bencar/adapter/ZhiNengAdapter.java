package com.bcwcar.android.bencar.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.SmartMaintenanceActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.datasave.OnlyOneDataSave;
import com.bcwcar.android.bencar.util.CollectionUtil;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ZhiNengAdapter extends BaseAdapter {
	private List<Map<String, String>> list;
	private Context context;
	public ZhiNengAdapter(Context context, List<Map<String, String>> list ) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
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
			if (convertView==null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bixuan_project_item_xml, parent,false);
			}
		LinearLayout fulei = (LinearLayout) convertView.findViewById(R.id.layout);
		TextView name = (TextView) convertView.findViewById(R.id.TextView_item_project_name001);
		TextView price = (TextView) convertView.findViewById(R.id.TextView_item_project_price001);
		HorizontalScrollView hh=(HorizontalScrollView)convertView.findViewById(R.id.HorizontalScrollView_baoyang001);
		final GridView mGridView=(GridView)convertView.findViewById(R.id.GridView_jiyou_bixuan001);
		hh.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction()==MotionEvent.ACTION_UP) {
					mGridView.requestFocus();
				}
				return false;
			}
		});
		if (list.get(position).get("ProjectOrder").toString().equals("1")) {
			hh.setVisibility(View.VISIBLE);
			try {
					List<Map<String, String>> list_jiyou_item= new ArrayList<Map<String, String>>();
					JSONArray	array = new JSONArray(list.get(position).get("Projects").toString());
					for (int j = 0; j < array.length(); j++) {
					JSONObject object2=array.getJSONObject(j);
					list_jiyou_item.add(CollectionUtil.jsonObjectToMap_String(object2));
					}
					setGridView(mGridView, list_jiyou_item);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
				hh.setVisibility(View.GONE);
		}
		name.setText(list.get(position).get("ProjectName").toString());
		price.setText("￥  "+list.get(position).get("SalePrice_After").toString());
		
		
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
	}
	 /**设置GirdView参数，绑定数据*/
    private void setGridView(final GridView mgridview,final List<Map<String, String>> list_gridview) {
        int size = list_gridview.size();
        int length = (BaseActivity.get_windows_width(context)/5)*2;
        int gridviewWidth = (int) (size * (length) );
        int itemWidth = (int) (length);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        mgridview.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        mgridview.setColumnWidth(itemWidth); // 设置列表项宽
        mgridview.setHorizontalSpacing(0); // 设置列表项水平间距
        mgridview.setStretchMode(GridView.NO_STRETCH);
        mgridview.setNumColumns(size); // 设置列数量=列表集合数
        JiYouShowAdapter adapter=new JiYouShowAdapter(context, list_gridview);
		mgridview.setAdapter(adapter);
		mgridview.requestFocus();
		mgridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				OnlyOneDataSave.save(context, "jiyouzhizhen", ""+position);
				SmartMaintenanceActivity.jiyou_refsh(list_gridview.get(position),position);
			}
		});
		
		
	
    }
    //机油切换
    public void jiyou_change(GridView mgridview,String postion){
    	
    	for (int i = 0; i < mgridview.getChildCount(); i++) {
			View convertView = mgridview.getChildAt(i);
			RelativeLayout fulei=(RelativeLayout)convertView.findViewById(R.id.RelativeLayout_jiyou_show001);
			ImageView gou=(ImageView)convertView.findViewById(R.id.ImageView_xuangzhong_state001);
			fulei.setBackgroundResource(R.drawable.yuanjiao_biankuang_hui);
			gou.setVisibility(View.GONE);
		}
    	View view = mgridview.getChildAt(Integer.parseInt(postion));
		RelativeLayout fulei=(RelativeLayout)view.findViewById(R.id.RelativeLayout_jiyou_show001);
		ImageView gou=(ImageView)view.findViewById(R.id.ImageView_xuangzhong_state001);
		fulei.setBackgroundResource(R.drawable.yuanjiao_biankuang_lankuang_bai);
		gou.setVisibility(View.VISIBLE);
    }
}
