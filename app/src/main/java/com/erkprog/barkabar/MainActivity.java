package com.erkprog.barkabar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.erkprog.barkabar.data.entity.sputnik.SputnikFeed;
import com.erkprog.barkabar.data.network.sputnikRepository.SputnikApi;
import com.erkprog.barkabar.data.network.sputnikRepository.SputnikClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  SputnikApi mService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mService = SputnikClient.getClient(this);

    Log.d(TAG, "onCreate: starting loading");
    mService.loadSputnikRss().enqueue(new Callback<SputnikFeed>() {
      @Override
      public void onResponse(Call<SputnikFeed> call, Response<SputnikFeed> response) {
        Log.d(TAG, "onResponse: starts");
        Log.d(TAG, "onResponse: " +  response.body().getChannel().getTitle());
        Log.d(TAG, "onResponse: " + response.body().getChannel().getItems().get(0).getLink());
        Log.d(TAG, "onResponse: " + response.body().getChannel().getItems().get(0).getImgUrl());
        Log.d(TAG, "onResponse: " + response.body().getChannel().getItems().size());
      }

      @Override
      public void onFailure(Call<SputnikFeed> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
        Log.d(TAG, "onFailure: starts");


      }
    });
  }
}
