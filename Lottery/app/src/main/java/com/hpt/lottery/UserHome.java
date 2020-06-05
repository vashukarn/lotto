package com.hpt.lottery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserHome extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private String userID;
    private String name = "Welcome!";
    private String email = "Lotto Lottery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_home);
        Button logout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = db.collection("users").document(userID);
        documentReference.addSnapshotListener(UserHome.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                assert documentSnapshot != null;
                name = documentSnapshot.getString("name");
                email = documentSnapshot.getString("email");
            }
        });
        TextView user = findViewById(R.id.username);
        TextView mail =  findViewById(R.id.email);
        user.setText(name);
        mail.setText(email);
        RelativeLayout Fdbk = findViewById(R.id.tofeedbackfromhome);
        Fdbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, Feedback.class));
            }
        });




        RelativeLayout Scheme = findViewById(R.id.scheme);
        final RelativeLayout Winners = findViewById(R.id.towinnersfromhome);
        Winners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, com.hpt.lottery.Winners.class));
            }
        });
        Scheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, UserScheme.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(UserHome.this, UserLogin.class);
                startActivity(i);
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
