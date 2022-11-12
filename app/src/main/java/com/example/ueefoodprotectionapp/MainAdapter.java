package com.example.ueefoodprotectionapp;

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

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.MyViewolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewolder holder, int position, @NonNull MainModel model) {
        holder.Username.setText(model.getUserName());
        holder.UserTime.setText(model.getTime());
        holder.UserDate.setText(model.getDate());
        holder.UserPhone.setText(model.getPhone());
//        Glide.with(holder.img.getContext())
//                .load(model.getUrl())
//                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
//                .circleCrop()
//                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
//                .into(holder.img);
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder( R.layout.request_change_form))
                        .setExpanded(false,2000)
                        .create();

//                dialogPlus.show();
                View v = dialogPlus.getHolderView();
                EditText Uname  = v.findViewById(R.id.updateNameInput);
                EditText UDate = v.findViewById(R.id.requestDateInput);
                EditText UTime = v.findViewById(R.id.requestTimeInput);
                EditText UPhone = v.findViewById(R.id.requestPhoneInput);

                Button btnUpdate = v.findViewById(R.id.UpdateButton);
                Uname.setText(model.getUserName());
                UDate.setText(model.getDate());
                UTime.setText(model.getTime());
                UPhone.setText(model.getPhone());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map =  new HashMap<>();
                        map.put("userName" ,Uname.getText().toString());
                        map.put("date" ,UDate.getText().toString());
                        map.put("time" ,UTime.getText().toString());
                        map.put("phone",UPhone.getText().toString());
//                        map.put("userName" ,Uname.getText().toString());
//                        map.put("userName" ,Uname.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("AddRequest")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.Username.getContext(), "Data Updated", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.Username.getContext(), "Error while Updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Username.getContext());
                builder.setTitle("Are sure want to delete");
                builder.setMessage("Delete data can;t be undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("AddRequest").
                                child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.Username.getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public MyViewolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new MyViewolder(v);
    }

    class MyViewolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView Username,UserDate, UserTime,UserPhone;
        Button delete,update;

        public MyViewolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.img1);
            Username = (TextView) itemView.findViewById(R.id.txtname);
            UserDate = (TextView) itemView.findViewById(R.id.coursename);
            UserTime = (TextView) itemView.findViewById(R.id.emailname);
            UserPhone = (TextView) itemView.findViewById(R.id.phone);
            delete = (Button) itemView.findViewById(R.id.btnDelete);
            update = (Button) itemView.findViewById(R.id.btnEdit);

        }
    }
}
