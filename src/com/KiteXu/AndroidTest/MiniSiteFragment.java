package com.KiteXu.AndroidTest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.KiteXu.AndroidTest.Adapters.MiniSiteAdapter;
import com.KiteXu.AndroidTest.common.JsonData;
import com.KiteXu.AndroidTest.common.URLImg;
import com.KiteXu.AndroidTest.common.Utils;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

public class MiniSiteFragment extends SherlockFragment{

	private View mainView = null;
	private ListView miniSiteList;
	private static ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
	private static ArrayList<String> articleIdList = new ArrayList<String>();
	private LinearLayout miniSiteProgressBarLayout;
	private final int SUCCESS = 1, FAIL = 0;

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("@@@@@@", "Minisite destroied");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("@@@@@@", "Minisite Paused");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("@@@@@@", "Minisite resumed");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("@@@@@@", "Minisite stopped");
	}
	
	public static MiniSiteFragment newInstance() {
		MiniSiteFragment fragment = new MiniSiteFragment();
		return fragment;
	}
	
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			switch(msg.what)
			{
			case SUCCESS:
				
				miniSiteProgressBarLayout.setVisibility(View.GONE);
				miniSiteList.setVisibility(View.VISIBLE);
				
				final MiniSiteAdapter adapter = new MiniSiteAdapter(MiniSiteFragment.this.getActivity(), mData);
				miniSiteList.setAdapter(adapter);
				miniSiteList.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						Intent intent = new Intent(MiniSiteFragment.this.getActivity(), ArticleDetail.class);
						intent.putExtra("id", articleIdList.get(arg2));
						
						MiniSiteFragment.this.getActivity().startActivity(intent);
//						MiniSiteFragment.this.getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.fade_out);
					}

				});
				
				miniSiteList.setVisibility(View.VISIBLE);
				
				break;
				
			case FAIL:
				miniSiteProgressBarLayout.setVisibility(View.GONE);
				Toast.makeText(MiniSiteFragment.this.getActivity(), "请检查网络连接是否正确", Toast.LENGTH_LONG).show();
				
				break;
				
			}
			
		}
		
	};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		if(!(mData.isEmpty()))
		{
			
			handler.sendEmptyMessage(SUCCESS);
		}
		else
		{
			mData.clear();
			initList();
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		mainView = inflater.inflate(R.layout.fragment_mini_site, null);
		miniSiteProgressBarLayout = (LinearLayout)(mainView.findViewById(R.id.miniSiteProgressLayout));
		miniSiteList = (ListView)(mainView.findViewById(R.id.miniSiteList));
		this.setHasOptionsMenu(true);
		
		return mainView;
	}
	
//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		// TODO Auto-generated method stub
//		MenuItem refresh = menu.add("refresh");
//		refresh.setIcon(R.drawable.menu_refresh);
//		refresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//		refresh.setOnMenuItemClickListener(new OnMenuItemClickListener(){
//
//			@Override
//			public boolean onMenuItemClick(MenuItem item) {
//				// TODO Auto-generated method stub
//				
//				initList();
//				return true;
//			}
//			
//		});
//		
//		super.onCreateOptionsMenu(menu, inflater);
//	}
	
	private ArrayList<Map<String, Object>> getData()
	{
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		String path = "http://apis.guokr.com/minisite/article.json?retrieve_type=by_minisite&site_id=";
		
		JsonData jsonData = new JsonData(path);
		JSONObject jsonObject = null;
		
		try {
			String json = jsonData.getJson();
			jsonObject = new JSONObject(json);
			JSONArray result = jsonObject.getJSONArray("result");
			
			for(int i=0; i<result.length(); i++)
			{
				JSONObject curObject = result.getJSONObject(i);
				String articleTitle = curObject.getString("title");
				String abstractText = curObject.getString("summary");
				String publishTime = curObject.getString("date_published");
				publishTime = publishTime.substring(0, 10)+" "+publishTime.substring(11, 19);
				String articleImgURL = curObject.getString("small_image");
				String articleId = curObject.getString("id");
				
//				Log.v("@@@@@@", articleTitle);
//				Log.v("@@@@@@", abstractText);
//				Log.v("@@@@@@", publishTime);
//				Log.v("@@@@@@", articleImgURL);
				
				Map map = new HashMap<String, Object>();
				map.put("articleTitle", articleTitle);
				map.put("abstractText", abstractText);
				map.put("publishTime", publishTime);
				Bitmap articleImg = new URLImg(articleImgURL).getBitMap();
				map.put("articleImg", articleImg);
				
				data.add(map);
				articleIdList.add(articleId);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	private void initList()
	{
		miniSiteProgressBarLayout.setVisibility(View.VISIBLE);
		miniSiteList.setVisibility(View.GONE);
		
		new Thread()
		{
			public void run()
			{
				mData = getData();
				
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
}
