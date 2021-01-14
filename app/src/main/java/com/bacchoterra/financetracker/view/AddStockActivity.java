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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddStockActivity extends AppCompatActivity {

    //Layout views
    private Toolbar toolbar;
    private EditText editDate;
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

    //23renato77

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

    private void calculateTotal() {

        editPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!editQuantity.getText().toString().isEmpty() && charSequence.length() >0) {

                    price = Float.parseFloat(charSequence.toString());
                    quantity = Float.parseFloat(editQuantity.getText().toString());

                    txtTotalPrice.setText(String.valueOf(price * quantity));

                }

                if (charSequence.length() == 0){

                    txtTotalPrice.setText(null);

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!editPrice.getText().toString().isEmpty() && charSequence.length() >0) {

                    quantity = Float.parseFloat(charSequence.toString());
                    price = Float.parseFloat(editPrice.getText().toString());

                    txtTotalPrice.setText(String.valueOf(price * quantity));

                }

                if (charSequence.length() == 0){

                    txtTotalPrice.setText(null);

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public boolean onNavigateUp() {

        finish();

        return true;
    }

}