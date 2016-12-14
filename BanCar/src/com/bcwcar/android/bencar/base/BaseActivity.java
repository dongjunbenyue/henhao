package com.bcwcar.android.bencar.base;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainMy.LoginActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.util.FastBlur;
import com.bcwcar.android.bencar.widget.XlistView.XListView;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 继承这个BaseActivity的类，具有固定的标题+内容布局样式，
 * 需要实现getPageTitleView（）和getPageBodyView（）方法来指定标题和内容，
 * 在子类的onCreate()方法中，请不要再调用setContentView()方法
 * 
 * @author Administrator
 */
public abstract class BaseActivity extends FragmentActivity implements IBaseActivity {
	protected FrameLayout mTitleLayout, mBodyLayout, mToolBarLayout;
	public static Dialog dialog_save;
	public static AnimationDrawable anim;
	public String url_adress = com.bcwcar.android.bencar.config.Config.DATA_SERVER_URL + "/app/file/uploadPicture";
	public InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 禁止显示标题栏或ActionBar
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 禁止横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_base);
         imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		mTitleLayout = (FrameLayout) findViewById(R.id.base_xml_title_layout);
		mBodyLayout = (FrameLayout) findViewById(R.id.base_xml_body_layout);
		mToolBarLayout = (FrameLayout) findViewById(R.id.base_xml_toolbar_layout);

		setPageTitle(mTitleLayout);
		// 如果没有在子Activity中设置标题，则将title区域设置成GONE
		if (mTitleLayout.getChildCount() == 0) {
			mTitleLayout.setVisibility(View.GONE);
		}
		setPageBody(mBodyLayout);
		// if(mBodyLayout.getChildCount() == 0) {
		// mBodyLayout.setVisibility(View.GONE);
		// }
		setPageToolBar(mToolBarLayout);
		// 如果没有在子Activity中设置Tool bar，则将ToolBar区域设置成GONE
		if (mToolBarLayout.getChildCount() == 0) {
			mToolBarLayout.setVisibility(View.GONE);
		}

	}

	// listview 高度计算
	public static void setListViewHeight(ListView listView) {
		int totalHeight = 0;
		ListAdapter listAdapter = listView.getAdapter();
		for (int i = 0; i < listAdapter.getCount(); i++) {

			View listItemView = listAdapter.getView(i, null, listView);
			int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
			listItemView.measure(desiredWidth, 0);
			totalHeight += listItemView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight +20;
		listView.setLayoutParams(params);
	}

	// gridView 高度计算
	public static void setGridViewHeight(GridView gridView) {
		int totalHeight = 0;
		ListAdapter listAdapter = gridView.getAdapter();
		for (int i = 0; i < listAdapter.getCount() / 5; i++) {

			View listItemView = listAdapter.getView(i, null, gridView);
			int desiredWidth = MeasureSpec.makeMeasureSpec(gridView.getWidth(), MeasureSpec.AT_MOST);
			listItemView.measure(desiredWidth, 0);
			totalHeight += listItemView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight+20;
		gridView.setLayoutParams(params);

	}

	//加模糊效果
	@SuppressLint("NewApi")  
	public  void blur(Bitmap bkg, ImageView view,Context context) {  
	    long startMs = System.currentTimeMillis();  
	    float radius = 10;//模糊程度  
	    Bitmap bmp = Bitmap.createScaledBitmap(bkg, get_windows_width(context), view.getMeasuredHeight(), true);
	    bmp = FastBlur.doBlur(bmp, (int) radius, true);  
	    view.setBackground(new BitmapDrawable(getResources(), bmp)); 
	    /** 
	     * 打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，可是将scaleFactor设置大一些。 
	     */  
	    Log.i("jerome", "blur time:" + (System.currentTimeMillis() - startMs));  
	}
	
	// 设置图片的宽高
	public void SimpleDraweeView_WH(SimpleDraweeView mSimpleDraweeView, int width, int height, String uri) {

		ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
				.setResizeOptions(new ResizeOptions(width, height)).build();
		DraweeController controller = Fresco.newDraweeControllerBuilder().setImageRequest(request)
				.setOldController(mSimpleDraweeView.getController())
				// 其他设置
				.build();
		mSimpleDraweeView.setController(controller);
	}

	// 设置NumberPicker分割线的颜色值
	public void setNumberPickerDividerColor(NumberPicker numberPicker) {
		NumberPicker picker = numberPicker;
		Field[] pickerFields = NumberPicker.class.getDeclaredFields();
		for (Field pf : pickerFields) {
			if (pf.getName().equals("mSelectionDivider")) {
				pf.setAccessible(true);
				try {
					// 设置分割线的颜色值
					pf.set(picker, new ColorDrawable(this.getResources().getColor(R.color.mainblue)));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Resources.NotFoundException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	// 添加图片

	public static void image_size(ImageView imageView,Context context) {
		try {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(get_windows_width(context) / 5,
					get_windows_width(context) / 5);
			lp.setMargins(5, 5, 5, 5);
			imageView.setLayoutParams(lp);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static void image_size1(ImageView imageView,Context context) {
		try {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(get_windows_width(context) / 10,
					get_windows_width(context) / 10);

			imageView.setLayoutParams(lp);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// id转bitmap
	public static Bitmap ReadBitmapById(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;

		InputStream is = context.getResources().openRawResource(resId);
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(is, null, opt);
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
		}
		return bitmap;
	}

	/**
	 * fuction: 设置固定的宽度，高度随之变化，使图片不会变形
	 * 
	 * @param target
	 *            需要转化bitmap参数
	 * @param newWidth
	 *            设置新的宽度
	 * @return
	 */
	public static Bitmap fitBitmap(Bitmap target, int newWidth) {

		int width = target.getWidth();
		int height = target.getHeight();
		Matrix matrix = new Matrix();

		float scaleWidth = ((float) newWidth) / width;
		int newHeight = (int) (scaleWidth * height);
		matrix.postScale(scaleWidth, scaleWidth);
		Bitmap bmp = null;
		try {

			bmp = Bitmap.createScaledBitmap(target, newWidth, newHeight, true);
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
		}
		if (target != null && !target.equals(bmp) && !target.isRecycled()) {
			// 选择性=======释放资源
		}
		return bmp;// Bitmap.createBitmap(target, 0, 0, width, height, matrix,
					// true);
	}

	// 接口请求错误 重新登陆
	public void denglu_reset(String responseCode, String responseDescription, final Context context) {
		if (responseCode.equals("101") || responseCode.equals("103")) {
			showToast("尚未登录，请登录");
			UserLoginStatus.clear(getApplicationContext());
			Intent newIntent = new Intent(context, LoginActivity.class);
			context.startActivity(newIntent);
		}
	}
	
	// 头像圆角
	public static Bitmap setRoundCorner(Bitmap bitmap) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int left = 0, top = 0, right = width, bottom = height;
		float roundPx = height / 2;
		if (width > height) {
			left = (width - height) / 2;
			top = 0;
			right = left + height;
			bottom = height;
		} else if (height > width) {
			left = 0;
			top = (height - width) / 2;
			right = width;
			bottom = top + width;
			roundPx = width / 2;
		}
		Bitmap output = null;
		try {
			output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			int color = 0xff424242;
			Paint paint = new Paint();
			Rect rect = new Rect(left, top, right, bottom);
			RectF rectF = new RectF(rect);

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
		}
		return output;
	}

	/**
	 * function:图片转圆角
	 * 
	 * @param bitmap
	 *            需要转的bitmap
	 * @param pixels
	 *            转圆角的弧度
	 * @return 转圆角的bitmap
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = null;
		try {

			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final int color = 0xff424242;
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(rect);
			final float roundPx = pixels;
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
			if (bitmap != null && !bitmap.isRecycled()) {
				// bitmap.recycle();
			}
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
		}
		return output;
	}

	/// 加密MD5

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];

	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	// 获取屏幕宽高
	public static int get_windows_width(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	// 获取屏幕宽高
	public static int get_windows_height(Context context) {

		return context.getResources().getDisplayMetrics().heightPixels;
	}

	// 质量压缩
	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int tt = baos.toByteArray().length / 102400;
		if (tt > 5) {
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		} else {
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	protected static Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		System.out.println(bitmap.getByteCount() + "dongjun");
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	public static void changeFonts(ViewGroup root) {

		Typeface tf = BaseApplication.tf;

		for (int i = 0; i < root.getChildCount(); i++) {

			View v = root.getChildAt(i);

			if (v instanceof TextView) {

				((TextView) v).setTypeface(tf);

			} else if (v instanceof Button) {

				((Button) v).setTypeface(tf);

			} else if (v instanceof EditText) {

				((EditText) v).setTypeface(tf);

			} else if (v instanceof ViewGroup) {

				changeFonts((ViewGroup) v);

			}

		}
	}

	/**
	 * 圆形进度条
	 */
	public static void Dialogshow(Context context) {
		try {
			if (context!=null) {
				creat_dialog(context);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}
	}

	public static void Dialogcancel() {
		if (dialog_save != null) {
			dialog_save.cancel();
			anim.stop();
			dialog_save = null;
		}
	}

	public static void creat_dialog(Context context) {
		// 自定义布局
		View layout = LayoutInflater.from(context).inflate(R.layout.mydialogloading, null);
		ImageView loading = (ImageView) layout.findViewById(R.id.img_loding001);
		// 加载动画
		anim = (AnimationDrawable) loading.getBackground();
		dialog_save = new Dialog(context, R.style.MyDialog);
		dialog_save.setContentView(layout, new LayoutParams(get_windows_width(context), get_windows_height(context)));
		anim.start();
		dialog_save.show();
	}

	// /* 上传文件至Server的方法 */
	// // 上传
	// public String upload(String token, String url, String post_pic) {
	// String result = null;
	// try {
	//
	// String token_ut = URLEncoder.encode(token, "utf-8");
	// HttpClient client = new DefaultHttpClient();
	// client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
	// HttpVersion.HTTP_1_1);
	// HttpPost httpPost = new HttpPost(url);
	// System.out.println("ddddddddddddddddddddddddddddd***********");
	// File file = new File(post_pic);
	// if (file == null) {
	// } else {
	// StringBody token001 = new StringBody(token_ut);
	//
	// FileBody body = new FileBody(file);
	// MultipartEntity entity = new MultipartEntity();
	// entity.addPart("PicUrl", body);
	// entity.addPart("Token", token001);
	// httpPost.setEntity(entity);
	// }
	// HttpResponse response;
	// response = client.execute(httpPost);
	// HttpEntity resEntity = response.getEntity();
	// if (resEntity != null) {
	// String string = EntityUtils.toString(resEntity);
	// JSONObject jsonObject = new JSONObject(string);
	// result = jsonObject.getString("PicUrl").toString();
	// System.out.println("reslut"+result);
	// } else {
	// }
	// // }
	// client.getConnectionManager().shutdown();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return result;
	// }
	/* 上传文件至Server的方法 */
	// 上传
	public String upload(String token, String url, String post_pic) {
		String string = null;
		try {
			String token_ut = URLEncoder.encode(token, "utf-8");
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpPost httpPost = new HttpPost(url);
			System.out.println("url************" + url);
			System.out.println("post_pic************" + post_pic);
			System.out.println("token************" + token);
			File file = new File(post_pic);
			if (file == null) {
			} else {
				StringBody token001 = new StringBody(token_ut);

				FileBody body = new FileBody(file);
				MultipartEntity entity = new MultipartEntity();
				entity.addPart("PicUrl", body);
				entity.addPart("Token", token001);
				httpPost.setEntity(entity);
			}
			HttpResponse response;
			response = client.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				string = EntityUtils.toString(resEntity);
			} else {
			}
			// }
			client.getConnectionManager().shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param info
	 *            显示的内容
	 */
	public  void showToast(String info) {
		Toast.makeText(BaseActivity.this, info, Toast.LENGTH_SHORT).show();
	}

	public static boolean isPassWord(String mobiles) {
		Pattern p = Pattern.compile("^(?=.*[0-9].*)(?=.*[A-Za-z].*).{6,20}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}

	/**
	 * 
	 * 违章数据查询
	 */
	public void set_weizhang_data(String key, String value) {
		SharedPreferences dingdan = getSharedPreferences("weizhang", 0);
		SharedPreferences.Editor editor = dingdan.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String get_weizhang_data(String key) {
		SharedPreferences dingdan = getSharedPreferences("weizhang", 0);
		String string = dingdan.getString(key, "").toString();
		return string;
	}

	/**
	 * 领奖参数
	 */
	public void set_lingjiang_data(String key, String value) {
		SharedPreferences lingjiang = getSharedPreferences("lingjiang", 0);
		SharedPreferences.Editor editor = lingjiang.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String get_lingjiang_data(String key) {
		SharedPreferences lingjiang = getSharedPreferences("lingjiang", 0);
		String string = lingjiang.getString(key, "").toString();
		return string;

	}

	// 交通管理局 数据查询
	public String weizhang_search(int tt, String string) {
		String httpUrl = null;
		if (tt == 0) {
			httpUrl = "http://apis.baidu.com/netpopo/illegal/illegal1";
		} else if (tt == 1) {
			httpUrl = "http://apis.baidu.com/netpopo/illegal/illegal";
		}
		String httpArg = string;
		String jsonResult = request(httpUrl, httpArg);
		return jsonResult;
	}

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", "df079ddfae865c4e88cd96fb4351e5ce");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String request11(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header

			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
  
	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}



}