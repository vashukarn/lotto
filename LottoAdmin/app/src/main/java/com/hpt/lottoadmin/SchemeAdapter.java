package com.hpt.lottoadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class SchemeAdapter  extends FirestoreRecyclerAdapter<SchemeForRecyclerView, SchemeAdapter.SchemeHolder> {
    private OnItemClickListner listner;


    public SchemeAdapter(@NonNull FirestoreRecyclerOptions<SchemeForRecyclerView> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SchemeHolder holder, int position, @NonNull SchemeForRecyclerView model) {
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
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class SchemeHolder extends RecyclerView.ViewHolder{
        TextView Price;
        TextView Participant;
        TextView WinNum;
        TextView WinStart;
        TextView WinEnd;


        public SchemeHolder(@NonNull View itemView) {
            super(itemView);
            Price = itemView.findViewById(R.id.winner_name_text);
            Participant = itemView.findViewById(R.id.partici);
            WinNum = itemView.findViewById(R.id.winno);
            WinStart = itemView.findViewById(R.id.winner_prizewon_text);
            WinEnd = itemView.findViewById(R.id.amtstop);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listner != null){
                        listner.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }
    }
    public interface OnItemClickListner{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        this.listner = listner;

    }
}
