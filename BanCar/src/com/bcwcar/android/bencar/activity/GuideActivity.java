package com.bcwcar.android.bencar.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.fragment.bannerfragment1;
import com.bcwcar.android.bencar.activity.fragment.bannerfragment2;
import com.bcwcar.android.bencar.activity.fragment.bannerfragment3;
import com.bcwcar.android.bencar.activity.fragment.bannerfragment4;
import com.bcwcar.android.bencar.adapter.MyFragmentPagerAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;


import com.bcwcar.android.bencar.util.PageUtil;

import android.R.layout;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class GuideActivity extends FragmentActivity{
//    private ImageView imageView;
	private Timer timer;
	private int zhizhen;
	public VideoView videoview1;
	public RelativeLayout relll;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	
	 bannerfragment4 frgment4;
	 bannerfragment1 frgment1;
	bannerfragment3 frgment3;
	bannerfragment2 frgment2;
	public ViewPager pager_top;
	public MyFragmentPagerAdapter myFragmentPagerAdapter;
	RadioGroup radioGroup;
	TextView tView;
	TextView imageView;
	int[] radiolist=new int[]{R.id.radio001,R.id.radio002,R.id.radio003,R.id.radio004};
    Handler handler=new Handler(){

//    	public void handleMessage(android.os.Message msg) {
//    		if (msg.what == 0) {
//    			if (zhizhen % 4== 0){
//    				pager_top.setCurrentItem(0);
//    			}else if(zhizhen % 4== 1) {
//    				
//    				pager_top.setCurrentItem(1);
//    			} else if (zhizhen % 4 == 2) {
//    				pager_top.setCurrentItem(2);
//    			} else if (zhizhen%4==3) {
//    				pager_top.setCurrentItem(3);
//    				
//				}
//    		}
//    	};

    };
	
	protected void onResume() {
		super.onResume();
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.body_guide);
	System.out.println("111111111111110");
		    
        imageView=(TextView)findViewById(R.id.zhuceimage);
        imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent();
				mIntent.setClass(GuideActivity.this, MainActivity.class);
				GuideActivity.this.startActivity(mIntent);
				finish();
			}
		});
		pager_top = (ViewPager) findViewById(R.id.viewpager_home_buttom1);
		radioGroup=(RadioGroup)findViewById(R.id.radiogroup002);
		System.out.println("22222222222222222222");
		frgment1 = new bannerfragment1();
		frgment2 = new bannerfragment2();
		frgment3=  new bannerfragment3();
		frgment4=  new bannerfragment4();
		System.out.println("3333333333333333333333333");
		fragmentList.add(frgment1);
		fragmentList.add(frgment2);
		fragmentList.add(frgment3);
		fragmentList.add(frgment4);
		System.out.println("1444444444444444440");
		myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
		System.out.println("1111555550");
		pager_top.setAdapter(myFragmentPagerAdapter);
		System.out.println("66660");
		pager_top.setCurrentItem(0);
		radioGroup.check(radiolist[0]);
		pager_top.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				radioGroup.check(radiolist[arg0]);
				if (arg0==3) {
					radioGroup.setVisibility(View.GONE);
					imageView.setVisibility(View.VISIBLE);
				}else {
					radioGroup.setVisibility(View.VISIBLE);
					imageView.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	
		
	
//		
//		zhizhen=0;
//	timer=new Timer();
//		imageView=(ImageView)findViewById(R.id.guideimg);
		
//start_anmition();
		
	}
	public void start_anmition() {
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				zhizhen++;
				handler.sendEmptyMessage(0);
			}
		}, 3000, 3000);

	}
	
	

}
