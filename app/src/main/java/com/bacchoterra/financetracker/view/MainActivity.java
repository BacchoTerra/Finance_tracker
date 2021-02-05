package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.viewmodel.StockViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Layout Components
    private TextView txtTotalMoney;
    private TextView txtIbov;
    private TextView txtProfit;
    private TextView txtTotalOpenedStocks;
    private Button btnSeeStock;

    //ViewModel
    private StockViewModel viewModel;

    //Numbers
    private int openedQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initViewModel();
        getTotalSpent();
        getTotalProfit();
        getOpenedStocks();

    }

    private void init() {

        txtTotalMoney = findViewById(R.id.activity_main_txt_total_money);
        txtIbov = findViewById(R.id.activity_main_txt_ibov_variation);
        txtProfit = findViewById(R.id.activity_main_txt_profit);
        txtTotalOpenedStocks = findViewById(R.id.activity_main_txt_total_opened_stocks);
        btnSeeStock = findViewById(R.id.activity_main_txt_btn_see_stocks);
        btnSeeStock.setOnClickListener(this);

    }

    private void initViewModel() {

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(StockViewModel.class);

    }

    private void getTotalSpent() {


        viewModel.getAllAveragePrices().observe(this, new Observer<List<Float>>() {
            @Override
            public void onChanged(List<Float> floats) {

                float totalSpent = 0;

                if (floats.size() > 0){
                    for (float price : floats) {

                        totalSpent += price;
                        txtTotalMoney.setText(getString(R.string.money_symbol) + " " + price);

                    }

                }else {
                    txtTotalMoney.setText("---");
                }



            }
        });

    }

    private void getTotalProfit() {

        viewModel.getAllProfit().observe(this, new Observer<List<Float>>() {
            @Override
            public void onChanged(List<Float> floats) {

                float profit = 0;

                if (floats.size() > 0){

                    for (float prof:floats){

                        profit +=prof;
                        txtProfit.setText(getString(R.string.money_symbol) + " " + profit);

                    }
                }else {
                    txtProfit.setText("---");
                }

            }
        });
    }


    private void getOpenedStocks(){
        viewModel.getAllStock(StockViewModel.SELECT_ALL_OPENED,null).observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {

                txtTotalOpenedStocks.setText(String.valueOf(stocks.size()));

            }
        });
    }

    @Override
    public void onClick(View view) {

        startActivity(new Intent(this, StocksActivity.class));

    }
}