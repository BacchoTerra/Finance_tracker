package com.bacchoterra.financetracker.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.view.AddStockActivity;
import com.bacchoterra.financetracker.view.StocksActivity;
import com.bacchoterra.financetracker.viewmodel.StockViewModel;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class StockAdapter extends ListAdapter<Stock, StockAdapter.MyViewHolder> {

    //Context
    Activity activity;

    //Viewmodel
    private StockViewModel viewModel;

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


            return oldItem.getStockName().equals(newItem.getStockName()) && oldItem.getExpectedTimeInvested().equals(newItem.getExpectedTimeInvested()) && oldItem.getFinalTimestamp() == newItem.getFinalTimestamp() && oldItem.getInitialPrice() == newItem.getInitialPrice() && oldItem.getInitialTimestamp() == newItem.getInitialTimestamp() && oldItem.getProfit() == newItem.getProfit() && oldItem.getQuantity() == newItem.getQuantity() && oldItem.getTechniqueUsed().equals(newItem.getTechniqueUsed()) && oldItem.getTotalSpent() == newItem.getTotalSpent();


        }
    };


    public StockAdapter(Activity activity, StockViewModel viewModel) {
        super(DIFF_UTIL);

        this.activity = activity;
        this.viewModel = viewModel;

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
        expandCardLayout(holder);
        checkCardView(holder);
        excludeOperation(holder,stock);
        finalizeOperation(holder,stock);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView baseCardView;

        TextView txtDate;
        TextView txtInitialPrice;
        TextView txtQuantity;
        TextView txtStockName;
        TextView txtTotalSpent;

        ImageView imageExpand;

        ConstraintLayout constraintLayoutExtra;
        TextView txtTechnique;
        TextView txtEstimatedTime;
        TextView txtExclude;

        Button btnFinalize;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            baseCardView = itemView.findViewById(R.id.row_stock_baseCardView);

            txtDate = itemView.findViewById(R.id.row_stock_txt_date);
            txtInitialPrice = itemView.findViewById(R.id.row_stock_txt_price);
            txtQuantity = itemView.findViewById(R.id.row_stock_txt_quantity);
            txtTotalSpent = itemView.findViewById(R.id.row_stock_txt_price_result);
            txtStockName = itemView.findViewById(R.id.row_stock_txt_sotck_name);

            imageExpand = itemView.findViewById(R.id.row_stock_image_expand);

            constraintLayoutExtra = itemView.findViewById(R.id.row_stock_constraintLayout_extra);
            txtTechnique = itemView.findViewById(R.id.row_stock_txt_technique_2);
            txtEstimatedTime = itemView.findViewById(R.id.row_stock_txt_estimated_time_2);
            txtExclude = itemView.findViewById(R.id.row_stock_txt_exclude);

            btnFinalize = itemView.findViewById(R.id.row_stock_txt_btn_finalize);


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
        holder.txtQuantity.setText(String.valueOf(stock.getQuantity()));

        String initialPrice = String.valueOf(stock.getInitialPrice()).replace('.',',');
        String totalSpend = String.valueOf(stock.getTotalSpent()).replace('.',',');

        holder.txtInitialPrice.setText(initialPrice);
        holder.txtTotalSpent.setText(totalSpend);

        if (stock.getTechniqueUsed().isEmpty()){
            holder.txtTechnique.setText(activity.getResources().getString(R.string.nenhuma_definida));
        }else {
            holder.txtTechnique.setText(stock.getTechniqueUsed());
        }

        if (stock.getExpectedTimeInvested().isEmpty()){
            holder.txtEstimatedTime.setText(activity.getResources().getString(R.string.tempo_indeterminado));
        }else {
            holder.txtEstimatedTime.setText(stock.getExpectedTimeInvested());
        }

        if (stock.isFinished()){

            holder.txtStockName.setText("Finalizado");

        }else {
            holder.txtStockName.setText(stock.getStockName());
        }

    }

    private void expandCardLayout(MyViewHolder holder){

        holder.imageExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(holder.baseCardView,new AutoTransition());
                holder.constraintLayoutExtra.setVisibility(holder.constraintLayoutExtra.getVisibility() == View.VISIBLE? View.GONE:View.VISIBLE);
            }
        });


    }

    private void checkCardView(MyViewHolder holder) {

        holder.baseCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.baseCardView.setChecked(!holder.baseCardView.isChecked());
                return true;
            }
        });

        holder.baseCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.baseCardView.isChecked()){
                    holder.baseCardView.setChecked(false);
                }

            }
        });


    }

    private void excludeOperation(MyViewHolder holder,Stock stock){

        holder.txtExclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog(stock);
            }
        });



    }

    private void showDeleteDialog(Stock stock) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getString(R.string.deletar_operaçao));
        builder.setMessage(activity.getResources().getString(R.string.delete_stock_dialog_message));
        builder.setCancelable(true);
        builder.setPositiveButton(activity.getResources().getString(R.string.deletar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                     viewModel.delete(stock);
            }
        }).setNegativeButton(activity.getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void finalizeOperation(MyViewHolder holder,Stock stock){

        holder.btnFinalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stock.setFinished(true);
                viewModel.update(stock);


            }
        });



    }
}
