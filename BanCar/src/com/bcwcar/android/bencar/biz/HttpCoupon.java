package com.bcwcar.android.bencar.biz;

import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.http.OkHttpHelper;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;

import android.content.Context;

/**
 * 抵用券处理  所有网络请求业务
 */
public class HttpCoupon {
	/**
	 * 我的抵用券列表
	 * TODO
	 */
	public static void getUserTicketList(CallbackLogic callbackLogic, String token, String pageNum, String pageCount,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_USERTICKET_LIST;
		
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap(
			"Token", token, "PageNum", pageNum, "PageCount", pageCount),
				context);
		
	}
	
	
	
	public static void getMyCouponList(CallbackLogic callbackLogic, String token, String pageNum, String pageCount, String status,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_MYORDERLIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token, "PageNum", pageNum, "PageCount", pageCount, "Status", status),context);
	}
	
	/**
	 * 领取已开通城市的未开通品牌的抵用券
	 */
public static void getMyCoupon(CallbackLogic callbackLogic,String Token,String CityId,String CityName,String BrandId,String UserMobile,String UserPass,Context context) {
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_GET_GETMYCOUPU;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
				"Token", Token, "CityId", CityId, "CityName", CityName, "BrandId", BrandId,"UserMobile",UserMobile,"UserPass",UserPass),
				context);
};
	}
