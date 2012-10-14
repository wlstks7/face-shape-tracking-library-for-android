package com.example.facesdk.demo01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainSelectorActivity extends Activity{

	Button btn01, btn02, btn03;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		
		setContentView(R.layout.main_selector_activity_layout);
		
		btn01 = (Button)findViewById(R.id.selector_btn01);
		btn02 = (Button)findViewById(R.id.selector_btn02);
		btn03 = (Button)findViewById(R.id.selector_btn03);
		
		btn01.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent mIntent = new Intent(MainSelectorActivity.this, DebugActivity.class);
				startActivity(mIntent);
			}
		}); 
		btn02.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent mIntent = new Intent(MainSelectorActivity.this, SphereDemo.class);
				startActivity(mIntent);
			}
		});
		
		btn03.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent mIntent = new Intent(MainSelectorActivity.this, PortraitDemo.class);
				startActivity(mIntent);
			}
		});
	}
	
	
}