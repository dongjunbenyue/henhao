package com.bcwcar.android.bencar.activity.MainMy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.base.SysApplication;
import com.bcwcar.android.bencar.biz.HttpUserCar;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.ListViewUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.StringUtil;
import com.bcwcar.android.bencar.widget.XlistView.MyLetterListView;
import com.bcwcar.android.bencar.widget.XlistView.MyLetterListView.OnTouchingLetterChangedListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Request;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 选择车系品牌
 */
public class SelectBrandActivity002 extends BaseActivity {
	private static final String LOG_TAG = SelectBrandActivity002.class.getSimpleName();
	private ListView mListView;
	private List<Map<String, String>> mListViewData;
	private ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	private MyLetterListView letterListView = null;
	private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
	private String[] sections;// 存放存在的汉语拼音首字母
	private TextView overlay;
	private Handler handler;
	private OverlayThread overlayThread;
	private BaseAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mListView = (ListView)findViewById(R.id.select_brand_lv);
		initData();
		mListView.setOnItemClickListener(mListItemClickListener);
		letterListView = (MyLetterListView) findViewById(R.id.ListView_car_name001);
		letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
		try {
			
			initOverlay();
		} catch (Exception e) {
			// TODO: handle exception
		}
		handler = new Handler();
		overlayThread = new OverlayThread();
	}
	
	// listview数据tiao转
	private OnItemClickListener mListItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UserLoginStatus.save(SelectBrandActivity002.this, "BrandId",  mListViewData.get(position).get("BrandId").toString());
            UserLoginStatus.save(SelectBrandActivity002.this, "Brand", mListViewData.get(position).get("Brand").toString());
			JoinStore4SActivity.set_brand(mListViewData.get(position).get("Brand").toString());
			finish();
		}
	};

	// 侧边栏出触摸事件
	private class LetterListViewListener implements OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				System.out.println("position==="+position);
				mListView.setSelection(position);
				overlay.setText(sections[position] );
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				handler.postDelayed(overlayThread, 1000);
			}
		}

	}
	

	// 设置overlay不可见
	private class OverlayThread implements Runnable {

		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}

	// 初始化汉语拼音首字母弹出提示框
	private void initOverlay() {
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	// 获取所有品牌的列表
	private void initData() {
		
		HttpUserCar.getBrandList(new CallbackLogic() {

			
			

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				JSONArray array;
				try {
					array = data.getJSONArray("Data");
					mListViewData =CollectionUtil.jsonArrayToListMap(array);
					ListAdapter001 adapter = new ListAdapter001(mListViewData);
					mListView.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			
		}, UserLoginStatus.get(SelectBrandActivity002.this,"Token"),"","",SelectBrandActivity002.this);
	}

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		// leftView.setVisibility(View.GONE);
		rightView.setVisibility(View.GONE);
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText("品牌");
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_select_brand, bodyParentView);
		changeFonts(bodyParentView);
	}

	

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// LayoutInflater.from(this).inflate(R.layout.common_toolbar_1,
		// toolBarParentView);
	}

	/**
	 * 
	 * listview的适配器 ListViewAdapter
	 * 
	 * @author sy
	 * 
	 */
	private class ListAdapter001 extends BaseAdapter {
		private List<Map<String, String>> listViewdata;
		private TextView textView222;
		private String biaoji="1";
		public ListAdapter001(List<Map<String, String>> listViewdata) {
			this.listViewdata = listViewdata;
			alphaIndexer = new HashMap<String, Integer>();
			sections = new String[listViewdata.size()];

			for (int i = 0; i < listViewdata.size(); i++) {

				String currentStr = listViewdata.get(i).get("First");
				if (i==0) {
					alphaIndexer.put(currentStr, i);
					sections[i] = currentStr;
					
				}else {
					String currentStr001 = listViewdata.get(i-1).get("First");
					if (!currentStr.equals(currentStr001)) {
						System.out.println("位置=="+i);
						alphaIndexer.put(currentStr, i);
						sections[i] = currentStr;
					}
				}
			}
			

		}

		@Override
		public int getCount() {
			return listViewdata.size();
		}

		@Override
		public Object getItem(int position) {
			return listViewdata.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
			Map<String, String> item = listViewdata.get(position);
			boolean isTag = !StringUtil.isEmpty(item.get("TagTitle"));
			if (convertView==null) {
				
				convertView = layoutInflater.inflate(R.layout.brand_car_xml_item, parent, false);
			}
				SimpleDraweeView logo = (SimpleDraweeView) convertView.findViewById(R.id.select_brand_logo);
				String logoFullUrl = "asset:///" + item.get("First") + "/" + item.get("IconUrl");
				logo.setImageURI(Uri.parse(logoFullUrl));
				
				TextView brandName = (TextView) convertView.findViewById(R.id.select_brand_name);
				brandName.setText(item.get("Brand"));
				
				TextView brandnum = (TextView) convertView.findViewById(R.id.select_brand_zimu);
				brandnum.setText(item.get("First"));
				if (position==0) {
					brandnum.setVisibility(View.VISIBLE);
				}else {
					
					if (listViewdata.get(position-1).get("First").equals(item.get("First"))) {
						brandnum.setVisibility(View.GONE);
					}else {
						brandnum.setVisibility(View.VISIBLE);
					}
				}
				
			
			
				BaseActivity.changeFonts((ViewGroup)convertView);
			return convertView;
		}

	}

}
