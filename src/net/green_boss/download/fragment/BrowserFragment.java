package net.green_boss.download.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import android.widget.*;
import net.green_boss.download.MainActivity;
import net.green_boss.download.R;
import android.app.Fragment;
import android.os.Bundle;
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

public class BrowserFragment extends Fragment {
	//public static final String URL = "url";
	//private LinearLayout mBrowserLayout;
	//private GridView mWebViewListGridView;
	//private String mUrl;
	private WebView mWebView;
	private Button mGoButton;
	private EditText mAddress;
	private View mRootView;
	private ProgressBar mProgressBar;
	private MainActivity mActivity;
    private LinearLayout mContent;
    private GridView mGridView;
    private BaseAdapter mAdapter;

	public WebView getWebView() {
		return mWebView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = (MainActivity) getActivity();
	//	if (getArguments() != null) {
			//mUrl = getArguments().getString(URL);
	//	}
		// set has option menu
		setHasOptionsMenu(true);
		setRetainInstance(true);
	}

    private static Bitmap pictureDrawable2Bitmap(PictureDrawable pictureDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(
		/* pictureDrawable.getIntrinsicWidth() */100,
		/* pictureDrawable.getIntrinsicHeight() */300, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawPicture(pictureDrawable.getPicture());
        return bitmap;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		if (mRootView == null) {
			mRootView = inflater.inflate(R.layout.fragment_browser, container,
					false);
			mProgressBar = (ProgressBar) mRootView
					.findViewById(R.id.progress_bar);
			mProgressBar.setVisibility(View.GONE);
			mWebView = (WebView) mRootView.findViewById(R.id.web_view);
			mWebView.setWebViewClient(new WebViewClient() {

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

			});

			mWebView.setWebChromeClient(new WebChromeClient() {

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

			});
			WebSettings settings = mWebView.getSettings();
			settings.setJavaScriptEnabled(true);
			mWebView.loadUrl("http://www.nikon-image.com/support/manual/m_pdf_select.htm");

			// mWebView.loadUrl(mUrl);
			mAddress = (EditText) mRootView.findViewById(R.id.address);

			mGoButton = (Button) mRootView.findViewById(R.id.go);
			mGoButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					mWebView.loadUrl(mAddress.getText().toString());
				}
			});

			//mBrowserLayout = (LinearLayout) mRootView
			//		.findViewById(R.id.browser_layout);

            mContent = (LinearLayout) mRootView.findViewById(R.id.browser_layout);
            mGridView = (GridView) mRootView.findViewById(R.id.grid_layout);
            mAdapter = new BaseAdapter() {

                @Override
                public View getView(int position, View arg1, ViewGroup arg2) {
                    BrowserFragment bf = mActivity.getFragmentFactory().getBrowserFragmentList()
                            .get(position);
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
                    ImageView iv = new ImageView(mActivity);
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
                    return mActivity.getFragmentFactory().getBrowserFragmentList().size();
                }

                // public void change() {
                // notifyDataSetChanged();
                // }
            };
            mGridView.setAdapter(mAdapter);
			//mWebView.capturePicture();
			// mBrowserLayout.setVisibility(View.GONE);

			// mWebViewListGridView = (GridView) mRootView
			// .findViewById(R.id.grid_layout);
			//
			// mWebViewListGridView.setAdapter(new BaseAdapter() {
			//
			// @Override
			// public View getView(int arg0, View arg1, ViewGroup arg2) {
			// // TODO Auto-generated method stub
			// WebView v = new WebView(getActivity());// mWebView;
			// v.loadUrl("http://m.yahoo.co.jp");
			// // TextView v = new TextView(getActivity());
			// // v.setText("test");
			// return v;
			// }
			//
			// @Override
			// public long getItemId(int arg0) {
			// // TODO Auto-generated method stub
			// return 0;
			// }
			//
			// @Override
			// public Object getItem(int arg0) {
			// // TODO Auto-generated method stub
			// return null;
			// }
			//
			// @Override
			// public int getCount() {
			// // TODO Auto-generated method stub
			// return 4;
			// }
			// });
		}
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

    public void listTabs() {
        // hide the browser view
        // mFragmentFactory.
        // show select tab view
        if (mContent.getVisibility() == View.VISIBLE) {
            mContent.setVisibility(View.GONE);
            mGridView.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        } else {
            mContent.setVisibility(View.VISIBLE);
            mGridView.setVisibility(View.GONE);
        }
    }
}
