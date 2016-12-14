package com.bcwcar.android.bencar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.android.phone.mrpc.core.v;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.CarInformationActivity;
import com.bcwcar.android.bencar.activity.MainActivity;
import com.bcwcar.android.bencar.adapter.OtherProjectAdapter;
import com.bcwcar.android.bencar.adapter.SuggestProjectAdapter2;
import com.bcwcar.android.bencar.adapter.ZhiNengAdapter;
import com.bcwcar.android.bencar.base.ActivityDao;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.biz.HttpWallet;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.LocationDataSave;
import com.bcwcar.android.bencar.datasave.OnlyOneDataSave;
import com.bcwcar.android.bencar.datasave.UserCarDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.CollectionUtil;
import com.bcwcar.android.bencar.util.PageUtil;
import com.bcwcar.android.bencar.util.ResourceUtil;
import com.facebook.drawee.view.SimpleDraweeView;
/**
 * 智能保养
 */
public class SmartMaintenanceActivity extends BaseActivity {
	private static final String LOG_TAG = SmartMaintenanceActivity.class.getSimpleName();
	private SimpleDraweeView mCarSelectImg;
	private TextView viewProjectNum;
	private TextView mCarSelectText;
	private TextView mCarSelectRealMileage, mbuyCarTime001;
	private static ListView lv;
	private static ListView listview_jiyou;
	private static JSONArray mMaintenanceProject;
	private GridView expandableList, suggest_GridView;
	private TextView panduan, textView, quxiao, queren, baoyang_finish,conformtext;
	private static TextView zongjia;
	private LinearLayout lianghao, mianfei, shijimiles,baoyan_mianfei;
	private static LinearLayout jiyou_zhanshi;
	private LinearLayout jiyou_zhanshi002;
	private LinearLayout mianfei_text;
	private LinearLayout gongxiang_shuoming;
	private ImageView updown,updown1;
	private static List<Map<String, String>> list_zhanshi = new ArrayList<Map<String, String>>();
	private static List<Map<String, String>> list_zhineng001 = new ArrayList<Map<String, String>>();
	private static List<Map<String, String>> list_zhineng = new ArrayList<Map<String, String>>();
	private static List<Map<String, String>> list_need = new ArrayList<Map<String, String>>();
	private static List<Map<String, String>> list_suggest = new ArrayList<Map<String, String>>();
	private static List<Map<String, String>> list_other= new ArrayList<Map<String, String>>();
	private static TextView need_total_price;
	private TextView suggest_numer;
	private String carid;
	private static ZhiNengAdapter adapter_zhineng;
	private static SuggestProjectAdapter2 adapter_suggest;
	private RelativeLayout gonglishu_pop;
	private EditText gongli_EditText001;
	private static TextView gongxiang_fanxian;
	private static Context context;
	private String action = "0";
	private RelativeLayout suggestline,tipsrela;
	
//tiao
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		OnlyOneDataSave.save(SmartMaintenanceActivity.this, "jiyouzhizhen", "0");
		list_need.clear();
		list_zhineng.clear();
		list_suggest.clear();
		list_other.clear();
		try {
			action = getIntent().getStringExtra("action");
		} catch (Exception e) {
			// TODO: handle exception
		}
		initView();
		setAttribute();
		context = this;
	}

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
		centerView.setText(getString(R.string.text_zhineng_case));
		changeFonts(titleParentView);
		rightView.setVisibility(View.GONE);
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		// TODO Auto-generated method stub
		LayoutInflater.from(this).inflate(R.layout.body_smart_maintenance, bodyParentView);
		changeFonts(bodyParentView);
	}

	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		// TODO Auto-generated method stub

	}


	// 设置用户的默认车型信息
	public void initVar() {
		mCarSelectImg.setImageURI(
				ResourceUtil.getImageUriFromAssets(UserCarDataSave.get(SmartMaintenanceActivity.this, "BrandLogo")));
		mCarSelectText.setText(UserCarDataSave.get(SmartMaintenanceActivity.this, "CarName"));
		mCarSelectRealMileage.setText(UserCarDataSave.get(SmartMaintenanceActivity.this, "CarMiles"));
		mbuyCarTime001.setText(UserCarDataSave.get(SmartMaintenanceActivity.this, "BuyDate"));
		carid = UserCarDataSave.get(SmartMaintenanceActivity.this, "CarId");
		
		zongjia.setText(DingDanDataSave.get(SmartMaintenanceActivity.this, "OrderDiscountProPrice"));
		gongxiang_fanxian.setText("￥" + DingDanDataSave.get(SmartMaintenanceActivity.this, "OrderProPrice"));
	}

	// 实例化控件
	public void initView() {
		// ===建议项目==
		suggest_numer = (TextView) findViewById(R.id.TextView_suggest_listnumber001);
		suggest_GridView = (GridView) findViewById(R.id.smart_maintenance_sugest_project001);

		/// =====必须项目
		need_total_price = (TextView) findViewById(R.id.TextView_need_total_price001);
		// =========
		tipsrela=(RelativeLayout)findViewById(R.id.tipsrela);
		tipsrela.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tipsrela.setVisibility(View.GONE);
			}
		});
		baoyang_finish = (TextView) findViewById(R.id.smart_maintenance_reserve);
		baoyang_finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (list_zhineng == null||list_zhineng.size()==0) {
					showToast("产品暂无报价！");
					return;
				} else {
					try {
						save_dingdan(list_zhineng);
						double houtai_toytal = 0.00;
						for (int i = 0; i < list_zhineng.size(); i++) {
							String BenPrice = list_zhineng.get(i).get("DiscountPrice").toString();
							houtai_toytal += Double.valueOf(BenPrice).doubleValue();
						}
						DingDanDataSave.save(SmartMaintenanceActivity.this, "OurPrice",
								String.valueOf((int) houtai_toytal));
						String string = zongjia.getText().toString();
						String string001 = gongxiang_fanxian.getText().toString();
						String[] temp001 = string001.split("￥");

						DingDanDataSave.save(SmartMaintenanceActivity.this, "OrderProPrice", temp001[1]);
						DingDanDataSave.save(SmartMaintenanceActivity.this, "OrderDiscountProPrice", string);
					} catch (Exception e) {
						// TODO: handle exception
						return;
					}
					tipsrela.setVisibility(View.VISIBLE);
					
				}
			}
		});
		findViewById(R.id.module_car_select_real_mileage_right_arrow).setVisibility(View.GONE);
		gongxiang_fanxian = (TextView) findViewById(R.id.TextView_gongxiang_fanxian001);
		gongxiang_shuoming = (LinearLayout) findViewById(R.id.LinearLayout_gongxiang_shuoming001);
		shijimiles = (LinearLayout) findViewById(R.id.module_car_select_real_mileage_layout);
		shijimiles.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gonglishu_pop.setVisibility(View.VISIBLE);
			}
		});
		
		shijimiles.setVisibility(View.VISIBLE);
		conformtext=(TextView)findViewById(R.id.confirmsmarttext);
		conformtext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PageUtil.jumpTo(SmartMaintenanceActivity.this, FillInOrderActivity.class);
				tipsrela.setVisibility(View.GONE);
			}
		});
		mbuyCarTime001 = (TextView) findViewById(R.id.goucheshijian);
		mbuyCarTime001.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gonglishu_pop.setVisibility(View.VISIBLE);
			}
		});
		gonglishu_pop = (RelativeLayout) findViewById(R.id.PP888888888);
		gonglishu_pop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gonglishu_pop.setVisibility(View.GONE);
			}
		});
		quxiao = (TextView) findViewById(R.id.textview_gongli_num001);
		queren = (TextView) findViewById(R.id.textview_gongli_num002);
		quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gonglishu_pop.setVisibility(View.GONE);
			}
		});
		queren.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ctext = gongli_EditText001.getText().toString() + "";
				int tt = Integer.parseInt(ctext);
				if (tt < 5000) {
					gongli_EditText001.setText("5000");
				} else if (tt > 300000) {
					showToast("公里数不能高于30万KM");
					return;
				}
				if (!ctext.equals("")) {
					mCarSelectRealMileage.setText(gongli_EditText001.getText().toString() + "");
					gongli_EditText001.setText("");
					gongli_EditText001.setHint("请输入实际公里数(km)");
					UserCarDataSave.save(SmartMaintenanceActivity.this, "Miles", ctext);
					gonglishu_pop.setVisibility(View.GONE);
					list_zhanshi.clear();
					list_zhineng.clear();
					list_zhineng001.clear();
					setAttribute();
				}
			}
		});
		gongli_EditText001 = (EditText) findViewById(R.id.EditText_gongli_EditText001);

		zongjia = (TextView) findViewById(R.id.money_tv);
		expandableList = (GridView) findViewById(R.id.smart_maintenance_other_project);
		lianghao = (LinearLayout) findViewById(R.id.smart_maintenance_other_project_layout);
		lianghao.setVisibility(View.GONE);
		lianghao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (panduan.getText().equals("0")) {
					expandableList.setVisibility(View.VISIBLE);
					panduan.setText("1");
					updown.setBackgroundResource(R.drawable.up);
				} else {
					expandableList.setVisibility(View.GONE);
					panduan.setText("0");
					updown.setBackgroundResource(R.drawable.down);
				}
			}
		});
		textView = (TextView) findViewById(R.id.smart_maintenance_other_project_prompt);
		listview_jiyou = (ListView) findViewById(R.id.listview_jiyou_hznagshi001);
		listview_jiyou.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				jiyou_zhanshi.setVisibility(View.GONE);
				jiyou_zhanshi.setFocusable(false);
				jiyou_zhanshi.setFocusableInTouchMode(false);
				if (list_zhanshi.get(position).get("ProjectName").toString().equals("机油")) {
					for (int i = 0; i < list_zhineng.size(); i++) {
						list_zhineng001.add(list_zhineng.get(i));
					}
					list_zhineng.clear();
					list_zhineng.add(list_zhanshi.get(position));
					for (int i = 1; i < list_zhineng001.size(); i++) {
						list_zhineng.add(list_zhineng001.get(i));
					}
					adapter_zhineng.notifyDataSetChanged();
					setListViewHeight(lv);
					tongji(list_zhineng);

				} else {
					return;
				}

			}
		});
		jiyou_zhanshi = (LinearLayout) findViewById(R.id.jiyou_zhangshi001);
		jiyou_zhanshi002 = (LinearLayout) findViewById(R.id.jiyou_zhangshi002);
		jiyou_zhanshi002.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				jiyou_zhanshi.setVisibility(View.GONE);
				jiyou_zhanshi.setFocusable(false);
				jiyou_zhanshi.setFocusableInTouchMode(false);
				list_zhineng001.clear();
			}
		});
		mCarSelectImg = (SimpleDraweeView) findViewById(R.id.module_car_select_img);
		mCarSelectText = (TextView) findViewById(R.id.module_car_select_text);
		mCarSelectRealMileage = (TextView) findViewById(R.id.module_car_select_real_mileage_text);
		panduan = (TextView) findViewById(R.id.textview_zhankai_listview);
		lv = (ListView) findViewById(R.id.lv);
		updown = (ImageView) findViewById(R.id.up_down001);
		updown1=(ImageView) findViewById(R.id.up_down002);
		suggestline=(RelativeLayout)findViewById(R.id.sugesstline);
		suggestline.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (suggest_GridView.getVisibility()==0) {
					suggest_GridView.setVisibility(View.GONE);
					
					updown1.setBackgroundResource(R.drawable.down);
				} else {
					suggest_GridView.setVisibility(View.VISIBLE);
					
					updown1.setBackgroundResource(R.drawable.up);
				}
			}
		});
		mianfei = (LinearLayout) findViewById(R.id.mainfei_baoyan001);
		mianfei_text = (LinearLayout) findViewById(R.id.mainfei_baoyan_text001);
		mianfei_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mianfei.setVisibility(View.GONE);
			}
		});

		baoyan_mianfei = (LinearLayout) findViewById(R.id.no_money001);
		baoyan_mianfei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mianfei.setVisibility(View.VISIBLE);
			}
		});

	}

	

	// listview 高度计算
	public static void setListViewHeight(ListView listView) {
		int totalHeight = 0;
		ListAdapter listAdapter = listView.getAdapter();
		for (int i = 0; i < listAdapter.getCount(); i++) {

			View listItemView = listAdapter.getView(i, null, listView);
			int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
			listItemView.measure(desiredWidth, 0);
			totalHeight += listItemView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;
		listView.setLayoutParams(params);

	}

	// listview 高度计算
	public void setListViewHeight001(GridView listView) {
		int totalHeight = 0;
		View listItemView;
		ListAdapter listAdapter = listView.getAdapter();
		for (int i = 0; i < listAdapter.getCount(); i++) {

			listItemView = listAdapter.getView(i, null, listView);
			listItemView.measure(0, 0);

			totalHeight += listItemView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		if (listAdapter.getCount() % 2 == 1) {
			listItemView = listAdapter.getView(0, null, listView);
			listItemView.measure(0, 0);

			params.height = (totalHeight + listItemView.getMeasuredHeight()) / 2;
		} else {

			params.height = totalHeight / 2;
		}
		
		listView.setLayoutParams(params);

	}

	public void setAttribute() {
		// 获取智能保养手册
		if (action.equals("CarInformationActivity")) {
			zhineng_list();
		} else if (action.equals("MainHomeFinishActivity")) {
			zhineng_list();
		}
	}

	// 智能list
	public void zhineng_list() {
		HttpOrder.getCarMaintenance(new CallbackLogic() {

			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
				// MaintenanceProject节点数组的数据绑定到零件保养列表,数组下面的Projects子节点才包含需要的数据
				try {
					System.out.println("alldata==" + alldata);
					JSONObject dataobject = data.getJSONObject(BizDefineAll.BIZ_RESPONSE_DATA);
					DingDanDataSave.save(SmartMaintenanceActivity.this, "NeedProPrice",
							dataobject.getString("NeedProPrice"));
					DingDanDataSave.save(SmartMaintenanceActivity.this, "OrderProPrice",
							dataobject.getString("OrderProPrice"));
					DingDanDataSave.save(SmartMaintenanceActivity.this, "OrderDiscountProPrice",
							dataobject.getString("OrderDiscountProPrice"));
					mMaintenanceProject = data.getJSONObject(BizDefineAll.BIZ_RESPONSE_DATA)
							.getJSONArray("NeedProject");
					json_zhineng(mMaintenanceProject);// 必须项json 解析
					json_suggest(dataobject);// 建议项json 解析
					// ==============赋值==================
					adapter_zhineng = new ZhiNengAdapter(getApplicationContext(), list_need);
					lv.setAdapter(adapter_zhineng);
					setListViewHeight(lv);
					adapter_suggest = new SuggestProjectAdapter2(SmartMaintenanceActivity.this, list_suggest);
					suggest_GridView.setAdapter(adapter_suggest);
					setListViewHeight001(suggest_GridView);
					// tongji(list_zhineng);
					// 良好的列表，键对：OtherProject
					if (data.getJSONObject(BizDefineAll.BIZ_RESPONSE_DATA).isNull("OtherProject")) {

					} else {
						JSONArray otherProject = data.getJSONObject(BizDefineAll.BIZ_RESPONSE_DATA)
								.getJSONArray("OtherProject");

						for (int i = 0; i < otherProject.length(); i++) {
							Map<String, String> map=CollectionUtil.jsonObjectToMap_String(otherProject.getJSONObject(i));
							map.put("tag", "1");
							list_other.add(map);
							JSONArray array002 = new JSONArray(CollectionUtil.jsonObjectToMap_String(otherProject.getJSONObject(i)).get("Projects").toString());
							if (array002.length() != 0) {
								JSONObject object2 = array002.getJSONObject(0);
								System.out.println("========"+object2);
								list_other.get(i).putAll(CollectionUtil.jsonObjectToMap_String(object2));
							}
						}
						
							textView.setText(list_other.size() + "");
						
						expandableList.setAdapter(new OtherProjectAdapter(SmartMaintenanceActivity.this,list_other));
						setListViewHeight001(expandableList);
					}
					//赋值
					initVar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, UserLoginStatus.get(SmartMaintenanceActivity.this,"Token"),
				UserCarDataSave.get(SmartMaintenanceActivity.this, "CarId"),
				UserCarDataSave.get(SmartMaintenanceActivity.this, "CarMiles"),
				UserCarDataSave.get(SmartMaintenanceActivity.this, "BuyDate"),LocationDataSave.get(getApplicationContext(), "CityName"), LocationDataSave.get(getApplicationContext(), "CityId"),SmartMaintenanceActivity.this);
	}

	// 保存用户车型信息

	// 必须项目json 解析
	public void json_zhineng(JSONArray mMaintenanceProject) {
		String timeprice;
		double timeprice1 = 0.00;
		for (int i = 0; i < mMaintenanceProject.length(); i++) {
			try {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object = mMaintenanceProject.getJSONObject(i);
				map = CollectionUtil.jsonObjectToMap_String(object);
				list_need.add(map);
				System.out.println();
				JSONArray array = new JSONArray(map.get("Projects").toString());
				
				if (array.length() != 0) {
					JSONObject object2 = array.getJSONObject(0);
					list_zhineng.add(CollectionUtil.jsonObjectToMap_String(object2));
					list_need.get(i).putAll(CollectionUtil.jsonObjectToMap_String(object2));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		timeprice=list_need.get(i).get("TimePrice_After").toString();
		timeprice1 += Double.valueOf(timeprice).doubleValue();
		}
		need_total_price.setText("工时费￥" + (int)timeprice1);
	}

	// 建议项目json 解析
	public void json_suggest(JSONObject data) {
		try {
			JSONArray array = data.getJSONArray("SuggestProject");
			for (int i = 0; i < array.length(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				JSONObject object = array.getJSONObject(i);
				map = CollectionUtil.jsonObjectToMap_String(object);
				map.put("tag", "1");
				list_suggest.add(map);
				JSONArray array002 = new JSONArray(map.get("Projects").toString());
				if (array002.length() != 0) {
					JSONObject object2 = array002.getJSONObject(0);
					list_zhineng.add(CollectionUtil.jsonObjectToMap_String(object2));
					list_suggest.get(i).putAll(CollectionUtil.jsonObjectToMap_String(object2));
				}
			}
			
				suggest_numer.setText(list_suggest.size() + "");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 保存订单数据
	public void save_dingdan(List<Map<String, String>> list_zhineng) {
		StringBuffer sb16 = new StringBuffer();
		for (int i = 0; i < list_zhineng.size(); i++) {	
			sb16.append(list_zhineng.get(i).get("AccId"));
			sb16.append(",");
		}
		sb16.deleteCharAt(sb16.length() - 1);
		DingDanDataSave.save(SmartMaintenanceActivity.this, "AccIds", sb16.toString());
	}

	public static void add(Map<String, String> list) {
		list_zhineng.add(list);
		tongji(list_zhineng);
	}

	/// 选择删除项目
	public static void delete(String ProjectId) {
		for (int i = 0; i < list_zhineng.size(); i++) {
			if (list_zhineng.get(i).get("ProjectId").toString().equals(ProjectId)) {
				list_zhineng.remove(i);
				tongji(list_zhineng);
			}

		}

	}

	// 切换机油,刷新加格
	public static void jiyou_refsh(Map<String, String> map, int postion) {
		String ProjectId = map.get("ProjectId").toString();
		for (int i = 0; i < list_zhineng.size(); i++) {
			if (ProjectId.equals(list_zhineng.get(i).get("ProjectId").toString())) {
				list_zhineng.get(i).putAll(map);
				list_need.get(i).putAll(map);
				adapter_zhineng.notifyDataSetChanged();
			}
		}
		tongji(list_zhineng);
		tongji_need(list_need);
	}

	// 统计4s店指导价和犇车价
	public static void tongji(List<Map<String, String>> list_zhineng) {
		double gongxiang_total = 0.00;
		double bancar_toytal = 0.00;
		for (int i = 0; i < list_zhineng.size(); i++) {
			String gongxiang = list_zhineng.get(i).get("BenPrice").toString();
			String bancar = list_zhineng.get(i).get("DiscountPrice").toString();
			gongxiang_total += Double.valueOf(gongxiang).doubleValue();
			bancar_toytal += Double.valueOf(bancar).doubleValue();
		}
		zongjia.setText(String.valueOf((int) bancar_toytal));
		gongxiang_fanxian.setText("￥" + String.valueOf((int) gongxiang_total));
	}

	// 统计必须项目的犇车价
	public static void tongji_need(List<Map<String, String>> list_zhineng) {
		double bancar_toytal = 0.00;
		for (int i = 0; i < list_zhineng.size(); i++) {
			String bancar = list_zhineng.get(i).get("TimePrice_After").toString();
			bancar_toytal += Double.valueOf(bancar).doubleValue();
		}
		need_total_price.setText("工时费￥" + String.valueOf((int) bancar_toytal));
	}

	public static void shnachu(int position) {
		list_zhineng.remove(position);
		adapter_zhineng.notifyDataSetChanged();
		setListViewHeight(lv);
		tongji(list_zhineng);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		list_zhineng.clear();
		tongji(list_zhineng);
		list_zhineng001.clear();
		if (tipsrela.getVisibility()==0) {
			tipsrela.setVisibility(View.GONE);
		}
		super.onBackPressed();
	}

}
