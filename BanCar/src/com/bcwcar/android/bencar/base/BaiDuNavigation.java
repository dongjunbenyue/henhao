package com.bcwcar.android.bencar.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.baidu.mapapi.utils.route.RouteParaOption.EBusStrategyType;

public class BaiDuNavigation{

    // 起始坐标
   static double mLat1;
   static double mLon1;
    //终点坐标
    double mLat2;
    double mLon2;
    private LocationClient locationClient;
    private BDLocation bdLocation;
    private Context context;
    
    public BaiDuNavigation(Context context,double mLat1,double mLon1,double mLat2,double mLon2){
    	this.mLat2=mLat2;
    	this.mLat1=mLat1;
    	this.mLon2=mLon2;
    	this.mLon1=mLon1;
    	this.context=context;
    	
    	
    }



    /**
     * 启动百度地图导航(Native)
     */
    public void startNavi() {
        LatLng pt1 = new LatLng(mLat1, mLon1);
        LatLng pt2 = new LatLng(mLat2, mLon2);

        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(pt1).endPoint(pt2);

        try {
            BaiduMapNavigation.openBaiduMapNavi(para, context);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
            showDialog();
        }

    }

    /**
     * 启动百度地图导航(Web)
     */
    public void startWebNavi() {
        LatLng pt1 = new LatLng(mLat1, mLon1);
        LatLng pt2 = new LatLng(mLat2, mLon2);
        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(pt1).endPoint(pt2);

        BaiduMapNavigation.openWebBaiduMapNavi(para, context);
    }

    /**
     * 启动百度地图步行导航(Native)
     *
     */
    public void startWalkingNavi() {
        LatLng pt1 = new LatLng(mLat1, mLon1);
        LatLng pt2 = new LatLng(mLat2, mLon2);

        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(pt1).endPoint(pt2);

        try {
            BaiduMapNavigation.openBaiduMapWalkNavi(para, context);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
            showDialog();
        }

    }
    /**
     * 启动百度地图Poi周边检索
     */
    public void startPoiNearbySearch() {
        LatLng ptCenter = new LatLng(mLat1, mLon1); // 天安门
        PoiParaOption para = new PoiParaOption()
                .center(ptCenter)
                .radius(2000);

        try {
            BaiduMapPoiSearch.openBaiduMapPoiNearbySearch(para, context);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog();
        }

    }


    /**
     * 启动百度地图步行路线规划
     */
    public void startRoutePlanWalking() {
        LatLng ptStart = new LatLng(mLat1,mLon1);
        LatLng ptEnd = new LatLng(mLat2, mLon2);

        // 构建 route搜索参数
        RouteParaOption para = new RouteParaOption()
                .startPoint(ptStart);

        try {
            BaiduMapRoutePlan.openBaiduMapWalkingRoute(para, context);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog();
        }

    }

    /**
     * 启动百度地图驾车路线规划
     */
    public void startRoutePlanDriving() {
        LatLng ptStart = new LatLng(mLat1,mLon1);
        LatLng ptEnd = new LatLng(mLat2, mLon2);

        // 构建 route搜索参数
        RouteParaOption para = new RouteParaOption()
                .startPoint(ptStart);


        try {
            BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, context);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog();
        }

    }

    /**
     * 启动百度地图公交路线规划
     */
    public void startRoutePlanTransit() {
        LatLng ptStart = new LatLng(mLat1, mLon1);
        LatLng ptEnd = new LatLng(mLat2, mLon2);

        // 构建 route搜索参数
        RouteParaOption para = new RouteParaOption().endPoint(ptStart).busStrategyType(EBusStrategyType.bus_recommend_way);

        try {
            BaiduMapRoutePlan.openBaiduMapTransitRoute(para, context);
        } catch (Exception e) {
            e.printStackTrace();
            showDialog();
        }

    }


    protected void onDestroy() {
  
        BaiduMapNavigation.finish(context);
        BaiduMapRoutePlan.finish(context);
        BaiduMapPoiSearch.finish(context);
    }

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(context);
            }
        });

        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }


}
