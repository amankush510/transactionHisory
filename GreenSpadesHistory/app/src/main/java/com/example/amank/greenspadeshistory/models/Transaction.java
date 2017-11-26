package com.example.amank.greenspadeshistory.models;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by amank on 26-11-2017.
 */

public class Transaction {
    private String name;
    private String amountPaid;
    private String mobileNumber;
    private String transactionDate;
    private String itemsCost;
    private String totalTax;

    public Transaction(String name, String amountPaid, String mobileNumber, String transactionDate, String itemsCost, String totalTax) {
        this.name = name;
        this.amountPaid = amountPaid;
        this.mobileNumber = mobileNumber;
        this.transactionDate = transactionDate;
        this.itemsCost = itemsCost;
        this.totalTax = totalTax;
    }

    public String getName() {
        return name;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getItemsCost() {
        return itemsCost;
    }

    public String getTotalTax() {
        return totalTax;
    }
}
