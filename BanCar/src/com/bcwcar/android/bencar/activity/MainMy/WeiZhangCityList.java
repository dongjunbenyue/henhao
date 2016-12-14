package com.bcwcar.android.bencar.activity.MainMy;

import java.util.ArrayList;
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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import cn.sharesdk.tencent.qq.h;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WeiZhangCityList extends BaseActivity {
	private TextView shenfen,city;
	private ListView list001,list002;
	private List<Map<String, Object>> data001=new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> data002=new ArrayList<Map<String, Object>>();
	private SimpleAdapter adapter001,adadpter002;
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what==1) {
				jiexi(msg.obj.toString());
				adapter001=new SimpleAdapter(getApplicationContext(), data001, R.layout.weizhang_chaxun_city_item001, new String[]{"province"}, new int[]{R.id.textView1});
				list001.setAdapter(adapter001);
			}
			
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Thread(new Runnable() {
			 
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message message=new Message();
				message.what=1;
				message.obj=weizhang_search(0,"");
				handler.sendMessage(message);
			}
		}).start();


		initview();
	}
	//实例化控件
	public void initview(){
		shenfen=(TextView)findViewById(R.id.TextView_weizhang_city_show001);
		city=(TextView)findViewById(R.id.TextView_weizhang_city_show002);
		list001=(ListView)findViewById(R.id.ListView_weizhang_city_show001);
		list002=(ListView)findViewById(R.id.ListView_weizhang_city_show002);
		list002.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				city.setText(data002.get(position).get("city").toString());
				city.setTextColor(getResources().getColor(R.color.mainblue));
				set_weizhang_data("lsprefix", data002.get(position).get("lsprefix").toString());
				set_weizhang_data("lsnum", data002.get(position).get("lsnum").toString());
				set_weizhang_data("city", data002.get(position).get("city").toString());
				set_weizhang_data("carorg", data002.get(position).get("carorg").toString());
				WeiZhangSearchPage.addview_city(get_weizhang_data("province")+"  "+get_weizhang_data("city"),get_weizhang_data("lsprefix"));
				finish();
			}
		});
		list001.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				try {
					data002.clear();
					set_weizhang_data("province", data001.get(position).get("province").toString());
					set_weizhang_data("lsprefix", data001.get(position).get("lsprefix").toString());
					set_weizhang_data("lsnum", data001.get(position).get("lsnum").toString());
					if (data001.get(position).containsKey("list")) {
						set_weizhang_data("carorg", data001.get(position).get("carorg").toString());
						String string=data001.get(position).get("list").toString();
						shenfen.setText(data001.get(position).get("province").toString());
						shenfen.setTextColor(getResources().getColor(R.color.mainblue));
						JSONArray array=new JSONArray(string);
						for (int i = 0; i < array.length(); i++) {
							JSONObject object2=array.getJSONObject(i);
							data002.add(CollectionUtil.jsonObjectToMap001(object2));
						}
						adadpter002=new SimpleAdapter(getApplicationContext(), data002, R.layout.weizhang_chaxun_city_item002, new String[]{"city"}, new int[]{R.id.textView1});
						list002.setAdapter(adadpter002);
					}else {
						set_weizhang_data("carorg", data001.get(position).get("carorg").toString());
						WeiZhangSearchPage.addview_city(get_weizhang_data("province"),get_weizhang_data("lsprefix"));
						finish();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
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
		centerView.setText("选择城市");
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.weizhang_city_ch001, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}

	


	
	//解析数据
	public void jiexi(String string){
		try {
			JSONObject object=new JSONObject(string);
			JSONObject jsonObject=object.getJSONObject("result");
			JSONArray array=jsonObject.getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject object2=array.getJSONObject(i);
				data001.add(CollectionUtil.jsonObjectToMap001(object2));
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}





