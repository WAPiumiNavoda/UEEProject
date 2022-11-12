package com.example.ueefoodprotectionapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddFood extends AppCompatActivity {

    EditText contact, foodItem, quantity, location, expDate, price;
    ImageView foodImage, uploadbtn;
    Button submit, viewAll;
    Uri ImageUri;
    RelativeLayout relative;
    Calendar myCalendar;
    boolean isAllFieldsChecked = false;

    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.setTitle("Food Item Uploading");
        dialog.setCanceledOnTouchOutside(false);

        contact = findViewById(R.id.contact);
        foodItem = findViewById(R.id.foodItem);
        quantity = findViewById(R.id.quantity);
        location = findViewById(R.id.location);
        foodImage = findViewById(R.id.foodImage);
        expDate = (EditText) findViewById(R.id.expDate);
        price = findViewById(R.id.price);
        uploadbtn = findViewById(R.id.uploadbtn);
        submit = findViewById(R.id.submit);
        viewAll = findViewById(R.id.viewAll);
        relative = findViewById(R.id.relative);

        //Date Picker
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel(myCalendar, expDate);
            }
        };
        expDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddFood.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
                relative.setVisibility(View.VISIBLE);
                uploadbtn.setVisibility(View.GONE);
            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFood.this, RecyclerviewList.class);
                startActivity(intent);
            }
        });

        //Add Data
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    dialog.show();

                    final StorageReference reference = firebaseStorage.getReference().child("foods")
                            .child(System.currentTimeMillis() + "");

                    reference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    PostModel postModel = new PostModel();
                                    postModel.setFoodImage(uri.toString());

                                    postModel.setContact(contact.getText().toString());
                                    postModel.setFoodItem(foodItem.getText().toString());
                                    postModel.setQuantity(quantity.getText().toString());
                                    postModel.setLocation(location.getText().toString());
                                    postModel.setExpDate(expDate.getText().toString());
                                    postModel.setPrice(price.getText().toString());

                                    database.getReference().child("foods").push().setValue(postModel)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(AddFood.this, "Food Item Added Successfully", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();

                                                    Intent intent = new Intent(AddFood.this, CommunityHomepage.class);
                                                    startActivity(intent);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    dialog.dismiss();
                                                    Toast.makeText(AddFood.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    private void updateLabel(Calendar myCalendar, EditText expDate) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        expDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void UploadImage() {

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(AddFood.this, "Permission Denied", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK){
            ImageUri = data.getData();
            foodImage.setImageURI(ImageUri);
        }
    }

    //Validations
    private boolean CheckAllFields() {
        if(contact.length() == 0){
            contact.setError("This field is required");
            return false;
        }
        if(foodItem.length() == 0){
            foodItem.setError("This field is required");
            return false;
        }
        if (quantity.length() == 0) {
            quantity.setError("This field is required");
            return false;
        }
        if(location.length() == 0){
            location.setError("This field is required");
            return false;
        }
        if(expDate.length() == 0){
            expDate.setError("This field is required");
            return false;
        }
        if(price.length() == 0){
            price.setError("This field is required");
            return false;
        }
        else if(contact.length() < 10){
            contact.setError("Please enter a valid mobile number");
            return false;
        }
        return true;
    }

}