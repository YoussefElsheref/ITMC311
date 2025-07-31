package com.example.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button
        Button openWebButton = findViewById(R.id.open_web_button);

        // Set click listener
        openWebButton.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Opening Google...", Toast.LENGTH_SHORT).show();

            // Open Google in browser
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
            startActivity(intent);
        });
    }
}
