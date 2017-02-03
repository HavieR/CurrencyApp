package com.mateusz.currencyapp.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.mateusz.currencyapp.CurrencyApplication;
import com.mateusz.currencyapp.R;
import com.mateusz.currencyapp.activities.MainActivity;
import com.mateusz.currencyapp.api.MyWebService;
import com.mateusz.currencyapp.database.CurrencyDao;
import com.mateusz.currencyapp.models.Rate;
import com.mateusz.currencyapp.models.RatesInfo;
import com.mateusz.currencyapp.util.ConnectionValidation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Mateusz on 02.02.2017.
 */
public class GetDataService extends IntentService{
    private static final String TAG=GetDataService.class.getSimpleName();
    public static final int NOTIFICATION_ID=1;
    public static final String REFRESH_ACTION="com.example.mateusz.currency.REFRESH_ACTION";
    public static final String SHOW_SNACKBAR="show_snackbar";
    Handler mHandler=new Handler();
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */

    public GetDataService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG,"Method onHandleIntent");
        if(ConnectionValidation.ifConnected(getApplicationContext())) {

            final CurrencyDao currencyDao = ((CurrencyApplication) getApplication()).getCurrencyDao();
            Retrofit retrofit = ((CurrencyApplication) getApplication()).getRetrofit();
            MyWebService myWebService = retrofit.create(MyWebService.class);
            Call<List<RatesInfo>> call = myWebService.getJson();

            try {
                List<RatesInfo> response = call.execute().body();
                Log.d(TAG, "Rrtrofit Method onRespone");
                String numberDownloaded = response.get(0).getNo();
                String numberInDb = currencyDao.getInfoNumber();
                if (numberInDb != null) {
                    Log.d(TAG, "numberInDb is not null, next check if equal");
                    if (!(numberDownloaded.equals(numberInDb))) {
                        Log.d(TAG, "numberDownloaded and numberInDb are equal");
                        updateDataBase(response, numberDownloaded, currencyDao);
                        return;
                    }
                } else {
                    Log.d(TAG, "numberInDb is null, Database is empty");
                    updateDataBase(response, numberDownloaded, currencyDao);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "New Thread");
                    Toast.makeText(getApplicationContext(), "There are no new rates", Toast.LENGTH_SHORT).show();
                }
            });

        }else {
            Log.d(TAG, "New Thread");
            Intent snackBarIntent=new Intent(SHOW_SNACKBAR);
            sendBroadcast(snackBarIntent);
        }
    }

    private void updateDataBase(List<RatesInfo> response, String numberDownloaded, CurrencyDao currencyDao) {
        List<Rate> rates = new ArrayList<>();
        rates.addAll(response.get(0).getRates());
        currencyDao.clearTables();
        currencyDao.insertListOfRates(rates);
        String date = response.get(0).getEffectiveDate();
        currencyDao.insertInfo(numberDownloaded, date);
        Intent intent = new Intent(REFRESH_ACTION);
        sendBroadcast(intent);
        showNotification();
    }

    private void showNotification() {
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder=new Notification.Builder(getApplicationContext());

        builder.setContentTitle("News");
        builder.setContentText("New rates has appeared");
        builder.setSmallIcon(R.drawable.arrive);
        builder.setAutoCancel(true);

        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setContentIntent(pendingIntent);

        Notification notification=builder.getNotification();

        manager.notify(NOTIFICATION_ID,notification);
    }
}
