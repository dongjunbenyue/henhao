package com.bcwcar.android.bencar.adapter;


import com.bcwcar.android.bencar.base.BaseActivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.model.Conversation;

public class ConversationListAdapterEx extends ConversationListAdapter {

	public ConversationListAdapterEx(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
    protected View newView(Context context, int position, ViewGroup group) {
		System.out.println("position=="+position);
		 return super.newView(context, position, group);
    }

    @Override
    protected void bindView(View v, int position, UIConversation data) {
    	        super.bindView(v, position, data);
      BaseActivity.changeFonts((ViewGroup)v);
    }
}
