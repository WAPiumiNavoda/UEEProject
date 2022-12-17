package com.example.ueefoodprotectionapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ueefoodprotectionapp.data.Meeting;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MeetupListAdapater extends RecyclerView
        .Adapter<MeetupListAdapater.MeetupListViewHolder>{

    private Context ct;
    private ArrayList<Meeting> meetings;

    public MeetupListAdapater(Context ct, ArrayList<Meeting> meetings) {
        this.ct = ct;
       this.meetings  = meetings;
    }


    public void navigateToEditDetailsPage(String id) {
        Intent intent = new Intent(ct,MeetDetailsScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.i("Meeting ID", id);
        intent.putExtra("meetingId", id);
        ct.startActivity(intent);
    }


    @NonNull
    @Override
    public MeetupListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(ct);
        View view = layoutInflater.inflate(R.layout.social_meetup_item, parent, false);
        return new MeetupListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetupListViewHolder holder, int position) {
        holder.meetingTitle.setText(meetings.get(holder.getAdapterPosition()).getTitle());
        holder.meetingDescription.setText(meetings.get(holder.getAdapterPosition()).getMeeingDescription());
        holder.viewProfile.setText("View Meeting Details");
        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToEditDetailsPage(Double.toString(meetings.get(holder.getAdapterPosition()).getMeetingId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public class MeetupListViewHolder extends RecyclerView.ViewHolder {

        TextView meetingTitle;
        TextView meetingDescription;
        AppCompatButton viewProfile;

        public MeetupListViewHolder(@NonNull View itemView) {
            super(itemView);
            meetingTitle = itemView.findViewById(R.id.meetupItemHeadline);
            meetingDescription = itemView.findViewById(R.id.meetupItemDescription);
            viewProfile = itemView.findViewById(R.id.meetupitemdetailsButton);

        }
    }



}