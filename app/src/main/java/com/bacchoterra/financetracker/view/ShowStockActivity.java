package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.model.StockInformation;
import com.bacchoterra.financetracker.tools.FetchStockInformation;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ShowStockActivity extends AppCompatActivity implements View.OnClickListener {

    //Layout components
    private Toolbar toolbar;
    private TextView txtStockName;
    private TextView txtInitialDate;
    private TextView txtMarketStatus;
    private TextView txtAveragePrice;
    private TextView txtVariation;
    private TextView txtCurrentValue;
    private TextView txtQuantity;
    private TextView txtTotalSpent;
    private TextView txtProfit;
    private TextView txtDayVariation;
    private TextView txtMaxDay;
    private TextView txtMinDay;

    //Model
    private Stock stock;

    //Calendar
    Calendar calendar;

    //REST
    public StockInformation information;
    public FetchStockInformation fetchStockInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stock);
        init();
        initToolbar();
        retrieveStock();
        bindStock();
        checkMarketStatus();
        fetchStock(stock.getStockName());

    }

    private void init() {
        toolbar = findViewById(R.id.activity_show_stock_toolbar);
        txtStockName = findViewById(R.id.activity_show_stock_txt_stock_name);
        txtInitialDate = findViewById(R.id.activity_show_stock_txt_first_date);
        txtMarketStatus = findViewById(R.id.activity_show_stock_txt_market_status);
        txtAveragePrice = findViewById(R.id.activity_show_stock_txt_average_price);
        txtVariation = findViewById(R.id.activity_show_stock_txt_total_variation);
        txtCurrentValue = findViewById(R.id.activity_show_stock_txt_current_value);
        txtQuantity = findViewById(R.id.activity_show_stock_txt_quantity);
        txtTotalSpent = findViewById(R.id.activity_show_stock_txt_total_spent);
        txtProfit = findViewById(R.id.activity_show_stock_txt_profit);
        txtDayVariation = findViewById(R.id.activity_show_stock_txt_day_variation);
        txtMaxDay = findViewById(R.id.activity_show_stock_txt_max_day);
        txtMinDay = findViewById(R.id.activity_show_stock_txt_min_day);


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


    }

    private void checkMarketStatus() {

        if (calendar == null) {
            calendar = Calendar.getInstance();
        }

        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 10 && hour <= 18 && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            txtMarketStatus.setText(R.string.mercado_aberto);
            txtMarketStatus.setTextColor(ResourcesCompat.getColor(getResources(), R.color.profit_color, null));
        } else {
            txtMarketStatus.setText(R.string.mercado_fechado);
            txtMarketStatus.setTextColor(ResourcesCompat.getColor(getResources(), R.color.deficit_color, null));
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

    private void fetchStock(String stockName) {

        fetchStockInformation = new FetchStockInformation(this);

        fetchStockInformation.makeCall(stockName, new FetchStockInformation.OnStockFetched() {
            @Override
            public void onSuccess(StockInformation stockInformation) {

                bindApiValues(stockInformation);

            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("Rola", "onFailure: " + t.getMessage());
            }

            @Override
            public void onInternetFailure() {
                Log.i("Rola", "onInternetFailure: " + "no net");
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void bindApiValues(StockInformation information) {

        float currentValue = information.getData().getValor();
        float max = information.getData().getMaximo_dia();
        float min = information.getData().getMinimo_dia();
        float dayVariation = information.getData().getPorcentagem_variacao_dia();

        txtCurrentValue.setText(getString(R.string.money_symbol) + " " + currentValue);
        txtDayVariation.setText(dayVariation + " %");
        txtMaxDay.setText(getString(R.string.money_symbol) + " " + max);
        txtMinDay.setText(getString(R.string.money_symbol) + " " + min);

        txtVariation.setText(new DecimalFormat("0.00").format(getTotalVariation(currentValue)) + " %");

        if (getTotalVariation(currentValue) > 0) {
            txtVariation.setTextColor(ResourcesCompat.getColor(getResources(), R.color.profit_color, null));
        } else {
            txtVariation.setTextColor(ResourcesCompat.getColor(getResources(), R.color.deficit_color, null));
        }

        //TODO: txtProfit needs to be calculated yet..
        //TODO: add a progress above TextView fields tha has dependence on api values..and set something to them if api doesn't work

    }

    private float getTotalVariation(float currentValue) {

        return (currentValue * 100) / stock.getAveragePrice() - 100;

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();


    }


}