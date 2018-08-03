package com.erkprog.barkabar.data.network.kaktusRepository;

import com.erkprog.barkabar.data.entity.KaktusFeed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface KaktusApi {


  @GET("?rss")
  Call<KaktusFeed> loadKaktusFeed();
}
