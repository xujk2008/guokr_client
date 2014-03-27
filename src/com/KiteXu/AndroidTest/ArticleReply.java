package com.KiteXu.AndroidTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.KiteXu.AndroidTest.Adapters.ReplyAdapter;
import com.KiteXu.AndroidTest.common.JsonData;
import com.KiteXu.AndroidTest.common.URLImg;
import com.actionbarsherlock.app.SherlockActivity;

public class ArticleReply extends SherlockActivity{

	private ListView replyList;
	private LinearLayout replyProgressLayout;
	
	private ImageView return_icon;
	private RelativeLayout return_to_main;
	
	private String id;
	
	private ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
	
	private final int SUCCESS = 1, FAIL = 0;
	
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			switch(msg.what)
			{
			case SUCCESS:
				
				replyProgressLayout.setVisibility(View.GONE);
				replyList.setVisibility(View.VISIBLE);
				
				final ReplyAdapter adapter = new ReplyAdapter(ArticleReply.this, mData);
				replyList.setAdapter(adapter);
				
				replyList.setVisibility(View.VISIBLE);
				break;
			case FAIL:
				replyProgressLayout.setVisibility(View.GONE);
				Toast.makeText(ArticleReply.this, "请检查网络连接是否正确", Toast.LENGTH_LONG).show();
				break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_reply);
		
		initActionBar();
		
		replyList = (ListView)findViewById(R.id.replyList);
		replyProgressLayout = (LinearLayout)findViewById(R.id.replyProgressLayout);
		
		id = getIntent().getStringExtra("id");
		initReply();
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
					
					break;
				case MotionEvent.ACTION_UP:
					
					return_to_main.setBackgroundColor(0x000000);
					ArticleReply.this.finish();
					
					break;
				}
				
				return true;
			}
			
		});
		
		getSupportActionBar().setCustomView(mainActionBarView);
	}
	
	public void initReply()
	{

		replyProgressLayout.setVisibility(View.VISIBLE);
		replyList.setVisibility(View.GONE);
		
		new Thread()
		{
			public void run()
			{
				Log.v("@@@@@@", "id is " + id);
				
				mData = getData(id);

				if (!(mData.isEmpty())) {
					handler.sendEmptyMessage(SUCCESS);
				} else {
					handler.sendEmptyMessage(FAIL);
				}
			}
		}.start();
	}
	
	public ArrayList<Map<String, Object>> getData(String id)
	{
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		String path = "http://apis.guokr.com/minisite/article_reply.json?article_id=" + id;
		
		JsonData jsonData = new JsonData(path);
		JSONObject jsonObject = null;
		
		try {
			String json = jsonData.getJson();
			
			jsonObject = new JSONObject(json);
			JSONArray result = jsonObject.getJSONArray("result");
			
			for(int i=0; i<result.length(); i++)
			{
				JSONObject curObject = result.getJSONObject(i);
				
				String replyContent = curObject.getString("html");
				String replyTime = curObject.getString("date_created");
				replyTime = replyTime.substring(0, 10)+" "+replyTime.substring(11, 19);
				
				JSONObject authorObject = curObject.getJSONObject("author");
				String authorImgURL = (authorObject.getJSONObject("avatar")).getString("normal");
				String authorName = authorObject.getString("nickname");
				
				Log.v("@@@@@@", replyContent);
				Log.v("@@@@@@", authorName);
				Log.v("@@@@@@", replyTime);
				Log.v("@@@@@@", authorImgURL);
				
				Map map = new HashMap<String, Object>();
				map.put("replyContent", replyContent);
				map.put("replyTime", replyTime);
				map.put("authorName", authorName);
				Bitmap authorImg = (new URLImg(authorImgURL)).getBitMap();
				map.put("authorImg", authorImg);
				
				data.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
}
