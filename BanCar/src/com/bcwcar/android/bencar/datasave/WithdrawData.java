package com.bcwcar.android.bencar.datasave;



import com.bcwcar.android.bencar.biz.BizDefineAll;

import android.content.Context;
import android.content.SharedPreferences;

public class WithdrawData {
//	Token	N	口令
//	BankId	N	银行ID
//	CashSum	N	提现金额
//	CashPass	N	提现密码
//	Wallet 余额
//	PassFlag 密码标志
//	ChargeMoney 充值金额
//	ChargeFlag 充值标志
//	RechargeId 充值成功id

	/**
	 * 将用户登录成功时返回的数据进行保存
	 * @param userLoginData 去掉了最外层的Data节点
	 */
	public static void setWithdrawData(Context context,String key,String value) {
		SharedPreferences sp = context.getSharedPreferences(BizDefineAll.WITHDRAW, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
	
	public static String getWithdrawData(Context context,String key) {
		SharedPreferences sp = context.getSharedPreferences(BizDefineAll.WITHDRAW, Context.MODE_PRIVATE);
		return sp.getString(key, "");
	}
	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(BizDefineAll.WITHDRAW, 0);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.commit();
	}
}
