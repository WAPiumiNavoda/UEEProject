package com.example.ueefoodprotectionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CommunityHomepage extends AppCompatActivity {

    Button community;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_homepage);

        community = findViewById(R.id.community);

//        community.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CommunityHomepage.this, RecyclerviewList.class);
//                startActivity(intent);
//            }
//        });
    }
}