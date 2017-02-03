package com.mateusz.currencyapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 02.02.2017.
 */
public class RatesInfo {
    @SerializedName("table")
    @Expose
    private String table;
    @SerializedName("no")
    @Expose
    private String no;
    @SerializedName("effectiveDate")
    @Expose
    private String effectiveDate;
    @SerializedName("rates")
    @Expose
    private List<Rate> rates = new ArrayList<Rate>();

    /**
     *
     * @return
     * The table
     */
    public String getTable() {
        return table;
    }

    /**
     *
     * @param table
     * The table
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     *
     * @return
     * The no
     */
    public String getNo() {
        return no;
    }

    /**
     *
     * @param no
     * The no
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     *
     * @return
     * The effectiveDate
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     *
     * @param effectiveDate
     * The effectiveDate
     */
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     *
     * @return
     * The rates
     */
    public List<Rate> getRates() {
        return rates;
    }

    /**
     *
     * @param rates
     * The rates
     */
    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
