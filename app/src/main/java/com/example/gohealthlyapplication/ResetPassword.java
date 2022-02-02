package com.example.gohealthlyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    private EditText email;
    private Button restPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mAuth = FirebaseAuth.getInstance();
        restPassword = findViewById(R.id.restpass);
        email = findViewById(R.id.editTextTextEmailAddress2);
        restPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_user = email.getText().toString();
                if (TextUtils.isEmpty(email_user)) {
                    Toast.makeText(ResetPassword.this, "please ,enter your email", Toast.LENGTH_SHORT).show();

                }
                else {
                    mAuth.sendPasswordResetEmail(email_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ResetPassword.this, "please , check your email account to reset your password", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPassword.this , LoginActivity.class));
                            }
                            else
                            {
                                String message_error = task.getException().getMessage();
                                Toast.makeText(ResetPassword.this, "this is an error"+ message_error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}