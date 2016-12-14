package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpMainMy;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.squareup.okhttp.Request;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Acitivityfankui extends BaseActivity{
      String content;
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true);  //中文字体加粗
		centerView.setText("意见反馈");
		
		rightView.setVisibility(View.GONE);
		changeFonts(titleParentView);
		
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		View bodyView = LayoutInflater.from(this).inflate(R.layout.bancarfankui, bodyParentView);
		final EditText shuru=(EditText)bodyView.findViewById(R.id.fankui11);
		TextView tijiao=(TextView)bodyView.findViewById(R.id.tijiao);
		
	    content=shuru.getText().toString();
	    tijiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HttpMainMy.isuuefankui(new CallbackLogic() {

					@Override
					public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
						// TODO Auto-generated method stub
						showToast("提交成功");
						finish();
					}
					
					
				}, UserLoginStatus.get(Acitivityfankui.this, "Token"), shuru.getText().toString(),Acitivityfankui.this);
			}
		});
	    changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}


	

}
