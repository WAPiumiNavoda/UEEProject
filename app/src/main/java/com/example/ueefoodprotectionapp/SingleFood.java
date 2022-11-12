package com.example.ueefoodprotectionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleFood extends AppCompatActivity {

    TextView singleContact, singleHeadline, singleQuantity, singleFood, singleLocation, singleExpDate, singlePrice;
    ImageView singleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);

        singleContact = findViewById(R.id.singleContact);
        singleHeadline = findViewById(R.id.singleHeadline);
        singleQuantity = findViewById(R.id.singleQuantity);
        singleFood = findViewById(R.id.singleFood);
        singleLocation = findViewById(R.id.singleLocation);
        singleExpDate = findViewById(R.id.singleExpDate);
        singlePrice = findViewById(R.id.singlePrice);
        singleImage = findViewById(R.id.singleImage);

        Picasso.get().load(getIntent().getStringExtra("singleImage"))
                .placeholder(R.drawable.background)
                .into(singleImage);

        singleContact.setText(getIntent().getStringExtra("singleContact"));
        singleHeadline.setText(getIntent().getStringExtra("singleHeadline"));
        singleQuantity.setText(getIntent().getStringExtra("singleQuantity"));
        singleFood.setText(getIntent().getStringExtra("singleFood"));
        singleLocation.setText(getIntent().getStringExtra("singleLocation"));
        singleExpDate.setText(getIntent().getStringExtra("singleExpDate"));
        singlePrice.setText(getIntent().getStringExtra("singlePrice"));
    }
}