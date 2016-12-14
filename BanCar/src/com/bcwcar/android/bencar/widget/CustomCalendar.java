package com.bcwcar.android.bencar.widget;

import java.util.ArrayList;
import java.util.List;

import android.R.anim;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.adapter.CalendarAdapter;
import com.bcwcar.android.bencar.base.ActivityDao;
import com.bcwcar.android.bencar.util.CalendarAction;
import com.bcwcar.android.bencar.util.CalendarUtil;

public class CustomCalendar extends LinearLayout implements ActivityDao {
	private ChildViewPager cvp;
	// private List<View> childList;
	private LayoutInflater mInflater;
	// 用来保存日期数据
	private List<String> calendarDataList;
	private CalendarUtil calendarUtil;
	private CalendarAdapter calendarAdapter;
	private String todayStr,tomorrowStr;
	private Handler mHandler;
	private Context context;
	private String[] selectDate;
	private TextView selectTv;
	private CalendarAction action;
	private boolean isVisible;
	//当前日所在的viewpager的tag
	private int currentTag;

	public CustomCalendar(Context context, Handler mHandler) {
		super(context);
		mInflater = LayoutInflater.from(context);
		this.mHandler = mHandler;
		this.context = context;
		this.isVisible = false;
		initVar();
		initView();
		setAttribute();
		setAction();
	}
	public CustomCalendar(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		mInflater = LayoutInflater.from(context);
//		this.mHandler = mHandler;
		this.context = context;
		this.isVisible = false;
		initVar();
		initView();
		setAttribute();
		setAction();
	}

	public void setHandler(Handler mHandler) {
		System.out.println("001");
		this.mHandler = mHandler;
	}
	
	@Override
	public void initVar() {
		System.out.println("002");
		View rootView = mInflater.inflate(R.layout.view_calendar, null);
		addView(rootView);
		calendarDataList = new ArrayList<String>();
		calendarUtil = new CalendarUtil();
		calendarDataList.add(calendarUtil.getCurrentWeek(-1));
		calendarDataList.add(calendarUtil.getCurrentWeek(0));
		calendarDataList.add(calendarUtil.getCurrentWeek(1));
		currentTag = 1;
		calendarAdapter = new CalendarAdapter(context, calendarDataList) {
			@Override
			public View initPagerView(int position) {
				View view = mInflater
						.inflate(R.layout.item_view_calendar, null);
				initViewPagerItem(view, calendarDataList.get(position));
				return view;
			}
		};
		todayStr = calendarUtil.getNowTime("yyyy-MM-dd");
		tomorrowStr=calendarUtil.getTomorrowTime("yyyy-MM-dd");
	}

	@Override
	public void initView() {
		System.out.println("003");
		cvp = (ChildViewPager) findViewById(R.id.view_calendar_vp);
	}

	@Override
	public void setAttribute() {
		System.out.println("004");
		cvp.setAdapter(calendarAdapter);
		cvp.setCurrentItem(1);
	}

	//跳转到当前日所在的界面
	public void gotoCurrentDay() {
		System.out.println("005");
		//清除selectDate、selectTv
		selectDate = todayStr.split("-");
		selectTv = null;
		cvp.setCurrentItem(currentTag);
		if(action != null) {
			action.doAction(todayStr, true);
		}
		calendarAdapter.notifyDataSetChanged();
	}
	public void gotoCurrentDay1() {
		System.out.println("005");
		//清除selectDate、selectTv
		selectDate = tomorrowStr.split("-");
		selectTv = null;
		cvp.setCurrentItem(currentTag);
		if(action != null) {
			action.doAction(tomorrowStr, true);
		}
		calendarAdapter.notifyDataSetChanged();
	}

	@Override
	public void setAction() {
		System.out.println("006");
		cvp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				if (index == 0) {
					String[] dayArray = calendarDataList.get(0).split(" ");
					int dayIndex = Integer.valueOf(dayArray[0]) - 1;
					//					L.d("customCalendar",
					//							dayIndex + " " + calendarUtil.getCurrentWeek(dayIndex));
					calendarDataList.add(0, calendarUtil.getCurrentWeek(dayIndex));
					calendarAdapter.notifyDataSetChanged();
					currentTag = currentTag + 1;
					cvp.setCurrentItem(1);
				} else if (index == calendarDataList.size() - 1) {
					String[] dayArray = calendarDataList.get(
							calendarDataList.size() - 1).split(" ");
					int dayIndex = Integer.valueOf(dayArray[0]) + 1;
					//					L.d("customCalendar",
					//							dayIndex + " " + calendarUtil.getCurrentWeek(dayIndex));
					calendarDataList.add(calendarUtil.getCurrentWeek(dayIndex));
					calendarAdapter.notifyDataSetChanged();
					cvp.setCurrentItem(calendarDataList.size() - 2);
				}
				// 更改日期控件上面的提示语
				String[] dayArray = calendarDataList.get(index).split(" ");
				String lastDay = dayArray[dayArray.length - 1];
				String[] lastDayArray = lastDay.split("-");
				Message msg = new Message();
				msg.what = 0;
				msg.obj = lastDayArray[0] + "年" + lastDayArray[1] + "月";
				mHandler.sendMessage(msg);

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
	//设置Date的点击事件
	public void setDateAction(CalendarAction action) {
		System.out.println("005");
		this.action = action;
	}
	// 对某个特定的日期页面进行赋值
	private void initViewPagerItem(View rootView, String days) {
		System.out.println("====days===="+days);
		String[] dayArray = days.split(" ");
		ViewGroup rootVg = (ViewGroup) rootView
				.findViewById(R.id.item_view_calendar_ly);
		int size = rootVg.getChildCount();
		for (int i = 0; i < size; i++) {
			// 取每一天的年月日，但是第一个数值是index，所以需要加一
			final String oneDay = dayArray[i + 1];
			final String[] oneDayStr = oneDay.split("-");
			final TextView tv = (TextView) rootVg.getChildAt(i);
			final String[] toDayStr = todayStr.split("-");
			//设置当前代表的日期
			tv.setText(oneDayStr[2]);
			// 比较年月日， 设置当天
			if (CalendarUtil.isOneDay(oneDayStr, toDayStr)) {
				tv.setBackgroundResource(R.drawable.bitmap_calendar_today);
				isVisible = true;
			}else{
				if(selectDate != null) {
					//比较年月日，设置当前点击
					if (CalendarUtil.isOneDay(oneDayStr, selectDate)) {
						tv.setBackgroundResource(R.drawable.bitmap_calendar_select);
						tv.setTextColor(getResources().getColor(android.R.color.white));
						selectTv = tv;
					}
				}
				isVisible = false;
			}
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//恢复之前选中的item，并且不是今天
					if(!CalendarUtil.isOneDay(selectDate, toDayStr) && selectTv != null){
						System.out.println("0018");
						selectTv.setBackgroundResource(android.R.color.white);
						selectTv.setTextColor(getResources().getColor(R.color.inputblack));
					}
					selectTv = tv;
					selectDate = oneDayStr;
					//设置当前选中的item, 并且不是今天
					if(!CalendarUtil.isOneDay(oneDayStr, toDayStr)){
						tv.setBackgroundResource(R.drawable.bitmap_calendar_select);
						tv.setTextColor(getResources().getColor(android.R.color.white));
						isVisible = false;
					}else{
						selectTv = null;
						selectDate = null;
						isVisible = true;
					}
					if(action != null) {
						action.doAction(oneDay, isVisible);
					}
				}
			});
		}
	}
}