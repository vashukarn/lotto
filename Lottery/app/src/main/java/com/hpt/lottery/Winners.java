package com.hpt.lottery;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Winners extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference winnnersRef = db.collection("winners");
    private WinnerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_winners);
        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        Query query = winnnersRef.orderBy("name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<WinnersForRecyclerView> options = new FirestoreRecyclerOptions.Builder<WinnersForRecyclerView>()
                .setQuery(query, WinnersForRecyclerView.class)
                .build();

        adapter  = new WinnerAdapter(options);
        final RecyclerView recyclerView = findViewById(R.id.winner_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
