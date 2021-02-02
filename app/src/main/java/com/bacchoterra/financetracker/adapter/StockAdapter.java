package com.bacchoterra.financetracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

public class StockAdapter extends ListAdapter<Stock, RecyclerView.ViewHolder> {

    //date format
    private final Calendar calendar;

    //Context
    private final Activity activity;

    //Extras
    private final String moneySymbol;

    //ViewTypes
    public static final int STOCK_OPENED = 0;
    public static final int STOCK_CLOSED = 1;

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;

        switch (viewType) {
            case STOCK_OPENED:
                holder =  new MyViewHolderOpened(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stock_opened, parent, false));
                break;
            case STOCK_CLOSED:
                holder =  new MyViewHolderClosed(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stock_closed, parent, false));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Stock stock = getItem(position);

        switch (holder.getItemViewType()){
            case STOCK_OPENED:

                MyViewHolderOpened oHolder = (MyViewHolderOpened) holder;
                bindOpenedStock(stock,oHolder);
                break;

            case STOCK_CLOSED:
                MyViewHolderClosed cHolder = (MyViewHolderClosed) holder;
                bindClosedStock(stock,cHolder);



        }

    }

    @Override
    public int getItemViewType(int position) {

        return getItem(position).isFinished() ? STOCK_CLOSED : STOCK_OPENED;

    }

    public static class MyViewHolderOpened extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txtDay;
        TextView txtMonth;
        TextView txtAveragePrice;
        TextView txtQuantity;
        TextView txtCorretora;
        TextView txtStockName;


        public MyViewHolderOpened(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.row_stock_opened_card);
            txtStockName = itemView.findViewById(R.id.row_stock_opened_txt_stock_name);
            txtDay = itemView.findViewById(R.id.row_stock_opened_txt_day);
            txtMonth = itemView.findViewById(R.id.row_stock_opened_txt_month);
            txtQuantity = itemView.findViewById(R.id.row_stock_opened_txt_quantity);
            txtCorretora = itemView.findViewById(R.id.row_stock_opened_txt_corretora);
            txtAveragePrice = itemView.findViewById(R.id.row_stock_opened_txt_price);


        }
    }

    public static class MyViewHolderClosed extends RecyclerView.ViewHolder {

        CardView cardView;
        ConstraintLayout layoutBackground;
        ImageView imageDirection;
        TextView txtInitialDay;
        TextView txtInitialMonth;
        TextView txtFinalDay;
        TextView txtFinalMonth;
        TextView txtProfit;
        TextView txtQuantity;
        TextView txtCorretora;
        TextView txtStockName;


        public MyViewHolderClosed(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.row_stock_closed_card);
            layoutBackground = itemView.findViewById(R.id.row_stock_closed_layout_background);
            imageDirection = itemView.findViewById(R.id.row_stock_closed_image_direction);
            txtStockName = itemView.findViewById(R.id.row_stock_closed_txt_stock_name);
            txtInitialDay = itemView.findViewById(R.id.row_stock_closed_txt_initial_day);
            txtInitialMonth = itemView.findViewById(R.id.row_stock_closed_txt_initial_month);
            txtFinalDay = itemView.findViewById(R.id.row_stock_closed_txt_final_day);
            txtFinalMonth = itemView.findViewById(R.id.row_stock_closed_txt_final_month);
            txtQuantity = itemView.findViewById(R.id.row_stock_closed_txt_quantity);
            txtCorretora = itemView.findViewById(R.id.row_stock_closed_txt_corretora);
            txtProfit = itemView.findViewById(R.id.row_stock_closed_txt_profit);


        }
    }

    private void bindOpenedStock(Stock stock, MyViewHolderOpened holder) {

        buildOpenedRow(holder, stock);

    }

    private void buildOpenedRow(MyViewHolderOpened holder, Stock stock) {

        holder.txtStockName.setText(stock.getStockName());
        holder.txtCorretora.setText(stock.getCorretora());

        String quantity = stock.getQuantity() + " " + activity.getString(R.string.papeis);

        holder.txtQuantity.setText(quantity);

        String averagePrice = moneySymbol + new DecimalFormat("0.00").format(stock.getAveragePrice()).replace('.', ',');

        holder.txtAveragePrice.setText(averagePrice);
        holder.txtDay.setText(getDay(stock.getInitialTimestamp()));
        holder.txtMonth.setText(getMonth(stock.getInitialTimestamp()));


    }

    private void bindClosedStock(Stock stock, MyViewHolderClosed holder) {

        buildClosedRow(holder, stock);

    }

    private void buildClosedRow(MyViewHolderClosed holder, Stock stock) {
        holder.txtStockName.setText(stock.getStockName());
        holder.txtCorretora.setText(stock.getCorretora());

        String quantity = stock.getQuantity() + " " + activity.getString(R.string.papeis);

        holder.txtQuantity.setText(quantity);

        String averagePrice = moneySymbol + new DecimalFormat("0.00").format(stock.getProfit()).replace('.', ',');

        holder.txtProfit.setText(averagePrice);
        holder.txtInitialDay.setText(getDay(stock.getInitialTimestamp()));
        holder.txtInitialMonth.setText(getMonth(stock.getInitialTimestamp()));
        holder.txtFinalDay.setText(getDay(stock.getFinalTimestamp()));
        holder.txtFinalMonth.setText(getMonth(stock.getFinalTimestamp()));


        if (stock.getProfit() > 0){
            holder.layoutBackground.setBackground(ResourcesCompat.getDrawable(activity.getResources(),R.drawable.shape_gradient_card_profit,null));
            holder.imageDirection.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(),R.drawable.ic_round_trending_up_24,null));

        }else {
            holder.layoutBackground.setBackground(ResourcesCompat.getDrawable(activity.getResources(),R.drawable.shape_gradient_card_deficit,null));
            holder.imageDirection.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(),R.drawable.ic_round_trending_down_24,null));
        }

    }

    private String getDay(long timestamp) {

        calendar.setTimeInMillis(timestamp);

        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (day < 10) {
            return "0" + day;
        } else {
            return String.valueOf(day);
        }

    }

    private String getMonth(long timestamp) {
        calendar.setTimeInMillis(timestamp);

        return calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());

    }

    public Stock getStock(int position) {

        return getItem(position);

    }
}
