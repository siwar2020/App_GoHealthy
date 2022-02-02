package com.example.gohealthlyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText mFullName;
    EditText mEmail;
    EditText mPassword;
    EditText phone_num;
    Button register;
    private TextView login;
    private FirebaseAuth mAuth;
    FirebaseDatabase database; //realtime database
    private DatabaseReference mDatabase;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mFullName = findViewById(R.id.name);
        mEmail = findViewById(R.id.editTextTextEmailAddress);
        mPassword = findViewById(R.id.password_user);
        phone_num = findViewById(R.id.phone_number);
        register = findViewById(R.id.button3);
        login = findViewById(R.id.textView4);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getInstance().getReference().child("users"); // Pour lire ou écrire des données à partir de la base de données, vous avez besoin d'une instance de DatabaseReference :
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = mFullName.getText().toString();
                String txt_email = mEmail.getText().toString();
                String txt_password = mPassword.getText().toString();
                String txt_phone = phone_num.getText().toString();

                if (TextUtils.isEmpty( txt_username) ) {
                    Toast.makeText(RegisterActivity.this, "please enter your full name", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(txt_email)) {
                    Toast.makeText(RegisterActivity.this, "please enter your address email", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(txt_password)){
                    Toast.makeText(RegisterActivity.this, "please enter your password", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(txt_phone)) {
                    Toast.makeText(RegisterActivity.this, "please enter your phone number", Toast.LENGTH_SHORT).show();
                }
                else if (!validate_email(txt_email)){
                    Toast.makeText(RegisterActivity.this, "please enter a valid address email", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(txt_email, txt_password);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });



    }
    private boolean validate_email(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }



    private void registerUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(RegisterActivity.this, "email already in use", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(RegisterActivity.this, " you have successfully created a user account,", Toast.LENGTH_SHORT).show();
                            Save_UserData_ToDatabase();
                            startActivity(new Intent(RegisterActivity.this , LoginActivity.class));
                        }
                    }

                });
        }

    private void Save_UserData_ToDatabase() {
        firebaseUser = mAuth.getCurrentUser();
        String txt_username = mFullName.getText().toString();
        String txt_email = mEmail.getText().toString();
        String txt_password = mPassword.getText().toString();
        String txt_phone = phone_num.getText().toString();
        User user = new User(txt_username, txt_email, txt_password, txt_phone);
        mDatabase.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(RegisterActivity.this, "values storted in database", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(RegisterActivity.this, "failed to store values in database", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}