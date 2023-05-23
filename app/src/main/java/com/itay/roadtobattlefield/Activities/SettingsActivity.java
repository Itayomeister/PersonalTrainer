package com.itay.roadtobattlefield.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.google.android.material.appbar.MaterialToolbar;
import com.itay.roadtobattlefield.Fragments.HomeFragment;
import com.itay.roadtobattlefield.R;

public class SettingsActivity extends AppCompatActivity {

    static public RadioButton radioButton_easy, radioButton_hard;
    MaterialToolbar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        HomeFragment.settingsChecked = true;

        radioButton_hard = findViewById(R.id.dificullty_hard);
        radioButton_easy = findViewById(R.id.dificullty_easy);
        topBar = findViewById(R.id.topSettingsBar);
        topBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        radioButton_easy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(radioButton_easy.isChecked())
                    HomeFragment.hard = false;
                else
                    HomeFragment.hard = true;
            }
        });

        if(HomeFragment.hard)
            radioButton_hard.setChecked(true);
        else
            radioButton_easy.setChecked(true);

    }
}