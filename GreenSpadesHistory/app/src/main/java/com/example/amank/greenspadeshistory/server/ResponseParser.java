package com.example.amank.greenspadeshistory.server;

import android.util.Log;

import com.example.amank.greenspadeshistory.constants.JsonParameterConstants;
import com.example.amank.greenspadeshistory.models.Transaction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by amank on 26-11-2017.
 */

public class ResponseParser {
    public static ArrayList<Object> parseResponse(String response){
        ArrayList<Object> details = new ArrayList<>();
        JSONObject data;
        try{
            data = new JSONObject(response);

            JSONArray totals = data.getJSONArray(JsonParameterConstants.KPIS);

            HashMap<String, String> values = new HashMap<>();
            for(int i = 0; i < totals.length(); i++){
                values.put(totals.getJSONObject(i).getString(JsonParameterConstants.TEXT_1), totals.getJSONObject(i).getString(JsonParameterConstants.VALUE));
            }

            ArrayList<Transaction> transactions = new ArrayList<>();

            JSONArray trans = data.getJSONArray(JsonParameterConstants.TRANSACTIONS);

            for(int i = 0; i < trans.length(); i++){
                JSONObject obj = trans.getJSONObject(i);
                String name = null;
                String mobile = null;
                if(obj.getJSONObject(JsonParameterConstants.CUSTOMER).has(JsonParameterConstants.NAME)) {
                    name = obj.getJSONObject(JsonParameterConstants.CUSTOMER).getString(JsonParameterConstants.NAME);
                }
                String amountPaid = obj.getString(JsonParameterConstants.AMOUNT_PAID);
                String transactionDate = obj.getString(JsonParameterConstants.TRANSACTION_DATE);
                String itemsCost = obj.getString(JsonParameterConstants.ITEMS_COST);
                String totalTax = obj.getString(JsonParameterConstants.TOTAL_TAX);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf1 = new SimpleDateFormat("MMM dd, yyyy");
                transactionDate = sdf1.format(sdf.parse(transactionDate.substring(0,10)));

                if(obj.getJSONObject(JsonParameterConstants.CUSTOMER).has(JsonParameterConstants.MOBILE_NUMBER)) {
                    mobile = obj.getJSONObject(JsonParameterConstants.CUSTOMER).getString(JsonParameterConstants.MOBILE_NUMBER);
                }
                Transaction transaction = new Transaction(name, amountPaid, mobile, transactionDate, itemsCost, totalTax);
                transactions.add(transaction);
            }

            details.add(values);
            details.add(transactions);
        } catch (Exception e){
Log.e("ResponseParser", e.getMessage());
        }
        return details;
    }
}
