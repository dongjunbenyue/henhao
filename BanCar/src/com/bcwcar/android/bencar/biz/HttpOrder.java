package com.bcwcar.android.bencar.biz;

import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.http.OkHttpHelper;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.CollectionUtil;

import android.R.string;
import android.content.Context;
import android.provider.Settings.Secure;

/**
 * 订单处理和智能保养  所有网络请求业务
 */
public class HttpOrder {
	/**
	 * 获取车辆的保养手册
	 */
	public static void getCarMaintenance(CallbackLogic callbackLogic, String token, String carId, String carMiles, String buyDate,String CityName,String CityId,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_CAR_MAINTENANCE;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token, "CarId", carId, "CarMiles", carMiles, "BuyDate", buyDate,"CityId",CityId,"CityName",CityName),context);
	}
//	Token	N	口令
//	OrderId	Y	原始订单ID*
//	PlateNumber	N	车牌号*
//	CarId	N	车型ID*
//	Miles	N	公里数*
//	Contact	N	联系人*
//	MaintenanceTime	N	保养时间，格式2015-12-12*
//	MaintenanceSpan	N	保养时段*
//	TicketId	N	抵用券ID*
//	TicketType	N	当抵用券ID不为空时必传，1-商家卡卷，2-系统卡卷==============
//	TotalPrice	N	订单金额*
//	BillFlag	N	是否需要发票，0-不需要，1-需要*
//	BillHead	Y	发票抬头，需要时必填*
//	CellPhone	N	联系电话*
//	ShopId	N	网点ID*
//	Gender	N	性别0-女，1-男*
//	AccIds	N	配件ID,多个用逗号隔开

	/**
	 * 提交详单请求
	 */
	public static void addOrder(CallbackLogic callbackLogic, String token, String plateNumber,String OrderId, String carId, String miles,
			String contact, String maintenanceTime, String MaintenanceSpan,String ticketId,String TicketType,
			String billFlag, String billHead, String cellPhone,
			String shopId, String gender,String accIds,String type,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_ADD_ORDER;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token, "PlateNumber", plateNumber, "OrderId",OrderId,"CarId", carId, "Miles", miles,
					"Contact", contact, "MaintenanceTime", maintenanceTime,"MaintenanceSpan",MaintenanceSpan, 
					"TicketId", ticketId,
					 "BillFlag", billFlag, "BillHead", billHead, "CellPhone", cellPhone,
					"ShopId", shopId, "Gender", gender, "TicketType",TicketType,"AccIds",accIds,"Type",type),context);
	}
   /**
    * 提交服务订单
    * */
	 public static void addServiceOrder(CallbackLogic callbackLogic,String Token,String CarId,String Contact,String MaintenanceTime,String MaintenanceSpan,String TicketId,
			 String TicketType,String BillFlag,String BillHead,String CellPhone,String ShopId,String Gender,String AccId,String ProjectId,String Quantity,Context context) {
	String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_ADD_SERVICEORDER;
	OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap("Token",Token,"CarId",CarId,"Contact",Contact,"MaintenanceTime",MaintenanceTime,"MaintenanceSpan",MaintenanceSpan,
			"TicketId",TicketId,"TicketType",TicketType,"BillFlag",BillFlag,"BillHead",BillHead,"CellPhone",CellPhone,"ShopId",ShopId,"Gender",Gender,"AccId",AccId,"ProjectId",ProjectId,"Quantity",Quantity,"Type","1"), context);
	}
//	
	/**
	 * 我的订单列表
	 * @param status 0-全部，1-已付款，2-待付款，3-服务中，4-待评价
	 */
	public static void getMyOrderList(CallbackLogic callbackLogic, String token, String pageNum, String pageCount, String status,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_MYORDERLIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token, "PageNum", pageNum, "PageCount", pageCount, "Status", status),context);
	}
	public static void getMyOrderList1(CallbackLogic002 callbackLogic002
	, String token, String pageNum, String pageCount, String status) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_MYORDERLIST;
		OkHttpHelper002.getInstance().addPostRequest(callbackLogic002, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token, "PageNum", pageNum, "PageCount", pageCount, "Status", status));
	}
	
	/**
	 * 订单退款
	 */
	public static void orderRefund(CallbackLogic callbackLogic, String token, String orderId, String returnReason,Context context,String type) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_ORDER_REFUND;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token, "OrderId", orderId, "ReturnReason", returnReason,"Type",type),context);
	}
	/**
	 * 取消订单
	 */
	public static void cancelOrder(CallbackLogic callbackLogic, String token, String orderId, String deletedReason,Context context,String type) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_CANCEL_ORDER;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token, "OrderId", orderId, "DeletedReason", deletedReason,"Type",type),context);
	}
	/**
	 * 订单评价
	 */
	public static void orderEvaluate(CallbackLogic callbackLogic, String token, String orderId,
			String orderScore, String orderComment, String orderPics,String CommentFlag,Context context,String type) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_ORDER_EVALUATE;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token, "OrderId", orderId, "OrderScore", orderScore,
						"OrderComment", orderComment, "OrderPics", orderPics,"CommentFlag",CommentFlag,"Type",type),context);
	}
	
	
	
	/**
	 * 获取保养项目价格信息(APP)
	 */
	public static void getCarProPriceList(CallbackLogic callbackLogic, String token, String CarId,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_CARPROPRICELIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token, "CarId", CarId),context);
	}
//	
//	/**
//	 * 意见反馈
//	 */
//	public static void issueFeedback(CallbackLogic callbackLogic, String token, String content, String picUrl) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_ISSUE_FEEDBACK;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "Content", content, "PicUrl", picUrl));
//	}
//	
	/**
	 * 获取微信支付的支付Id和签名(APP)
	 */
	public static void getPrePayId(CallbackLogic callbackLogic, String token,String orderId,String PayType,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_PREPAYID;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
						"Token", token,"OrderId",orderId,"PayType",PayType),context);
	}
	/**
	 * 提交已经支付订单
	 */
	public static void commitOrder(CallbackLogic callbackLogic,String token,String orderId,String way,String type,Context context) {
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_COMMIT_ORDER;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap("Token",token,"OrderId",orderId,"PayType",way,"Type",type),context);
		
	}
	public static void getMyOrder(CallbackLogic callbackLogic,String token,String OrderId,Context context){
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_GET_ORDERDETAIL;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap("Token",token,"OrderId",OrderId),context);
		
		
	}
	/**
	 * 服务流程
	 */
	public static void getService(CallbackLogic callbackLogic,String token,String OrderId,Context context,String Type){
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_GET_SERVICE;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap("Token",token,
				"OrderId",OrderId,"Type",Type),context);
	}
	public static void getorderreturn(CallbackLogic callbackLogic,String Token,String OrderId,Context context) {
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.GETORDERRETURNRECORDLIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap("Token",Token,"OrderId",OrderId), context);
	}
	/**
	 * 追加项目价格
	 */
	public static void getZhuiJia(CallbackLogic callbackLogic,String token,String OrderId,String CarId,Context context){
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_ZHUIJIA;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap("Token",token,
				"OrderId",OrderId,"CarId",CarId),context);
	}
	/**
	 * 邀请人数排名
	 */
	public static void getRank(CallbackLogic callbackLogic,String token,Context context){
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_GET_RANK;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap("Token",token),context);
		
	}
		
	}
