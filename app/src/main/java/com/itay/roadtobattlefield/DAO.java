package com.itay.roadtobattlefield;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DAO {
    private DatabaseReference databaseReference;

    public DAO(DAOtype type) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        if (type == DAOtype.Trainee)
            databaseReference = firebaseDatabase.getReference(Trainee.class.getSimpleName());
        if (type == DAOtype.Complaint){
            databaseReference = firebaseDatabase.getReference(Complaint.class.getSimpleName());
        }

    }

    public Task<Void> add(Complaint complaint) {
        return databaseReference.push().setValue(complaint);
    }

    public Task<Void> add(Trainee trainee){
        return databaseReference.push().setValue(trainee);
    }

    public Query get(){
        return  databaseReference.orderByKey();
    }
}
