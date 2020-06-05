package com.hpt.lottery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {
    private FirebaseFirestore db;
    private EditText fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_feedback);
        fd = findViewById(R.id.fb);
        Button fbbtn = findViewById(R.id.savefeedback);
        db = FirebaseFirestore.getInstance();

        fbbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feed = fd.getText().toString();

                if(feed.isEmpty()){
                    Toast.makeText(Feedback.this, "You need to enter something to continue", Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<String, Object> back = new HashMap<>();
                    back.put("feedback", feed);

                    db.collection("feedbacks").document("feeds")
                            .set(back)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Feedback.this, "Feedback successfully sent!", Toast.LENGTH_SHORT).show();
                                    fd.setText("");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Feedback.this, "Could not send your feedback, Please try again!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }
}
