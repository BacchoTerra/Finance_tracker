package com.bacchoterra.financetracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.repository.StockRepository;

import java.util.List;

public class StockViewModel extends AndroidViewModel {

    private StockRepository repository;
    private LiveData<List<Stock>> allStock;


    public StockViewModel(@NonNull Application application) {
        super(application);
        repository = new StockRepository(application);
        allStock = repository.getAllRendaVariavel();

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

    public LiveData<List<Stock>> getAllStock(){

        return this.allStock;

    }
}
