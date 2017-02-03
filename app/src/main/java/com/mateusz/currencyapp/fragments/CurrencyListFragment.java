package com.mateusz.currencyapp.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mateusz.currencyapp.CurrencyApplication;
import com.mateusz.currencyapp.R;
import com.mateusz.currencyapp.adapters.currencylistadapter.RecyclerAdapter;
import com.mateusz.currencyapp.database.CurrencyDao;
import com.mateusz.currencyapp.models.Rate;
import com.mateusz.currencyapp.services.GetDataService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 02.02.2017.
 */
public class CurrencyListFragment extends Fragment {
    private final String TAG=CurrencyListFragment.class.getSimpleName();
    private List<Rate> rates;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private CurrencyApplication application;
    private CurrencyDao currencyDao;
    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(BroadcastReceiver.class.getSimpleName(),"GOT MASSAGE");
            refreshList();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"Method onCreateView");
        application= (CurrencyApplication) getActivity().getApplication();
        currencyDao=application.getCurrencyDao();
        rates=new ArrayList<>();
        rates=currencyDao.getRates();
        return inflater.inflate(R.layout.list_fragment_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"Method onViewCreated");
        adapter=new RecyclerAdapter(rates);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"Method onResume");
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(GetDataService.REFRESH_ACTION));

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"Mathod onPause()");
        getActivity().unregisterReceiver(broadcastReceiver);

    }

    private void refreshList() {
        Log.d(TAG,"Method refreshList()");
        rates.clear();
        rates.addAll(currencyDao.getRates());
        adapter.notifyDataSetChanged();
    }
}
