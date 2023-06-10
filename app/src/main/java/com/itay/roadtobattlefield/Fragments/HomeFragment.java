package com.itay.roadtobattlefield.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.itay.roadtobattlefield.Activities.MainActivity;
import com.itay.roadtobattlefield.Activities.SettingsActivity;
import com.itay.roadtobattlefield.Activities.TrainingActivity;
import com.itay.roadtobattlefield.Classes.WorkoutGenerator;
import com.itay.roadtobattlefield.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    static public Button generatePlan;
    static public TextView plan, error;

    static public Button startTraining;
    static public String txt = "Please generate a plan";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        plan = view.findViewById(R.id.txt_trainingPlan);
        plan.setText(txt);
        generatePlan = view.findViewById(R.id.btn_training);

        startTraining = view.findViewById(R.id.btn_startTrain);
        error = view.findViewById(R.id.txt_error);

        startTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.workoutPlan == null) {
                    Toast.makeText(getContext(), "Please make sure to generate a plan!", Toast.LENGTH_SHORT).show();
                } else
                    startActivity(new Intent(getContext(), TrainingActivity.class));
            }
        });

        generatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePlanInTxtView();
            }
        });

    }


    @Override
    public void onClick(View view) {

    }

    public void generatePlanInTxtView() {

        MainActivity.workoutGenerator = new WorkoutGenerator();
        txt = "";
        for (int i = 0; i < MainActivity.workoutPlan.length; i++) {
            txt = txt + MainActivity.workoutPlan[i].getNAME() + "\n";
        }
        plan.setText(txt);
    }

}