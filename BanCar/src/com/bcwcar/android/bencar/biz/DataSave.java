package com.bcwcar.android.bencar.biz;

public class DataSave {
	/**
	 * 调用接口后保存的信息，对应/app/user/toReg和/app/user/toLogin
	 */
	public static class UserLogin {
		public static final String SP_NAME = UserLogin.class.getName();
		public static final String ROOT_KEY = BizDefineAll.BIZ_RESPONSE_DATA;
	}
	/**
	 * 智能保养整个流程中需要保存的数据
	 */
	public static class SmartMaintenance {
		public static final String SP_NAME = SmartMaintenance.class.getName();
		/**
		 * 车型ID
		 */
		public static final String CAR_ID = "car_id";
		/**
		 * 购车时间
		 */
		public static final String BUY_DATE = "buy_date";
		/**
		 * 行驶里程
		 */
		public static final String CAR_MILES = "car_miles";
		
		public static final String MAINTENANCE_PROJECT = "MaintenanceProject";
		public static final String MAINTENANCE_PROJECT_SELECTED_INDEX = "MaintenanceProjectSelectedIndex";
		public static final String MAINTENANCE_DATE = "MaintenanceDate";
		
//		/**
//		 * 保养项目ID数据列表
//		 */
//		public static final String MAINTENANCE_PROJECT_IDS = "maintenance_project_ids";
//		/**
//		 * 免费检测项目ID数据列表
//		 */
//		public static final String FREE_TESTING_PROJECT_IDS = "free_testing_project_ids";
//		/**
//		 * 乐途价
//		 */
//		public static final String ZEATRIP_PRICE = "zeatrip_price";
//		/**
//		 * 实际价(犇车价) = 乐途价 - 优惠券价格
//		 */
//		public static final String ACTUAL_PRICE = "actual_price";
//		/**
//		 * 优惠券ID数据
//		 */
//		public static final String COUPON_ID = "coupon_id";
//		/**
//		 * 保养日期，2015年10月21日
//		 */
//		public static final String SCHEDULED_DATE = "scheduled_date";
//		/**
//		 * 保养日期，星期一
//		 */
//		public static final String SCHEDULED_DAY_OF_WEEK = "SCHEDULED_DAY_OF_WEEK";
	}
}