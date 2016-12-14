package com.bcwcar.android.bencar.activity;

import org.json.JSONException;
import org.json.JSONObject;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.PageUtil;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FillSelectActivity extends BaseActivity{
      private TextView guize,chaxun,xianshi;
      String key1;
      private EditText edit11;
      Handler handler=new Handler(){
  		public void handleMessage(android.os.Message msg) {
  			if (msg.what==1) {
  				jiexi(msg.obj.toString());
  			}
  		};
  	};
	private View imageView;
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText(getString(R.string.text_code_kown_car));
		rightView.setVisibility(View.GONE);
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.body_select_renshicar, bodyParentView);
		guize=(TextView)findViewById(R.id.texttianma);
		edit11=(EditText)findViewById(R.id.edittianma);
		xianshi=(TextView)findViewById(R.id.textxianshi);
		guize.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(FillSelectActivity.this, VincodeActivity.class);
			}
		});
		imageView=(ImageView)findViewById(R.id.changjianwenticall);
    imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-018-5025"));
				//Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:400-018-5025"));
				FillSelectActivity.this.startActivity(intent);
			}
		});
		chaxun=(TextView)findViewById(R.id.textchaxun);
		chaxun.setOnClickListener(new OnClickListener() {
			
			private String ccaa;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("5555555");
				key1=edit11.getText().toString();
				System.out.println("5555555");
				System.out.println(key1);
//				HttpUserCar.getVin(new CallbackLogic003() {
//					
//					private String oooo;
//
//					@Override
//					public void onNetworkError(Request request, IOException e) {
//						// TODO Auto-generated method stub
//						 
//					}
//					
//					@Override
//					public void onBizSuccess(String responseDescription, JSONObject data, String other) {
//						// TODO Auto-generated method stub
//						System.out.println("5555555");
//						try {
//							JSONObject jsonObject=new JSONObject(other);
//							String cccc=jsonObject.getString("reason");
//							String dddd=jsonObject.getString("error_code");
//							if (dddd.equals("0")) {
//								JSONObject jsonObject2=jsonObject.getJSONObject("result");
//								JSONObject jsonObject3=jsonObject2.getJSONObject("body");
//								oooo=jsonObject3.getString("CARINFO");
//								xianshi.setText(oooo);
//							}else {
//								xianshi.setText(cccc);
//							}
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					
//					@Override
//					public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
//						// TODO Auto-generated method stub
//						System.out.println(responseDescription+"2222");
//						xianshi.setText("输入错误或查询次数超过10次");
//					}
//				}, "LSVAB49J132043849");
		
			new Thread(new Runnable() {
				 
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ccaa=request11("http://getvin.api.juhe.cn/CarManagerServer/getVINFormat", "VIN="+key1+"&key=db0109095c7020b6c62ab86c2db9b23f");
					Message message=new Message();
					message.what=1;
					message.obj=ccaa;
					handler.sendMessage(message);
					System.out.println(ccaa);
					
				}
			}).start();

			}
		});
	changeFonts(bodyParentView);	
	}
    public void jiexi(String ccaa){
    	JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(ccaa);
		
		String cccc=jsonObject.getString("reason");
		String dddd=jsonObject.getString("error_code");
		if (dddd.equals("0")) {
			JSONObject jsonObject2=jsonObject.getJSONObject("result");
			JSONObject jsonObject3=jsonObject2.getJSONObject("body");
			String oooo = jsonObject3.getString("CARINFO");
			xianshi.setText(oooo);
		}else {
			xianshi.setText(cccc);
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}


}
