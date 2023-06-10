package com.itay.roadtobattlefield.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.itay.roadtobattlefield.Classes.DAO;
import com.itay.roadtobattlefield.DAOtype;
import com.itay.roadtobattlefield.NetworkReceiver;
import com.itay.roadtobattlefield.R;
import com.itay.roadtobattlefield.Classes.Trainee;
import com.itay.roadtobattlefield.TraineeLevel;

public class SignUpActivity extends AppCompatActivity {

    TextView error;
    EditText nameId, phoneId, emailId;
    Button btnSubmit;
    Spinner levelSpinner;
    static TraineeLevel traineeLevel;
    static Trainee trainee;
    public static String name, phone, email;
    private DAO dao;

    private NetworkReceiver networkReceiver;
    private IntentFilter intentFilter;
    static public int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        networkReceiver = new NetworkReceiver();
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        error = findViewById(R.id.txt_errorSignUp);
        nameId = findViewById(R.id.editText_nameId);
        emailId = findViewById(R.id.editText_emailId);
        phoneId = findViewById(R.id.editText_phoneId);
        btnSubmit = findViewById(R.id.btn_SignUp);
        levelSpinner = findViewById(R.id.spinner_levelId);

        dao = new DAO(DAOtype.Trainee);
        trainee = new Trainee();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.networkStatus)
                    signUp();
                else
                    Toast.makeText(SignUpActivity.this, "Check for Internet connection to register", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the receiver when the activity is resumed
        registerReceiver(networkReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the receiver when the activity is paused
        unregisterReceiver(networkReceiver);
    }

    void signUp() {
        if (nameId.getText().length() == 0)
            error.setText("Please fill in your name");
        else if (emailId.getText().length() == 0)
            error.setText("Please fill in your email");
        else if (phoneId.getText().length() == 0)
            error.setText("Please fill in your phone number");
        else {
            name = nameId.getText().toString();
            email = emailId.getText().toString();
            phone = phoneId.getText().toString();

            switch (levelSpinner.getSelectedItem().toString()) {
                case "Beginner":
                    traineeLevel = TraineeLevel.Begginer;
                    break;
                case "Intermediate":
                    traineeLevel = TraineeLevel.Intermediate;
                    break;
                case "Advanced":
                    traineeLevel = TraineeLevel.Advanced;
                    break;
                case "Hardcore":
                    traineeLevel = TraineeLevel.Hardcore;
                    break;
            }

            dao.generateTraineeId();
            Handler handler = new Handler();
            Runnable temp = new Runnable() {
                @Override
                public void run() {
                    if (userId == -1) {
                        handler.postDelayed(this, 250);
                    }
                    else {
                        MainActivity.userId = userId;
                        trainee = new Trainee(name, email, phone, traineeLevel, userId);
                        dao.addTrainee(trainee).addOnSuccessListener(suc -> {
                            popMessage("Trainee Saved!");
                            MainActivity.sharedPrefEditor.putBoolean("First time in RTB app", false).apply();
                            MainActivity.sharedPrefEditor.putInt("userId RTB", MainActivity.userId).apply();
                            MainActivity.trainee = trainee;
                            finish();
                        }).addOnFailureListener(er -> {
                            popMessage("Error: " + er.getMessage());
                        });
                    }
                }
            };
            temp.run();

        }
    }

    @Override
    public void onBackPressed() {
        if (MainActivity.sharedPreferences.getBoolean("First time in the app", true)) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Exit?")
                    .setMessage("Are you sure you want to exit without signing up? \n " +
                            "You will not be able to use the app!")
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finishAffinity();
                        }
                    })
                    .show();
        }
    }

    public void popMessage(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

    }

}