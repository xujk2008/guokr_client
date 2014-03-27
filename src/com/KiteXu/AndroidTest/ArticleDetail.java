package com.KiteXu.AndroidTest;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.KiteXu.AndroidTest.R.color;
import com.KiteXu.AndroidTest.common.JsonData;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

public class ArticleDetail extends SherlockActivity{

	private static String id;
	
	private View mainView;
	private Map<String, String> mData = new HashMap<String, String>();
	private LinearLayout articleDetailProgressLayout, articleHead;
	private ImageView return_icon;
	private RelativeLayout return_to_main;
	private TextView title, author, time;
	private WebView articleContent;
	private ImageView floatImage;
	private final int SUCCESS = 1, FAIL = 0;
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("@@@@@@", "ArticleDetail destroied");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("@@@@@@", "ArticleDetail Paused");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("@@@@@@", "ArticleDetail resumed");
		
		if(floatImage != null)
		{
			floatImage.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("@@@@@@", "ArticleDetail stopped");
		floatImage.setVisibility(View.GONE);
	}
	
	Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			switch(msg.what)
			{
			case SUCCESS:
				articleDetailProgressLayout.setVisibility(View.GONE);
				
				title.setText(mData.get("title"));
				author.setText(mData.get("author"));
				time.setText(mData.get("time"));
				
				WebSettings settings = articleContent.getSettings();
				//使图片自适应页面大小
				settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
				articleContent.loadData(mData.get("content"), "text/html; charset=UTF-8", null);
				articleContent.setVisibility(View.VISIBLE);
				
				//WebView设置背景色需要在加载数据之后
//				articleContent.setBackgroundColor(0xfff00000);
				articleContent.setBackgroundColor(0x00000000);
				articleContent.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
				
				setBacktoTopImage();
				
				break;
			case FAIL:
				articleDetailProgressLayout.setVisibility(View.GONE);
				Toast.makeText(ArticleDetail.this, "请检查网络连接是否正确", Toast.LENGTH_LONG).show();
				break;
			}
		}
		
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		MenuItem refresh = menu.add("replay");
		refresh.setIcon(R.drawable.chat);
		refresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		refresh.setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(ArticleDetail.this, ArticleReply.class);
				intent.putExtra("id", id);
				ArticleDetail.this.startActivity(intent);
				
				return true;
			}
			
		});
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_detail);
		
		initActionBar();
		
		articleDetailProgressLayout = (LinearLayout)findViewById(R.id.articleDetailProgressLayout);
		articleHead = (LinearLayout)findViewById(R.id.articleHead);
		title = (TextView)findViewById(R.id.title);
		author = (TextView)findViewById(R.id.author);
		time = (TextView)findViewById(R.id.time);
		
		articleContent = (WebView)findViewById(R.id.articleContent);
		
		articleDetailProgressLayout.setVisibility(View.VISIBLE);
		mData.clear();
		
		//注意要在manifest文件里添加android:hardwareAccelerated="false"
		//否则会出现WebView背景色不一致，闪烁等问题
		articleContent.setVisibility(View.GONE);
		
		id = (String)(getIntent().getStringExtra("id"));
		initText(id);
	}
	
	public Map<String, String> getArticleContent(String id)
	{
		String path = "http://apis.guokr.com/minisite/article/" + id + ".json";
		JsonData jsonData = new JsonData(path);
		
		String json = jsonData.getJson();
		
		HashMap<String, String> data = new HashMap<String, String>();
		
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONObject resultObject = (JSONObject)jsonObject.get("result");
			String content = resultObject.getString("content");
			String title = resultObject.getString("title");
			String date_published = resultObject.getString("date_published");
			String time = date_published.substring(0, 10)+" "+date_published.substring(11, 19);
			
			JSONObject authorObject = (JSONObject)resultObject.get("author");
			String author = authorObject.getString("nickname");
			
			data.put("content", content);
			data.put("title", title);
			data.put("time", time);
			data.put("author", author);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	public void initText(final String id)
	{
		new Thread()
		{
			public void run()
			{
				mData = getArticleContent(id);
				
				if(!(mData.isEmpty()))
				{
					handler.sendEmptyMessage(SUCCESS);
				}
				else
				{
					handler.sendEmptyMessage(FAIL);
				}
			}
		}.start();
	}
	
	private void initActionBar() {
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);

		getSupportActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.actionbar_tab_bg));
		View mainActionBarView = LayoutInflater.from(this).inflate(
				R.layout.main_aciton_bar, null);
		
		return_icon = (ImageView)(mainActionBarView.findViewById(R.id.return_icon));
		return_icon.setVisibility(View.VISIBLE);
		
		return_to_main = (RelativeLayout)(mainActionBarView.findViewById(R.id.return_to_main));
		return_to_main.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				
				switch(event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					
					return_to_main.setBackgroundColor(Color.WHITE);
					Log.v("@@@@@@", "ACTION_DOWN");
					
					break;
				case MotionEvent.ACTION_UP:
					
					return_to_main.setBackgroundColor(0x000000);
					Log.v("@@@@@@", "ACTION_UP");
					ArticleDetail.this.finish();
					
					break;
				}
				
				return true;
			}
			
		});
		
		getSupportActionBar().setCustomView(mainActionBarView);
	}
	
	public void setBacktoTopImage()
	{
		final WindowManager manager = (WindowManager)(ArticleDetail.this.getSystemService("window"));
		WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
		
		floatImage = new ImageView(ArticleDetail.this);
		floatImage.setImageResource(R.drawable.arrow_up);
		
		//设置图标尺寸
		floatImage.setAdjustViewBounds(true);
		floatImage.setMaxHeight(60);
		floatImage.setMaxWidth(60);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT);
		floatImage.setLayoutParams(params);
		floatImage.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				articleContent.scrollTo(0, 0);

			}
			
		});
		
		windowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		//设置背景透明
		windowParams.format = PixelFormat.RGBA_8888;
		//不影响其他部分的操作
		windowParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
		
		windowParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
		windowParams.x = 50;
		windowParams.y = 80;
		
		windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		
		manager.addView(floatImage, windowParams);
	} 
}
