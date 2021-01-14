package com.bacchoterra.financetracker.view;

import  androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bacchoterra.financetracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StocksActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fabAddStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks);
        init();
        initToolbar();
        initClickListener();
    }

    private void init() {

        toolbar = findViewById(R.id.activity_stock_toolbar);
        fabAddStock = findViewById(R.id.activity_stock_fab_add);

    }

    private void initClickListener(){

        fabAddStock.setOnClickListener(view-> startActivity(new Intent(StocksActivity.this,AddStockActivity.class)));

    }

    private void initToolbar() {


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24);


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
}