package com.bacchoterra.financetracker.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.adapter.StockAdapter;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.viewmodel.StockViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class StocksActivity extends AppCompatActivity {

    //Layout Components
    private Toolbar toolbar;
    private FloatingActionButton fabAddStock;

    //RecyclerView and ViewModel
    private RecyclerView recyclerView;
    private StockAdapter stockAdapter;
    private StockViewModel viewModel;

    //ActivityResult code
    public static final int ADD_STOCK = 100;
    public static final String ADD_STOCK_KEY = "add_stock_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);
        init();
        initToolbar();
        initClickListener();
        initViewModel();
        initRecyclerView();
    }

    private void initViewModel() {

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(StockViewModel.class);

        viewModel.getAllStock().observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                stockAdapter.submitList(stocks);
            }
        });

    }

    private void init() {

        toolbar = findViewById(R.id.activity_stock_toolbar);
        fabAddStock = findViewById(R.id.activity_stock_fab_add);
        recyclerView = findViewById(R.id.activity_stock_recycler_view);

    }

    private void initClickListener() {

        fabAddStock.setOnClickListener(view ->
                startActivityForResult(new Intent(StocksActivity.this, AddStockActivity.class), ADD_STOCK));

    }

    private void initToolbar() {


        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24);


    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        stockAdapter = new StockAdapter(this,viewModel);
        recyclerView.setAdapter(stockAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toobar_search_icon, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.menu_toobar_search_icon) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {

        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_STOCK && resultCode == RESULT_OK) {


            assert data != null && data.getExtras() != null;
            Stock stock = (Stock) data.getExtras().get(ADD_STOCK_KEY);
            viewModel.insert(stock);

        }

    }
}