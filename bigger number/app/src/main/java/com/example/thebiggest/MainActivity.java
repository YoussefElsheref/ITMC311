package com.example.thebiggest;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editTextNumber1, editTextNumber2, editTextNumber3;
    Button buttonCompare, buttonClear;
    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        editTextNumber3 = findViewById(R.id.editTextNumber3);
        buttonCompare = findViewById(R.id.buttonCompare);
        buttonClear = findViewById(R.id.buttonClear);
        textViewResult = findViewById(R.id.textViewResult);

        buttonCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1Str = editTextNumber1.getText().toString();
                String num2Str = editTextNumber2.getText().toString();
                String num3Str = editTextNumber3.getText().toString();

                if (num1Str.isEmpty() || num2Str.isEmpty() || num3Str.isEmpty()) {
                    textViewResult.setText("Please enter all three numbers!");
                    return;
                }

                int num1 = Integer.parseInt(num1Str);
                int num2 = Integer.parseInt(num2Str);
                int num3 = Integer.parseInt(num3Str);

                int max = Math.max(num1, Math.max(num2, num3));

                if (num1 == num2 && num2 == num3) {
                    textViewResult.setText("All three numbers are equal!");
                } else {
                    textViewResult.setText(max + " is the largest number");
                    playSound();
                    animateResult();
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNumber1.setText("");
                editTextNumber2.setText("");
                editTextNumber3.setText("");
                textViewResult.setText("");
            }
        });
    }

    private void playSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.bell);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private void animateResult() {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(800); // 0.8 seconds
        textViewResult.startAnimation(animation);
    }
}
