package com.bcwcar.android.bencar.biz;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 全局的业务逻辑判断
 */
public class BizGlobal {
	private JSONObject mResponseJSONObject = null;

	public BizGlobal(String responseBody) {
		try {
			mResponseJSONObject = new JSONObject(responseBody);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 返回业务逻辑结果代码字符串，0代表业务成功，1代表业务失败，-1代表JSON错误。
	 */
	public String getResponseCode() {
		if (mResponseJSONObject == null) {
			return BizDefineAll.BIZ_JSON_ERROR;
		}

		return mResponseJSONObject.optString(BizDefineAll.BIZ_RESPONSE_CODE, BizDefineAll.BIZ_JSON_ERROR);
	}
	public String getResponseCode1() {
		if (mResponseJSONObject == null) {
			return BizDefineAll.BIZ_JSON_ERROR;
		}

		return mResponseJSONObject.optString("error_code", BizDefineAll.BIZ_JSON_ERROR);
	}
	/**
	 * 返回业务订单id
	 * 
	 */
	public String getOrderId() {
		if (mResponseJSONObject == null) {
			return BizDefineAll.BIZ_JSON_ERROR;
		}

		return mResponseJSONObject.optString("OrderId", "");
	}

	/**
	 * 返回业务逻辑结果描述字符串，空字符串表示JSON错误。
	 */
	public JSONObject getResponseAll() {
		if (mResponseJSONObject == null) {
			return null;
		}

		return mResponseJSONObject;
	}
   public String getResponseAll1() {
	   if (mResponseJSONObject == null) {
			return null;
		}

		return mResponseJSONObject.toString();
}
	
	/**
	 * 返回业务逻辑结果描述字符串，空字符串表示JSON错误。
	 */
	public String getResponseDescription() {
		if (mResponseJSONObject == null) {
			return "";
		}

		return mResponseJSONObject.optString(BizDefineAll.BIZ_RESPONSE_DESCRIPTION, "");
	}
	public String getResponseDescription1() {
		if (mResponseJSONObject == null) {
			return "";
		}

		return mResponseJSONObject.optString("reason", "");
	}
	/**
	 * 返回业务逻辑结果数据，结果数据的key为BIZ_RESPONSE_DATA（"Data"）
	 * 如果返回null表示没有Data字段或JSON错误。
	 */
	public JSONObject getResponseData() {
		if (mResponseJSONObject == null) {
			return null;
		}
		
		Object obj = mResponseJSONObject.opt(BizDefineAll.BIZ_RESPONSE_DATA);
		if(obj == null) {
			return null;
		}
		
		try {
			return new JSONObject().put(BizDefineAll.BIZ_RESPONSE_DATA, obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}