package com.mateusz.currencyapp.adapters.currencylistadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mateusz.currencyapp.R;
import com.mateusz.currencyapp.adapters.currencylistadapter.RecyclerViewHolder;
import com.mateusz.currencyapp.models.Rate;

import java.util.List;
;

/**
 * Created by Mateusz on 02.02.2017.
 */
public class RecyclerAdapter extends RecyclerView.Adapter {
    private List<Rate> rates;

    public RecyclerAdapter(List<Rate> rates) {
        this.rates = rates;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecyclerViewHolder)holder).tvCurrency.setText(rates.get(position).getCurrency());
        ((RecyclerViewHolder)holder).tvCode.setText(rates.get(position).getCode());
        ((RecyclerViewHolder)holder).tvMid.setText(rates.get(position).getMid()+"");
    }

    @Override
    public int getItemCount() {
        return rates.size();
    }
}
