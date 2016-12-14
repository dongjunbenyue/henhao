package com.bcwcar.android.bencar.biz;

import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.http.OkHttpHelper;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;

import android.content.Context;

/**
 * 我的  所有网络请求业务
 */
public class HttpMainMy {
//	/**
//	 * 获取保养项目列表
//	 * @param string 
//	 */
//	public static void getProjectList(CallbackLogic callbackLogic, String token, String string) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_PROJECTLIST;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token,"CarId",string));
//	}
//	/**
//	 * 获取保养轨迹列表
//	 */
//	public static void getTraceList(CallbackLogic callbackLogic, String token,
//			String carId, String pageNum, String pageCount) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_TRACELIST;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//					"Token", token, "CarId", carId, "PageNum", pageNum, "PageCount", pageCount));
//	}
//	public static void  getTraceList1(CallbackLogic002 callbackLogic002, String token,
//			String carId, String pageNum, String pageCount) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_TRACELIST;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic002, baseUrl,
//				CollectionUtil.createStringMap(
//					"Token", token, "CarId", carId, "PageNum", pageNum, "PageCount", pageCount));
//	}
//	/**
//	 * 获取保养轨迹详情
//	 */
//	public static void getTraceDetail(CallbackLogic callbackLogic, String token, String traceId) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_TRACEDETAIL;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "TraceId", traceId));
//	}
//	/**
//	 * 增加保养轨迹
//	 */
//	public static void addTrace(CallbackLogic callbackLogic, String token, String carId, String carMiles,
//			String maintenencePrice, String maintenenceTime, String projectIds) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_ADD_TRACE;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "CarId", carId, "CarMiles", carMiles,
//						"MaintenencePrice", maintenencePrice, "MaintenenceTime", maintenenceTime, "ProjectIds", projectIds));
//	}
//	/**
//	 * 修改保养轨迹
//	 */
//	public static void updateTrace(CallbackLogic callbackLogic, String token, String traceId,String CarId, String carMiles,
//			String maintenencePrice, String maintenenceTime, String projectIds) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_UPDATE_TRACE;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token,"TraceId", traceId, "CarId",CarId, "CarMiles", carMiles,
//						"MaintenencePrice", maintenencePrice, "MaintenenceTime", maintenenceTime, "ProjectIds", projectIds));
//	}
//	/**
//	 * 删除保养轨迹
//	 */
//	public static void deleteTrace(CallbackLogic callbackLogic, String token, String traceId) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_DELETE_TRACE;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "TraceId", traceId));
//	}
	public static void isuuefankui(CallbackLogic callbackLogic,String token,String content,Context context) {
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_ISSUE_FEEDBACK;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap(
				"Token",token,"Content",content),context);
		
	}
}