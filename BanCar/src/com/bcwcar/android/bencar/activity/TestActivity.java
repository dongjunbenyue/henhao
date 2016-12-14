package com.bcwcar.android.bencar.activity;

import cn.jpush.android.api.JPushInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.bcwcar.android.bencar.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.TextView;

public class TestActivity extends Activity {
private TextView title_res,content_res,time;
private String mTimeString001 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss", Locale.CHINA)
.format(new Date(System.currentTimeMillis()));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.push_xml);
        title_res=(TextView)findViewById(R.id.TextView_push_title001);
        content_res=(TextView)findViewById(R.id.TextView_push_content001);
        time=(TextView)findViewById(R.id.TextView_push_time001);
        time.setText(mTimeString001);
        Intent intent = getIntent();
        if (null != intent) {
        	Bundle bundle = getIntent().getExtras();
        	String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        	String content = bundle.getString(JPushInterface.EXTRA_ALERT);
        	content_res.setText(content);
        	title_res.setText(title);
        }
        
    }

}
