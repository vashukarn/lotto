package com.hpt.lottoadmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class SalesAdapter extends FirestoreRecyclerAdapter<SalesForRecyclerView, SalesAdapter.SalesHolder> {

    public SalesAdapter(@NonNull FirestoreRecyclerOptions<SalesForRecyclerView> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull SalesHolder holder, int position, @NonNull SalesForRecyclerView model) {
        holder.textViewname.setText(model.getName());
        holder.textViewemail.setText(model.getEmail());
        holder.textViewquantity.setText(model.getQuantity());
        holder.textViewtotalamount.setText(model.getAmountspent());
        int amt = Integer.parseInt(model.getAmountspent());
        int qty = Integer.parseInt(model.getQuantity());
        holder.textViewschemeprice.setText(String.valueOf(amt/qty));


    }

    @NonNull
    @Override
    public SalesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_item,
                parent, false);
        return new SalesHolder(v);
    }

    class SalesHolder extends RecyclerView.ViewHolder{
        TextView textViewname;
        TextView textViewemail;
        TextView textViewschemeprice;
        TextView textViewquantity;
        TextView textViewtotalamount;

        public SalesHolder(@NonNull View itemView) {
            super(itemView);
            textViewname = itemView.findViewById(R.id.sales_name_text);
            textViewemail = itemView.findViewById(R.id.sales_email_text);
            textViewquantity = itemView.findViewById(R.id.sales_quantity_text);
            textViewschemeprice = itemView.findViewById(R.id.sales_schemeprice_text);
            textViewtotalamount = itemView.findViewById(R.id.sales_amounttotal_text);
        }
    }
}
