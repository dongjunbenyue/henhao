package com.bcwcar.android.bencar.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainMy.MyOrderActivity;
import com.bcwcar.android.bencar.activity.MainMy.MyWalletCouponActivity;
import com.bcwcar.android.bencar.activity.MainMy.WithdarwSettingsCashPassword;
import com.bcwcar.android.bencar.activity.MainMy.WithdrawCashPasswordPhone;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.MD5;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.biz.HttpWallet;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.QuanDingDanDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.PayResult;
import com.bcwcar.android.bencar.util.SignUtils;
import com.bcwcar.android.bencar.util.StringUtil;
import com.bcwcar.android.bencar.wxapi.ConstantWeiXin;
import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 付款页
 */
public class PaymentMethodActivity extends BaseActivity {

	private static final String LOG_TAG = PaymentMethodActivity.class.getSimpleName();
	// 支付宝
	// 商户PID
	public static final String PARTNER = "2088221658644444";
	// 商户收款账号
	public static final String SELLER = "kefuzhongxin@bcwcar.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMfi5Mt38cri/FHgHbcXY9RxQxkxHJ8G0yGFr04c2BMH2H7dnNDbFO3Wanpcpypise0cpWSjddZ3a52EYQTormonW85dq2T9fQCoHxYzHOfn18LVuQDsMJYAoryYX9CzTPBziOzCMo9tUNC/8PPloNISlxmBbzhgtCqrh2FfEkXjAgMBAAECgYBs4ACBd27DdQm8yxdEcEAXKi5DHYYadMwxmTCYmotzKjXZPHq7JGns9np+9dKz+pgxsAXCoGqCDAAZB08VLmw5PY21ApNrrrOzCXJGdG4MkiaPTxKm/hid7zBpjgnySExNqZBF/N2d6f1IvFBXZtjzY5dCdk0opZwJhQ+w+G47kQJBAOUkopF7dkJljmt/SUJrVWelfmjl2KlPEGuAft9Jawzm9VkbnDajJwdmz3krS+QtofZEn0Y6m51CcxZI4UAeTykCQQDfUGr0aK+KyCVfN5e1X/JTFGbofW3VYBkZ4eLbcxfb+rmBua/U3NUkVjPz9JTMknmxdm17yY6YI1FTA5uuFGorAkBlNq1oPXSbF2i1KuAdKnPq+DrB8uSkxHuoQHXKAw/5/vpYhc2WhLJ0noD+MJWxEEb+FImV58VczUEwkriMLdchAkEAyKM2FTXhUi2U9kG6emkkDWTtOMFI50caBlkfO8/ihfB0r4sn4XYvwh/5OZxr/Ik18S3YHDnxddt1e8JoIzWQVQJAHY3LoTws7VVdc4Btks/sdtDqblsSrnM1qJ8BdjOLtzdIJzM0p5ZtMdQTzkU8AVWicvtN5SeTRI11yRdpiGZJng==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIzaeF5E/RqgdlJZmE8RUFvTGN Ocq8bl+2xh8NTSHkTLXQh7UWSTd+m+bTFFHX4T85TJfMGhusDpWdK/mg437Q01Lx Ra98m6cCcCvSEeRY0qZ0gjk+OTPsTuHbmWLqZCh1HhVncQAnu25JtN/0D+c4Gtw3 APvIR/7Ky6WhKyGc2QIDAQAB";
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;
	private RelativeLayout alipayRL;
	private TextView yue_textview;
	private int user_yue;
	// 微信
	PayReq req;
	final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	StringBuffer sb;

	private RelativeLayout wxpayRL, zhifubao,yuepay;

	private String bancarPrice;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					
//					Bundle bundle = new Bundle();
//					bundle.putString("OrderId", DingDanDataSave.get(PaymentMethodActivity.this, "OrderId"));
//					PageUtil.jumpTo(PaymentMethodActivity.this, PaymentResultActivity.class, bundle);
					HttpOrder.commitOrder(new CallbackLogic() {

						public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
							// TODO Auto-generated method stub
						}
					}, UserLoginStatus.get(PaymentMethodActivity.this,"Token"), DingDanDataSave.get(PaymentMethodActivity.this, "OrderId"),"2",DingDanDataSave.get(getApplicationContext(), "Type"),PaymentMethodActivity.this);

					Intent intent = new Intent(PaymentMethodActivity.this, PaymentResultActivity.class);
					intent.putExtra("OrderId", DingDanDataSave.get(PaymentMethodActivity.this, "OrderId"));
					startActivity(intent);
					finish();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PaymentMethodActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PaymentMethodActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(PaymentMethodActivity.this, MainActivity.class);
						startActivity(intent);
						finish();
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(PaymentMethodActivity.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};

	private String action;
	private String orderInfo1, sign1;

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay() {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
			return;
		}
		// String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");
		String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", bancarPrice);

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = sign(orderInfo1);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo1 + "&sign=\"" + sign + "\"&" + getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PaymentMethodActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {
			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(PaymentMethodActivity.this);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/* 微信 */

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
		// leftView.setVisibility(View.GONE);
		leftView.setText("");
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText(getString(R.string.shop4s_maintain_payment_method_title));
		// rightView.setVisibility(View.GONE);

		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_zidingyi();
			}
		});
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PaymentMethodActivity.this, MainActivity.class);
				startActivity(intent);
				finish();

			}
		});
		changeFonts(titleParentView);
	}

	/**
	 * 生成签名
	 */
	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(ConstantWeiXin.API_KEY);

		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion", appSign);
		return appSign;
	}

	// 时间撮
	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	private void genPayReq(JSONObject data) {
		JSONObject object;
		try {
			object = data.getJSONObject(BizDefineAll.BIZ_RESPONSE_DATA);
			req.appId = ConstantWeiXin.APP_ID;
			req.partnerId = ConstantWeiXin.MCH_ID;
			req.prepayId = object.getString("PrePayId");
			req.packageValue = "Sign=WXPay";
			req.nonceStr = object.getString("NonceStr");
			req.timeStamp = String.valueOf(genTimeStamp());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		sb.append("sign\n" + req.sign + "\n\n");

		System.out.println("show002==" + req.toString());

		Log.e("orion", signParams.toString());
		msgApi.registerApp(ConstantWeiXin.APP_ID);
		msgApi.sendReq(req);

	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// LayoutInflater.from(this).inflate(R.layout.activity_test,
		// bodyParentView);
		LayoutInflater.from(this).inflate(R.layout.body_shop4s_maintain_payment_method, bodyParentView);
		changeFonts(bodyParentView);

	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// LayoutInflater.from(this).inflate(R.layout.common_toolbar_1,
		// toolBarParentView);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			action = getIntent().getStringExtra("action").toString();

		} catch (Exception e) {
			// TODO: handle exception
		}
		DingDanDataSave.save(PaymentMethodActivity.this, "OrderId", DingDanDataSave.get(PaymentMethodActivity.this, "OrderId"));

		/**
		 * 微信支付
		 */
		req = new PayReq();
		sb = new StringBuffer();
		msgApi.registerApp(ConstantWeiXin.APP_ID);
		yue_textview=(TextView)findViewById(R.id.TextView_yue_old001);
		wxpayRL = (RelativeLayout) findViewById(R.id.shop4s_maintain_payment_method_layout_wechatpay);// 微信父级
		zhifubao = (RelativeLayout) findViewById(R.id.shop4s_maintain_payment_method_layout_alipay);
		yuepay = (RelativeLayout) findViewById(R.id.shop4s_maintain_payment_method_layout_yuepay);

		wxpayRL.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HttpOrder.getPrePayId(new CallbackLogic() {
					@Override
					public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
						// TODO Auto-generated method stub
						showToast("正在调起支付，请稍等！");
						WithdrawData.setWithdrawData(getApplicationContext(), "ChargeFlag", "0");
						genPayReq(data);
					}
				}, UserLoginStatus.get(PaymentMethodActivity.this, "Token"), DingDanDataSave.get(PaymentMethodActivity.this, "OrderId"), "1", PaymentMethodActivity.this);
			}
		});
		zhifubao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				HttpOrder.getPrePayId(new CallbackLogic() {
					@Override
					public void onBizSuccess(String responseDescription, final JSONObject data, JSONObject alldata) {
						Log.e(LOG_TAG, data.toString());
						Dialogcancel();
						try {
							JSONObject json = data.getJSONObject(BizDefineAll.BIZ_RESPONSE_DATA);
							sign1 = json.getString("PaySign");
							orderInfo1 = json.getString("PayStr");
							// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
							pay();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, UserLoginStatus.get(PaymentMethodActivity.this, "Token"), DingDanDataSave.get(PaymentMethodActivity.this, "OrderId"), "2", PaymentMethodActivity.this);

			}
		});
		yuepay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * yue
				 * **/
				int tt=Integer.parseInt(DingDanDataSave.get(PaymentMethodActivity.this, "PaySum"));
				if (user_yue<tt) {
					showToast("余额不足，请选择其他支付方式");
					return;
				}else {
					String string=UserLoginStatus.get(PaymentMethodActivity.this, "PayFlag");
					if (string.equals("1")){//设置了支付密码
						Intent intent=new Intent(PaymentMethodActivity.this,WithdarwSettingsCashPassword.class);
						intent.putExtra("action", "PaymentMethodActivity");
						startActivity(intent);
					}else{//没有设置支付密码
						Intent intent=new Intent(PaymentMethodActivity.this,WithdrawCashPasswordPhone.class);
						startActivity(intent);
					}
					
				}
			}
		});
		TextView price = (TextView) findViewById(R.id.payment_method_price);
		String priceString;
		if (!StringUtil.isEmpty(DingDanDataSave.get(PaymentMethodActivity.this, "OrderId"))) {
			priceString = "￥" + DingDanDataSave.get(PaymentMethodActivity.this, "PaySum") + "元";
		} else {
			priceString = "￥" + DingDanDataSave.get(PaymentMethodActivity.this, "TotalPrice") + "元";
		}
		price.setText(priceString);
	HttpWallet.getUserExp(new CallbackLogic() {
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					JSONObject object=data.getJSONObject("Data");
					yue_textview.setText("余额:￥"+object.get("Wallet").toString());
					user_yue=object.getInt("Wallet");
					System.out.println(user_yue+"-------------------------");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}, UserLoginStatus.get(PaymentMethodActivity.this, "Token"), PaymentMethodActivity.this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			dialog_zidingyi();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}

	public void dialog_zidingyi() {
		// 自定义布局
		View layout = this.getLayoutInflater().inflate(R.layout.mydialog2, null);
		TextView queren = (TextView) layout.findViewById(R.id.TextView_mydialog_queren001);
		TextView quxiao = (TextView) layout.findViewById(R.id.TextView_mydialog_quxiao001);

		AlertDialog.Builder builder = new AlertDialog.Builder(PaymentMethodActivity.this,
				AlertDialog.THEME_DEVICE_DEFAULT_DARK);
		final AlertDialog dialog_save = builder.create();
		dialog_save.setView(layout);
		queren.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_save.cancel();
				    Bundle bundle =new Bundle();
				    bundle.putString("replace", "1");
				    bundle.putString(BizDefineAll.BIZ_ACTION, "PaymentMethodActivity");
				    PageUtil.jumpTo(PaymentMethodActivity.this, MyOrderActivity.class,bundle);
					finish();
				
			}
		});
		quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog_save.cancel();

			}
		});
		// 透明
		Window window = dialog_save.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.9f;
		window.setAttributes(lp);
		dialog_save.show();

	}

}
