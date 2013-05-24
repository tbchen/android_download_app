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
    //tab tag name
	public static final String TAB_TAG_BROWSER = "tabTagBrowser";
	public static final String TAB_TAG_DOWNLOADS = "tabTagDownloads";
	public static final String TAB_TAG_FILES = "tabTagFiles";
	public static final String TAB_TAG_SETTINGS = "tabTagSettings";
	private ActionBar mActionBar;
    public static MainActivity instance;

    private final static String[] TAGS = {TAB_TAG_BROWSER,TAB_TAG_DOWNLOADS,TAB_TAG_FILES,TAB_TAG_SETTINGS};
    private final static int[] ICONS = {
            R.drawable.ic_menu_mapmode,
            R.drawable.ic_menu_attachment,
            R.drawable.ic_menu_archive,
            R.drawable.ic_menu_preferences};
	private final FragmentFactory mFragmentFactory = new FragmentFactory(
			getFragmentManager());

    public FragmentFactory getFragmentFactory(){
        return mFragmentFactory;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        instance = this;
		setContentView(R.layout.activity_main);
		mActionBar = getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// don't show title
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);

        // add tabs
        for(int i=0;i<TAGS.length;i++){
            addTab(TAGS[i],ICONS[i]);
        }
	}

    /**
     * add tab to action bar
     */
    private void addTab(String tag, int icon){
        Tab tab = mActionBar.newTab();
        tab.setIcon(icon);
        tab.setTabListener(this);
        tab.setTag(tag);
        mActionBar.addTab(tab);
    }


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
