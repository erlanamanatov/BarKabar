package com.erkprog.barkabar.data.network.sputnikRepository;

import com.erkprog.barkabar.data.entity.sputnik.SputnikFeed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SputnikApi {

  @GET("export/rss2/archive/index.xml")
  Call<SputnikFeed> loadSputnikRss();


}
