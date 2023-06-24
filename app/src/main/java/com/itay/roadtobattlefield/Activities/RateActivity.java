package com.itay.roadtobattlefield.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.itay.roadtobattlefield.R;

public class RateActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        ratingBar = findViewById(R.id.ratingBar);
        button = findViewById(R.id.btn_rate);

        ratingBar.setRating(MainActivity.trainee.getRating());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.trainee.setRating(ratingBar.getRating());
                Toast.makeText(RateActivity.this, "Saved Rating: " + MainActivity.trainee.getRating(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}