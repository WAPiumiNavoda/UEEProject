package com.example.ueefoodprotectionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleFood extends AppCompatActivity {

    TextView singleHeadline, singleQuantity, singleFood, singleLocation;
    ImageView singleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);

        singleHeadline = findViewById(R.id.singleHeadline);
        singleQuantity = findViewById(R.id.singleQuantity);
        singleFood = findViewById(R.id.singleFood);
        singleLocation = findViewById(R.id.singleLocation);
        singleImage = findViewById(R.id.singleImage);

        Picasso.get().load(getIntent().getStringExtra("singleImage"))
                .placeholder(R.drawable.background)
                .into(singleImage);

        singleHeadline.setText(getIntent().getStringExtra("singleHeadline"));
        singleQuantity.setText(getIntent().getStringExtra("singleQuantity"));
        singleFood.setText(getIntent().getStringExtra("singleFood"));
        singleLocation.setText(getIntent().getStringExtra("singleLocation"));
    }
}