package com.itay.roadtobattlefield.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.itay.roadtobattlefield.Classes.DAO;
import com.itay.roadtobattlefield.DAOtype;
import com.itay.roadtobattlefield.R;
import com.itay.roadtobattlefield.Classes.Trainee;
import com.itay.roadtobattlefield.TraineeLevel;

public class SignUpActivity extends AppCompatActivity {

    TextView error;
    EditText nameId, phoneId, emailId;
    Button btnSubmit;
    Spinner levelSpinner;
    TraineeLevel traineeLevel;
    Trainee trainee;
    public static String name, phone, email;
    DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
                signUp();
            }
        });
    }

    void signUp(){
        if(nameId.getText().length() == 0)
            error.setText("Please fill in your name");
        else if(emailId.getText().length() == 0)
            error.setText("Please fill in your email");
        else if(phoneId.getText().length() == 0)
            error.setText("Please fill in your phone number");
        else{
            name = nameId.getText().toString();
            email = emailId.getText().toString();
            phone = phoneId.getText().toString();

            switch (levelSpinner.getSelectedItem().toString()){
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

            trainee = new Trainee(name, email, phone, traineeLevel);
            MainActivity.trainee = trainee;

            dao.addTrainee(trainee).addOnSuccessListener(suc -> {
                Toast.makeText(this, "Trainee Saved!" , Toast.LENGTH_SHORT).show();
                MainActivity.sharedPrefEditor.putBoolean("First time in the app", false).apply();
                MainActivity.sharedPrefEditor.putString("Trainee name", name).apply();
                MainActivity.sharedPrefEditor.putString("Trainee email", email).apply();
                MainActivity.sharedPrefEditor.putString("Trainee phone", phone).apply();
                MainActivity.sharedPrefEditor.putString("Trainee level", traineeLevel.toString()).apply();
                finish();
            }).addOnFailureListener(er -> {
                Toast.makeText(this, "error " + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onBackPressed() {
        if(MainActivity.sharedPreferences.getBoolean("First time in the app", true)){
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
}