package com.bcwcar.android.bencar.activity.MainMy;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.android.pushservice.PushManager;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpUserInfo;
import com.bcwcar.android.bencar.config.Constant;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ShareSDKUtil;
import com.bcwcar.android.bencar.widget.SlideSwitch;
import com.bcwcar.android.bencar.widget.SlideSwitch.OnStateChangedListener;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

/**
 * 设置页
 */
public class SettingActivity extends BaseActivity {
	private RelativeLayout xiugai_password;
	private TextView setting_logout;
	private SlideSwitch kaiguan;
	private RelativeLayout show_pop;
	private LinearLayout quxiao,gotoshare;
	private LinearLayout gengxin;
	private TextView banben_name;
	private int VerCode;
	private String versionName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initview();
	}
	
	
	//实例化控件
	public void initview(){
		kaiguan = (SlideSwitch) findViewById(R.id.SlideSwitch_dingdanye_fapiao_button001);// 
		if (JPushInterface.isPushStopped(getApplicationContext())) {
			kaiguan.setOpened(false);// 默认关闭
		}else {
			kaiguan.setOpened(true);// 默认关闭
		}	
		
		
		kaiguan.setOnStateChangedListener(new OnStateChangedListener() {

			@Override
			public void toggleToOn(View view) {
				// TODO Auto-generated method stub
				
				JPushInterface.resumePush(getApplicationContext());

				kaiguan.toggleSwitch(true);// 设置新的状态并执行过渡动画
			}

			@Override
			public void toggleToOff(View view) {
				// TODO Auto-generated method stub
				JPushInterface.stopPush(getApplicationContext());

				kaiguan.toggleSwitch(false);// 设置新的状态并执行过渡动画
			}
		});
	xiugai_password=(RelativeLayout)findViewById(R.id.checkforupdate);
	gotoshare=(LinearLayout)findViewById(R.id.goshare);
	gotoshare.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShareSDKUtil.showShare(SettingActivity.this,UserLoginStatus.get(getApplicationContext(), "InviteKey"));
		}
	});
	xiugai_password.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			HttpUserInfo.getVersion(new CallbackLogic()  {
			
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
					// TODO Auto-generated method stub
					try {
						System.out.println(data);
						JSONObject jsonObject=data.getJSONObject("Data");
						String VersionCode=jsonObject.getString("VersionCode").toString();
						 VerCode=Integer.parseInt(VersionCode);
						 versionName=jsonObject.getString("VersionName").toString();
						 banben_name.setText(versionName);
						 System.out.println(versionName);
						if (getVerCode(getApplicationContext())==VerCode&&versionName.equals(getVerName(getApplicationContext()))) {
							
							Toast.makeText(SettingActivity.this, "暂无更新", 0).show();
						}else {
							show_pop.setVisibility(View.VISIBLE);	
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}, UserLoginStatus.get(SettingActivity.this,"Token"),SettingActivity.this);
			
		}
	});
	setting_logout=(TextView)findViewById(R.id.setting_logout);
	setting_logout.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			UserLoginStatus.clear(getApplicationContext());
			DingDanDataSave.clear(getApplicationContext());
			UserCarDataSave.clear(getApplicationContext());
			WithdrawData.clear(getApplicationContext());
			finish();
		}
	});
	show_pop=(RelativeLayout)findViewById(R.id.RelativeLayout_banben_show001);
	quxiao=(LinearLayout)findViewById(R.id.LinearLayout_quxiao_banben001);
	quxiao.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			show_pop.setVisibility(View.GONE);
			
		}
	});
	gengxin=(LinearLayout)findViewById(R.id.LinearLayout_queren_banben001);
	gengxin.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			    Intent intent= new Intent();        
			    intent.setAction("android.intent.action.VIEW");    
			    Uri content_url = Uri.parse(Constant.versionCode_url);   
			    intent.setData(content_url);  
			    startActivity(intent);
			    show_pop.setVisibility(View.GONE);
		}
	});
	banben_name=(TextView)findViewById(R.id.TextView_banben_name);
	
//	if (UserLoginStatus.isLoggedOn(getApplicationContext())) {
//		
//		getbelong();
//	}
	System.out.println("zhixingle9999");
	
	}
	
		
		 /** 
	     * 获取软件版本号 
	     * @param context 
	     * @return 
	     */  
	    public static int getVerCode(Context context) {  
	        int verCode = -1;  
	        try {  
	            //注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分  
	            verCode = context.getPackageManager().getPackageInfo(  
	                    "com.bcwcar.android.bencar", 0).versionCode;  
	        } catch (NameNotFoundException e) {  
	            Log.e("msg",e.getMessage());  
	        }  
	        return verCode;  
	    }  
	    
	   /** 
	    * 获取版本名称 
	    * @param context 
	    * @return 
	    */  
	    public static String getVerName(Context context) {  
	        String verName = "";  
	        try {  
	            verName = context.getPackageManager().getPackageInfo(  
	            		"com.bcwcar.android.bencar", 0).versionName;  
	        } catch (NameNotFoundException e) {  
	            Log.e("msg",e.getMessage());  
	        }  
	        return verName;     
	}
	
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
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("设置");
		
		changeFonts(titleParentView);
	}
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.setting, bodyParentView);
		changeFonts(bodyParentView);
	}

	
	
	
	
	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
	}
}
