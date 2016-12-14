/**
 * 
 */
package com.bcwcar.android.bencar.activity;

import java.util.ArrayList;
import java.util.List;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainHome.ShopBigImageFragment;
import com.bcwcar.android.bencar.adapter.MyFragmentPagerAdapter;
import com.bcwcar.android.bencar.adapter.Sminadop;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.widget.CustomViewPager;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author lijian
 *
 */
public class ShopImageShowActivity extends BaseActivity {
	private GridView mGridView;
	private static String[] ShopPics;
	private Sminadop adapter;
	//// ===============大图
	private CustomViewPager mCustomViewPager;
	private MyFragmentPagerAdapter fragmentPagerAdapter;
	private List<Fragment> fragmentList = new ArrayList<Fragment>();
	private RelativeLayout big_imageshow_LinearLayout;
	private TextView guanbi, image_postion, image_total;

	// ===========
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		// TODO Auto-generated method stub
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
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
		centerView.setText("图show");

		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.shop_image_show_xml, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}

	// ===================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			ShopPics = getIntent().getStringArrayExtra("image");
		} catch (Exception e) {
			// TODO: handle exception
		}
		initview();
	}

	// 实例化控件
	public void initview() {
		guanbi = (TextView) findViewById(R.id.TextView_guangbi_001);
		guanbi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				big_imageshow_LinearLayout.setVisibility(View.GONE);
				mGridView.setVisibility(View.VISIBLE);

			}
		});
		image_postion = (TextView) findViewById(R.id.TextView_image_show_bigimage001);
		image_total = (TextView) findViewById(R.id.TextView_image_show_bigimage002);
		image_total.setText(ShopPics.length + "");
		big_imageshow_LinearLayout = (RelativeLayout) findViewById(R.id.LinearLayout_big_imageshow001);
		mGridView = (GridView) findViewById(R.id.GridView_shop_image_show001);
		adapter = new Sminadop(ShopImageShowActivity.this, ShopPics);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				big_imageshow_LinearLayout.setVisibility(View.VISIBLE);
				mCustomViewPager.setCurrentItem(position);
				mGridView.setVisibility(View.GONE);

			}
		});
		mCustomViewPager = (CustomViewPager) findViewById(R.id.viewpager_shop_bigimage001);
		for (int j = 0; j < ShopPics.length; j++) {
			Bundle bundle = new Bundle();
			bundle.putString("url", ShopPics[j]);
			ShopBigImageFragment ff = new ShopBigImageFragment().newInstance(bundle);
			fragmentList.add(ff);
		}
		fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
		mCustomViewPager.setAdapter(fragmentPagerAdapter);
		mCustomViewPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				image_postion.setText(String.valueOf(arg0 + 1));
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
	}
}
