package com.bacchoterra.financetracker.tools;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.santalu.maskara.widget.MaskEditText;

public class CheckStockFields {

    //Context
    private Context c;

    //Layout components
    private MaskEditText editDate;
    private EditText editPrice;
    private EditText editQuantity;
    private EditText editStockName;

    public CheckStockFields(MaskEditText editDate, EditText editPrice, EditText editQuantity, EditText editStockName, Context c) {
        this.editDate = editDate;
        this.editPrice = editPrice;
        this.editQuantity = editQuantity;
        this.editStockName = editStockName;
        this.c = c;
    }


    public boolean isEveryFieldOk() {

        boolean dateOk = editDate.getMasked().length() == 10;
        Log.i("dateNotOk  ", editDate.getMasked() + " / " + editDate.getMasked().length());
        boolean priceOk = !editPrice.getText().toString().isEmpty();
        boolean quantityOk = editQuantity.getText().toString().length() >= 1;
        boolean stockNameOk = editStockName.getText().toString().length() >= 3;

        if (dateOk) {
            if (priceOk) {
                if (quantityOk) {
                    if (stockNameOk) {
                        Toast.makeText(c, "sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        editStockName.setError("Adicione uma sigla válida");
                    }

                } else {
                    editQuantity.setError("Adicione uma quantidade válida");
                }

            } else {
                editPrice.setError("Use o ponto apenas para separar os centavos");
            }
        } else {
            editDate.setError("");
        }

        return dateOk && priceOk && quantityOk && stockNameOk;


    }

}
