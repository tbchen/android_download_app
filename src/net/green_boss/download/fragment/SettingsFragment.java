package net.green_boss.download.fragment;

import net.green_boss.download.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {
	public static final String HOME_PAGE_KEY = "settings_item_home_page";
	public static final String PASSCODE_LOCK = "settings_item_passcode_lock";
	private EditTextPreference mHomePagePref;
	private SwitchPreference mPasscodeLockPref;
	private SharedPreferences mSharedPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.settings);
		mHomePagePref = (EditTextPreference) findPreference(HOME_PAGE_KEY);
		mSharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		mHomePagePref.setSummary(mSharedPreferences
				.getString(HOME_PAGE_KEY, ""));

		mPasscodeLockPref = (SwitchPreference) findPreference(PASSCODE_LOCK);

	}

	@Override
	public void onStart() {
		super.onStart();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(HOME_PAGE_KEY)) {
			mHomePagePref.setSummary(sharedPreferences.getString(key, ""));
		} else if (key.equals(PASSCODE_LOCK)) {
			mPasscodeLockPref.isChecked();

		}
	}
}
