package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.adapter.StockAdapter;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.tools.DialogHelper;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

public class ShowFinishedStockActivity extends AppCompatActivity implements View.OnClickListener{

    private View headerView;
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
    private Button btnExclude;

    //Model
    private Stock stock;

    //Formatter
    private DecimalFormat decimalFormat;
    DialogHelper dialogHelper;


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

        headerView = findViewById(R.id.activity_show_fin_stock_view_header);
        imageBack = findViewById(R.id.activity_show_fin_stock_image_back);
        imageBack.setOnClickListener(this);
        txtStockName = findViewById(R.id.activity_show_fin_stock_txt_stock_name);
        txtCorretora = findViewById(R.id.activity_show_fin_stock_txt_corretora);
        txtProfit = findViewById(R.id.activity_show_fin_stock_txt_profit);
        txtInitialDate = findViewById(R.id.activity_show_fin_stock_txt_initial_date);
        txtFinalDate = findViewById(R.id.activity_show_fin_stock_txt_final_date);
        txtPriceQuantity = findViewById(R.id.activity_show_fin_stock_txt_price_quantity);
        txtTotalSpent = findViewById(R.id.activity_show_fin_stock_txt_total_spent);
        txtTechnique = findViewById(R.id.activity_show_fin_stock_txt_technique_used);
        txtEstimatedTime = findViewById(R.id.activity_show_fin_stock_txt_estimated_time);
        btnExclude = findViewById(R.id.activity_show_fin_stock_btn_exclude);
        btnExclude.setOnClickListener(this);


    }

    private void retrieveStock() {

        if (getIntent().getExtras() != null) {

            stock = (Stock) getIntent().getExtras().get(StocksActivity.SHOW_FINISHED_STOCK_KEY);
        }


    }

    private void bindStockValues() {

        if (stock.getProfit() > 0){
            headerView.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.shape_header_view_profit,null));
        }else {
            headerView.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.shape_header_view_deficit,null));
        }

        txtStockName.setText(stock.getStockName());
        if (stock.getCorretora() != null) {
            txtCorretora.setText(stock.getCorretora());
        }
        txtProfit.setText(getString(R.string.money_symbol) + " " + decimalFormat.format(stock.getProfit()));
        txtInitialDate.setText(getFormattedDates(stock.getInitialTimestamp()));
        txtFinalDate.setText(getFormattedDates(stock.getFinalTimestamp()));

        txtPriceQuantity.setText(getPriceQuantity());
        txtTotalSpent.setText(getString(R.string.money_symbol) + " " + decimalFormat.format(stock.getTotalSpent()));

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

        return getString(R.string.money_symbol) + " " + decimalFormat.format(stock.getAveragePrice()) + ' ' +
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

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == imageBack.getId()){
            finish();
        }else if (id == btnExclude.getId()){

            if (dialogHelper == null){
                dialogHelper =new DialogHelper(ShowFinishedStockActivity.this);
            }

            dialogHelper.constructSimpleDialog(R.string.excluir_opera_o, R.string.permanet_action, R.string.excluir, null, true, new DialogHelper.OnBtnClickedListener() {
                @Override
                public void onPositive() {
                    Intent intent = new Intent();
                    intent.putExtra(StocksActivity.SHOW_FINISHED_STOCK_KEY,stock);
                    StocksActivity.option = StocksActivity.EXCLUDE_STOCK;
                    setResult(RESULT_OK,intent);
                    finish();
                }

                @Override
                public void onNegative() {

                }
            });
            dialogHelper.showDialog();

        }

    }
}