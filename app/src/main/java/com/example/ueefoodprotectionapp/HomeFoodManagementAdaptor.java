package com.example.ueefoodprotectionapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFoodManagementAdaptor extends FirebaseRecyclerAdapter<HomeFoodMainModel,HomeFoodManagementAdaptor.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public HomeFoodManagementAdaptor(@NonNull FirebaseRecyclerOptions<HomeFoodMainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull HomeFoodMainModel model) {
        holder.type.setText(model.getType());
        holder.bDate.setText(model.getbDate());
        holder.amount.setText(model.getAmount());
        holder.item.setText(model.getItem());

        Glide.with(holder.imgUrl.getContext())
                .load(model.imgUrl)
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imgUrl);

// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        holder.btnChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imgUrl.getContext())
                        .setContentHolder(new ViewHolder(R.layout.home_food_change_popup))
                        .setExpanded(true,1400)
                        .create();

                // dialogPlus.show();
                View view = dialogPlus.getHolderView();


                EditText supply = view.findViewById(R.id.txtSupply);
                EditText amount = view.findViewById(R.id.txtAmount);
                EditText imgUrl = view.findViewById(R.id.txtUrl);

                EditText date = view.findViewById(R.id.txtDate);

                Button btnChange = view.findViewById(R.id.btnChangeSave);

                supply.setText(model.getItem());
                amount.setText(model.getAmount());
                imgUrl.setText(model.getImgUrl());
                date.setText(model.getbDate());



                dialogPlus.show();

                btnChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("amount",amount.getText().toString());
                        map.put("bDate",date.getText().toString());
                        map.put("imgUrl",imgUrl.getText().toString());
                        map.put("item",supply.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Foods")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.item.getContext(), "Changed successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.item.getContext(), "Error while Updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });

            }


        });
        //***************************************************************************************************

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.item.getContext());
                builder.setTitle("Are you sure ?");
                builder.setMessage("Deleted date can't be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Foods")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.item.getContext(), "Canceled.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_supply_item,parent,false);
        return new myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgUrl;
        TextView amount,bDate,item,type;
        Button btnChange , btnDelete ,btnDate;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUrl =(CircleImageView)itemView.findViewById(R.id.img1);
            amount=(TextView)itemView.findViewById(R.id.hfoodamount);
            bDate=(TextView)itemView.findViewById(R.id.hfooddate);
            item=(TextView)itemView.findViewById(R.id.hfoodItem);
            type=(TextView)itemView.findViewById(R.id.hfoodtype);

            btnChange=(Button)itemView.findViewById(R.id.btnChange);
            btnDelete=(Button)itemView.findViewById(R.id.btnRemove);
        }
    }
}
