package com.bacchoterra.financetracker.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bacchoterra.financetracker.dao.RendaVariavelDao;
import com.bacchoterra.financetracker.model.RendaVariavel;

@Database(entities = {RendaVariavel.class},version = 1)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase instance;
    public abstract RendaVariavelDao getRendaVariavelDao();

    public synchronized MyDatabase getInstance(Context c){

        if (instance == null){

            instance = Room.databaseBuilder(c.getApplicationContext()
                    ,MyDatabase.class,"mydatabase")
                    .fallbackToDestructiveMigration().build();

        }

        return instance;
    }

}
