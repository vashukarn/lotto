package com.hpt.lottoadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddWinner extends AppCompatActivity {
    EditText new_winner_name;
    EditText new_winner_prizewon;
    EditText new_winnner_scheme;
    Button winner_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_winner);
        new_winner_name = findViewById(R.id.add_winner_name);
        new_winner_prizewon = findViewById(R.id.add_winner_prize);
        new_winnner_scheme = findViewById(R.id.add_winner_scheme);
        winner_save = findViewById(R.id.save_winner);
        winner_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWinner();
            }
        });
    }

    private void saveWinner() {

        String strname = new_winner_name.getText().toString();
        String strprizewon = new_winner_prizewon.getText().toString();
        String strscheme = new_winnner_scheme.getText().toString();

        if (strname.isEmpty()) {
            new_winner_name.setError("Name is Required");
            new_winner_name.requestFocus();
        } else if (strprizewon.isEmpty()) {
            new_winner_prizewon.setError("Winning Amount is Required");
            new_winner_prizewon.requestFocus();
        } else if (strscheme.isEmpty()) {
            new_winnner_scheme.setError("Winning Scheme is Required");
            new_winnner_scheme.requestFocus();
        } else {
            int prize = Integer.parseInt(strprizewon);
            int scheme = Integer.parseInt(strscheme);

            CollectionReference collectionReference = FirebaseFirestore.getInstance()
                    .collection("winners");
            collectionReference.add(new WinnersForRecyclerView(strname, prize, scheme));
            Toast.makeText(AddWinner.this, "Scheme Successfully added", Toast.LENGTH_SHORT).show();
            new_winner_name.setText("");
            new_winner_prizewon.setText("");
            new_winnner_scheme.setText("");
        }
    }
}
