package com.bacchoterra.financetracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bacchoterra.financetracker.model.RendaVariavel;

import java.util.List;

@Dao
public interface RendaVariavelDao {

    @Insert
    void insert (RendaVariavel rendaVariavel);

    @Update
    void update(RendaVariavel variavel);

    @Delete
    void delete(RendaVariavel rendaVariavel);

    @Query("DELETE FROM renda_variavel_table")
    void deleteAllFromRendaVariavelTable ();

    @Query("SELECT * FROM renda_variavel_table")
    LiveData<List<RendaVariavel>> selectAllFromRendaVariavel();



}
