package net.green_boss.download;

import java.util.ArrayList;
import java.util.List;

import net.green_boss.download.fragment.BrowserFragment;
import net.green_boss.download.fragment.DownloadsFragment;
import net.green_boss.download.fragment.FilesFragment;
import net.green_boss.download.fragment.SettingsFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class FragmentFactory {
	private final List<BrowserFragment> browserFragmentList = new ArrayList<BrowserFragment>();
	private DownloadsFragment df;
	private SettingsFragment sf;
	private FilesFragment ff;
	public static final int MAX_TABS = 4;
	private int currentBrowserFragmentIndex = 0;
	private final FragmentManager fm;

	public enum FragmentType {
		BROWSER, DOWNLOADS, FILES, SETTINGS, UNKNOWN
	};

	private FragmentType currentFragmentType = FragmentType.UNKNOWN;

	public FragmentFactory(FragmentManager fm) {
		this.fm = fm;
	}

	public DownloadsFragment getDownloadFragment() {
		Fragment currentFragment = getCurrentFragment();
		FragmentTransaction ft = fm.beginTransaction();
		if (currentFragment != null) {
			ft.detach(currentFragment);
		}
		if (df == null) {
			df = new DownloadsFragment();
			ft.add(R.id.content, df, null);
		} else {
			ft.attach(df);
		}
		ft.commit();
		currentFragmentType = FragmentType.DOWNLOADS;
		return df;
	}

	public SettingsFragment getSettingsFragment() {
		Fragment currentFragment = getCurrentFragment();
		FragmentTransaction ft = fm.beginTransaction();
		if (currentFragment != null) {
			ft.detach(currentFragment);
		}
		if (sf == null) {
			sf = new SettingsFragment();
			ft.add(R.id.content, sf, null);
		} else {
			ft.attach(sf);
		}
		ft.commit();
		currentFragmentType = FragmentType.SETTINGS;
		return sf;
	}

	public FilesFragment getFilesFragment() {
		Fragment currentFragment = getCurrentFragment();
		FragmentTransaction ft = fm.beginTransaction();
		if (currentFragment != null) {
			ft.detach(currentFragment);
		}
		if (ff == null) {
			ff = new FilesFragment();
			ft.add(R.id.content, ff, null);
		} else {
			ft.attach(ff);
		}
		ft.commit();
		currentFragmentType = FragmentType.FILES;
		return ff;
	}

	public List<BrowserFragment> getBrowserFragmentList() {
		return browserFragmentList;
	}

	public BrowserFragment getBrowserFragment() {
		Fragment currentFragment = getCurrentFragment();
		FragmentTransaction ft = fm.beginTransaction();
		if (currentFragment != null) {
			ft.detach(currentFragment);
		}
		BrowserFragment bf;
		if (browserFragmentList.size() <= 0) {
			bf = new BrowserFragment();
			browserFragmentList.add(bf);
			currentBrowserFragmentIndex = 0;
			ft.add(R.id.content, bf, null);
		} else {
			bf = browserFragmentList.get(currentBrowserFragmentIndex);
			ft.attach(bf);
		}
		ft.commit();
		currentFragmentType = FragmentType.BROWSER;
		return bf;
	}

	// private BrowserFragment getCurrentBrowserFragment() {
	// BrowserFragment bf;
	// if (browserFragmentList.size() < MAX_TABS) {
	// bf = new BrowserFragment();
	// browserFragmentList.add(bf);
	// ft.add(android.R.id.content, bf, null);
	// } else {
	// bf = getNextBrowserFragment();
	// ft.attach(bf);
	// }
	// }
	//
	// private BrowserFragment getNextBrowserFragment() {
	// if (currentBrowserFragmentIndex == MAX_TABS - 1) {
	// return browserFragmentList.get(0);
	// } else {
	// return browserFragmentList.get(currentBrowserFragmentIndex + 1);
	// }
	// }

	private Fragment getCurrentFragment() {
		if (currentFragmentType == FragmentType.UNKNOWN) {
			return null;
		}

		if (currentFragmentType == FragmentType.DOWNLOADS) {
			return df;
		}

		if (currentFragmentType == FragmentType.FILES) {
			return ff;
		}

		if (currentFragmentType == FragmentType.SETTINGS) {
			return sf;
		}

		if (currentFragmentType == FragmentType.BROWSER) {
			return browserFragmentList.get(currentBrowserFragmentIndex);
		}

		return null;
	}
}
