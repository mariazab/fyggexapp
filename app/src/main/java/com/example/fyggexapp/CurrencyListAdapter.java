package com.example.fyggexapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder> {

    public static class CurrencyViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView euroTextView;

        private CurrencyViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            euroTextView = itemView.findViewById(R.id.euro);
        }
    }

    private final LayoutInflater inflater;
    private List<Currency> rateList;

    public CurrencyListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.rate_item, parent, false);
        return new CurrencyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        if (rateList != null) {
            Currency current = rateList.get(position);

            holder.nameTextView.setText(current.getName());
            holder.euroTextView.setText(String.format("%s€", current.getEuroPrice()));
        }
    }

    void setRateList(List<Currency> currencyItems) {
        rateList = currencyItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(rateList != null) {
            return rateList.size();
        } else {
            return 0;
        }
    }
}