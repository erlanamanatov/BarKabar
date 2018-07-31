package com.erkprog.barkabar.data.network.kloopRepository;

import com.erkprog.barkabar.data.entity.KloopFeed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface KloopApi {

  @GET("feed")
  Call<KloopFeed> loadSputnikRss();

}
