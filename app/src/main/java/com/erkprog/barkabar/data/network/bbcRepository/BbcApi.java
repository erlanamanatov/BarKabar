package com.erkprog.barkabar.data.network.bbcRepository;

import com.erkprog.barkabar.data.entity.BbcFeed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BbcApi {

  @GET("news/world/rss.xml")
  Call<BbcFeed> loadBbcFeed();
}
