package com.itay.roadtobattlefield.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itay.roadtobattlefield.Classes.Complaint;
import com.itay.roadtobattlefield.Classes.DAO;
import com.itay.roadtobattlefield.DAOtype;
import com.itay.roadtobattlefield.R;

public class ContactUsActivity extends AppCompatActivity {

    Button btnSend;
    EditText editTxt_name, editTxt_phone, editTxt_complaint;
    String name, email, complaint, phone;
    DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        btnSend = findViewById(R.id.sendApplication_btn);
        editTxt_name = findViewById(R.id.name_editTxt);
        editTxt_phone = findViewById(R.id.phoneNumber_editTxt);
        editTxt_complaint = findViewById(R.id.application_editTxt);

        editTxt_name.setText(MainActivity.trainee.getFullName());

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    void sendData(){
        dao = new DAO(DAOtype.Complaint);

        name = MainActivity.trainee.getFullName();
        email = MainActivity.trainee.getEmail();
        phone = MainActivity.trainee.getPhoneNum();
        complaint = editTxt_complaint.getText().toString();
        dao.addComplaint(new Complaint(name, email, phone, complaint)).addOnSuccessListener(suc -> {
            Toast.makeText(this, "Added complaint of: " + name, Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(er -> {
            Toast.makeText(this, "error " + er.getMessage(), Toast.LENGTH_SHORT).show();
        });
        resetAllFields();
    }

    void resetAllFields(){
        editTxt_complaint.setText("");
        editTxt_phone.setText("");
        editTxt_name.setText("");
    }
}