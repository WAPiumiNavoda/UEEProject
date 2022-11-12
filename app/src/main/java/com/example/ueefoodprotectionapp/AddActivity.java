package com.example.ueefoodprotectionapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText supply , Amount ,ImgUrl;
    Button btnAdd,btnBack;
    String date ;
    DatePickerDialog datePickerDialog;
    Button dateButton ;
    Context context;
    RadioGroup radioGroup;
    String TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_food_add);
        radioGroup = findViewById(R.id.radioGp);
        initDatePicker();
        context = this;

        supply = (EditText)findViewById(R.id.txtSupply);
        Amount=(EditText) findViewById(R.id.txtAmount);
        ImgUrl=(EditText) findViewById(R.id.txtUrl);
        dateButton=(Button) findViewById(R.id.btnDate);

        btnAdd=(Button) findViewById(R.id.btnAddSave);
        btnBack=(Button) findViewById(R.id.btnBack);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.gRadio:
                       TYPE = "g";
                        break;

                    case R.id.kgRadio:
                        TYPE ="kg";
                        break;

                }
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertData();
                clearAll();

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Date Picker ---------------------------------------------------------------------------
    private Bundle initDatePicker()
    {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                date = makeDateString(day, month, year);
                dateButton.setText(date);
                //*********************************************************

            }

        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        return null;
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }
    private void clearAll()
    {
        Amount.setText("");
        ImgUrl.setText("");
        supply.setText("");

    }
    private void insertData() {
        String AMOUNT = Amount.getText().toString();
        String IMGURL = ImgUrl.getText().toString();
        String ITEM = supply.getText().toString();

        if (AMOUNT.isEmpty()) {
            Amount.setError("Amount is Required");
        } else if (IMGURL.isEmpty()) {
            ImgUrl.setError("Amount is an must");
        }else if(ITEM.isEmpty()){
            supply.setError("Supply is must");
//        }else if(date.isEmpty()){
//            dateButton.setError("Date is must");
        }
        else {
            Map<String, Object> map = new HashMap<>();
            map.put("amount", Amount.getText().toString());
            map.put("imgUrl", ImgUrl.getText().toString());
            map.put("item", supply.getText().toString());
            map.put("bDate", date);
            map.put("type", TYPE);



//        map.put("type",kg.getText().toString());
//        map.put("type",g.getText().toString());
            FirebaseDatabase.getInstance().getReference().child("Foods").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddActivity.this, "Data Inserted successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddActivity.this, "Error while insertion", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}