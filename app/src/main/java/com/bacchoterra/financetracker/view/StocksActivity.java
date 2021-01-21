package com.bacchoterra.financetracker.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.adapter.StockAdapter;
import com.bacchoterra.financetracker.adapter.StockAdapter2;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.viewmodel.StockViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class StocksActivity extends AppCompatActivity {

    //Layout Components
    private Toolbar toolbar;
    private FloatingActionButton fabAddStock;
    private MaterialSearchView searchView;
    private ImageView imageBackgroung;
    private TextView txtNoItem;

    //RecyclerView and ViewModel
    private RecyclerView recyclerView;
    //private StockAdapter stockAdapter;
    private StockAdapter2 stockAdapter;
    private StockViewModel viewModel;
    private LiveData<List<Stock>> selectedStocks;

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
        getItemsFromViewModel(StockViewModel.SELECT_ALL, stockAdapter,null);
        setSearchViewListener();
    }

    private void init() {

        toolbar = findViewById(R.id.activity_stock_toolbar);
        fabAddStock = findViewById(R.id.activity_stock_fab_add);
        recyclerView = findViewById(R.id.activity_stock_recycler_view);
        searchView = findViewById(R.id.activity_stock_search_view);

        imageBackgroung = findViewById(R.id.activity_stock_image_background);
        txtNoItem = findViewById(R.id.activity_stock_txt_no_item);

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

    private void initViewModel() {

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(StockViewModel.class);
    }

    private void getItemsFromViewModel(int options, StockAdapter2 adapter,String query) {


        selectedStocks = viewModel.getAllStock(options,query);

        selectedStocks.observe(this, new Observer<List<Stock>>() {
            @Override
            public void onChanged(List<Stock> stocks) {
                adapter.submitList(stocks);
                recyclerView.scrollToPosition(0);

                if (stocks.size() == 0){
                    imageBackgroung.setVisibility(View.VISIBLE);
                    txtNoItem.setVisibility(View.VISIBLE);
                }else {
                    imageBackgroung.setVisibility(View.GONE);
                    txtNoItem.setVisibility(View.GONE);
                }

            }
        });

    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        //stockAdapter = new StockAdapter(this, viewModel);
        stockAdapter = new StockAdapter2(this);
        recyclerView.setAdapter(stockAdapter);


    }

    private void setSearchViewListener(){

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getItemsFromViewModel(StockViewModel.SELECT_ALL_BY_NAME,stockAdapter,query.toUpperCase() + "%");

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() >0){
                    getItemsFromViewModel(StockViewModel.SELECT_ALL_BY_NAME,stockAdapter,newText.toUpperCase() + "%");
                    return true;
                }else {
                    getItemsFromViewModel(StockViewModel.SELECT_ALL,stockAdapter,null);
                }

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toobar_search_icon, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_toolbar_search_icon);
        searchView.setMenuItem(menuItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_toolbar_search_icon:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_toolbar_all_stock:
                getItemsFromViewModel(StockViewModel.SELECT_ALL, stockAdapter,null);
                break;

            case R.id.menu_toolbar_total_price:
                getItemsFromViewModel(StockViewModel.SELECT_ALL_BY_TOTAL_SPENT,stockAdapter,null);
                break;

            case R.id.menu_toolbar_stock_price:
                getItemsFromViewModel(StockViewModel.SELECT_ALL_BY_INITIAL_PRICE,stockAdapter,null);
                break;

            case R.id.menu_toolbar_finalized:
                getItemsFromViewModel(StockViewModel.SELECT_ALL_FINISHED, stockAdapter,null);
                break;

            case R.id.menu_toolbar_opened:
                getItemsFromViewModel(StockViewModel.SELECT_ALL_OPENED,stockAdapter,null);


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

    @Override
    public void onBackPressed() {

        if (searchView.isSearchOpen()){
            searchView.closeSearch();
        }else {
            super.onBackPressed();
        }

    }
}