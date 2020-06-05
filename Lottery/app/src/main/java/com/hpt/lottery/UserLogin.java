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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class UserLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_login);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        TextView tosignuppage = findViewById(R.id.signup);
        final EditText email = findViewById(R.id.username);
        final EditText pass = findViewById(R.id.password);
        TextView forgot = findViewById(R.id.forgotpass);
        Button tohome = findViewById(R.id.verif);
        TextView condn  = findViewById(R.id.termscondn);
        condn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, TermsAndConditions.class));
            }
        });
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();

        if(mUser != null){
            startActivity(new Intent(UserLogin.this, UserHome.class));
        }
        tohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = pass.getText().toString().trim();

                if(Email.isEmpty()){
                    email.setError("Email is Required");
                    email.requestFocus();
                }
                else if(Password.isEmpty()){
                    pass.setError("Password is Required");
                    pass.requestFocus();
                }
                else{
                    mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                if (Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified()){
                                    findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                                    startActivity(new Intent(UserLogin.this, UserHome.class));
                                }
                                else {
                                    Toast.makeText(UserLogin.this, "Please verify your email address!", Toast.LENGTH_SHORT).show();

                                }
                            }
                            else {
                                Toast.makeText(UserLogin.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        tosignuppage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                startActivity(new Intent(UserLogin.this, UserSignup.class));
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                startActivity(new Intent(UserLogin.this, UserForgotPassword.class));
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
