package com.bcwcar.android.bencar.base;

import android.view.View;
import android.view.ViewGroup;

public interface IBaseActivity {
	/**
	 * 用户在Activity中必须将定义好的Title View添加到titleParentView中去。
	 * 如果用户没有设置自己定义好的标题，标题区域会被设置成GONE
	 */
	void setPageTitle(ViewGroup titleParentView);
	/**
	 * 用户在Activity中必须将定义好的Body View添加到bodyParentView中去
	 */
	void setPageBody(ViewGroup bodyParentView);
	/**
	 * 用户在Activity中必须将定义好的ToolBar View添加到toolBarParentView中去。
	 * 如果用户没有设置自己定义好的工具栏，工具栏区域会被设置成GONE
	 */
	void setPageToolBar(ViewGroup toolBarParentView);

}