package com.example.ueefoodprotectionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchFood extends AppCompatActivity {

    Button search;
    EditText place,food;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);

        place = findViewById(R.id.searchTextOne);
        food = findViewById(R.id.searchTextTwo);
        search = findViewById(R.id.search_requestButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchFood.this,MapActivity.class));
                finish();
            }
        });
    }

}