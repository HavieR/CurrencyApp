package com.mateusz.currencyapp.adapters.currencylistadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mateusz.currencyapp.R;

/**
 * Created by Mateusz on 02.02.2017.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView tvCurrency;
    TextView tvCode;
    TextView tvMid;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        tvCurrency= (TextView) itemView.findViewById(R.id.currency);
        tvCode= (TextView) itemView.findViewById(R.id.code);
        tvMid= (TextView) itemView.findViewById(R.id.mid);
    }
}
