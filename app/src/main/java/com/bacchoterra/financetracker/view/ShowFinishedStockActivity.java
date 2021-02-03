package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

public class ShowFinishedStockActivity extends AppCompatActivity {

    private ImageView imageBack;
    private TextView txtStockName;
    private TextView txtCorretora;
    private TextView txtProfit;
    private TextView txtInitialDate;
    private TextView txtFinalDate;
    private TextView txtPriceQuantity;
    private TextView txtTotalSpent;
    private TextView txtTechnique;
    private TextView txtEstimatedTime;

    //Model
    private Stock stock;

    //Formatter
    private DecimalFormat decimalFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_finished_stock);
        decimalFormat = new DecimalFormat("0.00");

        init();
        retrieveStock();
        bindStockValues();
    }

    private void init() {

        imageBack = findViewById(R.id.activity_show_fin_stock_image_back);
        txtStockName = findViewById(R.id.activity_show_fin_stock_txt_stock_name);
        txtCorretora = findViewById(R.id.activity_show_fin_stock_txt_corretora);
        txtProfit = findViewById(R.id.activity_show_fin_stock_txt_profit);
        txtInitialDate = findViewById(R.id.activity_show_fin_stock_txt_initial_date);
        txtFinalDate = findViewById(R.id.activity_show_fin_stock_txt_final_date);
        txtPriceQuantity = findViewById(R.id.activity_show_fin_stock_txt_price_quantity);
        txtTotalSpent = findViewById(R.id.activity_show_fin_stock_txt_total_spent);
        txtTechnique = findViewById(R.id.activity_show_fin_stock_txt_technique_used);
        txtEstimatedTime = findViewById(R.id.activity_show_fin_stock_txt_estimated_time);


    }

    private void retrieveStock() {

        if (getIntent().getExtras() != null) {

            stock = (Stock) getIntent().getExtras().get(StocksActivity.SHOW_FINISHED_STOCK_KEY);
        }


    }

    private void bindStockValues() {

        txtStockName.setText(stock.getStockName());
        if (stock.getCorretora() != null) {
            txtCorretora.setText(stock.getCorretora());
        }
        txtProfit.setText(getString(R.string.money_symbol) + " " + decimalFormat.format(stock.getProfit()));
        txtInitialDate.setText(getFormattedDates(stock.getInitialTimestamp()));
        txtFinalDate.setText(getFormattedDates(stock.getFinalTimestamp()));

        txtPriceQuantity.setText(getPriceQuantity());
        txtTotalSpent.setText(decimalFormat.format(stock.getTotalSpent()));

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

    private String getPriceQuantity() {

        return decimalFormat.format(stock.getAveragePrice()) + ' ' +
                '(' +
                stock.getQuantity() +
                ')';


    }

    private String getFormattedDates(long timestamp) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String sDay = "";

        if (day < 10){
            sDay = "0" + calendar.get(Calendar.DAY_OF_MONTH);
        }else {
            sDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        }


        return sDay + "\n" + calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());


    }
}