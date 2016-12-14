package com.bcwcar.android.bencar.activity.MainMy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.CollectionUtil;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WeiZhangRestual extends BaseActivity{
private TextView no_weizhang,chepai;
private LinearLayout show_listview;
private ListView listview;
private SimpleAdapter adapter;
private List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initview();
		try {
			
			initdata(getIntent().getStringExtra("msg"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		adapter=new SimpleAdapter(getApplicationContext(), list, R.layout.weizhang_search_res_xml_item001, 
				new String[]{"price","score","time","address","content"}, 
				new int[]{R.id.TextView_weizhang_listview_item001,R.id.TextView_weizhang_listview_item002,R.id.TextView_weizhang_listview_item003,R.id.TextView_weizhang_listview_item004,R.id.TextView_weizhang_listview_item005});
		listview.setAdapter(adapter);
		
	}
	//实例化控件
	private void initview(){
		no_weizhang=(TextView)findViewById(R.id.textView_no_weizhang_tag001);
		chepai=(TextView)findViewById(R.id.textView_weizhang_chepai001);
		show_listview=(LinearLayout)findViewById(R.id.LinearLayout_jieguo_show001);
		listview=(ListView)findViewById(R.id.ListView_weizhang_search_res001);
	}
	public void initdata(String string){
		try {
			JSONObject object=new JSONObject(string);
			String status=object.getString("status").toString();
			String msg=object.getString("msg").toString();
			String result=object.getString("result").toString();
			if (result.equals("")) {
				showToast(msg);
				no_weizhang.setVisibility(View.VISIBLE);
				no_weizhang.setText(msg);
				show_listview.setVisibility(View.GONE);
			}else {
				no_weizhang.setVisibility(View.GONE);
				show_listview.setVisibility(View.VISIBLE);
				JSONObject object2=object.getJSONObject("result");
				chepai.setText(object2.getString("lsprefix")+object2.getString("lsnum"));
				JSONArray array=object2.getJSONArray("list");
				for (int i = 0; i < array.length(); i++) {
					JSONObject object3=array.getJSONObject(i);
					list.add(CollectionUtil.jsonObjectToMap(object3));
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
		rightView.setVisibility(View.GONE);
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("查询结果");
		changeFonts(titleParentView);
	}
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.weizhang_restual_xml, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}

	

	
	
}
