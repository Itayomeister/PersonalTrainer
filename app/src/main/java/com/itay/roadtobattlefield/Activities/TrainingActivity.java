package com.itay.roadtobattlefield.Activities;

import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.itay.roadtobattlefield.Classes.StrengthExercises.GeneralExercise;
import com.itay.roadtobattlefield.Classes.StrengthExercises.MuscleGroups;
import com.itay.roadtobattlefield.Classes.WorkoutGenerator;
import com.itay.roadtobattlefield.MusicService;
import com.itay.roadtobattlefield.R;

public class TrainingActivity extends AppCompatActivity {

    ProgressBar progressBar;
    int currentProgress = 0;
    CountDownTimer countDownTimer;
    int setsRemaining = 0, exercise = 0;
    String message = "bot";

    Vibrator vibrator;

    Button btn, btn_Sets;
    ImageButton btn_Volume;
    TextView txt, txt_Details;

    private Intent volumeIntent;
    private boolean volume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        volumeIntent = new Intent(this, MusicService.class);

        progressBar = findViewById(R.id.progressBar);
        btn = findViewById(R.id.btn_training);
        btn_Volume = findViewById(R.id.btn_music);
        btn_Sets = findViewById(R.id.btn_startSet);
        btn_Sets.setVisibility(View.INVISIBLE);
        btn_Sets.setEnabled(false);
        txt = findViewById(R.id.timer);
        txt_Details = findViewById(R.id.txt_Details);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTraining();
            }
        });


        btn_Volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(volume){
                    stopService(volumeIntent);
                    btn_Volume.setImageResource(R.drawable.ic_baseline_volume_off_24);
                    volume = false;
                } else {
                    startService(volumeIntent);
                    btn_Volume.setImageResource(R.drawable.ic_baseline_volume_on_24);
                    volume = true;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(volumeIntent);
    }

    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit the training session?")
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }

    private void StartTraining() {
        btn_Sets.setVisibility(View.VISIBLE);
        btn_Sets.setEnabled(true);
        message = "remaining " + MainActivity.workoutPlan[0].getNAME() + " Sets: " + MainActivity.workoutPlan[0].getNumber_of_sets();


        btn_Sets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Sets.setEnabled(false);
            }
        });

        new Thread(() -> {
            for (this.exercise = 0; exercise < MainActivity.workoutPlan.length; exercise++) {
                for (int j = 0; j < MainActivity.workoutPlan[exercise].getNumber_of_sets(); j++) {
                    setsRemaining = MainActivity.workoutPlan[exercise].getNumber_of_sets() - j;
                    while (btn_Sets.isEnabled()) {
                    }
                    message = "remaining " + MainActivity.workoutPlan[exercise].getNAME() + " Sets: " + setsRemaining;
                    runOnUiThread(() -> {
                        txt_Details.setText(message);
                        createCountdownTimer(MainActivity.workoutPlan[exercise].getRest_time_minutes()
                                , MainActivity.workoutPlan[exercise].getRest_time_seconds());
                    });
                    while (!btn_Sets.isEnabled()) {
                    }
                }
                runOnUiThread(() -> {
                    Toast.makeText(this, "Watch out! New exercise!", Toast.LENGTH_SHORT).show();
                });
            }
            runOnUiThread(() -> {
                Toast.makeText(this, "Well done! A fabulous workout", Toast.LENGTH_SHORT).show();
            });
        }).start();


    }

    private void createCountdownTimer(int min, int sec) {
        int time = min * 60 + sec;
        progressBar.setMax(time * 1000);
        countDownTimer = new CountDownTimer(time * 1000, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentProgress = (int) (progressBar.getMax() - millisUntilFinished);
                progressBar.setProgress(currentProgress);
                int temp = (int) (millisUntilFinished / 1000) + 1;
                int tempMin = temp / 60, tempSec = temp % 60;
                String text = tempSec < 10 ? tempMin + " : 0" + tempSec : tempMin + " : " + tempSec;
                txt.setText(text);
            }

            @Override
            public void onFinish() {
                vibrator.vibrate(500);
                currentProgress = 0;
                progressBar.setProgress(currentProgress);
                String temp = sec < 10 ? min + " : 0" + sec : min + " : " + sec;
                txt.setText(temp);
                btn_Sets.setEnabled(true);
                Toast.makeText(TrainingActivity.this, "Time to start another Set!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }



}