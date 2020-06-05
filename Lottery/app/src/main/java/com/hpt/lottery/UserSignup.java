package com.hpt.lottery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserSignup extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    private String  UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_signup);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        final EditText name = findViewById(R.id.username);
        final EditText email = findViewById(R.id.emailid);
        final EditText pass = findViewById(R.id.password);
        final EditText confpass = findViewById(R.id.confpassword);
        Button logintofirebase = findViewById(R.id.signuptohome);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        TextView tologinpage = findViewById(R.id.login);
        tologinpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserSignup.this, UserLogin.class));
            }
        });
        logintofirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Name = name.getText().toString().trim();
                final String Email = email.getText().toString().trim();
                final String Password = pass.getText().toString().trim();
                String ConfirmPassword = confpass.getText().toString().trim();

                if(Name.isEmpty()){
                    name.setError("Name is Required");
                    name.requestFocus();
                }
                else if(Email.isEmpty()){
                    email.setError("Email is Required");
                    email.requestFocus();
                }
                else if(Password.isEmpty()){
                    pass.setError("Password is Required");
                    pass.requestFocus();
                }
                else if(ConfirmPassword.isEmpty()){
                    confpass.setError("Confirm Password is Required");
                    confpass.requestFocus();
                }
                else if(!(ConfirmPassword.equals(Password))){
                    Toast.makeText(UserSignup.this, "Both passwords are not equal", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Objects.requireNonNull(mAuth.getCurrentUser()).sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            UID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                                            Map<String, String> userMap = new HashMap<>();
                                                            userMap.put("name", Name);
                                                            userMap.put("email", Email);
                                                            userMap.put("password", Password);
                                                            db.collection("users").document(UID).set(userMap);

                                                            Toast.makeText(UserSignup.this, "Registered Successfully! Please check your email for verification", Toast.LENGTH_SHORT).show();
                                                            name.setText("");
                                                            email.setText("");
                                                            pass.setText("");
                                                            confpass.setText("");
                                                            FirebaseAuth.getInstance().signOut();
                                                            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                                                            startActivity(new Intent(UserSignup.this, UserLogin.class));
                                                        }
                                                        else {
                                                            Toast.makeText(UserSignup.this, Objects.requireNonNull(task.getException())
                                                                    .getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                    else {
                                        Toast.makeText(UserSignup.this, Objects.requireNonNull(task.getException())
                                                .getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
