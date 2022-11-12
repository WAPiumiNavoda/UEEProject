package com.example.ueefoodprotectionapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CommunityAdapter extends FirebaseRecyclerAdapter<PostModel, CommunityAdapter.ViewHolder> {

    private Context context;
    public CommunityAdapter(@NonNull FirebaseRecyclerOptions<PostModel> options, Context context) {
        super(options);

        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull final PostModel model) {

        holder.itemContact.setText(model.getContact());
        holder.itemHeadLine.setText(model.getFoodItem());
        holder.itemQuantity.setText(model.getQuantity());
        holder.itemLocation.setText(model.getLocation());
        holder.itemExpDate.setText(model.getExpDate());
        holder.itemPrice.setText(model.getPrice());

        String imageUri = model.getFoodImage();
        Picasso.get().load(imageUri).into(holder.itemImage);

        //View Single Item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleFood.class);
                intent.putExtra("singleImage", model.getFoodImage());
                intent.putExtra("singleHeadline", model.getFoodItem());
                intent.putExtra("singleQuantity", model.getQuantity());
                intent.putExtra("singleFood", model.getFoodItem());
                intent.putExtra("singleLocation", model.getLocation());
                intent.putExtra("singleContact", model.getContact());
                intent.putExtra("singleExpDate", model.getExpDate());
                intent.putExtra("singlePrice", model.getPrice());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.community_food, parent, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemContact, itemHeadLine, itemQuantity, itemLocation, itemExpDate, itemPrice;
        ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemContact = itemView.findViewById(R.id.itemContact);
            itemHeadLine = itemView.findViewById(R.id.itemHeadLine);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            itemLocation = itemView.findViewById(R.id.itemLocation);
            itemExpDate = itemView.findViewById(R.id.itemExpDate);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}
