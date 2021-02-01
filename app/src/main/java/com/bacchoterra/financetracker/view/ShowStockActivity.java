package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.model.StockInformation;
import com.bacchoterra.financetracker.tools.FetchStockInformation;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ShowStockActivity extends AppCompatActivity implements View.OnClickListener {

    //Layout components
    private ConstraintLayout rootLayout;
    private ImageView imageBack;
    private TextView txtStockName;
    private TextView txtStockCorretora;
    private TextView txtInitialDate;
    private TextView txtTechnique;
    private TextView txtExpectedTime;
    private TextView txtMarketStatus;
    private Button btnAdd;
    private Button btnSell;
    private Button btnFinalize;
    private TextView txtAveragePrice;
    private TextView txtVariation;
    private TextView txtCurrentValue;
    private TextView txtQuantity;
    private TextView txtTotalSpent;
    private TextView txtProfit;
    private TextView txtDayVariation;
    private TextView txtMaxDay;
    private TextView txtMinDay;
    private SpinKitView progressCurrentValue;
    private SpinKitView progressTotalVariation;
    private SpinKitView progressDayVariation;
    private SpinKitView progressMaxDay;
    private SpinKitView progressMinDay;

    //Model
    private Stock stock;

    //Calendar
    private Calendar calendar;

    //Formaters
    private DecimalFormat decimalFormat;

    //REST
    public FetchStockInformation fetchStockInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stock);
        decimalFormat = new DecimalFormat("0.00");
        init();
        retrieveStock();
        bindStock();
        checkMarketStatus();
        fetchStock(stock.getStockName());

    }

    private void init() {
        rootLayout = findViewById(R.id.activity_show_stock_root_layout);
        imageBack = findViewById(R.id.activity_show_stock_image_back);
        imageBack.setOnClickListener(this);
        txtStockName = findViewById(R.id.activity_show_stock_txt_stock_name);
        txtStockCorretora = findViewById(R.id.activity_show_stock_txt_stock_corretora);
        btnAdd = findViewById(R.id.activity_show_stock_btn_add);
        btnSell = findViewById(R.id.activity_show_stock_btn_sell);
        btnFinalize = findViewById(R.id.activity_show_stock_btn_finalize);
        txtInitialDate = findViewById(R.id.activity_show_stock_txt_first_date);
        txtTechnique = findViewById(R.id.activity_show_stock_txt_technique);
        txtExpectedTime = findViewById(R.id.activity_show_stock_txt_time_expected);
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
        progressCurrentValue = findViewById(R.id.activity_show_stock_progress_current_value);
        progressTotalVariation = findViewById(R.id.activity_show_stock_progress_total_variation);
        progressDayVariation = findViewById(R.id.activity_show_stock_progress_day_var);
        progressMaxDay = findViewById(R.id.activity_show_stock_progress_max_day);
        progressMinDay = findViewById(R.id.activity_show_stock_progress_min_day);


    }

    private void retrieveStock() {

        stock = (Stock) getIntent().getExtras().get(StocksActivity.SHOW_STOCK_KEY);

    }

    private void bindStock() {

        txtStockName.setText(stock.getStockName());
        txtInitialDate.setText(parseDate());
        txtAveragePrice.setText(parseMoney(stock.getAveragePrice()));

        if (!stock.getTechniqueUsed().isEmpty()){
            txtTechnique.setText(stock.getTechniqueUsed());
        }else {
            txtTechnique.setText(getString(R.string.item_nao_definido));
        }

        if (!stock.getExpectedTimeInvested().isEmpty()){
            txtExpectedTime.setText(stock.getExpectedTimeInvested());
        }else {
            txtExpectedTime.setText(getString(R.string.item_nao_definido));
        }

        txtQuantity.setText(String.valueOf(stock.getQuantity()));
        txtTotalSpent.setText(calculateTotalSpent());

        if (stock.getCorretora() != null){
            txtStockCorretora.setText(stock.getCorretora());
        }else {
            txtStockCorretora.setVisibility(View.GONE);
        }


    }

    private void checkMarketStatus() {

        if (calendar == null) {
            calendar = Calendar.getInstance();
        }

        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 10 && hour <= 18 && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            txtMarketStatus.setText(R.string.mercado_aberto);
            txtMarketStatus.setTextColor(ResourcesCompat.getColor(getResources(), R.color.opened_market_color, null));
        } else {
            txtMarketStatus.setText(R.string.mercado_fechado);
            txtMarketStatus.setTextColor(ResourcesCompat.getColor(getResources(), R.color.close_market_color, null));
        }

    }

    private String calculateTotalSpent() {

        return getString(R.string.money_symbol) + " " + decimalFormat.format(stock.getAveragePrice() * stock.getQuantity());

    }

    private float calculateProfit(float currentPrice){

        return (currentPrice * stock.getQuantity()) - (stock.getAveragePrice() * stock.getQuantity());


    }

    private String parseDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        return sdf.format(stock.getInitialTimestamp());

    }

    private String parseMoney(float value) {

        return getString(R.string.money_symbol) + " " + decimalFormat.format(value);
    }

    private void fetchStock(String stockName) {

        if (fetchStockInformation == null) {
            fetchStockInformation = new FetchStockInformation(this);
        }

        fetchStockInformation.makeCall(stockName, new FetchStockInformation.OnStockFetched() {
            @Override
            public void onSuccess(StockInformation stockInformation) {

                bindApiValues(stockInformation);

            }

            @Override
            public void onIntrinsicFailure(Throwable t) {
                showInternetSnackBar();
                Log.i("RetroError", "onIntrinsicFailure: " + t.getMessage() + " /// " + t.getClass());
                bindApiValues(null);
            }

            @Override
            public void onBackEndFailure(int code) {


                if (code == 400) {
                    showErrorSnackBar(R.string.ativo_nao_encontrado);
                } else {
                    showErrorSnackBar(R.string.erro_desconhecido);
                }

                bindApiValues(null);

            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void bindApiValues(StockInformation information) {

        if (information != null) {
            float currentValue = information.getData().getValor();
            float max = information.getData().getMaximo_dia();
            float min = information.getData().getMinimo_dia();
            float dayVariation = information.getData().getPorcentagem_variacao_dia();

            txtCurrentValue.setText(getString(R.string.money_symbol) + " " + currentValue);
            txtDayVariation.setText(dayVariation + " %");
            txtMaxDay.setText(getString(R.string.money_symbol) + " " + max);
            txtMinDay.setText(getString(R.string.money_symbol) + " " + min);

            txtVariation.setText(decimalFormat.format(getTotalVariation(currentValue)) + " %");

            txtProfit.setText(decimalFormat.format(calculateProfit(information.getData().getValor())));

            if (getTotalVariation(currentValue) > 0) {
                txtVariation.setTextColor(ResourcesCompat.getColor(getResources(), R.color.profit_color, null));
            } else {
                txtVariation.setTextColor(ResourcesCompat.getColor(getResources(), R.color.deficit_color, null));
            }
        } else {
            txtCurrentValue.setText("---");
            txtDayVariation.setText("---");
            txtMaxDay.setText("---");
            txtMinDay.setText("---");
            txtVariation.setText("---");
            txtProfit.setText("----");

        }

        handleProgressIndicators();


    }

    private float getTotalVariation(float currentValue) {

        return (currentValue * 100) / stock.getAveragePrice() - 100;

    }

    private void showInternetSnackBar() {

        Snackbar snackbar = Snackbar.make(rootLayout, R.string.sem_internet, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.tentar_novamente, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchStock(stock.getStockName());
            }
        });
        snackbar.show();


    }

    private void showErrorSnackBar(int message) {

        Snackbar snackbar = Snackbar.make(rootLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    private void handleProgressIndicators() {

        progressCurrentValue.setVisibility(View.GONE);
        progressTotalVariation.setVisibility(View.GONE);
        progressDayVariation.setVisibility(View.GONE);
        progressMaxDay.setVisibility(View.GONE);
        progressMinDay.setVisibility(View.GONE);


    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == imageBack.getId()){

            finish();

        }


    }


}