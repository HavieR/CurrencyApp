package com.mateusz.currencyapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mateusz.currencyapp.models.Rate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 02.02.2017.
 */
public class CurrencyDao {
    DBHelper dbHelper;
    private final String TAG=CurrencyDao.class.getSimpleName();

    public static final String TABLE_RATES_NAME="rates";
        public static final String RATE_CURRENCY="currency";
        public static final String RATE_CODE="code";
        public static final String RATE_MID="mid";

    public static final String TABLE_INFO_NAME="info";
        public static final String INFO_NUMBER="no";
        public static final String INFO_DATE="date";

    public CurrencyDao(Context context) {
        Log.d(TAG,"Constructor");
        dbHelper=new DBHelper(context);
    }

    private Rate mapCursorToRate(Cursor cursor){
        Log.d(TAG,"Method mapCursorToRate");
        int currencyColumnId=cursor.getColumnIndex(CurrencyDao.RATE_CURRENCY);
        int codeColumnId=cursor.getColumnIndex(CurrencyDao.RATE_CODE);
        int midColumnId=cursor.getColumnIndex(CurrencyDao.RATE_MID);
        Rate rate=new Rate();
        rate.setCurrency(cursor.getString(currencyColumnId));
        rate.setCode(cursor.getString(codeColumnId));
        rate.setMid(cursor.getDouble(midColumnId));
        return rate;
    }

    public List<Rate> getRates(){
        Log.d(TAG,"Method getRates");
        Cursor cursor=dbHelper.getReadableDatabase().rawQuery("SELECT * FROM "
                +CurrencyDao.TABLE_RATES_NAME,null);
        List<Rate> rates=new ArrayList<>();
        if (cursor!=null){
            Log.d(TAG,"Method getRates cursor is not null");
            while(cursor.moveToNext()){
                rates.add(mapCursorToRate(cursor));
            }
            dbHelper.close();
            return rates;
        }
        dbHelper.close();
        return null;
    }

    public void insertRate(Rate rate){
        Log.d(TAG,"Method insertRate");
        ContentValues contentValues=new ContentValues();
        contentValues.put(CurrencyDao.RATE_CURRENCY,rate.getCurrency());
        contentValues.put(CurrencyDao.RATE_CODE,rate.getCode());
        contentValues.put(CurrencyDao.RATE_MID,rate.getMid());
        dbHelper.getWritableDatabase().insertOrThrow(CurrencyDao.TABLE_RATES_NAME,null,contentValues);
        dbHelper.close();

    }
    public void insertListOfRates(List<Rate> rates){
        Log.d(TAG,"Method insertListOfRate");
        SQLiteDatabase writebleDB=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        for(Rate rate:rates) {
            contentValues.put(CurrencyDao.RATE_CURRENCY, rate.getCurrency());
            contentValues.put(CurrencyDao.RATE_CODE, rate.getCode());
            contentValues.put(CurrencyDao.RATE_MID, rate.getMid());
            writebleDB.insertOrThrow(CurrencyDao.TABLE_RATES_NAME, null, contentValues);
        }
        dbHelper.close();
    }

    public void insertInfo(String no, String date){
        Log.d(TAG,"Method insertInfo");
        ContentValues contentValues=new ContentValues();
        contentValues.put(CurrencyDao.INFO_NUMBER,no);
        contentValues.put(CurrencyDao.INFO_DATE,date);

        dbHelper.getWritableDatabase().insertOrThrow(CurrencyDao.TABLE_INFO_NAME,null,contentValues);
        dbHelper.close();
    }

    public String getInfoNumber(){
        Log.d(TAG,"Method getInfoNumber");
        String date;
        String query=String.format
                ("SELECT %s FROM %s",CurrencyDao.INFO_NUMBER,CurrencyDao.TABLE_INFO_NAME);
        Cursor cursor=dbHelper.getReadableDatabase().rawQuery(query,null);
        if(cursor!=null && cursor.getCount()!=0){
            Log.d(TAG,"Method get InfoNumber, cursor is not null and contain elements");
            cursor.moveToNext();
            int dateColumnId=cursor.getColumnIndex(CurrencyDao.INFO_NUMBER);
            date=cursor.getString(dateColumnId);
            dbHelper.close();
            return date;
        }
        return null;

    }
    public void clearTables(){
        Log.d(TAG,"Method clearTables");
        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        sqLiteDatabase.delete(CurrencyDao.TABLE_INFO_NAME,null,null);
        sqLiteDatabase.delete(CurrencyDao.TABLE_RATES_NAME,null,null);
        dbHelper.close();
    }
}
