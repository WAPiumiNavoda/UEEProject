package com.example.ueefoodprotectionapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ueefoodprotectionapp.data.DoMeeting;

import java.util.HashMap;

public class RateMeetingScreen extends AppCompatActivity {

    EditText ratingInput;
    Button markAsCompletedButton;
    DoMeeting doMeeting = new DoMeeting();

    public void onAddRating (String documentId) {
        HashMap<String, Object> socialDogMap = new HashMap<String, Object>();
        socialDogMap.put("meetRating", Integer.parseInt(ratingInput.getText().toString()));

        doMeeting.getDatabaseReference().child(documentId).updateChildren(socialDogMap).addOnSuccessListener(res -> {
                showSuccessAlert(documentId);

        }).addOnFailureListener(e -> {
            Log.e("Dog updated error", e.getMessage());
        });
    }


    private void showSuccessAlert(String documentID) {
        AlertDialog alertDialog = new AlertDialog.Builder(RateMeetingScreen.this, R.style.SocialDogAlertTheme).create();
        alertDialog.setTitle("Meeting rated successfully");
        alertDialog.setMessage("Your rated your meeting successfully!");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Okay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int rating = Integer.parseInt(ratingInput.getText().toString());
                        if(rating < 3){
                            Intent intent = new Intent(getApplicationContext(), ReportToAdminScreen.class);
                            startActivity(intent);
                        }else  {
                            Intent intent = new Intent(getApplicationContext(), NavigationPage.class);
                            startActivity(intent);
                        }
                    }
                });

        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_meeting_screen);
        ratingInput = findViewById(R.id.ratingScreenInput);
        markAsCompletedButton = findViewById(R.id.completeButton);

        Intent intent = getIntent();
        String documentId= intent.getStringExtra("documentID");

        markAsCompletedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddRating(documentId);
            }
        });

    }
}