package com.example.myviewpager;
import java.util.zip.Inflater;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.myviewpager.MyViewPager.PageChangeListener;


public class MainActivity extends Activity {

	private MyViewPager myViewPager;
	private RadioGroup radioGroup;
	private int[] picIds ={R.drawable.a1,R.drawable.a2,R.drawable.a3,
			R.drawable.a4,R.drawable.a5}; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myViewPager = (MyViewPager) findViewById(R.id.myViewPager);
		radioGroup = (RadioGroup) findViewById(R.id.radio_group);
		
		for (int i = 0; i < picIds.length; i++) {
			ImageView img = new ImageView(this);
			img.setBackgroundResource(picIds[i]);
			myViewPager.addView(img);
			
		}
		
		View otherView = getLayoutInflater().inflate(R.layout.other, null);
		myViewPager.addView(otherView, 2);
		
		for (int i = 0; i < myViewPager.getChildCount(); i++) {
			RadioButton radioButton = new RadioButton(this);
			radioButton.setId(i);   //radioButton的id和自定义的viewPager的id一致
			radioGroup.addView(radioButton);
			if(i == 0){
				radioButton.setChecked(true);
			}
		}
		
		myViewPager.setPageChangeListener(new PageChangeListener() {
			
			@Override
			public void moveToDest(int currId) {
				((RadioButton)radioGroup.getChildAt(currId)).setChecked(true);
			}
		});
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				myViewPager.moveToDest(checkedId);
			}
		});
	}
	
}
