package com.example.ueefoodprotectionapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import java.util.UUID;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.ueefoodprotectionapp.data.DoMeeting;
import com.example.ueefoodprotectionapp.data.Meeting;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PlaceMeeting extends AppCompatActivity {

    EditText meetingTitle;
    EditText meetingOtherParty;
    EditText dateInput;
    EditText venueInput;
    EditText meetingDescriptionInput;
    AppCompatButton placeMeetingSubmitButton;
    private FirebaseAuth mAuth;

    public  void createMeeting() {
        String date = dateInput.getText().toString();
        String venue = venueInput.getText().toString();
        String meetingDescription = meetingDescriptionInput.getText().toString();
        String meetingTitleString = meetingTitle.getText().toString();
        String meetingOtherPartyString = meetingOtherParty.getText().toString();

        mAuth  = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Meeting meeting = new Meeting(date, venue, meetingDescription, currentUser.getUid(), meetingTitleString, meetingOtherPartyString);

        if(currentUser != null){
            meeting.setUserId(currentUser.getUid().toString());
        }



        meeting.setMeetingId(Math.random());

        DoMeeting firebaseMeeting = new DoMeeting();
        firebaseMeeting.addMeeting(meeting).addOnSuccessListener(res -> {
            showSuccessAlert();
        }).addOnFailureListener(failure -> {
            Log.e("Data Insertion Error", failure.getMessage());
        });
    }

    private void showSuccessAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(PlaceMeeting.this, R.style.SocialDogAlertTheme).create();
        alertDialog.setTitle("Your meeting has been placed successfully!");
        alertDialog.setMessage("Your meeting has been palced successfully!");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_place_meeting);

        dateInput  = findViewById(R.id.meetingDateInput);
        venueInput = findViewById(R.id.venueInput);
        meetingDescriptionInput = findViewById(R.id.meetingDescriptionInput);
        placeMeetingSubmitButton = findViewById(R.id.placeMeetingSubmitButton);
        meetingTitle = findViewById(R.id.meetingTitle);
        meetingOtherParty = findViewById(R.id.meetingOtherParticipant);

        placeMeetingSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMeeting();
            }
        });


    }
}