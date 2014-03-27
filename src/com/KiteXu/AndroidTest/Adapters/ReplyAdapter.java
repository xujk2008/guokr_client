package com.KiteXu.AndroidTest.Adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.KiteXu.AndroidTest.R;

public class ReplyAdapter extends BaseAdapter{

	private Context context;
	private List<Map<String, Object>> data;
	private static int index = 0;
	
	public ReplyAdapter(Context context, List<Map<String, Object>> data)
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
			convertView = LayoutInflater.from(context).inflate(R.layout.reply_list_item, null);
			holder = new ViewHolder();
			
			holder.replyContent = (WebView)convertView.findViewById(R.id.replyContent);
			holder.authorImg = (ImageView)convertView.findViewById(R.id.authorImg);
			holder.authorName = (TextView)convertView.findViewById(R.id.authorName);
			holder.replyTime = (TextView)convertView.findViewById(R.id.replyTime);
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
					ReplyAdapter.this.setIndex(position);
					ReplyAdapter.this.notifyDataSetChanged();
				}
				
				return false;
			}
		});
		
		if(index == position)
		{
			convertView.setBackgroundColor(Color.LTGRAY);
		}
		else
		{
			convertView.setBackgroundColor(0xfff3f3f3);
		}
		
		convertView.setTag(holder);
		String content = (String)(data.get(position)).get("replyContent");
//		content = "<p>" + content + "</p>";
		(holder.replyContent).loadData(content, "text/html; charset=UTF-8", null);
		(holder.replyContent).setBackgroundColor(0x00000000);
		(holder.replyContent).setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		
		holder.authorName.setText((String)data.get(position).get("authorName"));
		holder.replyTime.setText((String)data.get(position).get("replyTime"));		
		holder.authorImg.setImageBitmap((Bitmap)data.get(position).get("authorImg")); 
		
		return convertView;
	}

	
	public class ViewHolder
	{
		ImageView authorImg;
		TextView authorName;
		TextView replyTime;
		WebView replyContent;
	}
	
}
