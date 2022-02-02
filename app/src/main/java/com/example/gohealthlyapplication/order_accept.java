package com.example.gohealthlyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class order_accept extends AppCompatActivity {
    String OrderId ="";
    Button send;
    EditText message;
    FirebaseDatabase database; //realtime database
    private DatabaseReference mDatabase;
    private DatabaseReference orders_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_accept);
        OrderId = getIntent().getStringExtra("pid");
        message =findViewById(R.id.message);
        send = findViewById(R.id.button_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(message.toString()))
                {
                    Toast.makeText(order_accept.this, "Please enter message to send", Toast.LENGTH_SHORT).show();
                }
                else {

                    database = FirebaseDatabase.getInstance();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("orders");
                    mDatabase.child(OrderId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String phone_number = snapshot.child("phone").getValue().toString();
                            SmsManager.getDefault().sendTextMessage(phone_number,null,message.getText().toString(),null,null);
                            Intent intent = new Intent(order_accept.this,admin_fonction.class);
                            startActivity(intent);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }

            }
        });
    }



}