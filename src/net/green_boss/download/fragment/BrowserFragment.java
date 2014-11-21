package net.green_boss.download.fragment;

import net.green_boss.download.MainActivity;
import net.green_boss.download.R;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class BrowserFragment extends Fragment {
	private final String TAG = BrowserFragment.class.getSimpleName();
	private WebView mWebView;
	private Button mGoButton;
	private EditText mAddress;
	private View mRootView;
	private ProgressBar mProgressBar;
	private LinearLayout mBrowserLayout;
	private GridView mGridView;
	private BaseAdapter mAdapter;

	/**
	 * get web view
	 * 
	 * @return web view
	 */
	public WebView getWebView() {
		initViews();
		return mWebView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set has option menu
		setHasOptionsMenu(true);
		setRetainInstance(true);
	}

	/**
	 * convert picture to bitmap
	 * 
	 * @param pictureDrawable
	 * @return bitmap TODO:TBA
	 */
	private static Bitmap pictureDrawable2Bitmap(PictureDrawable pictureDrawable) {
		Bitmap bitmap = Bitmap.createBitmap(
		/* pictureDrawable.getIntrinsicWidth() */100,
		/* pictureDrawable.getIntrinsicHeight() */300, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawPicture(pictureDrawable.getPicture());
		return bitmap;
	}

	/**
	 * init views
	 */
	private void initViews() {
		if (mRootView == null) {
			LayoutInflater inflater = (LayoutInflater) MainActivity.instance
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// root view
			mRootView = inflater
					.inflate(R.layout.fragment_browser, null, false);

			// progress bar
			mProgressBar = (ProgressBar) mRootView
					.findViewById(R.id.progress_bar);
			// hide progress bar
			mProgressBar.setVisibility(View.GONE);

			// web view
			mWebView = (WebView) mRootView.findViewById(R.id.web_view);
			// set web view client
			mWebView.setWebViewClient(getWebViewClient());
			// set web view chrome client
			mWebView.setWebChromeClient(getWebChromeClient());
			// set settings
			setWebViewSettings();
			// load url
			mWebView.loadUrl("http://www.nikon-image.com/support/manual/m_pdf_select.htm");
			// mWebView.loadUrl("http://10.0.2.2/chen/files.html");
			// mWebView.loadUrl(mUrl);

			// address text
			mAddress = (EditText) mRootView.findViewById(R.id.address);
			// TODO:TBA

			// GO button(TODO:to be delete,replace by ime done button)
			mGoButton = (Button) mRootView.findViewById(R.id.go);
			mGoButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					mWebView.loadUrl(mAddress.getText().toString());
				}
			});

			// browser layout
			mBrowserLayout = (LinearLayout) mRootView
					.findViewById(R.id.browser_layout);

			// grid view(browser tab select)
			mGridView = (GridView) mRootView.findViewById(R.id.grid_layout);
			// grid view adapter
			initAdapter();
			// set adapter
			mGridView.setAdapter(mAdapter);
			// hide grid view
			mGridView.setVisibility(View.GONE);
		}
	}

	/**
	 * get new web view client
	 * 
	 * @return
	 */
	private WebViewClient getWebViewClient() {
		return new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				mAddress.setText(url);
				if (url.endsWith(".pdf")) {
					// mActivity.mDownloadsFragment.add(url);
					// mActivity.mDownloadsFragment.mListView.getAdapter().
					return true;
				}
				return super.shouldOverrideUrlLoading(view, url);
			}

		};
	}

	private WebChromeClient getWebChromeClient() {
		return new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					mProgressBar.setVisibility(View.GONE);
				} else {
					mProgressBar.setVisibility(View.VISIBLE);
					mProgressBar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog,
					boolean isUserGesture, Message resultMsg) {
				// WebView childView = new WebView(getActivity());
				WebView childView = MainActivity.instance.getFragmentFactory()
						.getBrowserFragment().getWebView();
				// print browser fragment size
				Log.d(TAG, "browser fragment size:"
						+ MainActivity.instance.getFragmentFactory()
								.getBrowserFragmentList().size());
				// final WebSettings settings = childView.getSettings();
				// settings.setJavaScriptEnabled(true);
				// childView.setWebChromeClient(this);
				WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
				transport.setWebView(childView);
				resultMsg.sendToTarget();
				return true;
			}
		};
	}

	private void setWebViewSettings() {
		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportMultipleWindows(true);
	}

	private void initAdapter() {
		mAdapter = new BaseAdapter() {
			@Override
			public View getView(int position, View arg1, ViewGroup arg2) {
				BrowserFragment bf = MainActivity.instance.getFragmentFactory()
						.getBrowserFragmentList().get(position);
				WebView wv = bf.getWebView();
				Picture p = wv.capturePicture();
				Bitmap bmp = null;
				try {
					bmp = pictureDrawable2Bitmap(new PictureDrawable(p));
					// FileOutputStream out = new FileOutputStream(filename);
					// bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
					// out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				ImageView iv = new ImageView(MainActivity.instance);
				if (bmp != null)
					iv.setImageBitmap(bmp);
				return iv;
			}

			@Override
			public long getItemId(int arg0) {
				return 0;
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				return MainActivity.instance.getFragmentFactory()
						.getBrowserFragmentList().size();
			}
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		initViews();
		return mRootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		if (menu.size() <= 0) {
			inflater.inflate(R.menu.fragment_browser, menu);
			super.onCreateOptionsMenu(menu, inflater);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			mWebView.reload();
			break;
		case R.id.action_back:
			if (mWebView.canGoBack()) {
				mWebView.goBack();
			}
			break;
		case R.id.action_forward:
			if (mWebView.canGoForward()) {
				mWebView.goForward();
			}
			break;
		case R.id.action_settings:
			listTabs();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void listTabs() {
		// hide the browser view
		// mFragmentFactory.
		// show select tab view
		if (mBrowserLayout.getVisibility() == View.VISIBLE) {
			mBrowserLayout.setVisibility(View.GONE);
			mGridView.setVisibility(View.VISIBLE);
			mAdapter.notifyDataSetChanged();
		} else {
			mBrowserLayout.setVisibility(View.VISIBLE);
			mGridView.setVisibility(View.GONE);
		}
	}
}
