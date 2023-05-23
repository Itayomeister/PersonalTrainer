package com.itay.roadtobattlefield;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.itay.roadtobattlefield.Activities.MainActivity;

public class TurnOffFirstyActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_off_firsty);

        btn = findViewById(R.id.btn_TurnOffFirsty);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.sharedPrefEditor.putBoolean("First time in RTB app", true).commit();
            }
        });
    }
}