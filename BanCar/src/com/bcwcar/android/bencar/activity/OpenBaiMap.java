package com.bcwcar.android.bencar.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.SnapshotReadyCallback;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaiDuNavigation;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.Model;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.datasave.LocationDataSave;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import io.rong.message.LocationMessage;

public class OpenBaiMap extends BaseActivity {
	private TextView navigation, addstr;
	private RelativeLayout RelativeLayout_navigation001;
	private MapView mMapView;
	private BaiduMap mbaidumap;
	MapStatusUpdate msu;
	public LocationClient mLocationClient = null;
	private String action;
	// marker覆盖物
	private Marker marker1;
	// 融云回调
	LocationMessage mMsg;
	private double lat, lon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initview();
		if (action.equals("0")) {
			gps();
		} else if (action.equals("1")) {
			gps();
			if (getIntent().hasExtra("location")) {
				mMsg = getIntent().getParcelableExtra("location");
				addstr.setText(mMsg.getPoi());
				Marker_location(mMsg.getLat(), mMsg.getLng());
			}
		}
	}

	// 6217 9955 1001 2663 471
	// 3.18
	// 4286
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		if (Model.getInstance().getLastLocationCallback() != null)
			Model.getInstance().getLastLocationCallback().onFailure("失败");
		Model.getInstance().setLastLocationCallback(null);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	// 实例化控件
	public void initview() {
		RelativeLayout_navigation001 = (RelativeLayout) findViewById(R.id.RelativeLayout_navigation001);
		addstr = (TextView) findViewById(R.id.TextView_navigation_text001);
		navigation = (TextView) findViewById(R.id.TextView_navigation001);
		navigation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("start=Latitude=" + Double.valueOf(LocationDataSave.get(OpenBaiMap.this,"Latitude")).doubleValue());
				System.out.println("start=Longitude=" + Double.valueOf(LocationDataSave.get(OpenBaiMap.this,"Longitude")).doubleValue());
				System.out.println("end=Latitude=" + mMsg.getLat());
				System.out.println("end=Longitude=" + mMsg.getLng());
				BaiDuNavigation baiDuNavigation = new BaiDuNavigation(getApplicationContext(),
						Double.valueOf(LocationDataSave.get(OpenBaiMap.this,"Latitude")).doubleValue(),
						Double.valueOf(LocationDataSave.get(OpenBaiMap.this,"Longitude")).doubleValue(), mMsg.getLat(), mMsg.getLng());
				baiDuNavigation.startWebNavi();
			}
		});
		mMapView = (MapView) findViewById(R.id.baidu_mapView);
		mbaidumap = mMapView.getMap();
		// 对marker覆盖物添加点击事件
		if (action.equals("1")) {
			RelativeLayout_navigation001.setVisibility(View.VISIBLE);
			mbaidumap.setOnMarkerClickListener(new OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(Marker arg0) {
					if (arg0 == marker1) {
						final LatLng latLng = arg0.getPosition();
						// 将经纬度转换成屏幕上的点
						// Point point =
						// bdMap.getProjection().toScreenLocation(latLng);
						showToast(mMsg.getPoi() + "==" + mMsg.getLat() + "==" + mMsg.getLng());
					}
					return false;
				}
			});
		} else if (action.equals("0")) {
			RelativeLayout_navigation001.setVisibility(View.GONE);
		}
	}

	// 定位
	public void gps() {
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		LocationClientOption locOption = new LocationClientOption();
		locOption.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		locOption.setCoorType("bd09ll");// 设置定位结果类型
		locOption.setScanSpan(5000);// 设置发起定位请求的间隔时间,ms
		locOption.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		locOption.setNeedDeviceDirect(true);// 设置返回结果包含手机的方向
		mLocationClient.setLocOption(locOption);
		mLocationClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation bDLocation) {
				// TODO Auto-generated method stub
				LocationDataSave.save(OpenBaiMap.this, "Latitude", bDLocation.getLatitude() + "");
				LocationDataSave.save(OpenBaiMap.this,"Longitude", bDLocation.getLongitude() + "");
				LocationDataSave.save(OpenBaiMap.this,"CityName", bDLocation.getAddrStr() + "");
				if (action.equals("0")) {
					Marker_location(bDLocation.getLatitude(), bDLocation.getLongitude());
				}
			}
		});
		mLocationClient.start();

	}

	// 标记位置
	public void Marker_location(Double Latitude, Double Lontitude) {
		// 定义Maker坐标点
		LatLng point = new LatLng(Latitude, Lontitude);
		msu = MapStatusUpdateFactory.newLatLngZoom(point, 15.0f);
		if (msu != null) {

			mbaidumap.setMapStatus(msu);
		}
		// 构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.maker);
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
		// 在地图上添加marker，并显示
		marker1 = (Marker) mbaidumap.addOverlay(option);
		// 添加缩略图控件
	}

	// 发送回调截图
	public void sendcallback() {
		String string = "http://api.map.baidu.com/staticimage?width=240&height=240&" + "center="
				+ LocationDataSave.get(OpenBaiMap.this,"Longitude") + "," + LocationDataSave.get(OpenBaiMap.this,"Latitude") + "&zoom=14" + "&markers="
				+ LocationDataSave.get(OpenBaiMap.this,"Longitude") + "," + LocationDataSave.get(OpenBaiMap.this,"Latitude") + "&markerStyles=0xFF0000";
		mMsg = LocationMessage.obtain(Double.valueOf(LocationDataSave.get(OpenBaiMap.this,"Latitude")).doubleValue(),
				Double.valueOf(LocationDataSave.get(OpenBaiMap.this,"Longitude")).doubleValue(), LocationDataSave.get(OpenBaiMap.this,"AddrStr"),
				Uri.parse(string));
		Model.getInstance().getLastLocationCallback().onSuccess(mMsg);
		Model.getInstance().setLastLocationCallback(null);
		finish();
	}

	

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		action=getIntent().getStringExtra(BizDefineAll.BIZ_ACTION);
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_baidumap, titleParentView);
		changeFonts((ViewGroup) rootView);
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		if (action.equals("1")) {
			rightView.setVisibility(View.GONE);
		}
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
				sendcallback();
			}
		});
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.openbaidumap_xml, bodyParentView);
		changeFonts((ViewGroup) view);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}


}
