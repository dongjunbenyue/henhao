package com.bcwcar.android.bencar.activity;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.adapter.ConversationListAdapterEx;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpUserInfo;
import com.bcwcar.android.bencar.config.Constant;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.widget.CustomDialog;
import com.squareup.okhttp.Request;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.RongIMClient.CreateDiscussionCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import io.rong.imlib.model.Conversation.ConversationType;

/**
 * 
 * 主页 会话列表
 * 
 ***/
public class ConversationListActivity extends BaseActivity {
	private RelativeLayout system_RelativeLayout,shop_RelativeLayout,quan_RelativeLayout;
	private TextView date01,date02,date03;
	private String mTimeString001 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
			.format(new Date(System.currentTimeMillis()));
	private Map<String, String> map001=new HashMap<String, String>();
	private Map<String, String> map002=new HashMap<String, String>();
	private Map<String, String> map003=new HashMap<String, String>();
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		getnewslist();
		enterFragment();
		/**
		 * 设置会话列表界面操作的监听器。
		 */
		RongIM.setConversationListBehaviorListener(new MyConversationListBehaviorListener());
		initview();
	}

	//huo获取消息列表
	public void getnewslist(){
		HttpUserInfo.getMessageInfo(new CallbackLogic() {
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					JSONArray array=data.getJSONArray("Data");
					for (int i = 0; i < array.length(); i++) {
						JSONObject object=array.getJSONObject(i);
						String MesType=object.get("MesType").toString();
						if (MesType.equals("1")) {
							map001=CollectionUtil.jsonObjectToMap_String(object);
						}else if (MesType.equals("2")) {
							map002=CollectionUtil.jsonObjectToMap_String(object);
						}else if (MesType.equals("3")) {
							map003=CollectionUtil.jsonObjectToMap_String(object);
						}
					}
					
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}, UserLoginStatus.get(ConversationListActivity.this, "Token"), ConversationListActivity.this);
		
	}
	
	
	//实例化
	public void initview(){
		date01=(TextView)findViewById(R.id.textView_today_date001);
		date02=(TextView)findViewById(R.id.textView_today_date002);
		date03=(TextView)findViewById(R.id.textView_today_date003);
		date01.setText(mTimeString001);
		date02.setText(mTimeString001);
		date03.setText(mTimeString001);
		system_RelativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout_news_system_001);
		shop_RelativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout_news_shop_001);
		quan_RelativeLayout=(RelativeLayout)findViewById(R.id.RelativeLayout_news_quan_001);
		system_RelativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ConversationListActivity.this,WebViewActivityDataShow.class);
				intent.putExtra("url", map001.get("ConnectUrl").toString());
				intent.putExtra("title", "系统消息");
				startActivity(intent);
				
			}
		});
		shop_RelativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ConversationListActivity.this,WebViewActivityDataShow.class);
				intent.putExtra("url", map002.get("ConnectUrl").toString());
				intent.putExtra("title", "商家消息");
				startActivity(intent);
			}
		});
		quan_RelativeLayout.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(ConversationListActivity.this,WebViewActivityDataShow.class);
		intent.putExtra("url", map003.get("ConnectUrl").toString());
		intent.putExtra("title", "购券消息");
		startActivity(intent);
	}
});
		
		
		
		
		
	}
	
	
	
	
 	private class MyConversationListBehaviorListener implements RongIM.ConversationListBehaviorListener{
	    /**
	     * 长按会话列表中的 item 时执行。
	     *
	     * @param context        上下文。
	     * @param view           触发点击的 View。
	     * @param uiConversation 长按时的会话条目。
	     * @return 如果用户自己处理了长按会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
	     */
	    @Override
	    public boolean onConversationLongClick(Context context, View view, final UIConversation uiConversation) {
	    showAlertDialog(uiConversation);
	        return true;
	    }

	    /**
	     * 点击会话列表中的 item 时执行。
	     *
	     * @param context        上下文。
	     * @param view           触发点击的 View。
	     * @param uiConversation 会话条目。
	     * @return 如果用户自己处理了点击会话后的逻辑处理，则返回 true， 否则返回 false，false 走融云默认处理方式。
	     */
	    @Override
	    public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
	        return false;
	    }

		@Override
		public boolean onConversationPortraitClick(Context arg0, ConversationType arg1, String arg2) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onConversationPortraitLongClick(Context arg0, ConversationType arg1, String arg2) {
			// TODO Auto-generated method stub
			return false;
		}
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		// sensor_register();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	/**
	 * 加载 会话列表 ConversationListFragment
	 */
	private void enterFragment() {
		ConversationListFragment fragment = (ConversationListFragment) getSupportFragmentManager()
				.findFragmentById(R.id.conversationlist);
		Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon().appendPath("conversationlist")
				.appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") // 设置私聊会话非聚合显示
				.appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")// 设置群组会话聚合显示
				.appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")// 设置讨论组会话非聚合显示
				.appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")// 设置系统会话非聚合显示
				.build();
		fragment.setUri(uri);
		fragment.setAdapter(new ConversationListAdapterEx(RongContext.getInstance()));
	}

//=================================================

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// initSystemBar(ConversationListActivity.this, R.color.main_yellow);
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		changeFonts((ViewGroup) rootView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		rightView.setText("");
		leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("消息列表");
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.conversationlist, bodyParentView);
		changeFonts((ViewGroup) view);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}
	public void showAlertDialog(final UIConversation uiConversation) {
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setTitle(uiConversation.getUIConversationTitle());
		builder.setPositiveButton(getString(R.string.button_conversationlist_item_delet), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//设置你的操作事项
				RongIM.getInstance().getRongIMClient().removeConversation(uiConversation.getConversationType(), uiConversation.getConversationTargetId());
			}
		});
		builder.setNegativeButton(getString(R.string.button_conversationlist_item_top),
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						RongIM.getInstance().getRongIMClient().setConversationToTop(uiConversation.getConversationType(), uiConversation.getConversationTargetId(), true);
					}
				});

		builder.create().show();

	}

}
