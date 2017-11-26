package com.example.amank.greenspadeshistory.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amank.greenspadeshistory.R;
import com.example.amank.greenspadeshistory.models.Transaction;

import java.util.ArrayList;

/**
 * Created by amank on 26-11-2017.
 */

public class TransactionsListAdapter extends RecyclerView.Adapter<TransactionsListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Transaction> transactionDetails;

    public TransactionsListAdapter(Context context, ArrayList<Transaction> list){
        this.context = context;
        this.transactionDetails = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.transactions_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.transactionDate.setText(transactionDetails.get(position).getTransactionDate());
        if(transactionDetails.get(position).getMobileNumber() == null){
            holder.mobileNumber.setVisibility(View.GONE);
        } else
        holder.mobileNumber.setText(transactionDetails.get(position).getMobileNumber());
        if(transactionDetails.get(position).getName() == null){
            holder.name.setVisibility(View.GONE);
        } else
            holder.name.setText(transactionDetails.get(position).getName());
        holder.amountPaid.setText(transactionDetails.get(position).getAmountPaid());
        holder.ll_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator flip = ObjectAnimator.ofFloat(holder.ll_front, "rotationX", 0f, 90f);
                flip.setDuration(1500);
                flip.start();

                final Handler handler = new Handler();

                flip.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        holder.ll_front.setVisibility(View.GONE);
                        holder.ll_back.setVisibility(View.VISIBLE);
                        ObjectAnimator flip1 = ObjectAnimator.ofFloat(holder.ll_back, "rotationX", -90f, 0f);
                        flip1.setDuration(1500);
                        flip1.start();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, 100 );

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

            }
        });
        holder.ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator flip = ObjectAnimator.ofFloat(holder.ll_back, "rotationX", 0f, 90f);
                flip.setDuration(1500);
                flip.start();

                final Handler handler = new Handler();

                flip.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        holder.ll_back.setVisibility(View.GONE);
                        holder.ll_front.setVisibility(View.VISIBLE);
                        ObjectAnimator flip1 = ObjectAnimator.ofFloat(holder.ll_front, "rotationX", -90f, 0f);
                        flip1.setDuration(1500);
                        flip1.start();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, 100 );

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

            }
        });

        holder.totalTax.setText(transactionDetails.get(position).getTotalTax());
        holder.itemsCost.setText(transactionDetails.get(position).getItemsCost());
    }

    @Override
    public int getItemCount() {
        if(transactionDetails != null){
            return transactionDetails.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
            amountPaid = itemView.findViewById(R.id.tv_amount_paid);
            transactionDate = itemView.findViewById(R.id.tv_transaction_date);
            name = itemView.findViewById(R.id.tv_name);
            mobileNumber = itemView.findViewById(R.id.tv_mobile_number);
            ll_front = itemView.findViewById(R.id.ll_front);
            ll_back = itemView.findViewById(R.id.ll_back);
            itemsCost = itemView.findViewById(R.id.tv_items_cost);
            totalTax = itemView.findViewById(R.id.tv_total_tax);
        }

        LinearLayout ll_front;
        LinearLayout ll_back;
        TextView itemsCost;
        TextView totalTax;
        TextView amountPaid;
        TextView transactionDate;
        TextView name;
        TextView mobileNumber;
    }
}
