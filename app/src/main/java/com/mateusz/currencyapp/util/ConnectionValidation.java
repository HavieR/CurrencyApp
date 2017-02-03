package com.mateusz.currencyapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mateusz on 02.02.2017.
 */
public class ConnectionValidation {

    public static boolean ifConnected(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isConnected();
    }
}
