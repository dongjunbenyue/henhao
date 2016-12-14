package com.bcwcar.android.bencar.datasave;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用户订单数据保存
 */


//Token	N	口令
//OrderId	Y	原始订单ID
//PlateNumber	N	车牌号
//CarId	N	车型ID
//Miles	N	公里数
//Contact	N	联系人
//MaintenanceTime	N	保养时间，格式2015-12-12
//MaintenanceSpan	N	保养时段
//TicketId	N	抵用券ID
//TicketType	N	当抵用券ID不为空时必传，1-商家卡卷，2-系统卡卷
//TotalPrice	N	订单金额
//BillFlag	N	是否需要发票，0-不需要，1-需要
//BillHead	Y	发票抬头，需要时必填
//CellPhone	N	联系电话
//ShopId	N	网点ID
//Gender	N	性别0-女，1-男
//AccIds	N	配件ID,多个用逗号隔开


//"ShopInfo": {
//ShopData:判断用户是否选择了网店==0：没有==1：已选择
//    "ContactPhone": "073188292233",
//    "BrandName": "英菲尼迪",
//    "Score": 3,
//    "ShopName": "湖南兰天晟天英菲尼迪4S店",
//    "Address": "岳麓大道西3588号长张高速收费出口南侧",
//    "LogoUrl": "uploadFiles\/uploadImgs\/20160408\/49f1ed8177bb434ca3fc9522b77307f5.jpg",
//    "ProviderNames": "东风英菲尼迪,英菲尼迪(进口)",
//    "CommentCount": 1,
//    "ShopId": "cb4f4224-784a-4d66-803a-a47beb13b888",
//    "Distance": 3.7
//},
//OrderId

//StartDate	N	开始日期
//EndDate	N	结束日期
//ShopName	N	网点名称
//TicketSum	N	卡卷面额
//TicketDesc	N	卡卷描述
//TicketType	N	1-商家卡卷，2-系统卡卷
//TicketId	N	卡卷ID
//TicketName	N	卡卷名称
//====
//"PaySum": "6667",
//"ResponseCode": "0",
//"ResponseDescription": "",
//"OrderId": "330e0bbe-ba41-4bf1-a6ce-326f48b6c0be"
public class DingDanDataSave {

	public static void save(Context context,String key, String value) {
		SharedPreferences dingdan = context.getSharedPreferences("dingdan", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public static void clear(Context context) {
		SharedPreferences dingdan = context.getSharedPreferences("dingdan", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.clear();
		editor.commit();
	}
	public static String get(Context context,String key) {
		SharedPreferences dingdan = context.getSharedPreferences("dingdan", 0);
		String string = dingdan.getString(key, "").toString();
		return string;
	}
	
	
	
	
	
}
