package com.mateusz.currencyapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mateusz on 02.02.2017.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="currency.db";
    public static final int VERSION=1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String table1=String.format
                ("CREATE TABLE %s (_ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT,%s TEXT,%s FLOAT)",
                        CurrencyDao.TABLE_RATES_NAME,CurrencyDao.RATE_CURRENCY,CurrencyDao.RATE_CODE,
                        CurrencyDao.RATE_MID);
        Log.d("CREATE FIRST TABLE",table1);
        db.execSQL(table1);


        String table2=String.format
                ("CREATE TABLE %s (_ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT)",
                        CurrencyDao.TABLE_INFO_NAME,CurrencyDao.INFO_NUMBER,
                        CurrencyDao.INFO_DATE);
        Log.d("CREATE SECOND TABLE",table2);
        db.execSQL(table2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CurrencyDao.TABLE_RATES_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CurrencyDao.TABLE_INFO_NAME);
        onCreate(db);

    }
}
