package com.bcwcar.android.bencar.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 转换服务器返回的数据，提供给ListView使用
 */
public class ListViewUtil {
	/**
	 * 转换成带分类过滤器的ListView使用的数据
	 */
	public static List<Map<String, String>> convertToTagListViewData(JSONObject jsonObjectString) throws Exception {
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		JSONArray arrayData = jsonObjectString.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA);
		for(int i = 0; i < arrayData.length(); i++) {
			JSONObject jsonObject = arrayData.getJSONObject(i);
			String firstKey = jsonObject.keys().next();
			//如果TagTitle字段不为空，表示这一列是tag元素，否则是普通数据。
			resultList.add(CollectionUtil.createStringMap("TagTitle", firstKey));
			JSONArray firstValue = jsonObject.getJSONArray(firstKey);
			resultList.addAll(CollectionUtil.jsonArrayToListMap(firstValue));
		}
		
		return resultList;
	}
	public static List<Map<String, String>> convertToTagListViewData1(JSONObject jsonObjectString) throws Exception {
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		JSONArray arrayData = jsonObjectString.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA);
		for(int i = 0; i < arrayData.length(); i++) {
			JSONObject jsonObject = arrayData.getJSONObject(i);
			String firstKey = jsonObject.keys().next();
			//如果TagTitle字段不为空，表示这一列是tag元素，否则是普通数据。
			resultList.add(CollectionUtil.createStringMap("TagTitle", firstKey));
			JSONArray firstValue = jsonObject.getJSONArray(firstKey);
			resultList.addAll(CollectionUtil.jsonArrayToListMap(firstValue));
		}
		
		return resultList;
	}
//	public static List<Map<String, String>> convertcity(JSONObject jsonObject111)throws Exception {
//		List<Map<String, String>> list=new ArrayList<>();
//		JSONArray arraydata=jsonObject111.getJSONArray(BizDefineAll.BIZ_RESPONSE_DATA);
//		for (int i=0 ;i <arraydata.length();i++) {
//			JSONObject jsonObject22=arraydata.getJSONObject(i);
//			for (int y=0 ;y<jsonObject22.length(); y++) {
//				JSONArray jsonArray33=jsonObject22.getJSONArray(jsonObject22.keys().toString());
//			    
//			}
			
			
			
	//	}
		
//	}
    	
	/**
	 * 创建显示所有品牌的第一行，提供给Spinner使用。
	 */
	public static Map<String, String> createBrandInfoEmptyLineData() {
		return CollectionUtil.createStringMap("BrandId", "", "Brand", "品牌", "IconUrl", "", "First", "B");
	}
	/**
	 * 创建显示所有区域的第一行，提供给Spinner使用。
	 */
	public static Map<String, String> createAreaEmptyLineData() {
		return CollectionUtil.createStringMap("DistrictId", "", "DistrictName", "区域", "CityId", "");
	}
	/**
	 * 创建显示所有推荐排序的第一行，提供给Spinner使用。
	 */
	public static Map<String, String> createRecommendOrderEmptyLineData() {
		return CollectionUtil.createStringMap("OrderId", "", "OrderString", "推荐排序");
	}
}