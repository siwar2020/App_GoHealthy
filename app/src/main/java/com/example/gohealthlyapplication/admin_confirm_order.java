package com.example.gohealthlyapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class admin_confirm_order extends AppCompatActivity {
    private RecyclerView recyclerview;
    private DatabaseReference mDatabase;
    private DatabaseReference orders_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_confirm_order);
        recyclerview = findViewById(R.id.ordersListe);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        orders_ = mDatabase.child("orders");
        FirebaseRecyclerOptions<order> options = new FirebaseRecyclerOptions.Builder<order>().setQuery(orders_, order.class).build();
        orderAdapter adapter = new orderAdapter(options);
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(new orderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DataSnapshot documentSnapshot, int position) {
                String id=documentSnapshot.getKey();
                Intent intent =  new Intent(admin_confirm_order.this, order_accept.class);
                intent.putExtra("pid",id);
                CharSequence options[] = new CharSequence[] {"Yes" ,"No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(admin_confirm_order.this);
                builder.setTitle("Would you to confirm this shippement ?");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            startActivity(intent);

                        }
                        if(which==1){

                        }
                    }
                });
                builder.show();
            }
        });
        adapter.startListening();
    };



}