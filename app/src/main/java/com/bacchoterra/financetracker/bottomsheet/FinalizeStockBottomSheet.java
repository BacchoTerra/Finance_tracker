package com.bacchoterra.financetracker.bottomsheet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.tools.StockFinalizer;
import com.bacchoterra.financetracker.view.ShowStockActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class FinalizeStockBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener{


    //Layout components
    private TextInputEditText editPrice;
    private TextView txtProfit;
    private Button btnFinalize;

    //Interface
    private OnFinalizeListener mListener;

    //Model
    Stock stock;

    @Override
    public void onAttach(@NonNull Context context) {

        try {
            mListener = (OnFinalizeListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException("Please implement OnFinalizeListener interface");
        }

        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sheet_finalize_stock,container,false);
        init(view);
        getStockFromArguments();

        return view;

    }

    public void init(View view){

        editPrice = view.findViewById(R.id.sheet_finalize_Stock_edit_price);
        txtProfit = view.findViewById(R.id.sheet_finalize_Stock_txtProfit);
        btnFinalize = view.findViewById(R.id.sheet_finalize_Stock_btn_finalize);

        btnFinalize.setOnClickListener(this);

    }

    private void getStockFromArguments(){

        assert getArguments() != null;
        stock = (Stock) getArguments().get(ShowStockActivity.STOCK_KEY);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();


        if (id == btnFinalize.getId()){

            StockFinalizer stockFinalizer = new StockFinalizer(stock);
            stock = stockFinalizer.buildFinalizedStock(Objects.requireNonNull(editPrice.getText()).toString());
            mListener.onFinalize(stock);

        }
    }

    public interface OnFinalizeListener{

        void onFinalize(Stock stock);

    }
}
