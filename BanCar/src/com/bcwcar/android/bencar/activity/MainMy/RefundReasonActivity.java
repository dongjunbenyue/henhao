package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Request;

import android.app.AlertDialog;
import android.app.usage.UsageEvents;
import android.content.DialogInterface;

import android.view.View.OnClickListener;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.a.a.r;

/**
 * 退款原因页
 */
public class RefundReasonActivity extends BaseActivity {
	private static final String LOG_TAG = RefundReasonActivity.class.getSimpleName();
	private String mOrderId;
	private RadioGroup mRadioGroup;
	private String OrderType;
	private String type;
	private String ShopName;
	private String ShopAddress;
	private String LogoUrl;
	private SimpleDraweeView simplelogo;
	private TextView ShopAddress1;
	private TextView shopname1;
	private String Type;

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		mOrderId = getIntent().getStringExtra("OrderId");
		Type=DingDanDataSave.get(getApplicationContext(), "Type");
		Log.d(LOG_TAG, "mOrderId = " + mOrderId);

		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		
		leftView.setText("");
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("我要退款");
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
		// LayoutInflater.from(this).inflate(R.layout.activity_test,
		// bodyParentView);
		LayoutInflater.from(this).inflate(R.layout.body_refund_reason, bodyParentView);
		ShopName=getIntent().getStringExtra("ShopName");
		ShopAddress=getIntent().getStringExtra("ShopAddress");
		LogoUrl=getIntent().getStringExtra("LogoUrl");
		simplelogo=(SimpleDraweeView)findViewById(R.id.wangdian_logo0011);
		ShopAddress1=(TextView)findViewById(R.id.wangdian_adrress001);
		shopname1=(TextView)findViewById(R.id.wangdian_name001);
		ResourceUtil.setSimpleDraweeViewImage(simplelogo, LogoUrl);
		shopname1.setText(ShopName);
		ShopAddress1.setText(ShopAddress);
		Button button = (Button) findViewById(R.id.refund_reason_do);
		button.setText("我要退款");
		changeFonts(bodyParentView);
	}

	
	public void onViewClick(View view) {
		final int viewId = view.getId();
		if (R.id.refund_reason_do == viewId) {
			int checkedId = mRadioGroup.getCheckedRadioButtonId();
			if (-1 == checkedId) {
				Toast.makeText(RefundReasonActivity.this, R.string.refund_reason_please_select_reason,
						Toast.LENGTH_SHORT).show();
				return;
			}
			RadioButton rb = (RadioButton) findViewById(checkedId);
			
			String returnReason = rb.getText().toString();
			
			HttpOrder.orderRefund(new CallbackLogic() {

				

				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
					// TODO Auto-generated method stub
				showToast("退款成功");
				finish();
				}

			}, UserLoginStatus.get(RefundReasonActivity.this,"Token"), mOrderId, returnReason,RefundReasonActivity.this,Type);
		} 
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// LayoutInflater.from(this).inflate(R.layout.common_toolbar_1,
		// toolBarParentView);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		mRadioGroup = (RadioGroup) findViewById(R.id.refund_reason_list_group);
		changeFonts(mRadioGroup);

		// ListView list = (ListView) findViewById(R.id.refund_reason_list);
		// List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();
		// Map<String, Object> dataLine = new HashMap<String, Object>();
		// dataLine.put("title", "预定错了！");
		// dataLine.put("checked", true);
		// data.add(dataLine);
		// dataLine = new HashMap<String, Object>();
		// dataLine.put("title", "计划改变，没时间前往！");
		// dataLine.put("checked", false);
		// data.add(dataLine);
		// dataLine = new HashMap<String, Object>();
		// dataLine.put("title", "对4S店服务不满意！");
		// dataLine.put("checked", false);
		// data.add(dataLine);
		// dataLine = new HashMap<String, Object>();
		// dataLine.put("title", "到店说无预约或无合作！");
		// dataLine.put("checked", false);
		// data.add(dataLine);
		// dataLine = new HashMap<String, Object>();
		// dataLine.put("title", "在4S店直接消费更便宜！");
		// dataLine.put("checked", false);
		// data.add(dataLine);
		// BaseAdapter adapter = new SimpleAdapter(
		// this, data, R.layout.list_item_refund_reason,
		// new String[]{"title", "checked"},
		// new int[] {R.id.refund_reason_title, R.id.refund_reason_checked});
		// list.setAdapter(adapter);
	}
}