package com.bcwcar.android.bencar.biz;

/**
 * 业务需要的一些常量全在这里定义
 */
public class BizDefineAll {
	public static final String BIZ_ACTION = "action";
	//以下是HTTP业务请求的返回值KEY定义
	public static final String BIZ_RESPONSE_CODE = "ResponseCode";
	public static final String BIZ_RESPONSE_DESCRIPTION = "ResponseDescription";
	public static final String BIZ_RESPONSE_DATA = "Data";
	//以下是HTTP业务请求的返回值VALUE定义
	public static final String BIZ_JSON_ERROR = "-1";
	public static final String BIZ_SUCCESSFUL = "0";
	public static final String BIZ_FAILING = "1";
	
	public static final String DEVICE_TYPE = "android";
	//以下是SharedPreferences业务的存储字段

	//以下是SharedPreferences业务的存储字段

	/***
	 * 用户钱包数据字段
	 * */
	public static final String USER_WALLET_DATA = "userwallet";
	public static final String USER_WALLET_DATA_ALL= "userwallet_all";
	/***
	 * 用户银行卡数据
	 * */
	public static final String USER_BANK_DATA = "userbank";
	public static final String USER_BANK_DATA_ALL= "userbank_all";
	/***
	 * 银行卡列表数据
	 * */
	public static final String BANK_LIST= "banklist";
	public static final String BANK_LIST_ALL= "banklist_all";
	/***
	 * 提现数据
	 * */
	public static final String WITHDRAW= "withdraw";
	
	
		/**
		 * 用户数据及字段
		 * **/
		public static final String USER_DATA = "userinfo";
		public static final String USER_DATA_ALL= "user_all";
		public static final String USER_DATA_TO_TOKEN= "userinfototoken";
		public static final String USER_DATA_ALL_TO_TOKEN= "user_all_totoken";
	
	/**
	 * 每页加载数量
	 */
	public static final String PAGESIZE = "5"; 
	
	
	////////////////////////////登录注册  所有API接口定义/////////////////////////////////////
	/**获取版本号*/
	public static final String API_GET_GETVERSION ="/app/android/getVersion";
	/**获取验证码*/
	public static final String API_GET_CHECKCODE = "/app/getBcwCarCheckCode";
	/**判断验证码是否正确*/
	public static final String API_IS_CHECKCODE_RIGHT = "/app/isCheckCodeRight";
	public static final String IS_CHECKCODE_RIGHT_CODETYPE_REG = "0";				//注册
	public static final String IS_CHECKCODE_RIGHT_CODETYPE_FIND_PWD = "1";			//找回密码
	public static final String IS_CHECKCODE_RIGHT_CODETYPE_OTHER = "2";				//其它
	
	/**注册*/
	public static final String API_TO_REG = "/app/user/toReg";
	/**登录*/
	public static final String API_TO_LOGIN = "/app/user/toLogin";
	/**找回密码*/
	public static final String API_FIND_PWD = "/app/user/findPwd";
	/**修改密码*/
	public static final String API_PWD_RESET = "/app/user/pwdReset";
	/***
	 * /app/sys/getRewardInfo
	 * **/
	public static final String API_GETREWARDINFO= "/app/sys/getRewardInfo";
	
	////////////////////////////我的用户资料  所有API接口定义/////////////////////////////////////
	
	/**网点活动展示列表*/
	public static final String API_GET_SHOPSHOWINFO= "/app/shop/getShopShowInfo";
	/**
	 * /app/sys/getMessageInfo
	 * 
	 * ***/
	public static final String API_GET_MESSAGEINFO= "/app/sys/getMessageInfo";
	/**获得用户详细资料*/
	public static final String API_GET_USER_DETAIL = "/app/user/getUserDetail";
	/**更新用户资料*/
	public static final String API_UPDATE_USERINFO = "/app/user/updateUserInfo";
	/**上传图片*/
	public static final String API_UPLOAD_PICTURE = "/app/file/uploadPicture";
	/**新增收货地址*/
	public static final String API_ADD_ADDRESS = "/app/userAddress/addAddress";
	/**修改收货地址*/
	public static final String API_UPDATE_ADDRESS = "/app/userAddress/updateAddress";
	/**删除收货地址*/
	public static final String API_DELETE_ADDRESS = "/app/userAddress/deleteAddress";
	/**获取收货地址列表*/
	public static final String API_GET_ADDRESSLIST = "/app/userAddress/getAddressList";
	/**获取收货地址详情*/
	public static final String API_GET_ADDRESSDETAIL = "/app/userAddress/getAddressDetail";
	
	////////////////////////////我的  所有API接口定义/////////////////////////////////////
	/**获取车辆的保养手册*/
	public static final String API_GET_CAR_MAINTENANCE = "/app/car/getCarMaintenance";
	/**获取保养项目列表*/
	public static final String API_GET_PROJECTLIST = "/app/car/getProjectList";
	/**获取保养轨迹列表*/
	public static final String API_GET_TRACELIST = "/app/trace/getTraceList";
	/**获取保养轨迹详情*/
	public static final String API_GET_TRACEDETAIL = "/app/trace/getTraceDetail";
	/**新增保养轨迹*/
	public static final String API_ADD_TRACE = "/app/trace/addTrace";
	/**修改保养轨迹*/
	public static final String API_UPDATE_TRACE = "/app/trace/updateTrace";
	/**删除保养轨迹*/
	public static final String API_DELETE_TRACE = "/app/trace/deleteTrace";
	/**意见反馈*/
	public static final String API_ISSUE_FEEDBACK = "/app/user/issueFeedback";
	
	////////////////////////////订单处理  所有API接口定义/////////////////////////////////////
	/**订单列表*/
	public static final String API_GET_MYORDERLIST = "/app/order/getMyOrderList";
	/**订单详情*/
	public static final String API_GET_ORDERDETAIL = "/app/order/getOrder";
	/**新增订单*/
	public static final String API_ADD_ORDER = "/app/order/addOrder";
	/**新增服务订单**/
	public static final String API_ADD_SERVICEORDER="/app/order/addServiceOrder";
	/**提交已付款订单*/
	public static final String API_COMMIT_ORDER="/app/order/pay";
	/**获得追加项目价格*/
	public static final String API_ZHUIJIA="/app/price/getOtherCarProPriceList";
	/**订单退款*/
	public static final String API_ORDER_REFUND = "/app/order/return";
	/**取消订单*/
	public static final String API_CANCEL_ORDER = "/app/order/deleteOrder";
	/**订单评价*/
	public static final String API_ORDER_EVALUATE = "/app/order/updateOrderScore";
	/**保养手册信息*/
	public static final String API_GET_CARMAINTENANCE = "/app/car/getCarMaintenance";
	/**获取微信支付的支付Id和签名(APP)*/
	public static final String API_GET_PREPAYID = "/app/pay/getPrePayId";
	/**   获取保养项目价格信息(APP)   */
	public static final String API_GET_CARPROPRICELIST = "/app/price/getCarProPriceList";
	/** 退款流程**/
	public static final String    GETORDERRETURNRECORDLIST="/app/order/getOrderReturnRecordList";
	////////////////////////////网点信息  所有API接口定义/////////////////////////////////////
	/**获取已审核的服务网点列表*/
	public static final String API_GET_SHOP_LIST = "/app/shop/getShowShopList";
	/**获取已审核的某个服务网点详情*/
	public static final String API_GET_SHOP_DETAIL = "/app/shop/getShopDetail";
	/**获取已审核的某个城市的区域列表*/
	public static final String API_GET_DISTRICTINFO = "/app/shop/getDistrictInfo";
	/**获取获取可保养的网点列表列表*/
	public static final String API_GET_GETSHOWSHOPLISTBYCAR = "/app/shop/getShowShopListByCar";
   /**增加网点**/
    public static final String API_ADD_SHOP="/app/shop/addShop";
    /**网点点赞
     * 
     */
    public static final String API_ADD_SHOPPRAISE="/app/shop/praise";
    /**
     * 服务网点评论列表
     */
    public static final String API_GET_SHOPPINGLUN="/app/shop/getShopFeedbackList";
    /***/
    public static final String API_GET_SERVICE="/app/order/getOrderTrace";
    
    /**
     * 获取所有网点的券
     * */
   public static final String API_GET_ALLSHOPTICKETLIST="/app/ticket/getAllShopTicketList";
	
	///////////////////////////志同道合  所有API接口定义/////////////////////////////////////
	/**官方帖子列表，用在志同道合主页列表*/
	public static final String API_GET_OFFICIAL_ARTICLELIST = "/app/social/getOfficialArticleList";
	/**官方帖子详情*/
	public static final String API_GET_OFFICIAL_ARTICLEDETAIL = "/app/social/getOfficialArticleDetail";
	/**官方帖子回复列表*/
	public static final String API_GET_OFFICIAL_REPLYLIST = "/app/social/getOfficialReplyList";
	/**赞了指定帖子的所有人*/
	public static final String API_GET_OFFICIAL_PRAISELIST = "/app/social/getOfficialPraiseList";
	
	/**普通帖子列表，用在朋友圈*/
	public static final String API_GET_ARTICLELIST = "/app/social/getArticleList";
	/**帖子详情*/
	public static final String API_GET_ARTICLEDETAIL = "/app/social/getArticleDetail";
	/**帖子回复列表*/
	public static final String API_GET_REPLYLIST = "/app/social/getReplyList";
	/**关注的人列表*/
	public static final String API_GET_MYCONCERNLIST = "/app/social/getMyConcernList";
	/**回复我的所有帖子的所有人列表*/
	public static final String API_GET_MYREPLYLIST = "/app/social/getMyReplyList";
	/**赞我的所有帖子的所有人列表*/
	public static final String API_GET_MYPRAISELIST = "/app/social/getMyPraiseList";
	/**赞某人的指定帖子的所有人列表*/
	public static final String API_GET_PRAISELIST = "/app/social/getPraiseList";
	/**发表文章*/
	public static final String API_ISSURE_ARTICLE = "/app/social/issureArticle";
	/**回复文章*/
	public static final String API_REPLY_ARTICLE = "/app/social/replyArticle";
	/**用户关注*/
	public static final String API_CONCERN = "/app/social/concern";
	/**文章点赞*/
	public static final String API_PRAISE = "/app/social/praise";
	
	///////////////////////////用户车型  所有API接口定义/////////////////////////////////////
	/**热门品牌/app/car/getHotBrandList*/
	public static final String API_GETHOTBRANDLIST= "/app/car/getHotBrandList";
	/**车型品牌列表*/
	public static final String API_GETBRANDLIST = "/app/car/getBrandList";
	/**车型品牌列表*/
	public static final String API_BRANDINFO = "/app/car/brandinfo";
	/**车系信息*/
	public static final String API_SERIESINFO = "/app/car/seriesinfo";
	/**车型信息*/
	public static final String API_CARINFO = "/app/car/carinfo";
	/**增加新车*/
	public static final String API_ADD_USERCAR = "/app/userCar/addUserCar";
	/**修改用户车型*/
	public static final String API_UPDATE_USERCAR = "/app/userCar/updateUserCar";
	/**删除用户车型*/
	public static final String API_DELETE_USERCAR = "/app/userCar/deleteUserCar";
	/**用户车型列表*/
	public static final String API_GET_USERCARLIST = "/app/userCar/getUserCarList";
	/**用户默认车型信息*/
	public static final String API_GET_USERDEFAULTCARINFO = "/app/userCar/getUserDefaultCarInfo";
	///////////////////////////我的钱包   所有API接口定义/////////////////////////////////////
	/**获取用户抵用券列表*/
	public static final String API_GET_USERTICKET_LIST= "/app/ticket/getUserTicketList";
	/**获取用户积分，现金，牛币*/
	public static final String API_GET_USEREXP= "/app/user/getUserExp";
	
	///            用户得分
	/**提现密码**/
	public static final String applyCustomerCashPass="/app/cash/applyCustomerCashPass";
	/****提现详情****/
	public static final String getCashWithdrawDetail="/app/cash/getCashWithdrawDetail";
	/****获取用户银行列表(APP)**/
	public static final String API_GET_USERBANKLIST= "/app/userBank/getUserBankList";
	/****增加用户银行(APP)**/
	public static final String API_GET_ADDUSERBANK= "/app/userBank/addUserBank";
	/****获取银行列表信息APP)**/
	public static final String API_GET_GETBANKLIST= "/app/userBank/getBankList";
	/****获取用户银行信息**/
	public static final String API_GET_GETBANKINFO= "/app/userBank/getUserBankInfo";
	/****修改银行信息**/
	public static final String API_GET_UPDATEUSERBANK= "/app/userBank/updateUserBank";
	/****删除银行信息**/
	public static final String API_GET_DELETEUSERBANK= "/app/userBank/deleteUserBank";
	/****获取用户钱包列表**/
	public static final String API_GET_GETUSERWALLETLIST= "/app/userScore/getUserWalletList";
	/****提现申请**/
	public static final String API_GET_APPLYCASHWITHDRAW= "/app/cash/applyCashWithdraw";
	/****充值**/
	public static final String RECHARGE="/app/customer/recharge";
	/***充值确认**/
	public static final String CONFIRMRECHARGE="/app/customer/confirmRecharge";
///app/ticket/buyUserTicket
	/***购买卡券**/
	public static final String BUYUSERTICKET="/app/ticket/buyUserTicket";
	/**确认购买卡券*/
	///app/ticket/confirmUserTicket
	public static final String CONFIRMUSERTICKET="/app/ticket/confirmUserTicket";
	
	
	/****我的邀请人列表(APP)***/
	public static final String API_GET_GETINVITEDFRIENDSLIST= "/app/invite/getInvitedFriendsList";
	/****我的积分列表(APP)***/
	public static final String API_GET_GETUSERSCORELIST= "/app/userScore/getUserScoreList";
	/****我的牛币列表(APP)***/
	public static final String API_GET_GETUSERCURRENCYLIST= "/app/userScore/getUserCurrencyList";
	/****积分兑换牛币(APP)***/
	public static final String API_GET_EXCHANGESCORETOCOIN= "/app/userScore/exchangeScoreToCoin";
	
	/****积分兑换礼物(APP)***/
	public static final String API_GET_EXCHANGESCORTOPRESENT= "/app/userScore/exchangeScoreToPresent";
	/****礼物列表(APP)***/
	public static final String API_GET_GETPRESENTLIST= "/app/score/getPresentList";
	
	/****牛币兑换现金(APP)***/
	public static final String API_GET_EXCHANGECOINTOCASH= "/app/userScore/exchangeCoinToCash";
	public static final String API_GET_RANK="/app/invite/getInvitedFriendSort";
	/*****领奖******/
	public static final  String API_GET_LINGJIANG="/app/invite/getAward";
	/*****签到*****/
	public static final  String API_GET_SIGN="/app/score/sign";
	
	/***获取用户可用的抵用券**/
	public static final  String API_GET_GETUSERABLETICKET="/app/order/getUserableTicket";
	/***领取已开通城市未开通品牌抵用券**/
	public static final  String API_GET_GETMYCOUPU="/app/user/getCityUnUseBrandTicket";
	
	//获取兑吧登录的URL	
	public static final  String API_GET_GETDUIBALOGINURL="/app/duiba/getDuiBaLoginUrl";
  /***按品牌和城市获取网点信息列表***/
	public static final  String API_GET_GETSERVICENETWORK="/app/shop/getShopByCityAndBrand";
	/***客服归属信息***/
	public static final String API_GET_GETKEFUGUISHU="/app/customer/getUserShopInfo";
	/***用户归属4s店***/
	public static final String API_GET_UPDATECUSTOMERBELONGSHOP="/app/shop/updateCustomerBelongShop";
  /***获取客服信息***/
	public static final  String API_GET_EMDETAIL="/app/employee/getEmployeeDetail";
	/***对客服评分***/
	public static final   String API_GET_EMSCORE="/app/employee/scoreToEmployee";
	/***获取融云token***/
	public static final   String API_GET_RONGIM="/ry_chat/getToken";
	
	//================发现======================
	/**app/service/getServiceList获取发现页面的list**/
	public static final   String API_GET_SERVICELIST="/app/service/getServiceList";
	/**
	 * 获取增值服务网点列表
	 * */
	///app/shop/getServiceShopList
	public static final   String API_GET_SERVICESHOPLIST="/app/shop/getServiceShopList";
	/**
	 * 获取服务详情
	 * */
	///app/service/getServiceDetail
	public static final   String API_GET_SERVICEDETAIL="/app/service/getServiceDetail";
	/**
	 * 获取服务评论
	 * **/
	///app/service/getServiceComment
	public static final   String API_GET_SERVICECOMMENT="/app/service/getServiceComment";
}





