package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ShowStockActivity extends AppCompatActivity implements View.OnClickListener {

    //Layout components
    private Toolbar toolbar;
    private TextView txtStockName;
    private TextView txtInitialDate;
    private TextView txtAveragePrice;
    private TextView txtVariation;
    private TextView txtCurrentValue;
    private TextView txtQuantity;
    private TextView txtTotalSpent;
    private TextView txtProfit;
    private TextView txtTechnique;
    private TextView txtEstimatedTime;

    //Model
    private Stock stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stock);
        init();
        initToolbar();
        retrieveStock();
        bindStock();

    }

    private void init() {
        toolbar = findViewById(R.id.activity_show_stock_toolbar);
        txtStockName = findViewById(R.id.activity_show_stock_txt_stock_name);
        txtInitialDate = findViewById(R.id.activity_show_stock_txt_first_date);
        txtAveragePrice = findViewById(R.id.activity_show_stock_txt_average_price);
        txtVariation = findViewById(R.id.activity_show_stock_txt_variation);
        txtCurrentValue = findViewById(R.id.activity_show_stock_txt_current_value);
        txtQuantity = findViewById(R.id.activity_show_stock_txt_quantity);
        txtTotalSpent = findViewById(R.id.activity_show_stock_txt_total_spent);
        txtProfit = findViewById(R.id.activity_show_stock_txt_profit);
        txtTechnique = findViewById(R.id.activity_show_stock_txt_technique);
        txtEstimatedTime = findViewById(R.id.activity_show_stock_txt_expected_time);

    }

    private void initToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void retrieveStock() {

        stock = (Stock) getIntent().getExtras().get(StocksActivity.SHOW_STOCK_KEY);

    }

    private void bindStock() {

        txtStockName.setText(stock.getStockName());
        txtInitialDate.setText(parseDate());
        txtAveragePrice.setText(parseMoney(stock.getAveragePrice()));

        txtVariation.setText("----");
        txtCurrentValue.setText("----");

        txtQuantity.setText(String.valueOf(stock.getQuantity()));
        txtTotalSpent.setText(calculateTotalSpent());
        txtProfit.setText("----");

        if (!stock.getTechniqueUsed().isEmpty()) {
            txtTechnique.setText(stock.getTechniqueUsed());
        } else {
            txtTechnique.setText(getString(R.string.item_nao_definido));
        }

        if (!stock.getExpectedTimeInvested().isEmpty()) {
            txtEstimatedTime.setText(stock.getExpectedTimeInvested());
        } else {
            txtEstimatedTime.setText(getString(R.string.item_nao_definido));
        }


    }

    private String calculateTotalSpent() {

        return getString(R.string.money_symbol) + " " + String.valueOf(stock.getAveragePrice() * stock.getQuantity());

    }

    private String parseDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        return sdf.format(stock.getInitialTimestamp());

    }

    private String parseMoney(float value) {

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        return getString(R.string.money_symbol) + " " + decimalFormat.format(value);
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();


    }


}