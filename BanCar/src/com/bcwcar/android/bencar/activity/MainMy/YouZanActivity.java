package com.bcwcar.android.bencar.activity.MainMy;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.youzan.sdk.YouzanBridge;
import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.YouzanUser;
import com.youzan.sdk.http.engine.OnRegister;
import com.youzan.sdk.http.engine.QueryError;
import com.youzan.sdk.web.plugin.YouzanChromeClient;
import com.youzan.sdk.web.plugin.YouzanWebClient;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class YouZanActivity extends BaseActivity {
	private WebView mwebview;
	// TODO-WARNING: 传入链接, 请修改成你们店铺的链接
	String URL_HOMEPAGE = "https://wap.koudaitong.com/v2/showcase/homepage?alias=1daabroxj";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mwebview = (WebView) findViewById(R.id.webview001);
		// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		mwebview.setWebViewClient(new WebViewClient() {
					@Override
					public boolean shouldOverrideUrlLoading(WebView view, String url) {
						// TODO Auto-generated method stub
						// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
						view.loadUrl(url);
						return true;
					}
					@Override
					public void onPageFinished(WebView view, String url) {
						// TODO Auto-generated method stub
						super.onPageFinished(view, url);
						//Dialogcancel();加载完成  建议提示dialogo
					}
				});
		youzan_register();
	}
//注册信息
	@SuppressWarnings("deprecation")
	public void youzan_register() {
		YouzanUser user = new YouzanUser();
		user.setUserId(UserLoginStatus.get(getApplicationContext(), "UserId"));// 用户ID,用于标识该用户在APP中是唯一的(推荐使用用户的手机号等)
		user.setGender(1);
		user.setNickName(UserLoginStatus.get(getApplicationContext(), "NickName"));
		user.setTelephone(UserLoginStatus.get(getApplicationContext(), "UserMobile"));
		user.setUserName(UserLoginStatus.get(getApplicationContext(), "NickName"));

		YouzanSDK.asyncRegisterUser(user, new OnRegister() {
			/**
			 * 注册失败, 请参考错误信息修改注册参数 如报非法请求, 请检查UA, AppID和AppSecret是否正确
			 */
			@Override
			public void onFailed(QueryError queryError) {
				Toast.makeText(YouZanActivity.this, queryError.getMsg(), Toast.LENGTH_SHORT).show();
			}
			/**
			 * 注册成功, 打开有赞入口网页
			 */
			@Override
			public void onSuccess() {
				loadPage(URL_HOMEPAGE);
			}
		});
	}

	/**
	 * 自定义ChromeClient 必须继承自{@link YouzanWebClient}
	 */
	private class ChromeClient extends YouzanChromeClient {

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			// 这里获取到WebView的标题
		}
	}

	/**
	 * 自定义WebClient 必须继承自{@link YouzanWebClient}
	 */
	private class WebClient extends YouzanWebClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (super.shouldOverrideUrlLoading(view, url)) {
				return true;
			}
			return false;// 或者做其他操作
		}
	}

	public void loadPage(String url) {
		YouzanBridge bridge = YouzanBridge.build(this, mwebview)
				.setWebClient(new WebClient())
				.setChromeClient(new ChromeClient())
				.create();
		bridge.hideTopbar(true);// 隐藏顶部店铺信息栏
		if (mwebview != null && !TextUtils.isEmpty(url)) {
			mwebview.loadUrl(url);// 店铺链接
		}
	}
	@SuppressWarnings("deprecation")
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
		
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("自驾装备");
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(YouZanActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		changeFonts((ViewGroup)rootView);
	}
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.youzan_activity_main, bodyParentView);
	}
	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}
	
}
