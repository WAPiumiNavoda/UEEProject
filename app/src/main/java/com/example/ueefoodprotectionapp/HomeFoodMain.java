package com.example.ueefoodprotectionapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

 public class HomeFoodMain extends AppCompatActivity {
    RecyclerView recyclerView;
    HomeFoodManagementAdaptor homeFoodManagementAdaptor;
    Button addButton;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_supply_main);

    recyclerView = (RecyclerView)findViewById(R.id.rv);
    recyclerView.setLayoutManager(new LinearLayoutManager(HomeFoodMain.this));

        FirebaseRecyclerOptions<HomeFoodMainModel> options =
                new FirebaseRecyclerOptions.Builder<HomeFoodMainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Foods"), HomeFoodMainModel.class)
                        .build();

        homeFoodManagementAdaptor = new HomeFoodManagementAdaptor(options);
        recyclerView.setAdapter(homeFoodManagementAdaptor);

        addButton = (Button)findViewById(R.id.btnAddSupply);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddActivity.class));
            }
        });
        ActionBar HomeMainAB = getSupportActionBar();
        HomeMainAB.setTitle("Home Food Management ");
        HomeMainAB.setDisplayHomeAsUpEnabled(true);
        HomeMainAB.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        homeFoodManagementAdaptor.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        homeFoodManagementAdaptor.stopListening();
    }
   //Search Function *****************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.h_search,menu);
        MenuItem item = menu.findItem(R.id.hfSearch);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void txtSearch(String str)
    {
        FirebaseRecyclerOptions<HomeFoodMainModel> options =
                new FirebaseRecyclerOptions.Builder<HomeFoodMainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Foods").orderByChild("item").startAfter(str).endAt(str+"~"), HomeFoodMainModel.class)
                        .build();

        homeFoodManagementAdaptor = new HomeFoodManagementAdaptor(options);
        homeFoodManagementAdaptor.startListening();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(homeFoodManagementAdaptor);
    }
//***********************************************************************************************************************
}