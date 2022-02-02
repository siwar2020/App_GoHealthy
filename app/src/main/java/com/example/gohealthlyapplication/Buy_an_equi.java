package com.example.gohealthlyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Buy_an_equi extends AppCompatActivity {
    private EditText name, homeAddress,cityName,phone ;
    private Button achat;

    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String UserId = user.getUid();
    private TextView nom,mail;
    private ImageView im_equip;
    private String id_;
    private DatabaseReference mDatabase;
    private DatabaseReference equipements_medicaments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_an_equi);
        name=findViewById(R.id.name);
        homeAddress=findViewById(R.id.homeaddress);
        cityName=findViewById(R.id.city);
        phone=findViewById(R.id.Phone);
        achat= findViewById(R.id.button_confirm);
        mDatabase=FirebaseDatabase.getInstance().getReference();
        equipements_medicaments = mDatabase.child("equipements_médicaments");
        id_=getIntent().getExtras().getString("pid","0");

        achat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkDetails();
                        Confirmation(id_);

                    }
    }); }

            private void Confirmation(String id_) {
                Map<String, Object> Encore = new HashMap<>();
                Encore.put("encoreDisponible", false);
                DatabaseReference productRef = equipements_medicaments.child(id_);
                try {
                    productRef.updateChildren(Encore);
                } catch (Exception e) {
                }
            }



                private void checkDetails() {
        if (TextUtils.isEmpty(name.getText().toString()))
        {
            Toast.makeText(Buy_an_equi.this,"please enter your name",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone.getText().toString()))
        {
            Toast.makeText(Buy_an_equi.this,"please enter your phone number",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(homeAddress.getText().toString()))
        {
            Toast.makeText(Buy_an_equi.this,"please enter your home address",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cityName.getText().toString()))
        {
            Toast.makeText(Buy_an_equi.this,"please enter your city name",Toast.LENGTH_SHORT).show();
        }
        else {
            confrm_order();
        }

    }

    private void confrm_order() {
        String txt_name= name.getText().toString();
        String txt_phone= phone.getText().toString();
        String txt_homeaddress= homeAddress.getText().toString();
        String txt_city= cityName.getText().toString();
        String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String currentTime ,currentDate;
        Calendar date =Calendar.getInstance();
        SimpleDateFormat current_Date =new SimpleDateFormat("MM dd, yyy");
        currentDate =current_Date.format(date.getTime());
        SimpleDateFormat  current_Time =new SimpleDateFormat("HH:mm:ss a");
        currentTime =current_Time.format(date.getTime());
        DatabaseReference orders = FirebaseDatabase.getInstance().getReference().child("orders").child(current_user_id);
        order order_ = new order(txt_name,txt_phone,txt_homeaddress,txt_city,currentDate, currentTime);
        orders.setValue(order_).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Buy_an_equi.this,"your order will be validated by admin",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Buy_an_equi.this, HomeActivity.class);
                    startActivity(intent);

                }

            }
        });


    }

}
