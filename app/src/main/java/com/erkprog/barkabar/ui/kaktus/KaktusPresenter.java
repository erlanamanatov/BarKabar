package com.erkprog.barkabar.ui.kaktus;

import android.util.Log;

import com.erkprog.barkabar.data.entity.KaktusFeed;
import com.erkprog.barkabar.data.entity.KaktusItem;
import com.erkprog.barkabar.data.network.kaktusRepository.KaktusApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaktusPresenter implements KaktusContract.Presenter {
  private static final String TAG = "KaktusPresenter";

  private KaktusContract.View mView;
  private KaktusApi mService;

  public KaktusPresenter(KaktusApi service) {
    mService = service;
  }


  @Override
  public boolean isAttached() {
    return mView != null;
  }

  @Override
  public void loadData() {
    if (mService != null) {

    mService.loadKaktusFeed().enqueue(new Callback<KaktusFeed>() {
      @Override
      public void onResponse(Call<KaktusFeed> call, Response<KaktusFeed> response) {
        Log.d(TAG, "onResponse: starts");
        KaktusItem item = response.body().getData().get(0);
        Log.d(TAG, "onResponse: " + item.getTitle());
        Log.d(TAG, "onResponse: " + item.getDescription());
        Log.d(TAG, "onResponse: " + item.getCreatedDate());
        Log.d(TAG, "onResponse: " + item.getLink());
        Log.d(TAG, "onResponse: " + item.getImgUrl());
      }
      @Override
      public void onFailure(Call<KaktusFeed> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
      }
    });

    }
  }

  @Override
  public void bind(KaktusContract.View view) {
    mView = view;
  }

  @Override
  public void unBind() {
    mView = null;
  }
}
