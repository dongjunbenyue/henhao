package com.bcwcar.android.bencar.activity.MainMy;

import java.util.Hashtable;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.ImageFileCache;
import com.bcwcar.android.bencar.base.ImageGetFromHttp;
import com.bcwcar.android.bencar.base.ImageMemoryCache;
import com.bcwcar.android.bencar.biz.HttpUserInfo;
import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.http.AsyncBitmapLoader;
import com.bcwcar.android.bencar.http.AsyncBitmapLoader.ImageCallBack001;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.util.ShareSDKUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FreeMantence extends BaseActivity {

	private TextView textweixin, friends, rank;
	private ImageView sweepIV;
	private TextView textfaceinvite, present_name;
	private LinearLayout line1111, lingjiangline;
	private RelativeLayout weilingjiangline;
	private String url;
	private AsyncBitmapLoader asyncBitmapLoader;
	private SimpleDraweeView present_SimpleDraweeView;

	private Bitmap bitmap;
	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;
	private Bitmap bitmap_user_icon;
	final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			System.out.println("=================" + msg.obj.toString());
			bitmap_user_icon = (Bitmap) msg.obj;
			if (bitmap_user_icon != null) {
				fileCache.saveBitmap(bitmap_user_icon,
						Config.IMAGE_SERVER_URL + "/" + UserLoginStatus.get(FreeMantence.this, "IconUrl"));
				memoryCache.addBitmapToCache(
						Config.IMAGE_SERVER_URL + "/" + UserLoginStatus.get(FreeMantence.this, "IconUrl"),
						bitmap_user_icon);
			}else {
				bitmap_user_icon=BitmapFactory.decodeResource(getResources(), R.drawable.logo);
			}
		}
	};
	private TextView textgotorank;

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub

		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		rightView.setText("规则说明");
		// leftView.setVisibility(View.GONE);
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(FreeMantence.this, Cheatmoneyrule.class);
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("邀请好友");
		changeFonts(titleParentView);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initview();
		initdata();
		getpresent_data();
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.freemantence, bodyParentView);
		changeFonts(bodyParentView);
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

	public void initview() {
		present_name = (TextView) findViewById(R.id.TextView_presents_name001);
		present_SimpleDraweeView = (SimpleDraweeView) findViewById(R.id.SimpleDraweeView_presents_image001);         
		textweixin = (TextView) findViewById(R.id.gotoshareinternt);
		sweepIV = (ImageView) findViewById(R.id.ecodeimgview1);
		friends = (TextView) findViewById(R.id.textrank);
		textfaceinvite = (TextView) findViewById(R.id.facetoface);
		sweepIV = (ImageView) findViewById(R.id.ecodeimgview1);
		line1111 = (LinearLayout) findViewById(R.id.ecodline);
		textgotorank = (TextView) findViewById(R.id.gotoranklist);
		rank = (TextView) findViewById(R.id.textpeople);
		lingjiangline = (LinearLayout) findViewById(R.id.lingjiangline);
		lingjiangline.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(FreeMantence.this, CheatmoneyPrize.class);
			}
		});
 
		weilingjiangline = (RelativeLayout) findViewById(R.id.weilingjiangline);
		textgotorank.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(FreeMantence.this, RankActivity.class);
			}
		});
		line1111.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				line1111.setVisibility(View.GONE);
			}
		});
		textfaceinvite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				try {
					bitmap = makeQRImage(bitmap_user_icon, url, 200, 200);
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				line1111.setVisibility(View.VISIBLE);
				sweepIV.setImageBitmap(bitmap);
			}
		});
		textweixin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShareSDKUtil.showShare(FreeMantence.this, UserLoginStatus.get(getApplicationContext(), "InviteKey"));
			}
		});
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

	public void initdata() {
		memoryCache = new ImageMemoryCache(this);
		fileCache = new ImageFileCache();
		url = Config.DATA_SERVER_URL + "/app/sendUInvitation?InviteCode="
				+ UserLoginStatus.get(FreeMantence.this, "InviteKey");
		if (UserLoginStatus.get(FreeMantence.this, "IconUrl").equals("")) {
			bitmap_user_icon=BitmapFactory.decodeResource(getResources(), R.drawable.logo);
		}else {
			bitmap_user_icon = getBitmap(Config.IMAGE_SERVER_URL + "/" + UserLoginStatus.get(FreeMantence.this, "IconUrl"));
		}
		
		friends.setText(WithdrawData.getWithdrawData(getApplicationContext(), "InviteCount"));
		if ((Integer.parseInt(WithdrawData.getWithdrawData(getApplicationContext(), "SortOrder"))==0)){
			rank.setText((Integer.parseInt(WithdrawData.getWithdrawData(getApplicationContext(), "SortOrder"))) + "");
		}else {
			rank.setText((Integer.parseInt(WithdrawData.getWithdrawData(getApplicationContext(), "SortOrder")) - 1) + "");
		}
		
		if (WithdrawData.getWithdrawData(getApplicationContext(), "Award").equals("1")) {
			lingjiangline.setVisibility(View.VISIBLE);
			weilingjiangline.setVisibility(View.GONE);
		}
	}

	// 获取礼品名称和图像
	public void getpresent_data() {
		HttpUserInfo.getRewardInfo(new CallbackLogic() {

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {

					JSONArray array = data.getJSONArray("Data");
					JSONObject object = array.getJSONObject(0);
					if (array.length() != 0) {
						present_name.setText(object.get("RewardName").toString());
						ResourceUtil.setSimpleDraweeViewImage(present_SimpleDraweeView,
								object.get("PicUrl").toString());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}, UserLoginStatus.get(FreeMantence.this, "Token"), FreeMantence.this);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

}
