package com.hpt.lottery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UserScheme extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference schemeref = db.collection("scheme");

    private SchemeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_scheme);
        setUpRecyclerView();
    }
    private void setUpRecyclerView(){
        Query query = schemeref.orderBy("price", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ForRecyclerView> options = new FirestoreRecyclerOptions.Builder<ForRecyclerView>()
                .setQuery(query, ForRecyclerView.class)
                .build();

        adapter  = new SchemeAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListner(new SchemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                ForRecyclerView forRecyclerView = documentSnapshot.toObject(ForRecyclerView.class);
                assert forRecyclerView != null;
                int intprice = forRecyclerView.getPrice();
                int intwinstart = forRecyclerView.getWinneramountstart();
                int intwinend = forRecyclerView.getWinneramountend();
                int intwinner = forRecyclerView.getNoofwinners();
                int intpartici = forRecyclerView.getParticipant();

                Intent takingpricetocheckout = new Intent(UserScheme.this, CheckoutPage.class);
                takingpricetocheckout.putExtra("price", intprice);
                takingpricetocheckout.putExtra("participants", intpartici);
                takingpricetocheckout.putExtra("winno", intwinner);
                takingpricetocheckout.putExtra("winstart", intwinstart);
                takingpricetocheckout.putExtra("winend", intwinend);
                startActivity(takingpricetocheckout);
//                Toast.makeText(UserScheme.this,"The price is" + String.valueOf(halwa),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
