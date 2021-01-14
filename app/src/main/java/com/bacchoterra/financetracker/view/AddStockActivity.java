package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.bacchoterra.financetracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddStockActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtDate,txtDataChosen;
    private EditText editPrice;
    private EditText editQuantity;
    private TextView txtTotalPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        init();
        initToolbar();

    }

    private void init() {

        toolbar = findViewById(R.id.activity_add_stock_toolbar);
        txtDate = findViewById(R.id.activity_add_stock_txt_date);
        txtDataChosen = findViewById(R.id.activity_add_stock_txt_choose_date);
        editPrice = findViewById(R.id.activity_add_stock_edit_price);
        editQuantity = findViewById(R.id.activity_add_stock_edit_quantity);
        txtTotalPrice = findViewById(R.id.activity_add_stock_txt_total_price);

    }

    private void initToolbar(){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24);
        getSupportActionBar().setTitle(null);

    }

    @Override
    public boolean onNavigateUp() {

        finish();

        return true;
    }
}