package com.bacchoterra.financetracker.repository;

import com.bacchoterra.financetracker.model.StockInformation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StockApi {

    @GET("{stockName}")
    Call<StockInformation> getStockInformation(@Path("stockName") String stockName) ;

}
