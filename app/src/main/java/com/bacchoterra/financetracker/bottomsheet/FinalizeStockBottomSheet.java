package com.bacchoterra.financetracker.bottomsheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.tools.StockCalculations;
import com.bacchoterra.financetracker.view.ShowStockActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class FinalizeStockBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    //Layout components
    private TextInputEditText editPrice;
    private TextView txtProfit;
    private Button btnFinalize;

    //Model
    private Stock stock;

    //Format
    private DecimalFormat decimalFormat;

    //Interface
    private OnFinalizeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {

        try {
            mListener = (OnFinalizeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement OnFinalizeListener");
        }

        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sheet_finalize_stock, container, false);
        decimalFormat = new DecimalFormat("0.00");

        init(view);
        retrieveStock();
        bindFinalization();

        return view;

    }

    private void init(View view) {

        editPrice = view.findViewById(R.id.sheet_finalize_Stock_edit_price);
        txtProfit = view.findViewById(R.id.sheet_finalize_Stock_txt_profit);
        btnFinalize = view.findViewById(R.id.sheet_finalize_Stock_btn_finalize);
        btnFinalize.setOnClickListener(this);

    }

    private void retrieveStock() {

        if (getArguments() != null) {
            stock = (Stock) getArguments().get(ShowStockActivity.BOTTOM_SHEET_ARGS_KEY);
        }
    }

    private float calculateProfit(float finalPrice) {

        return StockCalculations.calculateProfit(stock.getAveragePrice(), finalPrice, stock.getQuantity());

    }

    private void bindFinalization() {

        editPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setProfitPrice(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void setProfitPrice(CharSequence charSequence) {

        if (charSequence.length() > 0) {
            float profit = calculateProfit(Float.parseFloat(charSequence.toString()));
            txtProfit.setText(getString(R.string.money_symbol) + " " + decimalFormat.format(profit));

            if (profit >= 0) {

                txtProfit.setTextColor(ResourcesCompat.getColor(getActivity().getResources(), R.color.profit_color, null));
                txtProfit.setCompoundDrawables(ContextCompat.getDrawable(getActivity(),
                        R.drawable.ic_baseline_arrow_drop_up_24),
                        null, null, null);

            } else {

                txtProfit.setTextColor(ResourcesCompat.getColor(getActivity().getResources(), R.color.deficit_color, null));
                txtProfit.setCompoundDrawables(ContextCompat.getDrawable(getActivity(),
                        R.drawable.ic_baseline_arrow_drop_down_24),
                        null, null, null);
            }

            setFinishedValuesToStock(profit);

        } else {
            txtProfit.setText(null);
        }


    }

    private void setFinishedValuesToStock(float profit){
        stock.setProfit(profit);
        stock.setFinished(true);
        stock.setFinalTimestamp(System.currentTimeMillis());
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == btnFinalize.getId()) {

            if (!editPrice.getText().toString().isEmpty()){
                mListener.onFinalize(stock);
                this.dismiss();
            }else {
                Toast.makeText(getActivity(), getString(R.string.insira_um_valor_valido), Toast.LENGTH_SHORT).show();
            }


        }

    }

    public interface OnFinalizeListener {

        void onFinalize(Stock stock);

    }
}
