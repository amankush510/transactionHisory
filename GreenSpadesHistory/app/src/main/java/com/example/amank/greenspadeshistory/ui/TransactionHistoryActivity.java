package com.example.amank.greenspadeshistory.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amank.greenspadeshistory.Utils;
import com.example.amank.greenspadeshistory.constants.JsonParameterConstants;
import com.example.amank.greenspadeshistory.R;
import com.example.amank.greenspadeshistory.models.Transaction;
import com.example.amank.greenspadeshistory.adapters.TransactionsListAdapter;
import com.example.amank.greenspadeshistory.server.retrofit.RetrofitClient;
import com.example.amank.greenspadeshistory.server.ResponseParser;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionHistoryActivity extends AppCompatActivity {
    private String TAG = TransactionHistoryActivity.class.getSimpleName();

    private RecyclerView rv_transactions;
    private TextView tv_total_sale_count;
    private TextView tv_total_Sale_sum;
    private TextView tv_today_sale_sum;
    private TextView tv_today_sale_count;

    private TransactionsListAdapter adapter;
    private ArrayList<Transaction> data;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        init();
        initUI();
        initUIActions();
        if(Utils.isNetworkAvailable(this)) {
            getData();
        } else{
            Toast.makeText(this, "Please check your internet connection and try again!", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void init(){
        getSupportActionBar().setTitle("History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data = new ArrayList<>();
        adapter = new TransactionsListAdapter(this, data);

        dialog =  new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
    }

    private void initUI(){
        rv_transactions = findViewById(R.id.rv_transactions);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_transactions.setLayoutManager(mLayoutManager);
        rv_transactions.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        tv_today_sale_count = findViewById(R.id.tv_today_sale_count);
        tv_today_sale_sum = findViewById(R.id.tv_today_sale_sum);
        tv_total_sale_count = findViewById(R.id.tv_total_sale_count);
        tv_total_Sale_sum = findViewById(R.id.tv_total_sale_sum);
    }

    private void initUIActions(){
        rv_transactions.setAdapter(adapter);
    }

    private void getData(){
        Call<ResponseBody> call = RetrofitClient.getClient().getData();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ArrayList<Object> res = ResponseParser.parseResponse(response.body().string());
                    updateUI(res);
                } catch (Exception e){
                    Log.e(TAG,e.getMessage());
                    dialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.cancel();
                Toast.makeText(TransactionHistoryActivity.this, "Server is not available right now, please try again after sometime", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void updateUI(ArrayList<Object> res){
        data.addAll((ArrayList<Transaction>) res.get(1));
        HashMap<String, String> map = (HashMap) res.get(0);

        for(String key: map.keySet()){
            String value = map.get(key);
            if(key.equalsIgnoreCase(JsonParameterConstants.TODAY_SALE_COUNT)){
                if(value == null || value.equalsIgnoreCase("null")){
                    tv_today_sale_count.setText("0");
                } else
                tv_today_sale_count.setText(value);
            } else if(key.equalsIgnoreCase(JsonParameterConstants.TODAY_SALE_SUM)){
                if(value == null || value.equalsIgnoreCase("null")) {
                    tv_today_sale_sum.setText("0");
                } else
                    tv_today_sale_sum.setText(value);
            } else if(key.equalsIgnoreCase(JsonParameterConstants.TOTAL_SALE_COUNT)){
                if(value == null || value.equalsIgnoreCase("null")) {
                    tv_total_sale_count.setText("0");
                } else
                    tv_total_sale_count.setText(value);
            } else if(key.equalsIgnoreCase(JsonParameterConstants.TOTAL_SALE_SUM)){
                if(value == null || value.equalsIgnoreCase("null")) {
                    tv_total_Sale_sum.setText("0");
                } else
                    tv_total_Sale_sum.setText(value);
            }
        }
        adapter.notifyDataSetChanged();
        dialog.cancel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
                finish();
        }
        return true;
    }
}
