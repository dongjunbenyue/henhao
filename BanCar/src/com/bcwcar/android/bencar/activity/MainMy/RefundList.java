package com.bcwcar.android.bencar.activity.MainMy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.activity.WangDianDatas;
import com.bcwcar.android.bencar.adapter.ServiceAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.google.gson.JsonIOException;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.sharesdk.onekeyshare.themes.classic.IndicatorView;

public class RefundList extends BaseActivity{
    TextView ordermoney,refundperiod,paymethod,ordernum,tuihuanfangshi;
    ListView listview;
    private String OrderId;
    private Map<String, String> map = new HashMap<String, String>();
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
		// leftView.setVisibility(View.GONE);
		leftView.setText("");

		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("退款详情");
		
		changeFonts(titleParentView);
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	OrderId=getIntent().getStringExtra("OrderId");
    	initview();
    }
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	initdata();
    }
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.refundlist, bodyParentView);
	}
	public void  initview(){
		ordermoney=(TextView)findViewById(R.id.ordermoney);
		refundperiod=(TextView)findViewById(R.id.refundperiod);
		paymethod=(TextView)findViewById(R.id.paymethod);
		listview=(ListView)findViewById(R.id.tuikuanliucheng);
		ordernum=(TextView)findViewById(R.id.ordernum);
		tuihuanfangshi=(TextView)findViewById(R.id.tuihuanfangshi);
	}
    public void initdata(){
    	HttpOrder.getorderreturn(new CallbackLogic() {
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				jiexi(data);
				ordermoney.setText(map.get("PaySum"));
				refundperiod.setText(map.get("ReturnCircle"));
				paymethod.setText(map.get("PayTypeName"));
				ordernum.setText(map.get("OrderNo"));
				tuihuanfangshi.setText(map.get("ReturnType"));
				ServiceAdapter adapter=new ServiceAdapter(getApplicationContext(), list);
				listview.setAdapter(adapter);
			}
		}, UserLoginStatus.get(RefundList.this, "Token"), OrderId, RefundList.this);
    }
    public void jiexi(JSONObject data){
     
		String string;
		try {
			string = data.getString("Data").toString();
		
		JSONObject object = new JSONObject(string);
		JSONArray array = object.getJSONArray("CheckList");
		if (array.length() != 0) {
			for (int i = 0; i < array.length(); i++) {
				list.add(CollectionUtil.jsonObjectToMap_String(array.getJSONObject(i)));
			}
		} else {

		}
		map = CollectionUtil.jsonObjectToMap_String(object);
		
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
