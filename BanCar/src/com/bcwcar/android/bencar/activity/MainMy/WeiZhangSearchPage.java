package com.bcwcar.android.bencar.activity.MainMy;

import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.widget.AllCapTransformationMethod;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class WeiZhangSearchPage extends BaseActivity{

	private static TextView chepai_xuanze;
	private static TextView city_click;
	private EditText chepai_num,fadongji_num,chejia_num;
	private TextView chaxun;
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what==1) {
				Dialogcancel();
				Intent intent=new Intent(WeiZhangSearchPage.this,WeiZhangRestual.class);
				intent.putExtra("msg", msg.obj.toString());
				startActivity(intent);
			}
			
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initview();
	}
	
	
	public  static void addview_city(String cityname,String chepai){
		city_click.setText(cityname);
		chepai_xuanze.setText(chepai);
	}
	//实例化空间
	public void initview(){
		chaxun=(TextView)findViewById(R.id.TextView_chaxun001);
		chaxun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String string_chepai_num=chepai_num.getText().toString();
				final String string_fadongji_num=fadongji_num.getText().toString();
				final String string_chejia_num=chejia_num.getText().toString();
				final String string_city_num=city_click.getText().toString();
				if (!string_chepai_num.equals("")&&!string_fadongji_num.equals("")&&!string_chejia_num.equals("")&&!string_city_num.equals("")) {
					
					new Thread(new Runnable() {
						 
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Message message=new Message();
							message.what=1;
							message.obj=weizhang_search(1,"carorg="+get_weizhang_data("carorg")+"&lsprefix="+get_weizhang_data("lsprefix")+"&lsnum="+string_chepai_num+"&lstype=02&frameno="+string_chejia_num+"&engineno="+string_fadongji_num);
							handler.sendMessage(message);
						}
					}).start();
				}else {
					showToast("信息不完整");
				}
			}
		});
		chepai_num=(EditText)findViewById(R.id.EditText_weizhang_search_carnum001);
		chepai_num.setTransformationMethod(new AllCapTransformationMethod());
		fadongji_num=(EditText)findViewById(R.id.EditText_weizhang_search_fadongjinum001);
		chejia_num=(EditText)findViewById(R.id.EditText_weizhang_search_chejianum001);
		fadongji_num.setTransformationMethod(new AllCapTransformationMethod());
		chejia_num.setTransformationMethod(new AllCapTransformationMethod());
	
		city_click=(TextView)findViewById(R.id.TextView_weizhang_search_city001);
		city_click.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WeiZhangSearchPage.this, WeiZhangCityList.class);
				startActivity(intent);
			}
		});
		chepai_xuanze = (TextView) findViewById(R.id.TextView_dingdanye_carnum001);// 车牌选择
		
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
		centerView.setText("违章查询");
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.weizhang_search_xml, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}

	


}
