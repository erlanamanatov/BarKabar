package com.erkprog.barkabar.data.network.kaktusRepository;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class KaktusClient {
  private static final String BASE_URL = "https://kaktus.media/";

  private static KaktusApi mService;

  public static KaktusApi getClient() {
    if (mService == null) {
      final Retrofit retrofit = new Retrofit.Builder()
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .baseUrl(BASE_URL)
          .build();

      mService = retrofit.create(KaktusApi.class);
    }

    return mService;
  }
}
