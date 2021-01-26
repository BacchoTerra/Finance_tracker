package com.bacchoterra.financetracker.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bacchoterra.financetracker.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FinalizeStockBottomSheet extends BottomSheetDialogFragment{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sheet_finalize_stock,container,false);

        return view;

    }
}
