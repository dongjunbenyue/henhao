package com.bcwcar.android.bencar.activity.MainMy;

import java.util.ArrayList;
import java.util.List;

import com.alipay.android.phone.mrpc.core.r;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.util.PageUtil;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * 我的订单容器页
 * @author Administrator
 */
public class MyOrderActivity extends BaseActivity {
	private static final String LOG_TAG = MyOrderActivity.class.getSimpleName();
	public static String FRAGMENT_DATA_STATUS = "status";
	public List<Integer> mFragmentIndexData;
	private TextView text1,text2,text3,text4,text5;
	private String action=null;
	private String repalece;
	private static View view1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		action="1";
		repalece="0";
		text1=(TextView)findViewById(R.id.my_order_xml_payment);
		text2=(TextView)findViewById(R.id.my_order_xml_non_payment);
		text3=(TextView)findViewById(R.id.my_order_xml_in_service);
		text4=(TextView)findViewById(R.id.my_order_xml_wait_for_evaluate);
		text5=(TextView)findViewById(R.id.my_order_xml_wait_for_refund);
		mFragmentIndexData = new ArrayList<Integer>();
		mFragmentIndexData.add(R.id.my_order_xml_payment);
		mFragmentIndexData.add(R.id.my_order_xml_non_payment);
		mFragmentIndexData.add(R.id.my_order_xml_in_service);
		mFragmentIndexData.add(R.id.my_order_xml_wait_for_evaluate);
		mFragmentIndexData.add(R.id.my_order_xml_wait_for_refund);
		try {
			action=getIntent().getStringExtra("action");
			repalece=getIntent().getStringExtra("replace");
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		System.out.println(repalece+"**************************************");
		Bundle bundle = new Bundle();
		bundle.putString(FRAGMENT_DATA_STATUS, "1");
		replaceFragmentPageWithData(new MyOrderCommPage(), bundle);
		setTabStaus(mFragmentIndexData.indexOf(R.id.my_order_xml_payment));
		if (!repalece.equals("0")) {
			onViewClick1(repalece);
		}
	}
	
	/**
	 * 切换到已付款、待付款、服务中或待评价，同时带上要传递的数据
	 * @param fragment 未包含要传递的数据Bundle
	 * @param data 需要传递给fragment的数据
	 */
	private void replaceFragmentPageWithData(Fragment fragment, Bundle data) {
		if(data != null) {
			fragment.setArguments(data);
		}
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.my_order_xml_fragment_root, fragment)
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}
	
	/**
	 * 设置Fragment顶部的切换条的状态 
	 * @param pageIndex 第几页面（从0开始）
	 */
	private void setTabStaus(int pageIndex) {
		final int tabID = mFragmentIndexData.get(pageIndex);
		for(int element : mFragmentIndexData) {
			if(tabID != element) {
				findViewById(element).setBackgroundResource(R.drawable.bg_white);
			} else {
				findViewById(element).setBackgroundResource(R.drawable.bg_white_inverse);
			}
		}
	}
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent=new Intent(MyOrderActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true);  //中文字体加粗
		centerView.setText(getString(R.string.my_order_title));
		rightView.setText("全部订单");
		rightView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(MyOrderActivity.this, MyOrderAll.class);
			}
		});
		changeFonts(titleParentView);
	}
	
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
	LayoutInflater.from(this).inflate(R.layout.body_my_order, bodyParentView);
		changeFonts(bodyParentView);
		
	}
	
	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	public void onViewClick(View view) {
		
		final int viewID = view.getId();
		
		switch (viewID) {
		case R.id.my_order_xml_payment : {
			Bundle bundle = new Bundle();
			bundle.putString(FRAGMENT_DATA_STATUS, "1");
			replaceFragmentPageWithData(new MyOrderCommPage(), bundle);
			setTabStaus(mFragmentIndexData.indexOf(R.id.my_order_xml_payment));
			text1.setTextColor(getResources().getColor(R.color.mainblue));
			text2.setTextColor(getResources().getColor(R.color.black));
			text3.setTextColor(getResources().getColor(R.color.black));
			text4.setTextColor(getResources().getColor(R.color.black));
			text5.setTextColor(getResources().getColor(R.color.black));
			break;
		}
		case R.id.my_order_xml_non_payment : {
			Bundle bundle = new Bundle();
			bundle.putString(FRAGMENT_DATA_STATUS, "2");
			replaceFragmentPageWithData(new MyOrderCommPage(), bundle);
			
			setTabStaus(mFragmentIndexData.indexOf(R.id.my_order_xml_non_payment));
			text2.setTextColor(getResources().getColor(R.color.mainblue));
			text1.setTextColor(getResources().getColor(R.color.black));
			text3.setTextColor(getResources().getColor(R.color.black));
			text4.setTextColor(getResources().getColor(R.color.black));
			text5.setTextColor(getResources().getColor(R.color.black));
			break;
		}
		case R.id.my_order_xml_in_service : {
			Bundle bundle = new Bundle();
			bundle.putString(FRAGMENT_DATA_STATUS, "3");
//			replaceFragmentPageWithData(new MyOrderCommPayment(), bundle);
			replaceFragmentPageWithData(new MyOrderCommPage(), bundle);
			setTabStaus(mFragmentIndexData.indexOf(R.id.my_order_xml_in_service));
			text3.setTextColor(getResources().getColor(R.color.mainblue));
			text2.setTextColor(getResources().getColor(R.color.black));
			text1.setTextColor(getResources().getColor(R.color.black));
			text4.setTextColor(getResources().getColor(R.color.black));
			text5.setTextColor(getResources().getColor(R.color.black));
			break;
		}
		case R.id.my_order_xml_wait_for_evaluate : {
			Bundle bundle = new Bundle();
			bundle.putString(FRAGMENT_DATA_STATUS, "4");
			replaceFragmentPageWithData(new MyOrderCommPage(), bundle);
			
			setTabStaus(mFragmentIndexData.indexOf(R.id.my_order_xml_wait_for_evaluate));
			text4.setTextColor(getResources().getColor(R.color.mainblue));
			text2.setTextColor(getResources().getColor(R.color.black));
			text3.setTextColor(getResources().getColor(R.color.black));
			text1.setTextColor(getResources().getColor(R.color.black));
			text5.setTextColor(getResources().getColor(R.color.black));
			break;
		}
		case R.id.my_order_xml_wait_for_refund : {
			Bundle bundle = new Bundle();
			bundle.putString(FRAGMENT_DATA_STATUS, "5");
			replaceFragmentPageWithData(new MyOrderCommPage(), bundle);
			
			setTabStaus(mFragmentIndexData.indexOf(R.id.my_order_xml_wait_for_refund));
			text5.setTextColor(getResources().getColor(R.color.mainblue));
			text2.setTextColor(getResources().getColor(R.color.black));
			text3.setTextColor(getResources().getColor(R.color.black));
			text1.setTextColor(getResources().getColor(R.color.black));
			text4.setTextColor(getResources().getColor(R.color.black));
			break;
		}
		default:
			break;
		}
	}
	public void onViewClick1(String string) {
		
		switch (string) {
		case "1": {
			
			Bundle bundle = new Bundle();
			bundle.putString(FRAGMENT_DATA_STATUS, "1");
			replaceFragmentPageWithData(new MyOrderCommPage(), bundle);
			setTabStaus(mFragmentIndexData.indexOf(R.id.my_order_xml_payment));
			text1.setTextColor(getResources().getColor(R.color.mainblue));
			text2.setTextColor(getResources().getColor(R.color.black));
			text3.setTextColor(getResources().getColor(R.color.black));
			text4.setTextColor(getResources().getColor(R.color.black));
			text5.setTextColor(getResources().getColor(R.color.black));
			break;
		}
		case "2" : {
			Bundle bundle = new Bundle();
			bundle.putString(FRAGMENT_DATA_STATUS, "2");
			replaceFragmentPageWithData(new MyOrderCommPage(), bundle);
			
			setTabStaus(mFragmentIndexData.indexOf(R.id.my_order_xml_non_payment));
			text2.setTextColor(getResources().getColor(R.color.mainblue));
			text1.setTextColor(getResources().getColor(R.color.black));
			text3.setTextColor(getResources().getColor(R.color.black));
			text4.setTextColor(getResources().getColor(R.color.black));
			text5.setTextColor(getResources().getColor(R.color.black));
			break;
		}
		case "3" : {
			Bundle bundle = new Bundle();
			bundle.putString(FRAGMENT_DATA_STATUS, "3");
//			replaceFragmentPageWithData(new MyOrderCommPayment(), bundle);
			replaceFragmentPageWithData(new MyOrderCommPage(), bundle);
			setTabStaus(mFragmentIndexData.indexOf(R.id.my_order_xml_in_service));
			text3.setTextColor(getResources().getColor(R.color.mainblue));
			text2.setTextColor(getResources().getColor(R.color.black));
			text1.setTextColor(getResources().getColor(R.color.black));
			text4.setTextColor(getResources().getColor(R.color.black));
			text5.setTextColor(getResources().getColor(R.color.black));
			break;
		}
		case "4" : {
			Bundle bundle = new Bundle();
			bundle.putString(FRAGMENT_DATA_STATUS, "4");
			replaceFragmentPageWithData(new MyOrderCommPage(), bundle);
			
			setTabStaus(mFragmentIndexData.indexOf(R.id.my_order_xml_wait_for_evaluate));
			text4.setTextColor(getResources().getColor(R.color.mainblue));
			text2.setTextColor(getResources().getColor(R.color.black));
			text3.setTextColor(getResources().getColor(R.color.black));
			text1.setTextColor(getResources().getColor(R.color.black));
			text5.setTextColor(getResources().getColor(R.color.black));
			break;
		}
		case "5" : {
			Bundle bundle = new Bundle();
			bundle.putString(FRAGMENT_DATA_STATUS, "5");
			replaceFragmentPageWithData(new MyOrderCommPage(), bundle);
			
			setTabStaus(mFragmentIndexData.indexOf(R.id.my_order_xml_wait_for_refund));
			text5.setTextColor(getResources().getColor(R.color.mainblue));
			text2.setTextColor(getResources().getColor(R.color.black));
			text3.setTextColor(getResources().getColor(R.color.black));
			text1.setTextColor(getResources().getColor(R.color.black));
			text4.setTextColor(getResources().getColor(R.color.black));
			break;
		}
		default:
			break;
		}
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			Intent intent=new Intent(MyOrderActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
	
}