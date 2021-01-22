package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ShowStockActivity extends AppCompatActivity {

    //Layout
    private Toolbar toolbar;
    private TextView txtStockName;
    private TextView txtStockState;
    private TextView txtTotalPrice;
    private TextView txtQuantity;
    private TextView txtAveragePrice;
    private TextView txtTechnique;
    private TextView txtEstimatedTime;
    private TextView txtFirstDate;

    //Model
    Stock stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stock);
        init();
        initToolbar();
        retrieveStock();
    }

    private void init() {

        toolbar = findViewById(R.id.activity_show_stock_toolbar);
        txtStockName = findViewById(R.id.activity_show_stock_txt_stock_name);
        txtStockState = findViewById(R.id.activity_show_stock_txt_stock_state);
        txtTotalPrice = findViewById(R.id.activity_show_stock_txt_total_spent);
        txtQuantity = findViewById(R.id.activity_show_stock_txt_quantity);
        txtAveragePrice = findViewById(R.id.activity_show_stock_txt_average_price);
        txtTechnique = findViewById(R.id.activity_show_stock_txt_technique);
        txtEstimatedTime = findViewById(R.id.activity_show_stock_txt_estimated_time);
        txtFirstDate = findViewById(R.id.activity_show_stock_txt_first_buy);


    }

    private void initToolbar() {

        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void retrieveStock() {

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        stock = (Stock) bundle.get(StocksActivity.SHOW_STOCK_KEY);

        bindStock(stock);


    }

    private void bindStock(Stock stock) {

        String stockName = stock.getStockName();
        String totalPrice = getString(R.string.money_symbol) + " " + stock.getTotalSpent();
        String averagePrice = " " + getString(R.string.money_symbol) + " " + stock.getInitialPrice();
        String technique = stock.getTechniqueUsed();
        String estimatedTime = stock.getExpectedTimeInvested();
        String firstDate = " " + stock.getInitialTimestamp();
        String quantity = String.valueOf(stock.getQuantity());

        txtStockName.setText(stockName);

        if (stock.isFinished()) {
            txtStockState.setText(getString(R.string.operacao_finalizada));
        } else {
            txtStockState.setText(getString(R.string.operacao_aberta));
        }
        txtTotalPrice.append(totalPrice);

        txtAveragePrice.append(averagePrice);

        if (!technique.isEmpty()){
            txtTechnique.append(technique);
        }else {
            txtTechnique.append(getString(R.string.nenhuma_definida));
        }

        if (!estimatedTime.isEmpty()){
            txtEstimatedTime.append(estimatedTime);
        }else {
            txtEstimatedTime.append(" " + getString(R.string.tempo_indeterminado));
        }

        txtFirstDate.append(" "+ formatDate(stock.getInitialTimestamp()));

        txtQuantity.setText(quantity);

    }

    private String formatDate(long timestamp){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(calendar.getTime());



    }
}