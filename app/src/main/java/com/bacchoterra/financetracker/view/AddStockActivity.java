package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.tools.CalculateTotalSpend;
import com.bacchoterra.financetracker.tools.HelpStockBottomSheet;
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
    private TextView txtHelp;
    private Button btnAdd;

    //Help BottomSheet
    private HelpStockBottomSheet bottomSheetHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        init();
        createToolbar();
        setEditDateToCurrentDate();
        calculateTotal();
        showHelpBottomSheet();
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
        btnAdd = findViewById(R.id.activity_add_stock_btn_add);
        txtHelp = findViewById(R.id.activity_add_stock_txt_help);

    }

    private void createToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24);
        getSupportActionBar().setTitle(null);

    }

    private void showHelpBottomSheet(){

        txtHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetHelp == null){
                    bottomSheetHelp = new HelpStockBottomSheet();
                }

                bottomSheetHelp.show(getSupportFragmentManager(),null);
            }
        });


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