package com.bacchoterra.financetracker.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bacchoterra.financetracker.dao.RendaVariavelDao;
import com.bacchoterra.financetracker.database.MyDatabase;
import com.bacchoterra.financetracker.model.RendaVariavel;

import java.util.List;

public class RendaVariavelRepository {

    //SQL options
    private static final int INSERT = 0;
    private static final int DELETE = 1;
    private static final int DELETE_ALL = 2;
    private static final int UPDATE = 3;


    public RendaVariavelDao mDao;
    public LiveData<List<RendaVariavel>> allRendaVariavel;

    public RendaVariavelRepository(Application app) {

        MyDatabase myDatabase = MyDatabase.getInstance(app);
        mDao = myDatabase.getRendaVariavelDao();

        allRendaVariavel = mDao.selectAllFromRendaVariavel();
    }

    public void insert(RendaVariavel rendaVariavel){

        new SqlOperations(mDao,INSERT).execute(rendaVariavel);

    }

    public void delete(RendaVariavel rendaVariavel){

        new SqlOperations(mDao,DELETE).execute(rendaVariavel);

    }

    public void update(RendaVariavel rendaVariavel){

        new SqlOperations(mDao,UPDATE).execute(rendaVariavel);

    }

    public void deleteAll (){
        new SqlOperations(mDao,DELETE_ALL).execute();

    }

    public LiveData<List<RendaVariavel>> getAllRendaVariavel(){

        return this.allRendaVariavel;

    }


    private class SqlOperations extends AsyncTask<RendaVariavel, Void, Void> {

        private final RendaVariavelDao mDao;
        private int option_selected;

        public SqlOperations(RendaVariavelDao dao, int option) {

            mDao = dao;
            option_selected = option;
        }

        @Override
        protected Void doInBackground(RendaVariavel... rendaVariavels) {


            switch (option_selected) {

                case INSERT:
                    mDao.insert(rendaVariavels[0]);
                    break;
                case DELETE:
                    mDao.delete(rendaVariavels[0]);
                    break;
                case UPDATE:
                    mDao.update(rendaVariavels[0]);
                    break;
                case DELETE_ALL:
                    mDao.deleteAllFromRendaVariavelTable();


            }

            return null;
        }
    }

}
