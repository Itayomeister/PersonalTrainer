package com.itay.roadtobattlefield.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itay.roadtobattlefield.Classes.Complaint;
import com.itay.roadtobattlefield.Classes.DAO;
import com.itay.roadtobattlefield.Classes.Trainee;
import com.itay.roadtobattlefield.DAOtype;
import com.itay.roadtobattlefield.NetworkReceiver;
import com.itay.roadtobattlefield.R;

public class ContactUsActivity extends AppCompatActivity {

    private Button btnSend;
    private String complaint;
    private EditText editTxt_complaint;
    private Trainee trainee;
    private DAO dao;

    private NetworkReceiver networkReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        networkReceiver = new NetworkReceiver();
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        btnSend = findViewById(R.id.sendApplication_btn);
        editTxt_complaint = findViewById(R.id.application_editTxt);

        trainee = MainActivity.trainee;

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTxt_complaint.getText().length() == 0)
                    Toast.makeText(ContactUsActivity.this, "Make sure you wrote the complaint", Toast.LENGTH_SHORT).show();
                else if (MainActivity.networkStatus){
                    sendData();
                }
                else
                    Toast.makeText(ContactUsActivity.this, "Check for Internet connection to send", Toast.LENGTH_SHORT).show();
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

    void sendData() {
        dao = new DAO(DAOtype.Complaint);

        complaint = editTxt_complaint.getText().toString();
        dao.addComplaint(new Complaint(trainee, complaint)).addOnSuccessListener(suc -> {
            Toast.makeText(this, "Added complaint of: " + trainee.getFullName(), Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(er -> {
            Toast.makeText(this, "error " + er.getMessage(), Toast.LENGTH_SHORT).show();
        });
        resetAllFields();
    }

    void resetAllFields() {
        editTxt_complaint.setText("");
    }
}