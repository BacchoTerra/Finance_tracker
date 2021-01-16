package com.bacchoterra.financetracker.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bacchoterra.financetracker.dao.StockDao;
import com.bacchoterra.financetracker.model.Stock;

//Version 1 - initial version
//Version 2 - Used as a Test
//Version 3 - Initial version, but with 'graphsUsed' column deleted
@Database(entities = {Stock.class},version = 3)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase instance;
    public abstract StockDao getRendaVariavelDao();

    public synchronized static MyDatabase getInstance(Context c){

        if (instance == null){

            instance = Room.databaseBuilder(c.getApplicationContext()
                    ,MyDatabase.class,"mydatabase")
                    .fallbackToDestructiveMigration()
                    .build();

        }

        return instance;
    }




}
