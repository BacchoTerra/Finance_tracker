package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.tools.CalculateTotalSpend;
import com.santalu.maskara.Mask;
import com.santalu.maskara.widget.MaskEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddStockActivity extends AppCompatActivity {

    //Layout views
    private Toolbar toolbar;
    private MaskEditText editDate;
    private EditText editPrice;
    private EditText editQuantity;
    private TextView txtTotalPrice;

    //Price calculation
    float price;
    float quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        init();
        createToolbar();
        setEditDateToCurrentDate();
        calculateTotal();
    }

    private void calculateTotal() {

        new CalculateTotalSpend(editPrice,editQuantity,txtTotalPrice);

    }

    private void init() {

        toolbar = findViewById(R.id.activity_add_stock_toolbar);
        editDate = findViewById(R.id.activity_add_stock_edit_date);
        editPrice = findViewById(R.id.activity_add_stock_edit_price);
        editQuantity = findViewById(R.id.activity_add_stock_edit_quantity);
        txtTotalPrice = findViewById(R.id.activity_add_stock_txt_total_price_result);

    }

    private void createToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24);
        getSupportActionBar().setTitle(null);

    }

    private void setEditDateToCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        editDate.setText(sdf.format(calendar.getTimeInMillis()));

    }

    @Override
    public boolean onNavigateUp() {

        finish();

        return true;
    }

}