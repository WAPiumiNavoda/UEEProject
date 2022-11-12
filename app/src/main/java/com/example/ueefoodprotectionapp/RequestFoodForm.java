package com.example.ueefoodprotectionapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestFoodForm extends AppCompatActivity {


    Button requestButton, viewButton;
    EditText Uname, UcurrentLocation, Uaddress,UphoneNo,Udate,UTime;
    DatabaseReference addReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_food_form);

        /*action bar*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Request Food Form");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        requestButton = findViewById(R.id.BacktoHomeButton);


        Uname = findViewById(R.id.updateNameInput);
        UcurrentLocation = findViewById(R.id.requestLocationInput);
        Uaddress = findViewById(R.id.requestAddressInput);
        UphoneNo = findViewById(R.id.requestPhoneInput);
        Udate = findViewById(R.id.requestDateInput);
        UTime = findViewById(R.id.requestTimeInput);

        addReference = FirebaseDatabase.getInstance().getReference().child("AddRequest");

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertRequestData();
                startActivity(new Intent(RequestFoodForm.this,RequestFoodlist.class));
                finish();
            }
        });


    }

    //Insert Data function
    private void insertRequestData(){
        String addName = Uname.getText().toString();
        String addLocation = UcurrentLocation.getText().toString();
        String addAddress = Uaddress.getText().toString();
        String addphoneNo = UphoneNo.getText().toString();
        String adddate = Udate.getText().toString();
        String addtime = UTime.getText().toString();

        //Validation parts

            MainModel addRequest = new MainModel(addName,addLocation,addAddress,addphoneNo,adddate,addtime);
            addReference.push().setValue(addRequest);
            Toast.makeText(this, "Data Added successfully", Toast.LENGTH_SHORT).show();




    }
}