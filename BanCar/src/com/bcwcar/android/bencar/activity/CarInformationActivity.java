
package com.bcwcar.android.bencar.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainHome.MainHome;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpUserCar;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.util.StringUtil;
import com.bcwcar.android.bencar.widget.AllCapTransformationMethod;
import com.facebook.drawee.view.SimpleDraweeView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.NumberPicker.Formatter;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RelativeLayout;

/**
 * 填写爱车信息页
 */
public class CarInformationActivity extends BaseActivity implements OnValueChangeListener, OnScrollListener, Formatter {
	private static final String LOG_TAG = CarInformationActivity.class.getSimpleName();
	// ====初始界面控件
	private static SimpleDraweeView mCarSelectImg;
	private static TextView car_name_TextView;
	private static TextView car_yearkuan_TextView;
	private static TextView car_gettime_TextView;
	private TextView baoyang_buttong;
	private static EditText mTripDistance;
	private LinearLayout date_pop_LinearLayout,cardata_LinearLayout;
	// =================日期
	private String mCarId, mCarMiles, mBuyDate, userCarId;
	private ProgressDialog mProgressDialog;
	private TextView quxiao, queren;
	private NumberPicker mNumberPickerYear, mNumberPickerMonth;
	private int mNumberPickerYearValue;
	private int mNumberPickerMonthValue;
	private String action = null;
	private ImageView btn;
	private RelativeLayout date_xuanze001;
	private String mTimeString001 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
			.format(new Date(System.currentTimeMillis()));
	private int today_year, today_month, today_day;
	private Button baoyang_tijiao;
	private boolean save = false;
	private static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=CarInformationActivity.this;
		save = false;
		try {
			action = getIntent().getStringExtra("action");
		} catch (Exception e) {
			// TODO: handle exception
			action = "0";
		}
		initview();
		String[] temp = mTimeString001.split("-");
		today_day = Integer.parseInt(temp[2]);
		today_month = Integer.parseInt(temp[1]);
		today_year = Integer.parseInt(temp[0]);
		mNumberPickerYearValue = today_year;
		mNumberPickerMonthValue = today_month;
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	// 实例化控件
	public void initview() {
		cardata_LinearLayout=(LinearLayout)findViewById(R.id.LinearLayout_get001);
		cardata_LinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(CarInformationActivity.this,SelectCarActivity.class);
				intent.putExtra(BizDefineAll.BIZ_ACTION, "CarInformationActivity");
				startActivity(intent);
				
			}
		});
		mCarSelectImg = (SimpleDraweeView) findViewById(R.id.SimpleDraweeView_select_car_logo001);// 车的logo
		car_name_TextView = (TextView) findViewById(R.id.TextView_car_name001);// 车牌名称
		car_yearkuan_TextView = (TextView) findViewById(R.id.TextView_car_year_kuang001);// 车年款
		// =============初始赋值===================
		mCarSelectImg.setImageURI(
				ResourceUtil.getImageUriFromAssets(UserCarDataSave.get(CarInformationActivity.this, "BrandLogo")));
		car_name_TextView.setText(UserCarDataSave.get(CarInformationActivity.this, "CarName"));
		car_yearkuan_TextView.setText(UserCarDataSave.get(CarInformationActivity.this, "CarMiles")+"km");
		
		// ================初始赋值======end========
		car_gettime_TextView = (TextView) findViewById(R.id.TextView_car_goumai_date001);// 购车时间
		mTripDistance = (EditText) findViewById(R.id.car_information_trip_distance);// 输入的公里数
		mTripDistance.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mTripDistance.setText("");
			}
		});
		mTripDistance.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String ing = mTripDistance.getText().toString();
				if (isnum(ing)) {
					mCarMiles = mTripDistance.getText().toString();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		date_pop_LinearLayout = (LinearLayout) findViewById(R.id.LinearLayout_date_pop001);// 点击时间父类
		date_pop_LinearLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (imm != null) {
					imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
				}
				initNumberPicker(mNumberPickerYearValue, mNumberPickerMonthValue);
				date_xuanze001.setVisibility(View.VISIBLE);
			}
		});
		baoyang_buttong = (TextView) findViewById(R.id.TextView_baoyang_button001);
		baoyang_buttong.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserCarDataSave.save(CarInformationActivity.this, "CarMiles", mCarMiles);
				if (action.equals("SelectSeriesinfoActivity")) {
					
					if (!StringUtil.isEmpty(car_gettime_TextView.getText().toString())&& !StringUtil.isEmpty(mTripDistance.getText().toString())) {
						if (save) {
							Intent intent = new Intent(CarInformationActivity.this, SmartMaintenanceActivity.class);
							intent.putExtra("action", "CarInformationActivity");
							startActivity(intent);
						} else {
							addcar(1);
						}
					} else {
						showToast(getString(R.string.text_data_no_or_com));
					}
					
				}else if (action.equals("MainHomeActivity")) {
					if (!StringUtil.isEmpty(car_gettime_TextView.getText().toString())&& !StringUtil.isEmpty(mTripDistance.getText().toString())) {
						if (save) {
							Intent intent = new Intent(CarInformationActivity.this, SmartMaintenanceActivity.class);
							intent.putExtra("action", "CarInformationActivity");
							startActivity(intent);
						} else {
							updatausercar(1);
						}
					} else {
						showToast(getString(R.string.text_data_no_or_com));
					}
					
				}
				
				
			}
		});

		// ====日期选择控件======
		date_xuanze001 = (RelativeLayout) findViewById(R.id.LinearLayout_date001);
		quxiao = (TextView) findViewById(R.id.textview_aiche_quxiao001);
		quxiao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				date_xuanze001.setVisibility(View.GONE);
			}
		});
		queren = (TextView) findViewById(R.id.textview_aiche_queren001);
		queren.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mNumberPickerMonthValue < 10) {
					UserCarDataSave.save(CarInformationActivity.this, "BuyDate",
							mNumberPickerYearValue + "-0" + mNumberPickerMonthValue);
					car_gettime_TextView.setText(mNumberPickerYearValue + "年 0" + mNumberPickerMonthValue + "月");
				} else {
					UserCarDataSave.save(CarInformationActivity.this, "BuyDate",
							mNumberPickerYearValue + "-" + mNumberPickerMonthValue);
					car_gettime_TextView.setText(mNumberPickerYearValue + "年" + mNumberPickerMonthValue + "月");
				}
				date_xuanze001.setVisibility(View.GONE);
			}
		});
		mNumberPickerYear = (NumberPicker) findViewById(R.id.NumberPicker_car_date001);
		changeFonts(mNumberPickerYear);
		mNumberPickerMonth = (NumberPicker) findViewById(R.id.NumberPicker_car_date002);
		changeFonts(mNumberPickerMonth);
		setNumberPickerDividerColor(mNumberPickerMonth);
		setNumberPickerDividerColor(mNumberPickerYear);
if (action.equals("MainHomeActivity")) {
	car_gettime_TextView.setText(UserCarDataSave.get(CarInformationActivity.this, "BuyDate001"));
	mTripDistance.setText(UserCarDataSave.get(CarInformationActivity.this, "CarMiles"));
}
	}


	// 添加用户车型
	public void addcar(final int tt) {
		HttpUserCar.addUserCar(new CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				int tt=Integer.parseInt(UserLoginStatus.get(CarInformationActivity.this, "CarCount").toString());
				UserLoginStatus.save(CarInformationActivity.this, "CarCount", (tt+1)+"");
				if (tt == 0) {
					save = true;
					showToast(getString(R.string.text_add_car_success));
				} else if (tt !=0) {
					Intent intent = new Intent(CarInformationActivity.this, SmartMaintenanceActivity.class);
					intent.putExtra("action", "CarInformationActivity");
					startActivity(intent);
				}
			}
		}, UserLoginStatus.get(CarInformationActivity.this, "Token"),
				UserCarDataSave.get(CarInformationActivity.this, "CarId"),
				UserCarDataSave.get(CarInformationActivity.this, "CarMiles"),
				UserCarDataSave.get(CarInformationActivity.this, "BuyDate"), CarInformationActivity.this);
	}

	public  void updatausercar(final int tt){
		
		HttpUserCar.updateUserCar(new CallbackLogic() {
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				if (tt == 0) {
					save = true;
					showToast(getString(R.string.text_add_car_success));
				} else if (tt !=0) {
					Intent intent = new Intent(CarInformationActivity.this, SmartMaintenanceActivity.class);
					intent.putExtra("action", "CarInformationActivity");
					startActivity(intent);
				}
			}
		}, UserLoginStatus.get(CarInformationActivity.this, "Token"), 
UserCarDataSave.get(CarInformationActivity.this, "UserCarId"), 
UserCarDataSave.get(CarInformationActivity.this, "CarId"),
UserCarDataSave.get(CarInformationActivity.this, "CarMiles"),
UserCarDataSave.get(CarInformationActivity.this, "BuyDate"),"1",
CarInformationActivity.this);
	}
	
	



	/**
	 * 正则表达式 数字
	 */
	public static boolean isnum(String num) {
		Pattern p = Pattern.compile("^[0-9]*$");
		Matcher m = p.matcher(num);
		return m.matches();
	}

	public static void refresh(){
		mCarSelectImg.setImageURI(
				ResourceUtil.getImageUriFromAssets(UserCarDataSave.get(context, "BrandLogo")));
		car_name_TextView.setText(UserCarDataSave.get(context, "CarName"));
		car_yearkuan_TextView.setText(UserCarDataSave.get(context, "CarMiles")+"km");
		car_gettime_TextView.setText(UserCarDataSave.get(context, "BuyDate001"));
		mTripDistance.setText(UserCarDataSave.get(context, "CarMiles"));
	}

//=======================================
	@Override
	public void setPageTitle(ViewGroup titleParentView) {

		Resources res = titleParentView.getContext().getResources();
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
		rootView.setBackgroundColor(res.getColor(R.color.transparent));
		Button leftView = (Button) rootView.findViewById(R.id.common_title_1_left_view);
		TextView centerView = (TextView) rootView.findViewById(R.id.common_title_1_center_view);
		TextView rightView = (TextView) rootView.findViewById(R.id.common_title_1_right_view);
		leftView.setText("");
		leftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		centerView.setText(getString(R.string.car_information_title));
		rightView.setText(getString(R.string.car_save));
		rightView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserCarDataSave.save(CarInformationActivity.this, "CarMiles", mCarMiles);
				if (action.equals("SelectSeriesinfoActivity")) {
					addcar(0);
				}else if (action.equals("MainHomeActivity")) {
					updatausercar(0);
				}
			}
		});
		changeFonts(titleParentView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.body_car_information, bodyParentView);
		changeFonts(bodyParentView);
	}

	// NumberPicker默认值
	private void initNumberPicker(int mNumberPickerYearValue, int mNumberPickerMonthValue) {
		mNumberPickerYear.setFormatter(this);
		mNumberPickerYear.setOnValueChangedListener(this);
		mNumberPickerYear.setOnScrollListener(this);
		mNumberPickerYear.setMaxValue(today_year);
		mNumberPickerYear.setMinValue(2001);
		mNumberPickerYear.setValue(mNumberPickerYearValue);

		mNumberPickerMonth.setFormatter(this);
		mNumberPickerMonth.setOnValueChangedListener(this);
		mNumberPickerMonth.setOnScrollListener(this);
		mNumberPickerMonth.setMaxValue(12);
		mNumberPickerMonth.setMinValue(1);
		mNumberPickerMonth.setValue(mNumberPickerMonthValue);
	}


	// NumberPicker监听
	@Override
	public String format(int value) {
		// TODO Auto-generated method stub
		if (value < 10) {
			return "0" + value;
		}
		return String.valueOf(value);
	}

	// NumberPicker监听
	@Override
	public void onScrollStateChange(NumberPicker view, int scrollState) {
		// TODO Auto-generated method stub
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_FLING:
			// Toast.makeText(this, "后续滑动(飞呀飞，根本停下来)",
			// Toast.LENGTH_LONG).show();
			break;
		case OnScrollListener.SCROLL_STATE_IDLE:
			// Toast.makeText(this, "不滑动", Toast.LENGTH_LONG).show();
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			// Toast.makeText(this, "滑动中", Toast.LENGTH_LONG).show();
			break;
		}
	}

	// NumberPicker监听
	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		int viewId = picker.getId();
		if (R.id.NumberPicker_car_date001 == viewId) {
			if (newVal >= today_year) {
				mNumberPickerYear.setValue(today_year);
				mNumberPickerYearValue = today_year;
				if (mNumberPickerMonthValue >= today_month) {
					mNumberPickerMonth.setValue(today_month);
					mNumberPickerMonthValue = today_month;
					return;
				}
			}
			mNumberPickerYearValue = newVal;
			mBuyDate = newVal + "-" + mNumberPickerMonthValue;

		} else if (R.id.NumberPicker_car_date002 == viewId) {
			if (newVal >= today_month && mNumberPickerYearValue == today_year) {
				mNumberPickerMonth.setValue(today_month);
				mNumberPickerMonthValue = today_month;
				return;
			}
			mNumberPickerMonthValue = newVal;
			if (newVal < 10) {
				mBuyDate = mNumberPickerYearValue + "-0" + newVal;

			} else {
				mBuyDate = mNumberPickerYearValue + "-" + newVal;
			}

		} 
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// 没有工具栏，不用设置！
	}

}
