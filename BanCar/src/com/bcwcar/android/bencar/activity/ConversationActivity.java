package com.bcwcar.android.bencar.activity;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.Model;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.widget.CustomDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.RongIM.ConversationBehaviorListener;
import io.rong.imkit.RongIM.UserInfoProvider;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.fragment.MessageListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.InputView;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.RongIMClient.ResultCallback;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Discussion;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.LocationMessage;
import io.rong.imlib.model.Conversation.ConversationType;

public class ConversationActivity extends BaseActivity implements ConversationBehaviorListener {
	/**
	 * 目标 Id
	 */
	private String mTargetId;
	/**
	 * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
	 */
	private String mTargetIds;
	/**
	 * 会话类型
	 */
	private Conversation.ConversationType mConversationType;
	/**
	 * 标题，群组名称，讨论组名称等等
	 * 
	 */
	private String title;
	private TextView centerView;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		final RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.toubuaaa);
		relativeLayout.setVisibility(View.GONE);
		relativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			PageUtil.jumpTo(ConversationActivity.this, EverydayFeel.class);
			}
		});
		ImageView delete=(ImageView)findViewById(R.id.delete);
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				relativeLayout.setVisibility(View.GONE);
			}
		});
		RongIM.setConversationBehaviorListener(this);// 设置会话界面操作的监听器。
		
		/**
		 * 设置位置信息的提供者。
		 * 
		 * @param locationProvider
		 *            位置信息提供者。
		 * 
		 */
		RongIM.setLocationProvider(new RongIM.LocationProvider() {
			@Override
			public void onStartLocation(Context context, LocationCallback locationCallback) {
				// 在这里打开你的地图页面,保存 locationCallback 对象。
				Model.getInstance().setLastLocationCallback(locationCallback);
				//showToast("在这里打开你的地图页面,保存 locationCallback 对象");
				Intent intent = new Intent(ConversationActivity.this, OpenBaiMap.class);
				intent.putExtra("action", "0");
				startActivity(intent);
			}
		});
		// 底部bottom事件
		InputProvider.ExtendProvider[] provider = {
				new ImageInputProvider(RongContext.getInstance()), // 图片
				new CameraInputProvider(RongContext.getInstance()), // 相机
				new LocationInputProvider(RongContext.getInstance())// 地理位置
		};
		RongIM.getInstance().resetInputExtensionProvider(mConversationType.PRIVATE, provider);
		RongIM.getInstance().resetInputExtensionProvider(mConversationType.DISCUSSION, provider);
		Intent intent = getIntent();
		getIntentDate(intent);
	}
	/**
	 * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
	 */
	private void getIntentDate(Intent intent) {

		mTargetId = intent.getData().getQueryParameter("targetId");
		mTargetIds = intent.getData().getQueryParameter("targetIds");
		title = intent.getData().getQueryParameter("title").toString();

		System.out.println("mTargetId=" + mTargetId);
		System.out.println("mTargetIds=" + mTargetIds);
		System.out.println("title=" + title);
		centerView.setText(title + "");
		mConversationType = Conversation.ConversationType
				.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
		System.out.println(mConversationType.getName() + "=================获得当前会话类型==================="
				+ intent.getData().getLastPathSegment());
		enterFragment(mConversationType, mTargetId);

	}

	/**
	 * 加载会话页面 ConversationFragment
	 *
	 * @param mConversationType
	 *            会话类型
	 * @param mTargetId
	 *            目标 Id
	 */
	private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {
		ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager()
				.findFragmentById(R.id.conversation);
		Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon().appendPath("conversation")
				.appendPath(mConversationType.getName().toLowerCase()).appendQueryParameter("targetId", mTargetId)
				.build();
		fragment.setUri(uri);
	}

	
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		changeFonts((ViewGroup) rootView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		
		centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		rightView.setText("去评价");
		rightView.getPaint().setFakeBoldText(true);
		rightView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ConversationActivity.this,GoToPinjia.class);
				intent.putExtra("ids", mTargetId);
				startActivity(intent);
			}
		});
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.conversation, bodyParentView);
		changeFonts((ViewGroup) view);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

	/***
	 * 消息的点击事件
	 */
	@Override
	public boolean onMessageClick(Context context, View arg1, Message message) {
		// TODO Auto-generated method stub
		System.out.println("=======" + message.getContent());
		if (message.getContent() instanceof ImageMessage) {
			System.out.println("=======" + message.getContent());
			ImageMessage imageMessage = (ImageMessage) message.getContent();
			Intent intent = new Intent(context, PhotoActivity.class);
			intent.putExtra("photo",
					imageMessage.getLocalUri() == null ? imageMessage.getRemoteUri() : imageMessage.getLocalUri());
			if (imageMessage.getThumUri() != null)
				intent.putExtra("thumbnail", imageMessage.getThumUri());

			context.startActivity(intent);
		}
		else  if (message.getContent() instanceof LocationMessage) {
            Intent intent = new Intent(context, OpenBaiMap.class);
            intent.putExtra("location", message.getContent());
            intent.putExtra("action", "1");
            context.startActivity(intent);
        } 
		return false;
	}

	@Override
	public boolean onMessageLinkClick(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onMessageLongClick(Context arg0, View arg1, Message message) {
		// TODO Auto-generated method stub
		showAlertDialog(message);
		return true;
	}

	@Override
	public boolean onUserPortraitClick(Context arg0, ConversationType arg1, UserInfo arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onUserPortraitLongClick(Context arg0, ConversationType arg1, UserInfo arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	
	//弹出框
	public void showAlertDialog(final Message message) {
		CustomDialog.Builder builder = new CustomDialog.Builder(this);
		builder.setTitle(title);
//		builder.setPositiveButton(getString(R.string.button_conversationlist_item_delet), new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				//设置你的操作事项
//		//		RongIM.getInstance().getRongIMClient().removeConversation(uiConversation.getConversationType(), uiConversation.getConversationTargetId());
//			}
//		});
		builder.setNegativeButton("删除",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
				//		RongIM.getInstance().getRongIMClient().setConversationToTop(uiConversation.getConversationType(), uiConversation.getConversationTargetId(), true);
						int[] tt={message.getMessageId()};
						RongIM.getInstance().getRongIMClient().deleteMessages(tt);
					}
				});

		builder.create().show();

	}
	
	
	
}
