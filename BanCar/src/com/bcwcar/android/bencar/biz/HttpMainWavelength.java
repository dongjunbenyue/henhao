package com.bcwcar.android.bencar.biz;

import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.http.OkHttpHelper;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;

/**
 * 志同道合  所有网络请求业务
 */
public class HttpMainWavelength {
	//////////////////////////////以下是普通帖子的操作//////////////////////////////////
//	/**
//	 * 帖子列表
//	 */
//	public static void getArticleList(CallbackLogic callbackLogic,
//			String token, String pageNum, String pageCount, String userId) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_ARTICLELIST;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "PageNum", pageNum, "PageCount", pageCount, "UserId", userId));
//	}
//	public static void getArticleList001(CallbackLogic002 callbackLogic,
//			String token, String pageNum, String pageCount, String userId) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_ARTICLELIST;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "PageNum", pageNum, "PageCount", pageCount, "UserId", userId));
//	}
//	/**
//	 * 帖子详情
//	 */
//	public static void getArticleDetail(CallbackLogic callbackLogic, String token, String articleId) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_ARTICLEDETAIL;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "ArticleId", articleId));
//	}
//	/**
//	 * 帖子回复列表
//	 */
//	public static void getReplyList(CallbackLogic callbackLogic, String token, String articleId, String pageNum, String pageCount) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_REPLYLIST;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "ArticleId", articleId, "PageNum", pageNum, "PageCount", pageCount));
//	}
//	/**
//	 * 关注的人列表
//	 */
//	public static void getMyConcernList(CallbackLogic002 callbackLogic, String token,String UserId, String type, String pageNum, String pageCount) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_MYCONCERNLIST;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token,"UserId",UserId, "Type", type, "PageNum", pageNum, "PageCount", pageCount));
//	}
//	/**
//	 * 回复我的所有帖子的所有人列表
//	 */
//	public static void getMyReplyList(CallbackLogic callbackLogic, String token, String pageNum, String pageCount) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_MYREPLYLIST;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "PageNum", pageNum, "PageCount", pageCount));
//	}
//	/**
//	 * 赞我的所有帖子的所有人列表
//	 */
//	public static void getMyPraiseList(CallbackLogic callbackLogic, String token, String pageNum, String pageCount) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_MYPRAISELIST;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "PageNum", pageNum, "PageCount", pageCount));
//	}
//	/**
//	 * 赞某人的指定帖子的所有人列表
//	 */
//	public static void getPraiseList(CallbackLogic callbackLogic, String token, String userId, String articleId,
//			String pageNum, String pageCount) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_PRAISELIST;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "UserId", userId, "ArticleId", articleId, "PageNum", pageNum, "PageCount", pageCount));
//	}
//	/**
//	 * 发表文章
//	 */
//	public static void issureArticle(CallbackLogic002 callbackLogic, String token, String content, String pics, String clientType) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_ISSURE_ARTICLE;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "Content", content, "Pics", pics, "ClientType", clientType));
//	}
//	/**
//	 * 回复文章
//	 */
//	public static void replyArticle(CallbackLogic callbackLogic, String token,
//			String businessId, String content,String userName, String clientType, String type) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_REPLY_ARTICLE;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "BusinessId", businessId, "Content", content,
//						"UserName", userName, "ClientType", clientType, "Type", type));
//	}
//	/**
//	 * 用户关注
//	 */
//	public static void concern(CallbackLogic callbackLogic, String token, String businessId) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_CONCERN;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "BusinessId", businessId));
//	}
//	/**
//	 * 文章点赞
//	 */
//	public static void praise(CallbackLogic002 callbackLogic, String Token, String Type, String BusinessId) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_PRAISE;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", Token, "Type", Type, "BusinessId", BusinessId));
//	}
//	//////////////////////////////以下是官方帖子的操作//////////////////////////////////
//	/**
//	 * 官方帖子列表
//	 */
//	public static void getOfficialArticleList(CallbackLogic002 callbackLogic, String token, String pageNum, String pageCount) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_OFFICIAL_ARTICLELIST;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "PageNum", pageNum, "PageCount", pageCount));
//	}
//	/**
//	 * 官方帖子详情
//	 */
//	public static void getOfficialArticleDetail(CallbackLogic callbackLogic, String token, String articleId) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_OFFICIAL_ARTICLEDETAIL;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "ArticleId", articleId));
//	}
//	/**
//	 * 官方帖子回复列表
//	 */
//	public static void getOfficialReplyList(CallbackLogic callbackLogic, String token, String articleId, String pageNum, String pageCount) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_OFFICIAL_REPLYLIST;
//		OkHttpHelper.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "ArticleId", articleId, "PageNum", pageNum, "PageCount", pageCount));
//	}
//	/**
//	 * 赞了指定帖子的所有人
//	 */
//	public static void getOfficialPraiseList(CallbackLogic002 callbackLogic, String token, String articleId, String pageNum, String pageCount) {
//		String baseUrl = Config.DATA_SERVER_URL + BizDefineAll.API_GET_OFFICIAL_PRAISELIST;
//		OkHttpHelper002.getInstance().addPostRequest(callbackLogic, baseUrl,
//				CollectionUtil.createStringMap(
//						"Token", token, "ArticleId", articleId, "PageNum", pageNum, "PageCount", pageCount));
//	}
}