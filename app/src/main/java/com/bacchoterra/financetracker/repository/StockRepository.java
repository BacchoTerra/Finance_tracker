package com.bacchoterra.financetracker.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bacchoterra.financetracker.dao.StockDao;
import com.bacchoterra.financetracker.database.MyDatabase;
import com.bacchoterra.financetracker.model.Stock;

import java.util.List;

public class StockRepository {

    //SQL options
    private static final int INSERT = 0;
    private static final int DELETE = 1;
    private static final int DELETE_ALL = 2;
    private static final int UPDATE = 3;




    public StockDao mDao;
    public LiveData<List<Stock>> allRendaVariavel;

    public StockRepository(Application app) {

        MyDatabase myDatabase = MyDatabase.getInstance(app);
        mDao = myDatabase.getRendaVariavelDao();
    }

    public void insert(Stock stock){

        new SqlOperations(mDao,INSERT).execute(stock);

    }

    public void delete(Stock stock){

        new SqlOperations(mDao,DELETE).execute(stock);

    }

    public void update(Stock stock){

        new SqlOperations(mDao,UPDATE).execute(stock);

    }

    public void deleteAll (){
        new SqlOperations(mDao,DELETE_ALL).execute();

    }

    public LiveData<List<Stock>> getAllRendaVariavel(){

        allRendaVariavel = mDao.selectAllFromRendaVariavel();
        return this.allRendaVariavel;

    }

    public LiveData<List<Stock>> getAllFromRendaVariavelFinished(){

        allRendaVariavel = mDao.selectAllFromRendaVariavelFinished();

        return allRendaVariavel;

    }

    public LiveData<List<Stock>> getAllFromRendaVariavelOpened(){

        allRendaVariavel = mDao.selectAllFromRendaVariavelOpened();
        return allRendaVariavel;


    }

    public LiveData<List<Stock>> getAllFromRendaVariavelOrderByTotalSpent(){

        allRendaVariavel = mDao.selectAllFromRendaVariavelOrderByTotalSpent();
        return allRendaVariavel;

    }

    public LiveData<List<Stock>> getAllFromRendaVariavelOrderByInitialPrice(){
        allRendaVariavel = mDao.selectAllFromRendaVariavelOrderByInitialPrice();

        return allRendaVariavel;
    }

    private static class SqlOperations extends AsyncTask<Stock, Void, Void> {

        private final StockDao mDao;
        private int option_selected;

        public SqlOperations(StockDao dao, int option) {

            mDao = dao;
            option_selected = option;
        }

        @Override
        protected Void doInBackground(Stock... stocks) {


            switch (option_selected) {

                case INSERT:
                    mDao.insert(stocks[0]);
                    break;
                case DELETE:
                    mDao.delete(stocks[0]);
                    break;
                case UPDATE:
                    mDao.update(stocks[0]);
                    break;
                case DELETE_ALL:
                    mDao.deleteAllFromRendaVariavelTable();


            }

            return null;
        }
    }

}
