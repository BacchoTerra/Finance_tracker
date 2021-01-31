package com.bacchoterra.financetracker.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.bacchoterra.financetracker.model.Stock;
import com.bacchoterra.financetracker.model.StockInformation;
import com.bacchoterra.financetracker.repository.StockApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchStockInformation {

    //Context
    private Context context;

    //Constants
    public static final int STATUS_SUCCESS = 200;

    //Retrofit stuff
    private Retrofit retrofit;
    private Call<StockInformation> call;
    private StockApi stockApi;
    public static final String BASE_URL = "http://databaseapi.duckdns.org:8888/api/v1/stock/value/";

    public FetchStockInformation(Context context) {

        this.context = context;

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        stockApi = retrofit.create(StockApi.class);

    }

    private boolean hasInternetConnection() {


        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }

    //TODO: Handle each of those shits the right way...like if the status of the StockInformation is 200...this is important
    public void makeCall(String stockName, OnStockFetched listener) {



            call = stockApi.getStockInformation(stockName);
            call.enqueue(new Callback<StockInformation>() {
                @Override
                public void onResponse(Call<StockInformation> call, Response<StockInformation> response) {

                    if (response.isSuccessful()) {
                        listener.onSuccess(response.body());


                    } else {
                        listener.onBackEndFailure(response.code());
                    }

                }

                @Override
                public void onFailure(Call<StockInformation> call, Throwable t) {
                    listener.onIntrinsicFailure(t);
                }
            });

    }

    public interface OnStockFetched {

        void onSuccess(StockInformation stockInformation);

        void onIntrinsicFailure(Throwable t);

        void onBackEndFailure(int code);
    }

}
