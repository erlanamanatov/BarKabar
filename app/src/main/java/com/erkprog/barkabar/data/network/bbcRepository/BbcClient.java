package com.erkprog.barkabar.data.network.bbcRepository;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class BbcClient {

  private static String BASE_URL = "http://feeds.bbci.co.uk/";

  private static BbcApi mApi;

  public static BbcApi getClient() {
    if (mApi == null) {
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .build();

      mApi = retrofit.create(BbcApi.class);
    }

    return mApi;
  }
}
