package com.bcwcar.android.bencar.activity.MainMy;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.util.PageUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Chargemoney extends BaseActivity{

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
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
				centerView.getPaint().setFakeBoldText(true);  //中文字体加粗
				centerView.setText("充值金额");
				rightView.setVisibility(View.GONE);
				
			changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.body_charge, bodyParentView);
		changeFonts(bodyParentView);
	  final EditText editText=(EditText)findViewById(R.id.edittextmoney);
	  TextView textView=(TextView)findViewById(R.id.buttonconfirm);
	  textView.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			WithdrawData.setWithdrawData(getApplicationContext(), "ChargeMoney", editText.getText().toString());
			PageUtil.jumpTo(Chargemoney.this, PaytoCharge.class);
		}
	});
	}
   
	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}

}
