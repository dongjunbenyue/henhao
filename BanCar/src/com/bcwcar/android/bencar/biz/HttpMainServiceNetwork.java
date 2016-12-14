package com.bcwcar.android.bencar.biz;

import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.http.OkHttpHelper;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.squareup.okhttp.Callback;

import android.content.Context;

/**
 * 服务网点  所有网络请求业务
 */
public class HttpMainServiceNetwork {
//	/**
//	 * 获取已审核的服务网点列表, cityId必选，brandId和districtId以及recommendString可选。
//	 */

//	Token	Y	口令
//	PageNum	N	请求页数
//	PageCount	N	每页记录数
//	BrandId	Y	品牌Id
//	DistrictId	Y	区域Id
//	CityId	Y	城市ID
//	CityName	Y	城市名称
//	KeyWord	Y	搜索关键字，按网点名称
//	SortType	N	排序类型，1-时间，2-距离，3-评分，4-评论数
//	OrderType	N	排序方式，1-由低到高，2-由高到低
//	Longitude	N	当前纬度
//	Latitude	N	当前经度
	

	public static void getShopList(CallbackLogic callbackLogic,
			String token, String pageNum, String pageCount,
			String BrandId, String DistrictId,String CityId,String CityName,String KeyWord,String SortType,String OrderType,
			String latitude,String longitude,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_SHOP_LIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token, "PageNum", pageNum, "PageCount", pageCount,
						"BrandId", BrandId, "DistrictId", DistrictId, "CityId", CityId,"CityName",CityName, "KeyWord", KeyWord,"SortType",SortType,"OrderType",OrderType,
						 "Latitude", latitude,"Longitude", longitude),context);
	}
	public static void getShopList002(CallbackLogic002 callbackLogic,
			String token, String pageNum, String pageCount,
			String BrandId, String DistrictId,String CityId,String CityName,String KeyWord,String SortType,String OrderType,
			String latitude,String longitude ) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_SHOP_LIST;
		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token, "PageNum", pageNum, "PageCount", pageCount,
						"BrandId", BrandId, "DistrictId", DistrictId, "CityId", CityId,"CityName",CityName, "KeyWord", KeyWord,"SortType",SortType,"OrderType",OrderType,
						 "Latitude", latitude,"Longitude", longitude));
	}
//
//	public static void getShopList001(CallbackLogic callbackLogic,String token) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_SHOP_LIST;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token,"CityId", "ac0b0f70-9252-11e5-9246-f079592f54ca","Longitude", "28.12", "Latitude", "112.59"));
//	}
	/**
	 * 获取可保养的网点列表
	 */
//	Token	Y	口令
//	PageNum	N	请求页数
//	PageCount	N	每页记录数
//	CarId	N	车型Id
//	KeyWord	Y	搜索关键字，按网点名称
//	CityId	Y	城市ID
//	CityName	Y	城市名称
//	Longitude	N	当前纬度
//	Latitude	N	当前经度
	//AccIds 配件ids

	public static void getShowShopListByCar(CallbackLogic callbackLogic,String token, String AccIds,String pageNum, String pageCount,
			String CarId, String KeyWord,String CityId,String CityName,String Longitude,String Latitude,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETSHOWSHOPLISTBYCAR;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "Token", token, "AccIds",AccIds,"PageNum", pageNum, "PageCount", pageCount,
						"CarId", CarId, "KeyWord", KeyWord,"CityId",CityId,"CityName",CityName,"Longitude",Longitude,"Latitude",Latitude),context);
	}
	/**
	 * 获取某个服务网点（4s店）的详情
	 */
	public static void getShopDetails(CallbackLogic callbackLogic,
			String token, String shopId,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_SHOP_DETAIL;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "ShopId", shopId),context);
	}
//	/**
//	 * 获取某个城市的所有区域列表信息
//	 */
	
	public static void getDistrictInfo(CallbackLogic callbackLogic,
			String token, String cityId,String CityName,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_DISTRICTINFO;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "CityId", cityId,"CityName",CityName),context);}
	public static void getDistrictInfo002(CallbackLogic002 callbackLogic,
			String token, String cityId,String CityName) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_DISTRICTINFO;
		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "CityId", cityId,"CityName",CityName));}
	/**
	 * 增加网点
	 */
	public static void addShop(CallbackLogic callbackLogic,
			String token,String cityName,String cityId,String brandId,
			String brandName, String contactName, String contactPhone,Context context){
		 String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_ADD_SHOP;
		 OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, 
				 CollectionUtil.createStringMap("Token",token,"CityName",cityName,"CityId",cityId,
						 "BrandName",brandName,"ContactName",contactName,"ContactPhone",contactPhone),context);
		
	}
//	/*点赞
//	 * 
//	 */
//	public static void praiseShop(CallbackLogic002 callbackLogic,String token,String BusinessId){
//		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_ADD_SHOPPRAISE;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap("Token",token,
//				"BusinessId",BusinessId));
//	}
	/**
	 * 网点评论列表
	 */
	public static void getshopPinglun(CallbackLogic callbackLogic,String token,String PageNum,String PageCount,String ShopId,Context context){
		
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_GET_SHOPPINGLUN;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap(
				"Token",token,"PageNum",PageNum,"PageCount",PageCount,"ShopId",ShopId),context);
	}
//	public static void shopPinglun002(CallbackLogic002 callbackLogic002,String token,String PageNum,String PageCount,String ShopId) {
//		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_GET_SHOPPINGLUN;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic002, baseUrl,CollectionUtil.createStringMap(
//				"Token",token,"PageNum",PageNum,"PageCount",PageCount,"ShopId",ShopId));
//		
//	}
//	
// 
	
	/**
	 * 获取发现卡卷列表
	 */
//	参数名	是否可空	参数说明
//	Token	N	口令
//	CityId	Y	城市ID
//	CityName	Y	城市名称
//	PageNum	N	请求页数
//	PageCount	N	每页记录数
	
	public static void getAllShopTicketList(CallbackLogic callbackLogic,String token,String CityId,String CityName,String PageNum,String PageCount,Context context){
		
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_GET_ALLSHOPTICKETLIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap(
				"Token",token,"CityId",CityId,"CityName",CityName,"PageNum",PageNum,"PageCount",PageCount),context);
	}
	
}