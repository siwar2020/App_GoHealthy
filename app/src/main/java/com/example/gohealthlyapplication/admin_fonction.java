package com.example.gohealthlyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class admin_fonction extends AppCompatActivity {
    ImageButton validate_equi ,check_orders ;
    Button  signout;
    FirebaseAuth Ma_firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fonction);
        validate_equi= findViewById(R.id.validate_posts);
        signout =findViewById(R.id.logout);
        check_orders=findViewById(R.id.check_orders);
        validate_equi.setOnClickListener(v -> {
            Intent intent = new Intent(admin_fonction.this , check_new_posts.class);
            startActivity(intent);

        });
        check_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_fonction.this,admin_confirm_order.class);
                startActivity(intent);
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }
    public void logout() {
        Ma_firebaseAuth = FirebaseAuth.getInstance();
        Ma_firebaseAuth.signOut();
        Toast.makeText(admin_fonction.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(admin_fonction.this, LoginActivity.class);
        startActivity(intent);
    }

}