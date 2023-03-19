package com.itay.roadtobattlefield;

import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;

public class Training extends AppCompatActivity {

    ProgressBar progressBar;
    int currentProgress = 0;
    CountDownTimer countDownTimer;
    int counter = 6;

    Vibrator vibrator;

    Button btn;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        progressBar = findViewById(R.id.progressBar);
        btn = findViewById(R.id.btn_training);
        txt = findViewById(R.id.timer);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCountdownTimer(40);
            }
        });

    }

    private void createCountdownTimer(int time) {
        progressBar.setMax(time * 1000);
        countDownTimer = new CountDownTimer(time * 1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentProgress = (int) (progressBar.getMax() - millisUntilFinished);
                progressBar.setProgress(currentProgress);
                String text = "" + (int) ((millisUntilFinished / 1000) + 1);
                txt.setText(text);
            }

            @Override
            public void onFinish() {
                vibrator.vibrate((60 - time) * 20);
                if (time == 20){
                    counter--;
                    btn.setText("Left: " + counter);
                }

                if (counter != 0) {
                    currentProgress = 0;
                    createCountdownTimer(time == 40 ? 20 : 40);
                }
            }
        }.start();
    }
}