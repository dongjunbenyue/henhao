package com.bcwcar.android.bencar.biz;

import java.util.HashMap;
import java.util.Map;

import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.http.OkHttpHelper;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.CollectionUtil;

import android.content.Context;
import io.rong.message.StickerMessage;

/**
 * 我的钱包 所有网络请求业务
 */
public class HttpDiscover {

	/**
	 * 获取增值服务网点列表 TODO
	 */

	// Token Y 口令
	// PageNum N 请求页数
	// PageCount N 每页记录数
	// CarId Y 车辆Id
	// DistrictId Y 区域Id
	// CityId Y 城市ID
	// CityName Y 城市名称
	// KeyWord Y 搜索关键字，按网点名称
	// SortType N 排序类型，1-距离，2-评分，3-评论数
	// OrderType N 排序方式，1-由低到高，2-由高到低
	// Longitude N 当前纬度
	// Latitude N 当前经度
	// ServiceType N "
	//1 钣金喷漆
	// 2 镀金镀膜
	// 3 室内美容
	// 4 汽车贴膜
	// 5 抛光打蜡
	// 6 汽车精品
	// 7 抵用券
	// 8 其他
	// 0全部"

	public static void getServiceShopList(CallbackLogic callbackLogic, String Token, String PageNum, String PageCount,
			String CarId, String DistrictId,String CityId, String CityName, String KeyWord, String SortType, String OrderType,
			String Longitude, String Latitude, String ServiceType, Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_SERVICESHOPLIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", Token, "PageNum", PageNum, "PageCount", PageCount,
						"CarId",CarId,"DistrictId",DistrictId, "CityId", CityId, "CityName", CityName, "KeyWord", KeyWord, "SortType", SortType,
						"OrderType", OrderType, "Longitude", Longitude, "Latitude", Latitude, "ServiceType",
						ServiceType),
				context);
	}
	public static void getServiceShopList002(CallbackLogic002 callbackLogic, String Token, String PageNum, String PageCount,
			String CarId, String DistrictId,String CityId, String CityName, String KeyWord, String SortType, String OrderType,
			String Longitude, String Latitude, String ServiceType) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_SERVICESHOPLIST;
		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", Token, "PageNum", PageNum, "PageCount", PageCount,
						"CarId",CarId,"DistrictId",DistrictId, "CityId", CityId, "CityName", CityName, "KeyWord", KeyWord, "SortType", SortType,
						"OrderType", OrderType, "Longitude", Longitude, "Latitude", Latitude, "ServiceType",
						ServiceType));
	}
	/**
	 * 获取服务详情
	 * */
//	Token	Y	口令
//	AccId	N	服务项目ID
//	Longitude	N	经度
//	Latitude	N	纬度

	public static void getServiceDetail(CallbackLogic callbackLogic, String Token, String AccId, String Longitude,
			String Latitude,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_SERVICEDETAIL;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", Token, "AccId", AccId, "Longitude", Longitude,
						 "Latitude",Latitude),
				context);
	}
	
	
	
	/**
	 * 获取服务评论
	 * **/
//	Token	Y	口令
//	AccId	N	服务项目ID
//	PageNum	N	请求页数
//	PageCount	N	每页记录数


	public static void getServiceComment(CallbackLogic callbackLogic, String Token, String AccId, String PageNum,
			String PageCount,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_SERVICECOMMENT;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", Token, "AccId", AccId, "PageNum", PageNum,
						"PageCount", PageCount),
				context);
	}
}