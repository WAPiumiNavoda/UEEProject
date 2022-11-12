package com.example.ueefoodprotectionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class CommunityRecyclerview extends AppCompatActivity {

    RecyclerView community_id;
    CommunityAdapter adapter;
    androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_recyclerview);

        community_id = findViewById(R.id.community_id);
        community_id.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

        //Search Data
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchData(s);
                return true;
            }
        });

        //Retrieve to RecyclerViewer
        FirebaseRecyclerOptions<PostModel> options =
                new FirebaseRecyclerOptions.Builder<PostModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("foods"), PostModel.class)
                        .build();

        adapter = new CommunityAdapter(options, this);
        community_id.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //Search Data
    private void searchData(String s) {

        FirebaseRecyclerOptions<PostModel> options =
                new FirebaseRecyclerOptions.Builder<PostModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("foods").orderByChild("foodItem").startAt(s).endAt(s+"~"), PostModel.class)
                        .build();

        adapter = new CommunityAdapter(options, this);
        community_id.setAdapter(adapter);
        adapter.startListening();
    }
}