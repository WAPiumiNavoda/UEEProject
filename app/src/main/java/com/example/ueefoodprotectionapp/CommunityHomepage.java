package com.example.ueefoodprotectionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CommunityHomepage extends AppCompatActivity {

    Button community, myCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_homepage);

        community = findViewById(R.id.community);
        myCart = findViewById(R.id.myCart);

        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityHomepage.this, CommunityRecyclerview.class);
                startActivity(intent);
            }
        });

        myCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityHomepage.this, RecyclerviewList.class);
                startActivity(intent);
            }
        });
    }
}