package com.bcwcar.android.bencar.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.adapter.WangDianListAdapter;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpMainServiceNetwork;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.biz.HttpUserInfo;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CalendarAction;
import com.bcwcar.android.bencar.util.CalendarUtil;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.bcwcar.android.bencar.util.StringUtil;
import com.bcwcar.android.bencar.widget.AllCapTransformationMethod;
import com.bcwcar.android.bencar.widget.CustomCalendar;
import com.bcwcar.android.bencar.widget.SlideSwitch;
import com.bcwcar.android.bencar.widget.SlideSwitch.OnStateChangedListener;
import com.facebook.drawee.view.SimpleDraweeView;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager.NetworkCallback;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.NumberPicker.Formatter;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 详单填写页面
 */
public class FillInOrderActivity extends BaseActivity implements OnValueChangeListener, OnScrollListener, Formatter {
	private static final String LOG_TAG = FillInOrderActivity.class.getSimpleName();
	private TextView mianfei_baoyan, baoyang_date, chepai_xuanze, fanxian, today_clicable;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private static TextView quanquan;
	private TextView time_tmie_queren;
	private TextView time_tmie_quxiao;
	private TextView time_time;
	private TextView tubi;
	private static TextView bancar_price001;
	private static TextView baoyan_button, quan_name;
	private TextView quxiao;
	private TextView queren, phone;
	private RadioGroup radioGroup;
	private RadioButton boy, girl;
	private EditText chepai_num, name, fapiao_taitou;
	private LinearLayout mianfeibaoyan001, mianfei_text;
	private NumberPicker car_address001, time_tmie_NumberPicker;
	private LinearLayout chepai_xuanze_pop;
	private String[] shenfen = { "藏 ", "川 ", "鄂 ", "赣 ", "甘 ", "桂 ", "贵 ", "黑", "沪 ", "吉 ", "晋 ", "冀 ", "津 ", "京 ", "辽",
			"鲁", "蒙 ", "闽", "宁 ", "琼 ", "青 ", "陕 ", "苏 ", "皖 ", "湘 ", "新 ", "豫 ", "云", "渝", "粤 ", "浙", "港", "澳", "台" };
	private String chepai_adrress_shenfen = null;
	private String sex_xuanze = "1";
	private SlideSwitch kaiguan;
	private RelativeLayout fapiao, time_time_show, date_LinearLayout, ticket_RelativeLayout;
	private String[] times = { "08:30--10:30", "10:30--14:00", "14:00--15:30", "15:30--17:00" };
	private String[] times1 = { "10:30--14:00", "14:00--15:30", "15:30--17:00" };
	private String[] times2 = { "14:00--15:30", "15:30--17:00" };
	private String[] times3 = { "15:30--17:00" };
	private String time_res = "08:30--10:30";
	private String mTimeString001 = new SimpleDateFormat("yyyy-MM-dd-HH-mm", Locale.CHINA)
			.format(new Date(System.currentTimeMillis()));
	private String mTimeString002 = new SimpleDateFormat("yyyy年MM月dd日  EE", Locale.CHINA)
			.format(new Date(System.currentTimeMillis()));
	private int today_year, today_month, today_day, today_hour, today_minute;
	// ===================时间控件
	private CalendarUtil calendarUtil;
	private CustomCalendar customCalendar;
	private Handler mHandler = new Handler() {
		public void dispatchMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case 0:
				break;
			default:
				break;
			}
		};
	};
	// 点击today会触发下面的事件，可能需要修改某部分
	private CalendarAction calendarAction = new CalendarAction() {

		@Override
		public void doAction(String time, boolean nextPlanVisible) {

			Log.d("CalendarAction", "time = " + time);
			Log.d("CalendarAction", "nextPlanVisible = " + nextPlanVisible);
			DingDanDataSave.save(FillInOrderActivity.this, "MaintenanceTime", time);
			String[] timeArray = time.split("-");
			String[] timeArray2 = mTimeString001.split("-");
			int xuanze_date_year = Integer.parseInt(timeArray[0]);
			int xuanze_date_month = Integer.parseInt(timeArray[1]);
			int xuanze_date_day = Integer.parseInt(timeArray[2]);
			int today_hour = Integer.parseInt(timeArray2[3]);
			int today_day = Integer.parseInt(timeArray2[2]);
			int today_month = Integer.parseInt(timeArray2[1]);
			int today_year = Integer.parseInt(timeArray2[0]);
			if (((xuanze_date_year == today_year && xuanze_date_month > today_month) || (xuanze_date_year == today_year
					&& xuanze_date_month == today_month && xuanze_date_day > today_day)
					|| (xuanze_date_year > today_year))) {

				baoyang_date.setText(Integer.parseInt(timeArray[0]) + "年" + Integer.parseInt(timeArray[1]) + "月"
						+ Integer.parseInt(timeArray[2]) + "日    " + calendarUtil.getWeek(time)); // 设置月份

			} else if (xuanze_date_year == today_year && xuanze_date_month == today_month
					&& xuanze_date_day == today_day) {
				if (today_hour >= 17) {
					showToast("今天服务时间已过，为您切换到明天");
					customCalendar.gotoCurrentDay1();
					time = calendarUtil.getTomorrowTime("yyyy-MM-dd");
					DingDanDataSave.save(FillInOrderActivity.this, "MaintenanceTime", time);
				} else {
					baoyang_date.setText(Integer.parseInt(timeArray[0]) + "年" + Integer.parseInt(timeArray[1]) + "月"
							+ Integer.parseInt(timeArray[2]) + "日    " + calendarUtil.getWeek(time));

				}

			} else {
				showToast("不能选择今天以前的日期");
				customCalendar.gotoCurrentDay();
			}
		}
	};
	// =========================默认shop===========
	private SimpleDraweeView shop_logo;
	private TextView shop_name, shop_adrress, shoper;
	private LinearLayout choice_shop_linearlayout;
	private int shop_zhizhen = 0;
	private RelativeLayout relatianjia;
	private static Context context;

	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		context = FillInOrderActivity.this;
		DingDanDataSave.save(FillInOrderActivity.this, "TicketId", "");
		DingDanDataSave.save(FillInOrderActivity.this, "TicketName", "");
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
		centerView.setText(getString(R.string.shop4s_maintain_fill_in_order_title));
		rightView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FillInOrderActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		changeFonts((ViewGroup) rootView);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {

		LayoutInflater.from(this).inflate(R.layout.body_shop4s_maintain_fill_in_order, bodyParentView);
		changeFonts(bodyParentView);

	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		shop_zhizhen = 0;
		String[] temps = mTimeString001.split("-");
		today_minute = Integer.parseInt(temps[4]);
		today_hour = Integer.parseInt(temps[3]);
		today_day = Integer.parseInt(temps[2]);
		today_month = Integer.parseInt(temps[1]);
		today_year = Integer.parseInt(temps[0]);
		DingDanDataSave.save(FillInOrderActivity.this, "MaintenanceSpan", times[0]);// 设置默认时间段
		DingDanDataSave.save(FillInOrderActivity.this, "Gender", sex_xuanze);// 设置默认性别
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		initview();
		initdata();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (DingDanDataSave.get(getApplicationContext(), "XianShiFlag").equals("1")) {
		 	choice_shop_linearlayout.setVisibility(View.VISIBLE);
		 	relatianjia.setVisibility(View.GONE);
		}else {
			
		}
		if (shop_zhizhen != 0) {
			updata_shopdata();
		}
		shop_zhizhen = 1;
		bancar_price001.setText(DingDanDataSave.get(FillInOrderActivity.this, "OrderDiscountProPrice"));
		fanxian.setText("￥" + DingDanDataSave.get(FillInOrderActivity.this, "OrderProPrice"));
	}

	// 实例化控件
	public void initview() {
		today_clicable = (TextView) findViewById(R.id.TextView_today_date_queding001);
		today_clicable.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				customCalendar.gotoCurrentDay();
			}
		});
		baoyang_date = (TextView) findViewById(R.id.TextView_today_is_today_data);
		baoyang_date.setText(mTimeString002);
		quan_name = (TextView) findViewById(R.id.TextView_ticket_name001);
		ticket_RelativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout_youhui_quan001);
		ticket_RelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FillInOrderActivity.this, TicketListActivity.class);
				intent.putExtra("action","FillInOrderActivity");
				intent.putExtra("PaySum", DingDanDataSave.get(FillInOrderActivity.this, "OrderDiscountProPrice"));
				startActivity(intent);
			}
		});
		shop_logo = (SimpleDraweeView) findViewById(R.id.wangdian_logo001);
		shop_name = (TextView) findViewById(R.id.wangdian_name001);// 商店名字
		shop_adrress = (TextView) findViewById(R.id.wangdian_adrress001);// 服务厂商地址
		shoper = (TextView) findViewById(R.id.wangdian_changshang001);// 服务厂商名字
		relatianjia=(RelativeLayout)findViewById(R.id.reletianjia);
		relatianjia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FillInOrderActivity.this, SelectShop4S.class);
				startActivity(intent);
			}
		});
		choice_shop_linearlayout = (LinearLayout) findViewById(R.id.LinearLayout_wangdian_choice_show001);
		choice_shop_linearlayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shop_name.setTextColor(Color.parseColor("#4A4A4A"));
				Intent intent = new Intent(FillInOrderActivity.this, SelectShop4S.class);
				startActivity(intent);
			}
		});
		// =================
		time_time_show = (RelativeLayout) findViewById(R.id.RelativeLayout_time_time_show001);
		time_time_show.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				time_time_show.setVisibility(View.GONE);
			}
		});
		
		time_tmie_queren = (TextView) findViewById(R.id.TextView_time_time_queren001);
		time_tmie_queren.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.print("time" + time_res);
				System.out.println("value" + time_tmie_NumberPicker.getValue());
				time_res = times[time_tmie_NumberPicker.getValue()];
				DingDanDataSave.save(FillInOrderActivity.this, "MaintenanceSpan", time_res);
				time_time.setText(time_res);
				time_time_show.setVisibility(View.GONE);
			}
		});
		time_tmie_quxiao = (TextView) findViewById(R.id.TextView_time_time_quxiao001);
		time_tmie_quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				time_time_show.setVisibility(View.GONE);
			}
		});
		time_tmie_NumberPicker = (NumberPicker) findViewById(R.id.NumberPicker_time_time_xuanze001);
		time_tmie_NumberPicker.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

			}
		});
		setNumberPickerDividerColor(time_tmie_NumberPicker);

		time_time = (TextView) findViewById(R.id.fill_in_order_time_time001);
		time_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (imm != null) {
					imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
				}
				time_time_show.setVisibility(View.VISIBLE);
				System.out.println(DingDanDataSave.get(FillInOrderActivity.this, "MaintenanceTime"));
				System.out.println(
						today_year + "-" + format001(today_month) + "-" + format001(today_day) + "=================");
				if (DingDanDataSave.get(FillInOrderActivity.this, "MaintenanceTime")
						.equals(today_year + "-" + format001(today_month) + "-" + format001(today_day))) {
					if (today_hour < 10 || (today_hour == 10 && today_minute <= 30)) {
						time_tmie_NumberPicker.setDisplayedValues(times);
						time_tmie_NumberPicker.setFormatter(FillInOrderActivity.this);
						time_tmie_NumberPicker.setOnValueChangedListener(FillInOrderActivity.this);
						time_tmie_NumberPicker.setOnScrollListener(FillInOrderActivity.this);
						time_tmie_NumberPicker.setMaxValue(times.length - 1);
						time_tmie_NumberPicker.setMinValue(0);
						time_tmie_NumberPicker.setValue(0);
						System.out.println(times + "********************");
					} else if (10 <= today_hour && today_hour < 14) {

						time_tmie_NumberPicker.setFormatter(FillInOrderActivity.this);
						time_tmie_NumberPicker.setOnValueChangedListener(FillInOrderActivity.this);
						time_tmie_NumberPicker.setOnScrollListener(FillInOrderActivity.this);
						time_tmie_NumberPicker.setMaxValue(times.length - 1);
						time_tmie_NumberPicker.setMinValue(1);
						time_tmie_NumberPicker.setValue(1);
						time_tmie_NumberPicker.setDisplayedValues(times1);
						System.out.println(times.length + "********************");
					} else if ((14 <= today_hour && today_hour < 15) || (today_hour == 15 && today_minute <= 30)) {
						time_tmie_NumberPicker.setDisplayedValues(null);

						time_tmie_NumberPicker.setFormatter(FillInOrderActivity.this);
						time_tmie_NumberPicker.setOnValueChangedListener(FillInOrderActivity.this);
						time_tmie_NumberPicker.setOnScrollListener(FillInOrderActivity.this);
						time_tmie_NumberPicker.setMaxValue(times.length - 1);
						time_tmie_NumberPicker.setMinValue(2);
						time_tmie_NumberPicker.setValue(2);
						time_tmie_NumberPicker.setDisplayedValues(times2);
					} else if (((today_hour == 15 && today_minute > 30) || (15 < today_hour && today_hour < 17))) {

						time_tmie_NumberPicker.setFormatter(FillInOrderActivity.this);
						time_tmie_NumberPicker.setOnValueChangedListener(FillInOrderActivity.this);
						time_tmie_NumberPicker.setOnScrollListener(FillInOrderActivity.this);
						time_tmie_NumberPicker.setMaxValue(times.length - 1);
						time_tmie_NumberPicker.setMinValue(3);
						time_tmie_NumberPicker.setValue(3);
						time_tmie_NumberPicker.setDisplayedValues(times3);
					} else {
						time_tmie_NumberPicker.setDisplayedValues(times);
						time_tmie_NumberPicker.setFormatter(FillInOrderActivity.this);
						time_tmie_NumberPicker.setOnValueChangedListener(FillInOrderActivity.this);
						time_tmie_NumberPicker.setOnScrollListener(FillInOrderActivity.this);
						time_tmie_NumberPicker.setMaxValue(times.length - 1);
						time_tmie_NumberPicker.setMinValue(0);
						time_tmie_NumberPicker.setValue(0);
					}
				} else {
					time_tmie_NumberPicker.setDisplayedValues(times);
					time_tmie_NumberPicker.setFormatter(FillInOrderActivity.this);
					time_tmie_NumberPicker.setOnValueChangedListener(FillInOrderActivity.this);
					time_tmie_NumberPicker.setOnScrollListener(FillInOrderActivity.this);
					time_tmie_NumberPicker.setMaxValue(times.length - 1);
					time_tmie_NumberPicker.setMinValue(0);
					time_tmie_NumberPicker.setValue(0);
				}
			}
		});

		kaiguan = (SlideSwitch) findViewById(R.id.SlideSwitch_dingdanye_fapiao_button001);// 发票开关
		kaiguan.setOpened(false);// 默认关闭
		fanxian = (TextView) findViewById(R.id.TextView_gongxiang_fanxian001);
		quanquan = (TextView) findViewById(R.id.money_quan001);
		DingDanDataSave.save(FillInOrderActivity.this, "BillFlag", "0");
		kaiguan.setOnStateChangedListener(new OnStateChangedListener() {

			@Override
			public void toggleToOn(View view) {
				// TODO Auto-generated method stub
				DingDanDataSave.save(FillInOrderActivity.this, "BillFlag", "1");
				fapiao.setVisibility(View.VISIBLE);
				kaiguan.toggleSwitch(true);// 设置新的状态并执行过渡动画
			}

			@Override
			public void toggleToOff(View view) {
				// TODO Auto-generated method stub
				DingDanDataSave.save(FillInOrderActivity.this, "BillFlag", "0");
				kaiguan.toggleSwitch(false);// 设置新的状态并执行过渡动画
				fapiao.setVisibility(View.GONE);
			}
		});
		fapiao = (RelativeLayout) findViewById(R.id.RelativeLayout_fapiao_taitou001);
		fapiao_taitou = (EditText) findViewById(R.id.EditText_fapiao_taitou001);
		mianfeibaoyan001 = (LinearLayout) findViewById(R.id.mainfei_baoyan_dingdanye001);// 免费保养项目展示

		mianfei_text = (LinearLayout) findViewById(R.id.mainfei_baoyan_text001);
		mianfei_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mianfeibaoyan001.setVisibility(View.GONE);
			}
		});
		chepai_xuanze = (TextView) findViewById(R.id.TextView_dingdanye_carnum001);// 车牌选择
		chepai_xuanze.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (imm != null) {
					imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
				}
				chepai_xuanze_pop.setVisibility(View.VISIBLE);
			}
		});

		baoyan_button = (TextView) findViewById(R.id.smart_maintenance_reserve11);// 保养结算按钮
		baoyan_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (list.size()==0) {
					showToast("无可保养的4s店");
					return;
				}
				if ( !chepai_xuanze.getText().toString().equals("") && !chepai_num.getText().toString().equals("")
						&& !name.getText().toString().equals("")) {
					String plateNumber = chepai_xuanze.getText().toString() + chepai_num.getText().toString();
					DingDanDataSave.save(FillInOrderActivity.this, "Contact", name.getText().toString());
					DingDanDataSave.save(FillInOrderActivity.this, "CellPhone", phone.getText().toString());
					DingDanDataSave.save(FillInOrderActivity.this, "PlateNumber", plateNumber.toUpperCase());
					UserLoginStatus.save(FillInOrderActivity.this, "Contact", name.getText().toString());
					UserLoginStatus.save(FillInOrderActivity.this, "PlateNumber", plateNumber.toUpperCase());
					DingDanDataSave.save(FillInOrderActivity.this, "BillHead", fapiao_taitou.getText().toString());
					if (DingDanDataSave.get(FillInOrderActivity.this, "BillFlag").equals("1")&&fapiao_taitou.getText().toString().equals("")) {
						
						showToast("发票抬头不能为空");
						return;
					}
					
					tijiao(plateNumber.toUpperCase(), "", UserCarDataSave.get(FillInOrderActivity.this, "CarId"),
							UserCarDataSave.get(FillInOrderActivity.this, "CarMiles"),
							DingDanDataSave.get(FillInOrderActivity.this, "Contact"),
							DingDanDataSave.get(FillInOrderActivity.this, "MaintenanceTime"),
							DingDanDataSave.get(FillInOrderActivity.this, "MaintenanceSpan"),
							DingDanDataSave.get(FillInOrderActivity.this, "TicketId"),
							DingDanDataSave.get(FillInOrderActivity.this, "TicketType"),
							DingDanDataSave.get(FillInOrderActivity.this, "BillFlag"),
							DingDanDataSave.get(FillInOrderActivity.this, "BillHead"),
							DingDanDataSave.get(FillInOrderActivity.this, "CellPhone"),
							DingDanDataSave.get(FillInOrderActivity.this, "ShopId"),
							DingDanDataSave.get(FillInOrderActivity.this, "Gender"),
							DingDanDataSave.get(FillInOrderActivity.this, "AccIds"));
				} else {
					showToast("请填写完整信息");
				}
			}
		});
		bancar_price001 = (TextView) findViewById(R.id.money_tv11);// 总金额
		chepai_num = (EditText) findViewById(R.id.EditText_dingdanye_carnum001);// 车牌号码
		chepai_num.setTransformationMethod(new AllCapTransformationMethod());
		if (StringUtil.isEmpty(UserLoginStatus.get(FillInOrderActivity.this, "PlateNumber"))) {
			chepai_num.setHint("6位车牌号码");
		}else {
			String tt=UserLoginStatus.get(FillInOrderActivity.this, "PlateNumber");
			String yy=tt.substring(1, tt.length());
			chepai_num.setText(yy);
		}
		name = (EditText) findViewById(R.id.fill_in_order_contact);// 姓名
		if (StringUtil.isEmpty(UserLoginStatus.get(FillInOrderActivity.this, "Contact"))) {
			name.setHint("请输入姓名");
		}else {
			name.setText(UserLoginStatus.get(FillInOrderActivity.this, "Contact"));
		}
		phone = (TextView) findViewById(R.id.EditText_dingdanye_phonenum001);// 手机号码
		if (!StringUtil.isEmpty(UserLoginStatus.get(FillInOrderActivity.this, "UserMobile"))) {
			phone.setText(UserLoginStatus.get(FillInOrderActivity.this, "UserMobile"));
		}else {
			phone.setHint("请输入联系方式");
		}

		radioGroup = (RadioGroup) findViewById(R.id.RadioGroup_dingdanye_sex001);// 性别选择
		boy = (RadioButton) findViewById(R.id.RadioButtondingdanye_sex_boy);
		girl = (RadioButton) findViewById(R.id.RadioButtondingdanye_sex_girl);
		phone.setText(UserLoginStatus.get(FillInOrderActivity.this, "UserMobile"));
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == boy.getId()) {
					sex_xuanze = "1";
					DingDanDataSave.save(FillInOrderActivity.this, "Gender", sex_xuanze);
				} else if (checkedId == girl.getId()) {
					sex_xuanze = "0";
					DingDanDataSave.save(FillInOrderActivity.this, "Gender", sex_xuanze);
				}
			}
		});
		car_address001 = (NumberPicker) findViewById(R.id.NumberPicker_car_address001);
		car_address001.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
			}
		});
		initNumberPicker();
		setNumberPickerDividerColor(car_address001);
		chepai_xuanze_pop = (LinearLayout) findViewById(R.id.LinearLayout_chepai_xuanze001);// 车牌归属地展示view
		chepai_xuanze_pop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chepai_xuanze_pop.setVisibility(View.GONE);
			}
		});
		queren = (TextView) findViewById(R.id.textview_dingdanye_queren001);// pop确认
		queren.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chepai_xuanze.setText(chepai_adrress_shenfen);
				chepai_xuanze_pop.setVisibility(View.GONE);

			}
		});
		quxiao = (TextView) findViewById(R.id.textview_dingdanye_quxiao001);// pop取消
		quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				chepai_xuanze_pop.setVisibility(View.GONE);
			}
		});
		// ===============时间控件=============
		customCalendar = (CustomCalendar) findViewById(R.id.calendar);
		customCalendar.setDateAction(calendarAction);
		customCalendar.setHandler(mHandler);
		customCalendar.gotoCurrentDay();
		// 赋值

	}

	// 获取用户的可保养的网店信息
	public void initdata() {
		HttpMainServiceNetwork.getShowShopListByCar(new CallbackLogic() {
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					JSONArray jsonArray = data.getJSONArray("Data");
					list = CollectionUtil.jsonArrayToListMap(jsonArray);
					if (list.size() != 0) {
						Map<String, String> map = list.get(0);
						for (String key : map.keySet()) {
							Log.e("map遍历", "key= " + key + " and value= " + map.get(key));
							DingDanDataSave.save(FillInOrderActivity.this, key, map.get(key));
						}
						updata_shopdata();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, UserLoginStatus.get(FillInOrderActivity.this, "Token"),
				DingDanDataSave.get(FillInOrderActivity.this, "AccIds"), "1", "10",
				UserCarDataSave.get(FillInOrderActivity.this, "CarId"), "",
				LocationDataSave.get(FillInOrderActivity.this, "CityId"),
				LocationDataSave.get(FillInOrderActivity.this, "CityName"),
				LocationDataSave.get(FillInOrderActivity.this, "Longitude"),
				LocationDataSave.get(FillInOrderActivity.this, "Latitude"), FillInOrderActivity.this);
	}

	// 更新网店信息
	public void updata_shopdata() {
		ResourceUtil.setSimpleDraweeViewImage(shop_logo, DingDanDataSave.get(FillInOrderActivity.this, "LogoUrl"));
		shop_adrress.setText(DingDanDataSave.get(FillInOrderActivity.this, "Address"));
		shop_name.setText(DingDanDataSave.get(FillInOrderActivity.this, "ShopName"));
		shoper.setText(DingDanDataSave.get(FillInOrderActivity.this, "ProviderNames"));
	}

	public static void refresh_ticket() {
		quan_name.setText(DingDanDataSave.get(context, "TicketName"));
		if (DingDanDataSave.get(context, "TicketSum").equals("")) {
			quanquan.setText("￥0.00");
		} else {
			quanquan.setText("￥" + DingDanDataSave.get(context, "TicketSum"));
		}
	}

	// 提交订单
	public void tijiao(String plateNumber, String OrderId, String carId, String miles, String contact,
			String maintenanceTime, String MaintenanceSpan, String ticketId, String TicketType, String billFlag,
			String billHead, String cellPhone, String shopId, String gender, String accIds) {
		HttpOrder.addOrder(new CallbackLogic() {

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					Map<String, String> map = CollectionUtil.jsonObjectToMap_String(alldata);
					for (String key : map.keySet()) {
						DingDanDataSave.save(FillInOrderActivity.this, key, map.get(key));
					}
					DingDanDataSave.save(getApplicationContext(), "Type", "");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PageUtil.jumpTo(FillInOrderActivity.this, PaymentMethodActivity.class);
			}
		}, UserLoginStatus.get(FillInOrderActivity.this, "Token"), plateNumber, OrderId, carId, miles, contact,
				maintenanceTime, MaintenanceSpan, ticketId, TicketType, billFlag, billHead, cellPhone, shopId, gender,
				accIds,"", FillInOrderActivity.this);
	}
	// 获取订单id 供其他类调用

	// 设置listview的高度
	public void setListViewHeight001(ListView listView) {
		int totalHeight = 0;
		ListAdapter listAdapter = listView.getAdapter();
		for (int i = 0; i < listAdapter.getCount(); i++) {

			View listItemView = listAdapter.getView(i, null, listView);
			listItemView.measure(0, 0);
			totalHeight += listItemView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;
		listView.setLayoutParams(params);

	}

	// NumberPicker默认值
	private void initNumberPicker() {
		car_address001.setDisplayedValues(shenfen);
		car_address001.setFormatter(this);
		car_address001.setOnValueChangedListener(this);
		car_address001.setOnScrollListener(this);
		car_address001.setMaxValue(shenfen.length - 1);
		car_address001.setMinValue(0);
		car_address001.setValue(24);
		chepai_adrress_shenfen = shenfen[24];
		setNumberPickerDividerColor(car_address001);

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

	public String format001(int value) {
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
		// TODO Auto-generated method stub
		int viewId = picker.getId();
		if (R.id.NumberPicker_car_address001 == viewId) {

			chepai_adrress_shenfen = shenfen[newVal];

		} else if (R.id.NumberPicker_time_time_xuanze001 == viewId) {

		}

	}

}
