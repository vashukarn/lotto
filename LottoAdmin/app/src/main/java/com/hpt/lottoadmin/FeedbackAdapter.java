package com.hpt.lottoadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FeedbackAdapter extends FirestoreRecyclerAdapter<FeedbackForRecyclerView, FeedbackAdapter.FeedbackHolder> {
    public FeedbackAdapter(@NonNull FirestoreRecyclerOptions<FeedbackForRecyclerView> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FeedbackHolder holder, int position, @NonNull FeedbackForRecyclerView model) {
        holder.Feedbak.setText(model.getFeedback());

    }

    @NonNull
    @Override
    public FeedbackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_item,
                parent, false);
        return new FeedbackHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class FeedbackHolder extends RecyclerView.ViewHolder{
        TextView Feedbak;

        public FeedbackHolder(@NonNull View itemView) {
            super(itemView);
            Feedbak  = itemView.findViewById(R.id.fdbk_tv_item);

        }
    }
}
