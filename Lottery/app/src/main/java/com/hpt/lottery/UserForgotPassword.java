package com.hpt.lottery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class UserForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_forgot_password);
        final EditText email = findViewById(R.id.username);
        Button verificationmail = findViewById(R.id.verif);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);


        verificationmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernamemail = email.getText().toString().trim();
                if(usernamemail.isEmpty()){
                    email.setError("Please enter your mail id!");
                    email.requestFocus();
                }
                else {
                    mAuth.sendPasswordResetEmail(usernamemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                                Toast.makeText(UserForgotPassword.this, "Reset link has been sent to your Email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserForgotPassword.this, UserLogin.class));
                            }
                            else {
                                Toast.makeText(UserForgotPassword.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
