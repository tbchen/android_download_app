package net.green_boss.download.view;

import net.green_boss.download.R;
import net.green_boss.download.util.DownloadFileTask;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class DownloadRow extends LinearLayout {
	private final ProgressBar mDownloadProgressBar;
	private final ImageButton mDeleteButton;
	private DownloadFileTask mDownloadFileTask;
	private String mUrl;

	public DownloadRow(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_download_row, this);
		mDownloadProgressBar = (ProgressBar) findViewById(R.id.download_progress_bar);
		mDownloadProgressBar.setProgress(0);
		mDeleteButton = (ImageButton) findViewById(R.id.delete_button);
		mDeleteButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancelDownload();
			}
		});
	}

	public void startDownload(String url) {
		if (mDownloadFileTask != null) {
			Log.d("mDownloadFileTask:status", mDownloadFileTask.getStatus()
					.toString());
		}
		if (mDownloadFileTask == null
				|| !mDownloadFileTask.getStatus().equals(
						AsyncTask.Status.RUNNING)) {
			mUrl = url;
			mDownloadFileTask = new DownloadFileTask(mDownloadProgressBar);
			mDownloadFileTask.execute(mUrl);

		}
	}

	public void cancelDownload() {
		if (mDownloadFileTask != null
				&& mDownloadFileTask.getStatus().equals(
						AsyncTask.Status.RUNNING)) {
			mDownloadFileTask.cancel(true);
		}
	}

	// public void setProgress(int progress) {
	// mDownloadProgressBar.setProgress(progress);
	// }

	// public void setDeleteButtonListener()
}