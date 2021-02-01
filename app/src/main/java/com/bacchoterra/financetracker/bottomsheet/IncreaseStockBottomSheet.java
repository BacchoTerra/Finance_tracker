package com.bacchoterra.financetracker.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.tools.CalculateTotalSpend;
import com.bacchoterra.financetracker.tools.StockCalculations;
import com.bacchoterra.financetracker.view.ShowStockActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;

public class IncreaseStockBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    //Layout components
    private EditText editPrice;
    private EditText editQuantity;
    private Chip chip10;
    private Chip chip50;
    private Chip chip100;
    private Button btnAdd;

    //Model
    private Stock stock;

    //dynamic quantity
    private int dynamicQuantity = 0;

    //Listener
    private OnIncreaseListener mListener;


    @Override
    public void onAttach(@NonNull Context context) {

        try {
            mListener = (OnIncreaseListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "Mus implement OnIncreaseListener");
        }

        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sheet_add_stock, container, false);
        init(view);
        retrieveStock();
        handleDynamicIncrease();

        return view;
    }

    private void init(View view) {

        editPrice = view.findViewById(R.id.sheet_add_stock_edit_price);
        editQuantity = view.findViewById(R.id.sheet_add_stock_edit_quantity);
        btnAdd = view.findViewById(R.id.sheet_add_stock_btn_add);
        btnAdd.setOnClickListener(this);
        chip10 = view.findViewById(R.id.sheet_add_stock_chip_10);
        chip10.setOnClickListener(this);
        chip50 = view.findViewById(R.id.sheet_add_stock_chip_50);
        chip50.setOnClickListener(this);
        chip100 = view.findViewById(R.id.sheet_add_stock_chip_100);
        chip100.setOnClickListener(this);

    }

    private void retrieveStock() {
        if (getArguments() != null) {
            stock = (Stock) getArguments().get(ShowStockActivity.BOTTOM_SHEET_ARGS_KEY);
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == chip10.getId()) {

            increaseDynamicQuantity(10);

        } else if (id == chip50.getId()) {
            increaseDynamicQuantity(50);

        } else if (id == chip100.getId()) {
            increaseDynamicQuantity(100);

        } else if (id == btnAdd.getId()) {

            handleMainButtonClick();


        }

    }

    private void handleMainButtonClick() {

        if (!editPrice.getText().toString().isEmpty() && !editQuantity.getText().toString().isEmpty()) {

            stock.setAveragePrice(StockCalculations.calculateNewAveragePrice(stock.getAveragePrice(),
                    stock.getQuantity(), dynamicQuantity,
                    Float.parseFloat(editPrice.getText().toString())));

            stock.setQuantity(stock.getQuantity() + dynamicQuantity);
            stock.setTotalSpent(stock.getTotalSpent() + (dynamicQuantity * Float.parseFloat(editPrice.getText().toString())));
            mListener.onIncreased(stock);
            dismiss();


        } else {
            Toast.makeText(getActivity(), getString(R.string.insira_um_valor_valido), Toast.LENGTH_SHORT).show();
        }

    }

    private void increaseDynamicQuantity(int increment) {

        dynamicQuantity += increment;
        String quantity = String.valueOf(dynamicQuantity);
        editQuantity.setText(quantity);
    }

    private void handleDynamicIncrease(){

        editQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >0){
                    dynamicQuantity = Integer.parseInt(charSequence.toString());
                }else {
                    dynamicQuantity = 0;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public interface OnIncreaseListener{

        void onIncreased(Stock stock);

    }
}
