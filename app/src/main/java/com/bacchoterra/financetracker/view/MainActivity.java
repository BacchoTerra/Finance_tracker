package com.bacchoterra.financetracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.bacchoterra.financetracker.R;
import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.viewmodel.StockViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MaterialCardView cardRendaFixa;
    private MaterialCardView cardFundos;
    private MaterialCardView cardRendaVariavel;
    private MaterialCardView cardPrevidencia;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initCardSelection();

    }

    private void init(){

        cardRendaFixa = findViewById(R.id.card_renda_fixa);
        cardFundos = findViewById(R.id.card_fundos);
        cardRendaVariavel = findViewById(R.id.card_renda_variavel);
        cardPrevidencia = findViewById(R.id.card_renda_previdencia);

    }

    private void initCardSelection(){

        cardRendaFixa.setOnClickListener(view -> Toast.makeText(this, "Renda Fixa", Toast.LENGTH_SHORT).show());
        cardFundos.setOnClickListener(view -> Toast.makeText(this, "Fundos", Toast.LENGTH_SHORT).show());
        cardRendaVariavel.setOnClickListener(view -> Toast.makeText(this, "Renda Variavel", Toast.LENGTH_SHORT).show());
        cardPrevidencia.setOnClickListener(view -> Toast.makeText(this, "Previdência", Toast.LENGTH_SHORT).show());


    }



}