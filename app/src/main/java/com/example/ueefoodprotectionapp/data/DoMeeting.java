package com.example.ueefoodprotectionapp.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoMeeting {
    private DatabaseReference databaseReference;

    public DoMeeting() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Meeting.class.getSimpleName());
    }


    public Task<Void> addMeeting (Meeting meeting){
        return databaseReference.push().setValue(meeting);
    }


    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
