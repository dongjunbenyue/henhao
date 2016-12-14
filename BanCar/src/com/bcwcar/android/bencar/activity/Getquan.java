package com.bcwcar.android.bencar.activity;

import org.json.JSONObject;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpCoupon;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Getquan extends BaseActivity {
	TextView textcity, brandtext, getcode;
	TextView telenumber;
	Button commitsub;
	String telephonenub, ecodenumber, passwordnub;
	private CountDownTimer requestVerifyCodeTimer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initview();
		initdata();
}
//=======================================================================
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		rightView.setText("活动说明");
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Getquan.this, HuoDongShuoMing.class);
				startActivity(intent);
			}
		});
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("登记送豪礼");
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.getquan, bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

	public void dioalog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Getquan.this);
		builder.setMessage("恭喜您！登记成功。服务上线后会第一时间通知您，并送上200元抵用券。").setCancelable(false).setPositiveButton("查看我的抵用券",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// PageUtil.jumpTo(Getquan.this,MyWalletCouponActivity.class
						// );
						showToast("我的抵用券");
					}
				});
		AlertDialog alert = builder.create();

	}

	// 实例化控件
	public void initview() {
		textcity = (TextView) findViewById(R.id.quancity);
		telenumber = (TextView) findViewById(R.id.teleedit);
		brandtext = (TextView) findViewById(R.id.quanbrand);
		commitsub = (Button) findViewById(R.id.my_money_withdraw_next_step1);
		commitsub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getcup();
			}
		});
	}

	//
	public void initdata() {
		dioalog();
		textcity.setText(LocationDataSave.get(Getquan.this, "CityName"));
		brandtext.setText(UserCarDataSave.get(Getquan.this, "Brand"));
		telenumber.setText(UserLoginStatus.get(Getquan.this, "Token"));
	}
	
	
	/**
	 * 领取已开通城市的未开通品牌的抵用券
	 */
	public  void getcup(){
		HttpCoupon.getMyCoupon(new CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
				// TODO Auto-generated method stub
				System.out.println("==获取的数据="+data);
				AlertDialog.Builder builder = new AlertDialog.Builder(Getquan.this);
				builder.setMessage("恭喜您！登记成功。服务上线后会第一时间通知您，并送上200元抵用券。").setCancelable(false)
						.setPositiveButton("查看我的抵用券", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// PageUtil.jumpTo(Getquan.this,MyWalletCouponActivity.class
						// );
						showToast("跳转到我的抵用券页面  ==待跳转");
						finish();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}, UserLoginStatus.get(Getquan.this, "Token"),
				LocationDataSave.get(Getquan.this, "CityId"),
				LocationDataSave.get(Getquan.this, "CityName"),
				UserCarDataSave.get(Getquan.this, "BrandId"), "", "", Getquan.this);
	}

}
