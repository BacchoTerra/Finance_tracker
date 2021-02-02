package com.bacchoterra.financetracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class StockAdapter extends ListAdapter<Stock, StockAdapter.MyViewHolder> {

    //date format
    private final Calendar calendar;

    //Context
    private final Activity activity;

    //Extras
    private final String moneySymbol;
    private int adapterCount =0;

    public static final DiffUtil.ItemCallback<Stock> DIFF_UTIL = new DiffUtil.ItemCallback<Stock>() {
        @Override
        public boolean areItemsTheSame(@NonNull Stock oldItem, @NonNull Stock newItem) {


            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Stock oldItem, @NonNull Stock newItem) {
            return oldItem.getStockName().equals(newItem.getStockName()) && oldItem.getExpectedTimeInvested().equals(newItem.getExpectedTimeInvested()) && oldItem.getFinalTimestamp() == newItem.getFinalTimestamp() && oldItem.getAveragePrice() == newItem.getAveragePrice() && oldItem.getInitialTimestamp() == newItem.getInitialTimestamp() && oldItem.getProfit() == newItem.getProfit() && oldItem.getQuantity() == newItem.getQuantity() && oldItem.getTechniqueUsed().equals(newItem.getTechniqueUsed()) && oldItem.getTotalSpent() == newItem.getTotalSpent();
        }
    };


    public StockAdapter(Activity activity) {
        super(DIFF_UTIL);
        calendar = Calendar.getInstance();
        this.activity = activity;
        moneySymbol = activity.getString(R.string.money_symbol) + " ";
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stock, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Stock stock = getItem(position);

        bindStock(stock, holder);
        changeCardColor(holder);


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txtDay;
        TextView txtMonth;
        TextView txtAveragePrice;
        TextView txtQuantity;
        TextView txtCorretora;
        TextView txtStockName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.row_stock_card);
            txtStockName = itemView.findViewById(R.id.row_stock_txt_stock_name);
            txtDay = itemView.findViewById(R.id.row_stock_txt_day);
            txtMonth = itemView.findViewById(R.id.row_stock_txt_month);
            txtQuantity = itemView.findViewById(R.id.row_stock_txt_quantity);
            txtCorretora = itemView.findViewById(R.id.row_stock_txt_corretora);
            txtAveragePrice = itemView.findViewById(R.id.row_stock_txt_price);


        }
    }

    private void bindStock(Stock stock, StockAdapter.MyViewHolder holder) {

        buildRow(holder, stock, stock.isFinished());

    }

    private void buildRow(MyViewHolder holder, Stock stock, boolean finished) {

        holder.txtStockName.setText(stock.getStockName());
        holder.txtCorretora.setText(stock.getCorretora());

        String quantity = stock.getQuantity() + " " + activity.getString(R.string.papeis);

        holder.txtQuantity.setText(quantity);

        String averagePrice = moneySymbol + new DecimalFormat("0.00").format(stock.getAveragePrice()).replace('.', ',');

        holder.txtAveragePrice.setText(averagePrice);
        holder.txtDay.setText(getDay(stock));
        holder.txtMonth.setText(getMonth(stock));


    }

    private String getDay(Stock stock) {

        calendar.setTimeInMillis(stock.getInitialTimestamp());

        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (day < 10) {
            return "0" + day;
        } else {
            return String.valueOf(day);
        }

    }

    private String getMonth(Stock stock) {
        calendar.setTimeInMillis(stock.getInitialTimestamp());

        return calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());

    }

    private void changeCardColor(MyViewHolder holder) {

        if (adapterCount == 0){
            holder.cardView.setCardBackgroundColor(ResourcesCompat.getColor(activity.getResources(),R.color.card_row_stock_background_1,null));
            adapterCount ++;
        }else if (adapterCount == 1){
            holder.cardView.setCardBackgroundColor(ResourcesCompat.getColor(activity.getResources(),R.color.card_row_stock_background_2,null));
            adapterCount ++;
        }else {
            holder.cardView.setCardBackgroundColor(ResourcesCompat.getColor(activity.getResources(),R.color.card_row_stock_background_3,null));
            adapterCount = 0;
        }


    }

    public Stock getStock(int position) {

        return getItem(position);

    }
}
