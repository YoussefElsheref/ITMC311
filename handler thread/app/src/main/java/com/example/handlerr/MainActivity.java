package com.example.handlerr;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {
    ProgressBar progressBar1, progressBar2;
    TextView txtStatus1, txtStatus2;
    Button btnRunnable, btnMessage;
    Handler handler;
    boolean isRunning = false;
    int maxProgress = 20;
    int currentProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        txtStatus1 = findViewById(R.id.txtStatus1);
        txtStatus2 = findViewById(R.id.txtStatus2);
        btnRunnable = findViewById(R.id.btnRunnable);
        btnMessage = findViewById(R.id.btnMessage);

        progressBar1.setMax(maxProgress);
        progressBar2.setMax(maxProgress);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String data = (String) msg.obj;
                txtStatus2.setText("Handler (Message):\n" + data);
                progressBar2.incrementProgressBy(1);
                if (progressBar2.getProgress() == maxProgress) {
                    txtStatus2.append("\n\nCompleted via Message!");
                }
            }
        };

        btnRunnable.setOnClickListener(v -> startRunnableThread());
        btnMessage.setOnClickListener(v -> startMessageThread());
    }

    private void startRunnableThread() {
        currentProgress = 0;
        progressBar1.setProgress(0);
        txtStatus1.setText("Starting Runnable Thread...");

        Thread thread = new Thread(() -> {
            while (currentProgress < maxProgress) {
                try {
                    Thread.sleep(1000);
                    currentProgress++;
                    handler.post(() -> {
                        txtStatus1.setText("Handler (Runnable): " + currentProgress);
                        progressBar1.incrementProgressBy(1);
                        if (progressBar1.getProgress() == maxProgress) {
                            txtStatus1.append("\n\nCompleted via Runnable!");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void startMessageThread() {
        progressBar2.setProgress(0);
        txtStatus2.setText("Starting Message Thread...");
        isRunning = true;

        Thread thread = new Thread(() -> {
            for (int i = 0; i < maxProgress && isRunning; i++) {
                try {
                    Thread.sleep(1000);
                    Random random = new Random();
                    String data = "Random Value: " + random.nextInt(100);
                    Message msg = handler.obtainMessage(1, data);
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isRunning = false;
        });
        thread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }
}
