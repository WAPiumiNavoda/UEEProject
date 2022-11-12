package com.example.ueefoodprotectionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ueefoodprotectionapp.data.DoMeeting;
import com.example.ueefoodprotectionapp.data.Meeting;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MeetDetailsScreen extends AppCompatActivity {

    DoMeeting doMeeting = new DoMeeting();
    TextView meetingHeadline;
    TextView meetingDescription;
    TextView meetingVenue;
    AppCompatButton completeMeetingButton;
    AppCompatButton rescheduleButton;
    AppCompatButton cancelButton;

    public void markMeetingAsCompleted() {

    }

    public void reschedule() {

    }

    public void cancel() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_details_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        meetingHeadline = findViewById(R.id.meetDetailsHeading);
        meetingDescription = findViewById(R.id.meetDetailsDescription);
        meetingVenue = findViewById(R.id.meetingVenue);
        completeMeetingButton = findViewById(R.id.completeButton);
        rescheduleButton = findViewById(R.id.reschedule_button);
        cancelButton = findViewById(R.id.cancel_button);

        Intent intent = getIntent();
        int meetingId =  intent.getIntExtra("dogId", 0);

        doMeeting.getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot child: snapshot.getChildren()){
                        Meeting meeting = child.getValue(Meeting.class);

                        if(meeting.getMeetingId() == meetingId){
                         meetingHeadline.setText(meeting.getMeeingDescription());
                         meetingDescription.setText(meeting.getMeeingDescription());
                         meetingVenue.setText(meeting.getVenue());
                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database error", error.getMessage());
            }
        });

        completeMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markMeetingAsCompleted();
            }
        });

        rescheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reschedule();
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });



    }
}