package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.tools.CalculateTotalSpend;
import com.bacchoterra.financetracker.bottomsheet.HelpStockBottomSheet;
import com.bacchoterra.financetracker.tools.CheckStockFields;
import com.santalu.maskara.widget.MaskEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddStockActivity extends AppCompatActivity {

    //Layout views
    private Toolbar toolbar;
    private MaskEditText editDate;
    private EditText editPrice;
    private EditText editQuantity;
    private EditText editStockName;
    private EditText editTechnique;
    private EditText editEstimatedTime;
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
        sendStockObjectToStockActivity();
    }

    private void init() {

        toolbar = findViewById(R.id.activity_add_stock_toolbar);
        editDate = findViewById(R.id.activity_add_stock_edit_date);
        editStockName = findViewById(R.id.activity_add_stock_edit_stock_name);
        editPrice = findViewById(R.id.activity_add_stock_edit_price);
        editQuantity = findViewById(R.id.activity_add_stock_edit_quantity);
        editTechnique = findViewById(R.id.activity_add_stock_edit_technique);
        editEstimatedTime = findViewById(R.id.activity_add_stock_edit_estimated_time);
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

    private void showHelpBottomSheet() {

        txtHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetHelp == null) {
                    bottomSheetHelp = new HelpStockBottomSheet();
                }

                bottomSheetHelp.show(getSupportFragmentManager(), null);
            }
        });


    }

    /* it gets a Calendar instance to set the current date into the editText..the watcher is added before the setText()
     * to detect the change...i had to use a postDelayed to ensure that the listener could be attached before the
     * setText();
     */
    private void setEditDateToCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        editDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editDate.setText(sdf.format(calendar.getTimeInMillis()));
            }
        }, 200);


    }

    private void calculateTotal() {

        new CalculateTotalSpend(editPrice, editQuantity, txtTotalPrice);

    }

    private Stock createStock() {

        Stock stock = new Stock();
        stock.setStockName(editStockName.getText().toString().toUpperCase());
        stock.setAveragePrice(Float.parseFloat(editPrice.getText().toString()));
        stock.setQuantity(Integer.parseInt(editQuantity.getText().toString()));
        stock.setTotalSpent(Float.parseFloat(txtTotalPrice.getText().toString().replace(',', '.')));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            assert editDate.getText() != null;
            Date date = sdf.parse(editDate.getText().toString());
            assert date != null;
            stock.setInitialTimestamp(date.getTime());

        } catch (ParseException e) {

            e.printStackTrace();

        }

        if (!editTechnique.getText().toString().isEmpty()){
            stock.setTechniqueUsed(editTechnique.getText().toString());
        }

        if (!editEstimatedTime.getText().toString().isEmpty()){
            stock.setExpectedTimeInvested(editEstimatedTime.getText().toString());
        }


        return stock;


    }

    private void sendStockObjectToStockActivity() {


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckStockFields checkStockFields = new CheckStockFields(editDate,editPrice,editQuantity,editStockName,AddStockActivity.this);

                if (checkStockFields.isEveryFieldOk()){

                    Intent intent = new Intent();
                    intent.putExtra(StocksActivity.ADD_STOCK_KEY,createStock());
                    setResult(RESULT_OK,intent);
                    finish();

                }
            }
        });


    }

    @Override
    public boolean onNavigateUp() {

        finish();

        return true;
    }

}