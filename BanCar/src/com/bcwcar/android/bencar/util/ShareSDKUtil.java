package com.bcwcar.android.bencar.util;

import com.bcwcar.android.bencar.R;

import android.app.Activity;
import android.content.Context;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ShareSDKUtil {
	private final static String logo="http://www.bcwcar.com/banner/shareicon.png";
//	private final static String logo="asset:///ferrari.png";
	private final static String URLSHARE="http://www.bcwcar.com/BenCar/app/sendUInvitation?InviteCode=";
	
	public static void showShare(Activity activity,String InviteCode) {
		System.out.println("========"+URLSHARE+InviteCode);
		ShareSDK.initSDK(activity);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle("犇车养车，超低折扣，更有好礼相送！");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(URLSHARE+InviteCode);
		// text是分享文本，所有平台都需要这个字段
		oks.setText("车友们，快来注册犇车享受超低养车折扣吧。邀请好友注册犇车网，更有好礼相送哦！");
		oks.setImageUrl(logo);
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(URLSHARE+InviteCode);
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(activity.getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(URLSHARE+InviteCode);
		// 显示
		oks.show(activity);
		
	}
	public static void showShare1(Activity activity,String tilee,String url) {
		
		ShareSDK.initSDK(activity);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(tilee);
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(url);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(tilee);
		oks.setImageUrl(logo);
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(url);
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(activity.getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(url);
		// 显示
		oks.show(activity);
		
	}
	
//	public void test(){
//		
//		 ShareSDK.initSDK(this);
//		 OnekeyShare oks = new OnekeyShare();
//		 //关闭sso授权
//		 oks.disableSSOWhenAuthorize(); 
//		 
//		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//		 oks.setTitle(getString(R.string.share));
//		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//		 oks.setTitleUrl("http://sharesdk.cn");
//		 // text是分享文本，所有平台都需要这个字段
//		 oks.setText("我是分享文本");
//		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//		 //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//		 // url仅在微信（包括好友和朋友圈）中使用
//		 oks.setUrl("http://sharesdk.cn");
//		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//		 oks.setComment("我是测试评论文本");
//		 // site是分享此内容的网站名称，仅在QQ空间使用
//		 oks.setSite(getString(R.string.app_name));
//		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//		 oks.setSiteUrl("http://sharesdk.cn");
//		 
//		// 启动分享GUI
//		 oks.show(this);
//		
//		
//	}
}
