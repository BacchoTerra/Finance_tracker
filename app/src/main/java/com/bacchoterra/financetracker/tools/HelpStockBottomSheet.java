package com.bacchoterra.financetracker.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bacchoterra.financetracker.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class HelpStockBottomSheet extends BottomSheetDialogFragment {

    private ChipGroup chipGroup;
    private TextView txtInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sheet_help_stock, container, false);
        init(view);
        chipGroupSelection();

        return view;
    }

    private void init(View view) {

        chipGroup = view.findViewById(R.id.sheet_help_stock_chip_group);
        txtInfo = view.findViewById(R.id.sheet_help_stock_txt_info);

    }

    ;

    private void chipGroupSelection() {

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {

                if (group.getCheckedChipId() == R.id.sheet_help_stock_chip_tecnica) {

                    txtInfo.setText(getResources().getString(R.string.tecnica_utilizada_description));

                } else if (group.getCheckedChipId() == R.id.sheet_help_stock_chip_graficos) {

                    txtInfo.setText(getResources().getString(R.string.graficos_analisados_description));
                } else {

                    txtInfo.setText(getResources().getString(R.string.tempo_esperado_description));
                }

            }
        });

    }
}
