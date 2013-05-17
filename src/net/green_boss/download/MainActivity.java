/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.green_boss.download;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

/**
 * This demonstrates the use of action bar tabs and how they interact with other
 * action bar features.
 */
public class MainActivity extends Activity implements
		android.app.ActionBar.TabListener {
	public static final String TAB_TAG_BROWSER = "tabTagBrowser";
	public static final String TAB_TAG_DOWNLOADS = "tabTagDownloads";
	public static final String TAB_TAG_FILES = "tabTagFiles";
	public static final String TAB_TAG_SETTINGS = "tabTagSettings";
	private ActionBar mActionBar;

	// public BrowserFragment mBrowserFragment;
	// public DownloadsFragment mDownloadsFragment;
	private final FragmentFactory mFragmentFactory = new FragmentFactory(
			getFragmentManager());

	// private void addTab(String tag,) {
	// bffb4
	// }
    public FragmentFactory getFragmentFactory(){
        return mFragmentFactory;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mActionBar = getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// don't show title
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);

		// BrownserFragment
		// TabListener<BrowserFragment> browserTabListener = new
		// TabListener<BrowserFragment>(
		// this, getString(R.string.tab_browser), BrowserFragment.class);
		Tab browserTab = mActionBar.newTab();
		browserTab.setIcon(R.drawable.ic_menu_mapmode);
		browserTab.setTabListener(this);
		browserTab.setTag(TAB_TAG_BROWSER);
		mActionBar.addTab(browserTab);

		Tab downloadTab = mActionBar.newTab();
		downloadTab.setIcon(R.drawable.ic_menu_mapmode);
		downloadTab.setTabListener(this);
		downloadTab.setTag(TAB_TAG_DOWNLOADS);
		mActionBar.addTab(downloadTab);

		Tab filesTab = mActionBar.newTab();
		filesTab.setIcon(R.drawable.ic_menu_mapmode);
		filesTab.setTabListener(this);
		filesTab.setTag(TAB_TAG_FILES);
		mActionBar.addTab(filesTab);

		Tab settingsTab = mActionBar.newTab();
		settingsTab.setIcon(R.drawable.ic_menu_mapmode);
		settingsTab.setTabListener(this);
		settingsTab.setTag(TAB_TAG_SETTINGS);
		mActionBar.addTab(settingsTab);


		// DownloadFragment
		// TabListener<DownloadsFragment> downloadsTabListener = new
		// TabListener<DownloadsFragment>(
		// this, getString(R.string.tab_downloads),
		// DownloadsFragment.class);
		// Tab downloadsTab = mActionBar.newTab();
		// // downloadsTab.setText(getString(R.string.tab_downloads));
		// downloadsTab.setIcon(R.drawable.ic_menu_attachment);
		// downloadsTab.setTabListener(downloadsTabListener);
		// mActionBar.addTab(downloadsTab);
		// // mDownloadsFragment = (DownloadsFragment)
		// // downloadsTabListener.mFragment;
		//
		// // FilesFragment
		// mActionBar.addTab(mActionBar
		// .newTab()
		// // .setText(getString(R.string.tab_files))
		// .setIcon(R.drawable.ic_menu_archive)
		// .setTabListener(
		// new TabListener<FilesFragment>(this,
		// getString(R.string.tab_files),
		// FilesFragment.class)));
		//
		// // SettingsFragment
		// mActionBar.addTab(mActionBar
		// .newTab()
		// // .setText(getString(R.string.tab_files))
		// .setIcon(R.drawable.ic_menu_preferences)
		// .setTabListener(
		// new TabListener<SettingsFragment>(this,
		// getString(R.string.tab_files),
		// SettingsFragment.class)));
		// mActionBar.addTab(mActionBar
		// .newTab()
		// .setText(getString(R.string.tab_settings))
		// .setTabListener(
		// new TabListener<SettingsFragment>(this,
		// getString(R.string.tab_settings),
		// SettingsFragment.class)));
		// if (savedInstanceState != null) {
		// bar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
		// }
	}

	// @Override
	// protected void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	// }

	// public static class TabListener<T extends Fragment> implements
	// ActionBar.TabListener {
	// private final Activity mActivity;
	// private final String mTag;
	// private final Class<T> mClass;
	// private final Bundle mArgs;
	// public Fragment mFragment;
	//
	// public TabListener(Activity activity, String tag, Class<T> clz) {
	// this(activity, tag, clz, null);
	// }
	//
	// public TabListener(Activity activity, String tag, Class<T> clz,
	// Bundle args) {
	// mActivity = activity;
	// mTag = tag;
	// mClass = clz;
	// mArgs = args;
	//
	// // Check to see if we already have a fragment for this tab, probably
	// // from a previously saved state. If so, deactivate it, because our
	// // initial state is that a tab isn't shown.
	// mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
	// if (mFragment != null && !mFragment.isDetached()) {
	// FragmentTransaction ft = mActivity.getFragmentManager()
	// .beginTransaction();
	// ft.detach(mFragment);
	// ft.commit();
	// } else {
	// FragmentTransaction ft = mActivity.getFragmentManager()
	// .beginTransaction();
	// mFragment = Fragment.instantiate(mActivity, mClass.getName(),
	// mArgs);
	// ft.add(android.R.id.content, mFragment, mTag);
	// ft.detach(mFragment);
	// ft.commit();
	// }
	// }
	//
	// @Override
	// public void onTabSelected(Tab tab, FragmentTransaction ft) {
	// if (mFragment == null) {
	// mFragment = Fragment.instantiate(mActivity, mClass.getName(),
	// mArgs);
	// ft.add(android.R.id.content, mFragment, mTag);
	// } else {
	// ft.attach(mFragment);
	// }
	// }
	//
	// @Override
	// public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	// if (mFragment != null) {
	// ft.detach(mFragment);
	// }
	// }
	//
	// @Override
	// public void onTabReselected(Tab tab, FragmentTransaction ft) {
	// Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
	// }
	// }

	// --------tab listener--------
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Toast.makeText(this, TAB_TAG_BROWSER, Toast.LENGTH_SHORT).show();
		if (TAB_TAG_BROWSER.equals(tab.getTag())) {
			mFragmentFactory.getBrowserFragment();
		} else if (TAB_TAG_DOWNLOADS.equals(tab.getTag())) {
			mFragmentFactory.getDownloadFragment();
		} else if (TAB_TAG_FILES.equals(tab.getTag())) {
			mFragmentFactory.getFilesFragment();
		} else if (TAB_TAG_SETTINGS.equals(tab.getTag())) {
			mFragmentFactory.getSettingsFragment();
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}


}
