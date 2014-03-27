package com.KiteXu.AndroidTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment3 extends Fragment{

	private View view = null;

	public static Fragment3 newInstance() {
		Fragment3 fragment = new Fragment3();
		return fragment;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		TextView fragment3Text = (TextView)(view.findViewById(R.id.fragment3Text));
		fragment3Text.setText("Fragment3");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.fragment3, null);
		
		return view;
	}
	
}
