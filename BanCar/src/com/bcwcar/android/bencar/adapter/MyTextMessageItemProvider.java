package com.bcwcar.android.bencar.adapter;


import com.bcwcar.android.bencar.base.BaseActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.view.ViewGroup;
import io.rong.imkit.R;
import io.rong.imkit.RongContext;
import io.rong.imkit.model.LinkTextView;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.message.TextMessage;

@ProviderTag(messageContent = TextMessage.class, showPortrait = true, centerInHorizontal = false)
public class MyTextMessageItemProvider extends TextMessageItemProvider{
	@SuppressLint("NewApi")
	@Override
	public void bindView(View view, int arg1, TextMessage arg2, UIMessage arg3) {
		// TODO Auto-generated method stub
		super.bindView(view, arg1, arg2, arg3);
		BaseActivity.changeFonts((ViewGroup) view);
	}
	@Override
	public View newView(Context context, ViewGroup group) {
		// TODO Auto-generated method stub
		return super.newView(context, group);
	}
}
