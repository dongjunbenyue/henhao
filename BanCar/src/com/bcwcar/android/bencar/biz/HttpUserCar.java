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
 * 用户车型 所有网络请求业务
 */
public class HttpUserCar {
	/**
	 * 车型品牌列表
	 */
	public static void brandinfo(CallbackLogic callbackLogic, Context context,String token,String CityId,String CityName) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_BRANDINFO;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "CityId", CityId,"CityName",CityName),context);
	}
	/**
	 * SUOYOU 车型品牌列表
	 */
	public static void getBrandList(CallbackLogic callbackLogic, String token,String CityId,String CityName,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GETBRANDLIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "CityId", CityId,"CityName",CityName),context);
	}
	/**
	 * 车系信息
	 */
	public static void seriesinfo(CallbackLogic callbackLogic, String token, String brandId,String CityFlag,String CityId,String CityName,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_SERIESINFO;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "BrandId", brandId,"CityFlag",CityFlag, "CityId", CityId,"CityName",CityName),context);
	}
//   /**
//    * vin码 
//    */
//	public static void getVin(CallbackLogic003 callbackLogic003,String key1){
//		String baseUrl="http://getvin.api.juhe.cn/CarManagerServer/getVINFormat";
//		OkHttpHelper003.getInstance().addPostRequest(callbackLogic003, baseUrl,
//          CollectionUtil.createStringMap("VIN",key1,"key","db0109095c7020b6c62ab86c2db9b23f"));
//	}
	/**
	 * 车型信息
	 */
	public static void carinfo(CallbackLogic callbackLogic, String token, String seriesId,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_CARINFO;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "SeriesId", seriesId),context);
	}

	/**
	 * 增加新车
	 */
	public static void addUserCar(CallbackLogic callbackLogic, String token, String carId, String carMiles,
			String buyDate,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_ADD_USERCAR;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap("Token", token,
				"CarId", carId, "CarMiles", carMiles, "BuyDate", buyDate),context);
	}
	
	/**
	 * 修改用户车型
	 */
	public static void updateUserCar(CallbackLogic callbackLogic, String token, String userCarId, String carId,
			String carMiles, String buyDate,String DefaultFlag,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_UPDATE_USERCAR;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "UserCarId", userCarId, "CarId", carId, "CarMiles",
						carMiles, "BuyDate", buyDate,"DefaultFlag",DefaultFlag),context);
	}

	/**
	 * 获取用户默认车型信息
	 */
	public static void getUserCarList(CallbackLogic callbackLogic, String token, String pageNum, String pageCount,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_USERCARLIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "PageNum", pageNum,"PageCount", pageCount),context);
	}
	/**
	 * 获取用户默认车型列表
	 */
	public static void getUserDefaultCarInfo(CallbackLogic callbackLogic, String token,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_USERDEFAULTCARINFO;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token),context);
	}
	public static void getUserDefaultCarInfo002(CallbackLogic002 callbackLogic, String token) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_USERDEFAULTCARINFO;
		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token));
	}
	/**
	 * 删除用户删除用户车型
	 * 
	 */
	public static void deleteUserCar(CallbackLogic CallbackLogic, String token, String UserCarId,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_DELETE_USERCAR;
		OkHttpHelper.getInstance().addPostRequest(CallbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "UserCarId", UserCarId),context);
	}
	
	/**
	 * 热门品牌
	 * 
	 */
	public static void getHotBrandList(CallbackLogic CallbackLogic, String token, String CityName,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GETHOTBRANDLIST;
		OkHttpHelper.getInstance().addPostRequest(CallbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "CityName", CityName),context);
	}
	public static void getHotBrandList002(CallbackLogic002 CallbackLogic, String token, String CityName ) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GETHOTBRANDLIST;
		OkHttpHelper002.getInstance().addPostRequest(CallbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token, "CityName", CityName));
	}
}