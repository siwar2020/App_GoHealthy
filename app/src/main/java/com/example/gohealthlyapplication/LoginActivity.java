package com.example.gohealthlyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "login";
    private EditText Email, mPassword;
    private Button login;
    private TextView forget_pass;
    private Button Sig;
    private FirebaseAuth mAuth;
    FirebaseDatabase database; //realtime database
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.editTextTextEmailAddress3);
        mPassword = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.button4);
        forget_pass = findViewById(R.id.forgetpassword);
        Sig = findViewById(R.id.button5);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = Email.getText().toString();
                String txt_password = mPassword.getText().toString();
                if (TextUtils.isEmpty(txt_email)) {
                    Toast.makeText(LoginActivity.this, "please enter your address email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(LoginActivity.this, "please enter your password", Toast.LENGTH_SHORT).show();

                } else if (!validate_email(txt_email)) {
                    Toast.makeText(LoginActivity.this, "please enter a valid address email", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(txt_email, txt_password);
                }

            }
        });
        Sig.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });
        forget_pass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPassword.class));

            }
        });

    }

    private boolean validate_email(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }


    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            admin_not_admin(user);

                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidUserException){
                                Toast.makeText(LoginActivity.this, "email not in use.", Toast.LENGTH_SHORT).show();

                            }
                            else  if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(LoginActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    private void admin_not_admin(FirebaseUser user) {
        String uid = user.getUid();
        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                mDatabase.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               String admin_user =snapshot.child("admin").getValue().toString();
                if( admin_user.equals("true")){
                    Intent intent = new Intent(LoginActivity.this,admin_fonction.class);
                    startActivity(intent);
                }
                else if (admin_user.equals("false")){
                    Intent intent =new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
