package com.hpt.lottoadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class WinnerAdapter extends FirestoreRecyclerAdapter<WinnersForRecyclerView, WinnerAdapter.WinnerHolder> {

    public WinnerAdapter(@NonNull FirestoreRecyclerOptions<WinnersForRecyclerView> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull WinnerHolder holder, int position, @NonNull WinnersForRecyclerView model) {
        holder.textViewName.setText(model.getName());
        holder.textViewPrizewon.setText(String.valueOf(model.getPrizewon()));
        holder.textViewUnderScheme.setText(String.valueOf(model.getUnderscheme()));

    }

    @NonNull
    @Override
    public WinnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.winners_item,
                parent, false);
        return new WinnerHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class WinnerHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewPrizewon;
        TextView textViewUnderScheme;

        public WinnerHolder(@NonNull View itemView) {
            super(itemView);
            textViewName  = itemView.findViewById(R.id.winner_name_text);
            textViewPrizewon = itemView.findViewById(R.id.winner_prizewon_text);
            textViewUnderScheme = itemView.findViewById(R.id.winner_underscheme_text);
        }
    }
}
