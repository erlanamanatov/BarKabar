package com.erkprog.barkabar.ui.sputnik;

import android.util.Log;

import com.erkprog.barkabar.data.entity.sputnik.SputnikFeed;
import com.erkprog.barkabar.data.network.sputnikRepository.SputnikApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SputnikPresenter implements SputnikContract.Presenter {
  private static final String TAG = "SputnikPresenter";

  private SputnikContract.View mView;
  private SputnikApi mService;

  SputnikPresenter(SputnikApi service) {
    mService = service;
  }

  @Override
  public void loadData() {
    mView.showProgress();
    if (mService != null) {
      mService.loadSputnikRss().enqueue(new Callback<SputnikFeed>() {
        @Override
        public void onResponse(Call<SputnikFeed> call, Response<SputnikFeed> response) {
          if (isAttached()) {
            mView.dismissProgress();
            if (response.isSuccessful()) {
              Log.d(TAG, "onResponse: starts");
              Log.d(TAG, "onResponse: " + response.body().getChannel().getTitle());
              Log.d(TAG, "onResponse: " + response.body().getChannel().getItems().get(0).getLink());
              Log.d(TAG, "onResponse: " + response.body().getChannel().getItems().get(0).getImgUrl());
              Log.d(TAG, "onResponse: " + response.body().getChannel().getItems().size());
            } else {
              Log.d(TAG, "onResponse: not successful");
            }
          }
        }

        @Override
        public void onFailure(Call<SputnikFeed> call, Throwable t) {
          if (isAttached()) {
            mView.dismissProgress();
            Log.d(TAG, "onFailure: " + t.getMessage());
            mView.showMessage(t.getMessage());
          }

        }
      });
    }

  }

  @Override
  public boolean isAttached() {
    return mView != null;
  }

  @Override
  public void bind(SputnikContract.View view) {
    mView = view;
  }

  @Override
  public void unBind() {
    mView = null;
  }
}
