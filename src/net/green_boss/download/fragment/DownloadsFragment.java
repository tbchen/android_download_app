package net.green_boss.download.fragment;

import java.util.ArrayList;
import java.util.List;

import net.green_boss.download.R;
import net.green_boss.download.view.DownloadRow;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link DownloadsFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link DownloadsFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class DownloadsFragment extends Fragment {
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	private String mParam1;
	private String mParam2;
	public ListView mListView;
	public final List<String> mUrlList = new ArrayList<String>();
	private View mRootView;
	private Button mButton;
	private DownloadListAdapter mDownloadListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		mDownloadListAdapter = new DownloadListAdapter();
		setHasOptionsMenu(true);
		setRetainInstance(true);
	}

	public void add(String url) {
		mDownloadListAdapter.add(url);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		if (mRootView == null) {
			mRootView = inflater.inflate(R.layout.fragment_downloads,
					container, false);
			mButton = (Button) mRootView.findViewById(R.id.button1);
			mButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					add("http://10.0.2.2/chen/test.pdf");
				}
			});
			mListView = (ListView) mRootView.findViewById(R.id.list_view);
			mListView.setAdapter(mDownloadListAdapter);
		}
		return mRootView;
	}

	public class DownloadListAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			DownloadRow row = (DownloadRow) convertView;
			if (row == null) {
				row = new DownloadRow(getActivity(), null);
			}
			row.startDownload(mUrlList.get(position));
			return row;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public int getCount() {
			return mUrlList.size();
		}

		public void add(String url) {
			mUrlList.add(url);
			notifyDataSetChanged();
		}
	};
}
