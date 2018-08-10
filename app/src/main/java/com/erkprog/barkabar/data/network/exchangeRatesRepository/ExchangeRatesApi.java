package com.erkprog.barkabar.data.network.exchangeRatesRepository;

import com.erkprog.barkabar.data.entity.ExchangeRatesResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExchangeRatesApi {

  @GET("XML/daily.xml")
  Call<ExchangeRatesResponse> getExchangeRates();
}
