package com.KiteXu.AndroidTest.Adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.KiteXu.AndroidTest.R;

public class MiniSiteAdapter extends BaseAdapter{

	private Context context;
	private List<Map<String, Object>> data;
	private static int index = 0;
	
	public MiniSiteAdapter(Context context, List<Map<String, Object>> data)
	{
		super();
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public void setIndex(int selected)
	{
		this.index = selected;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		if(convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.minisite_list_item, null);
			holder = new ViewHolder();
			
			holder.articleTitle = (TextView)convertView.findViewById(R.id.articleTitle);
			holder.articleImg = (ImageView)convertView.findViewById(R.id.articleImg);
			holder.abstractText = (TextView)convertView.findViewById(R.id.abstractText);
			holder.publishTime = (TextView)convertView.findViewById(R.id.publishTime);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		convertView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				// TODO Auto-generated method stub
				
				if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
				{
					MiniSiteAdapter.this.setIndex(position);
					MiniSiteAdapter.this.notifyDataSetChanged();
				}
				
				return false;
			}
		});
		
		if(index == position)
		{
			convertView.setBackgroundColor(Color.LTGRAY);
			convertView.findViewById(R.id.abstractText).setSelected(true);
		}
		else
		{
			convertView.setBackgroundColor(0xfff3f3f3);
			convertView.findViewById(R.id.abstractText).setSelected(false);
		}
		
		convertView.setTag(holder);
		holder.articleTitle.setText((String)data.get(position).get("articleTitle"));
		holder.abstractText.setText((String)data.get(position).get("abstractText"));
		holder.publishTime.setText((String)data.get(position).get("publishTime"));		
		holder.articleImg.setImageBitmap((Bitmap)data.get(position).get("articleImg")); 
		
		return convertView;
	}

	
	public class ViewHolder
	{
		TextView articleTitle;
		ImageView articleImg;
		TextView abstractText;
		TextView publishTime;
	}
	
}
