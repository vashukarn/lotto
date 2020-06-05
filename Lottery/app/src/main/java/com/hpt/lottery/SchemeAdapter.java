package com.hpt.lottery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class SchemeAdapter extends FirestoreRecyclerAdapter<ForRecyclerView, SchemeAdapter.SchemeHolder> {
    private OnItemClickListener listener;


    public SchemeAdapter(@NonNull FirestoreRecyclerOptions<ForRecyclerView> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SchemeHolder holder, int position, @NonNull ForRecyclerView model) {
        holder.Price.setText(String.valueOf(model.getPrice()));
        holder.Participant.setText(String.valueOf(model.getParticipant()));
        holder.WinNum.setText(String.valueOf(model.getNoofwinners()));
        holder.WinStart.setText(String.valueOf(model.getWinneramountstart()));
        holder.WinEnd.setText(String.valueOf(model.getWinneramountend()));

    }

    @NonNull
    @Override
    public SchemeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scheme_item, parent, false);
        return new SchemeHolder(view);
    }

    class SchemeHolder extends RecyclerView.ViewHolder{
        TextView Price;
        TextView Participant;
        TextView WinNum;
        TextView WinStart;
        TextView WinEnd;


        public SchemeHolder(@NonNull View itemView) {
            super(itemView);
            Price = itemView.findViewById(R.id.price);
            Participant = itemView.findViewById(R.id.partici);
            WinNum = itemView.findViewById(R.id.winno);
            WinStart = itemView.findViewById(R.id.amtstart);
            WinEnd = itemView.findViewById(R.id.amtstop);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION && listener!= null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListner(OnItemClickListener listner){
        this.listener = listner;
    }
}
