package com.KiteXu.AndroidTest;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return titles[position];
	}

	private ArrayList<Fragment> fragments;
	private String[] titles;
	
	public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String titles[]) {
		super(fm);
		// TODO Auto-generated constructor stub
		
		this.fragments = fragments;
		this.titles = titles;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

}
