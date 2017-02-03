package com.mateusz.currencyapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mateusz on 02.02.2017.
 */
public class Rate {
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("mid")
    @Expose
    private double mid;

    /**
     *
     * @return
     * The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     * The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     * The code
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The mid
     */
    public double getMid() {
        return mid;
    }

    /**
     *
     * @param mid
     * The mid
     */
    public void setMid(double mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return this.code +" "+this.currency+ " "+this.mid;
    }
}
