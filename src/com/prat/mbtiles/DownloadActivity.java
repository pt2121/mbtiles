package com.prat.mbtiles;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class DownloadActivity extends Activity {
	private long enqueue;
    private DownloadManager dm;
    
    private static final String DEFAULT_URL = "http://a.tiles.mapbox.com/v3/nutiteq.geography-class.mbtiles";
    
    private static EditText urlET;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		
		urlET = (EditText)findViewById(R.id.url_et);
		urlET.setText(DEFAULT_URL);
		
		BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                	Intent i = new Intent(DownloadActivity.this, MapActivity.class);
                    startActivity(i);
                }
            }
        };
 
        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.download, menu);
		return true;
	}
	
	public void onClick(View view) {
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Request request = new Request(
                Uri.parse(urlET.getText().toString()));
        Log.v("MapActivity", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                "test.mbtiles");
        enqueue = dm.enqueue(request);
 
    }
 
    public void showDownload(View view) {
        Intent i = new Intent();
        i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(i);
    }

}
