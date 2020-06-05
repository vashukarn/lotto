package com.hpt.lottoadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MemberAdapter extends FirestoreRecyclerAdapter<MemberForRecyclerView, MemberAdapter.MemberHolder> {

    public MemberAdapter(@NonNull FirestoreRecyclerOptions<MemberForRecyclerView> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MemberHolder holder, int position, @NonNull MemberForRecyclerView model) {
        holder.textViewName.setText(model.getName());
        holder.textViewEmail.setText(model.getEmail());
    }

    @NonNull
    @Override
    public MemberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item,
                parent, false);
        return new MemberHolder(v);
    }

    class MemberHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewEmail;

        public MemberHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.member_name_text);
            textViewEmail = itemView.findViewById(R.id.member_email_text);
        }
    }
}
