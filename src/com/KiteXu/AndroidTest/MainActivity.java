package com.KiteXu.AndroidTest;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.viewpagerindicator.TabPageIndicator;


public class MainActivity extends SherlockFragmentActivity {

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// TODO Auto-generated method stub
//		SubMenu sub = menu.addSubMenu("A");
//		sub.addSubMenu("推荐给好友");
//		sub.addSubMenu("关于");
//		sub.addSubMenu("登陆");
//		sub.addSubMenu("退出");
//
//		MenuItem item = sub.getItem();
//		item.setIcon(R.drawable.menu_apprec);
//		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//
//		return super.onCreateOptionsMenu(menu);
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initActionBar();
		initViews();
	}

	private void initActionBar() {
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayShowHomeEnabled(false);

		getSupportActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.actionbar_tab_bg));
		View mainActionBarView = LayoutInflater.from(this).inflate(
				R.layout.main_aciton_bar, null);
		getSupportActionBar().setCustomView(mainActionBarView);
	}

	private void initViews() {
		String[] titles = { "主题站", "小组", "问答" };
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new MiniSiteFragment());
		fragments.add(new Fragment2());
		fragments.add(new Fragment3());

		FragmentPagerAdapter adapter = new MyFragmentPagerAdapter(
				this.getSupportFragmentManager(), fragments, titles);
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
	}

}