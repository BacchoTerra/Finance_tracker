package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.adapter.StockInformationAdapter;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.model.StockInformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ShowStockActivity extends AppCompatActivity {

    //Layout
    private Toolbar toolbar;
    private TextView txtStockName;
    private TextView txtTotalPrice;
    private TextView txtStockState;
    private RecyclerView recyclerView;

    //For RecyclerView
    private List<StockInformation> list = new ArrayList<>();
    private StockInformationAdapter adapter;


    //Model
    Stock stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_stock);
        init();
        initToolbar();
        retrieveStock();
        initRecyclerView();
    }

    private void init() {

        toolbar = findViewById(R.id.activity_show_stock_toolbar);
        txtStockName = toolbar.findViewById(R.id.activity_show_stock_txt_stock_name);
        txtTotalPrice = findViewById(R.id.activity_show_stock_txt_total_invested_value);
        txtStockState = findViewById(R.id.activity_show_stock_txt_state);
        recyclerView = findViewById(R.id.activity_show_stock_recycler_view);


    }

    private void initToolbar() {

        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setElevation(0);

    }

    private void retrieveStock() {

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        stock = (Stock) bundle.get(StocksActivity.SHOW_STOCK_KEY);

        bindStock(stock);


    }

    private void bindStock(Stock stock) {

        String totalPrice = getString(R.string.money_symbol) + " " + stock.getTotalSpent();

        txtStockName.setText(stock.getStockName());
        txtTotalPrice.setText(totalPrice);

        if (!stock.isFinished()){
            txtStockState.setText(getString(R.string.operacao_aberta));
            txtStockState.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_baseline_lock_24,0);
        }else {
            txtStockState.setText(String.valueOf(stock.getProfit()));
            txtStockState.setCompoundDrawables(null,null, null,null);
        }

    }

    private String formatDate(long timestamp){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(calendar.getTime());



    }

    private void initRecyclerView(){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        createInformationalObjects();
        adapter = new StockInformationAdapter(this,list);
        recyclerView.setAdapter(adapter);

    }

    private void createInformationalObjects(){

        String stringAveragePrice = getString(R.string.money_symbol) + " " + stock.getTotalSpent();

        StockInformation averagePrice = new StockInformation(getString(R.string.preco_medio),stringAveragePrice,R.drawable.ic_baseline_bar_chart_24);

        StockInformation quantity = new StockInformation(getString(R.string.quantidade),String.valueOf(stock.getQuantity()),R.drawable.ic_baseline_keyboard_capslock_24);

        StockInformation firstDate = new StockInformation(getString(R.string.primeira_compra),formatDate(stock.getInitialTimestamp()),R.drawable.ic_baseline_calendar_today_24);

        StockInformation technique = new StockInformation(getString(R.string.tecnica_utilizada),String.valueOf(stock.getTechniqueUsed()),R.drawable.ic_baseline_insights_24);

        StockInformation estimatedTime = new StockInformation(getString(R.string.tempo_estimado),stock.getExpectedTimeInvested(),R.drawable.ic_baseline_linear_scale_24);

        list.add(averagePrice);
        list.add(quantity);
        list.add(firstDate);
        list.add(technique);
        list.add(estimatedTime);

    }
}