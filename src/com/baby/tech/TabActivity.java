package com.baby.tech;

import java.util.ArrayList;

import net.youmi.android.spot.SpotManager;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ActionMode;
import android.view.KeyEvent;

import com.baby.tech.fragment.TabOne;
import com.baby.tech.fragment.TabTwo;
import com.baby.tech.fragment.TabThree;

@SuppressLint("NewApi")
public class TabActivity extends Activity {
	private static final String INSTANCESTATE_TAB = "tab";
	private static final int DEFAULT_OFFSCREEN_PAGES = 2;
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;
	ActionMode mActionMode;
	private Context mContext ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	 mContext = this ;
		//===============================
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(DEFAULT_OFFSCREEN_PAGES);// Ԥ����ص�ҳ������

		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE
				| ActionBar.DISPLAY_SHOW_HOME);

		mTabsAdapter = new TabsAdapter(TabActivity.this, mViewPager);
		mTabsAdapter.addTab(bar.newTab().setText(R.string.title_one), TabOne.class,
				null);
		mTabsAdapter.addTab(bar.newTab().setText(R.string.title_two), TabTwo.class,
				null);
		mTabsAdapter.addTab(bar.newTab().setText(R.string.title_three), TabThree.class,
				null);
		bar.setSelectedNavigationItem(PreferenceManager
				.getDefaultSharedPreferences(this).getInt(INSTANCESTATE_TAB, 0));
	}

	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(this).edit();
		editor.putInt(INSTANCESTATE_TAB, getActionBar()
				.getSelectedNavigationIndex());
		editor.commit();
	}

	public Context getmContext() {
	    return mContext;
	   }
	public void setActionMode(ActionMode actionMode) {
        mActionMode = actionMode;
    }

    public ActionMode getActionMode() {
        return mActionMode;
    }

    public Fragment getFragment(int tabIndex) {
        return mTabsAdapter.getItem(tabIndex);
    }

    @Override
    public void onBackPressed() {
     // 如果有需要，可以点击后退关闭插播广告。
     if (!SpotManager.getInstance(TabActivity.this).disMiss(true)) {
      super.onBackPressed();
     }
    }  
    
	public static class TabsAdapter extends FragmentPagerAdapter implements
			ActionBar.TabListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final Class<?> clss;
			private final Bundle args;
			private Fragment fragment;

			TabInfo(Class<?> _class, Bundle _args) {
				clss = _class;
				args = _args;
			}
		}

		public TabsAdapter(Activity activity, ViewPager pager) {
			super(activity.getFragmentManager());
			mContext = activity;
			mActionBar = activity.getActionBar();
			mViewPager = pager;
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
			TabInfo info = new TabInfo(clss, args);
			tab.setTag(info);
			tab.setTabListener(this);
			mTabs.add(info);
			mActionBar.addTab(tab);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			if (info.fragment == null) {
				info.fragment = Fragment.instantiate(mContext,
						info.clss.getName(), info.args);
			}
			return info.fragment;
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			mActionBar.setSelectedNavigationItem(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Object tag = tab.getTag();
			for (int i = 0; i < mTabs.size(); i++) {
				if (mTabs.get(i) == tag) {
					mViewPager.setCurrentItem(i);
				}
			}
			if (!tab.getText().equals(mContext.getString(R.string.title_three))) {
				ActionMode actionMode = ((TabActivity) mContext).getActionMode();
				if (actionMode != null) {
					actionMode.finish();
				}
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
		
		
  public boolean dispatchKeyEvent(KeyEvent event) {
      if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
        && event.getAction() == KeyEvent.ACTION_DOWN) {
       android.content.DialogInterface.OnClickListener positiveBtnListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
         if (which == -1) {
          // startActivity(new Intent(CarTabActivity.this,
          // BaseRegisterActivity.class));
         // finish();
         }
        }
       };
       DialogTool.createConfirmDialog(mContext, "提示", "确定退出程序？", "退出", "再玩玩",
         positiveBtnListener, positiveBtnListener, R.drawable.icon)
         .show();
       return true;
      }
      return false;
     }
	}

}
