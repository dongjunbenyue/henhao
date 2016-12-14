package com.bcwcar.android.bencar.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.squareup.okhttp.Request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.MainMy.AddAdress1;
import com.bcwcar.android.bencar.activity.MainMy.UserAdress;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.HttpUserInfo;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.util.PageUtil;

import android.widget.Toast;

public class UserAdressAdapter extends BaseAdapter {
	private List<Map<String, String>> list;
	private Context context;
	private int tt = 0;
	private int postion_zhizheng = 0;
	private Activity activity;
	private String action;
private List<CheckBox> lsit_checkbox = new ArrayList<CheckBox>();
	public UserAdressAdapter(Context context, List<Map<String, String>> list, Activity activity, String action) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.activity = activity;
		this.action = action;
		
	}
	public  void set_lingjiang_data(String key,String value){
		 SharedPreferences lingjiang=context.getSharedPreferences("lingjiang", 0);
		 SharedPreferences.Editor editor=lingjiang.edit();
		 editor.putString(key, value);
		 editor.commit();
	 }
	public String get_lingjiang_data(String key){
		SharedPreferences lingjiang = context.getSharedPreferences("lingjiang", 0);
		String string = lingjiang.getString(key, "").toString();
		return string;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_adress_item, parent, false);
		}

		final ImageView up_down = (ImageView) convertView.findViewById(R.id.ImageView_up_down001);
		LinearLayout dianji=(LinearLayout)convertView.findViewById(R.id.LinearLayout_address_jiandan001);
		LinearLayout xianshi = (LinearLayout) convertView.findViewById(R.id.linearLayout_address_button001);
		final LinearLayout show_detail = (LinearLayout) convertView.findViewById(R.id.LinearLayout_address_show001);
		CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox_user_adress_item001);
		lsit_checkbox.add(checkBox);
		System.out.println("chekbox==changsdi===="+lsit_checkbox.size());
		TextView adress = (TextView) convertView.findViewById(R.id.textView_user_adress_item001);
		TextView contact=(TextView)convertView.findViewById(R.id.textView_user_adress_item002);
		// 编辑和保存--删除按钮
		TextView name_xiugai = (TextView) convertView.findViewById(R.id.TextView_address_item_name001);
		TextView phone_xiugai = (TextView) convertView.findViewById(R.id.TextView_address_item_phone001);
		TextView youbian_xiugai = (TextView) convertView.findViewById(R.id.TextView_address_item_youbian001);
		TextView save = (TextView) convertView.findViewById(R.id.TextView_address_item_save001);
		TextView delete = (TextView) convertView.findViewById(R.id.TextView_address_item_delete001);
		// 编辑框
		final EditText address_EditText = (EditText) convertView.findViewById(R.id.EditText_address_item_address001);
		final EditText name_EditText = (EditText) convertView.findViewById(R.id.EditText_address_item_name001);
		final EditText phone_EditText = (EditText) convertView.findViewById(R.id.EditText_address_item_phone001);
		final EditText youbian_EditText = (EditText) convertView.findViewById(R.id.EditText_address_item_youbian001);
		shiqu_jiaodian(youbian_EditText);
		shiqu_jiaodian(phone_EditText);
		shiqu_jiaodian(name_EditText);
		shiqu_jiaodian(address_EditText);

		name_xiugai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				huode_jiaodian(name_EditText);
				shiqu_jiaodian(youbian_EditText);
				shiqu_jiaodian(phone_EditText);
			}
		});
		phone_xiugai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				huode_jiaodian(phone_EditText);
				shiqu_jiaodian(youbian_EditText);
				shiqu_jiaodian(name_EditText);
			}
		});
		youbian_xiugai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				huode_jiaodian(youbian_EditText);
				shiqu_jiaodian(phone_EditText);
				shiqu_jiaodian(name_EditText);
			}
		});

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String string001 = address_EditText.getText().toString();
				String string002 = name_EditText.getText().toString();
				String string003 = phone_EditText.getText().toString();
				String string004 = youbian_EditText.getText().toString();
				if (!string001.equals("") && !string002.equals("") && !string003.equals("") && !string004.equals("")) {
					tt = 0;
					show_detail.setVisibility(View.GONE);
					up_down.setImageResource(R.drawable.xiabiao);
					http_xiugai_user_address(list.get(position).get("AddressId").toString(), string002, string003,
							string001, string004, list.get(position).get("ProvinceId").toString(),
							list.get(position).get("ProvinceName").toString(),
							list.get(position).get("CityId").toString(), list.get(position).get("CityName").toString(),
							list.get(position).get("DistrictId").toString(),
							list.get(position).get("DistrictName").toString(), "0");
					
				} else {
					Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
				}
			}
		});

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				http_shanchu_user_address(list.get(position).get("AddressId").toString(), position);
			}
		});

		dianji.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				
				bundle.putString("AddressId", list.get(position).get("AddressId").toString());
				bundle.putString("Contact", list.get(position).get("Contact").toString());
				bundle.putString("ContactPhone", list.get(position).get("ContactPhone").toString());
				bundle.putString("ZipCode", list.get(position).get("ZipCode").toString());
				bundle.putString("ProvinceName", list.get(position).get("ProvinceName").toString());
				bundle.putString("CityId", list.get(position).get("CityId").toString());
				bundle.putString("DistrictId", list.get(position).get("DistrictId").toString());
				bundle.putString("DistrictName", list.get(position).get("DistrictName").toString());
				bundle.putString("ProvinceId", list.get(position).get("ProvinceId").toString());
				bundle.putString("CityName", list.get(position).get("CityName").toString());
				bundle.putString("Address", list.get(position).get("Address").toString());
				PageUtil.jumpTo(context, AddAdress1.class,bundle);
			}
		});
		if (action.equals("0")) {
			xianshi.setVisibility(View.GONE);
            checkBox.setVisibility(View.VISIBLE);
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked) {
						for (int i = 0; i < lsit_checkbox.size(); i++) {
							lsit_checkbox.get(i).setChecked(false);
							lsit_checkbox.get(position).setChecked(true);
						}
//						view_zhizhen=convertView;
//						CheckBox checkBox = (CheckBox) view_zhizhen.findViewById(R.id.CheckBox_user_adress_item001);
//						checkBox.setChecked(false);
//						CheckBox checkBox001 = (CheckBox) convertView.findViewById(R.id.CheckBox_user_adress_item001);
//						checkBox001.setChecked(false);
						String address_total = list.get(position).get("ProvinceName").toString()
								+ list.get(position).get("CityName").toString()
								+ list.get(position).get("DistrictName").toString()
								+ list.get(position).get("Address").toString();
						
						set_lingjiang_data("AddressId", list.get(position).get("AddressId").toString());
						set_lingjiang_data("AddressName", address_total);
						System.out.println("addressid+.........."+get_lingjiang_data("AddressId"));
					} else {
						
						return;
					}
				}
			});
		} else if (action.equals("1")) {
			checkBox.setVisibility(View.GONE);
		} else if (action.equals("2")) {
			 checkBox.setVisibility(View.VISIBLE);
			 xianshi.setVisibility(View.GONE);
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked) {
						for (int i = 0; i < lsit_checkbox.size(); i++) {
							lsit_checkbox.get(i).setChecked(false);
							lsit_checkbox.get(position).setChecked(true);
						}
						// String
						// address_total=list.get(position).get("ProvinceName").toString()+list.get(position).get("CityName").toString()+list.get(position).get("DistrictName").toString()+list.get(position).get("Address").toString();
						// JiFenDuiHuanPresent.duihuan_persent(list.get(position).get("AddressId").toString(),
						// address_total);
						//CheatmoneyPrize.cheatmoneylingjiang(list.get(position).get("AddressId").toString());
						WithdrawData.setWithdrawData(context,"AddressId", list.get(position).get("AddressId").toString());
						
					} else {
						return;
					}
				}
			});

		}
		String string = list.get(position).get("ProvinceName").toString()
				+ list.get(position).get("CityName").toString() + list.get(position).get("DistrictName").toString()
				+ list.get(position).get("Address").toString();
		adress.setText(string);
		contact.setText(list.get(position).get("Contact").toString());
		address_EditText.setText(string);
		name_EditText.setText(list.get(position).get("Contact").toString());
		phone_EditText.setText(list.get(position).get("ContactPhone").toString());
		youbian_EditText.setText(list.get(position).get("ZipCode").toString());
		BaseActivity.changeFonts((ViewGroup)convertView);
		return convertView;
		
	}

	// 失去焦点
	public void shiqu_jiaodian(EditText editText) {
		editText.setFocusable(false);
		editText.setFocusableInTouchMode(false);

	}

	// 获得焦点
	public void huode_jiaodian(EditText editText) {
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus(0);
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

	}

	// 修改用户地址
	public void http_xiugai_user_address(String addressId, String contact, String contactPhone, String address,
			String zipCode, String provinceId, String provinceName, String cityId, String cityName, String districtId,
			String districtName, String defaultFlag) {

		HttpUserInfo.updateAddress(new CallbackLogic() {

			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "成功修改", 1).show();
			}

			
		}, UserLoginStatus.get(context,"Token"), addressId, contact, contactPhone, address, zipCode, provinceId,
				provinceName, cityId, cityName, districtId, districtName, defaultFlag,context);

	}

	// 删除用户地址
	public void http_shanchu_user_address(String addressId, final int postion) {
		HttpUserInfo.deleteAddress(new CallbackLogic() {

			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data,JSONObject alldata) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "成功删除", 1).show();
				UserAdress.dele_user_address(postion);
			}

			
		}, UserLoginStatus.get(context,"Token"), addressId,context);

	}
}
