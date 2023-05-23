package com.itay.roadtobattlefield.Classes;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.itay.roadtobattlefield.DAOtype;

public class DAO {
    private DatabaseReference databaseReference;

    public DAO(DAOtype type) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        if (type == DAOtype.Trainee)
            databaseReference = firebaseDatabase.getReference(Trainee.class.getSimpleName());
        if (type == DAOtype.Complaint)
            databaseReference = firebaseDatabase.getReference(Complaint.class.getSimpleName());
        if(type == DAOtype.Workout)
            databaseReference = firebaseDatabase.getReference(WorkoutGenerator.class.getSimpleName());


    }

    public Task<Void> addComplaint(Complaint complaint) {
        return databaseReference.push().setValue(complaint);
    }

    public Task<Void> addTrainee(Trainee trainee) {
        return databaseReference.push().setValue(trainee);
    }

    public Task<Void> addWorkout(WorkoutGenerator workout) {
        return databaseReference.push().setValue(workout);
    }

    public Query get() {
        return databaseReference.orderByKey();
    }
}
