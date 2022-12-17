package com.example.ueefoodprotectionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.ueefoodprotectionapp.data.Meeting;
import com.example.ueefoodprotectionapp.data.DoMeeting;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class YourMeetups extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Meeting> meetings = new ArrayList<Meeting>();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_meetups);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        recyclerView = findViewById(R.id.meetupsRecycleView);
        DoMeeting doMeeting = new DoMeeting();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Log.i("Meetings", meetings.toString());

        recyclerView = findViewById(R.id.meetupsRecycleView);
        MeetupListAdapater meetupListAdapter = new MeetupListAdapater(getApplicationContext(), meetings);

        recyclerView.setAdapter(meetupListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        if(user.getUid() != null){
            Log.i("Meetings", user.getUid().toString());
            doMeeting.getDatabaseReference().orderByChild("userId").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i("Meetings", Boolean.toString(snapshot.exists()));
                    if(snapshot.exists()){

                        for(DataSnapshot child: snapshot.getChildren()){
                            Meeting meeting = child.getValue(Meeting.class);
                            meetings.add(meeting);
                        }

                        Log.i("Meetings", meetings.toString());
                        meetupListAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Database error", error.getMessage());
                }
            });
        }else  {
//            Intent intent = new Intent(this, LoginPage.class);
//            startActivity(intent);
        }
    }
}