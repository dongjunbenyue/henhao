package com.bcwcar.android.bencar.activity;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpUserInfo;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Request;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class GoToPinjia extends BaseActivity{

	private SimpleDraweeView img;
	private TextView EmpNametext;
	private RatingBar ratingBar;
	private CheckBox button1;
	private CheckBox button2;
	private CheckBox button3;
	private CheckBox button4;
	private CheckBox button5;
	private CheckBox button6;
	private Button buttontijiao;
	private String empid;
	protected String iconurl;
	protected String yuangongmingzi;
	private TextView textsore;
    private StringBuffer pingjiatext=new StringBuffer();
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
		leftView.setText("");
		centerView.setVisibility(View.GONE);
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.body_eluvate_guwen, bodyParentView);
		changeFonts(bodyParentView);
	}
    public void initview(){
     img=(SimpleDraweeView)findViewById(R.id.service_network_logo);	
     EmpNametext=(TextView)findViewById(R.id.EmpNametext);
     ratingBar = (RatingBar) findViewById(R.id.my_order_evaluate_OrderScore);
     button1=(CheckBox)findViewById(R.id.buttonpin1);
     button2=(CheckBox)findViewById(R.id.buttonpin2);
     button3=(CheckBox)findViewById(R.id.buttonpin3);
     button4=(CheckBox)findViewById(R.id.buttonpin4);
     button5=(CheckBox)findViewById(R.id.buttonpin5);
     button6=(CheckBox)findViewById(R.id.buttonpin6);
     textsore=(TextView)findViewById(R.id.kefupinfen);
     buttontijiao=(Button)findViewById(R.id.setting_logout);
     button1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (button1.isChecked()) {
				button1.setTextColor(getResources().getColor(R.color.white));;
				
				
			}else {
				button1.setTextColor(getResources().getColor(R.color.common_bg));
				
			}
		}
	});
     button2.setOnClickListener(new OnClickListener() {
 		
 		@Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			if (button2.isChecked()) {
 				button2.setTextColor(getResources().getColor(R.color.white));;
 			}else {
				button2.setTextColor(getResources().getColor(R.color.common_bg));
			}
 		}
 	});
     
     button3.setOnClickListener(new OnClickListener() {
 		
 		@Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			if (button3.isChecked()) {
 				button3.setTextColor(getResources().getColor(R.color.white));;
 			}else {
				button3.setTextColor(getResources().getColor(R.color.common_bg));
			}
 		}
 	});
     button4.setOnClickListener(new OnClickListener() {
 		
 		@Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			if (button4.isChecked()) {
 				button4.setTextColor(getResources().getColor(R.color.white));;
 			}else {
				button4.setTextColor(getResources().getColor(R.color.common_bg));
			}
 		}
 	});
     button5.setOnClickListener(new OnClickListener() {
 		
 		@Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			if (button5.isChecked()) {
 				button5.setTextColor(getResources().getColor(R.color.white));;
 			}else {
				button5.setTextColor(getResources().getColor(R.color.common_bg));
			}
 		}
 	});
     button6.setOnClickListener(new OnClickListener() {
 		
 		@Override
 		public void onClick(View v) {
 			// TODO Auto-generated method stub
 			if (button6.isChecked()) {
 				button6.setTextColor(getResources().getColor(R.color.white));;
 			}else {
				button6.setTextColor(getResources().getColor(R.color.common_bg));
			}
 		}
 	});
     ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
		
		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			// TODO Auto-generated method stub
			textsore.setText(String.valueOf(rating));
		}
	});
     buttontijiao.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (button1.isChecked()) {
				pingjiatext.append("解答问题很细心，");
			}
			if (button2.isChecked()) {
				pingjiatext.append("及时给予反馈，");
			}
			if (button3.isChecked()) {
				pingjiatext.append("很有耐心，");
			}
			if (button4.isChecked()) {
				pingjiatext.append("服务很到位，");
			}
			if (button5.isChecked()) {
				pingjiatext.append("幽默，");
			}
			if (button6.isChecked()) {
				pingjiatext.append("可爱，");
			}
			if (pingjiatext.equals("")||pingjiatext.equals(null)) {
				
			}else {
				pingjiatext.deleteCharAt(pingjiatext.length()-1);
			}
			HttpUserInfo.scoreToEmployee(new CallbackLogic() {
				
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
					// TODO Auto-generated method stub
					showToast("评价成功");
					System.out.println(pingjiatext.toString());
					pingjiatext.delete(0, pingjiatext.length()-1);
					finish();
				}
			}, UserLoginStatus.get(GoToPinjia.this, "Token"), empid, String.valueOf(ratingBar.getRating()),pingjiatext.toString(),GoToPinjia.this);
		}
	});
    }
    public void initdata(){
    HttpUserInfo.getEmployee(new CallbackLogic() {
		 

		@Override
		public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
			// TODO Auto-generated method stub
			Dialogcancel();
		try {
			JSONObject data1=data.getJSONObject("Data")	;
			iconurl=data1.getString("IconUrl");
			yuangongmingzi=data1.getString("EmpName");
			 ResourceUtil.setSimpleDraweeViewImage(img, iconurl);
			 EmpNametext.setText(yuangongmingzi);
			 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	}, UserLoginStatus.get(GoToPinjia.this, "Token"), empid,GoToPinjia.this);	
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
			empid=getIntent().getStringExtra("ids");
    	initview();
    	initdata();
    }
	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}


}
