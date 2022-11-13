package com.example.ueefoodprotectionapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavigationPage extends AppCompatActivity {
    private TextView navBarSocialText;
    private  TextView yourMeetupsScreen;
    private TextView HomeFood;
    private  TextView healthCare;
    private TextView placeMeeting;
    private FirebaseAuth mAuth;
    private  TextView currentUserEmail;
    private  TextView addFood;


    private void onNavBarSocialTextClick() {

    }

    public void onMeetupListClick () {
        Intent intent = new Intent(this, YourMeetups.class);
        startActivity(intent);
    }

    public void onPlaceScreenClick () {
        Intent intent = new Intent(this, PlaceMeeting.class);
        startActivity(intent);
    }

    public void  onAddFoodClick() {
        Intent intent = new Intent(this, AddFood.class);
        startActivity(intent);
    }
    private void onHomeFoodClick() {
        Intent intent = new Intent(this,HomeFoodMain.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_navigation_page);


        yourMeetupsScreen = (TextView) findViewById(R.id.yourMeetupsScreen);
        HomeFood = (TextView) findViewById(R.id.dayCare);
        placeMeeting = (TextView) findViewById(R.id.placeMeeting);
        currentUserEmail = (TextView) findViewById(R.id.currentuserEmails);
        addFood = (TextView) findViewById(R.id.addFoods);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUserEmail.setText(currentUser.getEmail());
        }

        HomeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHomeFoodClick();
            }

        });

        yourMeetupsScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMeetupListClick();
            }
        });


        placeMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlaceScreenClick();
            }
        });


        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddFoodClick();
            }
        });
    }
}