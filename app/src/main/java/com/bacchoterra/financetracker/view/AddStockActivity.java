package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.tools.CalculateTotalSpend;
import com.bacchoterra.financetracker.bottomsheet.HelpStockBottomSheet;
import com.bacchoterra.financetracker.tools.CheckStockFields;
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
    private EditText editStockName;
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
        checkAllFields();
    }

    private void init() {

        toolbar = findViewById(R.id.activity_add_stock_toolbar);
        editDate = findViewById(R.id.activity_add_stock_edit_date);
        editStockName = findViewById(R.id.activity_add_stock_edit_stock_name);
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

    private void checkAllFields() {

        CheckStockFields checkStockFields = new CheckStockFields(editDate, editPrice, editQuantity, editStockName, this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStockFields.isEveryFieldOk()) {
                    Toast.makeText(AddStockActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddStockActivity.this, "Erro", Toast.LENGTH_SHORT).show();
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