package com.example.ueefoodprotectionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
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

import java.util.HashMap;

public class MeetDetailsScreen extends AppCompatActivity {

    DoMeeting doMeeting = new DoMeeting();
    TextView meetingHeadline;
    TextView meetingDescription;
    TextView meetingVenue;
    AppCompatButton completeMeetingButton;
    AppCompatButton rescheduleButton;
    AppCompatButton cancelButton;

    String documentID;

    public void markMeetingAsCompleted(String documentId) {
        HashMap<String, Object> socialDogMap = new HashMap<String, Object>();
        socialDogMap.put("meetCompleted", true);

        doMeeting.getDatabaseReference().child(documentID).updateChildren(socialDogMap).addOnSuccessListener(res -> {
            showSuccessAlert(documentId);

        }).addOnFailureListener(e -> {
            Log.e("Dog updated error", e.getMessage());
        });

    }



    private void showSuccessAlert(String documentID) {
        AlertDialog alertDialog = new AlertDialog.Builder(MeetDetailsScreen.this, R.style.SocialDogAlertTheme).create();
        alertDialog.setTitle("Meeting was marked as completed");
        alertDialog.setMessage("Your meeting was marked as completed successfully!");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), RateMeetingScreen.class);
                        intent.putExtra("documentID", documentID);
                        startActivity(intent);
                    }
                });

        alertDialog.show();
    }

    public void reschedule() {
//        Intent intent = new Intent(getApplicationContext(), Re.class);
//        startActivity(intent);
    }

    public void cancel() {
        Intent intent = new Intent(getApplicationContext(), NavigationPage.class);
        startActivity(intent);
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
        doMeeting = new DoMeeting();

        Intent intent = getIntent();
        String meetingId =  intent.getStringExtra("meetingId");

        doMeeting.getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot child: snapshot.getChildren()){
                        Meeting meeting = child.getValue(Meeting.class);

                        if(meeting.getMeetingId() == Double.parseDouble(meetingId)){
                            documentID = child.getKey();
                         meetingHeadline.setText(meeting.getTitle());
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
                markMeetingAsCompleted(documentID);
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