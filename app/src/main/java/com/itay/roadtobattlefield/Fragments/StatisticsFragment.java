package com.itay.roadtobattlefield.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itay.roadtobattlefield.Activities.MainActivity;
import com.itay.roadtobattlefield.R;

public class StatisticsFragment extends Fragment {
    TextView speedTop, timeTop, distanceTop,
            speedAverage, timeAverage, distanceAverage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        speedTop = view.findViewById(R.id.tvMaxSpeedValue);
        speedAverage = view.findViewById(R.id.tvAverageSpeedValue);
        distanceTop = view.findViewById(R.id.tvMaxDistanceValue);
        distanceAverage = view.findViewById(R.id.tvAverageDistanceValue);
        timeTop = view.findViewById(R.id.tvMaxTimeValue);
        timeAverage = view.findViewById(R.id.tvAverageTimeValue);

        speedTop.setText(String.valueOf(MainActivity.trainee.getTopRunningSpeed()));
        speedAverage.setText(String.valueOf(MainActivity.trainee.getAverageRunningSpeed()));
        timeTop.setText(String.valueOf(MainActivity.trainee.getTopRunningTime()));
        timeAverage.setText(String.valueOf(MainActivity.trainee.getAverageRunningTime()));
        distanceTop.setText(String.valueOf(MainActivity.trainee.getTopRunningDistance()));
        distanceAverage.setText(String.valueOf(MainActivity.trainee.getAverageRunningDistance()));


    }
}