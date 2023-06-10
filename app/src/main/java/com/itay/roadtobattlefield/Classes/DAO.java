package com.itay.roadtobattlefield.Classes;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.itay.roadtobattlefield.Activities.MainActivity;
import com.itay.roadtobattlefield.Activities.SignUpActivity;
import com.itay.roadtobattlefield.DAOtype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.LogRecord;
import android.os.Handler;

public class DAO {
    private DatabaseReference databaseReference;
    int traineeId = 1;
    private String referenceName = "bot";
    private ArrayList<Trainee> traineesList = new ArrayList<>();
    public boolean exitApp = false, generated = false;

    public DAO(DAOtype type) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        if (type == DAOtype.Trainee)
            referenceName = "Trainees";
        if (type == DAOtype.Complaint)
            referenceName = "Complaints";
        databaseReference = firebaseDatabase.getReference(referenceName);

    }

    public Task<Void> addComplaint(Complaint complaint) {
        return databaseReference.push().setValue(complaint);
    }

    public Task<Void> addTrainee(Trainee trainee) {
        generateTraineeId();
        return databaseReference.child(String.valueOf(traineeId)).setValue(trainee);
    }

    public ArrayList<Trainee> viewTrainees(int traineeId) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    traineesList.add(data.getValue(Trainee.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return traineesList;
    }

    public void updateTrainee(Trainee trainee, int id, boolean exit) {
        databaseReference.child(String.valueOf(id)).setValue(trainee).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                    if(exit)
                        exitApp = true;
            }
        });
    }

    public void generateTraineeId() {
        databaseReference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                traineeId = (int) dataSnapshot.getChildrenCount() + 1;
                SignUpActivity.userId = traineeId;
                generated = true;

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                generateTraineeId();
            }
        });
        checkForGenerated();
    }

    private void checkForGenerated() {
        Handler handler = new Handler();
        if(!generated)
            handler.postDelayed(this::checkForGenerated, 250);
    }

    public Query get() {
        return databaseReference.orderByKey();
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
