package com.bcwcar.android.bencar.biz;

import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.http.OkHttpHelper;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.CollectionUtil;

import android.content.Context;

/**
 * 我的用户资料  所有网络请求业务
 */
public class HttpUserInfo {
	public static void getVersion(CallbackLogic callbackLogic,String token,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETVERSION;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,CollectionUtil.createStringMap(
						"Token", token),context);
	}
	public static void getRewardInfo(CallbackLogic callbackLogic,String token,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GETREWARDINFO;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,CollectionUtil.createStringMap(
						"Token", token),context);
	}
	
		public static void getShopShowInfo(CallbackLogic callbackLogic,String token,Context context) {
			String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_SHOPSHOWINFO;
			OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,CollectionUtil.createStringMap(
							"Token", token),context);
		}
		public static void getShopShowInfo002(CallbackLogic002 callbackLogic,String token,String Type) {
			String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_SHOPSHOWINFO;
			OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,CollectionUtil.createStringMap(
							"Token", token,"Type",Type));
		}

		public static void getMessageInfo(CallbackLogic callbackLogic,String token,Context context) {
			String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_MESSAGEINFO;
			OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,CollectionUtil.createStringMap(
							"Token", token),context);
		}
		
	public static void getUserDetail(CallbackLogic callbackLogic, String token, String userId,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_USER_DETAIL;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token, "UserId", userId),context);
	}
	public static void updateUserInfo(CallbackLogic callbackLogic, String token,
			String nickName, String carUrl, String carDesc, String iconUrl, String gender,
			String birthDate,  String provinceId, String provinceName,
			String cityId, String cityName, String job, String signatrue,String BackGroundIcon,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_UPDATE_USERINFO;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token, "NickName", nickName, "CarUrl", carUrl, "CarDesc", carDesc,
						"IconUrl", iconUrl, "Gender", gender, "BirthDate", birthDate, 
						"ProvinceId", provinceId, "ProvinceName", provinceName, "CityId", cityId, "CityName", cityName,
						"Job", job, "Signatrue", signatrue,"BackGroundIcon",BackGroundIcon),context);
	}
	public static void addAddress(CallbackLogic callbackLogic, String token, String contact, String contactPhone,
			String address, String zipCode, String provinceId, String provinceName, String cityId, String cityName,
			String districtId, String districtName,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_ADD_ADDRESS;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token, "Contact", contact, "ContactPhone", contactPhone, "Address", address,
						"ZipCode", zipCode, "ProvinceId", provinceId, "ProvinceName", provinceName,
						"CityId", cityId, "CityName", cityName, "DistrictId", districtId, "DistrictName", districtName),context);
	}
//	
	public static void addAddress001(CallbackLogic callbackLogic, String token, String contact, String contactPhone,
			String address, String zipCode, String provinceId, String provinceName, String cityId, String cityName,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_ADD_ADDRESS;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token, "Contact", contact, "ContactPhone", contactPhone, "Address", address,
						"ZipCode", zipCode, "ProvinceId", provinceId, "ProvinceName", provinceName,
						"CityId", cityId, "CityName", cityName),context);
	}
	public static void updateAddress(CallbackLogic callbackLogic, String token, String addressId, String contact, String contactPhone,
			String address, String zipCode, String provinceId, String provinceName, String cityId, String cityName,
			String districtId, String districtName, String defaultFlag,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_UPDATE_ADDRESS;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token, "AddressId", addressId, "Contact", contact, "ContactPhone", contactPhone, "Address", address,
						"ZipCode", zipCode, "ProvinceId", provinceId, "ProvinceName", provinceName,
						"CityId", cityId, "CityName", cityName, "DistrictId", districtId, "DistrictName", districtName, "DefaultFlag", defaultFlag),context);
	}
	public static void deleteAddress(CallbackLogic callbackLogic, String token, String addressId,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_DELETE_ADDRESS;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token, "AddressId", addressId),context);
	}
	public static void getAddressList(CallbackLogic callbackLogic, String token, String userId,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_ADDRESSLIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token, "UserId", userId),context);
	}
	public static void getAddressDetail(CallbackLogic callbackLogic, String token, String addressId,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_ADDRESSDETAIL;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token, "AddressId", addressId),context);
	}
//	public static void getServiceNetwork(CallbackLogic002 callbackLogic002,String token,String brandId,String cityName) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETSERVICENETWORK;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic002, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "BrandId", brandId,"CityName",cityName));
//		
//	}
	public static void getUserShopInfo(CallbackLogic callbackLogic,String token,String lat,String lon,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETKEFUGUISHU;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token,"Latitude",lat,"Longitude",lon),context);
	}
	/**
	 * 提交用户归属
	 * */
	public static void updateCustomerBelongShop(CallbackLogic callbackLogic,String token,String shopId,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_UPDATECUSTOMERBELONGSHOP;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token,"ShopId",shopId),context);
	}
	public static void getEmployee(CallbackLogic callbackLogic,String token,String EmployeeId,Context context){
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_EMDETAIL;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, 
				CollectionUtil.createStringMap("Token",token,"EmployeeId",EmployeeId),context);
	}
	public static void scoreToEmployee(CallbackLogic callbackLogic002,String token,String EmployeeId,String Score,String Mark,Context context){
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_EMSCORE;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic002, baseUrl, 
				CollectionUtil.createStringMap("Token",token,"EmployeeId",EmployeeId,"Score",Score,"Mark",Mark),context);
	}
//	public static void getRongim(CallbackLogic002 callbackLogic002 ,String userId,String userName,String portraitUri){
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_RONGIM;
//		OkHttpHelper002.getInstance().addPostRequest1(callbackLogic002, baseUrl, CollectionUtil.createStringMap("userId",userId,"userName",userName,"portraitUri",portraitUri));
//	}
	
}