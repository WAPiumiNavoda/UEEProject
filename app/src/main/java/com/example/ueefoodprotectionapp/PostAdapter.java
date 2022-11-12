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

public class PostAdapter extends FirebaseRecyclerAdapter<PostModel, PostAdapter.ViewHolder> {

    private Context context;
    public PostAdapter(@NonNull FirebaseRecyclerOptions<PostModel> options, Context context) {
        super(options);

        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull final PostModel model) {

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

        //Delete Data
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemHeadLine.getContext());
                builder.setTitle("Are you Sure?");
                builder.setMessage("Deleted data can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("foods")
                                .child(getRef(position).getKey()).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        //Update Data
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogPlus dialog = DialogPlus.newDialog(context)
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.content))
                        .setExpanded(false, 2000)
                        .create();

                View holderView = dialog.getHolderView();

                EditText contact = holderView.findViewById(R.id.updateContact);
                EditText foodItem = holderView.findViewById(R.id.updateFoodItem);
                EditText quantity = holderView.findViewById(R.id.updateQuantity);
                EditText location = holderView.findViewById(R.id.updateLocation);
                EditText expDate = holderView.findViewById(R.id.updateExpDate);
                EditText price = holderView.findViewById(R.id.updatePrice);
                Button btnUpdate = holderView.findViewById(R.id.btnUpdate);

                contact.setText(model.getContact());
                foodItem.setText(model.getFoodItem());
                quantity.setText(model.getQuantity());
                location.setText(model.getLocation());
                expDate.setText(model.getExpDate());
                price.setText(model.getPrice());

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String, Object> map = new HashMap<>();
                        map.put("contact", contact.getText().toString());
                        map.put("foodItem", foodItem.getText().toString());
                        map.put("quantity", quantity.getText().toString());
                        map.put("location", location.getText().toString());
                        map.put("expDate", expDate.getText().toString());
                        map.put("price", price.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("foods")
                                .child(getRef(position).getKey())
                                .updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Details Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Error while Updating", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

                dialog.show();

            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food, parent, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemHeadLine, itemQuantity, itemLocation, itemExpDate, itemPrice;
        ImageView itemImage;
        Button update, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemHeadLine = itemView.findViewById(R.id.itemHeadLine);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            itemLocation = itemView.findViewById(R.id.itemLocation);
            itemExpDate = itemView.findViewById(R.id.itemExpDate);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.itemImage);

            update = itemView.findViewById(R.id.update);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}

