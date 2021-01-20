package com.bacchoterra.financetracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bacchoterra.financetracker.model.Stock;

import java.util.List;

@Dao
public interface StockDao {

    @Insert
    void insert (Stock stock);

    @Update
    void update(Stock variavel);

    @Delete
    void delete(Stock stock);

    @Query("DELETE FROM stock_table")
    void deleteAllFromRendaVariavelTable ();

    @Query("SELECT * FROM stock_table")
    LiveData<List<Stock>> selectAllFromRendaVariavel();

    @Query("SELECT * FROM stock_table WHERE isFinished = 0")
    LiveData<List<Stock>> selectAllFromRendaVariavelOpened();

    @Query("SELECT * FROM stock_table WHERE isFinished = 1")
    LiveData<List<Stock>> selectAllFromRendaVariavelFinished();

    @Query("SELECT * FROM stock_table ORDER BY totalSpent DESC")
    LiveData<List<Stock>> selectAllFromRendaVariavelOrderByTotalSpent();

    @Query("SELECT * FROM stock_table ORDER BY initialPrice DESC")
    LiveData<List<Stock>> selectAllFromRendaVariavelOrderByInitialPrice();



}
