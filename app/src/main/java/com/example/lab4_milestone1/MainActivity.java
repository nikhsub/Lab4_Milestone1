package com.example.lab4_milestone1;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button start;
    private Button stop;
    private TextView progress;
    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        progress = findViewById(R.id.progressText);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownload(view);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopDownload(view);
            }
        });
    }


    public void mockFileDownloader() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                start.setText("Downloading...");
            }
        });


        for (int downloadProgress = 0; downloadProgress <= 100; downloadProgress = downloadProgress + 10) {
            if (stopThread) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        start.setText("Start");
                        progress.setText("");
                    }
                });
                return;
            }

//            Log.d(TAG, "Download Progress: " + downloadProgress + "%");\
            int finalDownloadProgress = downloadProgress;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setText("Download Progress: " + finalDownloadProgress + "%");
                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                start.setText("Start");
                progress.setText("");
            }
        });
    }

    class ExampleRunnable implements Runnable {
        @Override
        public void run() {
            mockFileDownloader();
        }
    }

    public void startDownload(View view) {
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view) {
        stopThread = true;
    }
}

