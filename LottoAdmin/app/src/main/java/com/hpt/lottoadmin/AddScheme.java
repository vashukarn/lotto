package com.hpt.lottoadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddScheme extends AppCompatActivity {
    EditText Price;
    EditText Participant;
    EditText Noofwinners;
    EditText Winamtstart;
    EditText Winamtstop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_scheme);
        Price = findViewById(R.id.add_winner_name);
        Participant = findViewById(R.id.add_winner_prize);
        Noofwinners = findViewById(R.id.add_winner_scheme);
        Winamtstart = findViewById(R.id.edit_text_winnerstart);
        Winamtstop = findViewById(R.id.edit_text_winnerend);
        final Button save = findViewById(R.id.save_schem);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveNote();
            }
        });
    }
    private void saveNote(){

        String strprice = Price.getText().toString();
        String strpartici = Participant.getText().toString();
        String strnowin = Noofwinners.getText().toString();
        String strstr = Winamtstart.getText().toString();
        String strend = Winamtstop.getText().toString();

        if(strprice.isEmpty()){
            Price.setError("Name is Required");
            Price.requestFocus();
        }
        else if(strpartici.isEmpty()){
            Participant.setError("Email is Required");
            Participant.requestFocus();
        }
        else if(strnowin.isEmpty()){
            Noofwinners.setError("Confirm Password is Required");
            Noofwinners.requestFocus();
        }
        else if(strstr.isEmpty()){
            Winamtstart.setError("Confirm Password is Required");
            Winamtstart.requestFocus();
        }
        else {
            int price = Integer.parseInt(strprice);
            int participation = Integer.parseInt(strpartici);
            int noofwin = Integer.parseInt(strnowin);
            int winstart = Integer.parseInt(strstr);
            int winend = Integer.parseInt(strend);;

            CollectionReference collectionReference = FirebaseFirestore.getInstance()
                    .collection("scheme");
            collectionReference.add(new SchemeForRecyclerView(price, participation, noofwin,winstart, winend));
            Toast.makeText(AddScheme.this, "Scheme Successfully added", Toast.LENGTH_SHORT).show();
            Price.setText("");
            Participant.setText("");
            Noofwinners.setText("");
            Winamtstart.setText("");
            Winamtstop.setText("");
        }
    }
}
