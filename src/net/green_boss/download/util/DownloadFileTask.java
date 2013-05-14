package net.green_boss.download.util;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import android.os.AsyncTask;
import android.widget.ProgressBar;

public class DownloadFileTask extends AsyncTask<String, Integer, String> {
	private final ProgressBar mProgressBar;

	public DownloadFileTask(ProgressBar progressBar) {
		mProgressBar = progressBar;
	}

	@Override
	protected String doInBackground(String... sUrl) {
		try {
			URL url = new URL(sUrl[0]);
			URLConnection connection = url.openConnection();
			connection.connect();
			// this will be useful so that you can show a typical 0-100%
			// progress bar
			int fileLength = connection.getContentLength();

			// download the file
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream("/mnt/sdcard/a"
					+ new Random().nextInt());

			byte data[] = new byte[1024];
			long total = 0;
			int count;
			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				publishProgress((int) (total * 100 / fileLength));
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		super.onProgressUpdate(progress);
		// mProgressDialog.setProgress(progress[0]);
		mProgressBar.setProgress(progress[0]);
	}
}