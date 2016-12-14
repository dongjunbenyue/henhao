package com.bcwcar.android.bencar.activity.MainMy;

import com.bcwcar.android.bencar.R;

import com.bcwcar.android.bencar.base.BaseActivity;

import com.bcwcar.android.bencar.util.PageUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 常见问题页
 */
public class FaqActivity extends BaseActivity {
	private static final String LOG_TAG = FaqActivity.class.getSimpleName();
	RelativeLayout rela1,rela2,rela3,rela4,rela5,rela6;
	LinearLayout line1,line2,line3,line4,line5,line6;
	TextView text1,text2,text3,text4,text5,text6;
	ImageView image1,image2,image3,image4,image5,image6;
	@Override
	public void setPageTitle(ViewGroup titleParentView) {
		View rootView = LayoutInflater.from(this).inflate(R.layout.common_title_1, titleParentView);
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
		centerView.getPaint().setFakeBoldText(true);  //中文字体加粗
		centerView.setText("常见问题");
		rightView.setVisibility(View.GONE);
		
	changeFonts(titleParentView);	
	}

	@Override
	public void setPageBody(ViewGroup bodyParentView) {
		LayoutInflater.from(this).inflate(R.layout.changjianwenti, bodyParentView);
		rela1=(RelativeLayout)findViewById(R.id.changjianwenti1);
		rela2=(RelativeLayout)findViewById(R.id.changjianwenti2);
		rela3=(RelativeLayout)findViewById(R.id.changjianwenti3);
		rela4=(RelativeLayout)findViewById(R.id.changjianwenti4);
		rela5=(RelativeLayout)findViewById(R.id.changjianwenti5);
		rela6=(RelativeLayout)findViewById(R.id.changjianwenti6);
		line1=(LinearLayout)findViewById(R.id.changjianwenti11);
		line2=(LinearLayout)findViewById(R.id.changjianwenti22);
		line3=(LinearLayout)findViewById(R.id.changjianwenti33);
		line4=(LinearLayout)findViewById(R.id.changjianwenti44);
		line5=(LinearLayout)findViewById(R.id.changjianwenti55);
		line6=(LinearLayout)findViewById(R.id.changjianwenti66);
		text1=(TextView)findViewById(R.id.diyige);
		text2=(TextView)findViewById(R.id.dierge);
		text3=(TextView)findViewById(R.id.disange);
		text4=(TextView)findViewById(R.id.disige);
		text5=(TextView)findViewById(R.id.diwuge);
		text6=(TextView)findViewById(R.id.diliuge);
		image1=(ImageView)findViewById(R.id.imageview100);
		image2=(ImageView)findViewById(R.id.imageview101);
		image3=(ImageView)findViewById(R.id.imageview102);
		image4=(ImageView)findViewById(R.id.imageview103);
		image5=(ImageView)findViewById(R.id.imageview104);
		image6=(ImageView)findViewById(R.id.imageview105);
		image1.setBackgroundResource(R.drawable.down);
		image2.setBackgroundResource(R.drawable.down);
		image3.setBackgroundResource(R.drawable.down);
		image4.setBackgroundResource(R.drawable.down);
		image5.setBackgroundResource(R.drawable.down);
		image6.setBackgroundResource(R.drawable.down);
		ImageView imageView=(ImageView)findViewById(R.id.changjianwenticall);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400-018-5025"));
				//Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:400-018-5025"));
				 FaqActivity.this.startActivity(intent);
			}
		});
		rela1.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (line1.getVisibility()==0) {
					line1.setVisibility(View.GONE);
					image1.setBackgroundResource(R.drawable.down);
				}else {
					line1.setVisibility(View.VISIBLE);
					image1.setBackgroundResource(R.drawable.up);
				}
				
				
			}
		});
		rela2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (line2.getVisibility()==0) {
					line2.setVisibility(View.GONE);
					image2.setBackgroundResource(R.drawable.down);
				}else {
					line2.setVisibility(View.VISIBLE);
					image2.setBackgroundResource(R.drawable.up);
				}
				
			}
		});
		rela3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (line3.getVisibility()==0) {
					line3.setVisibility(View.GONE);
					image3.setBackgroundResource(R.drawable.down);
				}else {
					line3.setVisibility(View.VISIBLE);
					image3.setBackgroundResource(R.drawable.up);
				}
				
			}
		});
		rela4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (line4.getVisibility()==0) {
					line4.setVisibility(View.GONE);
					
					image4.setBackgroundResource(R.drawable.down);
				}else {
					line4.setVisibility(View.VISIBLE);
					image4.setBackgroundResource(R.drawable.up);
				}
			}
		});
		rela5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (line5.getVisibility()==0) {
					line5.setVisibility(View.GONE);
					image5.setBackgroundResource(R.drawable.down);
				}else {
					line5.setVisibility(View.VISIBLE);
					image5.setBackgroundResource(R.drawable.up);
				}
			}
		});
		rela6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (line6.getVisibility()==0) {
					line6.setVisibility(View.GONE);
					image6.setBackgroundResource(R.drawable.down);
				}else {
					line6.setVisibility(View.VISIBLE);
					image6.setBackgroundResource(R.drawable.up);
				}
			}
		});
//		line1.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				line1.setVisibility(View.GONE);
//			}
//		});
//		line2.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				line2.setVisibility(View.GONE);
//			}
//		});
//		line3.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				line3.setVisibility(View.GONE);
//			}
//		});
//		line4.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				line4.setVisibility(View.GONE);
//			}
//		});
//		line5.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				line5.setVisibility(View.GONE);
//			}
//		});
//		line6.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				line6.setVisibility(View.GONE);
//			}
//		});
//		
	changeFonts(bodyParentView);
	}

	
	
	@Override
	public void setPageToolBar(ViewGroup toolBarParentView) {
		//没有工具栏，不用设置！
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.faq_expandable_listview);  
//		expandableListView.setAdapter(new FaqAdapter(this));  
	}
}
