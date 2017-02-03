package com.mateusz.currencyapp.api;

import com.mateusz.currencyapp.models.RatesInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Mateusz on 02.02.2017.
 */
public interface MyWebService {
    @GET("/api/exchangerates/tables/A/?format=json")
    Call<List<RatesInfo>> getJson();

}
