package com.bcwcar.android.bencar.widget;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bcwcar.android.bencar.R;
import com.bcwcar.android.bencar.activity.PayQuanActivity;
import com.bcwcar.android.bencar.activity.PaymentMethodActivity;
import com.bcwcar.android.bencar.activity.PaymentResultActivity;
import com.bcwcar.android.bencar.activity.SelectShop4S;
import com.bcwcar.android.bencar.activity.MainMy.MyWalletCouponActivity;
import com.bcwcar.android.bencar.activity.MainMy.WithdrawCashResultActivity;
import com.bcwcar.android.bencar.base.BaseActivity;
import com.bcwcar.android.bencar.biz.BizDefineAll;
import com.bcwcar.android.bencar.biz.HttpOrder;
import com.bcwcar.android.bencar.biz.HttpWallet;
import com.bcwcar.android.bencar.datasave.DingDanDataSave;
import com.bcwcar.android.bencar.datasave.QuanDingDanDataSave;
import com.bcwcar.android.bencar.datasave.UserLoginStatus;
import com.bcwcar.android.bencar.datasave.WithdrawData;
import com.bcwcar.android.bencar.http.OkHttpHelper.CallbackLogic;
import com.bcwcar.android.bencar.http.OkHttpHelper002.CallbackLogic002;
import com.bcwcar.android.bencar.util.StringUtil;
import com.bcwcar.android.bencar.wxapi.WXPayEntryActivity;
import com.squareup.okhttp.Request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.Toast;

public class KeyboardUtil {
	private String action;
	private Context ctx;
	private Activity act;
	private KeyboardView keyboardView;
	private Keyboard k1;// 数字键盘
	int[] ids={R.id.EditText_pay_password_key001,R.id.EditText_pay_password_key002,R.id.EditText_pay_password_key003
			,R.id.EditText_pay_password_key004,R.id.EditText_pay_password_key005,R.id.EditText_pay_password_key006};
	private EditText[] passwords;
	private int zhizhen=0;
	public KeyboardUtil(Activity act, Context ctx,String action) {
		this.act = act;
		this.ctx = ctx;
		this.action=action;
		initview();
	}
	// 实例化控件
		public void initview() {
	//键盘
			
			k1 = new Keyboard(ctx, R.layout.symbols);
			keyboardView = (KeyboardView) act.findViewById(R.id.keyboard_view);
			keyboardView.setKeyboard(k1);
			keyboardView.setEnabled(true);
		    keyboardView.setPreviewEnabled(false);
			keyboardView.setOnKeyboardActionListener(listener);
		//输入框
			passwords=new EditText[6];
			for (int i = 0; i < ids.length; i++) {
				passwords[i]=(EditText)act.findViewById(ids[i]);
				passwords[i].setInputType(InputType.TYPE_NULL);
				passwords[i].setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						showKeyboard();
						return false;
					}
				});
				passwords[i].addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub
						
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
			}
		}
		
		
	private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
		@Override
		public void swipeUp() {
			
		}

		@Override
		public void swipeRight() {
			
		}

		@Override
		public void swipeLeft() {
		}

		@Override
		public void swipeDown() {
		}

		@Override
		public void onText(CharSequence text) {
		}

		@Override
		public void onRelease(int primaryCode) {
		}

		@Override
		public void onPress(int primaryCode) {
		}

		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			System.out.println("primaryCode==="+primaryCode);
			if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
				hideKeyboard();
			} else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
				for (int j = 0; j < ids.length; j++) {
					passwords[j].setText("");
					zhizhen=0;
				}
			}  else {
				if (zhizhen==6) {
					hideKeyboard();
					return;
				}
				//设置为秘闻显示
		passwords[zhizhen].setTransformationMethod(PasswordTransformationMethod.getInstance());
				passwords[zhizhen].setText(Character.toString((char) primaryCode));
				zhizhen++;
				if (zhizhen==6) {
					hideKeyboard();
					System.out.println("action-======"+action);
					if (action.equals("pay")) {
							pay_edittext();
					}else if(action.equals("set")) {
						setpaypassword(getpassword());
					}else if(action.equals("reset")) {
						setpaypassword(getpassword());
					}else if(action.equals("PayQuanActivity")) {
						pay(getpassword());
					}else if(action.equals("PaymentMethodActivity")) {
						payadd(getpassword());
					}
				}
			}
		}
	};

	//显示键盘
	public void showKeyboard() {
		int visibility = keyboardView.getVisibility();
		if (visibility == View.GONE || visibility == View.INVISIBLE) {
			keyboardView.setVisibility(View.VISIBLE);
		}
	}
//隐藏键盘
	public void hideKeyboard() {
		int visibility = keyboardView.getVisibility();
		if (visibility == View.VISIBLE) {
			keyboardView.setVisibility(View.GONE);
		}
	}
	//隐藏键盘
		public String getpassword() {
			StringBuffer sb=new StringBuffer("");
			for (int i = 0; i < ids.length; i++) {
				if (StringUtil.isEmpty(passwords[i].getText().toString())) {
					return null;
				}
				sb.append(passwords[i].getText().toString());
			}
			return sb.toString();
		}
        //设置支付密码
		public void setpaypassword(final String CashPass){
			
			HttpWallet.applyCashPass(new CallbackLogic() {
			
				@Override
				public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
					// TODO Auto-generated method stub
					Toast.makeText(act, "密码设置成功", Toast.LENGTH_LONG).show();
					UserLoginStatus.save(ctx, "PayFlag", "1");
					UserLoginStatus.save(ctx, "PayPass", BaseActivity.MD5Encode(CashPass,"utf-8"));
					act.finish();
				}
			}, UserLoginStatus.get(act,"Token"),  BaseActivity.MD5Encode(CashPass,"utf-8"),act);
			
		}
//提现
		public void pay_edittext(){
		

		HttpWallet.applyCashWithdraw(new CallbackLogic002() {
			
			@Override
			public void onNetworkError(Request request, IOException e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onBizSuccess(String responseDescription, JSONObject data, String other) {
				// TODO Auto-generated method stub
				Toast.makeText(ctx, "提现成功", Toast.LENGTH_SHORT).show();
				act.finish();
			}
			
			@Override
			public void onBizFailure(String responseDescription, JSONObject data, String responseCode) {
				// TODO Auto-generated method stub
				act.finish();
				Toast.makeText(act, responseDescription, 1).show();;
				
			}
		},UserLoginStatus.get(act,"Token"),WithdrawData.getWithdrawData(act, "BankId") ,WithdrawData.getWithdrawData(act, "CashSum"),BaseActivity.MD5Encode(getpassword(), "utf-8"),act
				);
		
	}

//支付确认
		public void pay(String password){
			System.out.println("password==="+password); 
			String md=BaseActivity.MD5Encode(password,"utf-8");
			System.out.println("md=="+md); 
			System.out.println("PayPass=="+UserLoginStatus.get(ctx, "PayPass").toString()); 
			if (md.equals(UserLoginStatus.get(ctx, "PayPass"))) {
				HttpWallet.confirmUserTicket(new CallbackLogic() {
					@Override
					public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
						// TODO Auto-generated method stub
						Toast.makeText(ctx, "支付成功", Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(ctx, MyWalletCouponActivity.class);
						intent.putExtra(BizDefineAll.BIZ_ACTION, action);
						act.startActivity(intent);
						act.finish();
					}
				}, UserLoginStatus.get(ctx, "Token"),
						QuanDingDanDataSave.get(ctx, "UserTicketId"), "3", ctx);
			}else {
				Toast.makeText(ctx, "密码错误", 1).show();
			}
		}
//订单支付
		public void payadd(String password){
			System.out.println("password==="+password); 
			String md=BaseActivity.MD5Encode(password,"utf-8");
			System.out.println("md=="+md); 
			System.out.println("PayPass=="+UserLoginStatus.get(ctx, "PayPass").toString()); 
			if (md.equals(UserLoginStatus.get(ctx, "PayPass"))) {
				HttpOrder.commitOrder(new CallbackLogic() {
					@Override
					public void onBizSuccess(String responseDescription, JSONObject data, JSONObject alldata) {
						// TODO Auto-generated method stub
						Toast.makeText(ctx, "支付成功", 1).show();
						Intent intent=new Intent(ctx, PaymentResultActivity.class);
						intent.putExtra("OrderId", DingDanDataSave.get(ctx, "OrderId"));
						act.startActivity(intent);
						act.finish();
					}
				}, UserLoginStatus.get(ctx, "Token"), DingDanDataSave.get(ctx, "OrderId"), "3",DingDanDataSave.get(ctx, "Type"), ctx);

			}else {
				Toast.makeText(ctx, "密码错误", 1).show();
			}
			
		}	
}
