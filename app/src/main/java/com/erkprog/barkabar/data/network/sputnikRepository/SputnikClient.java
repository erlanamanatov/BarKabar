package com.erkprog.barkabar.data.network.sputnikRepository;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class SputnikClient {

  public static final String BASE_URL = "https://ru.sputnik.kg/";

  private static SputnikApi mService = null;

  public static SputnikApi getClient(Context context) {
    if (mService == null) {

      final Retrofit retrofit = new Retrofit.Builder()
          .addConverterFactory(SimpleXmlConverterFactory.create())
          .baseUrl(BASE_URL)
          .build();

      mService = retrofit.create(SputnikApi.class);
    }

    return mService;
  }
}
