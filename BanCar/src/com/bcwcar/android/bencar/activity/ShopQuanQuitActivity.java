package com.bcwcar.android.bencar.activity;

import java.util.HashMap;
import java.util.Map;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainMy.ConfermOrder;
import com.bcwcar.android.bencar.base.BaiDuNavigation;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.GPS_Positioning;
import com.bcwcar.android.bencar.base.ImageFileCache;
import com.bcwcar.android.bencar.base.ImageGetFromHttp;
import com.bcwcar.android.bencar.base.ImageMemoryCache;
import com.bcwcar.android.bencar.config.Config;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.QuanDingDanDataSave;
import com.bcwcar.android.bencar.util.ResourceUtil;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopQuanQuitActivity extends BaseActivity {
	private BaiDuNavigation openBaiduMap;
	private Bitmap bitmap;
	private ImageMemoryCache memoryCache;
	private ImageFileCache fileCache;
private ImageView muhou_imageview;
private TextView shop_name,quan_head_shuoming,quan_shiyong_xuzhi,
quan_price,quan_shjiprice,quan_sales_number,shop_adress,shop_phone,shop_changshang,shop_brand,pay;
private LinearLayout phone_LinearLayout,adress_LinearLayout;
private Handler mHandler = new Handler() {
	public void handleMessage(android.os.Message msg) {
		if (msg.what == 101) {

			// ===========
		} else if (msg.what == 0) {
			System.out.println("=================" + msg.obj.toString());
			bitmap = (Bitmap) msg.obj;
			if (bitmap != null) {
				muhou_imageview.setImageBitmap(bitmap);
				fileCache.saveBitmap(bitmap, Config.IMAGE_SERVER_URL + "/" + QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"LogoUrl").toString());
				memoryCache.addBitmapToCache(Config.IMAGE_SERVER_URL + "/" +QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"LogoUrl").toString(), bitmap);

			}
		}
	};

};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new GPS_Positioning(ShopQuanQuitActivity.this, mHandler).gps();
		memoryCache = new ImageMemoryCache(this);
		fileCache = new ImageFileCache();
		initview();
		initvar();
	}
	
	public void initview(){
		pay=(TextView)findViewById(R.id.TextView_quan_info_pay001);
		
		pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (QuanDingDanDataSave.get(getApplicationContext(), "TicketFlag").equals("7")) {
					Intent intent=new Intent(ShopQuanQuitActivity.this,PayQuanActivity.class);
					startActivity(intent);
				}else {
					Intent intent=new Intent(ShopQuanQuitActivity.this,ConfermOrder.class);
					startActivity(intent);
				}
				
				
			}
		});
		muhou_imageview=(ImageView)findViewById(R.id.ImageView_muhou001);
		shop_name=(TextView)findViewById(R.id.TextView_quan_info_shopname001);
		shop_phone=(TextView)findViewById(R.id.Textview_quan_info_xiangxi_phone);
		shop_adress=(TextView)findViewById(R.id.Textview_wangdian_xiangxi_adrress_quan_info001);
		
		quan_head_shuoming=(TextView)findViewById(R.id.TextView_quan_info_shopadress001);
		quan_shiyong_xuzhi=(TextView)findViewById(R.id.TextView_quan_info_shiyongxuzhi001);
		quan_sales_number=(TextView)findViewById(R.id.textview_quan_info_sales_number001);
		quan_price=(TextView)findViewById(R.id.TextView_quan_info_ticket_price001);
		quan_shjiprice=(TextView)findViewById(R.id.TextView_quan_info_ticket_nomarl_price001);
		
		
		
		
		phone_LinearLayout=(LinearLayout)findViewById(R.id.LinearLayout_quan_info_dianhua001);
		phone_LinearLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 拨打电话
				String phoneNumber = shop_phone.getText().toString();
				if (!phoneNumber.matches("\\d+")) {
					Toast.makeText(ShopQuanQuitActivity.this, R.string.shop4s_info_call_phone_error, Toast.LENGTH_SHORT)
							.show();
				} else {
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
					startActivity(intent);
				}

			}
		});
		adress_LinearLayout=(LinearLayout)findViewById(R.id.LinearLayout_quan_info_dingwei_daohang0011);
		adress_LinearLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				openBaiduMap = new BaiDuNavigation(getApplicationContext(),
						Double.valueOf(LocationDataSave.get(ShopQuanQuitActivity.this, "Latitude")).doubleValue(),
						Double.valueOf(LocationDataSave.get(ShopQuanQuitActivity.this, "Longitude")).doubleValue(),
						Double.valueOf(QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"Latitude").toString()).doubleValue(),
						Double.valueOf(QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"Longitude").toString()).doubleValue());
				openBaiduMap.startNavi();
				System.out.println(QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"Latitude").toString()+","+QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"Longitude").toString());
			}
		});
		
	}
	
	public void initvar(){
		quan_price.setText("￥"+QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"ActualPrice").toString());
		quan_head_shuoming.setText(QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"Propaganda").toString());
		quan_shjiprice.setText("￥"+QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"TotalPrice").toString());
		quan_sales_number.setText(QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"TotalNum").toString());
		quan_shiyong_xuzhi.setText(QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"TicketCare").toString());
		shop_name.setText(QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"AccName").toString());
		shop_phone.setText(QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"ContactPhone").toString());
		shop_adress.setText(QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"Address").toString());
		
		bitmap = getBitmap(Config.IMAGE_SERVER_URL + "/" +QuanDingDanDataSave.get(ShopQuanQuitActivity.this,"LogoUrl").toString());
		if (bitmap != null) {
			muhou_imageview.setImageBitmap(bitmap);
		}
	}
	
	
	// 下载图片
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
				ImageGetFromHttp.downloadBitmap(url, mHandler);
			} else {
				// 添加到内存缓存
				memoryCache.addBitmapToCache(url, result);
			}
		}
		return result;
	}
	
	
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_4, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		ImageView rightView = (ImageView) rootView.findViewById(R.id.common_title_1_right_view);
		// leftView.setVisibility(View.GONE);
		leftView.setText("");
		rightView.setVisibility(View.GONE);
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("商品详情");
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.shop_quan_pay_xml001, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

}
