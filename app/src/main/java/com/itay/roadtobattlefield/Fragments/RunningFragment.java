package com.itay.roadtobattlefield.Fragments;

import static java.lang.Math.max;
import static java.lang.Math.round;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itay.roadtobattlefield.Activities.MainActivity;
import com.itay.roadtobattlefield.Classes.Trainee;
import com.itay.roadtobattlefield.R;

public class RunningFragment extends Fragment {

    private Button btnRunning, btnDistance;
    private TextView txtTimer;
    private EditText editTextDistance;

    private Handler handler;

    private boolean isRunning = false;
    private double startTime = 0, currentTime = 0, elapsedTime = 0;
    private double distance = 0, speed = 0, time = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_running, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler = new Handler();

        btnRunning = view.findViewById(R.id.btn_startRunning);
        btnDistance = view.findViewById(R.id.btn_submitRunningDistance);
        txtTimer = view.findViewById(R.id.txt_timer);
        editTextDistance = view.findViewById(R.id.editText_runningDistance);

        btnRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    endStopwatch();
                } else {
                    startStopwatch();
                }
            }
        });

        btnDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTimer == null || editTextDistance == null)
                    return;
                else if (elapsedTime == 0 || editTextDistance.getText().length() < 1) {
                    Toast.makeText(getContext(), "Make sure to track your time and distance!", Toast.LENGTH_SHORT).show();
                } else if (isRunning) {
                    Toast.makeText(getContext(), "Make sure to stop the time!", Toast.LENGTH_SHORT).show();
                } else {
                    distance = Double.parseDouble(editTextDistance.getText().toString());
                    double sec = (elapsedTime / 1000) % 60;
                    double min = (elapsedTime / 1000) / 60;

                    time = min / 60 + sec / 3600;
                    speed = distance / time;

                    Trainee temp = MainActivity.trainee;
                    MainActivity.trainee.setAverageRunningTime((temp.getAverageRunningTime() * temp.getRunningAmount() + time) / (temp.getRunningAmount() + 1));
                    MainActivity.trainee.setAverageRunningDistance((temp.getAverageRunningDistance() * temp.getRunningAmount() + distance) / (temp.getRunningAmount() + 1));
                    MainActivity.trainee.setAverageRunningSpeed((temp.getAverageRunningSpeed() * temp.getRunningAmount() + speed) / (temp.getRunningAmount() + 1));
                    MainActivity.trainee.setRunningAmount(temp.getRunningAmount() + 1);
                    MainActivity.trainee.setTopRunningDistance(max(distance, temp.getTopRunningDistance()));
                    MainActivity.trainee.setTopRunningSpeed(max(speed, temp.getTopRunningSpeed()));
                    MainActivity.trainee.setTopRunningTime(max(time, temp.getTopRunningTime()));

                    Toast.makeText(getContext(), "here", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "speed " + speed, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "distance " + distance, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "time " + time, Toast.LENGTH_SHORT).show();
                    txtTimer.setText("00:00");
                    editTextDistance.setText("");
                }

            }
        });

    }

    private void startStopwatch() {
        if (txtTimer == null)
            return;
        txtTimer.setText("00:00");
        isRunning = true;
        btnRunning.setText("Stop Running");
        startTime = (int) System.currentTimeMillis();
        updateStopwatch();
    }

    private void endStopwatch() {
        isRunning = false;
    }

    private void updateStopwatch() {
        if (isRunning) {
            currentTime = (int) System.currentTimeMillis();
            elapsedTime = currentTime - startTime;
            setTimerText();

            handler.postDelayed(this::updateStopwatch, 1000);
        }
    }

    private void setTimerText() {
        int sec = (int) (elapsedTime / 1000) % 60;
        int min = (int) (elapsedTime / 1000) / 60;
        String seconds, minutes;
        if (sec < 10)
            seconds = "0" + sec;
        else
            seconds = "" + sec;
        if (min < 10)
            minutes = "0" + min;
        else
            minutes = "" + min;

        txtTimer.setText(minutes + ":" + seconds);
    }
}