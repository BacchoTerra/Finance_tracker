package com.bacchoterra.financetracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.StockInformation;

import java.util.List;

public class StockInformationAdapter extends RecyclerView.Adapter<StockInformationAdapter.MyViewHolder> {

    private final List<StockInformation> list;
    private final Activity activity;

    public StockInformationAdapter(Activity activity,List<StockInformation> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stock_information,parent,false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        StockInformation stockInformation = list.get(position);

        binDRows(stockInformation,holder);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView txtTitle;
        TextView txtContent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.row_stock_information_imageView);
            txtTitle = itemView.findViewById(R.id.row_stock_information_txtTitle);
            txtContent = itemView.findViewById(R.id.row_stock_information_txt_content);

        }
    }


    private void binDRows(StockInformation stockInformation,MyViewHolder holder){

        holder.txtTitle.setText(stockInformation.getStockInfoTitle());

        if (stockInformation.getStockInfoContent().isEmpty()){
            holder.txtContent.setText(activity.getString(R.string.item_nao_definido));
        }else {
            holder.txtContent.setText(stockInformation.getStockInfoContent());
        }
        holder.imageView.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(),stockInformation.getInfoDrawable(),null));



    }
}
