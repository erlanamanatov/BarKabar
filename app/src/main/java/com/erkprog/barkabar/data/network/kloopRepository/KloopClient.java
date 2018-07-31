package com.erkprog.barkabar.data.network.kloopRepository;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class KloopClient {

  private static final String BASE_URL = "https://kloop.kg/";

  private static KloopApi mService = null;

  public static KloopApi getClient(Context context) {
    if (mService == null) {

      final Retrofit retrofit = new Retrofit.Builder()
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .baseUrl(BASE_URL)
          .build();

      mService = retrofit.create(KloopApi.class);
    }

    return mService;
  }
}
