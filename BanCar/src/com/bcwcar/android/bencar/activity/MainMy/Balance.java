package com.bcwcar.android.bencar.activity.MainMy;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.BankCarActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.util.PageUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Balance extends BaseActivity{
     LinearLayout rechargeline,bankline,gotocharge;
     TextView consumption,withdraw,passwordmanage,textbanance;    
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
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
		centerView.setText("犇车钱包");
		rightView.setVisibility(View.GONE);
		
	changeFonts(titleParentView);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initview();
	}
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
        initdata();	
        if (!UserLoginStatus.isLoggedOn(getApplicationContext())) {
		PageUtil.jumpTo(Balance.this, LoginActivity.class);	
		finish();
		}
    }
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.banance, bodyParentView);
		changeFonts(bodyParentView);
	}
	
    public void initdata() {
    	System.out.println("Cash="+WithdrawData.getWithdrawData(getApplicationContext(), "Wallet"));
		textbanance.setText(WithdrawData.getWithdrawData(getApplicationContext(),"Wallet").toString());
		
		
	}
    public void initview() {
	rechargeline=(LinearLayout)findViewById(R.id.gotorecharge);
	textbanance=(TextView)findViewById(R.id.textbalance1);
	bankline=(LinearLayout)findViewById(R.id.addbank);
	consumption=(TextView)findViewById(R.id.consumption);
	passwordmanage=(TextView)findViewById(R.id.passwordcontrol);
	passwordmanage.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			PageUtil.jumpTo(Balance.this, Setpassword.class);
		}
	});
	withdraw=(TextView)findViewById(R.id.withdrawte);
	gotocharge=(LinearLayout)findViewById(R.id.gotorecharge);
	gotocharge.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			PageUtil.jumpTo(Balance.this, Chargemoney.class);
		}
	});
	consumption.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		PageUtil.jumpTo(Balance.this, MyMoneyDetailActivity.class);	
		}
	});
	bankline.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Bundle bundle=new Bundle();
			bundle.putString(BizDefineAll.BIZ_ACTION, "Balance");
			PageUtil.jumpTo(Balance.this, BankCarActivity.class,bundle);
		}
	});
	withdraw.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		PageUtil.jumpTo(Balance.this, WithdrawalsActivity.class);	
		}
	});
	}
    
	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}

}
