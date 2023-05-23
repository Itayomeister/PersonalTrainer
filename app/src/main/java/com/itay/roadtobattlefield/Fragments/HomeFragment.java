package com.itay.roadtobattlefield.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.itay.roadtobattlefield.Activities.SettingsActivity;
import com.itay.roadtobattlefield.R;

public class HomeFragment extends Fragment implements View.OnClickListener {


    RadioButton radioButton_hard, radioButton_easy;

    static public Button generatePlan;
    static public TextView plan, error;

    static public Button submit;
    static public EditText txt_distance;
    static public EditText txt_pullups;
    static public EditText txt_pushups;

    private String easyPlan = "Hello trainer! \n" +
            "You chose the easy difficulty \n" +
            "Here is your plan: \n" +
            "Run for 15 min \n" +
            "Perform 20 pushups, pullups and situps \n" +
            "Good Luck!";

    private String hardPlan = "Hello trainer! \n" +
            "You chose the hard difficulty, BRAVO! \n" +
            "Here is your plan: \n" +
            "Run for 30 min \n" +
            "Perform 40 pushups, pullups and situps \n" +
            "Good Luck!";

    public String txt = "Please generate a plan";

    String distance, pullups, pushups;

    private boolean planHard = false, planExist = false;
    static public boolean hard = false, settingsChecked = false;


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

        radioButton_easy = SettingsActivity.radioButton_easy;
        radioButton_hard = SettingsActivity.radioButton_hard;

        txt_distance = view.findViewById(R.id.editTextRunningDistance);
        txt_pullups = view.findViewById(R.id.editTextSetsForPullups);
        txt_pushups = view.findViewById(R.id.editTextSetsForPushups);

        submit = view.findViewById(R.id.btn_SubmitData);
        error = view.findViewById(R.id.txt_error);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txt_distance.getText().toString().matches("") || txt_pullups.getText().toString().matches("") || txt_pushups.getText().toString().matches("")){
                    error.setText("Please fill in all fields");
                    error.setTextColor(Color.RED);
                }

                else {
                    distance = txt_distance.getText().toString();
                    txt_distance.setText("");
                    pullups = txt_pullups.getText().toString();
                    txt_pullups.setText("");
                    pushups = txt_pushups.getText().toString();
                    txt_pushups.setText("");

                    plan.setText("dis: " + distance +"\n"+
                            "pull: " + pullups +"\n" +
                            "push: " + pushups);
                    error.setText("");
                }

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

        if(!settingsChecked && !planExist) {
             plan.setText("Check out the settings first to adjust the difficulty! \n" +
                     easyPlan);
             planExist = true;
             return;
        }

        else if ((plan.getText() != "Please generate a plan") && ((planHard && hard) || (!hard && !planHard && planExist))) {
            Toast.makeText(getActivity(), "you already have the right plan!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        else if (hard) {
            txt = hardPlan;
            planHard = true;
        } else {
            txt = easyPlan;
            planHard = false;
        }
        plan.setText(txt);
    }

}