package com.itay.roadtobattlefield;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    TextView error;
    EditText nameId, phoneId, emailId;
    Button btnSubmit;
    Spinner levelSpinner;
    TraineeLevel traineeLevel;
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

            MainActivity.sharedPrefEditor.putBoolean("First time in RTB app", false).commit();
            Trainee temp = new Trainee(name, email, phone, traineeLevel);
            MainActivity.trainee = temp;

            dao.add(temp).addOnSuccessListener(suc -> {
                Toast.makeText(this, "error weirdy", Toast.LENGTH_SHORT).show();
                MainActivity.sharedPrefEditor.putBoolean("First time in the app", false).apply();
                MainActivity.sharedPrefEditor.putString("Trainee name", name).apply();
                MainActivity.sharedPrefEditor.putString("Trainee email", email).apply();
                MainActivity.sharedPrefEditor.putString("Trainee phone", phone).apply();
                MainActivity.sharedPrefEditor.putString("Trainee level", traineeLevel.toString()).apply();
                //finish();
            }).addOnFailureListener(er -> {
                Toast.makeText(this, "error " + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}