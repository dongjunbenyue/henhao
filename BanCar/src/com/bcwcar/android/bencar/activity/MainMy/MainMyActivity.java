package com.bcwcar.android.bencar.activity.MainMy;

import java.util.Hashtable;
import java.util.Random;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainMy.CreditActivity.CreditsListener;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.ImageFileCache;
import com.bcwcar.android.bencar.base.ImageGetFromHttp;
import com.bcwcar.android.bencar.base.ImageMemoryCache;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpWallet;
import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.datasave.UserWalletListData;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.util.StringUtil;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主页|我的
 */
public class MainMyActivity extends BaseActivity {
	private long exitTime;
	private TextView button, carname, nickname, numcard, integration, textgotologo, textgoturigiset;
	private LinearLayout gotouser, gotobanance, gotomyorder, gotojifen, gotocard, linearLayoutzhuce, linearLayoutgeren;
	private RelativeLayout gotupeccancy;
	private RelativeLayout gotofreemantence;
	private RelativeLayout gotomarket;
	private RelativeLayout gotoaboutus;
	private RelativeLayout gotoemergence;
	private RelativeLayout gotoset;
	private TextView textyue, textdaifukuan, textyifukuan, textfuwuzhong, textdaipingjia, texttuikuan;
	private RelativeLayout textdaifukuan1;
	private RelativeLayout textyifukuan1;
	private RelativeLayout textfuwuzhong1;
	private RelativeLayout textdaipingjia1;
	private RelativeLayout texttuikuan1;
	private SimpleDraweeView simpleDraweeView;
	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;
	private Bitmap bitmap_user_icon;
	private ImageView erweima;
	private Bitmap bitmap;
	private Bitmap bitmap1;
	final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			System.out.println("=================" + msg.obj.toString());
			bitmap_user_icon = (Bitmap) msg.obj;
			if (bitmap_user_icon != null) {
				
				fileCache.saveBitmap(bitmap_user_icon,
						Config.IMAGE_SERVER_URL + "/" + UserLoginStatus.get(MainMyActivity.this, "IconUrl"));
				memoryCache.addBitmapToCache(
						Config.IMAGE_SERVER_URL + "/" + UserLoginStatus.get(MainMyActivity.this, "IconUrl"),
						bitmap_user_icon);
				System.out.println(bitmap_user_icon.getByteCount()+"777777777777777777");
			}else {
				bitmap_user_icon=BitmapFactory.decodeResource(getResources(), R.drawable.logo);
				System.out.println(bitmap_user_icon.getByteCount()+"8888888888888888888888");
			}
		}
	};
	private String url;
	private ImageView sweepIV;
	private LinearLayout line1111;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inntview();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (UserLoginStatus.isLoggedOn(getApplicationContext())) {
			linearLayoutgeren.setVisibility(View.VISIBLE);
			linearLayoutzhuce.setVisibility(View.GONE);
			initdata();
		} else {
			linearLayoutgeren.setVisibility(View.GONE);
			linearLayoutzhuce.setVisibility(View.VISIBLE);
			textyue.setText("0");
			integration.setText("0");
			numcard.setText("0");
			textdaifukuan.setVisibility(View.GONE);
			textfuwuzhong.setVisibility(View.GONE);
			textyifukuan.setVisibility(View.GONE);
			textdaipingjia.setVisibility(View.GONE);
			texttuikuan.setVisibility(View.GONE);
		}

	}

	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		changeFonts((ViewGroup) rootView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		TextView leftView = (TextView) rootView.findViewById(R.id.common_title_1_left_view);
		leftView.setVisibility(View.GONE);

		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		rightView.setText("签到");
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!UserLoginStatus.isLoggedOn(getApplicationContext())) {
					showToast("尚未登录，请登录！");
					PageUtil.jumpTo(MainMyActivity.this, LoginActivity.class);
				}else {
					
				
				HttpWallet.sign(new CallbackLogic() {

					@Override
					public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
						// TODO Auto-generated method stub
						showToast("签到成功,+5积分");
					}
				}, UserLoginStatus.get(MainMyActivity.this, "Token"), MainMyActivity.this);
			}}
		});

	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.activity_my, bodyParentView);
		changeFonts(bodyParentView);
	}

	public void inntview() {
		
		textyue = (TextView) findViewById(R.id.balance);
		textdaifukuan = (TextView) findViewById(R.id.numdaifukuan);
		textyifukuan = (TextView) findViewById(R.id.numdaishiyong);
		textfuwuzhong = (TextView) findViewById(R.id.numfuwuzhong);
		textdaipingjia = (TextView) findViewById(R.id.numdaipingjia);
		texttuikuan = (TextView) findViewById(R.id.numtuikuan);
		linearLayoutzhuce = (LinearLayout) findViewById(R.id.linetologin);
		linearLayoutgeren = (LinearLayout) findViewById(R.id.linetogeren);
		textdaifukuan1 = (RelativeLayout) findViewById(R.id.Reledaifukuan);
		textyifukuan1 = (RelativeLayout) findViewById(R.id.Releyifukuan);
		textfuwuzhong1 = (RelativeLayout) findViewById(R.id.Relefuwuzhong);
		textdaipingjia1 = (RelativeLayout) findViewById(R.id.Reledaipingjia);
		linearLayoutzhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(MainMyActivity.this, LoginActivity.class);
			}
		});
		texttuikuan1 = (RelativeLayout) findViewById(R.id.Reletuikuan);
		simpleDraweeView = (SimpleDraweeView) findViewById(R.id.service_network_logo);
		carname = (TextView) findViewById(R.id.carname);
		nickname = (TextView) findViewById(R.id.nickname);
		numcard = (TextView) findViewById(R.id.numcard);
		integration = (TextView) findViewById(R.id.integration);
		gotojifen = (LinearLayout) findViewById(R.id.gotojifen);
		gotocard = (LinearLayout) findViewById(R.id.gotocard);
		erweima = (ImageView) findViewById(R.id.shengchengerweima);
		sweepIV = (ImageView) findViewById(R.id.ecodeimgview1);
		line1111 = (LinearLayout) findViewById(R.id.ecodline);
		line1111.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				line1111.setVisibility(View.GONE);
			}
		});
		erweima.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				System.out.println(bitmap_user_icon.getByteCount()+"++++++++++++++++++++++++++++");
				ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(Config.IMAGE_SERVER_URL + "/" + UserLoginStatus.get(MainMyActivity.this, "IconUrl"))).setProgressiveRenderingEnabled(true).build();
				ImagePipeline imagePipeline = Fresco.getImagePipeline();
				DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, this);
				dataSource.subscribe(new BaseBitmapDataSubscriber() {
				    
					@Override
				    public void onNewResultImpl( Bitmap bitmap) {
				        // You can use the bitmap in only limited ways
				        // No need to do any cleanup.
				    	try {
							bitmap1 = makeQRImage(bitmap, url, 200, 200);
                           runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									line1111.setVisibility(View.VISIBLE);
									sweepIV.setImageBitmap(bitmap1);
								}
							});
						} catch (WriterException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
				    @Override
				    public void onFailureImpl(DataSource dataSource) {
				        // No cleanup required here.
				    	
							bitmap_user_icon=BitmapFactory.decodeResource(getResources(), R.drawable.logo);
						
				    	try {
							bitmap1 = makeQRImage(bitmap_user_icon, url, 200, 200);
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									line1111.setVisibility(View.VISIBLE);
									sweepIV.setImageBitmap(bitmap1);
								}
							});
							
						} catch (WriterException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
				}, CallerThreadExecutor.getInstance());
				
			}

		});
		gotocard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!UserLoginStatus.isLoggedOn(getApplicationContext())) {
					showToast("尚未登录，请登录！");
					PageUtil.jumpTo(MainMyActivity.this, LoginActivity.class);
				}else {
				Bundle bundle = new Bundle();
				bundle.putCharSequence(BizDefineAll.BIZ_ACTION, "MainMyActivity");
				PageUtil.jumpTo(MainMyActivity.this, MyWalletCouponActivity.class, bundle);
			}
			}
		});
		gotojifen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!UserLoginStatus.isLoggedOn(getApplicationContext())) {
					showToast("尚未登录，请登录！");
					PageUtil.jumpTo(MainMyActivity.this, LoginActivity.class);
				}else {
				HttpWallet.getDuiBaLoginUrl(new CallbackLogic() {

					@Override
					public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
						// TODO Auto-generated method stub

						jiexi(alldata.toString());

					}
				}, UserLoginStatus.get(MainMyActivity.this, "Token"), MainMyActivity.this);
			}}
		});
		textdaifukuan1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("replace", "1");
				bundle.putString(BizDefineAll.BIZ_ACTION, "MainMyActivity");
				PageUtil.jumpTo(MainMyActivity.this, MyOrderActivity.class, bundle);

			}
		});
		textyifukuan1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("replace", "2");
				bundle.putString(BizDefineAll.BIZ_ACTION, "MainMyActivity");
				PageUtil.jumpTo(MainMyActivity.this, MyOrderActivity.class, bundle);

			}
		});
		textfuwuzhong1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("replace", "3");
				bundle.putString(BizDefineAll.BIZ_ACTION, "MainMyActivity");
				PageUtil.jumpTo(MainMyActivity.this, MyOrderActivity.class, bundle);

			}
		});
		textdaipingjia1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("replace", "4");
				bundle.putString(BizDefineAll.BIZ_ACTION, "MainMyActivity");
				PageUtil.jumpTo(MainMyActivity.this, MyOrderActivity.class, bundle);

			}
		});
		texttuikuan1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("replace", "5");
				PageUtil.jumpTo(MainMyActivity.this, MyOrderActivity.class, bundle);

			}
		});
		gotouser = (LinearLayout) findViewById(R.id.gotouser);
		gotobanance = (LinearLayout) findViewById(R.id.gotobanance);
		gotomyorder = (LinearLayout) findViewById(R.id.gotomyorder);
		gotomyorder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				PageUtil.jumpTo(MainMyActivity.this, MyOrderAll.class);
			}
		});
		gotobanance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(MainMyActivity.this, Balance.class);
			}
		});

		gotofreemantence = (RelativeLayout) findViewById(R.id.gotofreemantence);
		gotomarket = (RelativeLayout) findViewById(R.id.gotomarket);
		gotomarket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserLoginStatus.isLoggedOn(getApplicationContext())) {
					PageUtil.jumpTo(MainMyActivity.this, YouZanActivity.class);
				}else {
					PageUtil.jumpTo(MainMyActivity.this, LoginActivity.class);
				}
				
			}
		});
		gotofreemantence.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserLoginStatus.isLoggedOn(getApplicationContext())) {
					PageUtil.jumpTo(MainMyActivity.this, FreeMantence.class);
				}else {
					PageUtil.jumpTo(MainMyActivity.this, LoginActivity.class);
				}
				
			}
		});
		gotupeccancy = (RelativeLayout) findViewById(R.id.gotopeccancy);
		gotupeccancy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(MainMyActivity.this, WeiZhangSearchPage.class);
			}
		});
		gotouser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(MainMyActivity.this, Userinfor.class);

			}
		});
		gotoaboutus = (RelativeLayout) findViewById(R.id.gotoaboutus);
		gotoaboutus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(MainMyActivity.this, AboutUs.class);
			}
		});
		gotoemergence = (RelativeLayout) findViewById(R.id.gotoemergence);
		gotoemergence.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(MainMyActivity.this, Emergence.class);
			}
		});
		gotoset = (RelativeLayout) findViewById(R.id.gotoset);
		gotoset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserLoginStatus.isLoggedOn(getApplicationContext())) {
					PageUtil.jumpTo(MainMyActivity.this, SettingActivity.class);
				} else {
					PageUtil.jumpTo(MainMyActivity.this, LoginActivity.class);
				}

			}
		});

	}

	public void jiexi(String string) {
		try {
			JSONObject object = new JSONObject(string);
			JSONObject object2 = object.getJSONObject("Data");
			String DuiBaLoginUrl = object2.getString("DuiBaLoginUrl");
			tiaozhuang(DuiBaLoginUrl);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Bitmap getBitmap(String url) {
		// 从内存缓存中获取图片
		System.out.println("// 从内存缓存中获取图片" + url);
		Bitmap result = memoryCache.getBitmapFromCache(url);
		if (result == null) {
			// 文件缓存中获取
			System.out.println("// 文件缓存中获取" + url);
			result = fileCache.getImage(url);
			if (result == null) {
				// 从网络获取
				ImageGetFromHttp.downloadBitmap(url, handler);
			} else {
				// 添加到内存缓存
				memoryCache.addBitmapToCache(url, result);
			}
		}
		return result;
	}

	public Bitmap makeQRImage(Bitmap logoBmp, String content, int QR_WIDTH, int QR_HEIGHT) throws WriterException {
		try {
			// 图像数据转换，使用了矩阵转换
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错率
			hints.put(EncodeHintType.MARGIN, 2); // default is 4
			hints.put(EncodeHintType.MAX_SIZE, 350);
			hints.put(EncodeHintType.MIN_SIZE, 100);
			BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				// 下面这里按照二维码的算法，逐个生成二维码的图片，//两个for循环是图片横列扫描的结果
				for (int x = 0; x < QR_WIDTH; x++) {
					// if (bitMatrix.get(x, y))
					// pixels[y * QR_WIDTH + x] = 0xff000000;
					// else
					// pixels[y * QR_WIDTH + x] = 0xffffffff;
					if (bitMatrix.get(x, y)) {
						if (x < QR_WIDTH / 2 && y < QR_HEIGHT / 2) {
							pixels[y * QR_WIDTH + x] = 0xFF000000;// 蓝色
							Integer.toHexString(new Random().nextInt());
						} else if (x < QR_WIDTH / 2 && y > QR_HEIGHT / 2) {
							pixels[y * QR_WIDTH + x] = 0xFF000000;// 黄色
						} else if (x > QR_WIDTH / 2 && y > QR_HEIGHT / 2) {
							pixels[y * QR_WIDTH + x] = 0xFF000000;// 绿色
						} else {
							pixels[y * QR_WIDTH + x] = 0xFF000000;// 黑色
						}
					}

					else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;// 白色
					}
				}
			}
			// ------------------添加图片部分------------------//
			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
			// 设置像素点
			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			// 获取图片宽高
			int logoWidth = logoBmp.getWidth();
			int logoHeight = logoBmp.getHeight();
			if (QR_WIDTH == 0 || QR_HEIGHT == 0) {
				return null;
			}
			if (logoWidth == 0 || logoHeight == 0) {
				return bitmap;
			}
			// 图片绘制在二维码中央，合成二维码图片
			// logo大小为二维码整体大小的1/2
			float scaleFactor = QR_WIDTH * 1.0f / 4 / logoWidth;
			try {
				Canvas canvas = new Canvas(bitmap);
				canvas.drawBitmap(bitmap, 0, 0, null);
				canvas.scale(scaleFactor, scaleFactor, QR_WIDTH / 2, QR_HEIGHT / 2);
				canvas.drawBitmap(logoBmp, (QR_WIDTH - logoWidth) / 2, (QR_HEIGHT - logoHeight) / 2, null);
				canvas.save(Canvas.ALL_SAVE_FLAG);
				canvas.restore();

				return bitmap;
			} catch (Exception e) {
				bitmap = null;
				e.getStackTrace();
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public void initdata() {
		HttpWallet.getUserExp(new CallbackLogic() {

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub

				UserWalletListData.save(getApplicationContext(), data.toString());
				WithdrawData.setWithdrawData(getApplicationContext(), "Wallet",
						UserWalletListData.getWalletItemData(getApplicationContext(), "Wallet"));
				System.out.println("Cash=" + WithdrawData.getWithdrawData(getApplicationContext(), "Wallet"));
				WithdrawData.setWithdrawData(getApplicationContext(), "UnPayOrderCount",
						UserWalletListData.getWalletItemData(getApplicationContext(), "UnPayOrderCount"));
				WithdrawData.setWithdrawData(getApplicationContext(), "PayedOrderCount",
						UserWalletListData.getWalletItemData(getApplicationContext(), "PayedOrderCount"));
				WithdrawData.setWithdrawData(getApplicationContext(), "ServiceOrderCount",
						UserWalletListData.getWalletItemData(getApplicationContext(), "ServiceOrderCount"));
				WithdrawData.setWithdrawData(getApplicationContext(), "UnCommentOrderCount",
						UserWalletListData.getWalletItemData(getApplicationContext(), "UnCommentOrderCount"));
				WithdrawData.setWithdrawData(getApplicationContext(), "ReturnOrderCount",
						UserWalletListData.getWalletItemData(getApplicationContext(), "ReturnOrderCount"));
				WithdrawData.setWithdrawData(getApplicationContext(), "TicketCount",
						UserWalletListData.getWalletItemData(getApplicationContext(), "TicketCount"));
				WithdrawData.setWithdrawData(getApplicationContext(), "Score",
						UserWalletListData.getWalletItemData(getApplicationContext(), "Score"));
				WithdrawData.setWithdrawData(getApplicationContext(), "NeedInviteCount",
						UserWalletListData.getWalletItemData(getApplicationContext(), "NeedInviteCount"));
				WithdrawData.setWithdrawData(getApplicationContext(), "InviteCount",
						UserWalletListData.getWalletItemData(getApplicationContext(), "InviteCount"));
				WithdrawData.setWithdrawData(getApplicationContext(), "SortOrder",
						UserWalletListData.getWalletItemData(getApplicationContext(), "SortOrder"));
				WithdrawData.setWithdrawData(getApplicationContext(), "Award",
						UserWalletListData.getWalletItemData(getApplicationContext(), "Award"));
				integration.setText(WithdrawData.getWithdrawData(getApplicationContext(), "Score"));
				numcard.setText(WithdrawData.getWithdrawData(getApplicationContext(), "TicketCount"));
				textdaifukuan.setText(WithdrawData.getWithdrawData(getApplicationContext(), "UnPayOrderCount"));
				if (!WithdrawData.getWithdrawData(getApplicationContext(), "UnPayOrderCount").equals("0")) {
					textdaifukuan.setVisibility(View.VISIBLE);
				}else {
					textdaifukuan.setVisibility(View.GONE);
				}

				textyifukuan.setText(WithdrawData.getWithdrawData(getApplicationContext(), "PayedOrderCount"));
				textfuwuzhong.setText(WithdrawData.getWithdrawData(getApplicationContext(), "ServiceOrderCount"));
				textdaipingjia.setText(WithdrawData.getWithdrawData(getApplicationContext(), "UnCommentOrderCount"));
				texttuikuan.setText(WithdrawData.getWithdrawData(getApplicationContext(), "ReturnOrderCount"));
				if (!WithdrawData.getWithdrawData(getApplicationContext(), "ServiceOrderCount").equals("0")) {
					textfuwuzhong.setVisibility(View.VISIBLE);
				}else {
					textfuwuzhong.setVisibility(View.GONE);
				}
				if (!WithdrawData.getWithdrawData(getApplicationContext(), "PayedOrderCount").equals("0")) {
					textyifukuan.setVisibility(View.VISIBLE);
				}else {
					textyifukuan.setVisibility(View.GONE);
					
				}
				if (!WithdrawData.getWithdrawData(getApplicationContext(), "UnCommentOrderCount").equals("0")) {
					textdaipingjia.setVisibility(View.VISIBLE);
				}else {
					textdaipingjia.setVisibility(View.GONE);
				}
				if (!WithdrawData.getWithdrawData(getApplicationContext(), "ReturnOrderCount").equals("0")) {
					texttuikuan.setVisibility(View.VISIBLE);
				}else {
					texttuikuan.setVisibility(View.GONE);
				}
				textyue.setText(UserWalletListData.getWalletItemData(getApplicationContext(), "Wallet"));
			}
		}, UserLoginStatus.get(MainMyActivity.this, "Token"), MainMyActivity.this);
		ResourceUtil.setSimpleDraweeViewImage(simpleDraweeView,
				UserLoginStatus.get(getApplicationContext(), "IconUrl"));
		carname.setText(UserCarDataSave.get(getApplicationContext(), "CarName"));
		nickname.setText(UserLoginStatus.get(getApplicationContext(), "NickName"));
		memoryCache = new ImageMemoryCache(this);
		fileCache = new ImageFileCache();
		url = Config.DATA_SERVER_URL + "/app/sendUInvitation?InviteCode="
				+ UserLoginStatus.get(MainMyActivity.this, "InviteKey");
		if (UserLoginStatus.get(MainMyActivity.this, "IconUrl").equals("")) {
			bitmap_user_icon=BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		}else {
			bitmap_user_icon = getBitmap(
					Config.IMAGE_SERVER_URL + "/" + UserLoginStatus.get(MainMyActivity.this, "IconUrl"));
		}
		

	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

	public void tiaozhuang(String DuiBaLoginUrl) {

		Intent intent = new Intent();
		intent.setClass(MainMyActivity.this, CreditActivity.class);
		intent.putExtra("navColor", "#ffffff"); // 配置导航条的背景颜色，请用#ffffff长格式。
		intent.putExtra("titleColor", "#000000"); // 配置导航条标题的颜色，请用#ffffff长格式。
		intent.putExtra("url", DuiBaLoginUrl); // 配置自动登陆地址，每次需服务端动态生成。
		startActivity(intent);

		CreditActivity.creditsListener = new CreditsListener() {
			/**
			 * 当点击分享按钮被点击
			 * 
			 * @param shareUrl
			 *            分享的地址
			 * @param shareThumbnail
			 *            分享的缩略图
			 * @param shareTitle
			 *            分享的标题
			 * @param shareSubtitle
			 *            分享的副标题
			 */
			public void onShareClick(WebView webView, String shareUrl, String shareThumbnail, String shareTitle,
					String shareSubtitle) {
				// 当分享按钮被点击时，会调用此处代码。在这里处理分享的业务逻辑。
				new AlertDialog.Builder(webView.getContext())
						.setTitle("分享信息").setItems(new String[] { "标题：" + shareTitle, "副标题：" + shareSubtitle,
								"缩略图地址：" + shareThumbnail, "链接：" + shareUrl }, null)
						.setNegativeButton("确定", null).show();
			}

			/**
			 * 当点击登录
			 * 
			 * @param webView
			 *            用于登录成功后返回到当前的webview并刷新。
			 * @param currentUrl
			 *            当前页面的url
			 */
			public void onLoginClick(WebView webView, String currentUrl) {
				// 当未登录的用户点击去登录时，会调用此处代码。
				// 为了用户登录后能回到之前未登录前的页面。
				// 当用户登录成功后，需要重新动态生成一次自动登录url，需包含redirect参数，将currentUrl放入redirect参数。
				new AlertDialog.Builder(webView.getContext()).setTitle("跳转登录").setMessage("跳转到登录页面？")
						.setPositiveButton("是", null).setNegativeButton("否", null).show();
			}

			/**
			 * 当点击“复制”按钮时，触发该方法，回调获取到券码code
			 * 
			 * @param webView
			 *            webview对象。
			 * @param code
			 *            复制的券码
			 */
			public void onCopyCode(WebView webView, String code) {
				// 当未登录的用户点击去登录时，会调用此处代码。
				new AlertDialog.Builder(webView.getContext()).setTitle("复制券码").setMessage("已复制，券码为：" + code)
						.setPositiveButton("是", null).setNegativeButton("否", null).show();
			}

			/**
			 * 积分商城返回首页刷新积分时，触发该方法。
			 */
			public void onLocalRefresh(WebView mWebView, String credits) {
				// String credits为积分商城返回的最新积分，不保证准确。
				// 触发更新本地积分，这里建议用ajax向自己服务器请求积分值，比较准确。
				Toast.makeText(MainMyActivity.this, "触发本地刷新积分：" + credits, Toast.LENGTH_SHORT).show();
			}
		};
	}

	// 返回键重写
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				// Toast.makeText(getApplicationContext(), "再按一次退出程序",
				// Toast.LENGTH_SHORT).show();
				showToast(getString(R.string.press_agin_to_back));
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();//这句代码要注析
	}

}
