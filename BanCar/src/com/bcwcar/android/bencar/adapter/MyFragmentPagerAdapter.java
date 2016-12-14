package com.bcwcar.android.bencar.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{  
   private List<Fragment> list;  
    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> list) {  
        super(fm);  
        this.list = list;  
    }  
      
    @Override  
    public int getCount() {  
        return list.size();  
    }  
    @Override  
    public Fragment getItem(int arg0) {  
        return list.get(arg0);  
    }  
      
      
      
      
}  