package com.bacchoterra.financetracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.repository.StockRepository;

import java.util.List;

public class StockViewModel extends AndroidViewModel {

    //SQL select options
    public static final int SELECT_ALL = 0;
    public static final int SELECT_ALL_FINISHED = 1;
    public static final int SELECT_ALL_OPENED = 2;
    public static final int SELECT_ALL_BY_INITIAL_PRICE = 3;
    public static final int SELECT_ALL_BY_TOTAL_SPENT = 4;
    public static final int SELECT_ALL_BY_NAME = 5;



    private final StockRepository repository;
    private LiveData<List<Stock>> allStock;


    public StockViewModel(@NonNull Application application) {
        super(application);

        repository = StockRepository.getInstance(application);

    }


    public void insert(Stock stock){
        repository.insert(stock);
    }

    public void delete(Stock stock){
        repository.delete(stock);
    }

    public void update(Stock stock){
        repository.update(stock);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<Stock>> getAllStock(int option,@Nullable String query){

        switch (option){

            case SELECT_ALL:
                allStock = repository.getAllRendaVariavel();
                break;
            case SELECT_ALL_FINISHED:
                allStock = repository.getAllFromRendaVariavelFinished();
                break;

            case SELECT_ALL_OPENED:
                allStock = repository.getAllFromRendaVariavelOpened();
                break;

            case SELECT_ALL_BY_INITIAL_PRICE:
                allStock = repository.getAllFromRendaVariavelOrderByInitialPrice();
                break;

            case SELECT_ALL_BY_TOTAL_SPENT:
                allStock = repository.getAllFromRendaVariavelOrderByTotalSpent();
                break;

            case SELECT_ALL_BY_NAME:
                allStock = repository.getAllFromRendaVariavelOrderByName(query);

        }

        return this.allStock;

    }

    public LiveData<List<Float>> getAllAveragePrices(){

        return repository.getAllAveragePrices();

    }

    public LiveData<List<Float>> getAllProfit(){

        return repository.getAllProfit();

    }
}
