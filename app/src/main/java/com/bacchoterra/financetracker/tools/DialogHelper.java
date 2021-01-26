package com.bacchoterra.financetracker.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bacchoterra.financetracker.view.ShowStockActivity;

public class DialogHelper {

    //Requirements
    private Context context;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    public DialogHelper(Context context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
    }

    public void constructSimpleDialog(String title, String message, String positiveBtn, @Nullable String negativeBtn, boolean isCancellable,OnBtnClickedListener listener) {

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(isCancellable);

        builder.setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onPositive();
            }
        });

        if (negativeBtn != null){

            builder.setNegativeButton(negativeBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onNegative();
                }
            });

        }

        alertDialog = builder.create();
    }

    public void constructSimpleDialog(int title, int message, int positiveBtn, @Nullable String negativeBtn, boolean isCancellable,OnBtnClickedListener listener) {

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(isCancellable);

        builder.setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onPositive();
            }
        });

        if (negativeBtn != null){

            builder.setNegativeButton(negativeBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onNegative();
                }
            });

        }

        alertDialog = builder.create();
    }

    public void showDialog(){
        alertDialog.show();
    }

    public void cancelDialog(){
        alertDialog.cancel();
    }

    public interface OnBtnClickedListener {
        void onPositive();

        void onNegative();
    }

}
