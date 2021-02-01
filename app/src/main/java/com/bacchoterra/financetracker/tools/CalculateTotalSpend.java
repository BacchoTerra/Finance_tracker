package com.bacchoterra.financetracker.tools;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CalculateTotalSpend {

    private final EditText editPrice;
    private final EditText editQuantity;
    private final TextView txtTotalPrice;
    private float totalSpend;

    //Float format
    private DecimalFormat decimalFormat;

    public CalculateTotalSpend(EditText editPrice, EditText editQuantity, TextView txtTotalPrice) {
        this.editPrice = editPrice;
        this.editQuantity = editQuantity;
        this.txtTotalPrice = txtTotalPrice;
        decimalFormat = new DecimalFormat("0.00");
        retrieveValues();
    }


    private void retrieveValues(){


        editPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 txtTotalPrice.setText(decimalFormat.format(calculate(editPrice,editQuantity)));

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
                 txtTotalPrice.setText(decimalFormat.format(calculate(editQuantity,editPrice)));


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

}

    private float calculate(EditText textWatcherOwner,EditText secondWritten) {

        String textFromOwner = textWatcherOwner.getText().toString();

        if (!secondWritten.getText().toString().isEmpty() && textFromOwner.length() >0) {

            float value1 = Float.parseFloat(textFromOwner);
            float value2 = Float.parseFloat(secondWritten.getText().toString());

            totalSpend = value1 * value2;

        }

        if (textFromOwner.length() == 0){

            totalSpend = 0;

        }

        return totalSpend;

    }

}
