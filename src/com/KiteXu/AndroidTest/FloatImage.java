package com.KiteXu.AndroidTest;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class FloatImage extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.float_image);
		
		final WindowManager manager = (WindowManager)getSystemService("window");
		WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
		
		final ListView backList = (ListView)findViewById(R.id.backList);
		String[] listContent = new String[18];
		for(int i=0; i<18; i++)
		{
			listContent[i] = "item"+(i+1);
		}
		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listContent);
		backList.setAdapter(mAdapter);
		
		final ImageView floatImage = new ImageView(this);
		floatImage.setImageResource(R.drawable.ic_launcher);
		floatImage.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				backList.setSelection(0);
			}
			
		});
		
		windowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		//设置背景透明
		windowParams.format = PixelFormat.RGBA_8888;
		//不影响其他部分的操作
		windowParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
		
		windowParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
		windowParams.x = 100;
		windowParams.y = 100;
		
		windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		
		manager.addView(floatImage, windowParams);
	}
	
}
