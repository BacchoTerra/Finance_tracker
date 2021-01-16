package com.bacchoterra.financetracker.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StockAdapter extends ListAdapter<Stock, StockAdapter.MyViewHolder> {

    //date format
    private Calendar calendar;
    private SimpleDateFormat sdf;


    private static final DiffUtil.ItemCallback<Stock> DIFF_UTIL = new DiffUtil.ItemCallback<Stock>() {
        @Override
        public boolean areItemsTheSame(@NonNull Stock oldItem, @NonNull Stock newItem) {

            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Stock oldItem, @NonNull Stock newItem) {

            /*
            return oldItem.getStockName().equals(newItem.getStockName()) && oldItem.getExpectedTimeInvested().equals(newItem.getExpectedTimeInvested()) && oldItem.getFinalTimestamp() == newItem.getFinalTimestamp() && oldItem.getInitialPrice() == newItem.getInitialPrice() && oldItem.getInitialTimestamp() == newItem.getInitialTimestamp() && oldItem.getProfit() == newItem.getProfit() && oldItem.getQuantity() == newItem.getQuantity() && oldItem.getTechniqueUsed().equals(newItem.getTechniqueUsed()) && oldItem.getTotalSpent() == newItem.getTotalSpent();

             */

            return oldItem.getStockName().equals(newItem.getStockName()) && oldItem.getInitialPrice() == newItem.getInitialPrice() && oldItem.getInitialTimestamp() == newItem.getInitialTimestamp() && oldItem.getQuantity() == newItem.getQuantity() && oldItem.getTotalSpent() == newItem.getTotalSpent();
        }
    };


    public StockAdapter() {
        super(DIFF_UTIL);
        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stock,parent,false);

        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Stock stock = getItem(position);

        bindStock(stock,holder);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate;
        TextView txtInitialPrice;
        TextView txtQuantity;
        TextView txtStockName;
        TextView txtTotalSpent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.row_stock_txt_date);
            txtInitialPrice = itemView.findViewById(R.id.row_stock_txt_price);
            txtQuantity = itemView.findViewById(R.id.row_stock_txt_quantity);
            txtTotalSpent = itemView.findViewById(R.id.row_stock_txt_price_result);
            txtStockName = itemView.findViewById(R.id.row_stock_txt_sotck_name);


        }
    }

    private String getDate(Stock stock){

        long timeInMillis = stock.getInitialTimestamp();
        calendar.setTimeInMillis(timeInMillis);

        return sdf.format(calendar.getTimeInMillis());



    }

    private void bindStock(Stock stock,MyViewHolder holder){
        holder.txtStockName.setText(stock.getStockName());
        holder.txtDate.setText(getDate(stock));
        holder.txtInitialPrice.setText(String.valueOf(stock.getInitialPrice()));
        holder.txtTotalSpent.setText(String.valueOf(stock.getTotalSpent()));
        holder.txtQuantity.setText(String.valueOf(stock.getQuantity()));
    }
}
