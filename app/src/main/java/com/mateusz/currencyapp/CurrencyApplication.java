package com.mateusz.currencyapp;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.mateusz.currencyapp.database.CurrencyDao;
import com.mateusz.currencyapp.services.GetDataService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mateusz on 02.02.2017.
 */
public class CurrencyApplication extends Application {
    public static final String TAG=CurrencyApplication.class.getSimpleName();
    private final String URL="http://api.nbp.pl/";
    private Retrofit retrofit;
    private CurrencyDao currencyDao;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Method onCreate");

        retrofit=new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        currencyDao=new CurrencyDao(getApplicationContext());
        Intent intent=new Intent(this, GetDataService.class);
        startService(intent);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public CurrencyDao getCurrencyDao() {
        return currencyDao;
    }

}
