package com.bcwcar.android.bencar.util;

import java.util.Locale;

import android.content.res.Resources;
import android.net.Uri;

import com.bcwcar.android.bencar.base.BaseApplication;
import com.bcwcar.android.bencar.base.SLog;
import com.bcwcar.android.bencar.config.Config;
import com.facebook.drawee.view.SimpleDraweeView;

public class ResourceUtil {
	/**
	 * 得到Application级别的上下文
	 */
	public static BaseApplication getBaseAppContext() {
		return BaseApplication.getBaseAppContext();
	}
	/**
	 * 得到Application级别的资源引用
	 */
	public static Resources getBaseAppResources() {
		return BaseApplication.getBaseAppResources();
	}
	/**
	 * 根据图片名生成从Assets目录加载的URI
	 * 业务相关
	 */
	public static Uri getImageUriFromAssets(String imageName) {
		try {
			if(!StringUtil.isEmpty(imageName)) {
				String folderName = imageName.substring(0, 1);
				String imgFullUrl = "asset:///" + folderName.toUpperCase(Locale.US) + "/" + imageName;
				return Uri.parse(imgFullUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * 根据图片名生成从Assets目录加载的URI
	 * 业务相关
	 */
	public static Uri getImageUriFromAssetsbaoyang(String imageName) {
		try {
			if(!StringUtil.isEmpty(imageName)) {
				String folderName = imageName.substring(0, 1);
				String imgFullUrl = "asset:///baoyang/" + imageName;
				return Uri.parse(imgFullUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * 根据图片名生成从Assets目录加载的URI
	 * 业务相关
	 */
	public static Uri getImageUriFromAssetsfuwu(String imageName) {
		try {
			if(!StringUtil.isEmpty(imageName)) {
				String folderName = imageName.substring(0, 1);
				String imgFullUrl = "asset:///fuwu/" + imageName;
				return Uri.parse(imgFullUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * 设置网路图片
	 * @param imageView 需要设置网络图片的控件
	 * @param imagePath 网络图片的相对路径
	 */
	public static void setSimpleDraweeViewImage(SimpleDraweeView imageView, String imagePath) {
		SLog.d(CommonFunction.getFileLineMethod(), "image=" + Config.IMAGE_SERVER_URL + "/" + imagePath);
		//图片的相对路径不能为空
		if(imagePath != null) {
			String imageUrl = Config.IMAGE_SERVER_URL + "/" + imagePath;
			SLog.d("", "imageUrl = " + imageUrl);
			imageView.setImageURI(Uri.parse(imageUrl));
		}else {
			SLog.d("image", "image is null");
		}
	}
	/**
	 * 设置网路图片
	 * @param imageView 需要设置网络图片的控件
	 * @param imagePath 网络图片的相对路径
	 */
	public static void setSimpleDraweeViewImage_ALL(SimpleDraweeView imageView, String imagePath) {
		//图片的相对路径不能为空
		if(imagePath != null) {
			imageView.setImageURI(Uri.parse(imagePath));
		}else {
			SLog.d("image", "image is null");
		}
	}
}