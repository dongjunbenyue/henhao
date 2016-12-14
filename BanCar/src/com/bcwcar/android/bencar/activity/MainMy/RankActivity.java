package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.adapter.RankAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RankActivity extends BaseActivity {
	ListView ranklistview;
	RankAdapter adapter;
	private List<Map<String, String>> listdata;
	SimpleDraweeView touxiang;
	TextView paiming,yaoqingrenshu,nickname;

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		centerView.setText("邀请排名");
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		// leftView.setVisibility(View.GONE);
		leftView.setText("");
		
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		rightView.setText("规则");
		rightView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			PageUtil.jumpTo(RankActivity.this, RankRule.class);
			}
		});
   changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(getApplicationContext()).inflate(R.layout.body_cheatmoney_rank, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

	

	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initview();
		initdata();

	}

	/*
	 * 实例化控件
	 */
	public void initview() {
		ranklistview = (ListView) findViewById(R.id.ranklistview);
	    touxiang=(SimpleDraweeView)findViewById(R.id.paimingtouxiang);
	    paiming=(TextView)findViewById(R.id.paimingtext);
	    yaoqingrenshu=(TextView)findViewById(R.id.invitenum);
	    nickname=(TextView)findViewById(R.id.nickname);
	}

	// 控件赋值
	public void initdata() {
		nickname.setText(UserLoginStatus.get(getApplicationContext(), "NickName"));
		ResourceUtil.setSimpleDraweeViewImage(touxiang, UserLoginStatus.get(getApplicationContext(), "IconUrl"));
		yaoqingrenshu.setText(WithdrawData.getWithdrawData(getApplicationContext(), "InviteCount"));
		if (WithdrawData.getWithdrawData(getApplicationContext(), "SortOrder").equals("0")) {
			paiming.setText("未上榜");
		}else {
			paiming.setText("第"+WithdrawData.getWithdrawData(getApplicationContext(), "SortOrder")+"名");
		}
	
		HttpOrder.getRank(new CallbackLogic() {

			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					JSONArray array = data.getJSONArray("Data");
					if (array.length() == 0) {
						showToast("没有数据");
						return;
					} else {
						listdata = CollectionUtil.jsonArrayToListMap(array);

						adapter = new RankAdapter(listdata, getApplicationContext());
						ranklistview.setAdapter(adapter);
						
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, UserLoginStatus.get(RankActivity.this,"Token"),RankActivity.this);
	}
}
