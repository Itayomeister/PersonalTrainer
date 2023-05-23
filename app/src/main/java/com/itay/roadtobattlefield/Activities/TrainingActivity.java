package com.itay.roadtobattlefield.Activities;

import static java.lang.Math.round;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itay.roadtobattlefield.Classes.StrengthExercises.GeneralExercise;
import com.itay.roadtobattlefield.Classes.StrengthExercises.MuscleGroups;
import com.itay.roadtobattlefield.R;

public class TrainingActivity extends AppCompatActivity {

    ProgressBar progressBar;
    int currentProgress = 0;
    CountDownTimer countDownTimer;
    int setsRemaining = 0, exercise = 0;
    String message = "bot";

    Vibrator vibrator;

    Button btn, btn_Sets;
    TextView txt, txt_Details;
    private GeneralExercise[] exercises = new GeneralExercise[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        setPullUp();
        setPushUp();
        setDragonFly();

        progressBar = findViewById(R.id.progressBar);
        btn = findViewById(R.id.btn_training);
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
    }

    private void StartTraining() {
        btn_Sets.setVisibility(View.VISIBLE);
        btn_Sets.setEnabled(true);


        btn_Sets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Sets.setEnabled(false);
            }
        });

        new Thread(() -> {
            for (this.exercise = 0; exercise < exercises.length; exercise++) {
                for (int j = 0; j < exercises[exercise].getNumber_of_sets(); j++) {
                    setsRemaining = exercises[exercise].getNumber_of_sets() - j;
                    while (btn_Sets.isEnabled()) {
                    }
                    message = "remaining " + exercises[exercise].getNAME() + " Sets: " + setsRemaining;
                    runOnUiThread(() -> {
                        txt_Details.setText(message);
                        createCountdownTimer(exercises[exercise].getRest_time_minutes()
                                , exercises[exercise].getRest_time_seconds());
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
        int time = min * 60 + sec + 30;
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


    private void setPullUp() {
        exercises[0] = new GeneralExercise(4, 7, 3, 0);
        exercises[0].setNAME("Pull Ups");
        exercises[0].setINFO("Pull Ups - A pulling exercise for the Back and biceps");
        exercises[0].setMUSCLE_GROUP(MuscleGroups.Back);
        exercises[0].setSec_MUSCLE_GROUPS(new MuscleGroups[]{MuscleGroups.RearDelts, MuscleGroups.Biceps});
    }

    private void setPushUp() {
        exercises[1] = new GeneralExercise(4, 20, 3, 0);
        exercises[1].setNAME("Push Ups");
        exercises[1].setINFO("Push Ups - A pushing exercise for the chest and triceps");
        exercises[1].setMUSCLE_GROUP(MuscleGroups.Chest);
        exercises[1].setSec_MUSCLE_GROUPS(new MuscleGroups[]{MuscleGroups.FrontDelts, MuscleGroups.Triceps});
    }

    private void setDragonFly() {
        exercises[2] = new GeneralExercise(3, 10, 1, 30);
        exercises[2].setNAME("Dragon Fly");
        exercises[2].setINFO("Dragon Fly - one of the hardest abs' exercises there is");
        exercises[2].setMUSCLE_GROUP(MuscleGroups.LowerAbs);
    }
}