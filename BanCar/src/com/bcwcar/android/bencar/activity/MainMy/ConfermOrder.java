package com.bcwcar.android.bencar.activity.MainMy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.FillInOrderActivity;
import com.bcwcar.android.bencar.activity.PaymentMethodActivity;
import com.bcwcar.android.bencar.activity.TicketListActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.QuanDingDanDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CalendarAction;
import com.bcwcar.android.bencar.util.CalendarUtil;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.StringUtil;
import com.bcwcar.android.bencar.widget.AmountView;
import com.bcwcar.android.bencar.widget.CustomCalendar;
import com.bcwcar.android.bencar.widget.SlideSwitch;
import com.bcwcar.android.bencar.widget.SlideSwitch.OnStateChangedListener;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.NumberPicker.Formatter;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;

public class ConfermOrder extends BaseActivity implements OnValueChangeListener, OnScrollListener, Formatter{
    private static Context context;
	private static TextView quan_name,zongjia;
	AmountView amoutview;
    private TextView baoyang_date;
    private String[] times = { "08:30--10:30", "10:30--14:00", "14:00--15:30", "15:30--17:00" };
	private String[] times1 = { "10:30--14:00", "14:00--15:30", "15:30--17:00" };
	private String[] times2 = { "14:00--15:30", "15:30--17:00" };
	private String[] times3 = { "15:30--17:00" };
	private String time_res = "08:30--10:30";
	private TextView time_time;
	private String mTimeString001 = new SimpleDateFormat("yyyy-MM-dd-HH-mm", Locale.CHINA)
			.format(new Date(System.currentTimeMillis()));
	private String mTimeString002 = new SimpleDateFormat("yyyy年MM月dd日  EE", Locale.CHINA)
			.format(new Date(System.currentTimeMillis()));
	private TextView today_clicable;
	private int today_year, today_month, today_day, today_hour, today_minute;
	// ===================时间控件
	private CalendarUtil calendarUtil;
	private NumberPicker time_tmie_NumberPicker;
	private CustomCalendar customCalendar;
	private ScrollView scrollView;
	private Handler mHandler = new Handler() {
		public void dispatchMessage(Message msg) {
			int what = msg.what;
			switch (what) {
			case 0:
				break;
		    case 1:
		    	scrollView.fullScroll(ScrollView.FOCUS_DOWN);
			default:
				break;
			}
		};
	};
    private CalendarAction calendarAction = new CalendarAction() {

		

		@Override
		public void doAction(String time, boolean nextPlanVisible) {

			Log.d("CalendarAction", "time = " + time);
			Log.d("CalendarAction", "nextPlanVisible = " + nextPlanVisible);
			QuanDingDanDataSave.save(ConfermOrder.this, "MaintenanceTime1", time);
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
					QuanDingDanDataSave.save(ConfermOrder.this, "MaintenanceTime1", time);
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
	private RelativeLayout time_time_show,youhuiquanrele;
	private TextView time_tmie_queren;
	private TextView time_tmie_quxiao;
	private SlideSwitch kaiguan;
	private RelativeLayout fapiao;
	private EditText fapiao_taitou;
	private TextView buttontijiao;
	private EditText textcontact;
	private TextView goodsname;
	private TextView goodsname1;
	private TextView singleprice;
	private TextView totoalprice;
	private TextView cellphone;
	
	
	public static void refresh_ticket() {
		quan_name.setText(DingDanDataSave.get(context, "TicketName"));
		if (QuanDingDanDataSave.get(context, "TicketSum").equals("")) {
			zongjia.setText(QuanDingDanDataSave.get(context, "ActualPrice"));
			zongjia.setText(StringUtil.string_to_int(QuanDingDanDataSave.get(context, "ActualPrice"))*StringUtil.string_to_int(QuanDingDanDataSave.get(context, "Quantity"))+"");
		} else {
			zongjia.setText(StringUtil.string_to_int(QuanDingDanDataSave.get(context, "ActualPrice"))*StringUtil.string_to_int(QuanDingDanDataSave.get(context, "Quantity"))-StringUtil.string_to_int(QuanDingDanDataSave.get(context, "TicketSum"))+"");
			
		}
	}
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
		centerView.setText("确认订单");
		leftView.setOnClickListener(new OnClickListener() {
            
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		centerView.getPaint().setFakeBoldText(true); // 中文字体加粗
		rightView.setVisibility(View.GONE);
		changeFonts(titleParentView);
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	
    	super.onCreate(savedInstanceState);
    	context=ConfermOrder.this;
    	QuanDingDanDataSave.save(ConfermOrder.this, "TicketId", "");
    	QuanDingDanDataSave.save(ConfermOrder.this, "TicketName", "");
    	QuanDingDanDataSave.save(ConfermOrder.this, "BillFlag1", "0");
    	QuanDingDanDataSave.save(ConfermOrder.this, "MaintenanceSpan1", "");// 设置默认时间段
    	initview();
    	initdata();
    }
	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
	 LayoutInflater.from(this).inflate(R.layout.confirmorder, bodyParentView);
	 changeFonts(bodyParentView);
	 
	}
	private void initview(){
		String[] timeArray2 = mTimeString001.split("-");
		today_hour = Integer.parseInt(timeArray2[3]);
		 today_day = Integer.parseInt(timeArray2[2]);
		 today_month = Integer.parseInt(timeArray2[1]);
		 today_year = Integer.parseInt(timeArray2[0]);
		 today_minute = Integer.parseInt(timeArray2[4]);
		 scrollView=(ScrollView)findViewById(R.id.scollivew);
		 zongjia=(TextView)findViewById(R.id.zongjia);
		 scrollView=(ScrollView)findViewById(R.id.scollivew);
		youhuiquanrele=(RelativeLayout)findViewById(R.id.youhuiquanrele);
		youhuiquanrele.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ConfermOrder.this, TicketListActivity.class);
				intent.putExtra("action", "ConfermOrder");
				intent.putExtra("PaySum", StringUtil.string_to_int(QuanDingDanDataSave.get(context, "ActualPrice"))*StringUtil.string_to_int(QuanDingDanDataSave.get(context, "Quantity"))+"");
				startActivity(intent);
			}
		});
		cellphone=(TextView)findViewById(R.id.cellphone);
		cellphone.setText(UserLoginStatus.get(context, "UserMobile"));
		baoyang_date=(TextView)findViewById(R.id.TextView_today_is_today_data);
		amoutview=(AmountView)findViewById(R.id.amount_view);
		QuanDingDanDataSave.save(getApplicationContext(), "Quantity", "1");
	     amoutview.setGoods_storage(50);
	     amoutview.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
	         @Override
	         public void onAmountChange(View view, int amount) {
	        	 QuanDingDanDataSave.save(getApplicationContext(), "Quantity", amount+"");
	        	 totoalprice.setText(StringUtil.string_to_int(QuanDingDanDataSave.get(context, "ActualPrice"))*StringUtil.string_to_int(QuanDingDanDataSave.get(context, "Quantity"))+"");
	        	 zongjia.setText(StringUtil.string_to_int(QuanDingDanDataSave.get(context, "ActualPrice"))*StringUtil.string_to_int(QuanDingDanDataSave.get(context, "Quantity"))+"");
	         }
	     });
	     
	  // ===============时间控件=============
			customCalendar = (CustomCalendar) findViewById(R.id.calendar);
			customCalendar.setDateAction(calendarAction);
			customCalendar.setHandler(mHandler);
			customCalendar.gotoCurrentDay();
			today_clicable = (TextView) findViewById(R.id.TextView_today_date_queding001);
			quan_name=(TextView)findViewById(R.id.youhuiquan);
			today_clicable.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					customCalendar.gotoCurrentDay();
				
				}
			});
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
					QuanDingDanDataSave.save(ConfermOrder.this, "MaintenanceSpan1", time_res);
					System.out.println(DingDanDataSave.get(getApplicationContext(), "MaintenanceTime1"));
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
					System.out.println(QuanDingDanDataSave.get(ConfermOrder.this, "MaintenanceTime1"));
					System.out.println(
							today_year + "-" + format001(today_month) + "-" + format001(today_day) + "=================");
					if (QuanDingDanDataSave.get(ConfermOrder.this, "MaintenanceTime1")
							.equals(today_year + "-" + format001(today_month) + "-" + format001(today_day))) {
						if (today_hour < 10 || (today_hour == 10 && today_minute <= 30)) {
							time_tmie_NumberPicker.setDisplayedValues(times);
							time_tmie_NumberPicker.setFormatter(ConfermOrder.this);
							time_tmie_NumberPicker.setOnValueChangedListener(ConfermOrder.this);
							time_tmie_NumberPicker.setOnScrollListener(ConfermOrder.this);
							time_tmie_NumberPicker.setMaxValue(times.length - 1);
							time_tmie_NumberPicker.setMinValue(0);
							time_tmie_NumberPicker.setValue(0);
							System.out.println(times + "********************");
						} else if (10 <= today_hour && today_hour < 14) {

							time_tmie_NumberPicker.setFormatter(ConfermOrder.this);
							time_tmie_NumberPicker.setOnValueChangedListener(ConfermOrder.this);
							time_tmie_NumberPicker.setOnScrollListener(ConfermOrder.this);
							time_tmie_NumberPicker.setMaxValue(times.length - 1);
							time_tmie_NumberPicker.setMinValue(1);
							time_tmie_NumberPicker.setValue(1);
							time_tmie_NumberPicker.setDisplayedValues(times1);
							
						} else if ((14 <= today_hour && today_hour < 15) || (today_hour == 15 && today_minute <= 30)) {
							time_tmie_NumberPicker.setDisplayedValues(null);

							time_tmie_NumberPicker.setFormatter(ConfermOrder.this);
							time_tmie_NumberPicker.setOnValueChangedListener(ConfermOrder.this);
							time_tmie_NumberPicker.setOnScrollListener(ConfermOrder.this);
							time_tmie_NumberPicker.setMaxValue(times.length - 1);
							time_tmie_NumberPicker.setMinValue(2);
							time_tmie_NumberPicker.setValue(2);
							time_tmie_NumberPicker.setDisplayedValues(times2);
						} else if (((today_hour == 15 && today_minute > 30) || (15 < today_hour && today_hour < 17))) {

							time_tmie_NumberPicker.setFormatter(ConfermOrder.this);
							time_tmie_NumberPicker.setOnValueChangedListener(ConfermOrder.this);
							time_tmie_NumberPicker.setOnScrollListener(ConfermOrder.this);
							time_tmie_NumberPicker.setMaxValue(times.length - 1);
							time_tmie_NumberPicker.setMinValue(3);
							time_tmie_NumberPicker.setValue(3);
							time_tmie_NumberPicker.setDisplayedValues(times3);
						} else {
							time_tmie_NumberPicker.setDisplayedValues(times);
							time_tmie_NumberPicker.setFormatter(ConfermOrder.this);
							time_tmie_NumberPicker.setOnValueChangedListener(ConfermOrder.this);
							time_tmie_NumberPicker.setOnScrollListener(ConfermOrder.this);
							time_tmie_NumberPicker.setMaxValue(times.length - 1);
							time_tmie_NumberPicker.setMinValue(0);
							time_tmie_NumberPicker.setValue(0);
						}
					} else {
						time_tmie_NumberPicker.setDisplayedValues(times);
						time_tmie_NumberPicker.setFormatter(ConfermOrder.this);
						time_tmie_NumberPicker.setOnValueChangedListener(ConfermOrder.this);
						time_tmie_NumberPicker.setOnScrollListener(ConfermOrder.this);
						time_tmie_NumberPicker.setMaxValue(times.length - 1);
						time_tmie_NumberPicker.setMinValue(0);
						time_tmie_NumberPicker.setValue(0);
					}
				}
			});
			singleprice=(TextView)findViewById(R.id.singleprice);
			totoalprice=(TextView)findViewById(R.id.totoalprice);
			singleprice.setText("￥"+QuanDingDanDataSave.get(context, "ActualPrice"));
			
			totoalprice.setText("￥"+StringUtil.string_to_int(QuanDingDanDataSave.get(context, "ActualPrice"))*StringUtil.string_to_int(QuanDingDanDataSave.get(context, "Quantity"))+"");
			textcontact=(EditText)findViewById(R.id.contact);
			goodsname=(TextView)findViewById(R.id.goodsname);
			goodsname1=(TextView)findViewById(R.id.goodsname1);
			goodsname.setText(QuanDingDanDataSave.get(context, "AccName"));
			
			kaiguan = (SlideSwitch) findViewById(R.id.SlideSwitch_dingdanye_fapiao_button001);// 发票开关
			kaiguan.setOpened(false);// 默认关闭
			fapiao = (RelativeLayout) findViewById(R.id.RelativeLayout_fapiao_taitou001);
			fapiao_taitou = (EditText) findViewById(R.id.EditText_fapiao_taitou001);
			zongjia.setText("￥"+StringUtil.string_to_int(QuanDingDanDataSave.get(context, "ActualPrice"))*StringUtil.string_to_int(QuanDingDanDataSave.get(context, "Quantity"))+"");
			kaiguan.setOnStateChangedListener(new OnStateChangedListener() {

				@Override
				public void toggleToOn(View view) {
					// TODO Auto-generated method stub
					QuanDingDanDataSave.save(ConfermOrder.this, "BillFlag1", "1");
					fapiao.setVisibility(View.VISIBLE);
					kaiguan.toggleSwitch(true);// 设置新的状态并执行过渡动画
					new Thread(new Runnable() {
						public void run() {
							
							mHandler.sendEmptyMessage(1);
						}
					}).start();
//					ConfermOrder.this.runOnUiThread( new Runnable() {
//						public void run() {
//							scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//						}
//					});
					
				}

				@Override
				public void toggleToOff(View view) {
					// TODO Auto-generated method stub
					QuanDingDanDataSave.save(ConfermOrder.this, "BillFlag1", "0");
					kaiguan.toggleSwitch(false);// 设置新的状态并执行过渡动画
					fapiao.setVisibility(View.GONE);
				}
			});
			buttontijiao=(TextView)findViewById(R.id.textbutton);
			buttontijiao.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					tijiao();
				}
			});
	}
    private void initdata(){}

	// NumberPicker监听
	
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
    public void tijiao(){
    	HttpOrder.addServiceOrder(new CallbackLogic() {
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// TODO Auto-generated method stub
				try {
					System.out.println("//////////"+alldata);
					Map<String, String> map = CollectionUtil.jsonObjectToMap_String(alldata);
					for (String key : map.keySet()) {
						DingDanDataSave.save(ConfermOrder.this, key, map.get(key));
					}
					DingDanDataSave.save(getApplicationContext(), "Type", "1");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PageUtil.jumpTo(ConfermOrder.this, PaymentMethodActivity.class);
			
			}
		}, UserLoginStatus.get(getApplicationContext(), "Token"), UserCarDataSave.get(getApplicationContext(), "CarId"), textcontact.getText().toString(), QuanDingDanDataSave.get(getApplicationContext(), "MaintenanceTime1"), QuanDingDanDataSave.get(getApplicationContext(), "MaintenanceSpan1"), QuanDingDanDataSave.get(getApplicationContext(), "TicketId"),
    	QuanDingDanDataSave.get(getApplicationContext(), "TicketType"), QuanDingDanDataSave.get(getApplicationContext(), "BillFlag1"), fapiao_taitou.getText().toString(), 
    	UserLoginStatus.get(getApplicationContext(), "UserMobile"), QuanDingDanDataSave.get(getApplicationContext(), "ShopId"), "0", QuanDingDanDataSave.get(getApplicationContext(), "AccId"),  QuanDingDanDataSave.get(getApplicationContext(), "ProjectId"), QuanDingDanDataSave.get(getApplicationContext(), "Quantity"), ConfermOrder.this);
    }
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
	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		// TODO Auto-generated method stub
		
	}

	

}
