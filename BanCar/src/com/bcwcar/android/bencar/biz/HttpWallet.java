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
 * 我的钱包   所有网络请求业务
 */
public class HttpWallet {
	
	/**
	 * JIFEN积分商城
	 * TODO
	 */
	public static void getDuiBaLoginUrl(CallbackLogic callbackLogic, String token,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETDUIBALOGINURL;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token),context);
	}
	
	
	/**
	 * 我的抵用券列表
	 * TODO
	 */
	public static void getUserTicketList(CallbackLogic002 callbackLogic, String token, String ShopId,String PaySum) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_USERTICKET_LIST;
		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token, "ShopId",ShopId ,"PaySum",PaySum));
	}
	/**
	 * YONGHU用户签到
	 * TODO
	 */
	public static void sign(CallbackLogic callbackLogic, String token,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_SIGN;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token),context);
	}
	/**
	 * 获取用户抵用券
	 * TODO
	 */
	public static void getUserableTicket(CallbackLogic callbackLogic, String token,String TotalPrice,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETUSERABLETICKET;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token,"TotalPrice",TotalPrice),context);
	}
   /**
    * 提现详情	
    */
	public static void  getCashWithdrawDetail(CallbackLogic callbackLogic,String Token,String ApplyId,Context context){
	String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.getCashWithdrawDetail;
	      OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap("Token",Token,"ApplyId",ApplyId), context);
	}
	/**
	 * 设置提现密码
	 */
	public static void applyCashPass(CallbackLogic callbackLogic,String Token,String CashPass,Context context){
		String baString=Config.DATA_SERVER_URL+BizDefineAll.applyCustomerCashPass;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baString, CollectionUtil.createStringMap("Token",Token,"CashPass",CashPass), context);
	}
	/**
	 * 获取用户积分，现金，牛币
	 * TODO
	 */
	public static void getUserExp(CallbackLogic callbackLogic, String token,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_USEREXP;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token),context);
	}
	
	/***
	 * 获取用户银行列表(APP)
	 * 
	*/
	public static void getUserBankList(CallbackLogic callbackLogic, String token,String PageNum,String PageCount,Context context ) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_USERBANKLIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token,"PageNum", PageNum,"PageCount",PageCount),context);
	}
	
	
	/***
	 * 获取用户银行列表(APP)
	 * 
	*/
	public static void addUserBank(CallbackLogic callbackLogic, String token,String BankId,String RegBank, String BankNo,String AccountName,String IdCardNo,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_ADDUSERBANK;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap(
					"Token", token,"BankId",BankId,"RegBank",RegBank,"BankNo",BankNo,"AccountName",AccountName,"IdCardNo",IdCardNo),context);
	}
	
	
	/**
	 * 
	 * 获取银行列表信息
	 * */
	
	public static void getBankList(CallbackLogic callbackLogic, String token,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETBANKLIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token),context);
	}
	
	
//	/**
//	 * 
//	 * 获取用户银行信息
//	 * */
//	
//	public static void getUserBankInfo(CallbackLogic callbackLogic, String token,String UserBankId) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETBANKINFO;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap("Token", token,"UserBankId",UserBankId));
//	}
//	/**
//	 * 
//	 * 修改银行信息
//	 * */
//	
//	public static void updateUserBank(CallbackLogic callbackLogic, String token,String BankId,String RegBank,String BankNo,String AccountName,String IdCardNo,String DefaultFlag,String UserBankId) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_UPDATEUSERBANK;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap("Token", token,"BankId",BankId,"RegBank", RegBank,"BankNo",BankNo,"AccountName", AccountName,"IdCardNo",IdCardNo,"DefaultFlag", DefaultFlag,"UserBankId",UserBankId));
//	}
//	
	/**
	 * 删除银行信息
	 * */
	
	public static void deleteUserBank(CallbackLogic callbackLogic, String token,String UserBankId,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_DELETEUSERBANK;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token,"UserBankId",UserBankId),context);
	}
	
	/**
	 * 
	 * 获取用户钱包列表
	 * */
	
	public static void getUserWalletList(CallbackLogic callbackLogic, String token,String PageNum, String PageCount,String QueryDate,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETUSERWALLETLIST;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token,"PageNum",PageNum,"PageCount",PageCount,"QueryDate",QueryDate),context);
	}
	
	/**
	 * 
	 * 提现申请
	 * */
	
	public static void applyCashWithdraw(CallbackLogic002 callbackLogic, String token,String BankId, String CashSum,String CashPass,Context context) {
		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_APPLYCASHWITHDRAW;
		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
				CollectionUtil.createStringMap("Token", token,"BankId",BankId,"CashSum",CashSum,"CashPass",CashPass));
	}
	
	
//	/**
//	 * 
//	 * 我的邀请人列表(APP)
//	 * */
//	
//	public static void getInvitedFriendsList(CallbackLogic002 callbackLogic, String token,String PageNum, String PageCount) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETINVITEDFRIENDSLIST;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap("Token", token,"PageNum",PageNum,"PageCount",PageCount));
//		
//	}
//	
//	/**
//	 * 
//	 * 获取用户积分列表(APP)
//	 * */
//	
//	public static void getUserScoreList(CallbackLogic002 callbackLogic, String token,String PageNum, String PageCount,String QueryDate) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETUSERSCORELIST;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap("Token", token,"PageNum",PageNum,"PageCount",PageCount,"QueryDate",QueryDate));
//		
//	}
//	/**
//	 * 
//	 * 获取用户牛币列表(APP)
//	 * */
//	
//	public static void getUserCurrencyList(CallbackLogic002 callbackLogic, String token,String PageNum, String PageCount,String QueryDate) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETUSERCURRENCYLIST;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap("Token", token,"PageNum",PageNum,"PageCount",PageCount,"QueryDate",QueryDate));
//		
//	}
//	
//	
//	/**
//	 * 
//	 * 积分兑换牛币(APP)
//	 * */
//	
//	public static void exchangeScoreToCoin(CallbackLogic002 callbackLogic, String token,String ExchangeScore) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_EXCHANGESCORETOCOIN;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap("Token", token,"ExchangeScore",ExchangeScore));
//		
//	}
//	/**
//	 * 
//	 * 牛币兑换现金(APP)
//	 * */
//	
//	public static void exchangeCoinToCash(CallbackLogic002 callbackLogic, String token,String ExchangeCurrency) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_EXCHANGECOINTOCASH;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap("Token", token,"ExchangeCurrency",ExchangeCurrency));
//		
//	}
//	
//	/**
//	 * 
//	 * 礼物列表(APP)
//	 * */
//	
//	public static void getPresentList(CallbackLogic002 callbackLogic, String token) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_GETPRESENTLIST;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap("Token", token));
//		
//	}
//	/**
//	 * 
//	 * 积分兑换礼物
//	 * */
//	
//	public static void exchangeScoreToPresent(CallbackLogic002 callbackLogic, String token,String AddressId,String AddressName,String PresentCode) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_EXCHANGESCORTOPRESENT;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap("Token", token,"AddressId",AddressId,"AddressName",AddressName,"PresentCode",PresentCode));
//		
//	}
//	
	/**
	 * 兑奖
	 */
	public static void cheatmoneyduijiang(CallbackLogic002 callbackLogic002,String token,String AddressId) {
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.API_GET_LINGJIANG;
		OkHttpHelper002.getInstance().addPostRequest(callbackLogic002, baseUrl,	CollectionUtil.createStringMap("Token", token,"AddressId",AddressId));
		
	}
	public static void applyCustomerCashPass(CallbackLogic callbackLogic,String Token,String CashPass,Context context){
		String baString=Config.DATA_SERVER_URL+BizDefineAll.applyCustomerCashPass;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baString, CollectionUtil.createStringMap("Token",Token,"CashPass",CashPass), context);
		
	}
	/**
	 * 充值
	 */
	public static void recharge(CallbackLogic callbackLogic,String Token,String TotalSum,String PayType,Context context) {
		String baseurl=Config.DATA_SERVER_URL+BizDefineAll.RECHARGE;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseurl, CollectionUtil.createStringMap("Token",Token,"TotalSum",TotalSum,"PayType",PayType), context);
		
	}
	/**
	 * 充值确认
	 */
	public static void confirmrecharge(CallbackLogic callbackLogic,String Token,String RechargeId,String PayType,Context context) {
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.CONFIRMRECHARGE;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, CollectionUtil.createStringMap("Token",Token,"RechargeId",RechargeId,"PayType",PayType), context);
		
	}
	
	/**
	 * 购买卡券
	 */
//	Token	N	口令
//	PaySum	N	付款金额，正整数
//	PayType	N	1-微信，2-支付宝
//	ShopTicketId	N	卡卷ID

	public static void buyUserTicket(CallbackLogic callbackLogic,String Token,String PaySum,String PayType,String ShopTicketId,Context context) {
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.BUYUSERTICKET;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, 
				CollectionUtil.createStringMap("Token",Token,
						"PaySum",PaySum,
						"PayType",PayType,
						"ShopTicketId",ShopTicketId
						),
				context);
		
	}
	
	
	/**
	 * 确认购买卡券
	 * **/
//	Token	N	口令
//	UserTicketId	N	用户卡卷ID
//	PayType	N	付款方式，1-微信，2-支付宝

	public static void confirmUserTicket(CallbackLogic callbackLogic,String Token,String UserTicketId,String PayType,Context context) {
		String baseUrl=Config.DATA_SERVER_URL+BizDefineAll.CONFIRMUSERTICKET;
		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl, 
				CollectionUtil.createStringMap("Token",Token,
						"PayType",PayType,
						"UserTicketId",UserTicketId
						),
				context);
		
	}
	
}