package com.erkprog.barkabar.data.network.exchangeRatesRepository;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ExRatesClient {

  private static ExchangeRatesApi mApi;
  private static final String BASE_URL = "http://www.nbkr.kg/";

  public static ExchangeRatesApi getClient() {
    if (mApi == null) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .build();

      mApi = retrofit.create(ExchangeRatesApi.class);

    }
    return mApi;
  }
}
