package com.bcwcar.android.bencar.activity.MainMy;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpMainWavelength;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.squareup.okhttp.Request;

import android.R.string;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 订单评价页
 */
public class EvaluateActivity extends BaseActivity {
	private static final String LOG_TAG = EvaluateActivity.class.getSimpleName();
	private String mOrderId;
	private LinearLayout add_imageview001;
	private static LinearLayout add_imageview002;
	private LinearLayout zhaoxiang,zhaoxiang_bendi,zhaoxiang_quxiao;
	private ImageView imageView001;
	private Button fabu;
	private EditText content;
	private RatingBar ratingBar;
	private static Context context;
	private CheckBox checkBox;
	String niming="1";
	Bitmap bitmap;
	private static List<String> list = new ArrayList<String>();// 接收选择的图片的路劲
	private static List<String> list_imageurl = new ArrayList<String>();// 接收服务器返回的路径
	private static int zhizhen = 0;// 判断
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (list.size()!=0) {
				
				if (msg.what < list.size()) {
					list_imageurl.add(msg.obj.toString());
				} else {
					
					StringBuffer sBuffer = new StringBuffer();
					for (int i = 0; i < list_imageurl.size(); i++) {
						sBuffer.append(list_imageurl.get(i).toString() + ",");
					}
					sBuffer.deleteCharAt(sBuffer.length() - 1);
					fabiao(content.getText().toString(), sBuffer.toString(), String.valueOf(ratingBar.getRating()));
				}
			}else {
				fabiao(content.getText().toString(), "", String.valueOf(ratingBar.getRating()));
			}
		};

	};
	private String OrderId;
	private RelativeLayout system_show;
	private String OrderType;
	private String type;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;
	private static final int PHOTO_REQUEST_GALLERY = 2;

	
	
	
	
	@Override
	public void setPageTitle(ViewGroup titleParentView){

		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView imageView=(TextView)rootView.findViewById(R.id.common_title_1_right_view);
	     imageView.setVisibility(View.GONE);
		// leftView.setVisibility(View.GONE);
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("订单评价");
		
		changeFonts(titleParentView);
	}

	
	
	
	
	
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_my_order_evaluate, bodyParentView);
		changeFonts(bodyParentView);
	}

	

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// LayoutInflater.from(this).inflate(R.layout.common_toolbar_1,
		// toolBarParentView);
	}

	
	
	/**
     * 按正方形裁切图片
     */
    public static Bitmap ImageCrop(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

        int retX = w > h ? (w - h) / 2 : 0;//基于原图，取正方形左上角x坐标
        int retY = w > h ? 0 : (h - w) / 2;

        //下面这句是关键
        return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
    }
	//an按uri 获取bitmap
    private Bitmap getBitmapFromUri(Uri uri) {
		try {
			// 读取uri所在的图片
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
			return bitmap;
		} catch (Exception e) {
			Log.e("[Android]", e.getMessage());
			Log.e("[Android]", "目录为：" + uri);
			e.printStackTrace();
			return null;
		}
	}
	 /**
		 * 将图片保存到本地图片上
		 * */
        public String savePublishPicture(Bitmap bitmap) {
		 String publishfilename=System.currentTimeMillis()+ ".jpg";
		 File f = new
		 File(Environment.getExternalStorageDirectory(),publishfilename);
		 try {
		 if (f.exists()) {
		 f.delete();
		 }
		 f.createNewFile();
		 FileOutputStream fOut = null;
		 fOut = new FileOutputStream(f);
		 BufferedOutputStream bos = new BufferedOutputStream(fOut);
		 compressImage(bitmap).compress(Bitmap.CompressFormat.JPEG, 100, bos);// 把Bitmap对象解析成流
		 fOut.flush();
		 fOut.close();
		
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
		 return Environment.getExternalStorageDirectory()+"/"+publishfilename;
		 }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:
			File f = new File(Environment.getExternalStorageDirectory() + "/bencar/takephoto.jpg");
			try {
				Uri u = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(),
						f.getAbsolutePath(), null, null));
				String path=savePublishPicture(ImageCrop(getBitmapFromUri(u)));
				
				EvaluateActivity.addview(ImageCrop(getBitmapFromUri(u)),path);
				
				// u就是拍摄获得的原始图片的uri，剩下的你想干神马坏事请便……
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		case PHOTO_REQUEST_GALLERY:
			if (data != null) {
				Bitmap bm = null;
				// 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
				ContentResolver resolver = getContentResolver();
				// 此处的用于判断接收的Activity是不是你想要的那个

				try {
					Uri originalUri = data.getData(); // 获得图片的uri
					bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
					// 这里开始的第二部分，获取图片的路径：
					String[] proj = { MediaStore.Images.Media.DATA };
					// 好像是android多媒体数据库的封装接口，具体的看Android文档
					Cursor cursor = managedQuery(originalUri, proj, null, null, null);
					// 按我个人理解 这个是获得用户选择的图片的索引值
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					// 将光标移至开头 ，这个很重要，不小心很容易引起越界
					cursor.moveToFirst();
					// 最后根据索引值获取图片路径

					String path = cursor.getString(column_index);
					String path001=savePublishPicture(ImageCrop(bm));
					EvaluateActivity.addview(ImageCrop(bm), path001);
				} catch (IOException e) {
					Log.e("TAG-->Error", e.toString());
				}
			}
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		list.clear();
		context = this;
		initview();

		OrderId = getIntent().getStringExtra("order_id");
		OrderType=getIntent().getStringExtra("OrderType");
		if (OrderType.equals("3")) {
			type="1";
		}else {
			type="";
		}
	}

	// 实例化控件
	public void initview() {
		zhaoxiang=(LinearLayout)findViewById(R.id.LinearLayout_camera_show_zhaoxiang001);
		zhaoxiang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 先验证手机是否有sdcard
				system_show.setVisibility(View.GONE);
							String status = Environment.getExternalStorageState();
							if (status.equals(Environment.MEDIA_MOUNTED)) {
								try {
									File dir = new File(Environment.getExternalStorageDirectory() + "/bencar");
									if (!dir.exists())
										dir.mkdirs();

									Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
									File f = new File(dir, "takephoto.jpg");// localTempImgDir和localTempImageFileName是自己定义的名字
									Uri u = Uri.fromFile(f);
									intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
									intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
									startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
								} catch (ActivityNotFoundException e) {
									// TODO Auto-generated catch block
									showToast("没有找到储存目录");
								}
							} else {
								showToast("没有储存卡");
							}
			}
		});
		zhaoxiang_bendi=(LinearLayout)findViewById(R.id.LinearLayout_camera_show_bendi001);
		zhaoxiang_bendi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				system_show.setVisibility(View.GONE);
				Intent intent2 = new Intent(Intent.ACTION_PICK, null);
				intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent2, PHOTO_REQUEST_GALLERY);
			}
		});
		zhaoxiang_quxiao=(LinearLayout)findViewById(R.id.LinearLayout_camera_show_quxiao001);
		zhaoxiang_quxiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				system_show.setVisibility(View.GONE);
			}
		});
		system_show=(RelativeLayout)findViewById(R.id.RelativeLayout_camera_show001);
		system_show.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				system_show.setVisibility(View.GONE);
			}
		});


		
		
		
		
		add_imageview001 = (LinearLayout) findViewById(R.id.LinearLayout_add_imageview001);// imageview父类布局
		add_imageview001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (list.size() > 3) {
					Toast.makeText(context, "最多可添加4张图！", Toast.LENGTH_SHORT).show();
				} else {
					system_show.setVisibility(View.VISIBLE);
				}
			}
		});
		checkBox=(CheckBox)findViewById(R.id.checkpinglun);
		checkBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkBox.isChecked()) {
					niming="2";
				}else {
					niming="1";
				}
			}
		});
		add_imageview002 = (LinearLayout) findViewById(R.id.LinearLayout_add_imageview002);// imageview添加的父类布局
		imageView001 = (ImageView) findViewById(R.id.ImageView_add001);
		fabu = (Button) findViewById(R.id.Button_fabu_tijaio001);// 发布信息 按钮
		ratingBar = (RatingBar) findViewById(R.id.my_order_evaluate_OrderScore);
		fabu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {

					if (content.getText().toString().equals("")) {
						showToast("请输入内容！");
					} else {
						send();

					}
				} catch (Exception e) {
					// TODO: handle exception

				}
			}
		});
		content = (EditText) findViewById(R.id.my_order_evaluate_OrderComment);
	}

	// 发表文章
	public void fabiao(String content, String pics, String orderScore) {
		
		HttpOrder.orderEvaluate(new CallbackLogic() {
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				showToast(responseDescription);
				finish();
			}
		}, UserLoginStatus.get(EvaluateActivity.this,"Token"), OrderId, orderScore, content, pics,niming,EvaluateActivity.this,type);

	}

	// 提交图片

	public void send() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					for (int i = 0; i < list.size() + 1; i++) {
						if (i < list.size()) {

							Message message = new Message();
							message.what = i;
							message.obj = upload(UserLoginStatus.get(EvaluateActivity.this,"Token"), url_adress,
									list.get(i).toString())+"";
							handler.sendMessage(message);
						} else {
							handler.sendEmptyMessage(list.size() + 1);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	// 添加图片
	public static void addview(Bitmap bitmap, String url) {
		try {
			
			list.add(url);
			ImageView imageView = new ImageView(context);
			imageView.setImageBitmap(bitmap);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(get_windows_width(context)/5,get_windows_width(context)/5);
			lp.setMargins(5, 5, 5, 10);
			imageView.setLayoutParams(lp);
			add_imageview002.addView(imageView);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
